import java.awt.*;

public class ChatClient extends Frame {

    TextField tf = new TextField();
    TextArea ta = new TextArea();

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }

    public void launchFrame() {
        setLocation(400, 150);
        setSize(500, 500);
        add(ta, BorderLayout.NORTH);
        add(tf, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
}
