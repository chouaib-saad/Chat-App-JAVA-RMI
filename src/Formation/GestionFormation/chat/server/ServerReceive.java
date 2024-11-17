package Formation.GestionFormation.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class ServerReceive implements Runnable {
    private Socket socket;
    private ArrayList<Socket> listeUtilisateur;
    private Vector<String> nomUtilisateur;
    private Map<String, Socket> carte;


    public ServerReceive(Socket s, ArrayList<Socket> listaUsuario, Vector<String> nomeUsuario, Map<String, Socket> map) {
        this.socket = s;
        this.listeUtilisateur = listaUsuario;
        this.nomUtilisateur = nomeUsuario;
        this.carte = map;
    }

    public void run() {
        try {
            BufferedReader brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String s = brIn.readLine();
                String[] strs = s.split(",,");
                String info = strs[0];  // juger le type d'info
                String line = strs[1];
                //System.out.println(line);
                String name = "";
                if (strs.length == 3)
                    name = strs[2];

                if (info.equals("1")) {   // 1 pour Nouvelle demande de message
                    ServerIHM.console.append("Nouveau message ---->> " + line + "\r\n");
                    ServerIHM.console.setCaretPosition(ServerIHM.console.getText().length());
                    new ServerSend(listeUtilisateur, line, "1", "");
                } else if (info.equals("2")) {  // 2 pour connexion
                    if (!nomUtilisateur.contains(line)) {
                        ServerIHM.console.append("Nouvelle connexion demandée ---->> " + line + "\r\n");
                        ServerIHM.console.setCaretPosition(ServerIHM.console.getText().length());
                        nomUtilisateur.add(line);
                        carte.put(line, socket);
                        ServerIHM.user.setListData(nomUtilisateur);
                        new ServerSend(listeUtilisateur, nomUtilisateur, "2", line);
                    } else {
                        ServerIHM.console.append("Connexion dupliquée ---->> " + line + "\r\n");
                        ServerIHM.console.setCaretPosition(ServerIHM.console.getText().length());
                        listeUtilisateur.remove(socket);
                        new ServerSend(socket, "", "4");
                    }
                } else if (info.equals("3")) {  // 3 pour quitter
                    ServerIHM.console.append("Sorti ---->> " + line + "\r\n");
                    ServerIHM.console.setCaretPosition(ServerIHM.console.getText().length());
                    nomUtilisateur.remove(line);
                    listeUtilisateur.remove(socket);
                    carte.remove(line);
                    ServerIHM.user.setListData(nomUtilisateur);
                    new ServerSend(listeUtilisateur, nomUtilisateur, "3", line);
                    socket.close();
                    break;  // rupture de l'info
                } else if (info.equals("4")) {   // 4 pour message privé
                    ServerIHM.console.append("Nouveau message privé ---->> " + line + "\r\n");
                    ServerIHM.console.setCaretPosition(ServerIHM.console.getText().length());
                    if (carte.containsKey(name))
                        new ServerSend(carte.get(name), line, "6");
                    else
                        new ServerSend(socket, "", "7");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
