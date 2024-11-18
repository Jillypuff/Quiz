import javax.swing.*;

public class GameGUI {

    Client client;
    JFrame gameFrame;
    JTextArea gameArea;
    JTextField answerField;
    JButton submitButton;

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
        int choice = JOptionPane.showOptionDialog(null, "Welcome to Quiz!", "Game Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            startGame();
        } else {
            System.exit(0);
        }
    }

    private void startGame() {

    }
}
