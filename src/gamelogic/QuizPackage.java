package gamelogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizPackage implements Serializable {

    private final List<Category> allAvailableCategories;

    private Category currentCategory;
    private List<Question> currentSetOfQuestions;
    private List<Category> currentSetOfCategories;

    // läs in från properties?
    final int AMOUNT_OF_QUESTIONS_PER_ROUND = 3;
    final int AMOUNT_OF_CATEGORY_ALTERNATIVES = 3;
    final int MAX_ROUNDS_PER_GAME = 2;


    public QuizPackage(){
        allAvailableCategories = new ArrayList<>(List.of(Category.values()));
    }

    public void setCurrentCategory(Category category){
        currentCategory = category;
        allAvailableCategories.remove(currentCategory);
    }

    public Category getCurrentCategory(){
        return currentCategory;
    }

    public void loadCurrentSetOfQuestions(){
        List<Question> allQuestions = QuestionDatabase.getQuestionsFromCategory(currentCategory);
        Collections.shuffle(allQuestions);
        currentSetOfQuestions = new ArrayList<>(allQuestions.subList(0, AMOUNT_OF_QUESTIONS_PER_ROUND));
    }

    public List<Question> getCurrentSetOfQuestions(){
        return currentSetOfQuestions;
    }

    public void loadSetOfCategories(){
        Collections.shuffle(allAvailableCategories);
        List<Category> selectedCategories = allAvailableCategories.subList(0, AMOUNT_OF_CATEGORY_ALTERNATIVES);
        currentSetOfCategories = new ArrayList<>(selectedCategories);
    }

    public List<Category> getCurrentSetOfCategories(){
        return currentSetOfCategories;
    }
}
