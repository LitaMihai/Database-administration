package states;

import misc.NewPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginState extends JFrame implements ActionListener {
    JButton submitButton;
    JPanel loginPanel;
    JLabel userLabel, passLabel;
    final JTextField userInput, passInput;



    public LoginState() {
        userLabel = new JLabel();
        userLabel.setText("UserName");

        userInput = new JTextField(15);

        passLabel = new JLabel();
        passLabel.setText("Password");

        passInput = new JPasswordField(15);

        submitButton = new JButton("SUBMIT");

        loginPanel = new JPanel(new GridLayout(3, 1));
        loginPanel.add(userLabel);
        loginPanel.add(userInput);
        loginPanel.add(passLabel);
        loginPanel.add(passInput);
        loginPanel.add(submitButton);

        add(loginPanel, BorderLayout.CENTER);

        submitButton.addActionListener(this);
        setTitle("LOGIN");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String userValue = userInput.getText();
        String passValue = passInput.getText();
        System.out.println(userValue + " " + passValue);
        if(userValue.equals("sa") && passValue.equals("ed308")){
            NewPage page = new NewPage();
            page.setVisible(true);

            JLabel welcome_label = new JLabel("Welcome: " + userValue);
            page.getContentPane().add(welcome_label);
        }
        else{
            System.out.println("Please enter valid username and password!");
        }
    }
}
