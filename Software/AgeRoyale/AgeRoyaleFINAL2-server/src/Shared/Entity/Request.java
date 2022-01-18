package Shared.Entity;

import java.io.Serializable;

public class Request implements Serializable {

    private String from;
    private String to;

    public Request(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

}
