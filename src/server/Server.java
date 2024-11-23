package server;

import gamelogic.Category;
import gamelogic.CurrentGame;
import gamelogic.Question;
import server.response.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    final private ServerSocket serverSocket;
    List<ConnectedClient> queue;
    GameManager gameManager;
    ServerProtocol protocol;

    Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.gameManager = new GameManager();
        this.protocol = new ServerProtocol(gameManager);
    }

    public void startServer() {
        queue = new ArrayList<>();
        System.out.println("Server started");
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ConnectedClient client = new ConnectedClient(socket);
                new Thread(client).start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public void handleStartGame(ConnectedClient client) throws IOException {
        gameManager.startGame(client, this);
    }

    public void handleRoundFinished(GameInstance instance, ConnectedClient client) throws IOException {
        gameManager.handleRoundFinished(instance, client);
    }

    public void sendNewRound(GameInstance instance) throws IOException {
        gameManager.sendNewRound(instance);
    }

    public void handleTurnSwitch(GameInstance instance) throws IOException {
        gameManager.handleTurnSwitch(instance);
    }

    public void sendFinalGameResult(GameInstance instance) throws IOException {
        int playerOneFinalScore = instance.playerOne.getScore();
        int playerTwoFinalScore = instance.playerTwo.getScore();

        instance.playerOne.sendResponse(new ResultResponse(ResponseType.GAME_OVER, playerOneFinalScore, playerTwoFinalScore));
        instance.playerOne.sendResponse(new ResultResponse(ResponseType.GAME_OVER, playerTwoFinalScore, playerOneFinalScore));
    }

    public void sendRoundResult(GameInstance instance) throws IOException {
        int playerOneScore = instance.playerOne.getScore();
        int playerTwoScore = instance.playerTwo.getScore();

        instance.playerOne.sendResponse(new ResultResponse(ResponseType.ROUND_RESULT, playerOneScore, playerTwoScore));
        instance.playerTwo.sendResponse(new ResultResponse(ResponseType.ROUND_RESULT, playerTwoScore, playerOneScore));
    }


    private void notifyOtherPlayersTurn(ConnectedClient client) throws IOException {
        client.sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));
    }

    private void sendTurnPackage(ConnectedClient client, Category category, List<Question> questions) throws IOException {
        client.sendResponse(new QuestionPackageResponse(ResponseType.QUESTIONS, category, questions));
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