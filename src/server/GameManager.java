package server;

import gamelogic.Category;
import gamelogic.GameInstance;
import gamelogic.Player;
import gamelogic.Question;
import server.response.*;

import java.io.IOException;
import java.util.List;

public class GameManager {

    private final ClientQueueManager clientQueueManager;

    public GameManager(ClientQueueManager clientQueueManager) {
        this.clientQueueManager = clientQueueManager;
    }

    public void handleConnectRequest(ConnectedClient client, String selectedUsername) throws IOException {
        client.setUsername(selectedUsername);
        client.sendResponse(new Response(ResponseType.CLIENT_CONNECTED));
    }

    public void handleDisconnectRequest(ConnectedClient client) {

    }

    public void handleStartGameRequest(ConnectedClient client) throws IOException {
        clientQueueManager.putInQueue(client);
        client.sendResponse(new Response(ResponseType.QUEUE_JOINED));
        createGameInstanceIfReady();
    }

    public void handleLeaveQueue(ConnectedClient client) throws IOException {
        clientQueueManager.removeFromQueue(client);
    }

    public void createGameInstanceIfReady() throws IOException {
        if (clientQueueManager.getQueue().size() >= 2) {

            ConnectedClient clientOne = clientQueueManager.takeFromQueue();
            ConnectedClient clientTwo = clientQueueManager.takeFromQueue();

            GameInstance instance = new GameInstance();
            instance.setPlayers(clientOne, clientTwo);

            clientOne.setInstance(instance);
            clientTwo.setInstance(instance);

            startRound(instance);
        }
    }

    public void startRound(GameInstance instance) throws IOException {
        instance.getQuizPackage().loadSetOfCategories();
        List<Category> setOfCategories = instance.getQuizPackage().getCurrentSetOfCategories();

        Player currentPlayer = instance.getCurrentTurnHolder();
        Player opponent = instance.getOpponent(currentPlayer);

        currentPlayer.getConnection().sendResponse(new CategoryPackageResponse(ResponseType.ROUND_STARTED, currentPlayer.getUsername(), opponent.getUsername(), setOfCategories));
        opponent.getConnection().sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));

    }

    public void handleCategoryChosen(ConnectedClient client, Category category) throws IOException {
        GameInstance instance = client.instance;
        instance.getQuizPackage().setCurrentCategory(category);

        instance.getQuizPackage().loadCurrentSetOfQuestions();
        List<Question> questions = instance.getQuizPackage().getCurrentSetOfQuestions();
        sendQuestions(instance, questions);
    }

    public void handleRoundFinished(ConnectedClient client, int clientScore) throws IOException {
        GameInstance instance = client.instance;
        Player currentPlayer = instance.getCurrentTurnHolder();

        currentPlayer.setRoundScore(clientScore);
        currentPlayer.addToTotalScore(clientScore);

        currentPlayer.setHasFinishedRound(true);

        if (instance.bothPlayersFinishedRound()) {
            System.out.println("Both players finished round");
            instance.incrementRoundsFinished();
            sendRoundResult(instance);

            instance.resetRound();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (instance.isGameOver()) {
                sendFinalResult(instance);
            } else {
                startRound(instance);
            }
        } else {
            instance.switchTurn();
            List<Question> currentQuestions = instance.getQuizPackage().getCurrentSetOfQuestions();
            sendQuestions(instance, currentQuestions);
        }
    }

    public void sendQuestions(GameInstance instance, List<Question> questions) throws IOException {
        Player currentPlayer = instance.getCurrentTurnHolder();
        Player opponent = instance.getOpponent(currentPlayer);

        currentPlayer.getConnection().sendResponse(new QuestionPackageResponse(ResponseType.QUESTIONS, currentPlayer.getUsername(), opponent.getUsername(), questions));
        opponent.getConnection().sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));
    }

    public void sendRoundResult(GameInstance instance) throws IOException {
        Player currentPlayer = instance.getCurrentTurnHolder();
        Player opponent = instance.getOpponent(currentPlayer);

        currentPlayer.getConnection().sendResponse(new ResultPackageResponse(ResponseType.ROUND_RESULT, currentPlayer.getRoundScore(), opponent.getRoundScore()));
        opponent.getConnection().sendResponse(new ResultPackageResponse(ResponseType.ROUND_RESULT, opponent.getRoundScore(), currentPlayer.getRoundScore()));
    }

    public void sendFinalResult(GameInstance instance) throws IOException {
        Player currentPlayer = instance.getCurrentTurnHolder();
        Player opponent = instance.getOpponent(currentPlayer);

        currentPlayer.getConnection().sendResponse(new ResultPackageResponse(ResponseType.FINAL_RESULT, currentPlayer.getTotalScore(), opponent.getTotalScore()));
        opponent.getConnection().sendResponse(new ResultPackageResponse(ResponseType.FINAL_RESULT, opponent.getTotalScore(), currentPlayer.getTotalScore()));
    }
}
