
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.*;

public class Login extends JFrame {
    JButton confirmButton = new JButton("Konfirmo");
    JPanel signPanel = new JPanel();
    JPanel namePanel = new JPanel();
    JPanel surnamenamePanel = new JPanel();
    JPanel passwordiPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel nameLabel = new JLabel("Emri:");
    JTextField nameField = new JTextField(20);
    JLabel surnameLabel = new JLabel("Mbiemri:");
    JTextField surnameField = new JTextField(20);
    JLabel passwordiLabel = new JLabel("Paswordi:");
    JPasswordField passwordiField = new JPasswordField(20);
    Client client = new Client();

    public Login() {
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        surnamenamePanel.add(surnameLabel);
        surnamenamePanel.add(surnameField);
        passwordiPanel.add(passwordiLabel);
        passwordiPanel.add(passwordiField);
        confirmButton.setBounds(50, 50, 50, 50);
        confirmButton.addActionListener(new ClickonConfirm());
        buttonPanel.add(confirmButton);
        signPanel.setLayout(new BoxLayout(signPanel, BoxLayout.Y_AXIS));
        signPanel.add(namePanel);
        signPanel.add(surnamenamePanel);
        signPanel.add(passwordiPanel);
        signPanel.add(buttonPanel);
        add(signPanel);
        setVisible(true);
    }

    class ClickonConfirm implements ActionListener {

        public ClickonConfirm() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == confirmButton) {
                LoginResult();
            }
        }
    }

    public void LoginResult() {

        client.setName(nameField.getText());
        client.setSurname(surnameField.getText());
        client.setPassword(passwordiField.getText());
        client.setBalance(0);

        boolean found;

        found = DbOperations.LoginCheck(client);

        if (!found) {
            Popup popup = new Popup("Kredencialet nuk jane te sakta");
        } else {
            Balanca bal = new Balanca(client);
            dispose();
        }
    }
}
