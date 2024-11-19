import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameGUI extends JFrame {

    JPanel main;
    JFrame gameFrame;
    JTextField questionField;
    JButton[] answerButtons = new JButton[4];

    Client client;

    public GameGUI(Client client) {
        this.client = client;
    }

    public void showLoginWindow() {
        String username;

        while (true) {
            username = JOptionPane.showInputDialog("Enter your username: ");

            if (username == null) {
                break;
            }

            if (!username.trim().isEmpty()) {
                client.startGame(username);

                gameFrame.dispose();
                return;
            }

            JOptionPane.showMessageDialog(gameFrame, "Please enter a valid username.", "Error", JOptionPane.ERROR_MESSAGE);
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
        gameFrame = new JFrame("Quiz Game");
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
