import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {

    private Socket s = null;
    private String nickName = null;
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
        launch.addActionListener(new LaunchListener());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        tf.addActionListener(new TFListener());
        setVisible(true);
        setResizable(true);
        connect();
    }

    private void connect() {
        try {
            s = new Socket("127.0.0.1", 8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class LaunchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!launchName.getText().equals("")) {
                nickName = launchName.getText();
                try {
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF(nickName + " is launched!");
                    dos.flush();
                    dos.close();
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
            String str = tf.getText().trim();
            ta.setText(str);
            tf.setText("");
            try {
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(str);
                dos.flush();
                dos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

}
