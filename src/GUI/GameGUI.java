package GUI;

import GUI.panels.*;
import client.Client;
import javax.swing.*;
import java.io.IOException;

public class GameGUI extends JFrame {

    public QuestionPanel questionPanel;
    public CategoryPanel categoryPanel;
    public LoginPanel loginPanel;
    public WelcomePanel welcomePanel;
    public WaitingPanel waitingPanel;
    public UglyScorePanel uglyScorePanel;

    Client client;

    public GameGUI(Client client) throws IOException, InterruptedException {
        this.client = client;
        loginPanel = new LoginPanel();
        add(loginPanel);
        setupMainFrame();
        loadPanel();
    }

    public void setupMainFrame(){
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void loadPanel(){
        welcomePanel = new WelcomePanel();
        questionPanel = new QuestionPanel();
        categoryPanel = new CategoryPanel();
        waitingPanel = new WaitingPanel();
        uglyScorePanel = new UglyScorePanel();
    }

    public void switchPanel(int panel){
        getContentPane().removeAll();
        switch (panel){
            case 1 -> this.add(loginPanel);
            case 2 -> this.add(welcomePanel);
            case 3 -> this.add(categoryPanel);
            case 4 -> this.add(questionPanel);
            case 5 -> this.add(waitingPanel);
            case 6 -> this.add(uglyScorePanel);
        }
        revalidate();
        repaint();
    }
}
