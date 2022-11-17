package states;

import misc.DataBase;
import stateDesign.Package;
import stateDesign.PackageState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class QueryState extends JFrame implements PackageState, ActionListener {
    final private Color backgroundColor = new Color(0, 10, 20);
    final private Color textColor = new Color(237, 242, 244);
    final private Color inputColor = new Color(0, 150, 170);

    private DataBase db;
    JButton showDoctorsButton;
    JButton showPatientButton;
    JButton showHealthInsuranceHousesButton;

    public QueryState(boolean signedInAsAdmin){
        try{
            db = new DataBase();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        this.showDoctorsButton = new JButton();
        this.showPatientButton = new JButton();
        this.showHealthInsuranceHousesButton = new JButton();

        InitFrame();
        InitVariables();
        AddToPanel();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                RepositionGUI();
            }
        });
    }

    private void InitFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Queries");
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);
    }

    private void InitVariables(){
        RepositionGUI();

        this.getContentPane().setBackground(this.backgroundColor);


        this.showDoctorsButton.setText("Show Doctors");
        this.showDoctorsButton.addActionListener(this);
        this.showDoctorsButton.setOpaque(false);
        this.showDoctorsButton.setContentAreaFilled(false);
        this.showDoctorsButton.setFont(new Font("Poppins Medium", Font.BOLD, 20));
        this.showDoctorsButton.setFocusable(false);
        this.showDoctorsButton.setForeground(this.textColor);

        this.showPatientButton.setText("Show Patients");
        this.showPatientButton.addActionListener(this);
        this.showPatientButton.setOpaque(false);
        this.showPatientButton.setContentAreaFilled(false);
        this.showPatientButton.setFont(new Font("Poppins Medium", Font.BOLD, 20));
        this.showPatientButton.setFocusable(false);
        this.showPatientButton.setForeground(this.textColor);
    }

    private void RepositionGUI(){
        this.showDoctorsButton.setBounds(this.getWidth() / 6, this.getHeight() / 6, 170, 30);
        this.showPatientButton.setBounds(this.showDoctorsButton.getX(), this.showDoctorsButton.getY() + 100, 170, 30);
    }

    private void AddToPanel(){
        this.add(this.showDoctorsButton);
        this.add(this.showPatientButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.showDoctorsButton){
            db.SendQuery("SELECT * FROM Doctori");
        }
        if(e.getSource() == this.showPatientButton){
            db.SendQuery("SELECT * FROM Pacienti");
        }
    }

    @Override
    public void next(Package pkg) {
        System.out.println("Nothing for the moment");
    }

    @Override
    public void prev(Package pkg) {
        pkg.setState(new LoginState());
    }

    @Override
    public void printStatus() {
        System.out.println("Query State");
    }


}
