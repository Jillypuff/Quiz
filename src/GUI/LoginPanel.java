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
        add(Box.createVerticalStrut(40));

        welcomeLabel = new JLabel("Welcome to QUIZ!");
        enterNameLabel = new JLabel("Enter Your Name:");
        usernameTextField = new JTextField(50);
        startButton = new JButton("START");
        exitButton = new JButton("EXIT");

        startButton.setPreferredSize(new Dimension(120, 40));
        exitButton.setPreferredSize(new Dimension(120, 40));

        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(new Color(22, 153, 153));
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        enterNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        usernameTextField.setForeground(Color.BLACK);
        usernameTextField.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        namePanel.add(enterNameLabel);
        namePanel.add(Box.createHorizontalStrut(5));
        namePanel.add(usernameTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createHorizontalStrut(100));
        buttonPanel.add(exitButton);

        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(1, 214, 196));
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(0, 153, 255));
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));

        add(welcomeLabel);
        add(Box.createVerticalStrut(50));
        add(namePanel);
        add(buttonPanel);
    }

    public void addActionListener(ActionListener listener){
        startButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }
}
