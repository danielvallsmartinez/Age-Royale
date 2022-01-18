package Server;

import Server.Controller.Controller;
import Server.Model.Network.JSONReader;
import Server.Model.Network.Server;
import Server.View.ServerView;

public class MainServer {

    public static void main(String[] args) {
        ServerView serverView = new ServerView();


        JSONReader jsonReader = new JSONReader();
        jsonReader.read();

        Controller controller = new Controller(jsonReader.getNetworkModel());
        serverView.Controller(controller);

        Server server = new Server(jsonReader.getNetworkModel());
        server.startServer();
    }
}
