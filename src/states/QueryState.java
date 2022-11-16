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
    final private Color errorColor = new Color(200, 8, 21);
    final private Color inputColor = new Color(0, 150, 170);

    private DataBase db;
    JButton showDoctorsButton;

    public QueryState(boolean signedInAsAdmin){
        try{
            db = new DataBase();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        this.showDoctorsButton = new JButton();

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
        this.showDoctorsButton.setForeground(this.textColor);
    }

    private void RepositionGUI(){
        this.showDoctorsButton.setBounds(this.getWidth() / 6, this.getHeight() / 6, 170, 30);
    }

    private void AddToPanel(){
        this.add(this.showDoctorsButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.showDoctorsButton){
            db.SendQuery("SELECT * FROM Doctori");
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
