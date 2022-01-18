package Server.Model.Network;

import Server.Model.DataBase.GameStorage;
import Server.Model.GameManagement;
import Shared.Entity.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server {
    private NetworkModel networkModel;
    private int port;
    private ServerSocket serverSocket;
    private boolean running;
    private ArrayList<DedicatedServer> dedicatedServers;
    private GameStorage gameStorage;
    private RequestList requestList;
    private GameManagement gameManagement;

    public Server(NetworkModel networkModel) {
        dedicatedServers = new ArrayList<>();
        serverSocket = null;
        this.networkModel = networkModel;
        gameStorage = new GameStorage(networkModel);
        requestList = new RequestList();
        gameManagement = new GameManagement();
    }

    public void startServer() {

        port = networkModel.getCommunicationPort();


        try {
            serverSocket = new ServerSocket(port);
            running = true;

            while (running) {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                DedicatedServer dServer = new DedicatedServer(socket, networkModel, this);
                dedicatedServers.add(dServer);
                dServer.start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void gameReady(int id, User user) throws IOException {
        int k = 0;
        for (int i = 0; i < gameStorage.getGamelist().getAvailableGames().size(); i++) {
            if (gameStorage.getGamelist().getAvailableGames().get(i).getId_game() == id) {
                gameStorage.getGamelist().getAvailableGames().get(i).setAvailable(false);
                gameStorage.getGamelist().getAvailableGames().get(i).setJoinedUser(user);
                k = i;
            }
        }

        int j = 0;
        boolean found = false;

        while (j < dedicatedServers.size() && !found) {
            if (dedicatedServers.get(j).getUserManager().getUser() != null) {
                if (gameStorage.getGamelist().getAvailableGames().get(k).getCreatorUser().getName().equals(dedicatedServers.get(j).getUserManager().getUser().getName())) {

                    found = true;
                    j--;
                }
                j++;
            }
        }
        int u = 0;
        found = false;
        while (u < dedicatedServers.size() && !found) {
            if (dedicatedServers.get(u).getUserManager().getUser() != null) {
                if (gameStorage.getGamelist().getAvailableGames().get(k).getJoinedUser().getName().equals(dedicatedServers.get(u).getUserManager().getUser().getName())) {
                    found = true;
                    u--;
                }
                u++;
            }
        }

        dedicatedServers.get(j).getUserManager().getUser().setTower(new Tower(gameStorage.getGamelist().getAvailableGames().get(k).getBoard(), dedicatedServers.get(j).getUserManager().getUser()));
        dedicatedServers.get(u).getUserManager().getUser().setTower(new Tower(gameStorage.getGamelist().getAvailableGames().get(k).getBoard(), dedicatedServers.get(u).getUserManager().getUser()));
        dedicatedServers.get(j).sendConfirmationStartGame(gameStorage.getGamelist().getAvailableGames().get(k));
        dedicatedServers.get(u).sendConfirmationStartGame(gameStorage.getGamelist().getAvailableGames().get(k));
    }

    public Board spectatorJoined(int id, User user) {
        boolean found = false;
        Game game = null;
        for (int i = 0; i < gameStorage.getGamelist().getAvailableGames().size() && !found; i++) {
            if (gameStorage.getGamelist().getAvailableGames().get(i).getId_game() == id) {
                gameStorage.getGamelist().getAvailableGames().get(i).addSpectator(user);
                game = gameStorage.getGamelist().getAvailableGames().get(i);
                found = true;
            }
        }
        return game.getBoard();
    }

    public int quitPlay(int id, User user) throws IOException {
        boolean found = false;
        boolean exit;
        int i = 0;
        int j = 0;
        for (i = 0; i < gameStorage.getGamelist().getAvailableGames().size() && !found; i++) {
            if (gameStorage.getGamelist().getAvailableGames().get(i).getId_game() == id) {
                for (j = 0; j < gameStorage.getGamelist().getAvailableGames().get(i).getSpectators().size() && !found; j++) {
                    if (gameStorage.getGamelist().getAvailableGames().get(i).getSpectators().get(j) == user) {
                        found = true;
                    }
                }
            }
        }
        i--;
        j--;
        if (!found) {
            user.setBanned(true);
            if (gameStorage.getGamelist().getAvailableGames().get(i).getCreatorUser() == user) {
                int u = 0;
                exit = false;
                for (u = 0; u < dedicatedServers.size() && !exit; u++) {
                    if (gameStorage.getGamelist().getAvailableGames().get(i).getJoinedUser() == dedicatedServers.get(u).getUserManager().getUser()) {

                        dedicatedServers.get(u).sendUserLeft();
                        exit = true;
                    }
                }
                gameStorage.getGamelist().getAvailableGames().get(i).setWinner(gameStorage.getGamelist().getAvailableGames().get(i).getJoinedUser().getId_user());
            }
            else {
                int u = 0;
                exit = false;
                for (u = 0; u < dedicatedServers.size() && !exit; u++) {
                    if (gameStorage.getGamelist().getAvailableGames().get(i).getCreatorUser() == dedicatedServers.get(u).getUserManager().getUser()) {

                        dedicatedServers.get(u).sendUserLeft();
                        exit = true;
                    }
                }
                gameStorage.getGamelist().getAvailableGames().get(i).setWinner(gameStorage.getGamelist().getAvailableGames().get(i).getCreatorUser().getId_user());
            }

            return 11; //si el usuario ha sido baneado
        }
        else {
            gameStorage.getGamelist().getAvailableGames().get(i).getSpectators().remove(j);
            return 12; //si el usuario no ha sido baneado significa que es espectador
        }
    }

    public void sendActualizedGameListToEveryone() throws IOException {

        GameList auxiliarGameList = new GameList();

        for (int u = 0; u < gameStorage.getGamelist().getAvailableGames().size(); u++) {

            if (!gameStorage.getGamelist().getAvailableGames().get(u).isPrivada()) {
                auxiliarGameList.getAvailableGames().add(gameStorage.getGamelist().getAvailableGames().get(u));
            }
        }

        for (int i = 0; i < dedicatedServers.size(); i++) {
            if (dedicatedServers.get(i).getUserManager().getUser() != null) {
                dedicatedServers.get(i).sendGameList(auxiliarGameList);
            }
        }
    }

    public void sendActualizedRequestList(String name) throws IOException, SQLException {

        RequestList requestList;

        for (int i = 0; i < dedicatedServers.size(); i++) {
            if (dedicatedServers.get(i).getUserManager().getUser() != null) {
                if (dedicatedServers.get(i).getUserManager().getUser().getName().equals(name) || dedicatedServers.get(i).getUserManager().getUser().getMail().equals(name)) {

                    requestList = dedicatedServers.get(i).getFollowDAO().getRequests(dedicatedServers.get(i).getUserManager().getUser());
                    dedicatedServers.get(i).sendUserRequestList(requestList);
                }
            }
        }
    }

    public void addFriend(String name, Friend friend) throws IOException {
        for (int i = 0; i < dedicatedServers.size(); i++) {
            if (dedicatedServers.get(i).getUserManager().getUser() != null) {
                if (dedicatedServers.get(i).getUserManager().getUser().getName().equals(name)) {
                    dedicatedServers.get(i).getUserManager().getUser().getFriendList().getFriends().add(friend);
                    dedicatedServers.get(i).sendFriendList();
                }
            }
        }
    }

    public void deleteFriend(int id, int idFriend) throws IOException {
        boolean exit = false;
        int i = 0;
        int j = 0;
        for (i = 0; i < dedicatedServers.size() && !exit; i++) {
            if (dedicatedServers.get(i).getUserManager().getUser() != null) {
                if (dedicatedServers.get(i).getUserManager().getUser().getId_user() == id) {
                    for (j = 0; j < dedicatedServers.get(i).getUserManager().getUser().getFriendList().getFriends().size() && !exit; j++) {
                        if (dedicatedServers.get(i).getUserManager().getUser().getFriendList().getFriends().get(j).getId() == idFriend) {

                            dedicatedServers.get(i).getUserManager().getUser().getFriendList().getFriends().remove(j);
                            dedicatedServers.get(i).sendFriendList();
                            exit = true;
                            System.out.println("i val: " + i + ", j val: " + j);
                        }
                    }
                }
            }
        }
    }

    public boolean isConnected(int idFrom) {

        int numDedicatedServers = dedicatedServers.size();

        for (int i = 0; i < numDedicatedServers; i++) {
            if (dedicatedServers.get(i).getUserManager().getUser() != null) {
                if (dedicatedServers.get(i).getUserManager().getUser().getId_user() == idFrom) {

                    return true;
                }
            }
        }

        return false;
    }

    public void sendPrivateGame(int idFriendToInvite, User creatorUser) throws IOException {

        int numDedicatedServers = dedicatedServers.size();

        for (int i = 0; i < numDedicatedServers; i++) {
            if (dedicatedServers.get(i).getUserManager().getUser() != null) {
                if (dedicatedServers.get(i).getUserManager().getUser().getId_user() == idFriendToInvite) {

                    UserSend userSend = new UserSend(creatorUser, 0);
                    dedicatedServers.get(i).sendPrivateGame(userSend);
                }
            }
        }
    }

    public void sendActualizedFriendList(int id_user) throws SQLException, IOException {
        for (int i = 0; i < dedicatedServers.size(); i++) {
            if (dedicatedServers.get(i).getUserManager().getUser() != null) {
                if (dedicatedServers.get(i).getUserManager().getUser().getId_user() == id_user) {
                    dedicatedServers.get(i).getUserManager().getUser().setFriendList(dedicatedServers.get(i).getFollowDAO().getFriends(dedicatedServers.get(i).getUserManager().getUser().getId_user(), this));
                    dedicatedServers.get(i).sendFriendList();
                }
            }
        }
    }

    public void sendBoard (Board board, String playerName) throws IOException {

        for(int i = 0; i < gameStorage.getGamelist().getAvailableGames().size(); i++) {
            if(!gameStorage.getGamelist().getAvailableGames().get(i).getAvailable()) {
                if (gameStorage.getGamelist().getAvailableGames().get(i).getCreatorUser().getName().equals(playerName) || gameStorage.getGamelist().getAvailableGames().get(i).getJoinedUser().getName().equals(playerName)) {
                    for (int j = 0; j < dedicatedServers.size(); j++) {
                        if (dedicatedServers.get(j).getUserManager().getUser().getName().equals(gameStorage.getGamelist().getAvailableGames().get(i).getJoinedUser().getName())) {

                            dedicatedServers.get(j).sendBoard(GameManagement.giraTaulell(board));
                        }
                        if (dedicatedServers.get(j).getUserManager().getUser().getName().equals(gameStorage.getGamelist().getAvailableGames().get(i).getCreatorUser().getName())) {

                            dedicatedServers.get(j).sendBoard(board);
                        }
                        for (int x = 0; x < gameStorage.getGamelist().getAvailableGames().get(i).getSpectators().size(); x++) {
                            if (dedicatedServers.get(j).getUserManager().getUser() == gameStorage.getGamelist().getAvailableGames().get(i).getSpectators().get(x)) {
                                dedicatedServers.get(j).sendBoard(board);
                            }
                        }
                    }
                }
            }
        }
    }

    public void actualizeBoard(PositionSend positionSend, User user) throws IOException {
        for(int i = 0; i < gameStorage.getGamelist().getAvailableGames().size(); i++) {
            if(!gameStorage.getGamelist().getAvailableGames().get(i).getAvailable()) {
                if (gameStorage.getGamelist().getAvailableGames().get(i).getCreatorUser().getName().equals(user.getName())) {

                    gameStorage.getGamelist().getAvailableGames().get(i).getBoard().setSquareMatrice(gameManagement.addTroop(positionSend, gameStorage.getGamelist().getAvailableGames().get(i).getBoard(), user).getSquareMatrice());
                }
                if (gameStorage.getGamelist().getAvailableGames().get(i).getJoinedUser().getName().equals(user.getName())) {

                    Board boardGirado = GameManagement.giraTaulell(gameStorage.getGamelist().getAvailableGames().get(i).getBoard());  //OJO OJITO OJETE
                    gameStorage.getGamelist().getAvailableGames().get(i).getBoard().setSquareMatrice(GameManagement.giraTaulell(gameManagement.addTroop(positionSend, boardGirado, user)).getSquareMatrice());  //OJO OJITO OJETE
                }
            }
        }
    }

    public Game findGame(User user) throws IOException {
        for(int i = 0; i < gameStorage.getGamelist().getAvailableGames().size(); i++) {
            if(!gameStorage.getGamelist().getAvailableGames().get(i).getAvailable()) {
                if (gameStorage.getGamelist().getAvailableGames().get(i).getCreatorUser().getName().equals(user.getName()) || gameStorage.getGamelist().getAvailableGames().get(i).getJoinedUser().getName().equals(user.getName())) {

                    return gameStorage.getGamelist().getAvailableGames().get(i);

                }
                else {
                    for (int u = 0; u < gameStorage.getGamelist().getAvailableGames().get(i).getSpectators().size(); u++) {

                        if (gameStorage.getGamelist().getAvailableGames().get(i).getSpectators().get(u).getName().equals(user.getName())) {
                            return gameStorage.getGamelist().getAvailableGames().get(i);
                        }
                    }
                }
            }
        }

        return null;
    }

    public void sendFinishGame (String playerName, int winner) throws IOException {

        boolean found = false;
        int aux = 0;
        for(int i = 0; i < gameStorage.getGamelist().getAvailableGames().size() && !found; i++) {
            if(!gameStorage.getGamelist().getAvailableGames().get(i).getAvailable()) {
                if (gameStorage.getGamelist().getAvailableGames().get(i).getCreatorUser().getName().equals(playerName) || gameStorage.getGamelist().getAvailableGames().get(i).getJoinedUser().getName().equals(playerName)) {
                    for (int j = 0; j < dedicatedServers.size(); j++) {
                        if (dedicatedServers.get(j).getUserManager().getUser().getName().equals(gameStorage.getGamelist().getAvailableGames().get(i).getJoinedUser().getName())) {
                            if (winner == 0) {
                                dedicatedServers.get(j).sendFinishGameConfirmation(false);
                            }
                            else {
                                dedicatedServers.get(j).sendFinishGameConfirmation(true);
                            }
                        }
                        if (dedicatedServers.get(j).getUserManager().getUser().getName().equals(gameStorage.getGamelist().getAvailableGames().get(i).getCreatorUser().getName())) {
                            if (winner == 0) {
                                dedicatedServers.get(j).sendFinishGameConfirmation(true);
                            }
                            else {
                                dedicatedServers.get(j).sendFinishGameConfirmation(false);
                            }
                        }
                        for (int x = 0; x < gameStorage.getGamelist().getAvailableGames().get(i).getSpectators().size(); x++) {

                            if (dedicatedServers.get(j).getUserManager().getUser() == gameStorage.getGamelist().getAvailableGames().get(i).getSpectators().get(x)) {

                                dedicatedServers.get(j).sendFinishGameSpectator();
                            }
                        }
                    }
                    aux = i;
                    found = true;
                }
            }
        }
        gameStorage.getGamelist().getAvailableGames().get(aux).setWinner(winner);

        gameStorage.getGamelist().getAvailableGames().remove(aux);
        sendActualizedGameListToEveryone();
    }

    public void moveTroop(Troop troop) throws IOException {
        if (findGame(troop.getUser()).getCreatorUser().equals(troop.getUser())) {
            User rivalUser = findGame(troop.getUser()).getJoinedUser();
            troop.setPosicio(GameManagement.chooseTroop(troop, findGame(troop.getUser()).getBoard(), rivalUser));
            Board board = GameManagement.moveAttackTroop(troop, findGame(troop.getUser()).getBoard(), troop.getPosicio(), rivalUser);
            findGame(troop.getUser()).setBoard(board);
            sendBoard(board, troop.getUser().getName());

            if (rivalUser.getTower().getHealth() <= 0) {
                rivalUser.setAttackTroops(new LinkedList<>());
                rivalUser.setDefensiveTroops(new LinkedList<>());
                sendFinishGame(troop.getUser().getName(), 0);
                sendFinishGame(rivalUser.getName(), 0);
            }
        }
        if (findGame(troop.getUser()).getJoinedUser().equals(troop.getUser())) {
            User rivalUser = findGame(troop.getUser()).getCreatorUser();
            troop.setPosicio(GameManagement.chooseTroop(troop, GameManagement.giraTaulell(findGame(troop.getUser()).getBoard()), rivalUser));
            Board board = GameManagement.moveAttackTroop(troop, GameManagement.giraTaulell(findGame(troop.getUser()).getBoard()), troop.getPosicio(), rivalUser);
            findGame(troop.getUser()).setBoard(GameManagement.giraTaulell(board));
            sendBoard(findGame(troop.getUser()).getBoard(), troop.getUser().getName());

            if (rivalUser.getTower().getHealth() <= 0) {
                rivalUser.setAttackTroops(new LinkedList<>());
                rivalUser.setDefensiveTroops(new LinkedList<>());
                sendFinishGame(troop.getUser().getName(), 1);
                sendFinishGame(rivalUser.getName(), 1);
            }
        }
    }

    public GameStorage getGameStorage() {
        return gameStorage;
    }

    public NetworkModel getNetworkModel() {
        return networkModel;
    }

    public ArrayList<DedicatedServer> getDedicatedServers() {
        return dedicatedServers;
    }

    public void closeTimerCoins(User joinedUser, User creatorUser) {

        for (int i = 0; i < dedicatedServers.size(); i++) {
            if (dedicatedServers.get(i).getUserManager().getUser() == joinedUser) {
                dedicatedServers.get(i).getTimer().cancel();
                dedicatedServers.get(i).getTimer().purge();
            }
            if (dedicatedServers.get(i).getUserManager().getUser() == creatorUser) {
                dedicatedServers.get(i).getTimer().cancel();
                dedicatedServers.get(i).getTimer().purge();
            }
        }
    }
}
