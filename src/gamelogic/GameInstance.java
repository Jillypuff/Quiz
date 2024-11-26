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

    public ConnectedClient player1;
    public ConnectedClient player2;

    public GameInstance(ConnectedClient player1, ConnectedClient player2) {
        availableCategories = new ArrayList<>(List.of(Category.values()));
        properties = new Properties();
        loadProperties();
        this.player1 = player1;
        this.player2 = player2;
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

    public void sendRandomizedCategories(){
        try{
            if (activePlayer == 1){
                player1.sendResponse(new Response(ResponseType.CHOSE_CATEGORY, randomizeCategories()));
                player2.sendResponse(new Response(ResponseType.WAITING_FOR_CATEGORY_CHOICE));
            } else {
                player2.sendResponse(new Response(ResponseType.CHOSE_CATEGORY, randomizeCategories()));
                player1.sendResponse(new Response(ResponseType.WAITING_FOR_CATEGORY_CHOICE));
            }
        } catch (Exception e){
            player1.closeEverything();
            player2.closeEverything();
            System.err.println("It's broke yo");
        }
    }

    public void sendQuestionPackage(){
        try {
            QuestionPackage questionPackage = createQuestionPackage();
            player1.sendResponse(new Response(ResponseType.GAME_STARTED, questionPackage));
            player2.sendResponse(new Response(ResponseType.GAME_STARTED, questionPackage));
        } catch (IOException e) {
            player1.closeEverything();
            player2.closeEverything();
        }
    }

    public void sendUpdatedScore(){
        try{
            player1.sendResponse(new Response(ResponseType.SEND_SCORE, player1Score,player2Score));
            player2.sendResponse(new Response(ResponseType.SEND_SCORE, player2Score,player1Score));
        }catch (IOException e){
            player1.closeEverything();
            player2.closeEverything();
        }
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
        this.player1Score = this.player1Score+player1Score;
    }

    public int getPlayer2Score(){
        return player2Score;
    }
    public void setPlayer2Score(int player2Score){
        this.player2Score = player2Score;
    }
    public void uppdatePlayer2Score(int player2Score){
        this.player2Score = this.player2Score+player2Score;
    }

    public int getAmountOfRounds() {
        return amountOfRounds;
    }
}
