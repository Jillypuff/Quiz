package server;

import client.request.Request;

public interface RequestHandler {
    void processRequest(Request request, ConnectedClient client);
}
