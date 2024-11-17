import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static final int PORT = 55554;

    public Client(){

        try(Socket socket = new Socket(InetAddress.getLocalHost(), PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            Scanner userInput = new Scanner(System.in);
            Object objIn;
            while((objIn = in.readObject()) != null){
                if(objIn instanceof Question question){

                    readQuestion(question);
                    String answer = userInput.nextLine();
                    out.writeObject(answer);
                }
                else if(objIn instanceof String serverAnswer){
                    System.out.println(serverAnswer);
                    break;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void readQuestion(Question question){
        System.out.println(question.question);
        for (String alternative : question.alternatives){
            System.out.println(alternative);
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
