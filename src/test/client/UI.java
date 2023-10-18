package test.client;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import me.dontsleep404.customsocket.DClient;
import me.dontsleep404.customsocket.event.EventHandle;
import me.dontsleep404.customsocket.event.EventPacket;
import me.dontsleep404.customsocket.packet.Packet;
import test.packet.PacketConnect;
import test.packet.PacketDisconnect;
import test.packet.PacketMessage;
import test.packet.PacketMessageCrypt;
import test.packet.PacketSetInfo;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static java.lang.System.out;

public class UI extends JFrame implements ActionListener {

    JTextArea taMessages;
    JTabbedPane tabbedPane;
    JTextField tfInput;
    JButton btnSend, btnExit, btnSendFile, btnSendEncrypt;
    private JFileChooser fileChooser;
    private DClient client;
    String username;

    public UI(String host, int port, String username) throws Exception {
        super(username);
        this.username = username;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildInterface();
        client = new DClient("localhost", 25565);
        client.setEventHandle(eventHandle());
        if (client.connect()) {
            client.listen();
            PacketSetInfo packet = new PacketSetInfo(username);
            client.sendPacket(packet);
        } else {
            taMessages.append("Failed to connect to server\n");
        }

    }

    public EventHandle eventHandle() {
        return new EventHandle(new ArrayList<Class<? extends Packet>>() {
            {
                add(PacketMessage.class);
                add(PacketSetInfo.class);
                add(PacketConnect.class);
                add(PacketDisconnect.class);
                add(PacketMessageCrypt.class);
            }
        }) {
            public HashMap<DClient, String> clients = new HashMap<DClient, String>();

            @Override
            public void onConnect(EventPacket eventPacket) {
                taMessages.append("Connected to server\n");
            }

            @Override
            public void onDisconnect(EventPacket eventPacket) {
                taMessages.append("Disconnected from server\n");
            }

            @Override
            public void onSentPacket(DClient client, Packet packet) {
                if (packet instanceof PacketMessageCrypt) {
                    ((PacketMessageCrypt) packet).encryptWithKey();
                    System.out.println(packet);
                }
                super.onSentPacket(client, packet);
            }

            @Override
            public void onPacketReceived(DClient client, Packet packet) {
                if (packet instanceof PacketSetInfo) {
                    PacketSetInfo packetSetInfo = (PacketSetInfo) packet;
                    clients.put(client, packetSetInfo.getName());
                    taMessages.append(packetSetInfo.getName() + ": Connected\n");
                }
                if (packet instanceof PacketDisconnect) {
                    String name = clients.get(client);
                    taMessages.append(name + ": Disconnected\n");
                    clients.remove(client);
                }
                if (packet instanceof PacketMessage) {
                    if (packet instanceof PacketMessageCrypt) {
                        ((PacketMessageCrypt) packet).decryptWithKey();
                    }
                    PacketMessage packetMessage = (PacketMessage) packet;
                    if (packetMessage.getType() == PacketMessage.Type.MESSAGE) {
                        taMessages.append(packetMessage.getName() + ": " + packetMessage.getMessage() + "\n");
                    }
                    if (packetMessage.getType() == PacketMessage.Type.FILE) {
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(packetMessage.getFileName());
                            fileOutputStream.write(packetMessage.getFileData());
                            fileOutputStream.close();
                            taMessages.append("Tệp tin đã được lưu lại: " + packetMessage.getFileName() + "\n");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    public void buildInterface() {
        tabbedPane = new JTabbedPane();
        btnSend = new JButton("Send");
        btnExit = new JButton("Exit");
        btnSendFile = new JButton("Send File"); // Nút gửi tệp tin
        btnSendEncrypt = new JButton("Send Encrypt");
        taMessages = new JTextArea();
        taMessages.setRows(10);
        taMessages.setColumns(50);
        taMessages.setEditable(false);
        tfInput = new JTextField(50);
        JScrollPane sp = new JScrollPane(taMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp, "Center");
        JPanel bp = new JPanel(new FlowLayout());
        bp.add(tfInput);
        bp.add(btnSend);
        bp.add(btnSendEncrypt);
        bp.add(btnSendFile); // Thêm nút gửi tệp tin vào giao diện
        bp.add(btnExit);
        add(bp, "South");
        btnSend.addActionListener(this);
        btnSendEncrypt.addActionListener(this);
        btnSendFile.addActionListener(this); // Xử lý sự kiện cho nút gửi tệp tin
        btnExit.addActionListener(this);
        setSize(500, 300);
        setVisible(true);
        pack();
        fileChooser = new JFileChooser(); // Khởi tạo JFileChooser
        fileChooser.setMultiSelectionEnabled(false);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnExit) {
            System.exit(0);
        } else if (evt.getSource() == btnSend) {
            String message = tfInput.getText().trim();
            client.sendPacket(new PacketMessage(message, username));
            tfInput.setText("");
        } else if (evt.getSource() == btnSendFile) {
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                sendFile(selectedFile.getAbsolutePath());
            }
        } else if (evt.getSource() == btnSendEncrypt) {
            String message = tfInput.getText().trim();
            client.sendPacket(new PacketMessageCrypt(message, username));
            tfInput.setText("");
        }
    }

    private void sendFile(String filePath) {
        try {
            File fileToSend = new File(filePath);
            if (!fileToSend.exists()) {
                taMessages.append("Tệp tin không tồn tại.\n");
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(fileToSend);
            // convert file to FilePacket

            byte[] fileData = new byte[(int) fileToSend.length()];
            fileInputStream.read(fileData);
            fileInputStream.close();
            PacketMessage packetMessage = new PacketMessage(fileToSend.getName(), fileData);
            client.sendPacket(packetMessage);

            taMessages.append("Bạn đã gửi một tệp tin: " + fileToSend.getName() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        try {
            new UI("localhost", 25565, args[0]);
        } catch (Exception ex) {
            out.println("Error --> " + ex.getMessage());
        }
    }
}
