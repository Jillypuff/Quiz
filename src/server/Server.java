package server;

import gamelogic.Category;
import gamelogic.CurrentGame;
import gamelogic.Question;
import server.response.CategoryPackageResponse;
import server.response.QuestionPackageResponse;
import server.response.ResponseType;
import server.response.Response;

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
        client.sendResponse(new Response(ResponseType.QUEUE_JOINED));
        if(queue.size() >= 2){
            ConnectedClient player1 = queue.removeFirst();
            ConnectedClient player2 = queue.removeFirst();

            GameInstance instance = new GameInstance(player1, player2);

            player1.gameInstance = instance;
            player2.gameInstance = instance;

            CurrentGame game = instance.game;

            player2.sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));
            player1.sendResponse(new CategoryPackageResponse(ResponseType.YOUR_TURN, game.getCurrentSetOfCategories()));
        }
    }

    public void handleRoundSwitch(GameInstance instance) throws IOException {
        List<Question> questions = instance.game.getQuestionsForCurrentCategory();
        Category currentCategory = instance.game.getCurrentCategory();

        if (instance.game.turnHolder.equals(instance.playerOne.username)) {

            instance.game.turnHolder = instance.playerTwo.username;
            notifyOtherPlayersTurn(instance.playerOne);
            sendTurnPackage(instance.playerTwo, currentCategory, questions);
        }
        else if (instance.game.turnHolder.equals(instance.playerTwo.username)) {

            instance.game.turnHolder = instance.playerOne.username;
            notifyOtherPlayersTurn(instance.playerTwo);
            sendTurnPackage(instance.playerOne, currentCategory, questions);
        }
    }

    private void notifyOtherPlayersTurn(ConnectedClient client) throws IOException {
        client.sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));
    }

    private void sendTurnPackage(ConnectedClient client, Category category, List<Question> questions) throws IOException {
        client.sendResponse(new QuestionPackageResponse(ResponseType.YOUR_TURN, category, questions));
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
