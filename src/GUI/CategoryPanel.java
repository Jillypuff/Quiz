package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPanel extends JPanel {

    public JButton category1;
    public JButton category2;
    public JButton category3;

    public CategoryPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 240, 240));
        add(Box.createVerticalStrut(60));


        JLabel label = new JLabel("Choose a category");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setForeground(new Color(60, 60, 60));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        category1 = createCategoryButton("CATEGORY 1");
        category2 = createCategoryButton("CATEGORY 2");
        category3 = createCategoryButton("CATEGORY 3");

        buttonPanel.add(category1);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(category2);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(category3);

        add(label);
        add(Box.createVerticalStrut(30));
        add(buttonPanel);
    }

    private JButton createCategoryButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 60));
        button.setFont(new Font("Tahoma", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 203, 172));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    public List<JButton> getCategoryButtons() {
        List<JButton> buttons = new ArrayList<>();
        buttons.add(category1);
        buttons.add(category2);
        buttons.add(category3);
        return buttons;
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Category Selection");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 400);
//        frame.setLocationRelativeTo(null);
//        frame.add(new CategoryPanel());
//        frame.setVisible(true);
//    }
}
