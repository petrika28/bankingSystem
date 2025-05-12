import javax.swing.*;
import java.awt.event.*;

public class Balanca extends JFrame {
    String actionType;

    JLabel nameLabel = new JLabel();
    JLabel surnameLabel = new JLabel();
    JLabel balanceLabel = new JLabel();

    JButton depositButton = new JButton("Depozito");
    JButton withdrawButton = new JButton("Terhiq");
    JButton transferButton = new JButton("Transfero");
    JButton historyButton = new JButton("Historiku i transferimeve");
    JButton confirmButton = new JButton("Konfirmo");

    JTextField amountField = new JTextField(20);
    JTextField receiverNameField = new JTextField(20);
    JTextField receiverSurnameField = new JTextField(20);

    JLabel amountLabel = new JLabel("Vendos shumen:");
    JLabel receiverNameLabel = new JLabel("Emri");
    JLabel receiverSurnameLabel = new JLabel("Mbiemri");

    JPanel userPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel amountPanel = new JPanel();
    JPanel namePanel = new JPanel();
    JPanel surnamePanel = new JPanel();

    Client client;
    Client receiver = new Client();

    public Balanca(Client client) {
        this.client = client;
        setSize(500, 500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setupUI();
        updateUserInfo();
        setVisible(true);
    }

    private void setupUI() {
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

        depositButton.addActionListener(new DepositAction());
        withdrawButton.addActionListener(new WithdrawAction());
        transferButton.addActionListener(new TransferAction());
        historyButton.addActionListener(new HistoryAction());
        confirmButton.addActionListener(new ConfirmAction());

        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(historyButton);

        userPanel.add(nameLabel);
        userPanel.add(surnameLabel);
        userPanel.add(balanceLabel);
        userPanel.add(buttonPanel);

        add(userPanel);
    }

    class DepositAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prepareTransaction("shto");
        }
    }

    class WithdrawAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prepareTransaction("terhiq");
        }
    }

    class TransferAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prepareTransaction("transfero");
        }
    }

    class HistoryAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new Historik(client);
        }
    }

    class ConfirmAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            processTransaction();
        }
    }

    private void prepareTransaction(String action) {
        userPanel.remove(namePanel);
        userPanel.remove(surnamePanel);

        if (action.equals("transfero")) {
            namePanel.removeAll();
            surnamePanel.removeAll();

            namePanel.add(receiverNameLabel);
            namePanel.add(receiverNameField);
            surnamePanel.add(receiverSurnameLabel);
            surnamePanel.add(receiverSurnameField);

            userPanel.add(namePanel);
            userPanel.add(surnamePanel);
        }

        amountPanel.removeAll();
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);
        amountPanel.add(confirmButton);

        userPanel.remove(amountPanel);
        userPanel.add(amountPanel);

        actionType = action;
        revalidate();
        repaint();
    }

    private void processTransaction() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            receiver.setName(receiverNameField.getText());
            receiver.setSurname(receiverSurnameField.getText());

            if (amount < 0) {
                new Popup("Vlera nuk mund te jete negative");
                return;
            }

            switch (actionType) {
                case "shto":
                    client.add(amount);
                    DbOperations.updateDb(client);
                    break;
                case "terhiq":
                    if (ActionService.checkForNegativeOperation(client, amount)) {
                        new Popup("Nuk keni mjaftueshem para ne llogari");
                    } else {
                        client.withdraw(amount);
                        DbOperations.updateDb(client);
                    }
                    break;
                case "transfero":
                    if (ActionService.checkForNegativeOperation(client, amount)) {
                        new Popup("Nuk keni mjaftueshem para ne llogari");
                    } else if (client.getName().equals(receiver.getName()) &&
                               client.getSurname().equals(receiver.getSurname())) {
                        new Popup("Nuk mund t'i transferosh para vetes");
                    } else if (!DbOperations.checkTransfer(receiver)) {
                        new Popup("Klienti nuk ekziston");
                    } else {
                        client.withdraw(amount);
                        receiver.add(amount);
                        DbOperations.updateDb(client);
                        DbOperations.updateDb(receiver);
                        DbOperations.insertTransferedAmount(client, receiver, amount);
                    }
                    break;
            }

            resetUI();
            updateUserInfo();
        } catch (NumberFormatException ex) {
            new Popup("Ju lutem vendosni nje numer te vlefshem");
        }
    }

    private void resetUI() {
        amountPanel.removeAll();
        userPanel.remove(amountPanel);
        userPanel.remove(namePanel);
        userPanel.remove(surnamePanel);

        amountField.setText("");
        receiverNameField.setText("");
        receiverSurnameField.setText("");

        revalidate();
        repaint();
    }

    private void updateUserInfo() {
        nameLabel.setText("Emri: " + client.getName());
        surnameLabel.setText("Mbiemri: " + client.getSurname());
        balanceLabel.setText("Balanca: " + client.getBalance());

        revalidate();
        repaint();
    }
}
