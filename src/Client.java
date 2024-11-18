import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    String username;
    GameGUI gameGUI;

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

    public void startListening() {
        new Thread(() -> {
//            try {
//                while(in.readObject() instanceof Response response){
//                    if(response.type == ResponseType.CONNECTION_ESTABLISHED){
//                        //stäng log in fönstret och öppna spelfönstret?
//                    }
//                    else if (response.type == ResponseType.QUESTION){
//                        //skriv ut frågan i fönstret och alternativen
//                    }
//                    else if (response.type == ResponseType.ANSWER_EVALUATED){
//                        //skriv ut om spelaren hade rätt eller fel
//                    }
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
        }).start();
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

    public void run() {
        gameGUI = new GameGUI(this);
        gameGUI.showLoginWindow();
    }

    public void startGame(String username) {
        this.username = username;

        try {
            socket = new Socket("localhost", 55554);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(username);
            gameGUI.showMenu();

            startConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client(null);
        client.run();
    }
}
