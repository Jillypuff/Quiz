package gamelogic;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

public class ActiveGame {

    private int spelid;
    private int antalFragor;
    private int antalKategoriSet;
    private Properties properties;
    private QuestionInPannel[][] spelare1Spelbrade;
    private QuestionInPannel[][] spelare2Spelbrade;
    //private int[][] gameState;
    ServerSocket spelare1;
    ServerSocket spelare2;

    ActiveGame(ServerSocket spelare1, ServerSocket spelare2) {
        properties = new Properties();
        loadProperties();
        uppdateSpelNummerInProperties();

        spelare1Spelbrade = new QuestionInPannel[antalFragor][antalKategoriSet];
        spelare2Spelbrade = new QuestionInPannel[antalFragor][antalKategoriSet];
        //gameState = new int[(antalFragor*2)][antalKategoriSet];

        while(true) {

        }





    }

    ActiveGame(){
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
            this.properties.store(new FileWriter("src/Game_properties.properties"), "Activ Game Properties");
        }
        catch(IOException e){
            e.printStackTrace();
            //***************************************************************************************************************************lägg till sout kommentar
        }
        this.spelid = Integer.parseInt(this.properties.getProperty("spelNummer", "1"));
        this.antalFragor = Integer.parseInt(this.properties.getProperty("antalFragor", "3"));
        this.antalKategoriSet = Integer.parseInt(this.properties.getProperty("kategoriSet", "4"));
    }

    public void uppdateSpelNummerInProperties() {//************************************************************************************************ behöver den här metoden vara "syncronized"

        Properties tempProperties = new Properties();


        try{
            tempProperties.load(new FileReader("src/Game_properties.properties"));
        }
        catch(Exception e){
            e.printStackTrace();
        }


        int spelNummer = Integer.parseInt(tempProperties.getProperty("spelNummer"));
        String nySpelNummer = String.valueOf(spelNummer)+1;
        tempProperties.setProperty("spelNummer", nySpelNummer);


        try{
            tempProperties.store(new FileOutputStream("src/Game_properties.properties"), null);
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
