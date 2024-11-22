package server.response;

import gamelogic.Category;

import java.io.Serializable;
import java.util.List;

public class CategoryPackageResponse extends Response implements Serializable {

    List<Category> setOfCategories;

    public CategoryPackageResponse(ResponseType responseType, List<Category> setOfCategories) {
        super(responseType);
        this.setOfCategories = setOfCategories;
    }

    public List<Category> getSetOfCategories() {
        return setOfCategories;
    }
}
