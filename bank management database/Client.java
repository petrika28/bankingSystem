import javax.swing.JTextField;

public class Client {
    private String name;
    private String surname;
    private String password;
    private double balance = 0;
    private int id;

    public Client() {
    }

    public Client(String name, String surname, String password) {
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void update(int operation) {
        this.balance = this.balance + operation;
    }

    public double add(double amount) {
        return this.balance = this.balance + amount;
    }

    public double withdraw(double amount) {
        return this.balance = this.balance - amount;
    }







    public void shtoBalance(double vlera, Client client, Client receiver, String veprimi, JTextField transferNameText, JTextField transferoTekstmbiemri) {

        boolean found;

        if (veprimi.equals("shto")) {
            client.add(vlera);
            DbOperations.updateDb(client);
        }

        else if (veprimi.equals("terhiq")) {
            if (client.getBalance() - vlera < 0) {
                Popup mungese = new Popup("Nuk keni mjaftueshem para ne llogari");
            } else {
                client.withdraw(vlera);
                DbOperations.updateDb(client);
            }
        }

        else if (veprimi.equals("transfero")) {
            receiver.setName(transferNameText.getText());
            receiver.setSurname(transferoTekstmbiemri.getText());
            found = DbOperations.checkTransfer(receiver);

            if (found) {
                if (client.getBalance() - vlera < 0) {
                    Popup mungese = new Popup("Nuk keni mjaftueshem para ne llogari");
                } else {
                    if (client.getName().equals(receiver.getName())
                            && client.getSurname().equals(receiver.getSurname())) {
                        Popup transferimVetes = new Popup("Nuk mund t'i transferosh para vetes");
                    } else {
                        client.withdraw(vlera);
                        receiver.add(vlera);
                        receiver.setName(transferNameText.getText());
                        receiver.setSurname(transferoTekstmbiemri.getText());
                        DbOperations.updateDb(client);
                        DbOperations.updateDb(receiver);
                        DbOperations.insertTransferedAmount(client, receiver, vlera);
                    }

                }

            } else {
                Popup nukEkx = new Popup("Klienti nuk ekziston");
            }

        }
    }
}