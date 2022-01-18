package Server.Model.DataBase.DAO;
import Server.Model.DataBase.DBConnector;
import Shared.Entity.User;
import Server.Model.Network.NetworkModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

public class UserDAO {

    private NetworkModel networkModel;

    public UserDAO(NetworkModel networkModel) {

        this.networkModel = networkModel;
    }

    public int countAllUsers(){
        int numberOfUsers = 0;

        String query = "SELECT COUNT(id_user) FROM User;";
        ResultSet resultat = DBConnector.getInstance(networkModel).selectQuery(query);

        try {
            resultat.next();
            numberOfUsers = resultat.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfUsers;
    }

    public LinkedList<User> getAllUsers() {
        LinkedList<User> users = new LinkedList<>();
        String query = "SELECT id_user, name, mail, password FROM User;";
        ResultSet resultat = DBConnector.getInstance(networkModel).selectQuery(query);
        try{
            while(resultat.next()){
                int id_user = resultat.getInt("id_user");
                String name = resultat.getString("name");
                String mail = resultat.getString("mail");
                String password = resultat.getString("password");
                users.add(new User(id_user, name, mail, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void banUser(int idUser, Timestamp timestamp) {
        long timeMillisecs = timestamp.getTime();
        String query = "UPDATE User SET banned = 1, bannedDate = '"+timeMillisecs+"' WHERE id_user = '"+idUser+"';";
        DBConnector.getInstance(networkModel).updateQuery(query);
    }
}
