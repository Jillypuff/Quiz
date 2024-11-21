package gamelogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CurrentGame implements Serializable {

    public String playerOne;
    public String playerTwo;
    public String turnHolder;
    private Category currentCategory;
    private List<Category> currentSetOfCategories = new ArrayList<>();
    private List<Category> allAvailableCategories;

    //Ska läsas in från properties
    int amountOfQuestions = 3;
    int amountOfCategoryAlternatives = 3;

    public CurrentGame(String playerOne, String playerTwo){
        allAvailableCategories = new ArrayList<>(List.of(Category.values()));
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

    public List<Category> getCurrentSetOfCategories(){
        return currentSetOfCategories;
    }

    public void setTurnHolder(String player){
        turnHolder = player;
    }

    public void setCurrentCategory(Category category){
        currentCategory = category;
    }

    public Category getCurrentCategory(){
        return currentCategory;
    }

    public Question getCurrentQuestion(){
        List<Question> questions = fetchQuestionsForCurrentCategory();
        return questions.removeFirst();
    }

    public List<Question> fetchQuestionsForCurrentCategory(){
        System.out.println("Inside current game class, going to get questions from category");
        List<Question> allQuestions = QuestionDatabase.getQuestionsFromCategory(currentCategory);
        for(Question question : allQuestions){
            System.out.println(question.question);
        }
        Collections.shuffle(allQuestions);
        return new ArrayList<>(allQuestions.subList(0,amountOfQuestions));
    }

    public void updateCurrentSetOfCategories(){
        Collections.shuffle(allAvailableCategories);
        List<Category> selectedCategories = allAvailableCategories.subList(0, amountOfCategoryAlternatives);
        currentSetOfCategories.addAll(selectedCategories);
        allAvailableCategories.removeAll(selectedCategories);
    }
}
