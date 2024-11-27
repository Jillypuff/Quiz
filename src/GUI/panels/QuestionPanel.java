package GUI.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuestionPanel extends JPanel {

    private final JLabel questionLabel;
    private final JButton continueButton;
    private final List<JButton> answerButtons;

    public QuestionPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 21));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        continueButton = createButton("Continue", 14);
        continueButton.setVisible(false);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        answerButtons = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            answerButtons.add(createButton("", 16));
        }

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (JButton button : answerButtons) {
            buttonPanel.add(button);
        }

        add(Box.createVerticalStrut(10));
        add(questionLabel);
        add(Box.createVerticalStrut(10));
        add(continueButton);
        add(buttonPanel);
    }

    public JButton createButton(String text, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font("", Font.PLAIN, fontSize));
        button.setFocusable(Boolean.FALSE);
        return button;
    }

    public void resetButtonColor() {
        for (JButton button : answerButtons) {
            button.setBackground(null);
        }
    }

    public void disableButtons() {
        answerButtons.forEach(button -> button.setEnabled(false));
        continueButton.setVisible(true);
    }

    public void enableButtons(){
        continueButton.setVisible(false);
        answerButtons.forEach(button -> button.setEnabled(true));
    }

    public void addActionListeners(ActionListener listener){
        for (JButton button : answerButtons) {
            button.addActionListener(listener);
        }
    }

    public List<JButton> getAllAnswerButtons(){
        return new ArrayList<>(answerButtons);
    }

    public JButton getContinueButton() {
        return continueButton;
    }

    public void getQuestion(String question, String answer1, String answer2, String answer3, String answer4){
        questionLabel.setText(question);
        resetButtonColor();
        answerButtons.get(0).setText(answer1);
        answerButtons.get(1).setText(answer2);
        answerButtons.get(2).setText(answer3);
        answerButtons.get(3).setText(answer4);
        enableButtons();
    }

    public void setWaitingButton(){
        continueButton.setText("Waiting");
        continueButton.setEnabled(false);
    }

    public void setContinueButton(){
        continueButton.setText("Continue");
        continueButton.setEnabled(true);
    }
}