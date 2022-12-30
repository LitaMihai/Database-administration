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
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateState implements PackageState, ActionListener {
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
    final private JLabel doctorNameLabel, doctorSpecialityLabel;
    final private JComboBox<String> doctorsDropdownList;
    final private JTextField doctorSpecialityInput;

    // Pills
    final private JLabel pillsNameLabel, pillsDiseaseLabel, pillsSideEffectsLabel, pillFromDatabaseLabel;
    final private JTextField  pillsSideEffectsInput, pillsNameInput;
    final private String pillsOptions[], diseasesOptions[], doctorsOptions[];
    final private JComboBox<String> pillsDiseaseDropdownList;
    final private JComboBox<String>  pillsDropdownList;
    private String doctorNameAuxiliaryVariable, doctorSurnameAuxiliaryVariable;

    UpdateState(JFrame frame, String sqlTable, DataBase dataBase, boolean isAdmin){
        this.frame = frame;
        this.sqlTable = sqlTable;
        this.dataBase = dataBase;

        this.sendButton = new JButton();
        this.backButton = new JButton();

        this.frame.getContentPane().removeAll();
        this.frame.repaint();

        this.title = new JLabel();

        this.isAdmin = isAdmin;

        // Doctors
        int numberOfDoctors = this.dataBase.getNumberOf("Doctors");
        this.doctorsOptions = new String[numberOfDoctors];
        this.dataBase.getObjects("Doctors", this.doctorsOptions);

        this.doctorNameLabel = new JLabel();
        this.doctorsDropdownList = new JComboBox<>(doctorsOptions);
        this.doctorSpecialityLabel = new JLabel();
        this.doctorSpecialityInput = new JTextField();

        // Pills
        int numberOfPills = this.dataBase.getNumberOf("Pills");
        this.pillsOptions = new String[numberOfPills];
        this.dataBase.getObjects("Pills", this.pillsOptions);

        int numberOfDiseases = this.dataBase.getNumberOf("Diseases");
        this.diseasesOptions = new String[numberOfDiseases];
        this.dataBase.getObjects("Diseases", this.diseasesOptions);

        this.pillFromDatabaseLabel = new JLabel();
        this.pillsDropdownList = new JComboBox<>(this.pillsOptions);
        this.pillsNameLabel = new JLabel();
        this.pillsNameInput = new JTextField();
        this.pillsDiseaseLabel = new JLabel();
        this.pillsDiseaseDropdownList = new JComboBox<>(this.diseasesOptions);
        this.pillsSideEffectsLabel = new JLabel();
        this.pillsSideEffectsInput = new JTextField();

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
            case "Doctors" -> {
                this.title.setText("Update Doctor");
                this.frame.setTitle("Update Doctor");
            }
            case "Pills" -> {
                this.title.setText("Update Pill");
                this.frame.setTitle("Update Pill");
            }
            case "Patients" -> {
                this.title.setText("Update Patient");
                this.frame.setTitle("Update Patient");
            }
            case "HealthInsuranceHouses" -> {
                this.title.setText("Update Health Insurance House");
                this.frame.setTitle("Update Health Insurance House");
            }
            case "Diseases" -> {
                this.title.setText("Update Disease");
                this.frame.setTitle("Update Disease");
            }
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
        this.doctorNameLabel.setText("Select Doctor");
        this.doctorNameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.doctorNameLabel.setForeground(this.textColor);

        this.doctorsDropdownList.setBorder(null);
        this.doctorsDropdownList.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.doctorsDropdownList.setBackground(this.inputColor);
        this.doctorsDropdownList.setForeground(this.textColor);
        this.doctorsDropdownList.setFocusable(false);

        this.doctorSpecialityLabel.setText("Speciality");
        this.doctorSpecialityLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.doctorSpecialityLabel.setForeground(this.textColor);

        this.doctorSpecialityInput.setBorder(null);
        this.doctorSpecialityInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.doctorSpecialityInput.setBackground(this.inputColor);
        this.doctorSpecialityInput.setForeground(this.textColor);

        // Pills
        this.pillFromDatabaseLabel.setText("Select Pill");
        this.pillFromDatabaseLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillFromDatabaseLabel.setForeground(this.textColor);

        this.pillsDropdownList.setBorder(null);
        this.pillsDropdownList.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsDropdownList.setBackground(this.inputColor);
        this.pillsDropdownList.setForeground(this.textColor);
        this.pillsDropdownList.setFocusable(false);

        this.pillsNameLabel.setText("Name");
        this.pillsNameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsNameLabel.setForeground(this.textColor);

        this.pillsNameInput.setBorder(null);
        this.pillsNameInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsNameInput.setBackground(this.inputColor);
        this.pillsNameInput.setForeground(this.textColor);

        this.pillsDiseaseLabel.setText("Treated Disease");
        this.pillsDiseaseLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsDiseaseLabel.setForeground(this.textColor);

        this.pillsDiseaseDropdownList.setBorder(null);
        this.pillsDiseaseDropdownList.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsDiseaseDropdownList.setBackground(this.inputColor);
        this.pillsDiseaseDropdownList.setForeground(this.textColor);
        this.pillsDiseaseDropdownList.setFocusable(false);

        this.pillsSideEffectsLabel.setText("Side Effects");
        this.pillsSideEffectsLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsSideEffectsLabel.setForeground(this.textColor);

        this.pillsSideEffectsInput.setBorder(null);
        this.pillsSideEffectsInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsSideEffectsInput.setBackground(this.inputColor);
        this.pillsSideEffectsInput.setForeground(this.textColor);

        pillsDropdownList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPill = pillsDropdownList.getItemAt(pillsDropdownList.getSelectedIndex()).toString();
                try{
                    ResultSet resultSet = dataBase.getResultSetFromTable("Pills", selectedPill, null);
                    while(resultSet.next()){
                        pillsNameInput.setText(resultSet.getString(3));
                        pillsDiseaseDropdownList.setSelectedIndex(resultSet.getInt(2) - 1);
                        pillsSideEffectsInput.setText(resultSet.getString(4));
                    }
                }
                catch(SQLException sqlException){
                    throw new RuntimeException(sqlException);
                }
            }
        });

        doctorsDropdownList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDoctor = doctorsDropdownList.getItemAt(doctorsDropdownList.getSelectedIndex());
                doctorNameAuxiliaryVariable = selectedDoctor.substring(0, selectedDoctor.indexOf(" "));
                doctorSurnameAuxiliaryVariable = selectedDoctor.substring(selectedDoctor.indexOf(" ") + 1);
                try{
                    ResultSet resultSet = dataBase.getResultSetFromTable("Doctors", doctorNameAuxiliaryVariable, doctorSurnameAuxiliaryVariable);
                    while(resultSet.next()){
                        doctorSpecialityInput.setText(resultSet.getString(4));
                    }
                }
                catch(SQLException sqlException){
                    throw new RuntimeException(sqlException);
                }
            }
        });
    }

    private void RepositionGUI() {
        this.title.setBounds(this.frame.getWidth() / 2 - (450 / 2), this.frame.getHeight() / 2 - (230), 450, 50);

        switch(this.sqlTable){
            case "Doctors" -> {
                this.doctorNameLabel.setBounds(this.title.getX() - 50, this.title.getY() + 130, 200, 30);
                this.doctorsDropdownList.setBounds(this.doctorNameLabel.getX() + 200, this.doctorNameLabel.getY(), 295, 30);
                this.doctorSpecialityLabel.setBounds(this.doctorNameLabel.getX(), this.doctorNameLabel.getY() + 57, 200, 30);
                this.doctorSpecialityInput.setBounds(this.doctorSpecialityLabel.getX() + 200, this.doctorSpecialityLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.doctorSpecialityLabel.getX(), this.doctorSpecialityLabel.getY() + 150, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 326, this.sendButton.getY(), 170, 30);
            }
            case "Pills" -> {
                this.pillFromDatabaseLabel.setBounds(this.title.getX() - 50, this.title.getY() + 130, 250, 30);
                this.pillsDropdownList.setBounds(this.pillFromDatabaseLabel.getX() + 230, this.pillFromDatabaseLabel.getY(), 295, 30);
                this.pillsNameLabel.setBounds(this.pillFromDatabaseLabel.getX(), this.pillFromDatabaseLabel.getY() + 100, 250, 30);
                this.pillsNameInput.setBounds(this.pillsNameLabel.getX() + 230, this.pillsNameLabel.getY(), 295, 30);
                this.pillsDiseaseLabel.setBounds(this.pillsNameLabel.getX(), this.pillsNameLabel.getY() + 57, 250, 30);
                this.pillsDiseaseDropdownList.setBounds(this.pillsDiseaseLabel.getX() + 230, this.pillsDiseaseLabel.getY(), 295, 30);
                this.pillsSideEffectsLabel.setBounds(this.pillsDiseaseLabel.getX(), this.pillsDiseaseLabel.getY() + 57, 250, 30);
                this.pillsSideEffectsInput.setBounds(this.pillsSideEffectsLabel.getX() + 230, this.pillsSideEffectsLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.pillsSideEffectsLabel.getX(), this.pillsSideEffectsLabel.getY() + 150, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 356, this.sendButton.getY(), 170, 30);
            }
        }
    }

    private void AddToPanel() {
        this.frame.add(this.title);
        this.frame.add(this.sendButton);
        this.frame.add(this.backButton);

        switch (this.sqlTable){
            case "Doctors" -> {
                this.frame.add(this.doctorNameLabel);
                this.frame.add(this.doctorsDropdownList);
                this.frame.add(this.doctorSpecialityLabel);
                this.frame.add(this.doctorSpecialityInput);
            }
            case "Pills" -> {
                this.frame.add(this.pillFromDatabaseLabel);
                this.frame.add(this.pillsDropdownList);
                this.frame.add(this.pillsNameLabel);
                this.frame.add(this.pillsNameInput);
                this.frame.add(this.pillsDiseaseLabel);
                this.frame.add(this.pillsDiseaseDropdownList);
                this.frame.add(this.pillsSideEffectsLabel);
                this.frame.add(this.pillsSideEffectsInput);
            }
        }
    }

    private boolean areEmptyButtons(String table){
        switch (table){
            case "Pills": return this.pillsNameInput.getText().equals("") && this.pillsSideEffectsInput.getText().equals("");
            case "Doctors": return this.doctorSpecialityInput.getText().equals("");
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.sendButton) {
            switch (this.sqlTable) {
                case "Pills" -> {
                    if (!areEmptyButtons("Pills")) {
                        int done = this.dataBase.sendUpdate("UPDATE Medicamente SET BoalaID=" + (this.pillsDiseaseDropdownList.getSelectedIndex() + 1) +
                                ", Denumire='" + this.pillsNameInput.getText() + "'" +
                                ", ReactiiAdversePosibile='" + this.pillsSideEffectsInput.getText() + "'" +
                                " WHERE Denumire='" + this.pillsDropdownList.getItemAt(this.pillsDropdownList.getSelectedIndex()) + "'");

                        if (done == 1) {
                            this.dataBase.sendQuery("SELECT Medicamente.Denumire, Boli.Nume AS 'Boala Tratata',  Medicamente.ReactiiAdversePosibile " +
                                    "FROM Medicamente INNER JOIN Boli ON Medicamente.BoalaID = Boli.BoalaID", false, false);
                            this.prev(Package.pkg);
                        }
                    }
                }
                case "Doctors" -> {
                    if(!areEmptyButtons("Doctors")) {
                        int done = this.dataBase.sendUpdate("UPDATE Doctori SET Specializare='" + this.doctorSpecialityInput.getText() + "'" +
                                " WHERE Nume='" + this.doctorNameAuxiliaryVariable + "'" +
                                " AND Prenume='" + this.doctorSurnameAuxiliaryVariable + "'");
                        if(done == 1){
                            this.dataBase.sendQuery("SELECT * FROM Doctori", false, false);
                            this.prev(Package.pkg);
                        }
                    }
                }
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
            case "Doctors" -> System.out.println("Update Doctors State");
            case "Pills" -> System.out.println("Update Pill State");
            case "Patients" -> System.out.println("Update Patient State");
            case "HealthInsuranceHouses" -> System.out.println("Update Health Insurance House State");
            case "Diseases" -> System.out.println("Update Disease State");
        }
    }
}
