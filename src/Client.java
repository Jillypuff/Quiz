import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public static final int PORT = 55554;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;


    public Client() throws IOException {

        socket = new Socket(InetAddress.getLocalHost(), PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        startListening();
        startSending();

    }

    public void startSending() {
        new Thread(() -> {
            Scanner userInput = new Scanner(System.in);
            String userOutput;
            while((userOutput = userInput.nextLine()) != null) {
                if (userOutput.equalsIgnoreCase("exit")) {
                    close();
                }
                try {
                    out.writeObject(userOutput);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }

    public void sendConnectRequest() throws IOException {
        out.writeObject(new Request(RequestType.CONNECT));
    }

    public void startListening() {
        new Thread(() -> {
            try {
                while(in.readObject() instanceof Response response){
                    if(response.type == ResponseType.CONNECTION_ESTABLISHED){
                        //stäng log in fönstret och öppna spelfönstret?
                    }
                    else if (response.type == ResponseType.QUESTION){
                        //skriv ut frågan i fönstret och alternativen
                    }
                    else if (response.type == ResponseType.ANSWER_EVALUATED){
                        //skriv ut om spelaren hade rätt eller fel
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readQuestion(Question question){
        System.out.println(question.question);
        for (String alternative : question.alternatives){
            System.out.println(alternative);
        }
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
