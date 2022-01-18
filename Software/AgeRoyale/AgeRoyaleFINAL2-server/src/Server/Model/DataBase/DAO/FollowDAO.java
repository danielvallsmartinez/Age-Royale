package Server.Model.DataBase.DAO;

import Server.Model.DataBase.DBConnector;
import Server.Model.Network.Server;
import Shared.Entity.*;
import Server.Model.Network.NetworkModel;

import java.sql.*;
import java.util.LinkedList;

public class FollowDAO {

    private NetworkModel networkModel;

    public FollowDAO(NetworkModel networkModel) {
        this.networkModel = networkModel;
    }

    public void addRequest(int idFrom, int idTo) throws SQLException {
        String query = "INSERT INTO Request (fromUser, toUser) VALUES ((SELECT id_user FROM User WHERE id_user = "+idFrom+"), (SELECT id_user FROM User WHERE id_user = "+idTo+"));";
        Connection conexion = DriverManager.getConnection(networkModel.getUrl(), networkModel.getAccess_User(), networkModel.getPassword());
        PreparedStatement add;
        add = conexion.prepareStatement(query);
        add.execute();
        add.close();
    }

    public void deleteRequest(int idFrom, int idTo) throws SQLException {
        Connection conexion = DriverManager.getConnection(networkModel.getUrl(), networkModel.getAccess_User(), networkModel.getPassword());
        PreparedStatement delete;
        delete = conexion.prepareStatement("DELETE FROM Request WHERE (fromUser = "+idFrom+" AND toUser = "+idTo+") OR (fromUser = "+idTo+" AND toUser = "+idFrom+");");
        delete.execute();
        delete.close();
    }

    public FriendList getFriends(int id_user, Server server) throws SQLException {
        FriendList follows = new FriendList();
        String query = "select u.name, f.joinedUser from Follow as f, User as u where f.creatorUser = " + id_user + " and f.joinedUser = u.id_user;";
        ResultSet resultat = DBConnector.getInstance(networkModel).selectQuery(query);

        try{
            while(resultat.next()){

                String name = resultat.getString(1);
                int id = resultat.getInt(2);
                Friend friend = new Friend(id, name);

                if (!server.isConnected(id)) {
                    friend.setConnected(false);
                }

                follows.getFriends().add(friend);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resultat.close();
        query = "select u.name, f.creatorUser from Follow as f, User as u where f.joinedUser = " + id_user + " and f.creatorUser = u.id_user;";

        resultat = DBConnector.getInstance(networkModel).selectQuery(query);
        try{
            while(resultat.next()){

                String name2 = resultat.getString(1);
                int id2 = resultat.getInt(2);
                Friend friend2 = new Friend(id2, name2);

                if (!server.isConnected(id2)) {
                    friend2.setConnected(false);
                }

                follows.getFriends().add(friend2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return follows;
    }

    public RequestList getRequests(User user) throws SQLException {
        RequestList requests = new RequestList();
        String query = "select u.name from Request as r, User as u where r.toUser = " + user.getId_user() + " and r.fromUser = u.id_user;";
        ResultSet resultat = DBConnector.getInstance(networkModel).selectQuery(query);
        try{
            while(resultat.next()){

                String name = resultat.getString(1);
                Request request = new Request(name , user.getName());
                requests.getRequests().add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resultat.close();

        return requests;
    }
}
