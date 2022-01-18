package Shared.Entity;

import java.io.Serializable;
import java.util.LinkedList;

public class GameList implements Serializable{

    private LinkedList<Game> availableGames;

    public GameList() {
        availableGames = new LinkedList<>();
    }

    public LinkedList<Game> getAvailableGames() {
        return availableGames;
    }
}
