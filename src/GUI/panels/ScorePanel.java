package GUI.panels;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private final JLabel playerScoreLabel;
    private final JLabel opponentScoreLabel;
    private final JLabel resultLabel;
    private final JButton continueButton;

    private final ImageIcon backgroundImage = new ImageIcon("src/GUI/images/Image2.jpg");

    public ScorePanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        playerScoreLabel = new JLabel("Your Score: 0");
        opponentScoreLabel = new JLabel("Opponent's Score: 0");
        resultLabel = new JLabel("");
        continueButton = new JButton("Continue");

        playerScoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        opponentScoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        resultLabel.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        continueButton.setFont(new Font("Arial", Font.BOLD, 14));

        playerScoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        opponentScoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        resultLabel.setAlignmentX(CENTER_ALIGNMENT);
        continueButton.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(60));
        add(resultLabel);
        add(Box.createVerticalStrut(40));
        add(playerScoreLabel);
        add(Box.createVerticalStrut(10));
        add(opponentScoreLabel);
        add(Box.createVerticalStrut(50));
        add(continueButton);
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
            String resultText = (gameOver) ? "Game ended on a draw" : "DRAW!";
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, 600, 360, this);
    }
}