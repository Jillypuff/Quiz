package GUI.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionPanel extends JPanel {

    private JTextArea questionLabel;
    private final JButton continueButton;
    private final List<JButton> answerButtons;

    private final ImageIcon backgroundImage = new ImageIcon("src/GUI/images/Image2.jpg");

    public QuestionPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        questionLabel = createQuestionLabel();

        continueButton = createButton("Continue", 14);
        continueButton.setVisible(false);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        answerButtons = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            answerButtons.add(createButton("", 16));
        }

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setOpaque(false);

        for (JButton button : answerButtons) {
            buttonPanel.add(button);
        }

        add(Box.createVerticalStrut(10));
        add(questionLabel);
        add(Box.createVerticalStrut(10));
        add(continueButton);
        add(buttonPanel);
    }

    private JTextArea createQuestionLabel() {
        questionLabel = new JTextArea();
        questionLabel.setEditable(false);
        questionLabel.setWrapStyleWord(true);
        questionLabel.setLineWrap(true);
        questionLabel.setColumns(20);
        questionLabel.setRows(2);
        questionLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        questionLabel.setPreferredSize(new Dimension(500, 60));
        questionLabel.setMaximumSize(new Dimension(500,60));
        questionLabel.setOpaque(false);
        questionLabel.setBorder(null);
        questionLabel.setFocusable(false);
        questionLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        return questionLabel;
    }

    private JButton createButton(String text, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font("", Font.PLAIN, fontSize));
        button.setFocusable(Boolean.FALSE);
        return button;
    }

    private void resetButtonColor() {
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
        continueButton.setFocusable(false);
        continueButton.setEnabled(false);
    }

    public void setContinueButton(){
        continueButton.setText("Continue");
        continueButton.setFocusable(false);
        continueButton.setEnabled(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, 600, 360, this);
    }
}