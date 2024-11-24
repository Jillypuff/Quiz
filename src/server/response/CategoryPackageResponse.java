package server.response;

import gamelogic.Category;

import java.util.List;

public class CategoryPackageResponse extends GamePackageResponse {

    private List<Category> categories;

    public CategoryPackageResponse(ResponseType responseType, String currentPlayer, String opponentPlayer, List<Category> categories) {
        super(responseType, currentPlayer, opponentPlayer);
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
