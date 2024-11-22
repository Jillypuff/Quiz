package gamelogic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class CurrentGame implements Serializable {

    public String playerOne;
    public String playerTwo;
    public String turnHolder;
    private Category currentCategory;
    private List<Category> currentSetOfCategories = new ArrayList<>();
    private List<Category> allAvailableCategories;

    //Ska läsas in från properties
    int amountOfQuestions = 3;
    int amountOfCategoryAlternatives = 3;

    public CurrentGame(String playerOne, String playerTwo){
        allAvailableCategories = new ArrayList<>(List.of(Category.values()));
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        setTurnHolder(playerOne);
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

    public void changeTurnHolder(){
        if (turnHolder.equals(playerOne)){
            turnHolder = playerTwo;
        } else {
            turnHolder = playerOne;
        }
    }

    public List<Category> getCurrentSetOfCategories(){
        return currentSetOfCategories;
    }

    public void setTurnHolder(String player){
        turnHolder = player;
    }

    public void setCurrentCategory(Category category){
        currentCategory = category;
    }

    public Category getCurrentCategory(){
        return currentCategory;
    }

    public Question getCurrentQuestion(){
        List<Question> questions = fetchQuestionsForCurrentCategory();
        return questions.removeFirst();
    }

    public List<Question> fetchQuestionsForCurrentCategory(){
        System.out.println("Inside current game class, going to get questions from category");
        List<Question> allQuestions = QuestionDatabase.getQuestionsFromCategory(currentCategory);
        for(Question question : allQuestions){
            System.out.println(question.question);
        }
        Collections.shuffle(allQuestions);
        return new ArrayList<>(allQuestions.subList(0,amountOfQuestions));
    }

    public void updateCurrentSetOfCategories(){
        Collections.shuffle(allAvailableCategories);
        List<Category> selectedCategories = allAvailableCategories.subList(0, amountOfCategoryAlternatives);
        currentSetOfCategories.addAll(selectedCategories);
//        allAvailableCategories.removeAll(selectedCategories);
    }

    public void removeChosenCategory(Category category){
        allAvailableCategories.remove(category);
    }

//    public void loadProperties(){
//        try {
//            this.properties.load(new FileInputStream("src/Game_Properties.properties"));
//        }
//        catch(IOException e){
//            e.printStackTrace();
//            //***************************************************************************************************************************lägg till sout kommentar
//        }
//        this.spelid = Integer.parseInt(this.properties.getProperty("spelNummer"));
//        this.questionAmount = Integer.parseInt(this.properties.getProperty("antalFragor"));
//        this.categoryAmount = Integer.parseInt(this.properties.getProperty("kategoriSet"));
//        //System.out.println(this.spelid + " " + this.antalFragor + " " + this.antalKategoriSet);
//    }

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

//    public void visaVariabler(){
//        System.out.println(
//                "spelnr: "+ spelid +
//                        "\nAntal frågor: " + questionAmount +
//                        "\nAntal kategori: " + categoryAmount
//                //properties;
//                //spelare1Spelbrade;
//                //spelare2Spelbrade;
//                //spelare 1
//                //spelare 2
//        );
//    }
}
