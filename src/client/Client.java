package client;

import GUI.GameGUI;
import server.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements ActionListener {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    String username;
    GameGUI gameGUI;

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
    }

    public void startListening() {
        new Thread(() -> {
            ClientProtocol protocol = new ClientProtocol();
            try{
                while(in.readObject() instanceof Response response){
                    protocol.processResponse(response, this);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    public void closeEverything(Socket socket, ObjectOutputStream out, ObjectInputStream in){
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

    public void sendRequest(JButton button) throws IOException {
        if(button == gameGUI.loginPanel.loginButton){
            System.out.println("Sending connect-request");
            out.writeObject(new Request(RequestType.CONNECT, gameGUI.loginPanel.usernameTextField.getText()));
        } else if (button == gameGUI.loginPanel.exitGameButton){
            System.out.println("Shutting down");
            System.exit(0);
        } else if (button == gameGUI.mainPanel.logoutButton){
            System.out.println("Logging out");
            out.writeObject(new Request(RequestType.DISCONNECT));
        } else if (button == gameGUI.mainPanel.newGameButton){
            System.out.println("Starting new game");
            out.writeObject(new Request(RequestType.START_GAME, username));
        }
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
