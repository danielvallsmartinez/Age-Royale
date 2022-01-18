package Shared.Entity;

import java.io.Serializable;
import java.util.LinkedList;

public class RequestList implements Serializable {

    private LinkedList<Request> requests;

    public RequestList() {
        requests = new LinkedList<>();
    }

    public LinkedList<Request> getRequests() {
        return requests;
    }
}
