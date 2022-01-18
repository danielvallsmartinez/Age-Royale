package Shared.Entity;

import Server.Model.Network.DedicatedServer;

import java.io.Serializable;

public class Troop implements Serializable {

    private String name;
    private int health;
    private int damage;
    private int attackRange;
    private String photoURL;
    private int posx;
    private int posy;
    private Board board;
    private User user;
    private boolean isDefensive;
    private double[] posicio;
    private int lastMove;
    //private boolean castle;

    public Troop(String name, int health, int damage, int attackRange, String photoURL, int posx, int posy,  Board board, User user) {
        this.attackRange = attackRange;
        this.damage = damage;
        this.health = health;
        this.name = name;
        this.photoURL = photoURL;
        this.board = board;
        this.user = user;
        this.posx = posx;
        this.posy = posy;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public double calcularDistancia(int posxOut, int posyOut) {

        double x = Math.abs(posx - posxOut);
        double y = Math.abs(posy - posyOut);
        double distancia = Math.hypot(x,y);

        return distancia;

    }


    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    /*public boolean isCastle() {
        return castle;
    }

    public void setCastle(boolean castle) {
        this.castle = castle;
    }*/

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDefensive(boolean defensive) {
        isDefensive = defensive;
    }

    public boolean isDefensive() {
        return isDefensive;
    }

    public double[] getPosicio() {
        return posicio;
    }

    public void setPosicio(double[] posicio) {
        this.posicio = posicio;
    }

    public int getLastMove() {
        return lastMove;
    }

    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
    }
}
