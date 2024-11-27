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

public class Client implements ActionListener {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    String username;
    GameGUI gameGUI;
    boolean running = true;

    public Client(Socket socket) {
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            startListening();
            gameGUI = new GameGUI(this);
        } catch (Exception e) {
            closeEverything(socket, out, in);
        }
        addListeners();
    }

    public void addListeners() {
        gameGUI.loginPanel.addActionListener(this);
        gameGUI.welcomePanel.addActionListener(this);
        gameGUI.waitingPanel.addActionListener(this);
        gameGUI.questionPanel.addActionListeners(this);
        addActionListenersToCategoryButtons();
    }

    public void startListening() {
        new Thread(() -> {
            ClientProtocol protocol = new ClientProtocol();
            try {
                while (running) {
                    Object obj = in.readObject();
                    if (obj instanceof Response response) {
                        protocol.processResponse(response, this);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stop() {
        running = false;
    }

    public void closeEverything(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
        stop();
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

    public void sendRequest(JButton button) throws IOException {
        if (button == gameGUI.loginPanel.startButton) {
            String username = gameGUI.loginPanel.usernameTextField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(gameGUI.loginPanel,
                        "You must enter a username!",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            System.out.println("Sending connect-request");
            sendRequest(new Request(RequestType.CONNECT, gameGUI.loginPanel.usernameTextField.getText()));
        } else if (button == gameGUI.loginPanel.exitButton) {
            int confirm = JOptionPane.showConfirmDialog(gameGUI.loginPanel,
                    "Are you sure you want to exit?",
                    "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (button == gameGUI.welcomePanel.getLogoutButton()) {
            sendRequest(new Request(RequestType.DISCONNECT));
        } else if (button == gameGUI.welcomePanel.getNewGameButton()) {
            System.out.println("Starting new game");
            sendRequest(new Request(RequestType.START_GAME, username));
        } else if (button == gameGUI.waitingPanel.getLeaveGameButton()) {
            System.out.println("Leaving queue");
            sendRequest(new Request(RequestType.LEAVE_QUEUE, username));
            gameGUI.switchPanel(2);
        }
    }

    public void addActionListenersToCategoryButtons() {
        List<JButton> buttons = gameGUI.categoryPanel.getCategoryButtons();
        for (JButton button : buttons) {
            button.addActionListener(e -> {
                Category selectedCategory = Category.valueOf(button.getText());
                System.out.println("Sending next question request");
                sendRequest(new Request(RequestType.CATEGORY_CHOSEN, username, selectedCategory));
            });
        }
    }

    public void sendRoundScore(){
        System.out.println("Sending round score");
        sendRequest(new Request(RequestType.ROUND_SCORE, username, gameManager.getRoundScore(), gameManager.isActivePlayer));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        try {
            sendRequest(button);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 55554);
        Client client = new Client(socket);
    }
}