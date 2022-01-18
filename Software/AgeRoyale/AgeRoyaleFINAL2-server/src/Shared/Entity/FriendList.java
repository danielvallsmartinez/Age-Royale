package Shared.Entity;

import java.io.Serializable;
import java.util.LinkedList;

public class FriendList implements Serializable {

    private LinkedList<Friend> friends;

    public FriendList() {

        friends = new LinkedList<>();
    }

    public LinkedList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(LinkedList<Friend> friends) {
        this.friends = friends;
    }
}
