package server;

import java.util.List;

public interface ClientQueueManager {

    void putInQueue(ConnectedClient client);

    List<ConnectedClient> getQueue();

    ConnectedClient takeFromQueue();

    void removeFromQueue(ConnectedClient client);
}
