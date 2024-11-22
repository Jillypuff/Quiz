package server;

import client.request.Request;
import gamelogic.CurrentGame;
import server.response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {

    public Server server;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    public String username;
    private boolean running = true;
    CurrentGame currentGame;
    GameInstance gameInstance;

    public ConnectedClient(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    public synchronized void sendResponse(Response response) throws IOException {
        out.writeObject(response);
        out.flush();
    }

    @Override
    public void run() {
        try {
            System.out.println("Connected");
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            ServerProtocol protocol = new ServerProtocol();

            while(running && !socket.isClosed()){
                Object obj = in.readObject();
                if (obj instanceof Request request){
                    protocol.processRequest(request, this);
                }
            }

        } catch (Exception e) {
            closeEverything(socket, out, in);
        }
    }

    public void stop(){
        running = false;
    }

    public void closeEverything(Socket socket, ObjectOutputStream out, ObjectInputStream in){
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
}
