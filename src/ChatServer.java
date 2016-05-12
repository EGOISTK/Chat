import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {

        try {

            ServerSocket ss = new ServerSocket(8888);

            while (true) {

                Socket s = ss.accept();

                DataInputStream dis = new DataInputStream(s.getInputStream());

                while (!s.isInputShutdown()) {

                    System.out.println(dis.readUTF());

                }

                dis.close();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
