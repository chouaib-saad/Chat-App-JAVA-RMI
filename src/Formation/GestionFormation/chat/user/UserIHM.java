package Formation.GestionFormation.chat.user;

import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class UserIHM extends JInternalFrame {
    public static JButton connect, Quitter;
    public static JTextArea textMessage;
    public static Socket socket = null;
    public static JList<String> user;

    private JTextField nomUtilisateur, port, message, msgPriv;
    private JButton msgPrive, envoyer;


    public static void main(String[] args) {
        // Run the UserIHM window inside a JFrame
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chat Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 500);

            UserIHM userIHM = new UserIHM();
            frame.add(userIHM);
            frame.setVisible(true);
        });
    }

    public UserIHM() {
        super("Discussion", true, true, true, true);
        init();
    }

    private void init() {
        this.setLayout(null);
        this.setBounds(400, 400, 530, 420);
        this.setResizable(true);

        JLabel label_nomUtilisateur = new JLabel("Utilisateur:");
        label_nomUtilisateur.setBounds(10, 28, 70, 30);
        this.add(label_nomUtilisateur);

        nomUtilisateur = new JTextField();
        nomUtilisateur.setBounds(80, 28, 70, 30);
        this.add(nomUtilisateur);

        JLabel label_port = new JLabel("Port:");
        label_port.setBounds(180, 28, 50, 30);
        this.add(label_port);

        port = new JTextField();
        port.setBounds(230, 28, 50, 30);
        this.add(port);

        connect = new JButton("Entrer");
        connect.setBounds(300, 28, 90, 30);
        this.add(connect);

        Quitter = new JButton("Quitter");
        Quitter.setBounds(400, 28, 90, 30);
        this.add(Quitter);

        textMessage = new JTextArea();
        textMessage.setBounds(10, 70, 340, 220);
        textMessage.setEditable(false);
        textMessage.setLineWrap(true);
        textMessage.setWrapStyleWord(true);

        JScrollPane paneText = new JScrollPane(textMessage);
        paneText.setBounds(10, 90, 360, 240);
        this.add(paneText);

        JLabel label_listeUtilisateur = new JLabel("Liste des Utilisateurs");
        label_listeUtilisateur.setBounds(380, 58, 200, 50);
        this.add(label_listeUtilisateur);

        user = new JList<>();
        JScrollPane paneUser = new JScrollPane(user);
        paneUser.setBounds(375, 90, 140, 240);
        this.add(paneUser);

        JLabel label_msg = new JLabel("Entrez un message");
        label_msg.setBounds(10, 320, 180, 50);
        this.add(label_msg);

        message = new JTextField();
        message.setBounds(10, 355, 188, 30);
        this.add(message);

        JLabel label_utilisateur = new JLabel("Expéditeur");
        label_utilisateur.setBounds(272, 320, 250, 50);
        this.add(label_utilisateur);

        msgPriv = new JTextField("non sélectionné");
        msgPriv.setBounds(272, 355, 100, 30);
        msgPriv.setEnabled(false);
        this.add(msgPriv);

        msgPrive = new JButton("Message Privé");
        msgPrive.setBounds(376, 355, 140, 30);
        this.add(msgPrive);

        envoyer = new JButton("Groupe");
        envoyer.setBounds(190, 355, 77, 30);
        this.add(envoyer);

        setupListeners();
        this.setVisible(true);
    }

    private void setupListeners() {
        // Handle window closing
        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if (socket != null && socket.isConnected()) {
                    try {
                        new UserSend(socket, getNomUser(), "3", "");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                System.exit(0);
            }
        });

        // Quit button action
        Quitter.addActionListener(e -> {
            if (socket != null && socket.isConnected()) {
                try {
                    new UserSend(socket, getNomUser(), "3", "");
                    connect.setText("Entrer");
                    Quitter.setText("Déconnecté!");
                    socket.close();
                    socket = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "La connexion a été interrompue !");
            }
        });

        // Connect button action
        connect.addActionListener(e -> {
            if (socket != null && socket.isConnected()) {
                JOptionPane.showMessageDialog(null, "Déjà connecté !");
            } else {
                String ip = "127.0.0.1";
                String portClinet = port.getText();
                String username = nomUtilisateur.getText();

                if (username.isEmpty() || portClinet.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "L'utilisateur ou le port ne peuvent pas être vides !");
                } else {
                    try {
                        int porte = Integer.parseInt(portClinet);
                        socket = new Socket(ip, porte);
                        connect.setText("Entré");
                        Quitter.setText("Quitter");
                        new UserSend(socket, getNomUser(), "2", "");
                        new Thread(new UserReceive(socket)).start();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Échec de la connexion, vérifiez l'IP et le port !");
                    }
                }
            }
        });

        // Private message button action
        msgPrive.addActionListener(e -> {
            if ("non sélectionné".equals(msgPriv.getText())) {
                JOptionPane.showMessageDialog(null, "Pas d'utilisateur sélectionné !");
            } else {
                envoyerMsgPrive();
            }
        });

        // Group message button action
        envoyer.addActionListener(e -> envoyerMsg());

        // User list selection
        user.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String selectedUser = user.getSelectedValue();
                    if (selectedUser != null && !selectedUser.isEmpty()) {
                        msgPriv.setText(selectedUser.trim());
                    }
                }
            }
        });
    }

    private void envoyerMsg() {
        String msg = message.getText();
        if (msg.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il n'y a rien à envoyer !");
        } else if (socket == null || !socket.isConnected()) {
            JOptionPane.showMessageDialog(this, "Pas de connexion !");
        } else {
            try {
                new UserSend(socket, getNomUser() + ": " + msg, "1", "");
                message.setText("");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Échec de l'envoi !");
            }
        }
    }

    private void envoyerMsgPrive() {
        String msg = message.getText();
        if (msg.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il n'y a rien à envoyer !");
        } else if (socket == null || !socket.isConnected()) {
            JOptionPane.showMessageDialog(this, "Pas de connexion !");
        } else {
            try {
                new UserSend(socket, getNomUser() + ": " + msg, "4", msgPriv.getText());
                textMessage.append(getNomUser() + ": " + msg + "\n");
                message.setText("");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Message privé non envoyé !");
            }
        }
    }

    private String getNomUser() {
        return nomUtilisateur.getText();
    }
}
