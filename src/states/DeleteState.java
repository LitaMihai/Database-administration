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

    private boolean isAdmin;

    // Doctors

    // Pills
    final private String pillsOptions[];
    final private JComboBox<String> pillsDropdownList;
    final private JLabel pillsNameLabel;

    // Patients

    // Diseases
    final private String diseasesOptions[];
    final private JComboBox<String> diseasesDropdownList;
    final private JLabel diseasesNameLabel;

    // HealthInsuranceHouses
    final private String healthInsuranceHousesOptions[];
    final private JComboBox<String> healthInsuranceHousesDropdownList;
    final private JLabel healthInsuranceHousesNameLabel;

    DeleteState(JFrame frame, String sqlTable, DataBase dataBase, boolean isAdmin){
        this.frame = frame;
        this.sqlTable = sqlTable;
        this.dataBase = dataBase;

        this.sendButton = new JButton();
        this.backButton = new JButton();

        this.frame.getContentPane().removeAll();
        this.frame.repaint();
        this.frame.setTitle(sqlTable);

        this.title = new JLabel();

        this.isAdmin = isAdmin;

        // Doctors

        // Pills
        int numberOfPills = this.dataBase.getNumberOf("Pills");
        this.pillsOptions = new String[numberOfPills];
        this.dataBase.getObjects("Pills", this.pillsOptions);

        this.pillsDropdownList = new JComboBox<>(this.pillsOptions);
        this.pillsNameLabel = new JLabel();

        // Patients

        // Diseases
        int numberOfDiseases = this.dataBase.getNumberOf("Diseases");
        this.diseasesOptions = new String[numberOfDiseases];
        this.dataBase.getObjects("Diseases", this.diseasesOptions);

        this.diseasesDropdownList = new JComboBox<>(this.diseasesOptions);
        this.diseasesNameLabel = new JLabel();

        // HealthInsuranceHouses
        int numberOfHealthInsuranceHouses = this.dataBase.getNumberOf("HealthInsuranceHouses");
        this.healthInsuranceHousesOptions = new String[numberOfHealthInsuranceHouses];
        this.dataBase.getObjects("HealthInsuranceHouses", this.healthInsuranceHousesOptions);

        this.healthInsuranceHousesDropdownList = new JComboBox<>(this.healthInsuranceHousesOptions);
        this.healthInsuranceHousesNameLabel = new JLabel();

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
            case "Doctors" -> this.title.setText("Delete Doctor");
            case "Pills" -> this.title.setText("Delete Pill");
            case "Patients" -> this.title.setText("Delete Patient");
            case "HealthInsuranceHouses" -> this.title.setText("Delete Health Insurance House");
            case "Diseases" -> this.title.setText("Delete Disease");
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

        // Doctors

        // Pills
        this.pillsNameLabel.setText("Name");
        this.pillsNameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsNameLabel.setForeground(this.textColor);

        this.pillsDropdownList.setBorder(null);
        this.pillsDropdownList.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsDropdownList.setBackground(this.inputColor);
        this.pillsDropdownList.setForeground(this.textColor);
        this.pillsDropdownList.setFocusable(false);

        // Patients

        // Diseases
        this.diseasesNameLabel.setText("Name");
        this.diseasesNameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.diseasesNameLabel.setForeground(this.textColor);

        this.diseasesDropdownList.setBorder(null);
        this.diseasesDropdownList.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.diseasesDropdownList.setBackground(this.inputColor);
        this.diseasesDropdownList.setForeground(this.textColor);
        this.diseasesDropdownList.setFocusable(false);

        // HealthInsuranceHouses
        this.healthInsuranceHousesNameLabel.setText("Name");
        this.healthInsuranceHousesNameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.healthInsuranceHousesNameLabel.setForeground(this.textColor);

        this.healthInsuranceHousesDropdownList.setBorder(null);
        this.healthInsuranceHousesDropdownList.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.healthInsuranceHousesDropdownList.setBackground(this.inputColor);
        this.healthInsuranceHousesDropdownList.setForeground(this.textColor);
        this.healthInsuranceHousesDropdownList.setFocusable(false);
    }

    private void RepositionGUI() {
        this.title.setBounds(this.frame.getWidth() / 2 - (450 / 2), this.frame.getHeight() / 2 - (230), 450, 30);

        switch(this.sqlTable){
            case "Pills" -> {
                this.pillsNameLabel.setBounds(this.title.getX(), this.title.getY() + 160, 250, 30);
                this.pillsDropdownList.setBounds(this.pillsNameLabel.getX() + 170, this.pillsNameLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.pillsNameLabel.getX() - 50, this.pillsNameLabel.getY() + 250, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 356, this.sendButton.getY(), 170, 30);
            }
            case "Diseases" -> {
                this.diseasesNameLabel.setBounds(this.title.getX(), this.title.getY() + 160, 250, 30);
                this.diseasesDropdownList.setBounds(this.diseasesNameLabel.getX() + 170, this.diseasesNameLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.diseasesNameLabel.getX() - 50, this.diseasesNameLabel.getY() + 250, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 356, this.sendButton.getY(), 170, 30);
            }
            case "HealthInsuranceHouses" -> {
                this.healthInsuranceHousesNameLabel.setBounds(this.title.getX(), this.title.getY() + 160, 250, 30);
                this.healthInsuranceHousesDropdownList.setBounds(this.healthInsuranceHousesNameLabel.getX() + 170, this.healthInsuranceHousesNameLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.healthInsuranceHousesNameLabel.getX() - 50, this.healthInsuranceHousesNameLabel.getY() + 250, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 356, this.sendButton.getY(), 170, 30);
            }
        }
    }

    private void AddToPanel() {
        this.frame.add(this.title);
        this.frame.add(this.sendButton);
        this.frame.add(this.backButton);

        switch(this.sqlTable){
            case "Diseases" -> {
                this.frame.add(this.diseasesNameLabel);
                this.frame.add(this.diseasesDropdownList);
            }
            case "HealthInsuranceHouses" -> {
                this.frame.add(this.healthInsuranceHousesNameLabel);
                this.frame.add(this.healthInsuranceHousesDropdownList);
            }
            case "Pills" -> {
                this.frame.add(this.pillsNameLabel);
                this.frame.add(this.pillsDropdownList);
            }
        }
    }

        @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.sendButton){
            switch(this.sqlTable){
                case "Diseases":
                    int done = this.dataBase.sendDelete("DELETE FROM Boli WHERE Nume='" +
                            this.diseasesDropdownList.getItemAt(this.diseasesDropdownList.getSelectedIndex()) + "';");

                    if(done == 1){
                        this.dataBase.sendDelete("DECLARE @max_seed int = ISNULL((SELECT MAX(BoalaID) FROM Boli),0)\n" +
                                "DBCC CHECKIDENT (Boli, RESEED, @max_seed);");
                        this.dataBase.sendQuery("SELECT * FROM Boli", false, false);
                    }

                    this.prev(Package.pkg);
                    break;
                case "HealthInsuranceHouses":
                    done = this.dataBase.sendDelete("DELETE FROM CaseDeSanatate WHERE Nume='" +
                            this.healthInsuranceHousesDropdownList.getItemAt(this.healthInsuranceHousesDropdownList.getSelectedIndex()) + "';");

                    if(done == 1){
                        this.dataBase.sendDelete("DECLARE @max_seed int = ISNULL((SELECT MAX(CasaDeSanatateID) FROM CaseDeSanatate),0)\n" +
                                "DBCC CHECKIDENT (CaseDeSanatate, RESEED, @max_seed);");
                        this.dataBase.sendQuery("SELECT * FROM CaseDeSanatate", false, false);
                    }

                    this.prev(Package.pkg);
                    break;
                case "Pills":
                    done = this.dataBase.sendDelete("DELETE FROM Medicamente WHERE Denumire='" +
                            this.pillsDropdownList.getItemAt(this.pillsDropdownList.getSelectedIndex()) + "';");

                    if(done == 1){
                        this.dataBase.sendDelete("DECLARE @max_seed int = ISNULL((SELECT MAX(MedicamentID) FROM Medicamente),0)\n" +
                                "DBCC CHECKIDENT (Medicamente, RESEED, @max_seed);");
                        this.dataBase.sendQuery("SELECT Medicamente.Denumire, Boli.Nume AS 'Boala Tratata',  Medicamente.ReactiiAdversePosibile " +
                                "FROM Medicamente INNER JOIN Boli ON Medicamente.BoalaID = Boli.BoalaID", false, false);
                    }

                    this.prev(Package.pkg);
                    break;
            }
        }

        else if(e.getSource() == this.backButton){
            this.prev(Package.pkg);
        }
    }

    @Override
    public void next(Package pkg) {
        System.out.println("This is not going anywhere");
    }

    @Override
    public void prev(Package pkg) {
        switch (this.sqlTable) {
            case "Doctors" -> pkg.setState(new DoctorsState(this.frame, this.dataBase, this.isAdmin));
            case "Pills" -> pkg.setState(new PillsState(this.frame, this.dataBase, this.isAdmin));
            case "Patients" -> pkg.setState(new PatientsState(this.frame, this.dataBase, this.isAdmin));
            case "HealthInsuranceHouses" -> pkg.setState(new HealthInsuranceHousesState(this.frame, this.dataBase, this.isAdmin));
            case "Diseases" -> pkg.setState(new DiseasesState(this.frame, this.dataBase, this.isAdmin));
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
