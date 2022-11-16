package states;

import stateDesign.Package;
import stateDesign.PackageState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginState extends JFrame implements ActionListener, KeyListener, PackageState {
    JButton submitButton;
    JButton guestButton;
    JLabel userLabel, passLabel, messageLabel, loginLabel;
    final JTextField  userInput;
    final JPasswordField passInput;
    JCheckBox showPassword;
    final private Color backgroundColor = new Color(0, 10, 20);
    final private Color textColor = new Color(237, 242, 244);
    final private Color errorColor = new Color(200, 8, 21);
    final private Color inputColor = new Color(0, 150, 170);

    private boolean signedInAsAdmin;

    public LoginState(){
        this.loginLabel = new JLabel();
        this.messageLabel = new JLabel();
        this.userInput = new JTextField();
        this.passInput = new JPasswordField();
        this.userLabel = new JLabel();
        this.passLabel = new JLabel();
        this.showPassword = new JCheckBox();
        this.submitButton =  new JButton();
        this.guestButton = new JButton();

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

    private void onEnterPress(){
        String userValue = this.userInput.getText();
        String passValue = String.valueOf(this.passInput.getPassword());

        if(userValue.equals("sa") && passValue.equals("ed308")){
            this.signedInAsAdmin = true;

            // Next State
            this.next(Package.pkg);
        }
        else{
            this.messageLabel.setText("Invalid username or password!");
            this.messageLabel.setForeground(this.errorColor);
            this.messageLabel.setBounds(this.loginLabel.getX() - 10, this.loginLabel.getY() + 80, 450, 40);
            this.userInput.setText("");
            this.passInput.setText("");
        }
    }

    private void InitFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Login");
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);
    }

    private void InitVariables() {
        RepositionGUI();

        this.getContentPane().setBackground(this.backgroundColor);

        this.loginLabel.setText("Login");
        this.loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.loginLabel.setFont(new Font("Poppins Medium", Font.BOLD, 50));
        this.loginLabel.setForeground(this.textColor);

        this.messageLabel.setText("Sign in to continue.");
        this.messageLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 25));
        this.messageLabel.setForeground(this.textColor);

        this.userLabel.setText("Username");
        this.userLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.userLabel.setForeground(this.textColor);

        this.userInput.setBorder(null);
        this.userInput.addKeyListener(this);
        this.userInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.userInput.setBackground(this.inputColor);
        this.userInput.setForeground(this.textColor);

        this.passLabel.setText("Password");
        this.passLabel.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.passLabel.setForeground(this.textColor);

        this.passInput.setBorder(null);
        this.passInput.addKeyListener(this);
        this.passInput.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.passInput.setBackground(inputColor);
        this.passInput.setForeground(textColor);

        this.showPassword.addActionListener(this);
        this.showPassword.addKeyListener(this);
        this.showPassword.setText("Show Password");
        this.showPassword.setFont(new Font("Poppins Medium", Font.PLAIN, 20));
        this.showPassword.setBackground(this.backgroundColor);
        this.showPassword.setForeground(this.textColor);


        this.submitButton.setText("Sign in");
        this.submitButton.addActionListener(this);
        this.submitButton.setOpaque(false);
        this.submitButton.setContentAreaFilled(false);
        this.submitButton.setFont(new Font("Poppins Medium", Font.BOLD, 30));
        this.submitButton.setForeground(this.textColor);

        this.guestButton.setText("Sign in as guest");
        this.guestButton.addActionListener(this);
        this.guestButton.setOpaque(false);
        this.guestButton.setContentAreaFilled(false);
        this.guestButton.setFont(new Font("Poppins Medium", Font.BOLD, 30));
        this.guestButton.setForeground(this.textColor);

        this.signedInAsAdmin = false;
    }

    private void RepositionGUI(){

        this.loginLabel.setBounds(this.getWidth() / 2 - 160, this.getHeight() / 2 - 280, 300, 100);
        this.messageLabel.setBounds(this.loginLabel.getX() + 45, this.loginLabel.getY() + 80, 450, 40);
        this.userLabel.setBounds(this.getWidth() / 2 - 150,
                this.getHeight() / 2 - (20 + 20 + 20 + 20 + 20 + 20),
                100 ,40);
        this.userInput.setBounds(this.userLabel.getX(), this.userLabel.getY() + 35, 300, 40);
        this.passLabel.setBounds(this.userInput.getX(), this.userInput.getY() + 35, 100, 40);
        this.passInput.setBounds(this.passLabel.getX(), this.passLabel.getY() + 35, 300, 40);
        this.showPassword.setBounds(this.passInput.getX(), this.passInput.getY() + 50, 200, 40);
        this.submitButton.setBounds(this.showPassword.getX(), this.showPassword.getY() + 50, 300, 60);
        this.guestButton.setBounds(this.submitButton.getX(), this.submitButton.getY() + 80, 300, 60);
    }

    private void AddToPanel(){
        this.add(this.loginLabel);
        this.add(this.messageLabel);
        this.add(this.userLabel);
        this.add(this.userInput);
        this.add(this.passLabel);
        this.add(this.passInput);
        this.add(this.showPassword);
        this.add(this.submitButton);
        this.add(this.guestButton);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.submitButton){
            this.onEnterPress();
        }

        if(ae.getSource() == this.guestButton){
            // Next State
            this.next(Package.pkg);
        }

        if(ae.getSource() == this.showPassword){
            if(this.showPassword.isSelected()){
                this.passInput.setEchoChar((char) 0);
            }
            else{
                this.passInput.setEchoChar('•');
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() != KeyEvent.VK_ENTER){
            this.messageLabel.setText("Sign in to continue.");
            this.messageLabel.setForeground(this.textColor);
            this.messageLabel.setBounds(this.loginLabel.getX() + 45, this.loginLabel.getY() + 80, 450, 40);
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            onEnterPress();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public boolean getSignedInAsAdmin(){
        return this.signedInAsAdmin;
    }

    @Override
    public void next(Package pkg) {
        pkg.setState(new QueryState(this.signedInAsAdmin));
        System.out.println("\nLoading QueryState\n");
        this.dispose();
    }

    @Override
    public void prev(Package pkg) {
        System.out.println("\nAlready in root state\n");
    }

    @Override
    public void printStatus() {
        System.out.println("\nLogin State\n");
    }
}
