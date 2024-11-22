package GUI;

import client.Client;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameGUI extends JFrame {

    public GamePanel gamePanel;
    public CategoryPanel categoryPanel;
    public LoginPanel loginPanel;
    public WelcomePanel welcomePanel;
    public WaitingPanel waitingPanel;

    Client client;

    public GameGUI(Client client) throws IOException, InterruptedException {
        this.client = client;
        loginPanel = new LoginPanel();
        add(loginPanel);
        setupMainFrame();
        loadPanel();
    }

    public void setupMainFrame(){
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void loadPanel(){
        welcomePanel = new WelcomePanel();
        gamePanel = new GamePanel();
        categoryPanel = new CategoryPanel();
        waitingPanel = new WaitingPanel();
    }

    public void switchPanel(int panel){
        getContentPane().removeAll();
        switch (panel){
            case 1 -> this.add(loginPanel);
            case 2 -> this.add(welcomePanel);
            case 3 -> this.add(categoryPanel);
            case 4 -> this.add(gamePanel);
            case 5 -> this.add(waitingPanel);
        }
        revalidate();
        repaint();
    }

    public void actionListener(ActionListener actionListener){

    }
}
