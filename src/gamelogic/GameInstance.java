package gamelogic;

import Modules.Category;
import Modules.QuestionInPanel;
import server.ConnectedClient;

import java.io.FileInputStream;
import java.io.FileReader;
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
    private QuestionInPanel[][] gameBoard;
    private int round;
    protected List<Category> availableCategories;

    ConnectedClient spelare1;
    ConnectedClient spelare2;

    public GameInstance(ConnectedClient spelare1, ConnectedClient spelare2) {
        availableCategories = new ArrayList<>(List.of(Category.values()));
        properties = new Properties();
        loadProperties();

        gameBoard = new QuestionInPanel[amountOfQuestions][amountOfRounds];

        gameBoardSetUpp(gameBoard,spelare1.username,spelare2.username);
        addRandomCategoryChoises(gameBoard);

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





    public void gameBoardSetUpp(QuestionInPanel[][] gameBoard, String player1, String player2){
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                QuestionInPanel questionInPanel = new QuestionInPanel();
                gameBoard[i][j] = questionInPanel;
                gameBoard[j][i].setCorrectPlayer1(false);
                gameBoard[j][i].setCorrectPlayer2(false);
                gameBoard[j][i].setPlayer1(player1);
                gameBoard[j][i].setPlayer2(player2);
            }
        }
    }


    public void addRandomCategoryChoises(QuestionInPanel[][] gameBoard){

        List<Category> categoriChoises = randomizeCategories();
        boolean categoryChoicesSet = false;

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
             if(gameBoard[i][j].getValdkategori()==null){
                 gameBoard[i][j].setRandomCategoryChoices(categoriChoises);
             }
             if(categoryChoicesSet){
                 return;
             }
            }
        }
    }





    public void loadProperties(){
        try {
            this.properties.load(new FileInputStream("src/gamelogic/Game_Properties.properties"));
        }
        catch(IOException e){
            e.printStackTrace();
            //***************************************************************************************************************************lägg till sout kommentar
        }
        this.amountOfQuestions = Integer.parseInt(this.properties.getProperty("amountOfQuestions", "2"));
        this.amountOfRounds = Integer.parseInt(this.properties.getProperty("amountOfRounds", "2"));

        //felsök
        System.out.println("Amount of questions:"+amountOfQuestions+"\nAmount of rounds:"+amountOfRounds);

    }

    public QuestionInPanel[][] getGameBoard() {
        return gameBoard;
    }




}
