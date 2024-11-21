package server;

import gamelogic.CurrentGame;

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
        queue.add(client);
        System.out.println("Sending queue joined response to " + client.username);
        client.sendResponse(new Response(ReponseType.QUEUE_JOINED));
        if(queue.size() >= 2){
            System.out.println("Queue as two players, attempting to create instance");
            ConnectedClient player1 = queue.removeFirst();
            ConnectedClient player2 = queue.removeFirst();

            System.out.println("Players found: " + player1.username + " " + player2.username);
            GameInstance instance = new GameInstance(player1, player2);

            player1.currentGame = instance.game;
            player2.currentGame = instance.game;

            System.out.println("Trying to update current set of categories");
            instance.game.updateCurrentSetOfCategories();
            System.out.println("Fetching current game from instance");
            CurrentGame game = instance.game;

            System.out.println("Sending other-players-turn to " + player2.username);
            player2.sendResponse(new Response(ReponseType.OTHER_PLAYERS_TURN));

            System.out.println("Sending your-turn to " + player1.username);
            player1.sendResponse(new Response(ReponseType.YOUR_TURN, game.getCurrentSetOfCategories()));
        }
    }

    public void handleTurnSwitch(ConnectedClient client) throws IOException {

    }

//    public void broadcastInstance(GameInstance instance) throws IOException {
//        instance.playerOne.sendResponse(new Response(ReponseType.GAME_JOINED, instance.game));
//        instance.playerTwo.sendResponse(new Response(ReponseType.GAME_JOINED, instance.game));
//    }

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
