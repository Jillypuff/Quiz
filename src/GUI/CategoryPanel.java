package GUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryPanel extends JPanel {

    // static ImageIcon icon = new ImageIcon("src/CategoryLogo.png");

    public JButton category1;
    public JButton category2;
    public JButton category3;


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

    public List<JButton> getCategoryButtons(){
        List<JButton> buttons = new ArrayList<JButton>();
        buttons.add(category1);
        buttons.add(category2);
        buttons.add(category3);
        return buttons;
    }
}
