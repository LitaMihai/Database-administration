package mainApp;

import stateDesign.Package;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            JFrame mainFrame = new JFrame();

            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(800, 600);
            mainFrame.setTitle("Login");
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setLayout(null);
            mainFrame.setVisible(true);

            Package pkg = new Package(mainFrame);
            pkg.setPackage(pkg);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}