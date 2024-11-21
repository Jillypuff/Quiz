package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    final private ServerSocket serverSocket;
    List<ConnectedClient> queue;

    Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        queue = new ArrayList<>();
        System.out.println("Server started");
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ConnectedClient client = new ConnectedClient(socket, this);
                new Thread(client).start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public void createInstance() throws IOException {
        if(queue.size() >= 2){
            ConnectedClient player1 = queue.removeFirst();
            ConnectedClient player2 = queue.removeFirst();
            System.out.println(player1.username + " " + player2.username);
            GameInstance instance = new GameInstance(player1, player2);
            broadcastInstance(instance);
        }
    }

    public void broadcastInstance(GameInstance instance) throws IOException {
        instance.playerOne.sendResponse(new Response(ReponseType.GAME_JOINED, instance.game));
        instance.playerTwo.sendResponse(new Response(ReponseType.GAME_JOINED, instance.game));
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

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(55554);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
