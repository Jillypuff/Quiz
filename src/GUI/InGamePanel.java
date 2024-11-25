package GUI;

import javax.swing.*;
import java.awt.*;

public class InGamePanel extends JPanel {

    JButton startRoundButton;

    public InGamePanel(){
        setLayout(new BorderLayout());

        // Create the label
        JLabel gameLabel = new JLabel("You're in a game", SwingConstants.CENTER);
        gameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Create the button
        startRoundButton = new JButton("Start Round");

        // Add components to the panel
        add(gameLabel, BorderLayout.CENTER);
        add(startRoundButton, BorderLayout.SOUTH);
    }

    public JButton getStartRoundButton() {
        return startRoundButton;
    }
}
