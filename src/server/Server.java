package server;

import Modules.Category;
import Modules.QuestionInPanel;
import Modules.ReponseType;
import Modules.Response;
import gamelogic.GameInstance;
import gamelogic.Question;

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


            GameInstance instance = new GameInstance(player1, player2);
//            QuestionInPanel[][] gameBoard = instance.getGameBoard();//***************************************************************
//
//            List<Category> categories = instance.randomizeCategories();
//
//            QuestionInPanel questionInPanel = new QuestionInPanel();
//            gameBoard[0][0] = questionInPanel;
//            gameBoard[0][0].setRandomCategoryChoices(categories);
//
//            gameBoard[0][0].setPlayer1(player1.username);
//            gameBoard[0][0].setPlayer2(player2.username);

            System.out.println("Players found: " + player1.username + " vs " + player2.username);
            player2.sendResponse(new Response(ReponseType.GAME, instance.getGameBoard()));
            player1.sendResponse(new Response(ReponseType.GAME, instance.getGameBoard()));
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

