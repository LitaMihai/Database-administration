package mainApp;

import stateDesign.Package;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            Package pkg = new Package();
            pkg.setPackage(pkg);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        // db.SendQuery("SELECT * FROM Doctori");
        //db.CloseConnection();
    }
}