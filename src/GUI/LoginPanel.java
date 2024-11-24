package GUI;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private final ImageIcon backgroundImage = new ImageIcon("src/GUI/Image.jpg");

    public JTextField usernameTextField;
    public JButton startButton;
    public JButton exitButton;

    public LoginPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(60));

        JLabel welcomeLabel = createLabel("Welcome to QUIZ!", 21);
        JLabel enterNameLabel = createLabel("Username", 14);
        usernameTextField = new JTextField(30);

        startButton = createButton("START");
        exitButton = createButton("EXIT");

        usernameTextField.setForeground(new Color(0xFF878787, true));
        usernameTextField.setHorizontalAlignment(SwingConstants.CENTER);
        usernameTextField.setPreferredSize(new Dimension(250, 35));
        usernameTextField.setMaximumSize(new Dimension(250, 35));
        usernameTextField.setOpaque(false);
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        usernameTextField.setBorder(bottomBorder);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setOpaque(false);
        namePanel.add(enterNameLabel);
        namePanel.add(usernameTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createHorizontalStrut(90));
        buttonPanel.add(exitButton);

        add(welcomeLabel);
        add(Box.createVerticalStrut(50));
        add(namePanel);
        add(Box.createVerticalStrut(50));
        add(buttonPanel);
    }

    private JLabel createLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Lucida Console", Font.PLAIN, size));
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150,40));
        button.setFont(new Font("Lucida Console", Font.PLAIN, 14));
        button.setFocusable(false);
        return button;
    }

    public void addActionListener(ActionListener listener){
        startButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, 600, 360, this);
    }
}
