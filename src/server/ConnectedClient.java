package server;

import client.request.Request;
import gamelogic.CurrentGame;
import server.response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {

    private boolean running = true;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    private String username;
    GameInstance gameInstance;
    private RequestHandler requestHandler;

    private int score = 0;
    private int matchesWon = 0;

    public ConnectedClient(Socket socket){
        this.socket = socket;
    }

    public synchronized void sendResponse(Response response, RequestHandler requestHandler) throws IOException {
        this.requestHandler = requestHandler;
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

            while(running && !socket.isClosed()){
                Object obj = in.readObject();
                if (obj instanceof Request request){
                    requestHandler.processRequest(request, this);
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

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }


    public void increaseScore(int points) {
        this.score += points;
    }

    public void resetScore() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void incrementMatchesWon() {
        this.matchesWon++;
    }

    public int getMatchesWon() {
        return matchesWon;
    }
}
