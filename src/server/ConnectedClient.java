package server;

import client.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// CONNECTEDCLIENT HANTERAR KOMMUNIKATIONEN MED JUST DENNA SOCKET, DVS SOM KOMMER FRÅN/TILL JUST DEN CLIENT SOM ANSLUTIT PÅ DENNA SOCKET
public class ConnectedClient implements Runnable {

    private ServerResponseDispatcher dispatcher;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;

    //Istället för att ta emot en server, tar den emot en ServerResponseDispatcher
    public ConnectedClient(Socket socket, ServerResponseDispatcher dispatcher){
        this.socket = socket;
        this.dispatcher = dispatcher;
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

            // Istället för protocol.processRequest dispatchar vi iväg requesten
            // vi fått in + denna connectedclient i ett event objekt, till ServerDispatcher
            while(in.readObject() instanceof Request request){
                dispatcher.dispatch(new Event(request, this));
            }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }
}
