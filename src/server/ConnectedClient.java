package server;

import client.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {

    ServerProtocol protocol;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    public String username;

    public ConnectedClient(Socket socket, ServerProtocol protocol) {
        this.socket = socket;
        this.protocol = protocol;
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

            while(in.readObject() instanceof Request request){
                protocol.processRequest(request, this);
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
}
