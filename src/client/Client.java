package client;

import server.Response;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

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

    public void sendRequest(Request request) throws IOException {
        System.out.println("Sending request: " + request.getType());
        out.writeObject(request);
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

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 55554);
        Client client = new Client(socket);
    }
}
