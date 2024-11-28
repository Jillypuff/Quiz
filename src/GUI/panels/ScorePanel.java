package GUI.panels;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private final JLabel playerScoreLabel;
    private final JLabel opponentScoreLabel;
    private final JLabel resultLabel;
    private final JButton continueButton;

    public ScorePanel() {

        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        playerScoreLabel = new JLabel("Your Score: 0");
        opponentScoreLabel = new JLabel("Opponent's Score: 0");
        resultLabel = new JLabel("");
        continueButton = new JButton("Continue");

        playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        opponentScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        continueButton.setFont(new Font("Arial", Font.BOLD, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 1;
        add(playerScoreLabel, gbc);

        gbc.gridy = 3;
        add(opponentScoreLabel, gbc);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(resultLabel, gbc);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        add(continueButton, gbc);
    }

    public void setScoreDisplay(int playerScore, int opponentScore, boolean gameOver) {
        playerScoreLabel.setText("Your Score: " + playerScore);
        opponentScoreLabel.setText("Opponent's Score: " + opponentScore);
        if (playerScore > opponentScore){
            String resultText = (gameOver) ? "YOU WON THE GAME!" : "You won this round!";
            resultLabel.setText(resultText);
        }
        else if (opponentScore > playerScore){
            String resultText = (gameOver) ? "YOU LOST" : "You lost this round.";
            resultLabel.setText(resultText);
        }
        else{
            String resultText = (gameOver) ? "Game ended on a draw" : "It was a draw.";
            resultLabel.setText(resultText);
        }
        String buttonText = (gameOver) ? "EXIT GAME" : "CONTINUE";
        continueButton.setText(buttonText);
    }

    public JButton getContinueButton() {
        return continueButton;
    }

    public void setButtonToWaiting(){
        getContinueButton().setText("Waiting...");
        getContinueButton().setEnabled(false);
    }

    public void setButtonToContinue(){
        getContinueButton().setText("Continue");
        getContinueButton().setEnabled(true);
    }
}
