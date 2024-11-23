package server;

import client.request.StartRoundRequest;
import gamelogic.Category;
import gamelogic.CurrentGame;
import gamelogic.Question;
import server.response.*;

import java.io.IOException;
import java.util.List;

public class GameManager {

    Server server;

    public GameManager(Server server) {
        this.server = server;
    }

    public void handleLeaveQueue(ConnectedClient client) {
        server.queue.remove(client);

    }

    public void handleStartGame(ConnectedClient client) throws IOException {
        server.queue.add(client);
        client.sendResponse(new Response(ResponseType.QUEUE_JOINED));

        if(server.queue.size() >= 2){
            ConnectedClient turnHolder = server.queue.removeFirst();
            ConnectedClient waitingPlayer = server.queue.removeFirst();

            GameInstance instance = new GameInstance(turnHolder, waitingPlayer);

            turnHolder.gameInstance = instance;
            waitingPlayer.gameInstance = instance;

            // Hämtar ett set av slumpade kategorier
            List<Category> setOfCategories = instance.game.getSetOfCategories();

            waitingPlayer.sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));
            turnHolder.sendResponse(new CategoryPackageResponse(ResponseType.CATEGORIES, setOfCategories));
        }
    }
    public void handleCategoryChosen(ConnectedClient client, Category chosenCategory) throws IOException {
        client.gameInstance.game.setCurrentCategory(chosenCategory);
        client.gameInstance.game.loadCurrentSetOfQuestions();

        System.out.println("Sending questions to turn holder: " + client.getUsername());
        client.sendResponse(new QuestionPackageResponse(ResponseType.QUESTIONS, chosenCategory, client.gameInstance.game.getCurrentSetOfQuestions()));
    }

    public void handleRoundFinished(GameInstance instance, ConnectedClient client) throws IOException {
        instance.setPlayerFinishedRound(client);

        // Måste ändra att den kollar both players finished round, sen om alla runder är finished,
        // i så fall skicka endresult, annars skicka round result
        //  innan den skickar round result behöver den kunna skicka switch round om inte both players finished
        if(instance.bothPlayersFinishedRound()){

            // Skicka rundans resultat
            System.out.println("Both players finished round");

            // Ökar roundsfinished, när det kommit till 3 bör spelet avlsutas
            instance.roundsFinished++;

            sendRoundResult(instance);
            instance.resetRoundState();

            try{
                Thread.sleep(5000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

            // Kollar om alla rundor är klara
            if (instance.allRoundsFinished()){
                System.out.println("All rounds finished");
                instance.endGameAndDetermineWinner();
                //Skickar då slutgiltigt resultat
                sendFinalGameResult(instance);
            }
            else{
                System.out.println("Fortsätter till nästa runda");
                // Annars byter vi plats på vems tur det är
                ConnectedClient waitingPlayer = instance.turnHolder.equals(instance.playerOne) ? instance.playerTwo : instance.playerOne;
                System.out.println("Väntande spelare: " + waitingPlayer.getUsername());
                waitingPlayer.sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));

                // Skickar nya kategorier till nuvarande turn holder
                List<Category> newSetOfCategories = instance.game.getSetOfCategories();
                System.out.println("Spelare som får välja nya kategorier: " + instance.turnHolder.getUsername());
                instance.turnHolder.sendResponse(new CategoryPackageResponse(ResponseType.CATEGORIES, newSetOfCategories));
            }
        }
        else{
            System.out.println("Hanterar att byta turn");
            handleTurnSwitch(instance);
        }
    }

    public void sendNewRound(GameInstance instance) throws IOException {
        if (!instance.allRoundsFinished()) {
            ConnectedClient waitingPlayer = instance.turnHolder.equals(instance.playerOne) ? instance.playerTwo : instance.playerOne;
            ConnectedClient turnHolder = instance.turnHolder;

            List<Category> newSetOfCategories = instance.game.getSetOfCategories();
            turnHolder.sendResponse(new CategoryPackageResponse(ResponseType.CATEGORIES, newSetOfCategories));
            waitingPlayer.sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));

        } else {
            System.out.println("All rounds finished");
            instance.endGameAndDetermineWinner();
            sendFinalGameResult(instance);
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
        instance.playerTwo.sendResponse(new ResultResponse(ResponseType.GAME_OVER, playerTwoFinalScore, playerOneFinalScore));
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
}
