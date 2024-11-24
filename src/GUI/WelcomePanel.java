package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel {

    private final JLabel welcomePrompt;
    private final JButton newGameButton;
    private final JButton logoutButton;

    private static final Color newGameButtonColor = new Color(1, 214, 196);
    private static final Color logoutButtonColor = new Color (0, 153, 255);

    public WelcomePanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(50));

        welcomePrompt = new JLabel();
        welcomePrompt.setAlignmentX(CENTER_ALIGNMENT);

        newGameButton = createButton("New Game", newGameButtonColor);
        logoutButton = createButton("Logout", logoutButtonColor);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(logoutButton);

        add(welcomePrompt);
        add(Box.createVerticalStrut(60));
        add(buttonPanel);
    }

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        return button;
    }

    public JLabel getWelcomePrompt() {
        return welcomePrompt;
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public void addActionListener(ActionListener listener){
        newGameButton.addActionListener(listener);
        logoutButton.addActionListener(listener);
    }
}
