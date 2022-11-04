package mainApp;

import misc.DataBase;
import states.LoginState;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DataBase db = new DataBase(); // Ne conectam la baza de date

        try {
            LoginState loginState = new LoginState();
            loginState.setSize(300, 100);
            loginState.setVisible(true);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        //db.SendQuery("SELECT * FROM Doctori");

        db.CloseConnection(); // Inchidem conectarea cu baza de date
    }
}