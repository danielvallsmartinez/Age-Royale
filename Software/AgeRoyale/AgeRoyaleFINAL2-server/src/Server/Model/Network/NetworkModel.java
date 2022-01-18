package Server.Model.Network;

public class NetworkModel {

    private int connectionPort;
    private String ip;
    private String database_Name;
    private String access_User;
    private String password;
    private int communicationPort;
    private String url;

    public int getConnectionPort() {
        return connectionPort;
    }

    public String getDatabase_Name() {
        return database_Name;
    }

    public String getAccess_User() {
        return access_User;
    }

    public String getPassword() {
        return password;
    }

    public int getCommunicationPort() {
        return communicationPort;
    }

    public String getUrl() {
        return url;
    }
}