import javax.swing.*;
import java.awt.*;

public class GameGUI {

    Client client;
    JFrame gameFrame;
    JTextField questionField;
    JButton[] answerButtons = new JButton[4];

    public GameGUI(Client client) {
        this.client = client;
    }

    public void showLoginWindow() {
        String username = JOptionPane.showInputDialog(null, "Enter your username:");
        if (username != null && !username.trim().isEmpty()) {
            client.startGame(username);
        } else {
            JOptionPane.showMessageDialog(gameFrame, "Please enter your username");
            showLoginWindow();
        }
    }

    public void showMenu() {
        String[] options = {"Start Game", "Exit Game"};
        int choice = JOptionPane.showOptionDialog(null, "Welcome!", "Game Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            startGame();
        } else {
            System.exit(0);
        }
    }

    public void startGame() {
        gameFrame = new JFrame("Game");
        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());

        questionField = new JTextField();
        questionField.setEditable(false);
        gameFrame.add(questionField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));
    }

    public void showQuestion(Question question) {
        questionField.setText(question.question);
    }
}
