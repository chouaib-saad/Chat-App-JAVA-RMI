package Formation.GestionFormation.chat.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class UserSend {
    UserSend(Socket s, Object message, String info, String name) throws IOException {
        String messages = info + ",," + message + ",," + name;
        PrintWriter pwOut = new PrintWriter(s.getOutputStream(), true);
        pwOut.println(messages);
    }
}
