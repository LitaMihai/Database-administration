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
import com.toedter.calendar.JCalendar;

public class InsertState implements PackageState, ActionListener {

    final private Color backgroundColor = new Color(0, 10, 20);
    final private Color textColor = new Color(237, 242, 244);
    final private Color inputColor = new Color(0, 150, 170);
    final private DataBase dataBase;
    final private JFrame frame;
    final private String sqlTable;
    final private JButton sendButton, backButton;
    final private JLabel title;
    private boolean show;

    // Doctors
    final private JLabel doctorsNameLabel, doctorsSurnameLabel, doctorsSpecialityLabel;
    final private JTextField doctorsNameInput, doctorsSurnameInput, doctorsSpecialityInput;

    // Pills
    final private JLabel pillsNameLabel, pillsDiseaseTreatedLabel, pillsSideEffectsLabel;
    final private JTextField pillsNameInput, pillsSideEffectsInput;
    final private JComboBox<String> pillsDiseaseTreatedDropdownList;

    // Patients
    final private JLabel patientNameLabel, patientSurnameLabel, patientPersonalIdentificationNumberLabel, patientStreetLabel;
    final private JLabel patientCityLabel, patientCountyLabel, patientBirthDateLabel, patientHealthInsuranceHouseLabel, patientTestedPillLabel;
    final private JLabel patientStreetNumberLabel, patientSexLabel;
    final private JTextField patientNameInput, patientSurnameInput, patientPersonalIdentificationNumberInput, patientStreetInput;
    final private JTextField patientCityInput, patientCountyInput, patientBirthDateInput;

    final private JComboBox<String>  patientTestedPillDropdownList, patientHealthInsuranceHouseDropdownList;
    final private String testedPillsOptions[];
    final private String healthInsuranceHouseOptions[];
    final private JTextField patientStreetNumberInput;
    final private JButton pickDate;
    final private JCalendar calendar;
    private String patientBirthDateSQL;
    final private JComboBox<String> sexDropdownList;
    final private String sexOptions[] = {"M", "F"};
    final private String diseasesOptions[];

    // Diseases
    final private JLabel diseasesNameLabel;
    final private JTextField diseasesNameInput;

    // HealthInsuranceHouses
    final private JLabel healthInsuranceHousesNameLabel;
    final private JTextField healthInsuranceHousesNameInput;

    public InsertState(JFrame frame, String sqlTable, DataBase dataBase){
        this.frame = frame;
        this.sqlTable = sqlTable;
        this.dataBase = dataBase;

        this.sendButton = new JButton();
        this.backButton = new JButton();

        this.frame.getContentPane().removeAll();
        this.frame.repaint();
        this.frame.setTitle(sqlTable);

        this.title = new JLabel();

        this.show = false;

        // Doctors
        this.doctorsNameLabel = new JLabel();
        this.doctorsSurnameLabel = new JLabel();
        this.doctorsSpecialityLabel = new JLabel();
        this.doctorsNameInput = new JTextField();
        this.doctorsSurnameInput = new JTextField();
        this.doctorsSpecialityInput = new JTextField();

        // Pills
        int numberOfDiseases = this.dataBase.getNumberOf("Diseases");
        this.diseasesOptions = new String[numberOfDiseases];
        this.dataBase.getObjects("Diseases", this.diseasesOptions);

        this.pillsNameLabel = new JLabel();
        this.pillsDiseaseTreatedLabel = new JLabel();
        this.pillsSideEffectsLabel = new JLabel();
        this.pillsNameInput = new JTextField();
        this.pillsDiseaseTreatedDropdownList = new JComboBox<>(this.diseasesOptions);
        this.pillsSideEffectsInput = new JTextField();

        // Patients
        int numberOfPills = this.dataBase.getNumberOf("Pills");
        this.testedPillsOptions = new String[numberOfPills];
        this.dataBase.getObjects("Pills", this.testedPillsOptions);

        int numberOfHealthInsuranceHouses = this.dataBase.getNumberOf("Health Insurance Houses");
        this.healthInsuranceHouseOptions = new String[numberOfHealthInsuranceHouses];
        this.dataBase.getObjects("Health Insurance Houses", this.healthInsuranceHouseOptions);

        this.patientNameLabel = new JLabel();
        this.patientSurnameLabel = new JLabel();
        this.patientPersonalIdentificationNumberLabel = new JLabel();
        this.patientStreetLabel = new JLabel();
        this.patientCityLabel = new JLabel();
        this.patientCountyLabel = new JLabel();
        this.patientBirthDateLabel = new JLabel();
        this.patientHealthInsuranceHouseLabel = new JLabel();
        this.patientTestedPillLabel = new JLabel();
        this.patientStreetNumberLabel = new JLabel();
        this.patientSexLabel = new JLabel();
        this.patientNameInput = new JTextField();
        this.patientSurnameInput = new JTextField();
        this.patientPersonalIdentificationNumberInput = new JTextField();
        this.patientStreetInput = new JTextField();
        this.patientCityInput = new JTextField();
        this.patientCountyInput = new JTextField();
        this.patientBirthDateInput = new JTextField();
        this.patientHealthInsuranceHouseDropdownList = new JComboBox<>(healthInsuranceHouseOptions);
        this.patientTestedPillDropdownList = new JComboBox<>(testedPillsOptions);
        this.patientStreetNumberInput = new JTextField();
        this.pickDate = new JButton();
        this.calendar = new JCalendar();
        this.patientBirthDateSQL = "";
        this.sexDropdownList = new JComboBox<>(sexOptions);

        // Diseases
        this.diseasesNameLabel = new JLabel();
        this.diseasesNameInput = new JTextField();

        // HealthInsuranceHouses
        this.healthInsuranceHousesNameLabel = new JLabel();
        this.healthInsuranceHousesNameInput = new JTextField();

        InitVariables();
        AddToPanel();

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                RepositionGUI();
            }
        });
    }

    private void InitVariables(){
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

        // Doctors
        this.doctorsNameLabel.setText("Nume");
        this.doctorsNameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.doctorsNameLabel.setForeground(this.textColor);

        this.doctorsNameInput.setBorder(null);
        this.doctorsNameInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.doctorsNameInput.setBackground(this.inputColor);
        this.doctorsNameInput.setForeground(this.textColor);

        this.doctorsSurnameLabel.setText("Prenume");
        this.doctorsSurnameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.doctorsSurnameLabel.setForeground(this.textColor);

        this.doctorsSurnameInput.setBorder(null);
        this.doctorsSurnameInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.doctorsSurnameInput.setBackground(this.inputColor);
        this.doctorsSurnameInput.setForeground(this.textColor);

        this.doctorsSpecialityLabel.setText("Specialitate");
        this.doctorsSpecialityLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.doctorsSpecialityLabel.setForeground(this.textColor);

        this.doctorsSpecialityInput.setBorder(null);
        this.doctorsSpecialityInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.doctorsSpecialityInput.setBackground(this.inputColor);
        this.doctorsSpecialityInput.setForeground(this.textColor);

        // Pills
        this.pillsNameLabel.setText("Denumire");
        this.pillsNameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsNameLabel.setForeground(this.textColor);

        this.pillsNameInput.setBorder(null);
        this.pillsNameInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsNameInput.setBackground(this.inputColor);
        this.pillsNameInput.setForeground(this.textColor);

        this.pillsDiseaseTreatedLabel.setText("Boala Tratata");
        this.pillsDiseaseTreatedLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsDiseaseTreatedLabel.setForeground(this.textColor);

        this.pillsDiseaseTreatedDropdownList.setBorder(null);
        this.pillsDiseaseTreatedDropdownList.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsDiseaseTreatedDropdownList.setBackground(this.inputColor);
        this.pillsDiseaseTreatedDropdownList.setForeground(this.textColor);
        this.pillsDiseaseTreatedDropdownList.setFocusable(false);

        this.pillsSideEffectsLabel.setText("Reactii Adverse Posibile");
        this.pillsSideEffectsLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsSideEffectsLabel.setForeground(this.textColor);

        this.pillsSideEffectsInput.setBorder(null);
        this.pillsSideEffectsInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsSideEffectsInput.setBackground(this.inputColor);
        this.pillsSideEffectsInput.setForeground(this.textColor);

        // Patients
        this.patientNameLabel.setText("Nume");
        this.patientNameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientNameLabel.setForeground(this.textColor);

        this.patientNameInput.setBorder(null);
        this.patientNameInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientNameInput.setBackground(this.inputColor);
        this.patientNameInput.setForeground(this.textColor);

        this.patientSurnameLabel.setText("Prenume");
        this.patientSurnameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientSurnameLabel.setForeground(this.textColor);

        this.patientSurnameInput.setBorder(null);
        this.patientSurnameInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientSurnameInput.setBackground(this.inputColor);
        this.patientSurnameInput.setForeground(this.textColor);

        this.patientPersonalIdentificationNumberLabel.setText("CNP");
        this.patientPersonalIdentificationNumberLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientPersonalIdentificationNumberLabel.setForeground(this.textColor);

        this.patientPersonalIdentificationNumberInput.setBorder(null);
        this.patientPersonalIdentificationNumberInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientPersonalIdentificationNumberInput.setBackground(this.inputColor);
        this.patientPersonalIdentificationNumberInput.setForeground(this.textColor);

        this.patientStreetLabel.setText("Strada");
        this.patientStreetLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientStreetLabel.setForeground(this.textColor);

        this.patientStreetInput.setBorder(null);
        this.patientStreetInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientStreetInput.setBackground(this.inputColor);
        this.patientStreetInput.setForeground(this.textColor);

        this.patientCityLabel.setText("Oras");
        this.patientCityLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientCityLabel.setForeground(this.textColor);

        this.patientCityInput.setBorder(null);
        this.patientCityInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientCityInput.setBackground(this.inputColor);
        this.patientCityInput.setForeground(this.textColor);

        this.patientCountyLabel.setText("Judet");
        this.patientCountyLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientCountyLabel.setForeground(this.textColor);

        this.patientCountyInput.setBorder(null);
        this.patientCountyInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientCountyInput.setBackground(this.inputColor);
        this.patientCountyInput.setForeground(this.textColor);

        this.patientBirthDateLabel.setText("Data Nasterii");
        this.patientBirthDateLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientBirthDateLabel.setForeground(this.textColor);

        this.patientBirthDateInput.setBorder(null);
        this.patientBirthDateInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientBirthDateInput.setBackground(this.inputColor);
        this.patientBirthDateInput.setForeground(this.textColor);

        this.patientHealthInsuranceHouseLabel.setText("Casa Sanatate");
        this.patientHealthInsuranceHouseLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientHealthInsuranceHouseLabel.setForeground(this.textColor);

        this.patientHealthInsuranceHouseDropdownList.setBorder(null);
        this.patientHealthInsuranceHouseDropdownList.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientHealthInsuranceHouseDropdownList.setBackground(this.inputColor);
        this.patientHealthInsuranceHouseDropdownList.setForeground(this.textColor);
        this.patientHealthInsuranceHouseDropdownList.setFocusable(false);

        this.patientTestedPillLabel.setText("Medicament Testat");
        this.patientTestedPillLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientTestedPillLabel.setForeground(this.textColor);

        this.patientTestedPillDropdownList.setBorder(null);
        this.patientTestedPillDropdownList.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientTestedPillDropdownList.setBackground(this.inputColor);
        this.patientTestedPillDropdownList.setForeground(this.textColor);
        this.patientTestedPillDropdownList.setFocusable(false);

        this.patientStreetNumberLabel.setText("Nr");
        this.patientStreetNumberLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientStreetNumberLabel.setForeground(this.textColor);

        this.patientStreetNumberInput.setBorder(null);
        this.patientStreetNumberInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientStreetNumberInput.setBackground(this.inputColor);
        this.patientStreetNumberInput.setForeground(this.textColor);

        this.patientSexLabel.setText("Sex");
        this.patientSexLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.patientSexLabel.setForeground(this.textColor);

        this.sexDropdownList.setBorder(null);
        this.sexDropdownList.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.sexDropdownList.setBackground(this.inputColor);
        this.sexDropdownList.setForeground(this.textColor);
        this.sexDropdownList.setFocusable(false);

        this.pickDate.setText("Date");
        this.pickDate.addActionListener(this);
        this.pickDate.setFont(new Font("Poppins Medium", Font.BOLD, 15));
        this.pickDate.setFocusable(false);
        this.pickDate.setForeground(this.textColor);
        this.pickDate.setBackground(this.inputColor);

        this.calendar.setVisible(false);
        this.calendar.getDayChooser().addPropertyChangeListener("day", e ->{
            String year = Integer.toString(this.calendar.getYearChooser().getYear());
            String month = Integer.toString(this.calendar.getMonthChooser().getMonth() + 1);
            String day = Integer.toString(this.calendar.getDayChooser().getDay());

            this.patientBirthDateInput.setText(
                year + "." +
                month + "." +
                day
            );

            this.patientBirthDateSQL = year + month + day;
        });

        // HealthInsuranceHouses
        this.healthInsuranceHousesNameLabel.setText("Nume");
        this.healthInsuranceHousesNameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.healthInsuranceHousesNameLabel.setForeground(this.textColor);

        this.healthInsuranceHousesNameInput.setBorder(null);
        this.healthInsuranceHousesNameInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.healthInsuranceHousesNameInput.setBackground(this.inputColor);
        this.healthInsuranceHousesNameInput.setForeground(this.textColor);

        // Diseases
        this.diseasesNameLabel.setText("Nume");
        this.diseasesNameLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.diseasesNameLabel.setForeground(this.textColor);

        this.diseasesNameInput.setBorder(null);
        this.diseasesNameInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.diseasesNameInput.setBackground(this.inputColor);
        this.diseasesNameInput.setForeground(this.textColor);
    }

    private void RepositionGUI(){
        this.title.setBounds(this.frame.getWidth() / 2 - (450 / 2), this.frame.getHeight() / 2 - (230), 450, 30);

        switch (this.sqlTable) {
            case "Doctors" -> {
                this.doctorsNameLabel.setBounds(this.title.getX() - 50, this.title.getY() + 130, 200, 30);
                this.doctorsNameInput.setBounds(this.doctorsNameLabel.getX() + 200, this.doctorsNameLabel.getY(), 295, 30);
                this.doctorsSurnameLabel.setBounds(this.doctorsNameLabel.getX(), this.doctorsNameLabel.getY() + 57, 200, 30);
                this.doctorsSurnameInput.setBounds(this.doctorsSurnameLabel.getX() + 200, this.doctorsSurnameLabel.getY(), 295, 30);
                this.doctorsSpecialityLabel.setBounds(this.doctorsSurnameLabel.getX(), this.doctorsSurnameLabel.getY() + 57, 200, 30);
                this.doctorsSpecialityInput.setBounds(this.doctorsSpecialityLabel.getX() + 200, this.doctorsSpecialityLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.doctorsSpecialityLabel.getX(), this.doctorsSpecialityLabel.getY() + 150, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 326, this.sendButton.getY(), 170, 30);
            }
            case "Pills" -> {
                this.pillsNameLabel.setBounds(this.title.getX() - 50, this.title.getY() + 130, 250, 30);
                this.pillsNameInput.setBounds(this.pillsNameLabel.getX() + 230, this.pillsNameLabel.getY(), 295, 30);
                this.pillsDiseaseTreatedLabel.setBounds(this.pillsNameLabel.getX(), this.pillsNameLabel.getY() + 57, 250, 30);
                this.pillsDiseaseTreatedDropdownList.setBounds(this.pillsDiseaseTreatedLabel.getX() + 230, this.pillsDiseaseTreatedLabel.getY(), 295, 30);
                this.pillsSideEffectsLabel.setBounds(this.pillsDiseaseTreatedLabel.getX(), this.pillsDiseaseTreatedLabel.getY() + 57, 250, 30);
                this.pillsSideEffectsInput.setBounds(this.pillsSideEffectsLabel.getX() + 230, this.pillsSideEffectsLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.pillsSideEffectsLabel.getX(), this.pillsSideEffectsLabel.getY() + 150, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 356, this.sendButton.getY(), 170, 30);
            }
            case "Patients" -> {
                this.title.setBounds(this.frame.getWidth() / 2 - (450 / 2), this.frame.getHeight() / 2 - (280), 450, 30);
                this.patientNameLabel.setBounds(this.title.getX() - 120, this.title.getY() + 54, 200, 30);
                this.patientNameInput.setBounds(this.patientNameLabel.getX() + 200, this.patientNameLabel.getY(), 300, 30);
                this.patientSurnameLabel.setBounds(this.patientNameLabel.getX(), this.patientNameLabel.getY() + 44, 200, 30);
                this.patientSurnameInput.setBounds(this.patientSurnameLabel.getX() + 200, this.patientSurnameLabel.getY(), 300, 30);
                this.patientPersonalIdentificationNumberLabel.setBounds(this.patientSurnameLabel.getX(), this.patientSurnameLabel.getY() + 44, 200, 30);
                this.patientPersonalIdentificationNumberInput.setBounds(this.patientPersonalIdentificationNumberLabel.getX() + 200, this.patientPersonalIdentificationNumberLabel.getY(), 300, 30);
                this.patientStreetLabel.setBounds(this.patientPersonalIdentificationNumberLabel.getX(), this.patientPersonalIdentificationNumberLabel.getY() + 44, 200, 30);
                this.patientStreetInput.setBounds(this.patientStreetLabel.getX() + 200, this.patientStreetLabel.getY(), 300, 30);
                this.patientCityLabel.setBounds(this.patientStreetLabel.getX(), this.patientStreetLabel.getY() + 44, 200, 30);
                this.patientCityInput.setBounds(this.patientCityLabel.getX() + 200, this.patientCityLabel.getY(), 300, 30);
                this.patientCountyLabel.setBounds(this.patientCityLabel.getX(), this.patientCityLabel.getY() + 44, 200, 30);
                this.patientCountyInput.setBounds(this.patientCountyLabel.getX() + 200, this.patientCountyLabel.getY(), 300, 30);
                this.patientHealthInsuranceHouseLabel.setBounds(this.patientCountyLabel.getX(), this.patientCountyLabel.getY() + 44, 200, 30);
                this.patientHealthInsuranceHouseDropdownList.setBounds(this.patientHealthInsuranceHouseLabel.getX() + 200, this.patientHealthInsuranceHouseLabel.getY(), 300, 30);
                this.patientBirthDateLabel.setBounds(this.patientHealthInsuranceHouseLabel.getX(), this.patientHealthInsuranceHouseLabel.getY() + 44, 200, 30);
                this.patientBirthDateInput.setBounds(this.patientBirthDateLabel.getX() + 200, this.patientBirthDateLabel.getY(), 300, 30);
                this.pickDate.setBounds(this.patientBirthDateInput.getX() + 340, this.patientBirthDateInput.getY(), 70, 30);
                this.calendar.setBounds(this.pickDate.getX() + 80, this.pickDate.getY(), 300, 160);
                this.patientTestedPillLabel.setBounds(this.patientBirthDateLabel.getX(), this.patientBirthDateLabel.getY() + 44, 200, 30);
                this.patientTestedPillDropdownList.setBounds(this.patientTestedPillLabel.getX() + 200, this.patientTestedPillLabel.getY(), 300, 30);
                this.patientStreetNumberLabel.setBounds(this.patientStreetInput.getX() + 334, this.patientStreetInput.getY(), 50, 30);
                this.patientStreetNumberInput.setBounds(this.patientStreetNumberLabel.getX() + 35, this.patientStreetNumberLabel.getY(), 50, 30);
                this.patientSexLabel.setBounds(this.patientCountyInput.getX() + 334, this.patientCountyInput.getY(), 50, 30);
                this.sexDropdownList.setBounds(this.patientSexLabel.getX() + 50, this.patientSexLabel.getY(), 50, 30);
                this.sendButton.setBounds(this.patientTestedPillLabel.getX() + 80, this.patientTestedPillLabel.getY() + 70, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 356, this.sendButton.getY(), 170, 30);
            }
            case "HealthInsuranceHouses" -> {
                this.healthInsuranceHousesNameLabel.setBounds(this.title.getX(), this.title.getY() + 160, 250, 30);
                this.healthInsuranceHousesNameInput.setBounds(this.healthInsuranceHousesNameLabel.getX() + 170, this.healthInsuranceHousesNameLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.healthInsuranceHousesNameLabel.getX() - 50, this.healthInsuranceHousesNameLabel.getY() + 250, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 356, this.sendButton.getY(), 170, 30);
            }
            case "Diseases" -> {
                this.diseasesNameLabel.setBounds(this.title.getX(), this.title.getY() + 160, 250, 30);
                this.diseasesNameInput.setBounds(this.diseasesNameLabel.getX() + 170, this.diseasesNameLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.diseasesNameLabel.getX() - 50, this.diseasesNameLabel.getY() + 250, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 356, this.sendButton.getY(), 170, 30);
            }
        }
    }

    private void AddToPanel(){
        this.frame.add(this.title);
        this.frame.add(this.sendButton);
        this.frame.add(this.backButton);

        switch (this.sqlTable) {
            case "Doctors" -> {
                this.frame.add(this.doctorsNameLabel);
                this.frame.add(this.doctorsNameInput);
                this.frame.add(this.doctorsSurnameLabel);
                this.frame.add(this.doctorsSurnameInput);
                this.frame.add(this.doctorsSpecialityLabel);
                this.frame.add(this.doctorsSpecialityInput);
            }
            case "Pills" -> {
                this.frame.add(this.pillsNameLabel);
                this.frame.add(this.pillsNameInput);
                this.frame.add(this.pillsDiseaseTreatedLabel);
                this.frame.add(this.pillsDiseaseTreatedDropdownList);
                this.frame.add(this.pillsSideEffectsLabel);
                this.frame.add(this.pillsSideEffectsInput);
            }
            case "Patients" -> {
                this.frame.add(this.patientNameLabel);
                this.frame.add(this.patientNameInput);
                this.frame.add(this.patientSurnameLabel);
                this.frame.add(this.patientSurnameInput);
                this.frame.add(this.patientPersonalIdentificationNumberLabel);
                this.frame.add(this.patientPersonalIdentificationNumberInput);
                this.frame.add(this.patientStreetLabel);
                this.frame.add(this.patientStreetInput);
                this.frame.add(this.patientCityLabel);
                this.frame.add(this.patientCityInput);
                this.frame.add(this.patientCountyLabel);
                this.frame.add(this.patientCountyInput);
                this.frame.add(this.patientBirthDateLabel);
                this.frame.add(this.patientBirthDateInput);
                this.frame.add(this.pickDate);
                this.frame.add(this.calendar);
                this.frame.add(this.patientHealthInsuranceHouseLabel);
                this.frame.add(this.patientHealthInsuranceHouseDropdownList);
                this.frame.add(this.patientTestedPillLabel);
                this.frame.add(this.patientTestedPillDropdownList);
                this.frame.add(this.patientStreetNumberLabel);
                this.frame.add(this.patientStreetNumberInput);
                this.frame.add(this.patientSexLabel);
                this.frame.add(this.sexDropdownList);
            }
            case "HealthInsuranceHouses" -> {
                this.frame.add(this.healthInsuranceHousesNameLabel);
                this.frame.add(this.healthInsuranceHousesNameInput);
            }
            case "Diseases" -> {
                this.frame.add(this.diseasesNameLabel);
                this.frame.add(this.diseasesNameInput);
            }
        }
    }

    private boolean areEmptyDoctorsButtons(){
        return !this.doctorsNameInput.getText().equals("")
                && !this.doctorsSurnameInput.getText().equals("")
                && !this.doctorsSpecialityInput.getText().equals("");
    }

    private boolean areEmptyPillsButtons(){
        return !this.pillsNameInput.getText().equals("")
                && !this.pillsSideEffectsInput.getText().equals("");
    }

    private boolean areEmptyPatientsButtons(){
        return !this.patientNameInput.getText().equals("")
                && !this.patientSurnameInput.getText().equals("")
                && !this.patientPersonalIdentificationNumberInput.getText().equals("")
                && !this.patientStreetInput.getText().equals("")
                && !this.patientCityInput.getText().equals("")
                && !this.patientCountyInput.getText().equals("")
                && !this.patientBirthDateInput.getText().equals("")
                && !this.patientStreetNumberInput.getText().equals("");
    }

    private boolean areEmptyDiseasesButtons(){
        return !this.diseasesNameInput.getText().equals("");
    }

    private boolean areEmptyHealthInsuranceHousesButtons(){
        return !this.healthInsuranceHousesNameInput.getText().equals("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.sendButton){
            switch (this.sqlTable){
                case "Doctors":
                    if(areEmptyDoctorsButtons()){
                        int done = this.dataBase.sendInsert("INSERT INTO Doctori VALUES('" +
                                this.doctorsNameInput.getText() + "', '" +
                                this.doctorsSurnameInput.getText() + "', '" +
                                this.doctorsSpecialityInput.getText() + "');");

                        if(done == 1)
                            this.dataBase.sendQuery("SELECT * FROM Doctori", false, false);
                        this.prev(Package.pkg);
                    }
                    break;

                case "Pills":
                    if(areEmptyPillsButtons()){
                        int s1 = -1;
                        ResultSet resultSet;
                        try {
                            resultSet = this.dataBase.getStatement().executeQuery("SELECT BoalaID FROM Boli WHERE Nume = '" + this.pillsDiseaseTreatedDropdownList.getItemAt(this.pillsDiseaseTreatedDropdownList.getSelectedIndex()).toString() + "'");
                            while (resultSet.next()) {
                                s1 = resultSet.getInt(1);
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        if(s1 != -1){
                            int done = this.dataBase.sendInsert("INSERT INTO Medicamente VALUES(" +
                                    Integer.toString(s1) + ", '" +
                                    this.pillsNameInput.getText() + "', '" +
                                    this.pillsSideEffectsInput.getText() + "');");
                            if(done == 1)
                                this.dataBase.sendQuery("SELECT Medicamente.Denumire, Boli.Nume AS 'Boala Tratata', Medicamente.ReactiiAdversePosibile " +
                                        "FROM Medicamente INNER JOIN Boli ON Medicamente.BoalaID = Boli.BoalaID", false, false);
                            this.prev(Package.pkg);
                        }
                    }
                    break;

                case "Patients":
                    if(areEmptyPatientsButtons()){
                        int s1 = -1, s2 = -1;
                        ResultSet resultSet;
                        try {
                            resultSet = this.dataBase.getStatement().executeQuery("SELECT MedicamentID FROM Medicamente WHERE Denumire = '" + this.patientTestedPillDropdownList.getItemAt(this.patientTestedPillDropdownList.getSelectedIndex()).toString() + "'");
                            while (resultSet.next()) {
                                s1 = resultSet.getInt(1);
                            }
                            resultSet = this.dataBase.getStatement().executeQuery("SELECT CasaDeSanatateID FROM CaseDeSanatate WHERE Nume = '" + this.patientHealthInsuranceHouseDropdownList.getItemAt(this.patientHealthInsuranceHouseDropdownList.getSelectedIndex()).toString() + "'");
                            while (resultSet.next()) {
                                s2 = resultSet.getInt(1);
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        if((s1 != -1) && (s2 != -1)){
                            int done = this.dataBase.sendInsert("INSERT INTO Pacienti VALUES(" +
                                    Integer.toString(s1) + ", " +
                                    Integer.toString(s2) + ", '" +
                                    this.patientNameInput.getText() + "', '" +
                                    this.patientSurnameInput.getText() + "', '" +
                                    this.patientPersonalIdentificationNumberInput.getText() + "', '" +
                                    this.patientStreetInput.getText() + "', '" +
                                    this.patientStreetNumberInput.getText() + "', '" +
                                    this.patientCityInput.getText() + "', '" +
                                    this.patientCountyInput.getText() + "', '" +
                                    this.sexDropdownList.getItemAt(this.sexDropdownList.getSelectedIndex()).toString() + "', '" +
                                    this.patientBirthDateSQL + "');"
                            );

                            if(done == 1)
                                this.dataBase.sendQuery("SELECT Pacienti.Nume, Pacienti.Prenume, Pacienti.CNP, Pacienti.Strada, Pacienti.Numar, Pacienti.Oras, Pacienti.Judet, Pacienti.Sex, Pacienti.DataNasterii, CaseDeSanatate.Nume, Medicamente.Denumire\n" +
                                        "FROM Pacienti INNER JOIN CaseDeSanatate ON Pacienti.CasaDeSanatateID = CaseDeSanatate.CasaDeSanatateID INNER JOIN Medicamente on Pacienti.MedicamentID = Medicamente.MedicamentID", false, false);
                            this.prev(Package.pkg);
                        }
                    }
                    break;

                case "HealthInsuranceHouses":
                    if(areEmptyHealthInsuranceHousesButtons()){
                        int done = this.dataBase.sendInsert("INSERT INTO CaseDeSanatate VALUES('" +
                                this.healthInsuranceHousesNameInput.getText() + "')");

                        if(done == 1)
                            this.dataBase.sendQuery("SELECT * FROM CaseDeSanatate", false, false);
                        this.prev(Package.pkg);
                    }
                    break;

                case "Diseases":
                    if(areEmptyDiseasesButtons()){
                        int done = this.dataBase.sendInsert("INSERT INTO Boli VALUES('" +
                                this.diseasesNameInput.getText() + "')");

                        if(done == 1)
                            this.dataBase.sendQuery("SELECT * FROM Boli", false, false);
                        this.prev(Package.pkg);
                    }
            }
        }

        else if(e.getSource() == this.backButton){
            this.prev(Package.pkg);
        }

        else if(e.getSource() == this.pickDate){
            this.show = !this.show;
            this.calendar.setVisible(show);
        }
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
            case "Doctors" -> System.out.println("Insert Doctors State");
            case "Pills" -> System.out.println("Insert Pill State");
            case "Patients" -> System.out.println("Insert Patient State");
            case "HealthInsuranceHouses" -> System.out.println("Insert Health Insurance House State");
            case "Diseases" -> System.out.println("Insert Disease State");
        }
    }
}
