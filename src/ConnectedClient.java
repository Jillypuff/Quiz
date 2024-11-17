import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;


public class ConnectedClient implements Runnable {

    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ConnectedClient(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            System.out.println("Connected");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Question question = new Question("What city is the capital of Sweden?", "Stockholm",
                    Arrays.asList("Paris", "London", "Stockholm" , "Budapest"));
            out.writeObject(question);
            Object objIn = in.readObject();
            if(objIn instanceof String answer){
                if (question.checkAnswer(answer)){
                    out.writeObject("CORRECT!");
                }
                else{
                    out.writeObject("WRONG");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {

            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
