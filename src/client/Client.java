package client;

import GUI.GameGUI;
import client.request.Request;
import client.request.RequestType;
import client.request.StartRoundRequest;
import gamelogic.Category;
import server.response.Response;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class Client {

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
            gameGUI = new GameGUI();
        } catch (Exception e){
            closeEverything(socket, out, in);
        }
        addActionListenersToButtons();
    }

    public void startListening() {
        ClientProtocol protocol = new ClientProtocol();
        new Thread(() -> {
            try{
                while(running){
                    Object obj = in.readObject();
                    if (obj instanceof Response response){
                        protocol.processResponse(this, response);
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

    public void addActionListenersToButtons(){
        gameGUI.loginPanel.loginButton.addActionListener(e->{
            String givenUsername = gameGUI.loginPanel.usernameTextField.getText();
            username = givenUsername;
            System.out.println(givenUsername + " sending connect-request");
            sendRequest(new Request(RequestType.CONNECT, givenUsername));
        });
        gameGUI.loginPanel.exitGameButton.addActionListener(e->{
            System.out.println("Shutting down");
            System.exit(0);
        });
        gameGUI.mainPanel.logoutButton.addActionListener(e->{
            System.out.println("Logging out");
            sendRequest(new Request(RequestType.DISCONNECT, username));
        });
        gameGUI.mainPanel.newGameButton.addActionListener(e->{
            System.out.println(username + " starting new game");
            sendRequest(new Request(RequestType.START_GAME, username));
        });
        gameGUI.waitingPanel.leaveQueueButton.addActionListener(e->{
            System.out.println("Leaving queue");
            sendRequest(new Request(RequestType.LEAVE_QUEUE, username));
            gameGUI.switchPanel(2);
        });
        gameGUI.uglyScorePanel.getContinueButton().addActionListener(e ->{

        });

        addActionListenersToCategoryButtons();
    }

    public void addActionListenersToCategoryButtons(){
        List<JButton> buttons = gameGUI.categoryPanel.getCategoryButtons();
        for(JButton button : buttons){
            button.addActionListener(e ->{
                Category selectedCategory = Category.valueOf(button.getText());
                System.out.println(username + " sending get question request");
                sendRequest(new StartRoundRequest(RequestType.CATEGORY_CHOSEN, username, selectedCategory));
            });
        }
    }


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 55554);
        Client client = new Client(socket);
    }
}
