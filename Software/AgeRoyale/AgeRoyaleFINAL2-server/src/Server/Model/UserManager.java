package Server.Model;

import Server.Model.DataBase.DBConnector;
import Server.Model.Network.NetworkModel;
import Server.Model.Network.Server;
import Shared.Entity.User;

import javax.swing.*;
import java.sql.*;

public class UserManager {

    private NetworkModel networkModel;
    private User user;

    public UserManager(NetworkModel networkModel) {
        this.networkModel = networkModel;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int user_login(User u, Server server) throws SQLException {

        try {

            Connection conexion = DriverManager.getConnection(networkModel.getUrl(), networkModel.getAccess_User(), networkModel.getPassword());
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select name, password, id_user, mail, banned, bannedDate from User ");

            String usuarioBases;
            String contraseñaBases;

            while (rs.next()) {

                usuarioBases = rs.getString(1);
                contraseñaBases = rs.getString(2);

                //Si coincide el usuario i contraseña mostramos mensaje de login correcto y si no són iguales muestra mensaje de error
                if (u.getName().equals(usuarioBases) && u.getPassword().equals(contraseñaBases)) {

                    if (server.isConnected(rs.getInt(3))) {

                        return 4;
                    }
                    else {
                        if (rs.getInt(5) == 0) {
                            user.setMail(rs.getString(4));
                            user.setId_user(rs.getInt(3));
                            return 0;
                        }
                        else {
                            Long longOld = rs.getLong(6);
                            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                            Long longCurrent = currentTimestamp.getTime();
                            long difference = compareTwoTimeStamps(longCurrent, longOld);
                            System.out.println("Difference: "+difference);
                            if (24 > difference) {
                                return 5;
                            }
                            else {
                                user.setMail(rs.getString(4));
                                user.setId_user(rs.getInt(3));
                                notBannedUser(user.getId_user());
                                return 0;
                            }
                        }


                    }
                }
            }

            return 1;
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error");
            return 3;
        }
    }

    public int user_register (User u) {

        try {

            Connection conexion = DriverManager.getConnection(networkModel.getUrl(), networkModel.getAccess_User(), networkModel.getPassword());
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select name, mail, password from User ");

            String userName;
            String userMail;
            String userPassword = "null";

            if (!rs.next()) {

                PreparedStatement insert;
                insert = conexion.prepareStatement("INSERT INTO User (id_user, name, mail, password, banned) VALUES ("+"'"+1+"'"+", "+"'"+u.getName()+"'"+", "+"'"+u.getMail()+"'"+", "+"'"+u.getPassword()+"', false)");
                insert.execute();
                insert.close();
                u.setId_user(1);
                return 0;
            }
            else {

                rs.first();
                int i;
                for (i = 2; rs.next(); i++);  //Empieza contando en 2 porque se supone que ya hay coomo minimo un usuario registrado, y el id del primero es 1

                rs.first();
                do {
                    userName = rs.getString(1);
                    userMail = rs.getString(2);
                    userPassword = rs.getString(3);

                    if (u.getName().equals(userName)) {
                        //El nom està pillat
                        return 1;
                    }
                    else {
                        if (u.getMail().equals(userMail)) {
                            //el mail està pillat
                            return 2;
                        }
                    }
                } while (rs.next());

                //S'ha registrat correctament
                PreparedStatement insert;
                insert = conexion.prepareStatement("INSERT INTO User (id_user, name, mail, password, banned) VALUES ("+"'"+i+"'"+", "+"'"+u.getName()+"'"+", "+"'"+u.getMail()+"'"+", "+"'"+u.getPassword()+"', false)");
                insert.execute();
                insert.close();
                user.setId_user(i);
                return 0;
            }
        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Connection error");
            return 3;
        }
    }

    public User getUser() {
        return user;
    }

    public static long compareTwoTimeStamps(Long milliseconds2, Long milliseconds1)
    {
        long diff = milliseconds2 - milliseconds1;
        long diffHours = diff / (60 * 60 * 1000);

        return diffHours;
    }

    public void notBannedUser(int idUser) {

        String query = "UPDATE User SET banned = 0 WHERE id_user = "+idUser+";";
        DBConnector.getInstance(networkModel).updateQuery(query);

    }
}
