package server;

import java.util.List;

public interface ClientQueueManager {

    void putInQueue(ClientConnection client);

    List<ClientConnection> getQueue();

    ClientConnection takeFromQueue();

    void removeFromQueue(ClientConnection client);
}
