package Shared.Entity;

import java.io.Serializable;

public class Friend implements Serializable {

    private int id;
    private String name;
    private boolean connected;

    public Friend (int id, String name) {
        this.id = id;
        this.name = name;
        connected = true; //TEMPORAL
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
