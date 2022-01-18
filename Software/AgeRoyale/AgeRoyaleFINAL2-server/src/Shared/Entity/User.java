package Shared.Entity;

import Server.Model.GameManagement;
import Server.Model.Network.DedicatedServer;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class User implements Serializable {

    private static final long serialVersionUID = 123457L;

    private int id_user;
    private String name;
    private String mail;
    private String password;
    private int coin;
    private LinkedList<Troop> troops;
    private FriendList friendList;
    private RequestList requestList;
    private LinkedList<AttackTroop> attackTroops;
    private LinkedList<DefensiveTroop> defensiveTroops;
    private Tower tower;
    private DedicatedServer dedicatedServer;
    private boolean banned;
    //private Timer timer;

    public User(int id_user, String name, String mail, String password) {
        this.id_user = id_user;
        this.name = name;
        this.mail = mail;
        this.password = password;
        friendList = new FriendList();
        requestList = new RequestList();
        troops = new LinkedList<>();
        attackTroops = new LinkedList<>();
        defensiveTroops = new LinkedList<>();
        //timer = new Timer();
        coin = 0;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        troops = new LinkedList<>();
        attackTroops = new LinkedList<>();
        defensiveTroops = new LinkedList<>();
        //timer = new Timer();
        coin = 0;
    }

    public User() {
    }

    /*
    TimerTask coins = new TimerTask() {
        @Override
        public void run() {


            if( coin < 10) {

                coin++;
                try {
                    dedicatedServer.agafaMonedes(coin);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
    };
    public void setTimer() {
        timer.schedule(coins, 2000, 2000);
    }*/

    /*public void startCoins() {

        final Runnable velocitat = new Runnable() {
            public void run() {
                if( coin < 10) {

                    coin++;
                    try {
                        dedicatedServer.agafaMonedes(coin);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        final ScheduledFuture<?> velocityHandle = scheduler.scheduleAtFixedRate(velocitat, 0, 2000, MILLISECONDS);
    }*/

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public LinkedList<Troop> getTroops() {
        return troops;
    }

    public void setTroops(LinkedList<Troop> troops) {
        this.troops = troops;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isBanned() {
        return banned;
    }

    public FriendList getFriendList() {
        return friendList;
    }

    public void setFriendList(FriendList friendList) {
        this.friendList = friendList;
    }

    public void setRequestList(RequestList requestList) {
        this.requestList = requestList;
    }

    public RequestList getRequestList() {
        return requestList;
    }

    public void setDedicatedServer(DedicatedServer dedicatedServer) {
        this.dedicatedServer = dedicatedServer;
    }

    public DedicatedServer getDedicatedServer() {
        return dedicatedServer;
    }

    public LinkedList<AttackTroop> getAttackTroops() {
        return attackTroops;
    }

    public LinkedList<DefensiveTroop> getDefensiveTroops() {
        return defensiveTroops;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public Tower getTower() {
        return tower;
    }

    public void setAttackTroops(LinkedList<AttackTroop> attackTroops) {
        this.attackTroops = attackTroops;
    }

    public void setDefensiveTroops(LinkedList<DefensiveTroop> defensiveTroops) {
        this.defensiveTroops = defensiveTroops;
    }
}
