package gamelogic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class CurrentGame implements Serializable {

    private Category currentCategory;
    private final List<Category> allAvailableCategories;

    private int amountOfQuestions = 3;
    private int amountOfCategoryAlternatives = 3;

    public CurrentGame(String playerOneName, String playerTwoName){
        allAvailableCategories = new ArrayList<>(List.of(Category.values()));
    }

    public void setCurrentCategory(Category category){
        currentCategory = category;
        allAvailableCategories.remove(currentCategory);
    }

    public Category getCurrentCategory(){
        return currentCategory;
    }

    public List<Question> getQuestionsForCurrentCategory(){
        List<Question> allQuestions = QuestionDatabase.getQuestionsFromCategory(currentCategory);
        Collections.shuffle(allQuestions);
        return new ArrayList<>(allQuestions.subList(0,amountOfQuestions));
    }

    public List<Category> getCurrentSetOfCategories(){
        Collections.shuffle(allAvailableCategories);
        List<Category> selectedCategories = allAvailableCategories.subList(0, amountOfCategoryAlternatives);
        return new ArrayList<>(selectedCategories);
    }
}
