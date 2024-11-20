package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//SERVERN LIGGER OCH TAR EMOT ANSLUTNINGAR; PLUS HANTERAR OPERATIONERNA PÅ DESSA
public class Server {

    final private ServerSocket serverSocket;
    List<ConnectedClient> queue;
    //Lägger till en ServerResponseDispatcher
    ServerResponseDispatcher responseDispatcher;

    Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.responseDispatcher = new ServerResponseDispatcher(this);
    }

    public void startServer() {
        queue = new ArrayList<>();
        System.out.println("Server started");
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ConnectedClient client = new ConnectedClient(socket, responseDispatcher);
                new Thread(client).start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    //ny metod
    public void handleConnect(Event event) throws IOException {
        ConnectedClient client = event.getClient();
        client.setUsername(event.getRequest().getUsername());
        client.sendResponse(new Response(ServerResponse.CLIENT_CONNECTED));
        System.out.println("User connected: " + client.getUsername());
    }

    //bytte namn från createInstance till handleStartGame
    public void handleStartGame(Event event) throws IOException {
        queue.add(event.getClient());
        if(queue.size() >= 2){
            ConnectedClient player1 = queue.removeFirst();
            ConnectedClient player2 = queue.removeFirst();
            System.out.println(player1.getUsername() + " " + player2.getUsername());
            GameInstance instance = new GameInstance(player1, player2);
            broadcastInstance(instance);
        }
    }

    public void handleDisconnect(Event event) throws IOException {
        ConnectedClient client = event.getClient();
        client.sendResponse(new Response(ServerResponse.CLIENT_DISCONNECTED));
        client.closeEverything(client.getSocket(), client.getOut(), client.getIn());
    }

    public void broadcastInstance(GameInstance instance) throws IOException {
        instance.getPlayerOne().sendResponse(new Response(ServerResponse.GAME_JOINED, instance.getGame()));
        instance.getPlayerTwo().sendResponse(new Response(ServerResponse.GAME_JOINED, instance.getGame()));
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
