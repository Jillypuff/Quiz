package GUI.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPanel extends JPanel {

    private final ImageIcon backgroundImage = new ImageIcon("src/GUI/images/Image2.jpg");

    private final List<JButton> categoryButtons;

    public CategoryPanel() {

        categoryButtons = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel categoryLabel = new JLabel("Choose a category");
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryLabel.setFont(new Font("Lucida Console", Font.PLAIN, 16));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = createButtonPanel();

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(categoryLabel);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        for (int i = 0; i < 3; i++) {
            JButton button = createCategoryButton();
            categoryButtons.add(button);
            buttonPanel.add(button);
            if (i < 2) {
                buttonPanel.add(Box.createVerticalStrut(15));
            }
        }
        return buttonPanel;
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
        return categoryButtons;
    }

    public JButton getCategory1() {
        return categoryButtons.size() > 0 ? categoryButtons.get(0) : null;
    }

    public JButton getCategory2() {
        return categoryButtons.size() > 1 ? categoryButtons.get(1) : null;
    }

    public JButton getCategory3() {
        return categoryButtons.size() > 2 ? categoryButtons.get(2) : null;
    }

        @Override
        protected void paintComponent (Graphics g){
            super.paintComponent(g);
            g.drawImage(backgroundImage.getImage(), 0, 0, 600, 360, this);
        }
    }