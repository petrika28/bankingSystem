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

    public void update(double operation) {
        this.balance = this.balance + operation;
    }

    public double add(double amount) {
        return this.balance = this.balance + amount;
    }

    public double withdraw(double amount) {
        return this.balance = this.balance - amount;
    }

}