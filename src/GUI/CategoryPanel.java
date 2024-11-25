package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPanel extends JPanel {

    private final ImageIcon backgroundImage = new ImageIcon("src/GUI/images/Image2.jpg");

    private final JButton category1;
    private final JButton category2;
    private final JButton category3;

    public CategoryPanel() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel categoryLabel = new JLabel("Choose a category");
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryLabel.setFont(new Font("Lucida Console", Font.PLAIN, 16));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        category1 = createCategoryButton();
        category2 = createCategoryButton();
        category3 = createCategoryButton();

        buttonPanel.add(category1);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(category2);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(category3);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(categoryLabel);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createCategoryButton() {
        JButton button = new JButton("Category");
        button.setPreferredSize(new Dimension(50, 35));
        button.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMargin(new Insets(15, 50, 15, 50));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, 600, 360, this);
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
