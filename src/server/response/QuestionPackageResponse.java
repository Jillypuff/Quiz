package server.response;

import gamelogic.Category;
import gamelogic.Question;

import java.io.Serializable;
import java.util.List;

public class QuestionPackageResponse extends Response implements Serializable {

    Category category;
    List<Question> setOfQuestions;

    public QuestionPackageResponse(ResponseType responseType, Category category, List<Question> setOfQuestions) {
        super(responseType);
        this.category = category;
        this.setOfQuestions = setOfQuestions;
    }
    public List<Question> getSetOfQuestions() {
        return setOfQuestions;
    }
}
