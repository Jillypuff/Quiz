package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements ClientQueueManager{

    private final ServerSocket serverSocket;
    private List<ClientConnection> queue;
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
                ClientConnection client = new ClientConnection(socket, protocol);
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
    public void putInQueue(ClientConnection client) {
        queue.add(client);
    }

    @Override
    public List<ClientConnection> getQueue(){
        return queue;
    }

    @Override
    public ClientConnection takeFromQueue() {
        return queue.removeFirst();
    }

    @Override
    public void removeFromQueue(ClientConnection client){
        queue.remove(client);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(55554);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}

