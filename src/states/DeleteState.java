package states;

import Database.DataBase;
import stateDesign.Package;
import stateDesign.PackageState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DeleteState implements PackageState, ActionListener {

    final private Color backgroundColor = new Color(0, 10, 20);
    final private Color textColor = new Color(237, 242, 244);
    final private Color inputColor = new Color(0, 150, 170);
    final private DataBase dataBase;
    final private JFrame frame;
    final private String sqlTable;
    final private JButton sendButton, backButton;
    final private JLabel title;

    DeleteState(JFrame frame, String sqlTable, DataBase dataBase){
        this.frame = frame;
        this.sqlTable = sqlTable;
        this.dataBase = dataBase;

        this.sendButton = new JButton();
        this.backButton = new JButton();

        this.frame.getContentPane().removeAll();
        this.frame.repaint();
        this.frame.setTitle(sqlTable);

        this.title = new JLabel();



        InitVariables();
        AddToPanel();

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                RepositionGUI();
            }
        });
    }

    private void InitVariables() {
        RepositionGUI();

        this.frame.getContentPane().setBackground(this.backgroundColor);

        switch (this.sqlTable) {
            case "Doctors" -> this.title.setText("Insert Doctor");
            case "Pills" -> this.title.setText("Insert Pill");
            case "Patients" -> this.title.setText("Insert Patient");
            case "HealthInsuranceHouses" -> this.title.setText("Insert Health Insurance House");
            case "Diseases" -> this.title.setText("Insert Disease");
        }

        this.title.setFont(new Font("Poppins Medium", Font.BOLD, 30));
        this.title.setForeground(this.textColor);
        this.title.setHorizontalAlignment(SwingConstants.CENTER);

        this.sendButton.setText("Send");
        this.sendButton.addActionListener(this);
        this.sendButton.setFont(new Font("Poppins Medium", Font.BOLD, 20));
        this.sendButton.setFocusable(false);
        this.sendButton.setForeground(this.textColor);
        this.sendButton.setBackground(this.inputColor);

        this.backButton.setText("Back");
        this.backButton.addActionListener(this);
        this.backButton.setFont(new Font("Poppins Medium", Font.BOLD, 20));
        this.backButton.setFocusable(false);
        this.backButton.setForeground(this.textColor);
        this.backButton.setBackground(this.inputColor);
    }

    private void RepositionGUI() {
        this.title.setBounds(this.frame.getWidth() / 2 - (450 / 2), this.frame.getHeight() / 2 - (230), 450, 30);
    }

    private void AddToPanel() {
        this.frame.add(this.title);
        this.frame.add(this.sendButton);
        this.frame.add(this.backButton);
    }

        @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void next(Package pkg) {
        System.out.println("This is not going anywhere");
    }

    @Override
    public void prev(Package pkg) {
        switch (this.sqlTable) {
            case "Doctors" -> pkg.setState(new DoctorsState(this.frame, this.dataBase));
            case "Pills" -> pkg.setState(new PillsState(this.frame, this.dataBase));
            case "Patients" -> pkg.setState(new PatientsState(this.frame, this.dataBase));
            case "HealthInsuranceHouses" -> pkg.setState(new HealthInsuranceHousesState(this.frame, this.dataBase));
            case "Diseases" -> pkg.setState(new DiseasesState(this.frame, this.dataBase));
        }
    }

    @Override
    public void printStatus() {
        switch (this.sqlTable){
            case "Doctors" -> System.out.println("Delete Doctors State");
            case "Pills" -> System.out.println("Delete Pill State");
            case "Patients" -> System.out.println("Delete Patient State");
            case "HealthInsuranceHouses" -> System.out.println("Delete Health Insurance House State");
            case "Diseases" -> System.out.println("Delete Disease State");
        }
    }
}
