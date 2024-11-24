package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final ServerSocket serverSocket;
    private List<ConnectedClient> queue;
    private GameManager gameManager;
    private final ServerProtocol protocol;

    Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.gameManager = new GameManager(this);
        protocol = new ServerProtocol(gameManager);
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

    public void putInQueue(ConnectedClient client) {
        queue.add(client);
    }

    public List<ConnectedClient> getQueue(){
        return queue;
    }

    public ConnectedClient takeFromQueue() {
        return queue.removeFirst();
    }

    public void removeFromQueue(ConnectedClient client){
        queue.remove(client);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(55554);
        Server server = new Server(serverSocket);
        server.startServer();
    }

}

