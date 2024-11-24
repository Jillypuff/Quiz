package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WaitingPanel extends JPanel {

    private final JLabel queuedLabel = new JLabel("Your new game starts soon..");
    private final JButton leaveGameButton;

    public WaitingPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(70));

        queuedLabel.setAlignmentX(CENTER_ALIGNMENT);

        leaveGameButton = createButton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(leaveGameButton);

        add(queuedLabel);
        add(Box.createVerticalStrut(30));
        add(buttonPanel);
    }

    private JButton createButton() {
        JButton button = new JButton();
        button.setText("Leave Game");
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(new Color(Color.RED.getRGB()));
        button.setFocusable(false);
        return button;
    }

    public void addActionListener(ActionListener listener){
        leaveGameButton.addActionListener(listener);
    }

    public JLabel getQueuedLabel() {
        return queuedLabel;
    }

    public JButton getLeaveGameButton() {
        return leaveGameButton;
    }
}
