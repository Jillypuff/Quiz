package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements ClientQueueManager{

    private final ServerSocket serverSocket;
    private List<ConnectedClient> queue;
    private final ServerProtocol protocol;

    Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        protocol = new ServerProtocol(new GameManager(this));
        queue = new ArrayList<>();
    }

    public void startServer() {
        queue = new ArrayList<>();
        System.out.println("Server started");
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ConnectedClient client = new ConnectedClient(socket, protocol);
                new Thread(client).start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try  {
            if (serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Server socket closed.");
            System.exit(1);
        }
    }

    @Override
    public void putInQueue(ConnectedClient client) {
        queue.add(client);
    }

    @Override
    public List<ConnectedClient> getQueue(){
        return queue;
    }

    @Override
    public ConnectedClient takeFromQueue() {
        return queue.removeFirst();
    }

    @Override
    public void removeFromQueue(ConnectedClient client){
        queue.remove(client);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(55554);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}

