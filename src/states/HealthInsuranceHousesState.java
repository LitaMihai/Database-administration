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

public class HealthInsuranceHousesState implements PackageState, ActionListener {
    final private Color backgroundColor = new Color(0, 10, 20);
    final private Color textColor = new Color(237, 242, 244);
    final private Color inputColor = new Color(0, 150, 170);
    final private JFrame frame;
    final private DataBase dataBase;
    final private JLabel title, query1Text;
    final private JButton viewButton, deleteButton, insertButton, backButton, query1Button;
    private int buttonPressed;
    private boolean isAdmin;

    public HealthInsuranceHousesState(JFrame frame, DataBase dataBase, boolean isAdmin){
        this.frame = frame;
        this.frame.getContentPane().removeAll();
        this.frame.repaint();
        this.frame.setTitle("Case De Sanatate");

        this.dataBase = dataBase;

        this.title = new JLabel();
        this.query1Text = new JLabel();

        this.viewButton = new JButton();
        this.deleteButton = new JButton();
        this.insertButton = new JButton();
        this.backButton = new JButton();
        this.query1Button = new JButton();

        this.buttonPressed = 0;

        this.isAdmin = isAdmin;

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

        this.title.setText("Case Sanatate");
        this.title.setFont(new Font("Poppins Medium", Font.BOLD, 30));
        this.title.setForeground(this.textColor);
        this.title.setHorizontalAlignment(SwingConstants.CENTER);

        this.query1Text.setText("<html><center>" + "Casele de sanatate  care au mai mult de 3 asigurati" + "</center></html>");
        this.query1Text.setFont(new Font("Poppins Medium", Font.BOLD, 20));
        this.query1Text.setForeground(this.textColor);
        this.query1Text.setHorizontalAlignment(SwingConstants.CENTER);

        this.viewButton.setText("View");
        this.viewButton.addActionListener(this);
        this.viewButton.setFont(new Font("Poppins Medium", Font.BOLD, 20));
        this.viewButton.setFocusable(false);
        this.viewButton.setForeground(this.textColor);
        this.viewButton.setBackground(this.inputColor);

        this.deleteButton.setText("Delete");
        this.deleteButton.addActionListener(this);
        this.deleteButton.setFont(new Font("Poppins Medium", Font.BOLD, 20));
        this.deleteButton.setFocusable(false);
        this.deleteButton.setForeground(this.textColor);
        this.deleteButton.setBackground(this.inputColor);
        if(this.isAdmin == false){
            this.deleteButton.setEnabled(false);
            this.deleteButton.setOpaque(false);
        }

        this.insertButton.setText("Insert");
        this.insertButton.addActionListener(this);
        this.insertButton.setFont(new Font("Poppins Medium", Font.BOLD, 20));
        this.insertButton.setFocusable(false);
        this.insertButton.setForeground(this.textColor);
        this.insertButton.setBackground(this.inputColor);
        if(this.isAdmin == false){
            this.insertButton.setEnabled(false);
            this.insertButton.setOpaque(false);
        }

        this.backButton.setText("Back");
        this.backButton.addActionListener(this);
        this.backButton.setFont(new Font("Poppins Medium", Font.BOLD, 20));
        this.backButton.setFocusable(false);
        this.backButton.setForeground(this.textColor);
        this.backButton.setBackground(this.inputColor);

        this.query1Button.setText("Execute");
        this.query1Button.addActionListener(this);
        this.query1Button.setFont(new Font("Poppins Medium", Font.BOLD, 20));
        this.query1Button.setFocusable(false);
        this.query1Button.setForeground(this.textColor);
        this.query1Button.setBackground(this.inputColor);
    }

    private void RepositionGUI(){
        int auxWidth = this.frame.getWidth() / 2;
        int auxHeight = this.frame.getHeight() / 2;

        this.title.setBounds(auxWidth - (343), auxHeight - (90 + 68), 230, 30);
        this.viewButton.setBounds(this.title.getX() + 30, this.title.getY() + 77, 170, 30);
        this.deleteButton.setBounds(this.viewButton.getX(), this.viewButton.getY() + 57, 170, 30);
        this.insertButton.setBounds(this.deleteButton.getX(), this.deleteButton.getY() + 57, 170, 30);
        this.backButton.setBounds(this.title.getX() + 500, this.title.getY() - 110, 170, 30);
        this.query1Text.setBounds(this.viewButton.getX() + 300, this.viewButton.getY(), 313, 60);
        this.query1Button.setBounds(this.query1Text.getX() + (313/2) - (170/2), this.query1Text.getY() + 80, 170, 30);
    }

    private void AddToPanel(){
        this.frame.add(this.title);
        this.frame.add(this.viewButton);
        this.frame.add(this.deleteButton);
        this.frame.add(this.insertButton);
        this.frame.add(this.backButton);
        this.frame.add(this.query1Text);
        this.frame.add(this.query1Button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.viewButton)
            dataBase.sendQuery("SELECT * FROM CaseDeSanatate", false, false);

        else if(e.getSource() == this.query1Button)
            dataBase.sendQuery(
                    "SELECT CaseDeSanatate.Nume, COUNT(Pacienti.CasaDeSanatateID) AS NrAsigurati\n" +
                            "FROM CaseDeSanatate INNER JOIN Pacienti ON CaseDeSanatate.CasaDeSanatateID = Pacienti.CasaDeSanatateID\n" +
                            "GROUP BY CaseDeSanatate.Nume\n" +
                            "HAVING COUNT(Pacienti.CasaDeSanatateID) > 3", true, true
            );

        else if(e.getSource() == this.insertButton){
            this.buttonPressed = 0;
            this.next(Package.pkg);
        }

        else if(e.getSource() == this.deleteButton){
            this.buttonPressed = 1;
            this.next(Package.pkg);
        }

        else if(e.getSource() == this.backButton)
            this.prev(Package.pkg);
    }

    @Override
    public void next(Package pkg) {
        switch (this.buttonPressed){
            case 0:
                pkg.setState(new InsertState(this.frame, "HealthInsuranceHouses", this.dataBase, this.isAdmin));
                break;
            case 1:
                pkg.setState(new DeleteState(this.frame, "HealthInsuranceHouses", this.dataBase, this.isAdmin));
                break;
        }
    }

    @Override
    public void prev(Package pkg) {
        pkg.setState(new MenuState(this.frame, this.isAdmin));
    }

    @Override
    public void printStatus() {
        System.out.println("Health Insurance Houses State");
    }
}
