import java.io.DataInputStream;
import java.io.EOFException;
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

                String nickName = dis.readUTF();

                System.out.println(nickName  + " is online!");

                while (s.isConnected()) {

                    try {

                        System.out.println(dis.readUTF());

                    } catch (EOFException e) {

                        System.out.println(nickName + " is offline!");
                        break;

                    }

                }

                dis.close();
                s.close();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
