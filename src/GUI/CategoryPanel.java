package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPanel extends JPanel {

    private final JButton category1;
    private final JButton category2;
    private final JButton category3;

    public CategoryPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 240, 240));
        add(Box.createVerticalStrut(60));


        JLabel categoryLabel = new JLabel("Choose a category");
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        categoryLabel.setForeground(new Color(60, 60, 60));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        category1 = createCategoryButton();
        category2 = createCategoryButton();
        category3 = createCategoryButton();

        buttonPanel.add(category1);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(category2);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(category3);

        add(categoryLabel);
        add(Box.createVerticalStrut(30));
        add(buttonPanel);
    }

    private JButton createCategoryButton() {
        JButton button = new JButton("");
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

    public JButton getCategory1() {
        return category1;
    }

    public JButton getCategory2() {
        return category2;
    }

    public JButton getCategory3() {
        return category3;
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
