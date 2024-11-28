package client;

import GUI.GameGUI;
import Modules.Request;
import Modules.RequestType;
import Modules.Category;
import Modules.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class Client {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private String username;
    private GameGUI gameGUI;
    private boolean playing = false;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
            this.in = new ObjectInputStream(this.socket.getInputStream());
            gameGUI = new GameGUI(this);
        } catch (Exception e) {
            closeEverything();
        }
        addAllListeners();
    }

    public boolean isInGame(){
        return playing;
    }

    public void inGame(boolean playing){
        this.playing = playing;
    }

    public void addAllListeners() {
        addActionListenerToLoginPanel();
        addActionListenersToWelcomePanel();
        addActionListenersToWaitingPanel();
        addActionListenersToCategoryPanel();
        addActionListenerToUglyScorePanel();
    }

    public void startListening() {
        new Thread(() -> {
            ClientProtocol protocol = new ClientProtocol();
            try {
                while (socket.isConnected()) {
                    Object obj = in.readObject();
                    if (obj instanceof Response response) {
                        protocol.processResponse(response, this);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR; CANT READ FROM SOCKET BECAUSE JOSSAN CALLED INSTANCE.NULL OSV");
                System.exit(0);
            }
        }).start();
    }

    public void closeEverything() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Closed connection");
            System.exit(1);
        }
    }

    public void sendRequest(Request request) {
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addActionListenerToLoginPanel() {
        resetActionListeners(gameGUI.loginPanel.getStartButton());
        resetActionListeners(gameGUI.loginPanel.getExitButton());

        gameGUI.loginPanel.getStartButton().addActionListener(e->{
            String username = gameGUI.loginPanel.getUsernameTextField().getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(gameGUI.loginPanel,
                        "You must enter a username!",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            System.out.println("Sending connect-request");
            sendRequest(new Request(RequestType.CONNECT, gameGUI.loginPanel.getUsernameTextField().getText()));
        });
        gameGUI.loginPanel.getExitButton().addActionListener(e->{
            int confirm = JOptionPane.showConfirmDialog(gameGUI.loginPanel,
                    "Are you sure you want to exit?",
                    "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                sendRequest(new Request(RequestType.EXIT_GAME));
                closeEverything();
                System.exit(0);
            }
        });
    }

    public void addActionListenersToWelcomePanel(){
        resetActionListeners(gameGUI.welcomePanel.getNewGameButton());
        resetActionListeners(gameGUI.welcomePanel.getLogoutButton());
        gameGUI.welcomePanel.getNewGameButton().addActionListener(e->{
            System.out.println("Starting new game");
            sendRequest(new Request(RequestType.START_GAME, username));
        });
        gameGUI.welcomePanel.getLogoutButton().addActionListener(e->{
            sendRequest(new Request(RequestType.DISCONNECT));
        });
    }

    public void addActionListenersToWaitingPanel(){
        resetActionListeners(gameGUI.waitingPanel.getLeaveGameButton());
        gameGUI.waitingPanel.getLeaveGameButton().addActionListener(e->{
            if (isInGame()){
                System.out.println("Client giving up");
                sendRequest(new Request(RequestType.GIVE_UP));
                inGame(false);
            }
            else{
                System.out.println("Leaving queue");
                sendRequest(new Request(RequestType.LEAVE_QUEUE, username));
            }
            gameGUI.switchPanel(2);
        });
    }

    public void addActionListenersToCategoryPanel() {
        List<JButton> buttons = gameGUI.categoryPanel.getCategoryButtons();
        for (JButton button : buttons) {
            resetActionListeners(button);
            button.addActionListener(e -> {
                Category selectedCategory = Category.valueOf(button.getText());
                System.out.println("Sending next question request");
                sendRequest(new Request(RequestType.CATEGORY_CHOSEN, username, selectedCategory));
            });
        }
    }

    public void addActionListenerToUglyScorePanel(){
        resetActionListeners(gameGUI.uglyScorePanel.getContinueButton());
        gameGUI.uglyScorePanel.getContinueButton().addActionListener(e->{
            if (isInGame()){
                sendRequest(new Request(RequestType.NEXT_ROUND, username));
                gameGUI.uglyScorePanel.setButtonToWaiting();
            }
            else{
                gameGUI.switchPanel(2);
            }
        });
    }

    public void resetActionListeners(JButton button){
        for(ActionListener listener: button.getActionListeners()){
            button.removeActionListener((listener));
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public GameGUI getGameGUI() {
        return gameGUI;
    }

    public void setGameGUI(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 55554);
        Client client = new Client(socket);
        client.startListening();
    }
}