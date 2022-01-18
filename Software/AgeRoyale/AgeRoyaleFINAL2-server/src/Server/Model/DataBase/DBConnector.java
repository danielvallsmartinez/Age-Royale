package Server.Model.DataBase;

import Server.Model.Network.NetworkModel;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
    private static String userName;
    private static String password;
    private static String db;
    private static int port;
    private static String url;
    private static Connection conn;
    private static Statement s;
    private static DBConnector instance;

    private DBConnector(NetworkModel networkModel) {
        this.userName = networkModel.getAccess_User();
        this.password = networkModel.getPassword();
        this.db = networkModel.getDatabase_Name();
        this.port = networkModel.getConnectionPort();
        this.url = networkModel.getUrl();
        this.instance = null;
    }

    public static DBConnector getInstance(NetworkModel networkModel){
        if(instance == null){
            instance = new DBConnector(networkModel);
            instance.connect();
        }
        return  instance;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = (Connection) DriverManager.getConnection(url, userName, password);
            if (conn != null) {
                System.out.println("Connexió a base de dades "+url+" ... Ok");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+url);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void insertQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
            System.out.println(ex.getMessage());
        }
    }

    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Modificar --> " + ex.getMessage());
        }
    }

    public ResultSet selectQuery(String query) {
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getMessage());
        }
        return rs;
    }
}