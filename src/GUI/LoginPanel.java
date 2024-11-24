package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    public JTextField usernameTextField;
    public JButton startButton;
    public JButton exitButton;

    private static final Color startButtonColor = new Color(1, 214, 196);
    private static final Color exitButtonColor = new Color (0, 153, 255);
    private static final Color welcomeLabelColor = new Color (22, 153, 153);

    public LoginPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(40));

        JLabel welcomeLabel = createLabel("Welcome to QUIZ!", welcomeLabelColor, 20);
        JLabel enterNameLabel = createLabel("Enter your name:", Color.BLACK, 14);
        usernameTextField = new JTextField(50);

        startButton = createButton("START", startButtonColor);
        exitButton = createButton("EXIT", exitButtonColor);

        usernameTextField.setForeground(Color.BLACK);
        usernameTextField.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        namePanel.add(enterNameLabel);
        namePanel.add(Box.createHorizontalStrut(5));
        namePanel.add(usernameTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createHorizontalStrut(100));
        buttonPanel.add(exitButton);

        add(welcomeLabel);
        add(Box.createVerticalStrut(50));
        add(namePanel);
        add(buttonPanel);
    }

    private JLabel createLabel(String text, Color color, int size) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, size));
        label.setForeground(color);
        return label;
    }

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150,40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusable(false);
        return button;
    }

    public void addActionListener(ActionListener listener){
        startButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }
}
