public class ServerProtocol {

    ServerProtocol(ClientRequest request, ConnectedClient client) {
        switch (request) {
            // Main window requests
            case ClientRequest.START_GAME -> client.queueClient(client);
            case ClientRequest.EXIT_GAME -> client.disconnect(client);
            case ClientRequest.LEAVE_QUEUE -> client.leaveQueue(client);

            // Game window requests
            case ClientRequest.ANSWER_ONE -> client.questionAnswer(1);
            case ClientRequest.ANSWER_TWO -> client.questionAnswer(2);
            case ClientRequest.ANSWER_THREE -> client.questionAnswer(3);
            case ClientRequest.ANSWER_FOUR -> client.questionAnswer(4);
            case ClientRequest.GIVE_UP -> client.leaveGame(client);
        }
    }
}
