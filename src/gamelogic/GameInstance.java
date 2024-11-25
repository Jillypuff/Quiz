package gamelogic;

import Modules.*;
import server.ConnectedClient;

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

    ConnectedClient player1;
    ConnectedClient player2;

    public GameInstance(ConnectedClient player1, ConnectedClient player2) {
        availableCategories = new ArrayList<>(List.of(Category.values()));
        properties = new Properties();
        loadProperties();
        this.player1 = player1;
        this.player2 = player2;
        amountOfQuestions = Integer.parseInt(properties.getProperty("amountOfQuestions"));
        amountOfRounds = Integer.parseInt(properties.getProperty("amountOfRounds"));
        try{
            this.player1.sendResponse(new Response(ResponseType.GAME_STARTED, this.amountOfRounds, true));
            this.player2.sendResponse(new Response(ResponseType.GAME_STARTED, this.amountOfRounds, false));
        } catch (Exception e){
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

    public List<Question> createListOfQuestions(){
        List<Question> questions = QuestionDatabase.getQuestionsFromCategory(currentCategory);
        Collections.shuffle(questions);
        return questions;
    }


    public void loadProperties(){
        try {
            this.properties.store(new FileWriter("src/Game_properties.properties"), "Game Properties");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        this.amountOfQuestions = Integer.parseInt(this.properties.getProperty("amountOfQuestions", "2"));
        this.amountOfRounds = Integer.parseInt(this.properties.getProperty("amountOfRounds", "2"));
    }
}
