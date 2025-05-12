import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.sql.*;

public class Historik extends JFrame {

    JPanel mainPanel = new JPanel();
    Client client;
    Client receiver = new Client();

    public Historik(Client client) {
        this.client = client;
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setSize(500, 500);
        shtoHistorik();
        add(scrollPane);
        setVisible(true);
    }

    public void shtoHistorik() {
        String sql = "SELECT emri, mbiemri, shuma FROM transferimet WHERE transferuesi IN (SELECT id FROM klientet WHERE emri = ? and mbiemri =? )";

        try (Connection db = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, client.getName());
                stmt.setString(2, client.getSurname());

                try (ResultSet rst = stmt.executeQuery()) {
                    while (rst.next()) {
                        receiver.setName(rst.getString("emri"));
                        receiver.setSurname(rst.getString("mbiemri"));
                        double transferredAmount = rst.getDouble("shuma");

                        JLabel nameLabel = new JLabel("Emri:" + receiver.getName());
                        JLabel surnameLabel = new JLabel("Mbiemri:" + receiver.getSurname());
                        JLabel amountLabel = new JLabel("Shuma:" + transferredAmount);

                        JPanel transferPanel = new JPanel();
                        transferPanel.add(nameLabel);
                        transferPanel.add(surnameLabel);
                        transferPanel.add(amountLabel);

                        mainPanel.add(transferPanel);
                        mainPanel.add(Box.createVerticalStrut(20));

                        revalidate();
                        repaint();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Popup popup = new Popup("Dicka shkoi keq");
        }
    }
}
