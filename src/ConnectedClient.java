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

            while((in.readObject() instanceof Request request)){
                if (request.type == RequestType.CONNECT){
                    out.writeObject("Connected");
                }
                else if (request.type == RequestType.START_GAME){
                    Question question = new Question("What city is the capital of Sweden?", "Stockholm",
                            Arrays.asList("Paris", "London", "Stockholm" , "Budapest"));
                    out.writeObject(question);
                }
                else if (request.type == RequestType.EVALUATE_ANSWER){
                    if (request.playerAnswer.equals(request.question.answer)){
                        out.writeObject("Correct");
                    }
                    else{
                        out.writeObject("Wrong");
                    }
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
