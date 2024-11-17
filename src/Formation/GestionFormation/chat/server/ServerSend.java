package Formation.GestionFormation.chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSend {
    ServerSend(ArrayList<Socket> listaUtilisateur, Object message, String info, String name) throws IOException {
        String messages = info + "." + message + "." + name;
        PrintWriter pwOut = null;
        for (Socket s : listaUtilisateur) {   // envoyer un message à chaque client nécessaire

            pwOut = new PrintWriter(s.getOutputStream(), true);
            pwOut.println(messages);
        }
    }

    ServerSend(Socket s, Object message, String info) throws IOException {
        String messages = info + "." + message;
        PrintWriter pwOut = new PrintWriter(s.getOutputStream(), true);
        pwOut.println(messages);
    }
}
