package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamePanel extends JPanel {

    // static ImageIcon icon = new ImageIcon("src/CategoryLogo.png");

    Color rightAnswerColor = new Color(1, 172, 1);
    Color wrongAnswerColor = new Color(255, 0, 0);

    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JButton nextQuestionButton;

    public GamePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(30));

        JLabel questionLabel = new JLabel("In which city does the show 'Friends' take place?");
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        nextQuestionButton = new JButton("Next Question");
        nextQuestionButton.setVisible(false);
        nextQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextQuestionButton.setFont(new Font("Arial", Font.PLAIN, 14));

        button1 = new JButton("Los Angeles");
        button2 = new JButton("Chicago");
        button3 = new JButton("New York City");
        button4 = new JButton("Boston");

        button1.setPreferredSize(new Dimension(100, 40));
        button2.setPreferredSize(new Dimension(100, 40));
        button3.setPreferredSize(new Dimension(100, 40));
        button4.setPreferredSize(new Dimension(100, 40));

        button1.setFont(new Font("Arial", Font.PLAIN, 18));
        button2.setFont(new Font("Arial", Font.PLAIN, 18));
        button3.setFont(new Font("Arial", Font.PLAIN, 18));
        button4.setFont(new Font("Arial", Font.PLAIN, 18));

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
                button3.setBackground(rightAnswerColor);
                nextQuestionButton.setVisible(true);
            }
        };

        button1.addActionListener(buttonListener);
        button2.addActionListener(buttonListener);
        button3.addActionListener(buttonListener);
        button4.addActionListener(buttonListener);

        add(questionLabel);
        add(Box.createVerticalStrut(20));
        add(nextQuestionButton);
        add(buttonPanel);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quiz");
        // frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 450);
        frame.setLocationRelativeTo(null);

        frame.add(new GamePanel());
        frame.setVisible(true);
    }
}
