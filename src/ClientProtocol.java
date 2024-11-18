public class ClientProtocol {

    ClientProtocol(ServerResponse response, ConnectedClient client) {
        switch (response){
            case ServerResponse.CLIENT_CONNECTED -> client.connectSuccess();
            case ServerResponse.CLIENT_DISCONNECTED -> client.disconnectSuccess();

            case ServerResponse.QUEUE_JOINED -> client.queueClient(client);
            case ServerResponse.GAME_JOINED -> client.gameStart(client);
        }
    }

    ClientProtocol(QuizActions action, ConnectedClient client){
        switch (action){
            case QuizActions.SEND_QUESTION -> client.recieveQuestion(question);
            case QuizActions.SEND_ANSWER -> client.recieveAnswer(answer);

            case QuizActions.SEND_SCORE -> client.recieveScore(score);
            case QuizActions.SEND_FINAL_RESULT -> client.recieveFinalResult(result);
        }
    }
}
