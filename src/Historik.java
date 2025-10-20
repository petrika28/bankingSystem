import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.sql.*;
import java.util.ArrayList;

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
        ArrayList<Client> receivers=ActionService.getTransferClients(client);
        ArrayList<Double> amounts=ActionService.getTransferAmounts(client);
        
        for (int i=0;i<receivers.size();i++){
        JLabel nameLabel = new JLabel("Emri:" + receivers.get(i).getName());
        JLabel surnameLabel = new JLabel("Mbiemri:" + receivers.get(i).getSurname());
        JLabel amountLabel = new JLabel("Shuma:" + amounts.get(i));

        JPanel transferPanel = new JPanel();
        transferPanel.add(nameLabel);
        transferPanel.add(surnameLabel);
        transferPanel.add(amountLabel);
        mainPanel.add(transferPanel);
        mainPanel.add(Box.createVerticalStrut(20));    
        }
        
        revalidate();
        repaint();
    }

}
