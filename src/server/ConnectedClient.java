package server;

import Modules.Response;
import Modules.Request;
import gamelogic.GameInstance;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {

    private final ServerProtocol protocol;
    private final Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;
    private boolean running = true;
    private GameInstance instance;

    private boolean isReady = false;

    public ConnectedClient(Socket socket, ServerProtocol protocol){
        this.socket = socket;
        this.protocol = protocol;
    }

    public synchronized void sendResponse(Response response) throws IOException {
        out.writeObject(response);
        out.flush();
    }

    public void readyForNewRound(boolean isReady){
        this.isReady = isReady;
    }

    public boolean isReadyForNewRound(){
        return isReady;
    }

    @Override
    public void run() {
        try {
            System.out.println("Connected");
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            while(running && !socket.isClosed()){
                Object obj = in.readObject();
                if (obj instanceof Request request){
                    protocol.processRequest(request, this);
                }
            }

        } catch (Exception e) {
            closeEverything();
        }
    }

    public void stop(){
        running = false;
    }

    public void closeEverything(){
        stop();
        try{
            if (out != null){
                out.close();
            }
            if (in != null){
                in.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public GameInstance getInstance() {
        return instance;
    }

    public void setInstance(GameInstance instance) {
        this.instance = instance;
    }
}
