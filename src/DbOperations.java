import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



//import com.mysql.cj.protocol.a.SqlDateValueEncoder;

public class DbOperations {
    public static void addUser(Client client) throws SQLException {
        String sql = "INSERT INTO klientet (emri,mbiemri,passwordi,balanca) VALUES (?,?,?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname());
            statement.setString(3, client.getPassword());
            statement.setDouble(4, 0);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Ndodhi nje gabim ne shtimin e perdoruesit");
        }
    }

    public static boolean checkSignup(Client client) throws SQLException {
        String sql = "SELECT * FROM klientet WHERE emri = ? AND mbiemri = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getSurname());

            try (ResultSet rst = statement.executeQuery()) {
                if (rst.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Dicka shkoi keq");
        }
        return false;

    }

    public static void insertTransferedAmount(Client client, Client receiver, double vlera) throws SQLException {
        String sqltransfers = "INSERT INTO transferimet (emri,mbiemri,shuma,transferuesi) VALUES (?,?,?,?)";
        try (Connection db = DatabaseConnection.getConnection();
                PreparedStatement statement3 = db.prepareStatement(sqltransfers)) {
            statement3.setDouble(3, vlera);
            statement3.setString(1, receiver.getName());
            statement3.setString(2, receiver.getSurname());
            statement3.setInt(4, client.getId());
            statement3.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Ndodhi nje gabim");
        }
    }

    public static boolean LoginCheck(Client client) throws SQLException {

        String sql = "SELECT * FROM klientet WHERE emri = ? AND mbiemri = ? AND passwordi = ?";
        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, client.getName());
                statement.setString(2, client.getSurname());
                statement.setString(3, client.getPassword());

                try (ResultSet rst = statement.executeQuery()) {
                    if (rst.next()) {

                        client.setBalance(rst.getDouble("balanca"));
                        client.setId(rst.getInt("id"));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Dicka shkoi keq");
        }
        return false;

    }

    public static boolean checkTransfer(Client receiver) throws SQLException {

        String sql2 = "SELECT balanca FROM klientet WHERE emri=? AND mbiemri=?";
        try (Connection db = DatabaseConnection.getConnection();
                PreparedStatement statement = db.prepareStatement(sql2)) {
            statement.setString(1, receiver.getName());
            statement.setString(2, receiver.getSurname());

            try (ResultSet rst = statement.executeQuery()) {
                if (rst.next()) {
                    receiver.setBalance(rst.getDouble("balanca"));
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Dicka shkoi keq");

        }

    }

    public static void updateDb(Client client) throws SQLException {
        String sql = "UPDATE klientet SET balanca=? WHERE emri=? AND mbiemri =?";
        try (Connection db = DatabaseConnection.getConnection();
                PreparedStatement statement = db.prepareStatement(sql)) {

            statement.setDouble(1, client.getBalance());
            statement.setString(2, client.getName());
            statement.setString(3, client.getSurname());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Ndodhi nje gabim");
        }
    }

    public static ArrayList<Client> getHistoricClients(Client client) {
        String sql = "SELECT emri, mbiemri FROM transferimet WHERE transferuesi IN (SELECT id FROM klientet WHERE emri = ? and mbiemri =? )";
        Client receiver = new Client();
        ArrayList<Client> receivers = new ArrayList<>();
        try (Connection db = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, client.getName());
                stmt.setString(2, client.getSurname());

                try (ResultSet rst = stmt.executeQuery()) {
                    while (rst.next()) {
                        receiver.setName(rst.getString("emri"));
                        receiver.setSurname(rst.getString("mbiemri"));
                        receivers.add(receiver);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receivers;
    }

    public static ArrayList<Double> getAmounts(Client client) {
        String sql = "SELECT shuma FROM transferimet WHERE transferuesi IN (SELECT id FROM klientet WHERE emri = ? and mbiemri =? )";
        ArrayList<Double> amounts = new ArrayList<>();
        try (Connection db = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, client.getName());
                stmt.setString(2, client.getSurname());

                try (ResultSet rst = stmt.executeQuery()) {
                    while (rst.next()) {
                        double amount = rst.getDouble("shuma");
                        amounts.add(amount);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amounts;
    }

    public static ArrayList<Client> getTransferClients(Client client) {
        String sql = "SELECT emri, mbiemri FROM transferimet WHERE transferuesi IN (SELECT id FROM klientet WHERE emri = ? and mbiemri =? )";
        Client receiver = new Client();
        ArrayList<Client> receivers = new ArrayList<>();
        try (Connection db = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, client.getName());
                stmt.setString(2, client.getSurname());

                try (ResultSet rst = stmt.executeQuery()) {
                    while (rst.next()) {
                        receiver.setName(rst.getString("emri"));
                        receiver.setSurname(rst.getString("mbiemri"));
                        receivers.add(receiver);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receivers;
    }

    public static ArrayList<Double> getTransferAmounts(Client client) {
        String sql = "SELECT shuma FROM transferimet WHERE transferuesi IN (SELECT id FROM klientet WHERE emri = ? and mbiemri =? )";
        ArrayList<Double> amounts = new ArrayList<>();
        try (Connection db = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, client.getName());
                stmt.setString(2, client.getSurname());

                try (ResultSet rst = stmt.executeQuery()) {
                    while (rst.next()) {
                        double amount=rst.getDouble("shuma");
                        amounts.add(amount);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amounts;
    }
}
