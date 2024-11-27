package gamelogic;

import Modules.*;
import server.ConnectedClient;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class GameInstance {

    private int amountOfQuestions;
    private int amountOfRounds;
    private Properties properties;
    private int round = 1;
    protected List<Category> availableCategories;
    private Category currentCategory;
    int activePlayer = 1;
    private int player1Score = 0;
    private int player2Score = 0;
    private int player1TotalScore = 0;
    private int palyer1RoundScore = 0;
    private int player2TotalScore = 0;
    private int palyer2RoundScore = 0;

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
            player1.readyForNewRound(false);
            player2.readyForNewRound(false);
            QuestionPackage questionPackage = createQuestionPackage();
            player1.sendResponse(new Response(ResponseType.GAME_STARTED, questionPackage));
            player2.sendResponse(new Response(ResponseType.GAME_STARTED, questionPackage));
        } catch (IOException e) {
            player1.closeEverything();
            player2.closeEverything();
        }
    }

    public void sendUpdatedScore(ConnectedClient client){
        client.readyForNewRound(true);
        try{
            if (player1.isReadyForNewRound() && player2.isReadyForNewRound()){
                player1.sendResponse(new Response(ResponseType.SEND_SCORE, player1Score,player2Score));
                player2.sendResponse(new Response(ResponseType.SEND_SCORE, player2Score,player1Score));
                client.readyForNewRound(false);
                client.readyForNewRound(false);
            }
        }catch (IOException e){
            player1.closeEverything();
            player2.closeEverything();
        }
    }

    public void sendRoundResult(){
        // Metod som Joakim kan fixa, skickar rundans poängresultat för båda klienterna, till båda klienterna
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

    public int getPlayer1Score(){
        return player1Score;
    }
    public void setPlayer1Score(int player1Score){
        this.player1Score = player1Score;
    }
    public void uppdatePlayer1Score(int player1Score){
        this.player1Score = player1Score;
        this.player1TotalScore += player1Score;
    }

    public int getPlayer2Score(){
        return player2Score;
    }
    public void setPlayer2Score(int player2Score){
        this.player2Score = player2Score;
    }
    public void uppdatePlayer2Score(int player2Score){
        this.player2Score = player2Score;
        this.player2TotalScore += player2Score;
    }

    public int getAmountOfRounds() {
        return amountOfRounds;
    }

    public int getPalyer1RoundScore() {
        return palyer1RoundScore;
    }

    public void setPalyer1RoundScore(int palyer1RoundScore) {
        this.palyer1RoundScore = palyer1RoundScore;
    }

    public int getPalyer2RoundScore() {
        return palyer2RoundScore;
    }

    public void setPalyer2RoundScore(int palyer2RoundScore) {
        this.palyer2RoundScore = palyer2RoundScore;
    }
}
