import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {


    int port = 55554;
    private Socket socket;
    List<GameInstance> games;

    Server(){
        games = new ArrayList<>();
        try(ServerSocket serverSocket = new ServerSocket(port)){

            System.out.println("Server started");
            socket = serverSocket.accept();
            ConnectedClient client = new ConnectedClient(socket);
            new Thread(client).start();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) {
        new Server();
    }
}
