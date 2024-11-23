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
            ConnectedClient turnHolder = queue.removeFirst();
            ConnectedClient waitingPlayer = queue.removeFirst();

            GameInstance instance = new GameInstance(turnHolder, waitingPlayer);

            turnHolder.gameInstance = instance;
            waitingPlayer.gameInstance = instance;

            CurrentGame game = instance.game;

            waitingPlayer.sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));
            turnHolder.sendResponse(new CategoryPackageResponse(ResponseType.CATEGORIES, game.getSetOfCategories()));
        }
    }

    public void handleRoundFinished(GameInstance instance, ConnectedClient client) throws IOException {
        instance.setPlayerFinishedRound(client);

        if(instance.bothPlayersFinishedRound()){

            // Skicka rundans resultat
            System.out.println("Both players finished round");
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
                // Annars byter vi plats på vems tur det är
                instance.changeTurnHolder();
                ConnectedClient waitingPlayer = instance.turnHolder.equals(instance.playerOne) ?
                        instance.playerTwo : instance.playerOne;

                // Skickar nya kategorier till nuvarande turn holder
                List<Category> newSetOfCategories = instance.game.getSetOfCategories();
                instance.turnHolder.sendResponse(new CategoryPackageResponse(ResponseType.CATEGORIES, newSetOfCategories));

                // Andra personen får vänta
                waitingPlayer.sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));
            }
        }
        else{
            handleTurnSwitch(instance);
        }
    }

    public void sendNewRound(GameInstance instance) throws IOException {
        if (!instance.allRoundsFinished()) {

//            ConnectedClient waitingPlayer = instance.turnHolder;
//            System.out.println(waitingPlayer.getUsername());
//            instance.changeTurnHolder();
//            ConnectedClient turnHolder = instance.turnHolder;
//            System.out.println(turnHolder.getUsername());

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
        ConnectedClient currentPlayer = instance.turnHolder;
        ConnectedClient nextPlayer;

        if (currentPlayer.equals(instance.playerOne)) {
            nextPlayer = instance.playerTwo;
        } else {
            nextPlayer = instance.playerOne;
        }

        instance.turnHolder = nextPlayer;
        currentPlayer.sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));
        sendTurnPackage(nextPlayer, instance.game.getCurrentCategory(), instance.game.getCurrentSetOfQuestions());




//        if (instance.turnHolder.equals(instance.playerOne)) {
//
//            instance.turnHolder = instance.playerTwo;
//            notifyOtherPlayersTurn(instance.playerOne);
//            sendTurnPackage(instance.playerTwo, instance.game.getCurrentCategory(), instance.game.getCurrentSetOfQuestions());
//        }
//        else if (instance.turnHolder.equals(instance.playerTwo)) {
//
//            instance.turnHolder = instance.playerOne;
//            notifyOtherPlayersTurn(instance.playerTwo);
//            sendTurnPackage(instance.playerOne, instance.game.getCurrentCategory(), instance.game.getCurrentSetOfQuestions());
//        }
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


//public void handleRoundFinished(GameInstance instance, ConnectedClient client) throws IOException {
//    instance.setPlayerFinishedRound(client);
//    if(instance.bothPlayersFinishedRound()){
//        System.out.println("Both players finished round");
//        instance.roundsFinished++;
//        sendRoundResult(instance);
//        instance.resetRoundState();
//        try{
//            Thread.sleep(5000);
//        }
//        catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        if (instance.allRoundsFinished()){
//            System.out.println("All rounds finished");
//            instance.endGameAndDetermineWinner();
//            sendFinalGameResult(instance);
//        }
//        else{
//            instance.changeTurnHolder();
//            ConnectedClient waitingPlayer = instance.turnHolder.equals(instance.playerOne) ?
//                    instance.playerTwo : instance.playerOne;
//
//            // Send new categories to new turn holder
//            List<Category> newSetOfCategories = instance.game.getSetOfCategories();
//            instance.turnHolder.sendResponse(new CategoryPackageResponse(ResponseType.CATEGORIES, newSetOfCategories));
//
//            // Tell other player to wait
//            waitingPlayer.sendResponse(new Response(ResponseType.OTHER_PLAYERS_TURN));
//        }
//
//
//
//
////            sendNewRound(instance);
////
////            if (instance.allRoundsFinished()){
////                System.out.println("All rounds finished");
////                instance.endGameAndDetermineWinner();
////                sendFinalGameResult(instance);
////            }
//    }
//    else{
//        handleTurnSwitch(instance);
//    }
//}
