package Formation.GestionFormation.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;

import javax.swing.JOptionPane;

public class Server implements Runnable {
    private int port;
    public static ArrayList<Socket> listeUtilisateurs = null;
    public static Vector<String> nomUtilisateur = null;    // sécurité de thread
    public static Map<String, Socket> carte = null;
    public static ServerSocket ss = null;
    public static boolean flag = true;

    public Server(int port) throws IOException {
        this.port = port;
    }

    public void run() {
        Socket s = null;
        listeUtilisateurs = new ArrayList<Socket>();   // Contient les ports des utilisateurs
        nomUtilisateur = new Vector<String>();      // contient les noms des utilisateurs
        carte = new HashMap<String, Socket>();   // mappage un à un des noms aux sockets

        System.out.println("Serveur démarré!");

        try {
            ss = new ServerSocket(port);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        while (flag) {
            try {
                s = ss.accept();
                listeUtilisateurs.add(s);
                new Thread(new ServerReceive(s, listeUtilisateurs, nomUtilisateur, carte)).start();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(ServerIHM.window, "Serveur arrêté！");
            }
        }
    }
}
