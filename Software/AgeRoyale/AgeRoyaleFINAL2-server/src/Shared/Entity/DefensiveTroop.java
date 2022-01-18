package Shared.Entity;

import Server.Model.GameManagement;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class DefensiveTroop extends Troop implements Serializable {

    private int attackFrequency;
    private int comptador;
    private double[] posicio;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> velocityHandle;


    public DefensiveTroop(String name, int health, int damage, int attackRange, String photoURL, int posx, int posy, Board board, User user, Board boardGirat, int attackFrequency) {

        super(name, health, damage, attackRange, photoURL, posx, posy, board, user);
        this.attackFrequency = attackFrequency;
        setDefensive(true);
    }

    public void startTimer() {

        comptador = 0;

        final Runnable velocitat = new Runnable() {
            public void run() {
                try {
                    if(comptador < attackFrequency){

                        if (getUser().getDedicatedServer().getServer().findGame(getUser()).getCreatorUser().equals(getUser())) {
                            getUser().getDedicatedServer().getServer().sendBoard(getBoard(), getUser().getName());
                        }
                        if (getUser().getDedicatedServer().getServer().findGame(getUser()).getJoinedUser().equals(getUser())) {
                            getUser().getDedicatedServer().getServer().sendBoard(GameManagement.giraTaulell(getBoard()), getUser().getName());
                        }

                    }else{

                        getUser().getDedicatedServer().getServer().moveTroop(DefensiveTroop.this);
                    }
                    comptador = attackFrequency;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        velocityHandle = scheduler.scheduleAtFixedRate(velocitat, 0, attackFrequency, MILLISECONDS);
    }

    public void setPosicio(double[] posicio) {
        this.posicio = posicio;
    }

    public double[] getPosicio() {
        return posicio;
    }

    public void stopTimer() {

        velocityHandle.cancel(true);
    }

}
