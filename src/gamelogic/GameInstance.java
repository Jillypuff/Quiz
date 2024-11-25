package gamelogic;

import Modules.Category;
import Modules.QuestionInPanel;
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
    private QuestionInPanel[][] Spelbrade;
    private int round;
    protected List<Category> availableCategories;

    ConnectedClient spelare1;
    ConnectedClient spelare2;

    public GameInstance(ConnectedClient spelare1, ConnectedClient spelare2) {
        availableCategories = new ArrayList<>(List.of(Category.values()));
        properties = new Properties();
        loadProperties();

        Spelbrade = new QuestionInPanel[amountOfQuestions][amountOfRounds];
        //spelare2Spelbrade = new QuestionInPannel[antalFragor][antalKategoriSet];
        //gameState = new int[(antalFragor*2)][antalKategoriSet];
    }

    GameInstance(){
        properties = new Properties();
        loadProperties();
    }

    // Används för att setta kategorilistan i questioninpanel objektet
    public List<Category> randomizeCategories(){
        Collections.shuffle(availableCategories);
        List<Category> setOfCategories = availableCategories.subList(0,3);
        return new ArrayList<>(setOfCategories);
    }

    //metod för att läsa in sparad sessionsid
    //metod för att uppdatera sessionsid tar int (nuvarande sessions id)

    //konstruktor som tar int (sesionsid)



    public void loadProperties(){
        try {
            this.properties.store(new FileWriter("src/gamelogic/Game_Properties.properties"), "Game Properties");
        }
        catch(IOException e){
            e.printStackTrace();
            //***************************************************************************************************************************lägg till sout kommentar
        }
        this.amountOfQuestions = Integer.parseInt(this.properties.getProperty("amountOfQuestions", "2"));
        this.amountOfRounds = Integer.parseInt(this.properties.getProperty("amountOfRounds", "2"));
    }

    public QuestionInPanel[][] getSpelbrade() {
        return Spelbrade;
    }
}
