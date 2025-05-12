import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbOperations {
    public static void addUser(Client klienti) {
        String sql = "INSERT INTO klientet (emri,mbiemri,passwordi,balanca) VALUES (?,?,?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, klienti.getName());
            statement.setString(2, klienti.getSurname());
            statement.setString(3, klienti.getPassword());
            statement.setDouble(4, 0);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            Popup gabim = new Popup("Ndodhi nje gabim ne shtimin e perdoruesit");
        }
    }

    public static boolean checkSignup(Client klienti) {
        String sql = "SELECT * FROM klientet WHERE emri = ? AND mbiemri = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, klienti.getName());
            statement.setString(2, klienti.getSurname());

            try (ResultSet rst = statement.executeQuery()) {
                if (rst.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Popup prblm = new Popup("Dicka shkoi keq");
        }
        return false;

    }

    public static void insertTransferedAmount(Client klienti, Client marresi, double vlera) {
        String sqltransferimet = "INSERT INTO transferimet (emri,mbiemri,shuma,transferuesi) VALUES (?,?,?,?)";
        try (Connection db = DatabaseConnection.getConnection();
                PreparedStatement statement3 = db.prepareStatement(sqltransferimet)) {
            statement3.setDouble(3, vlera);
            statement3.setString(1, marresi.getName());
            statement3.setString(2, marresi.getSurname());
            statement3.setInt(4, klienti.getId());
            statement3.executeUpdate();

        } catch (SQLException e) {
            Popup gabim = new Popup("Ndodhi nje gabim");
        }
    }

    public static boolean LoginCheck(Client klienti) {

        String sql = "SELECT * FROM klientet WHERE emri = ? AND mbiemri = ? AND passwordi = ?";
        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, klienti.getName());
                statement.setString(2, klienti.getSurname());
                statement.setString(3, klienti.getPassword());

                try (ResultSet rst = statement.executeQuery()) {
                    if (rst.next()) {

                        klienti.setBalance(rst.getDouble("balanca"));
                        klienti.setId(rst.getInt("id"));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Popup pop = new Popup("Dicka shkoi keq");
        }
        return false;

    }

    public static boolean checkTransfer(Client marresi) {

        String sql2 = "SELECT balanca FROM klientet WHERE emri=? AND mbiemri=?";
        try (Connection db = DatabaseConnection.getConnection();
                PreparedStatement statement = db.prepareStatement(sql2)) {
            statement.setString(1, marresi.getName());
            statement.setString(2, marresi.getSurname());

            try (ResultSet rst = statement.executeQuery()) {
                if (rst.next()) {
                    marresi.setBalance(rst.getDouble("balanca"));
                    return true;
                }
                
            }
        } catch (SQLException e) {

        }
        return false;
        

    }

    public static void updateDb(Client klienti) {
        String sql = "UPDATE klientet SET balanca=? WHERE emri=? AND mbiemri =?";
        try (Connection db = DatabaseConnection.getConnection();
                PreparedStatement statement = db.prepareStatement(sql)) {

            statement.setDouble(1, klienti.getBalance());
            statement.setString(2, klienti.getName());
            statement.setString(3, klienti.getSurname());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
