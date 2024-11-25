package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionPanel extends JPanel {

    Color rightAnswerColor = new Color(1, 172, 1);
    Color wrongAnswerColor = new Color(255, 0, 0);

    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JButton nextQuestionButton;

    JLabel questionLabel;

    public QuestionPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(30));

        questionLabel = new JLabel();
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));

        nextQuestionButton = new JButton("next question");
        nextQuestionButton.setVisible(false);
        nextQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextQuestionButton.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        nextQuestionButton.setPreferredSize(new Dimension(50, 30));

        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();

        button1.setPreferredSize(new Dimension(90, 30));
        button2.setPreferredSize(new Dimension(90, 30));
        button3.setPreferredSize(new Dimension(90, 30));
        button4.setPreferredSize(new Dimension(90, 30));

        button1.setFont(new Font("Malgun Gothic", Font.PLAIN, 18));
        button2.setFont(new Font("Malgun Gothic", Font.PLAIN, 18));
        button3.setFont(new Font("Malgun Gothic", Font.PLAIN, 18));
        button4.setFont(new Font("Malgun Gothic", Font.PLAIN, 18));

        button1.setFocusable(Boolean.FALSE);
        button2.setFocusable(Boolean.FALSE);
        button3.setFocusable(Boolean.FALSE);
        button4.setFocusable(Boolean.FALSE);

        List<JButton> buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);

        Collections.shuffle(buttons);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        ActionListener buttonListener = e -> {
            button1.setBackground(null);
            button2.setBackground(null);
            button3.setBackground(null);
            button4.setBackground(null);

            JButton clickedButton = (JButton) e.getSource();
            if (clickedButton.getText().equals("New York City")) {
                clickedButton.setBackground(rightAnswerColor);
                nextQuestionButton.setVisible(true);
            } else {
                clickedButton.setBackground(wrongAnswerColor);
                button3.setBorder(BorderFactory.createLineBorder(rightAnswerColor, 4));
            }
            disableButtons();
            nextQuestionButton.setVisible(true);
        };

        button1.addActionListener(buttonListener);
        button2.addActionListener(buttonListener);
        button3.addActionListener(buttonListener);
        button4.addActionListener(buttonListener);

        add(questionLabel);
        add(Box.createVerticalStrut(10));
        add(nextQuestionButton);
        add(buttonPanel);
    }

    private void disableButtons() {
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
    }

    public void addActionListeners(ActionListener listener){
        button1.addActionListener(listener);
        button2.addActionListener(listener);
        button3.addActionListener(listener);
        button4.addActionListener(listener);
    }

    public List<JButton> getAllAnswerButtons(){
        List<JButton> buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        return buttons;
    }

    public JLabel getQuestionLabel(){
        return questionLabel;
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Quiz");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 400);
//        frame.setLocationRelativeTo(null);
//        frame.add(new QuestionPanel());
//        frame.setVisible(true);
//    }
}
