import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ServerSocket ss = null;

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

            }

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

        private Socket s = null;
        DataInputStream dis = null;

        private Client(Socket s) {

            this.s = s;

        }

        @Override
        public void run() {

            try {

                dis = new DataInputStream(s.getInputStream());

                String nickName = dis.readUTF();

                System.out.println(nickName  + " is online!");

                while (s.isConnected()) {

                    try {

                        System.out.println(nickName + ":" + dis.readUTF());

                    } catch (EOFException e) {

                        System.out.println(nickName + " is offline!");
                        dis.close();
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
