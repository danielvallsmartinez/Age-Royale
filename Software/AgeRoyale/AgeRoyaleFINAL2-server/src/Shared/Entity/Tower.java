package Shared.Entity;

import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Tower extends Troop implements Serializable {

    private int comptador;
    private double[] posicio;
    private int[] posX;
    private int[] posY;

    public Tower (Board board, User user) {

        super("tower", 20, 0, 0, " ", -1, -1,  board, user);
        posX = new int[6];
        posX[0] = 4;
        posX[1] = 4;
        posX[2] = 4;
        posX[3] = 5;
        posX[4] = 5;
        posX[5] = 5;
        posY = new int[6];
        posY[0] = 16;
        posY[1] = 18;
        posY[2] = 19;
        posY[3] = 17;
        posY[4] = 18;
        posY[5] = 19;
    }

    public int[] getPosX() {
        return posX;
    }

    public int[] getPosY() {
        return posY;
    }
}
