package server;

import client.Request;
import client.RequestType;

public class Event {

    private Request request;
    private ConnectedClient client;

    public Event(Request request, ConnectedClient client) {
        this.request = request;
        this.client = client;
    }

    public Request getRequest() {
        return request;
    }

    public ConnectedClient getClient() {
        return client;
    }

    public RequestType getRequestType(){
        return request.getType();
    }
}
