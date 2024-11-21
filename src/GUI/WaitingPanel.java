package GUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class WaitingPanel extends JPanel {

    public JLabel welcomePrompt = new JLabel();
    public JLabel queuedLabel = new JLabel("Waiting for other player to arrive");
    public JButton leaveQueueButton;

    public WaitingPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(50));

        leaveQueueButton = new JButton("Leave Queue");

        add(welcomePrompt);
        add(queuedLabel);
        add(leaveQueueButton);
    }

    public void addActionListener(ActionListener listener){
        leaveQueueButton.addActionListener(listener);
    }
}
