import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {

    TextField nickName = new TextField();
    Button launch = new Button("Launch");
    TextField tf = new TextField();
    TextArea ta = new TextArea();

    public static void main(String[] args) {
        new ChatClient("ChatClient").launchFrame();
    }

    public ChatClient(String s) {
        super(s);
    }

    public void launchFrame() {
        setLocation(400, 150);
        setSize(500, 500);
        add(nickName,BorderLayout.NORTH);
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

    public void connect() {
        try {
            Socket s = new Socket("127.0.0.1", 8888);
        } catch (UnknownHostException e) {
          e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private class LaunchListener implements ActionListener {

    }*/

    private class LaunchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (nickName.getText() != null) {
                remove(nickName);
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
            ta.setText(tf.getText().trim());
            tf.setText("");
        }

    }

}
