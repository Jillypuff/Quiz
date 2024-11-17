import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static final int PORT = 55554;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(Socket socket){
        try{
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e){
            closeEverything(socket, out, in);
        }
    }

    public void startConnection (){
        try {
            Scanner userInput = new Scanner(System.in);
            Object objIn;
            while ((objIn = in.readObject()) != null) {
                if (objIn instanceof Question question) {

                    readQuestion(question);
                    String answer = userInput.nextLine();
                    out.writeObject(answer);
                } else if (objIn instanceof String serverAnswer) {
                    System.out.println(serverAnswer);
                    break;
                }
            }
        } catch (IOException e){
            closeEverything(socket, out, in);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void readQuestion(Question question){
        System.out.println(question.question);
        for (String alternative : question.alternatives){
            System.out.println(alternative);
        }
    }

    public void closeEverything(Socket socket, ObjectOutputStream out, ObjectInputStream in){
        try {
            if (out != null){
                out.close();
            }
            if (in != null){
                in.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            System.out.println("Closed connection");
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 55554);
        Client client = new Client(socket);
        client.startConnection();
    }
}
