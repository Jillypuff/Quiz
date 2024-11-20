import java.io.*;
import java.net.ServerSocket;
import java.util.Properties;

public class Activ_game {

    private int spelid;
    private int antalFragor;
    private int antalKategoriSet;
    private Properties properties;
    private QuestionInPannel[][] spelare1Spelbrade;
    private QuestionInPannel[][] spelare2Spelbrade;
    //private int[][] gameState;
    ConnectedClient spelare1;
    ConnectedClient spelare2;

    Activ_game(ConnectedClient spelare1, ConnectedClient spelare2) {
        properties = new Properties();
        loadProperties();
        uppdateSpelNummerInProperties();

        spelare1Spelbrade = new QuestionInPannel[antalFragor][antalKategoriSet];
        spelare2Spelbrade = new QuestionInPannel[antalFragor][antalKategoriSet];
        //gameState = new int[(antalFragor*2)][antalKategoriSet];


    }

    Activ_game(){
        properties = new Properties();
        loadProperties();
        uppdateSpelNummerInProperties();
        visaVariabler();
    }



    //metod för att läsa in sparad sessionsid
    //metod för att uppdatera sessionsid tar int (nuvarande sessions id)

    //konstruktor som tar int (sesionsid)



    public void loadProperties(){
        try {
            this.properties.load(new FileInputStream("src/Game_Properties.properties"));
        }
        catch(IOException e){
            e.printStackTrace();
            //***************************************************************************************************************************lägg till sout kommentar
        }
        this.spelid = Integer.parseInt(this.properties.getProperty("spelNummer"));
        this.antalFragor = Integer.parseInt(this.properties.getProperty("antalFragor"));
        this.antalKategoriSet = Integer.parseInt(this.properties.getProperty("kategoriSet"));
        System.out.println(this.spelid + " " + this.antalFragor + " " + this.antalKategoriSet);
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

        System.out.println(nuvarandeSpel);

        System.out.println(nySpelNummer);

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
        "\n Antal frågor: " + antalFragor +
        "\n Antal kategori: " + antalKategoriSet
        //properties;
        //spelare1Spelbrade;
        //spelare2Spelbrade;
        //spelare 1
        //spelare 2
        );
    }

}
