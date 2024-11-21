package gameLogic;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class gameLogic {

    private int spelid;
    /*
    PROPERTIES
     */
    private int questionAmount;
    private int categoryAmount;

    /*
    CATEGORIES AND QUESTIONS
     */
    private List<String> allCategories = new ArrayList<>(); //ska innehålla alla möjliga kategorier
    private List<String> selectedCategories = new ArrayList<>(); //ska innehålla rundans kategorival
    private List<Question> questions = new ArrayList<>();
    private Question currentQuestion;

    private Properties properties;
    private QuestionInPannel[][] gameBoard;
    private int activePlayer = 1;
    // private QuestionInPannel[][] spelare2Spelbrade;
    //private int[][] gameState;
    int playerOnePoints;
    int playerTwoPoints;

    gameLogic() {
        properties = new Properties();
        loadProperties();
        uppdateSpelNummerInProperties();
        visaVariabler();

        gameBoard = new QuestionInPannel[questionAmount][categoryAmount];
        // spelare2Spelbrade = new QuestionInPannel[questionAmount][categoryAmount];
        //gameState = new int[(antalFragor*2)][antalKategoriSet];

        //skicka till spelare 1 bestäm kategori
        //uppdatera kategorin för spelbräde 1 och 2 y1= samma kategori

        //spelare 1 får svara på frågor, när fråga slumpas ska samma fråga in på spelare 2's bräde i samma position
        //när spelare 1 svarat på alla frågor är det spelare 2's turbrädet ska uppdateras clientside efter att båda spelare svarat på alla frågor(första spelaren som svarar på frågor kan ju egentligen få se sina egna svar men men...).

        //där efter är det spelere 2's tur att välja kategori tänker att spelet kan kolla om det är jämnt eller ojämnt y värde den tittar på vid jämna värden är det spelare 1 som väljer och ojämna och spelare 2 vid jämna
    }

    /*
    SPELETS GÅNG
    x = DONE
    - = TODO
    / = BASED ON OTHER

    [x] SKAPA UPP EN INSTANS AV GAMELOGIC, SÄTTER PROPRTIES HUR MÅNGA KATEGORIER OCH FRÅGA PER KATEGORI
    [-] ALLA MÖJLIGA KATEGORITYPER LÄSES IN I EN LISTA
    [x] SÄTTER SPELARE 1 TILL AKTIV SPELARE
    [/]Runda x där x är antal kategorier som är satt
    {
        [x] Randomisera 3 kategorier och presentera för aktiv spelare
        [-] Aktiv spelare väljer en och skickar tillbaka
        [x] Kategorin läggs till i valdkategori lista
        [x] Kategorin tas bort ur alla kategorityper lista för att undvika duplikering
        [x] Vi randomiserar fram x antal frågor där x är antal som är valda i properties
        [-] Vi skapar paket med alla frågor och skickar till båda spelarna
        [-] Vi får tillbaka svar ifrån spelarna och sätter poäng baserad på korrekt svar eller ej
        [-] Om det är rundor kvar så gör vi om allt med en ny kategori
    }
    [-] Vi räknar ut slutgiltiga poängen och visar för båda spelarna
    [-] *Går att utöka med att spara alla frågor och svar och skicka tillba dom också*
     */

    public void evaluateAnswer(int choice){
        //jämför

    }

    public Question getCurrentQuestion(){
        return currentQuestion;
    }

    public void setCurrentQuestion(){
        if (!questions.isEmpty()){
            currentQuestion = questions.removeFirst();
        }
    }

    public void getQuestions(){
        // Hämtar ner alla frågor inom den valda kategorin
        List<Question> newQuestions = new ArrayList<>();
        Collections.shuffle(newQuestions);
        for (int i = 0; i < questionAmount; i++) {
            questions.add(newQuestions.get(i));
        }
    }

    //metod för att läsa in sparad sessionsid
    //metod för att uppdatera sessionsid tar int (nuvarande sessions id)

    //konstruktor som tar int (sesionsid)

    public int getActivePlayer(){
        return activePlayer;
    }

    public List<String> getCategoryChoices(){
        List<String> randomCategories = new ArrayList<>();
        Collections.shuffle(allCategories);
        for (int i = 0; i < 3; i++){
            randomCategories.add(allCategories.get(i));
        }
        return randomCategories;
    }

    public void setCategory(String category){
        selectedCategories.add(category);
        allCategories.remove(category);
    }

    public void changeActivePlayer(){
        if (activePlayer == 1){
            activePlayer = 2;
        } else {
            activePlayer = 1;
        }
    }

    public void loadProperties(){
        try {
            this.properties.load(new FileInputStream("src/Game_Properties.properties"));
        }
        catch(IOException e){
            e.printStackTrace();
            //***************************************************************************************************************************lägg till sout kommentar
        }
        this.spelid = Integer.parseInt(this.properties.getProperty("spelNummer"));
        this.questionAmount = Integer.parseInt(this.properties.getProperty("antalFragor"));
        this.categoryAmount = Integer.parseInt(this.properties.getProperty("kategoriSet"));
        //System.out.println(this.spelid + " " + this.antalFragor + " " + this.antalKategoriSet);
    }

    public void uppdateSpelNummerInProperties() {//************************************************************************************************ behöver den här metoden vara "syncronized"

        Properties tempProperties = new Properties();


        try{
            tempProperties.load(new FileInputStream("src/Game_Properties.properties"));
        }
        catch(Exception e){
            e.printStackTrace();
        }

        String nuvarandeSpel = tempProperties.getProperty("spelNummer");
        int nuvarandeSpelInt = Integer.parseInt(nuvarandeSpel);
        String nySpelNummer = (nuvarandeSpelInt+1) + "";

        //System.out.println(nuvarandeSpel);

        //System.out.println(nySpelNummer);

        tempProperties.setProperty("spelNummer", nySpelNummer);


        try{
            tempProperties.store(new FileOutputStream("src/Game_Properties.properties"), null);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void visaVariabler(){
        System.out.println(
        "spelnr: "+ spelid +
        "\nAntal frågor: " + questionAmount +
        "\nAntal kategori: " + categoryAmount
        //properties;
        //spelare1Spelbrade;
        //spelare2Spelbrade;
        //spelare 1
        //spelare 2
        );
    }
}
