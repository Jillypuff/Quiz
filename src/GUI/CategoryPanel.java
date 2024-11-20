package GUI;
import javax.swing.*;
import java.awt.*;

public class CategoryPanel extends JPanel {

    static ImageIcon icon = new ImageIcon("src/CategoryLogo.png");

    public CategoryPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(50));

        JLabel label = new JLabel("Choose a category");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));

        JButton button1 = new JButton("CATEGORY 1");
        JButton button2 = new JButton("CATEGORY 2");
        JButton button3 = new JButton("CATEGORY 3");

        button1.setPreferredSize(new Dimension(100, 40));
        button2.setPreferredSize(new Dimension(100, 40));
        button3.setPreferredSize(new Dimension(100, 40));

        button1.setFocusable(Boolean.FALSE);
        button2.setFocusable(Boolean.FALSE);
        button3.setFocusable(Boolean.FALSE);

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        add(label);
        add(Box.createVerticalStrut(50));
        add(buttonPanel);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quiz");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 450);
        frame.setLocationRelativeTo(null);

        frame.add(new CategoryPanel());
        frame.setVisible(true);
    }
}
