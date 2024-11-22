package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel{

    JLabel welcomeLabel;
    JLabel enterNameLabel;
    public JTextField usernameTextField;
    public JButton startButton;
    public JButton exitButton;

    public LoginPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(30));

        welcomeLabel = new JLabel("Welcome to Quizkampen!");
        enterNameLabel = new JLabel("Enter Your Name:");
        usernameTextField = new JTextField(20);
        startButton = new JButton("Start");
        exitButton = new JButton("Exit");

        startButton.setPreferredSize(new Dimension(100, 40));
        exitButton.setPreferredSize(new Dimension(100, 40));

        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        namePanel.add(enterNameLabel);
        namePanel.add(Box.createHorizontalStrut(10));
        namePanel.add(usernameTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createHorizontalStrut(90));
        buttonPanel.add(exitButton);

        add(welcomeLabel);
        add(Box.createVerticalStrut(60));
        add(namePanel);
        add(buttonPanel);
    }

    public void addActionListener(ActionListener listener){
        startButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }
}
