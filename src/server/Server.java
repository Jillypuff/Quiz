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
            player1.sendResponse(new CategoryPackageResponse(ResponseType.CATEGORIES, game.getCurrentSetOfCategories()));
        }
    }

    public void handleRoundFinished(GameInstance instance, ConnectedClient client) throws IOException {
        instance.setPlayerFinishedRound(client);
        if(instance.bothPlayersFinishedRound()){
            instance.roundsFinished++;
            sendRoundResult(instance);
            instance.resetRoundState();
            if (instance.allRoundsFinished()){
                instance.endGameAndDetermineWinner();
                sendFinalGameResult(instance);
            }
        }
        else{
            handleTurnSwitch(instance);
        }
    }

    public void handleTurnSwitch(GameInstance instance) throws IOException {
        if (instance.turnHolder.equals(instance.playerOne)) {

            instance.turnHolder = instance.playerTwo;
            notifyOtherPlayersTurn(instance.playerOne);
            sendTurnPackage(instance.playerTwo, instance.game.getCurrentCategory(), instance.game.getCurrentSetOfQuestions());
        }
        else if (instance.turnHolder.equals(instance.playerTwo)) {

            instance.turnHolder = instance.playerOne;
            notifyOtherPlayersTurn(instance.playerTwo);
            sendTurnPackage(instance.playerOne, instance.game.getCurrentCategory(), instance.game.getCurrentSetOfQuestions());
        }
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
