package GUI;
import javax.swing.*;
import java.awt.*;

public class CategoryPanel extends JPanel {

    // static ImageIcon icon = new ImageIcon("src/CategoryLogo.png");

    JButton category1;
    JButton category2;
    JButton category3;

    public CategoryPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(50));

        JLabel label = new JLabel("Choose a category");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));

        category1 = new JButton("CATEGORY 1");
        category2 = new JButton("CATEGORY 2");
        category3 = new JButton("CATEGORY 3");

        category1.setPreferredSize(new Dimension(100, 40));
        category2.setPreferredSize(new Dimension(100, 40));
        category3.setPreferredSize(new Dimension(100, 40));

        category1.setFocusable(Boolean.FALSE);
        category2.setFocusable(Boolean.FALSE);
        category3.setFocusable(Boolean.FALSE);

        buttonPanel.add(category1);
        buttonPanel.add(category2);
        buttonPanel.add(category3);

        add(label);
        add(Box.createVerticalStrut(50));
        add(buttonPanel);
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Quiz");
//        frame.setIconImage(icon.getImage());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(550, 450);
//        frame.setLocationRelativeTo(null);
//
//        frame.add(new CategoryPanel());
//        frame.setVisible(true);
//    }
}
