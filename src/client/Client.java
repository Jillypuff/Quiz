package client;

import GUI.GameGUI;
import gamelogic.Category;
import server.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements ActionListener {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    String username;
    GameGUI gameGUI;
    boolean running = true;

    public Client(Socket socket){
        try{
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            startListening();
            gameGUI = new GameGUI(this);
        } catch (Exception e){
            closeEverything(socket, out, in);
        }
        addListeners();
    }

    public void addListeners(){
        gameGUI.loginPanel.addActionListener(this);
        gameGUI.mainPanel.addActionListener(this);
        gameGUI.waitingPanel.addActionListener(this);
        gameGUI.gamePanel.addActionListeners(this);
        addActionListenersToCategoryButtons();
    }

    public void startListening() {
        new Thread(() -> {
            ClientProtocol protocol = new ClientProtocol();
            try{
                while(running){
                    Object obj = in.readObject();
                    if (obj instanceof Response response){
                        protocol.processResponse(response, this);
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    public void stop(){
        running = false;
    }

    public void closeEverything(Socket socket, ObjectOutputStream out, ObjectInputStream in){
        stop();
        try {
            if (out != null){
                out.close();
            }
            if (in != null){
                in.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            System.out.println("Closed connection");
            System.exit(1);
        }
    }

    public void sendRequest(Request request){
        try{
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRequest(JButton button) throws IOException {
        if(button == gameGUI.loginPanel.loginButton){
            System.out.println("Sending connect-request");
            sendRequest(new Request(RequestType.CONNECT, gameGUI.loginPanel.usernameTextField.getText()));
        } else if (button == gameGUI.loginPanel.exitGameButton){
            System.out.println("Shutting down");
            System.exit(0);
        } else if (button == gameGUI.mainPanel.logoutButton){
            System.out.println("Logging out");
            sendRequest(new Request(RequestType.DISCONNECT));
        } else if (button == gameGUI.mainPanel.newGameButton){
            System.out.println("Starting new game");
            sendRequest(new Request(RequestType.START_GAME, username));
        }
        else if (button == gameGUI.waitingPanel.leaveQueueButton){
            System.out.println("Leaving queue");
            sendRequest(new Request(RequestType.LEAVE_QUEUE, username));
            gameGUI.switchPanel(2);
        }
    }

    public void addActionListenersToCategoryButtons(){
        List<JButton> buttons = gameGUI.categoryPanel.getCategoryButtons();
        for(JButton button : buttons){
            button.addActionListener(e ->{
                Category selectedCategory = Category.valueOf(button.getText());
                System.out.println("Sending next question request");
                sendRequest(new Request(RequestType.NEXT_QUESTION, username, selectedCategory));
            });
        }
    }

//    public void addActionListenerToAnswerButtons(){
//        List<JButton> buttons = gameGUI.gamePanel.getAllAnswerButtons();
//        for(JButton button : buttons){
//            button.addActionListener(e -> {
//                sendRequest(new Request(RequestType.ANSWER, username));
//            });
//        }
//    }

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
