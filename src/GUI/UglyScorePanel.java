package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UglyScorePanel extends JPanel {
    private final JLabel playerScoreLabel;
    private final JLabel opponentScoreLabel;
    private final JLabel resultLabel;
    private final JButton continueButton;

    public UglyScorePanel() {
        // Set layout and panel properties
        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        // Initialize components
        playerScoreLabel = new JLabel("Your Score: 0");
        opponentScoreLabel = new JLabel("Opponent's Score: 0");
        resultLabel = new JLabel("");
        continueButton = new JButton("Continue");

        // Styling labels and button
        playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        opponentScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        continueButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Use GridBagLayout for proper alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(playerScoreLabel, gbc);

        gbc.gridy = 1;
        add(opponentScoreLabel, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(resultLabel, gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(continueButton, gbc);
    }

    public void setScoreDisplay(int playerScore, int opponentScore) {
        playerScoreLabel.setText("Your Score: " + playerScore);
        opponentScoreLabel.setText("Opponent's Score: " + opponentScore);
        if (playerScore > opponentScore){
            resultLabel.setText("You won this round!");
        }
        else if (opponentScore > playerScore){
            resultLabel.setText("You lost this round!");
        }
        else{
            resultLabel.setText("It was a draw");
        }
    }


    // Method to update the player score
    public void setPlayerScore(int score) {
        playerScoreLabel.setText("Your Score: " + score);
    }

    // Method to update the opponent score
    public void setOpponentScore(int score) {
        opponentScoreLabel.setText("Opponent's Score: " + score);
    }

    // Method to set the result text
    public void setResultText(String resultText) {
        resultLabel.setText(resultText);
    }

    public JButton getContinueButton() {
        return continueButton;
    }

}
