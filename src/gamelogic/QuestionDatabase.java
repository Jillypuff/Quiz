package gamelogic;

import Modules.Category;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDatabase {

    private static String filePath = "src/gamelogic/questionfile.txt";

    public static List<Question> getQuestionsFromCategory(Category category) {
        List<Question> questions = new ArrayList<>();

        System.out.println("Start reading from file");
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Stream opened, beginning loop");
            while((line = reader.readLine()) != null){
                String[] parts = line.split(";");
                if (parts.length>= 6){
                    String categoryName = parts[0];
                    if (categoryName.equalsIgnoreCase(category.name())){
                        String question = parts[1];
                        List<String> alternatives = Arrays.asList(parts[2], parts[3], parts[4], parts[5]);
                        String correctAnswer = parts[6];

                        questions.add(new Question(question, correctAnswer, alternatives));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }
}
