package states;

import stateDesign.Package;
import stateDesign.PackageState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginState implements ActionListener, KeyListener, PackageState {
    final private JButton submitButton;
    final private JButton guestButton;
    final private JLabel userLabel, passLabel, messageLabel, loginLabel;
    final private JTextField  userInput;
    final private  JPasswordField passInput;
    final private JCheckBox showPassword;
    final private JFrame frame;
    final private Color backgroundColor = new Color(0, 10, 20);
    final private Color textColor = new Color(237, 242, 244);
    final private Color errorColor = new Color(200, 8, 21);
    final private Color inputColor = new Color(0, 150, 170);
    private boolean isAdmin;

    public LoginState(JFrame frame){
        this.frame = frame;
        this.frame.getContentPane().removeAll();
        this.frame.repaint();

        this.loginLabel = new JLabel();
        this.messageLabel = new JLabel();
        this.userInput = new JTextField();
        this.passInput = new JPasswordField();
        this.userLabel = new JLabel();
        this.passLabel = new JLabel();
        this.showPassword = new JCheckBox();
        this.submitButton =  new JButton();
        this.guestButton = new JButton();
        this.isAdmin = false;

        InitVariables();
        AddToPanel();

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                RepositionGUI();
            }
        });
        this.frame.repaint(); // this line resolves a bug: when you start the app, buttons and text will be painted only if you hover with cursor
    }

    private void onEnterPress(){
        String userValue = this.userInput.getText();
        String passValue = String.valueOf(this.passInput.getPassword());

        if(userValue.equals("sa") && passValue.equals("ed308")){
            // Next State
            this.isAdmin = true;
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

    private void InitVariables() {
        RepositionGUI();

        this.frame.getContentPane().setBackground(this.backgroundColor);

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
        this.showPassword.setFocusable(false);
        this.showPassword.setBackground(this.backgroundColor);
        this.showPassword.setForeground(this.textColor);


        this.submitButton.setText("Sign in");
        this.submitButton.addActionListener(this);
        this.submitButton.setOpaque(false);
        this.submitButton.setContentAreaFilled(false);
        this.submitButton.setFont(new Font("Poppins Medium", Font.BOLD, 30));
        this.submitButton.setFocusable(false);
        this.submitButton.setForeground(this.textColor);

        this.guestButton.setText("Sign in as guest");
        this.guestButton.addActionListener(this);
        this.guestButton.setOpaque(false);
        this.guestButton.setContentAreaFilled(false);
        this.guestButton.setFont(new Font("Poppins Medium", Font.BOLD, 30));
        this.guestButton.setFocusable(false);
        this.guestButton.setForeground(this.textColor);
    }

    private void RepositionGUI(){

        this.loginLabel.setBounds(this.frame.getWidth() / 2 - 160, this.frame.getHeight() / 2 - 280, 300, 100);
        this.messageLabel.setBounds(this.loginLabel.getX() + 45, this.loginLabel.getY() + 80, 450, 40);
        this.userLabel.setBounds(this.frame.getWidth() / 2 - 150,
                this.frame.getHeight() / 2 - (20 + 20 + 20 + 20 + 20 + 20),
                100 ,40);
        this.userInput.setBounds(this.userLabel.getX(), this.userLabel.getY() + 35, 300, 40);
        this.passLabel.setBounds(this.userInput.getX(), this.userInput.getY() + 35, 100, 40);
        this.passInput.setBounds(this.passLabel.getX(), this.passLabel.getY() + 35, 300, 40);
        this.showPassword.setBounds(this.passInput.getX(), this.passInput.getY() + 50, 200, 40);
        this.submitButton.setBounds(this.showPassword.getX(), this.showPassword.getY() + 50, 300, 60);
        this.guestButton.setBounds(this.submitButton.getX(), this.submitButton.getY() + 80, 300, 60);
    }

    private void AddToPanel(){
        this.frame.add(this.loginLabel);
        this.frame.add(this.messageLabel);
        this.frame.add(this.userLabel);
        this.frame.add(this.userInput);
        this.frame.add(this.passLabel);
        this.frame.add(this.passInput);
        this.frame.add(this.showPassword);
        this.frame.add(this.submitButton);
        this.frame.add(this.guestButton);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.submitButton){
            this.onEnterPress();
        }

        else if(ae.getSource() == this.guestButton){
            // Next State
            this.isAdmin = false;
            this.next(Package.pkg);
        }

        else if(ae.getSource() == this.showPassword){
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

    @Override
    public void next(Package pkg) {
        pkg.setState(new MenuState(this.frame, this.isAdmin));
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
