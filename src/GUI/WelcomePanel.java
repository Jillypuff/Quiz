package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel {

    private final ImageIcon backgroundImage = new ImageIcon("src/GUI/images/Image2.jpg");

    private final JLabel welcomePrompt;
    private final JButton newGameButton;
    private final JButton logoutButton;

    public WelcomePanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(80));

        welcomePrompt = new JLabel();
        welcomePrompt.setFont(new Font("Lucida Console", Font.PLAIN, 18));
        welcomePrompt.setAlignmentX(CENTER_ALIGNMENT);
        getWelcomePrompt().setOpaque(false);

        newGameButton = createButton("NEW GAME");
        newGameButton.setFocusable(false);
        logoutButton = createButton("LOG OUT");
        logoutButton.setFocusable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(logoutButton);
        buttonPanel.setOpaque(false);

        add(welcomePrompt);
        add(Box.createVerticalStrut(60));
        add(buttonPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Lucida Console", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(150, 40));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, 600, 360, this);
    }
}
