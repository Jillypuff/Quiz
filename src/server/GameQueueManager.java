package server;

import Modules.Response;
import Modules.ResponseType;
import gamelogic.GameInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameQueueManager {

    private final List<ConnectedClient> queue;

    public GameQueueManager() {
        this.queue = new ArrayList<>();
    }

    public void handleStartGame(ConnectedClient client) throws IOException {
        addToQueue(client);
        System.out.println("Sending queue joined response to " + client.getUsername());
        client.sendResponse(new Response(ResponseType.QUEUE_JOINED));
        if(queue.size() >= 2){
            System.out.println("Queue as two players, attempting to create instance");
            ConnectedClient player1 = takeFromQueue();
            ConnectedClient player2 = takeFromQueue();

            System.out.println("Players found: " + player1.getUsername() + " vs " + player2.getUsername());
            GameInstance instance = new GameInstance(player1, player2);
            player1.setInstance(instance);
            player2.setInstance(instance);
        }
    }

    public ConnectedClient takeFromQueue() {
        return queue.removeFirst();
    }

    public void addToQueue(ConnectedClient client){
        if(!queue.contains(client)){
            queue.add(client);
        }
    }

    public void removeFromQueue(ConnectedClient client){
        queue.remove(client);
    }

}
