import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatServer {

    private ServerSocket ss = null;
    private List<Client> clientLists = new ArrayList<>();

    public static void main(String[] args) {

        new ChatServer().start();

    }

    private void start() {

        try {

            ss = new ServerSocket(8888);

            while (!ss.isClosed()) {

                Socket s = ss.accept();
                Client c = new Client(s);
                new Thread(c).start();

                clientLists.add(c);

            }

        } catch (BindException e) {

            System.out.println("端口已被占用.\n请关闭相关程序并重启服务器.");
            System.exit(0);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                ss.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

    private class Client implements Runnable {

        private String nickName = null;
        private Socket s = null;
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private String str = null;


        private Client(Socket s) {

            this.s = s;

        }

        private void send(String str) {

            try {

                dos.writeUTF(str);

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        @Override
        public void run() {

            try {

                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                nickName = dis.readUTF();

                System.out.println(nickName  + " is online!" + "   " + new Date());

                while (s.isConnected()) {

                    try {

                        str = nickName + ": " + dis.readUTF();

                        System.out.println(str);

                        for (Client clientList : clientLists) {

                            clientList.send(str);

                        }

                    } catch (EOFException e) {

                        System.out.println(nickName + " is offline!" + "   " + new Date());
                        dis.close();
                        dos.close();
                        s.close();
                        break;

                    }

                }

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

}
