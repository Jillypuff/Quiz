import javax.swing.*;
import java.awt.*;

public class GameGUI extends JFrame {

    Client client;
    JFrame gameFrame;
    JTextField questionField;
    JButton[] answerButtons = new JButton[4];

    JPanel loginWindow;
    JButton loginButton;
    JButton exitButton;
    JTextField usernameField;
    JLabel loginPrompt;

    JPanel mainWindow;
    JButton startGameButton;
    JButton logoutButton;
    JLabel mainPrompt;

    JPanel gameWindow;

    public GameGUI() {
        // this.client = client;
        this.setTitle("Quiz");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        setupAllWindows();
        this.add(loginWindow, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void switchWindow(int window){
        this.remove(loginWindow);
        this.remove(mainWindow);
        // this.remove(gameWindow);
        switch (window){
            case 1 -> this.add(loginWindow, BorderLayout.CENTER);
            case 2 -> this.add(mainWindow, BorderLayout.CENTER);
            // case 3 -> this.add(gameWindow, BorderLayout.CENTER);
        }
    }

    public void setupAllWindows(){
        loginWindow = new JPanel(new GridLayout(2,2));
        loginButton = new JButton("Login");
        loginButton.addActionListener(_ -> loggedIn());
        exitButton = new JButton("Exit");
        exitButton.addActionListener(_ -> exitGame());
        usernameField = new JTextField(10);
        loginPrompt = new JLabel("Välkommen!\nSkriv in ditt användarnamn");
        loginWindow.add(loginPrompt);
        loginWindow.add(usernameField);
        loginWindow.add(loginButton);
        loginWindow.add(exitButton);

        mainWindow = new JPanel(new FlowLayout());
        logoutButton = new JButton("Logout");
        mainWindow.add(logoutButton);

        gameWindow = new JPanel(new FlowLayout());
    }

    public void loggedIn(){
        String username = usernameField.getText();
        if (!username.isEmpty()){
            switchWindow(1);
            // client.startGame(username);
        } else {
            loginPrompt.setText("Skriv in användarnamn för att kunna logga in.");
        }
    }

    public void exitGame(){
        // client.disconnect();
        System.exit(0);
    }

    public void actionListener(Action action){
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
        gameFrame.setTitle("Game");
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

    public static void main(String[] args) {
        new GameGUI();
    }
}
