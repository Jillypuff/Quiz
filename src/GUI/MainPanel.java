package GUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel{

    public JLabel welcomePrompt = new JLabel();
    public JButton newGameButton;
    public JButton logoutButton;

    public MainPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(50));

        newGameButton = new JButton("New Game");
        logoutButton = new JButton("Logout");

        add(welcomePrompt);
        add(newGameButton);
        add(logoutButton);
    }

    public void addActionListener(ActionListener listener){
        newGameButton.addActionListener(listener);
        logoutButton.addActionListener(listener);
    }
}
