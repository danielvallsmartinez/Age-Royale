package Shared.Entity;

import Client.Controller.MenuController;
import Server.Model.GameManagement;
import Server.Model.Network.DedicatedServer;
import Shared.Entity.Troop;

import java.io.IOException;

import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class AttackTroop extends Troop implements Serializable {

    private int velocity;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> velocityHandle;
    private int comptador;
    private int lastMove;  //0 horizontal, 1 vertcial

    public AttackTroop(String name, int health, int damage, int attackRange, String photoURL, int posx, int posy, int velocity, Board board, User user) {

        super(name, health, damage, attackRange, photoURL, posx, posy,  board, user);
        this.velocity = velocity;
        lastMove = 0;
        setDefensive(false);
        //setTimer();

    }

    public void startTimer() {

        comptador = 0;

        final Runnable velocitat = new Runnable() {
            public void run() {
                try {

                    if(comptador < velocity){

                        if (getUser().getDedicatedServer().getServer().findGame(getUser()).getCreatorUser().equals(getUser())) {
                            getUser().getDedicatedServer().getServer().sendBoard(getBoard(), getUser().getName());
                        }
                        if (getUser().getDedicatedServer().getServer().findGame(getUser()).getJoinedUser().equals(getUser())) {
                            getUser().getDedicatedServer().getServer().sendBoard(GameManagement.giraTaulell(getBoard()), getUser().getName());
                        }

                    }else{
                        //System.out.println("jugador: "+AttackTroop.super.getUser().getName()+" antes de posicio");

                        getUser().getDedicatedServer().getServer().moveTroop(AttackTroop.this);
                    }
                    comptador = velocity;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        velocityHandle = scheduler.scheduleAtFixedRate(velocitat, 0, velocity, MILLISECONDS);

    }

    public void stopTimer() {

        velocityHandle.cancel(true);
    }
}
