package client.request;

import gamelogic.Category;

import java.io.Serializable;

public class StartRoundRequest extends Request implements Serializable {

    Category chosenCategory;

    public StartRoundRequest(RequestType requestType, String username, Category chosenCategory){
        super(requestType, username);
        this.chosenCategory = chosenCategory;
    }

    public Category getChosenCategory() {
        return chosenCategory;
    }
}
