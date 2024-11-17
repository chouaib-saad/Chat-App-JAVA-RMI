package Formation.GestionFormation.chat.server;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ServerIHM {
    public static JFrame window;
    public static int ports;
    public static JTextArea console;
    public static JList<String> user;

    JButton demarrer, quitter, envoyer;
    JTextField nomServeur, portServeur, message;

    //main
    public static void main(String[] args) {
        new ServerIHM();
    }

    public ServerIHM() {
        init();
    }

    public void init() {   // layout do Serveur
        window = new JFrame("Serveur");
        window.setLayout(null);
        window.setBounds(200, 200, 500, 350);
        window.setResizable(false);

        JLabel labelnomServeur = new JLabel("Serveur:");
        labelnomServeur.setBounds(10, 8, 80, 30);
        window.add(labelnomServeur);

        nomServeur = new JTextField();
        nomServeur.setBounds(80, 8, 60, 30);
        window.add(nomServeur);

        JLabel label_port = new JLabel("Port:");
        label_port.setBounds(150, 8, 60, 30);
        window.add(label_port);

        portServeur = new JTextField();
        portServeur.setBounds(200, 8, 70, 30);
        window.add(portServeur);

        demarrer = new JButton("Entrer");
        demarrer.setBounds(280, 8, 90, 30);
        window.add(demarrer);

        quitter = new JButton("Quitter");
        quitter.setBounds(380, 8, 110, 30);
        window.add(quitter);

        console = new JTextArea();
        console.setBounds(10, 70, 340, 320);
        console.setEditable(false);  // não pode ser editado

        console.setLineWrap(true);  // automatic content line feed
        console.setWrapStyleWord(true);

        JLabel label_text = new JLabel("Panneau du Serveur");
        label_text.setBounds(100, 47, 190, 30);
        window.add(label_text);

        JScrollPane paneText = new JScrollPane(console);
        paneText.setBounds(10, 70, 340  , 220);
        window.add(paneText);

        JLabel label_listaUsuario = new JLabel("Liste des Utilisateurs");
        label_listaUsuario.setBounds(357, 47, 180, 30);
        window.add(label_listaUsuario);

        user = new JList<String>();
        JScrollPane paneUser = new JScrollPane(user);
        paneUser.setBounds(355, 70, 130, 220);
        window.add(paneUser);

        message = new JTextField();
        message.setBounds(0, 0, 0, 0);
        window.add(message);

        envoyer = new JButton("Envoyer");
        envoyer.setBounds(0, 0, 0, 0);
        window.add(envoyer);

        myEvent();  // add listeners
        window.setVisible(true);

        startServerWithMyCredentials();
    }

    public void myEvent() {
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (Server.listeUtilisateurs != null && Server.listeUtilisateurs.size() != 0) {
                    try {
                        new ServerSend(Server.listeUtilisateurs, "", "5", "");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                System.exit(0); // sair da janela
            }
        });

        quitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Server.ss == null || Server.ss.isClosed()) {
                    JOptionPane.showMessageDialog(window, "Le serveur a été fermé !");
                } else {
                    if (Server.listeUtilisateurs != null && Server.listeUtilisateurs.size() != 0) {
                        try {
                            new ServerSend(Server.listeUtilisateurs, "", "5", "");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    try {
                        demarrer.setText("Démarrer");
                        quitter.setText("Fermer");
                        Server.ss.close();
                        Server.ss = null;
                        Server.listeUtilisateurs = null;
                        Server.flag = false;   // importante
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        demarrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Server.ss != null && !Server.ss.isClosed()) {
                    JOptionPane.showMessageDialog(window, "Le serveur a été démarré !");
                } else {
                    ports = getPort();
                    if (ports != 0) {
                        try {
                            Server.flag = true;
                            new Thread(new Server(ports)).start(); // inicia servidor thread
                            demarrer.setText("demarrer");
                            quitter.setText("Fermer");
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(window, "Échec de l'exécution"






                            );
                        }
                    }
                }
            }
        });

        message.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    envoyerMsg();
                }
            }
        });

        envoyer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                envoyerMsg();
            }
        });
    }

    public void envoyerMsg() {
        String messages = message.getText();
        if ("".equals(messages)) {
            JOptionPane.showMessageDialog(window, "Il n'y a rien à envoyer!");
        } else if (Server.listeUtilisateurs == null || Server.listeUtilisateurs.size() == 0) {
            JOptionPane.showMessageDialog(window, "Pas de connexion!");
        } else {
            try {
                new ServerSend(Server.listeUtilisateurs, getNomServeur() + ": " + messages, "1", "");
                message.setText(null);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(window, "Échec de l'envoi !");
            }
        }
    }

    public int getPort() {
        String port = portServeur.getText();
        String name = nomServeur.getText();
        if ("".equals(port) || "".equals(name)) {
            JOptionPane.showMessageDialog(window, "Aucun port ou nom d'utilisateur trouvé!");
            return 0;
        } else {
            return Integer.parseInt(port);
        }
    }

    public String getNomServeur() {
        return nomServeur.getText();
    }

    public void startServerWithMyCredentials(){
            try {
                Server.flag = true;
                new Thread(new Server(8000)).start(); // inicia servidor thread
                demarrer.setText("demarrer");
                quitter.setText("Fermer");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(window, "Échec de l'exécution"

                );
            }

            nomServeur.setText("serveur");
            portServeur.setText("8000");

    }
}