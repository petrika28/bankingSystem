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
