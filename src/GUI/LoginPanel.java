package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel{

    JLabel loginPrompt;
    public JTextField usernameTextField;
    public JButton loginButton;
    public JButton exitGameButton;

    public LoginPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(50));
        loginPrompt = new JLabel("Enter username");
        usernameTextField = new JTextField(10);
        loginButton = new JButton("Login");
        exitGameButton = new JButton("Exit Game");

        loginButton.setPreferredSize(new Dimension(100, 40));
        exitGameButton.setPreferredSize(new Dimension(100, 40));

        add(loginPrompt);
        add(usernameTextField);
        add(loginButton);
        add(exitGameButton);
    }

    public void addActionListener(ActionListener listener){
        loginButton.addActionListener(listener);
        exitGameButton.addActionListener(listener);
    }
}
