package server;

import client.Request;
import gameLogic.GameInstance;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ConnectedClient implements Runnable {

    public Server server;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    public String username;
    static List<ConnectedClient> connectedClients = new ArrayList<>();
    static List<ConnectedClient> queuedClients = new ArrayList<>();
    gameLogic currentGame;

    public ConnectedClient(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        connectedClients.add(this);
    }

    public void queueClient(ConnectedClient client){
        queuedClients.add(client);
        if (queuedClients.size() >= 2){
            ConnectedClient player1 = queuedClients.removeFirst();
            ConnectedClient player2 = queuedClients.removeFirst();

            GameInstance gameInstance = new GameInstance(player1, player2);
        }
    }

    public synchronized void sendResponse(Response response) throws IOException {
        out.writeObject(response);
        out.flush();
    }

    @Override
    public void run() {
        try {
            System.out.println("Connected");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            ServerProtocol protocol = new ServerProtocol();

            while(in.readObject() instanceof Request request){
                protocol.processRequest(request, this);
            }

        } catch (Exception e) {
            closeEverything(socket, out, in);
        }
    }

    public void closeEverything(Socket socket, ObjectOutputStream out, ObjectInputStream in){
        try{
            if (out != null){
                out.close();
            }
            if (in != null){
                in.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}