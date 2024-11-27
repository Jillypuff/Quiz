package server;

import Modules.ResponseType;
import Modules.Response;
import gamelogic.GameInstance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    final private ServerSocket serverSocket;
    List<ConnectedClient> queue;

    Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        queue = new ArrayList<>();
        System.out.println("Server started");
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ConnectedClient client = new ConnectedClient(socket, this);
                new Thread(client).start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public void handleStartGame(ConnectedClient client) throws IOException {
        addToQueue(client);
        System.out.println("Sending queue joined response to " + client.username);
        client.sendResponse(new Response(ResponseType.QUEUE_JOINED));
        if(queue.size() >= 2){
            System.out.println("Queue as two players, attempting to create instance");
            ConnectedClient player1 = queue.removeFirst();
            ConnectedClient player2 = queue.removeFirst();

            System.out.println("Players found: " + player1.username + " vs " + player2.username);
            GameInstance instance = new GameInstance(player1, player2);
            player1.instance = instance;
            player2.instance = instance;
        }
    }

    public void addToQueue(ConnectedClient client){
        if(!queue.contains(client)){
            queue.add(client);
        }
    }

    public void closeServerSocket() {
        try  {
            if (serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Server socket closed.");
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(55554);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}

