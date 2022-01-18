package Server.Model.DataBase;

import Server.Model.Network.NetworkModel;
import Shared.Entity.Game;
import Shared.Entity.GameList;
import Shared.Entity.User;

import javax.swing.*;
import java.sql.*;

public class GameStorage {

    private NetworkModel networkModel;
    private GameList gamelist;
    private boolean envia;

    public GameStorage (NetworkModel networkModel) {
        this.networkModel = networkModel;
        gamelist = new GameList();
        envia = false;
    }

    public void addGameList(Game game) {

            gamelist.getAvailableGames().addFirst(game);
            envia = true;

    }

    public void removeGameFromList(Game game) {

        for(int i = 0; i < gamelist.getAvailableGames().size(); i++){

            if (gamelist.getAvailableGames().get(i).getId_game() == game.getId_game()){

                gamelist.getAvailableGames().remove(i);
            }
        }

        envia = true;
    }

    public boolean updateGameList(){

        if(envia){

            envia = false;
            return true;

        }else{

            return false;

        }
    }

    public GameList getGamelist() {
        return gamelist;
    }
}