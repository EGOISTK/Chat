import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends Frame {

    private Socket s = null;
    private DataOutputStream dos = null;
    private String nickName;

    {
        nickName = null;
    }

    private TextField launchName = new TextField();
    private Button launch = new Button("Launch");
    private TextField tf = new TextField();
    private TextArea ta = new TextArea();

    public static void main(String[] args) {

        new ChatClient("ChatClient").launchFrame();

    }

    private ChatClient(String s) {

        super(s);

    }

    private void launchFrame() {

        setLocation(400, 150);
        setSize(500, 500);
        add(launchName,BorderLayout.NORTH);
        add(launch, BorderLayout.SOUTH);
        pack();

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                disconnect();
                System.exit(0);

            }

        });

        launch.addActionListener(new LaunchListener());
        tf.addActionListener(new TFListener());

        setVisible(true);
        setResizable(true);

    }

    private void connect() {

        try {

            s = new Socket("127.0.0.1", 8888);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private void disconnect() {

        try {

            dos.close();
            s.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private class LaunchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!launchName.getText().equals("")) {

                nickName = launchName.getText();

                setTitle("ChatClient-" + nickName);
                connect();

                try {

                    dos = new DataOutputStream(s.getOutputStream());

                    dos.writeUTF(nickName);
                    dos.flush();

                } catch (IOException e1) {

                    e1.printStackTrace();

                }

                remove(launchName);
                remove(launch);
                add(ta, BorderLayout.NORTH);
                add(tf, BorderLayout.SOUTH);
                pack();

            }

        }

    }

    private class TFListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String str = tf.getText();

            ta.setText(str);
            tf.setText("");

            try {

                dos = new DataOutputStream(s.getOutputStream());

                dos.writeUTF(str);
                dos.flush();

            } catch (IOException e1) {

                e1.printStackTrace();

            }

        }

    }

}
