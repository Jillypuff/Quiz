package gamelogic;

import java.io.Serializable;
import java.util.*;

public class CurrentGame implements Serializable {

    private Category currentCategory;
    private final List<Category> allAvailableCategories;
    private List<Question> currentSetOfQuestions;

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

    public void loadCurrentSetOfQuestions(){
        List<Question> allQuestions = QuestionDatabase.getQuestionsFromCategory(currentCategory);
        Collections.shuffle(allQuestions);
        currentSetOfQuestions = new ArrayList<>(allQuestions.subList(0, amountOfQuestions));
    }

    public List<Question> getCurrentSetOfQuestions(){
        return currentSetOfQuestions;
    }

    public List<Category> getCurrentSetOfCategories(){
        Collections.shuffle(allAvailableCategories);
        List<Category> selectedCategories = allAvailableCategories.subList(0, amountOfCategoryAlternatives);
        return new ArrayList<>(selectedCategories);
    }

}
