package mainApp;

import misc.DataBase;
import states.LoginState;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DataBase db = new DataBase();

        try {
            LoginState loginState = new LoginState();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        //db.SendQuery("SELECT * FROM Doctori");

        db.CloseConnection();
    }
}