import java.sql.SQLException;
import java.util.ArrayList;

public class ActionService {

    public static boolean checkForNegativeValue(double value) {
        if (value < 0)
            return true;

        return false;
    }

    public static boolean checkForNegativeOperation(Client client, double value) {
        if ((client.getBalance() - value) < 0)
            return true;

        return false;
    }

    public static String deposit(Client client, Double value) {
        client.add(value);
        try {
            DbOperations.updateDb(client);
            return "Depozitimi u krye me sukses";
        } catch (SQLException e) {
            return e.getMessage();
        }

    }

    public static String withdraw(Client client, Double value) {
        client.withdraw(value);
        try {
            DbOperations.updateDb(client);
            return "Terheqja u krye me sukses";
        } catch (SQLException e) {
            return e.getMessage();
        }

    }

    public static boolean CheckTransfer(Client client) {
        try {
            return DbOperations.checkTransfer(client);
        } catch (SQLException e) {
            e.getMessage();
            return false;
        }

    }

    public static boolean LoginCheck(Client client) {
        try {
            return DbOperations.LoginCheck(client);
        } catch (SQLException e) {
            e.getMessage();
            return false;
        }

    }

    public static String checkSignup(Client client) {
        try {
            if (!DbOperations.checkSignup(client)) {
                ActionService.addUser(client);
                return "Klienti u shtua me sukses";
            }
            return "Klienti ekziston";

        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public static String transfer(Client client, Client receiver, Double value,
            String transferName, String transferSurname) {

        receiver.setName(transferName);
        receiver.setSurname(transferSurname);
        client.withdraw(value);
        receiver.add(value);
        try {
            DbOperations.updateDb(client);
            DbOperations.updateDb(receiver);
            DbOperations.insertTransferedAmount(client, receiver, value);
            return "Tansferimi u krye me sukses";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public static String addUser(Client client) {
        try {
            DbOperations.addUser(client);
            return "Perdoruesi u shtua me sukses";
        } catch (SQLException e) {
            return e.getMessage();
        }

    }

    public static ArrayList<Client> getTransferClients(Client client) {
        return DbOperations.getTransferClients(client);
    }

    public static ArrayList<Double> getTransferAmounts(Client client) {
        return DbOperations.getTransferAmounts(client);
    }

}
