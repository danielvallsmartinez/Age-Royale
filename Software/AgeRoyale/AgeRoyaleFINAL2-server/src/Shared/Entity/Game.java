package Shared.Entity;

import Server.Model.GameManagement;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game implements Serializable {

    private int id_game;
    private String gameTime;
    private String date;
    private int winner;
    private User creatorUser;
    private User joinedUser;
    private String gameName;
    private boolean privada;
    private LinkedList<User> spectators;
    private boolean available;
    private int minutes;
    private String startHour;
    private String finishHour;
    private Board board;

    public Game(User creatorUser, String gameName) {

        this.creatorUser = creatorUser;
        this.gameName = gameName;
        this.id_game = id_game;
        gameTime = new String();
        date = new String();
        this.spectators = new LinkedList<>();
        available = true;
        board = new Board();
    }

    public Game(int id_game, String gameTime, String date, int winner, String gameName, boolean privada) {
        this.id_game = id_game;
        this.gameTime = gameTime;
        this.date = date;
        this.winner = winner;
        this.gameName = gameName;
        this.privada = privada;
        board = new Board();
    }

    public Game(int id_game, String gameTime, String date, int winner, String gameName, boolean privada, int creatorUser, int joinedUser) {
        this.id_game = id_game;
        this.gameTime = gameTime;
        this.date = date;
        this.winner = winner;
        this.gameName = gameName;
        this.privada = privada;
        this.creatorUser = new User();
        this.creatorUser.setId_user(creatorUser);
        this.joinedUser = new User();
        this.joinedUser.setId_user(joinedUser);
    }

    public void setJoinedUser(User joinedUser) {
        this.joinedUser = joinedUser;
        available = false;
    }

    public void addSpectator(User spectator) {

        spectators.add(spectator);
    }

    public User getCreatorUser() {
        return creatorUser;
    }

    public User getJoinedUser() {
        return joinedUser;
    }

    public String getGameName() {
        return gameName;
    }

    public boolean getAvailable() {
        return available;
    }

    public int getId_game() {
        return id_game;
    }

    public void setId_game(int id_game) {
        this.id_game = id_game;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public boolean isPrivada() {
        return privada;
    }

    public void setPrivada(boolean privada) {
        this.privada = privada;
    }

    public boolean comprovaPosicions(PositionSend positions) {
        //a implementaaaar
        return false;
    }

    public LinkedList<User> getSpectators() {
        return spectators;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(String finishHour) {
        this.finishHour = finishHour;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }
}
