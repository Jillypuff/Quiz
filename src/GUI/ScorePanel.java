package GUI;
import javax.swing.*;
import java.awt.*;
public class ScorePanel extends JPanel {

    private final JLabel[] playerScores;
    private final JLabel[] opponentScores;
    private final JButton continueButton;

    public ScorePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Player section
        JPanel playerPanel = createPlayerSection("YOU");

        // Opponent section
        JPanel opponentPanel = createPlayerSection("OPPONENT");

        // Add both sections to the main layout
        JPanel scoresPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        scoresPanel.setBackground(Color.WHITE);
        scoresPanel.add(playerPanel);
        scoresPanel.add(opponentPanel);

        // Continue button
        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 16));
        continueButton.setFocusPainted(false);
        continueButton.setBackground(new Color(0x4CAF50));
        continueButton.setForeground(Color.WHITE);

        // Add components to main layout
        add(scoresPanel, BorderLayout.CENTER);
        add(continueButton, BorderLayout.SOUTH);

        // Initialize score labels for updating
        playerScores = initializeScoreLabels(playerPanel);
        opponentScores = initializeScoreLabels(opponentPanel);
    }

    // Helper to create a player's section
    private JPanel createPlayerSection(String title) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sectionPanel.add(titleLabel);
        sectionPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing

        for (int i = 1; i <= 3; i++) {
            JPanel roundPanel = new JPanel(new BorderLayout());
            roundPanel.setBackground(Color.WHITE);

            JLabel roundLabel = new JLabel("ROUND " + i, SwingConstants.LEFT);
            roundLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JLabel scoreLabel = new JLabel("0", SwingConstants.RIGHT);
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
            scoreLabel.setName("score" + i);

            roundPanel.add(roundLabel, BorderLayout.WEST);
            roundPanel.add(scoreLabel, BorderLayout.EAST);
            sectionPanel.add(roundPanel);
        }

        return sectionPanel;
    }

    // Helper to initialize and store score labels for future updates
    private JLabel[] initializeScoreLabels(JPanel sectionPanel) {
        Component[] components = sectionPanel.getComponents();
        JLabel[] scoreLabels = new JLabel[3];
        int index = 0;
        for (Component component : components) {
            if (component instanceof JPanel roundPanel) {
                for (Component inner : roundPanel.getComponents()) {
                    if (inner instanceof JLabel label && label.getName() != null) {
                        scoreLabels[index++] = label;
                    }
                }
            }
        }
        return scoreLabels;
    }

    // Methods to update scores
    public void setPlayerScore(int round, int score) {
        if (round >= 1 && round <= 3) {
            playerScores[round - 1].setText(String.valueOf(score));
        }
    }

    public void setOpponentScore(int round, int score) {
        if (round >= 1 && round <= 3) {
            opponentScores[round - 1].setText(String.valueOf(score));
        }
    }

    public JButton getContinueButton() {
        return continueButton;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Score Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);

        ScorePanel scorePanel = new ScorePanel();
        frame.add(scorePanel);
        frame.setVisible(true);

        // Test updating scores
        scorePanel.setPlayerScore(1, 10);
        scorePanel.setPlayerScore(2, 20);
        scorePanel.setPlayerScore(3, 30);
        scorePanel.setOpponentScore(1, 15);
        scorePanel.setOpponentScore(2, 25);
        scorePanel.setOpponentScore(3, 5);
    }
}