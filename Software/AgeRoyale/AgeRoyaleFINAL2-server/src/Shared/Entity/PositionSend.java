package Shared.Entity;

import java.io.Serializable;

public class PositionSend implements Serializable {

    private int positionX;
    private int positionY;
    private int numCard;
    private String playerName;

    public PositionSend(int positionX, int positionY, int numCard, String playerName) {
        this.playerName = playerName;
        this.numCard = numCard;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getNumCard() {
        return numCard;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public String getPlayerName() {
        return playerName;
    }
}
