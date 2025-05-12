import javax.swing.JFrame;
import javax.swing.JLabel;

public class Popup extends JFrame {
    JLabel exist = new JLabel();

    public Popup(String teksti) {
        exist.setText(teksti);
        setSize(300, 300);
        add(exist);
        setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
