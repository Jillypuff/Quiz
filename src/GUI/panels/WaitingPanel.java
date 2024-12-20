package GUI.panels;

import javax.swing.*;
import java.awt.*;

public class WaitingPanel extends JPanel {

    private final ImageIcon backgroundImage = new ImageIcon("src/GUI/images/Image2.jpg");

    private final JLabel queuedLabel = new JLabel("waiting for players to join game...");
    private final JButton leaveGameButton;

    public WaitingPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        add(Box.createVerticalStrut(90));

        queuedLabel.setAlignmentX(CENTER_ALIGNMENT);
        queuedLabel.setFont(new Font("Lucida Console", Font.PLAIN, 14));
        queuedLabel.setOpaque(false);

        leaveGameButton = createButton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(leaveGameButton);
        buttonPanel.setOpaque(false);

        add(queuedLabel);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }

    private JButton createButton() {
        JButton button = new JButton();
        button.setText("LEAVE GAME");
        button.setFont(new Font("Lucida Console", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusable(false);
        button.setOpaque(false);
        return button;
    }

    public void setUpWaitingPanel(boolean inGame){
        if (inGame){
            queuedLabel.setText("Waiting for opponent...");
        }
        else{
            queuedLabel.setText("In queue for a game...");
        }
    }

    public JButton getLeaveGameButton() {
        return leaveGameButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
