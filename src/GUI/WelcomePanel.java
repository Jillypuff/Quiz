package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel{

    public JLabel welcomePrompt = new JLabel();
    public JButton newGameButton;
    public JButton logoutButton;



    public WelcomePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(50));

        welcomePrompt.setAlignmentX(CENTER_ALIGNMENT);

        newGameButton = new JButton("New Game");
        logoutButton = new JButton("Logout");

        newGameButton.setPreferredSize(new Dimension(100, 30));
        newGameButton.setBackground(new Color(1, 214, 196));
        newGameButton.setForeground(Color.WHITE);

        logoutButton.setPreferredSize(new Dimension(100, 30));
        logoutButton.setBackground(new Color(0, 153, 255));
        logoutButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(logoutButton);

        add(welcomePrompt);
        add(Box.createVerticalStrut(60));
        add(buttonPanel);
    }

    public void addActionListener(ActionListener listener){
        newGameButton.addActionListener(listener);
        logoutButton.addActionListener(listener);
    }
}
