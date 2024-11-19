package server;

import client.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ConnectedClient implements Runnable {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    private String username;
    static List<ConnectedClient> connectedClients = new ArrayList<>();
    static List<ConnectedClient> queuedClients = new ArrayList<>();

    public ConnectedClient(Socket socket){
        this.socket = socket;
        this.username = username;
        connectedClients.add(this);
    }

    public void queueClient(ConnectedClient client){
        queuedClients.add(client);
        if (queuedClients.size() >= 2){
            ConnectedClient player1 = queuedClients.removeFirst();
            ConnectedClient player2 = queuedClients.removeFirst();

            GameInstance gameInstance = new GameInstance(player1, player2);
        }
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
            in = new ObjectInputStream(socket.getInputStream());
            ServerProtocol protocol = new ServerProtocol();

            while(in.readObject() instanceof Request request){
                protocol.processRequest(request, this);
            }

//            Question question = new Question("What city is the capital of Sweden?", "Stockholm",
//                    Arrays.asList("Paris", "London", "Stockholm", "Budapest"));
//            out.writeObject(question);
//            Object objIn = in.readObject();
//            if (objIn instanceof String answer) {
//                if (question.checkAnswer(answer)) {
//                    out.writeObject("CORRECT!");
//                } else {
//                    out.writeObject("WRONG");
//                }
//            }
        } catch (Exception e) {
            closeEverything(socket, out, in);
        }
    }

    public void closeEverything(Socket socket, ObjectOutputStream out, ObjectInputStream in){
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
