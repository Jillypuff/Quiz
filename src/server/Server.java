package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    final private ServerSocket serverSocket;
    ServerProtocol protocol;
    List<ConnectedClient> queue;

    Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        protocol = new ServerProtocol();
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

    public void handleStartGame(ConnectedClient client) throws IOException {
        queue.add(client);
//        client.sendResponse(new Response(ResponseType.QUEUE_JOINED));
        if(queue.size() >= 2){
            startGame();
        }
    }

    public void startGame() throws IOException {
        ConnectedClient player1 = queue.removeFirst();
        ConnectedClient player2 = queue.removeFirst();
        System.out.println(player1.username + " " + player2.username);
        GameInstance instance = new GameInstance(player1, player2);
        broadcastInstance(instance);
    }

    public void broadcastInstance(GameInstance instance) throws IOException {
        instance.playerOne.sendResponse(new Response(ResponseType.GAME_JOINED, instance.game));
        instance.playerTwo.sendResponse(new Response(ResponseType.GAME_JOINED, instance.game));
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
