package GUI.panels;

import javax.swing.*;
import java.awt.*;

// detta är en frame. så ska det ej vara förstås, allt ska bara vara en panel
public class SecondUglyScorePanel extends JFrame {

    public SecondUglyScorePanel(){
        // Create the main frame
        setTitle("Score Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);


        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        mainPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                "SCORE",
                0, 0, new Font("Arial", Font.BOLD, 20), Color.DARK_GRAY
        ));

        // Create player score panels
        JPanel youPanel = createScorePanel("<You>", new int[]{3, 2, 1, 6}, Color.BLUE);
        JPanel opponentPanel = createScorePanel("<Opponent>", new int[]{1, 2, 1, 4}, Color.RED);

        // Add player panels to the main panel
        mainPanel.add(youPanel, BorderLayout.WEST);
        mainPanel.add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.CENTER); // Add spacing between columns
        mainPanel.add(opponentPanel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);
    }

    private static JPanel createScorePanel(String playerName, int[] scores, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Align scores vertically
        panel.setBackground(new Color(240, 248, 255)); // Match main panel background

        // Add the player's name
        JLabel nameLabel = new JLabel(playerName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(color);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space below the name

        // Add the scores
        String[] rounds = {"Round 1: ", "Round 2: ", "Round 3: ", "Total: "};
        for (int i = 0; i < scores.length; i++) {
            JLabel scoreLabel = new JLabel(rounds[i] + scores[i]);
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            scoreLabel.setForeground(Color.BLACK);
            scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(scoreLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between scores
        }

        return panel;
    }

    // Helper method to create styled JLabels
    private static JLabel createStyledLabel(String text, int fontSize, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
        return label;
    }

    public static void main(String[] args) {
        SecondUglyScorePanel frame = new SecondUglyScorePanel();
    }
}
