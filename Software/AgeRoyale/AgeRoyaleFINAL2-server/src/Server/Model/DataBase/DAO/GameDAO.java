package Server.Model.DataBase.DAO;

import Server.Model.DataBase.DBConnector;
import Shared.Entity.Game;
import Shared.Entity.User;
import Server.Model.Network.NetworkModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameDAO {

    private NetworkModel networkModel;
    private UserDAO userDAO = new UserDAO(networkModel);

    public GameDAO(NetworkModel networkModel) {

        this.networkModel = networkModel;
    }

    public void addGame(Game game) {
        if(!game.isPrivada()) {
            String query = "INSERT INTO Game(id_game, date, creatorUser, gameName, privada) VALUES ('" + game.getId_game() + "','" + game.getDate() + "',(SELECT id_user FROM User WHERE id_user = "+game.getCreatorUser().getId_user()+"),'" + game.getGameName() + "','0');";
            DBConnector.getInstance(networkModel).insertQuery(query);
        } else {
            String query = "INSERT INTO Game(id_game, date, creatorUser, gameName, privada) VALUES ('" + game.getId_game() + "','" + game.getDate() + "',(SELECT id_user FROM User WHERE id_user = "+game.getCreatorUser().getId_user()+"),'" + game.getGameName() + "','1');";
            DBConnector.getInstance(networkModel).insertQuery(query);
        }
    }

    public void addJoinedUser(int id_game, User user) {
        String query = "UPDATE Game SET joinedUSer ='"+user.getId_user()+"' WHERE id_game = '"+id_game+"';";
        DBConnector.getInstance(networkModel).updateQuery(query);
    }

    public void storeFinishedGame(Game game, int idCreator) {

        String query = "UPDATE Game SET gameTime = '"+game.getMinutes()+"', creatorUser = "+idCreator+", winner = '"+game.getWinner()+"' WHERE id_game = '"+game.getId_game()+"';";
        DBConnector.getInstance(networkModel).updateQuery(query);
    }


    public LinkedList<Game> getAllGames() throws SQLException {
        LinkedList<Game> games = new LinkedList<>();
        String query = "SELECT id_game, gameTime, date, winner, gameName, privada, creatorUser, joinedUser FROM Game;";
        ResultSet resultat = DBConnector.getInstance(networkModel).selectQuery(query);
        try{
            while(resultat.next()){
                int id_game = resultat.getInt(1);
                String gameTime = resultat.getString(2);
                String date = resultat.getString(3);
                int winner = resultat.getInt(4);
                String gameName = resultat.getString(5);
                boolean privada = resultat.getBoolean(6);
                int creatorUser = resultat.getInt(7);
                int joinedUser = resultat.getInt(8);

                Game game = new Game(id_game, gameTime, date, winner, gameName, privada, creatorUser, joinedUser);

                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public int countAllGames() {

        int numberOfGames = 0;

        String query = "SELECT COUNT(id_game) FROM Game;";
        ResultSet resultat = DBConnector.getInstance(networkModel).selectQuery(query);

        try {
            resultat.next();
            numberOfGames = resultat.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfGames;
    }

    public int[][] findTop10Users(){

        int[][] top10 = new int[10][3];
        int i = 0;
        String query = "SELECT COUNT(winner), winner, SUM(gameTime) FROM Game GROUP BY winner ORDER BY 1 DESC LIMIT 10;";
        ResultSet resultat = DBConnector.getInstance(networkModel).selectQuery(query);


        try{
            while(resultat.next()) {
                int nWins = resultat.getInt(1);
                int idUser = resultat.getInt(2);
                int gameTime = resultat.getInt(3);

                top10[i][0] = nWins;
                top10[i][1] = idUser;
                top10[i][2] = gameTime;

                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return top10;
    }


    public int[][] gamesPlayedPerUSer(){

        String query = "SELECT SUM(totalCreator + totalJoined) DIV 2, id_user FROM (SELECT COUNT(creatorUser) AS totalCreator, creatorUser, id_game FROM Game GROUP BY creatorUser, id_game) AS t1, (SELECT COUNT(joinedUser) AS totalJoined, joinedUser, id_game FROM Game GROUP BY joinedUser, id_game) AS t2, User WHERE t2.id_game = t1.id_game AND (User.id_user = t1.creatorUser OR User.id_user = t2.joinedUser) GROUP BY 2;";
        ResultSet resultat = DBConnector.getInstance(networkModel).selectQuery(query);

        int users = userDAO.countAllUsers();

        int[][] nGames = new int[users][2];
        int i = 0;

        try{
            while(resultat.next()) {
                int nOfGames = resultat.getInt(1);
                int idUser = resultat.getInt(2);

                nGames[i][0] = nOfGames;
                nGames[i][1] = idUser;

                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nGames;
    }
}
