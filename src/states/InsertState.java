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

public class InsertState implements PackageState, ActionListener {

    final private Color backgroundColor = new Color(0, 10, 20);
    final private Color textColor = new Color(237, 242, 244);
    final private Color inputColor = new Color(0, 150, 170);
    final private DataBase dataBase;
    final private JFrame frame;
    final private String sqlTable;
    final private JButton sendButton, backButton;

    // Doctors
    final private JLabel title, doctorsNameLabel, doctorsSurnameLabel, doctorsSpecialityLabel;
    final private JTextField doctorsNameInput, doctorsSurnameInput, doctorsSpecialityInput;

    // Pills
    final private JLabel pillsNameLabel, pillsDiseaseTreatedLabel, pillsSideEffectsLabel;
    final private JTextField pillsNameInput, pillsDiseaseTreatedInput, pillsSideEffectsInput;

    // Diseases
    final private JLabel diseasesNameLabel;
    final private JTextField diseasesNameInput;

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

        // Doctors
        this.doctorsNameLabel = new JLabel();
        this.doctorsSurnameLabel = new JLabel();
        this.doctorsSpecialityLabel = new JLabel();
        this.doctorsNameInput = new JTextField();
        this.doctorsSurnameInput = new JTextField();
        this.doctorsSpecialityInput = new JTextField();

        // Pills
        this.pillsNameLabel = new JLabel();
        this.pillsDiseaseTreatedLabel = new JLabel();
        this.pillsSideEffectsLabel = new JLabel();
        this.pillsNameInput = new JTextField();
        this.pillsDiseaseTreatedInput = new JTextField();
        this.pillsSideEffectsInput = new JTextField();

        // Diseases
        this.diseasesNameLabel = new JLabel();
        this.diseasesNameInput = new JTextField();

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

        this.pillsDiseaseTreatedInput.setBorder(null);
        this.pillsDiseaseTreatedInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsDiseaseTreatedInput.setBackground(this.inputColor);
        this.pillsDiseaseTreatedInput.setForeground(this.textColor);

        this.pillsSideEffectsLabel.setText("Reactii Adverse Posibile");
        this.pillsSideEffectsLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsSideEffectsLabel.setForeground(this.textColor);

        this.pillsSideEffectsInput.setBorder(null);
        this.pillsSideEffectsInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.pillsSideEffectsInput.setBackground(this.inputColor);
        this.pillsSideEffectsInput.setForeground(this.textColor);

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
        this.title.setBounds(this.frame.getWidth() / 2 - (243 / 2), this.frame.getHeight() / 2 - (230), 243, 30);

        switch (this.sqlTable) {
            case "Doctors" -> {
                this.doctorsNameLabel.setBounds(this.title.getX() - 130, this.title.getY() + 130, 200, 30);
                this.doctorsNameInput.setBounds(this.doctorsNameLabel.getX() + 200, this.doctorsNameLabel.getY(), 295, 30);
                this.doctorsSurnameLabel.setBounds(this.doctorsNameLabel.getX(), this.doctorsNameLabel.getY() + 57, 200, 30);
                this.doctorsSurnameInput.setBounds(this.doctorsSurnameLabel.getX() + 200, this.doctorsSurnameLabel.getY(), 295, 30);
                this.doctorsSpecialityLabel.setBounds(this.doctorsSurnameLabel.getX(), this.doctorsSurnameLabel.getY() + 57, 200, 30);
                this.doctorsSpecialityInput.setBounds(this.doctorsSpecialityLabel.getX() + 200, this.doctorsSpecialityLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.doctorsSpecialityLabel.getX(), this.doctorsSpecialityLabel.getY() + 150, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 326, this.sendButton.getY(), 170, 30);
            }
            case "Pills" -> {
                this.pillsNameLabel.setBounds(this.title.getX() - 150, this.title.getY() + 130, 250, 30);
                this.pillsNameInput.setBounds(this.pillsNameLabel.getX() + 230, this.pillsNameLabel.getY(), 295, 30);
                this.pillsDiseaseTreatedLabel.setBounds(this.pillsNameLabel.getX(), this.pillsNameLabel.getY() + 57, 250, 30);
                this.pillsDiseaseTreatedInput.setBounds(this.pillsDiseaseTreatedLabel.getX() + 230, this.pillsDiseaseTreatedLabel.getY(), 295, 30);
                this.pillsSideEffectsLabel.setBounds(this.pillsDiseaseTreatedLabel.getX(), this.pillsDiseaseTreatedLabel.getY() + 57, 250, 30);
                this.pillsSideEffectsInput.setBounds(this.pillsSideEffectsLabel.getX() + 230, this.pillsSideEffectsLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.pillsSideEffectsLabel.getX(), this.pillsSideEffectsLabel.getY() + 150, 170, 30);
                this.backButton.setBounds(this.sendButton.getX() + 356, this.sendButton.getY(), 170, 30);
            }
            case "Diseases" -> {
                this.diseasesNameLabel.setBounds(this.title.getX() - 150, this.title.getY() + 160, 250, 30);
                this.diseasesNameInput.setBounds(this.diseasesNameLabel.getX() + 230, this.diseasesNameLabel.getY(), 295, 30);
                this.sendButton.setBounds(this.diseasesNameLabel.getX(), this.diseasesNameLabel.getY() + 250, 170, 30);
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
                this.frame.add(this.pillsDiseaseTreatedInput);
                this.frame.add(this.pillsSideEffectsLabel);
                this.frame.add(this.pillsSideEffectsInput);
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
                && !this.pillsDiseaseTreatedInput.getText().equals("")
                && !this.pillsSideEffectsInput.getText().equals("");
    }

    private boolean areEmptyDiseasesButtons(){
        return !this.diseasesNameInput.getText().equals("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.sendButton ){
            switch (this.sqlTable){
                case "Doctors":
                    if(areEmptyDoctorsButtons()){
                        int done = this.dataBase.sendInsert("INSERT INTO Doctori VALUES('" +
                                this.doctorsNameInput.getText() + "', '" +
                                this.doctorsSurnameInput.getText() + "', '" +
                                this.doctorsSpecialityInput.getText() + "');");

                        if(done == 1)
                            this.dataBase.SendQuery("SELECT * FROM Doctori");
                        this.prev(Package.pkg);
                    }
                    break;

                case "Pills":
                    if(areEmptyPillsButtons()){
                        int s1 = -1;
                        ResultSet resultSet;
                        try {
                            resultSet = this.dataBase.getStatement().executeQuery("SELECT BoalaID FROM Boli WHERE Nume = '" + this.pillsDiseaseTreatedInput.getText() + "'");
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
                                this.dataBase.SendQuery("SELECT Medicamente.Denumire, Boli.Nume AS 'Boala Tratata', Medicamente.ReactiiAdversePosibile " +
                                        "FROM Medicamente INNER JOIN Boli ON Medicamente.BoalaID = Boli.BoalaID");
                            this.prev(Package.pkg);
                        }
                    }
                    break;

                case "Diseases":
                    if(areEmptyDiseasesButtons()){
                        int done = this.dataBase.sendInsert("INSERT INTO Boli VALUES('" +
                                this.diseasesNameInput.getText() + "')");

                        if(done == 1)
                            this.dataBase.SendQuery("SELECT * FROM Boli");
                        this.prev(Package.pkg);
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
            case "Doctors" -> pkg.setState(new DoctorsState(this.frame, this.dataBase));
            case "Pills" -> pkg.setState(new PillsState(this.frame, this.dataBase));
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
