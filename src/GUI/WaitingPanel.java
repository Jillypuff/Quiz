package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WaitingPanel extends JPanel {

    public JLabel queuedLabel = new JLabel("Waiting for a player to join..");
    public JButton leaveQueueButton;

    public WaitingPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(50));

        queuedLabel.setAlignmentX(CENTER_ALIGNMENT);

        leaveQueueButton = new JButton("Leave Queue");
        leaveQueueButton.setBackground(Color.RED);
        leaveQueueButton.setFocusable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(leaveQueueButton);


        add(queuedLabel);
        add(Box.createVerticalStrut(40));
        add(buttonPanel);
    }

    public void addActionListener(ActionListener listener){
        leaveQueueButton.addActionListener(listener);
    }
}
