import javax.swing.JTextField;

public class ActionService {

    public static boolean checkForNegativeValue(double vlera) {
        if (vlera<0)
            return true;

        return false;
    }
    public static boolean checkForNegativeOperation(Client client, double vlera) {
        if ((client.getBalance() - vlera)< 0)
            return true;

        return false;
    }

    public static void depozito(Client client, Double vlera) {
        client.add(vlera);
        DbOperations.updateDb(client);

    }

    public static void terhiq(Client client, Double vlera) {
        client.withdraw(vlera);
        DbOperations.updateDb(client);
    }

    public static void transfero(Client client, Client receiver, Double vlera,
            JTextField transferNameText, JTextField transferoTekstmbiemri) {
                
        receiver.setName(transferNameText.getText());
        receiver.setSurname(transferoTekstmbiemri.getText());
        client.withdraw(vlera);
        receiver.add(vlera);
        receiver.setName(transferNameText.getText());
        receiver.setSurname(transferoTekstmbiemri.getText());
        DbOperations.updateDb(client);
        DbOperations.updateDb(receiver);
        DbOperations.insertTransferedAmount(client, receiver, vlera);

    }

}
