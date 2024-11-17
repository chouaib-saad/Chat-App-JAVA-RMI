package Formation.GestionFormation.chat.user;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class UserReceive implements Runnable {
    private Socket s;

    public UserReceive(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            BufferedReader brIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while (true) {
                String s = brIn.readLine();
                String[] strs = s.split("\\.");
                String info = strs[0];
                String name = "", line = "";
                if (strs.length == 2)
                    line = strs[1];
                else if (strs.length == 3) {
                    line = strs[1];
                    name = strs[2];
                }

                if (info.equals("1")) {  // 1 pour message
                    UserIHM.textMessage.append(line + "\r\n");
                    UserIHM.textMessage.setCaretPosition(UserIHM.textMessage.getText().length());
                } else if (info.equals("2") || info.equals("3")) { // 2 pour entrer et 3 pour sortir
                    if (info.equals("2")) {
                        UserIHM.textMessage.append("(Alerte) " + name + " est entré !" + "\r\n");
                        UserIHM.textMessage.setCaretPosition(UserIHM.textMessage.getText().length());
                    } else {
                        UserIHM.textMessage.append("(Alerte) " + name + " est sorti !" + "\r\n");
                        UserIHM.textMessage.setCaretPosition(UserIHM.textMessage.getText().length());
                    }
                    String list = line.substring(1, line.length() - 1);
                    String[] data = list.split(",");
                    UserIHM.user.clearSelection();
                    UserIHM.user.setListData(data);
                } else if (info.equals("4")) {  // 4 pour alertes
                    UserIHM.connect.setText("Entrer");
                    UserIHM.Quitter.setText("Sortir");
                    UserIHM.socket.close();
                    UserIHM.socket = null;
                    JOptionPane.showMessageDialog(null, "Quelqu'un utilise déjà ce nom d'utilisateur");
                    break;
                } else if (info.equals("5")) {   // 5 pour fermer le serveur
                    UserIHM.connect.setText("Entré");
                    UserIHM.Quitter.setText("Sorti");
                    UserIHM.socket.close();
                    UserIHM.socket = null;
                    break;
                } else if (info.equals("6")) {  // 6 pour message privé
                    UserIHM.textMessage.append("(Message privé) " + line + "\r\n");
                    UserIHM.textMessage.setCaretPosition(UserIHM.textMessage.getText().length());
                } else if (info.equals("7")) {
                    JOptionPane.showMessageDialog(null, "Cet utilisateur n'est pas en ligne");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "L'utilisateur est parti");
        }
    }
}
