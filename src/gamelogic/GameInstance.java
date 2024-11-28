package gamelogic;

import Modules.*;
import server.ConnectedClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class GameInstance {

    private int amountOfQuestions;
    private int amountOfRounds;
    private Properties properties;
    protected List<Category> availableCategories;
    private Category currentCategory;
    private final List<Integer> player1Scores = new ArrayList<>();
    private final List<Integer> player2Scores = new ArrayList<>();

    public ConnectedClient player1;
    public ConnectedClient player2;
    // La till en aktiv spelare
    public ConnectedClient activeClient;


    public GameInstance(ConnectedClient player1, ConnectedClient player2) {
        availableCategories = new ArrayList<>(List.of(Category.values()));
        properties = new Properties();
        loadProperties();
        this.player1 = player1;
        this.player2 = player2;
        // La till att player1 sätts till aktiv spelare först
        activeClient = player1;
        amountOfQuestions = Integer.parseInt(properties.getProperty("amountOfQuestions"));
        amountOfRounds = Integer.parseInt(properties.getProperty("amountOfRounds"));
        try{
            player1.sendResponse(new Response(ResponseType.GAME_JOINED, amountOfRounds, true));
            player2.sendResponse(new Response(ResponseType.GAME_JOINED, amountOfRounds, false));
        } catch (Exception e){
            player1.closeEverything();
            player2.closeEverything();
        }
        sendRandomizedCategories();
    }

    // La till metod för att byta aktiv spelare
    public void switchActiveClient() {
        activeClient = (activeClient == player1) ? player2 : player1;
    }

    // Ändrar att titta vilken connectedclient som är aktiv
    public void sendRandomizedCategories(){
        // tittar först om båda är redo för new round
        if (player1.isReadyForNewRound() && player2.isReadyForNewRound()){
            try{
                if (activeClient == player1){
                    player1.sendResponse(new Response(ResponseType.CHOSE_CATEGORY, randomizeCategories()));
                    player2.sendResponse(new Response(ResponseType.WAITING_FOR_CATEGORY_CHOICE));
                } else {
                    player2.sendResponse(new Response(ResponseType.CHOSE_CATEGORY, randomizeCategories()));
                    player1.sendResponse(new Response(ResponseType.WAITING_FOR_CATEGORY_CHOICE));
                }
                // Byter vems tur det är tills nästa runda
                switchActiveClient();

            } catch (Exception e){
                player1.closeEverything();
                player2.closeEverything();
                System.err.println("It's broke yo");
            }
        }
    }

    public void sendQuestionPackage(){
        try {
            // sätter att dom ej är redo för ny runda
            notReadyForNewRound();
            QuestionPackage questionPackage = createQuestionPackage();
            player1.sendResponse(new Response(ResponseType.GAME_STARTED, questionPackage));
            player2.sendResponse(new Response(ResponseType.GAME_STARTED, questionPackage));
        } catch (IOException e) {
            player1.closeEverything();
            player2.closeEverything();
        }
    }

    public void sendRoundScore(ConnectedClient client){
        client.readyForNewRound(true);
        try{
            if (player1.isReadyForNewRound() && player2.isReadyForNewRound()){
                player1.sendResponse(new Response
                        (ResponseType.SEND_SCORE, player1Scores.getLast(), player2Scores.getLast()));
                player2.sendResponse(new Response
                        (ResponseType.SEND_SCORE, player2Scores.getLast(), player1Scores.getLast()));
                notReadyForNewRound();
            }
        }catch (IOException e){
            player1.closeEverything();
            player2.closeEverything();
        }
    }

    public void notReadyForNewRound(){
        player1.readyForNewRound(false);
        player2.readyForNewRound(false);
    }

    public void sendFinalResults(ConnectedClient client){
        client.readyForNewRound(true);
        System.out.println("Are they ready?");
        try {
            if (player1.isReadyForNewRound() && player2.isReadyForNewRound()){
                System.out.println("Sending final score to both players");
                int player1TotalScore = getTotalValueFromList(player1Scores);
                int player2TotalScore = getTotalValueFromList(player2Scores);
                player1.sendResponse(new Response(ResponseType.SEND_FINAL_RESULT, player1TotalScore, player2TotalScore));
                player2.sendResponse(new Response(ResponseType.SEND_FINAL_RESULT, player2TotalScore, player1TotalScore));
            }
        } catch (IOException e){
            player1.closeEverything();
            player2.closeEverything();
        }
    }

    public void sendGameOver(ConnectedClient client) throws IOException {
        if (client.username.equals(player1.username)){
            player2.sendResponse(new Response(ResponseType.GAME_OVER));
        }
        else{
            player1.sendResponse(new Response(ResponseType.GAME_OVER));
        }
    }

    public int getTotalValueFromList(List<Integer> list){
        return list.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public List<Category> randomizeCategories(){
        Collections.shuffle(availableCategories);
        List<Category> setOfCategories = availableCategories.subList(0,3);
        return new ArrayList<>(setOfCategories);
    }

    public QuestionPackage createQuestionPackage(){
        return new QuestionPackage(createListOfQuestions(), this.amountOfQuestions);
    }

    public void categoryChosen(Category category){
        currentCategory = category;
        sendQuestionPackage();
    }

    public List<Question> createListOfQuestions(){
        List<Question> questions = QuestionDatabase.getQuestionsFromCategory(currentCategory);
        Collections.shuffle(questions);
        List<Question> sublist = questions.subList(0,amountOfQuestions);
        return new ArrayList<>(sublist);
    }

    public void loadProperties(){
        try {
            this.properties.load(new FileInputStream("src/gamelogic/Game_Properties.properties"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        this.amountOfQuestions = Integer.parseInt(this.properties.getProperty("amountOfQuestions", "2"));
        this.amountOfRounds = Integer.parseInt(this.properties.getProperty("amountOfRounds", "2"));
    }

    public void updatePlayer1Score(int score){
        player1Scores.add(score);
    }

    public void updatePlayer2Score(int score){
        player2Scores.add(score);
    }
}
