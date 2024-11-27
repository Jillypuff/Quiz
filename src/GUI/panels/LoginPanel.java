package GUI.panels;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private final ImageIcon backgroundImage = new ImageIcon("src/GUI/images/Image.jpg");

    private final JTextField usernameTextField;
    private final JButton startButton, exitButton;

    public LoginPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(60));

        JLabel welcomeLabel = createLabel("QUIZ!", "Arial Black", 26);
        JLabel usernameLabel = createLabel("Enter username", "Lucida Console", 12);

        usernameTextField = createTextField();
        startButton = createButton("START");
        exitButton = createButton("EXIT");

        JPanel namePanel = createNamePanel(usernameLabel);
        JPanel buttonPanel = createButtonPanel();

        add(welcomeLabel);
        add(Box.createVerticalStrut(50));
        add(namePanel);
        add(Box.createVerticalStrut(50));
        add(buttonPanel);
    }

    private JLabel createLabel(String text, String font, int size) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font(font, Font.PLAIN, size));
        return label;
    }

    private JPanel createNamePanel(JLabel usernameLabel) {
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setOpaque(false);
        namePanel.add(usernameLabel);
        namePanel.add(usernameTextField);
        return namePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createHorizontalStrut(90));
        buttonPanel.add(exitButton);
        return buttonPanel;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(30);
        textField.setForeground(new Color(0xFF878787, true));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setPreferredSize(new Dimension(250, 35));
        textField.setMaximumSize(new Dimension(250, 35));
        textField.setOpaque(false);
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        textField.setBorder(bottomBorder);
        return textField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150,40));
        button.setFont(new Font("Arial Black", Font.PLAIN, 14));
        button.setFocusable(false);
        return button;
    }

    public void addActionListener(ActionListener listener){
        startButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, 600, 360, this);
    }
}
