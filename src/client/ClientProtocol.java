package client;

import server.Response;

public class ClientProtocol {

    public void processResponse(Response response, Client client){
        switch (response.getType()){
            case CLIENT_CONNECTED -> {
                System.out.println("Received client connected");
                client.gameGUI.switchPanel(2);
                String username = client.gameGUI.loginPanel.usernameTextField.getText();
                client.username = username;
                client.gameGUI.mainPanel.welcomePrompt.setText("Welcome " + username);
                // client.gameGUI.startGame();
                // client.gameGUI.textField.text = "Clienten konnektad";
                // Skriv ut i något relevant textfält att connection är lyckad
                // Hoppa till mainWindow
            }
            case CLIENT_DISCONNECTED -> {
                System.out.println("Client disconnected");
                client.gameGUI.switchPanel(1);
                // Skriv ut i något relevant textfält att connectionen är avbryten
                // Gå tillbaka till loginWindow
            }
            case QUEUE_JOINED -> {
                System.out.println("Received queue joined");
                client.gameGUI.switchPanel(1);
            }
            case GAME_JOINED -> {
                System.out.println("Received game joined");
                if (response.game.turnHolder.equals(client.username)){
                    client.gameGUI.switchPanel(3);
                }
                // Starta spelet, få kategorier? eller meddelas att den andra kör?
            }
            case SEND_QUESTION -> {
                // Skicka över ett Question objekt helt eller utan svar
            }
            case SEND_ANSWER -> {
                // Ge feedback om dom svara rätt eller inte
                // Ge poäng där efter
                //Knapp för fortsätt till nästa fråga blir tillgänglig
            }
            case SEND_SCORE -> {
                // Ge användaren sin poäng efter varje fråga
            }
            case SEND_FINAL_RESULT -> {
                // Visa båda spelarnas poäng och vem som vann!
            }
        }
    }
}
