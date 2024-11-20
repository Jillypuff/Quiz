package server;

import java.io.Serializable;

public class CurrentGame implements Serializable {

    public String playerOne;
    public String playerTwo;
    public String turnHolder;

    public CurrentGame(String playerOne, String playerTwo){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        setTurnHolder(playerOne);
    }

    public void changeTurnHolder(){
        if (turnHolder.equals(playerOne)){
            turnHolder = playerTwo;
        } else {
            turnHolder = playerOne;
        }
    }

    public void setTurnHolder(String player){
        turnHolder = player;
    }
}
