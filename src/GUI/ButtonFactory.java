package GUI;

import javax.swing.*;
import java.awt.*;

public class ButtonFactory {

    public static JButton createCategoryButton(String text) {
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

    public static JButton createStandardButton(String text, Color bgColor, Color fgColor, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(font);
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        return button;
    }

    public static JButton createAnswerButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 40));
        button.setFont(font);
        button.setFocusable(false);
        return button;
    }
}
