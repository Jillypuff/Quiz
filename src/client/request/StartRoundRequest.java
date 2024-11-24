package client.request;

import gamelogic.Category;

public class StartRoundRequest extends Request {

    Category selectedCategory;

    public StartRoundRequest(RequestType requestType, String username, Category selectedCategory) {
        super(requestType, username);
        this.selectedCategory = selectedCategory;
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }
}
