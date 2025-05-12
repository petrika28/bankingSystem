import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Sign extends JFrame {
    JButton confirmButton = new JButton("Konfirmo");
    JPanel signPanel = new JPanel();
    JPanel namePanel = new JPanel();
    JPanel surnamePanel = new JPanel();
    JPanel passwordiPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel nameLabel = new JLabel("Emri:");
    JTextField nameField = new JTextField("", 20);
    JLabel surnameLabel = new JLabel("Mbiemri:");
    JTextField surnameField = new JTextField("", 20);
    JLabel passwordLabel = new JLabel("Paswordi:");
    JPasswordField passwordiField = new JPasswordField("", 20);
    Client client = new Client();

    public Sign() {
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        surnamePanel.add(surnameLabel);
        surnamePanel.add(surnameField);
        passwordiPanel.add(passwordLabel);
        passwordiPanel.add(passwordiField);
        confirmButton.setBounds(50, 50, 50, 50);
        confirmButton.addActionListener(new ClickonConfirm());
        buttonPanel.add(confirmButton);
        signPanel.setLayout(new BoxLayout(signPanel, BoxLayout.Y_AXIS));
        signPanel.add(namePanel);
        signPanel.add(surnamePanel);
        signPanel.add(passwordiPanel);
        signPanel.add(buttonPanel);
        add(signPanel);
        setVisible(true);
    }

    class ClickonConfirm implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SignupResult();
        }
    }

    public void SignupResult() {
        client.setName(nameField.getText());
        client.setSurname(surnameField.getText());
        client.setPassword(passwordiField.getText());
        boolean found;

        if (client.getName().equals("") || client.getSurname().equals("") || client.getPassword().equals("")) {
            Popup bosh = new Popup("Fushat nuk mund te jene bosh");
        } else {

            found = DbOperations.checkSignup(client);

            if (!found) {
                DbOperations.addUser(client);
                dispose();
            } else {
                Popup popup = new Popup("Perdoruesi ekziston");
            }
        }
    }

}
