package Server.Model.Network;
import Server.Model.DataBase.DAO.FollowDAO;
import Server.Model.DataBase.DAO.GameDAO;
import Server.Model.DataBase.DAO.UserDAO;
import Server.Model.GameManagement;
import Shared.Entity.*;
import Server.Model.UserManager;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class DedicatedServer extends Thread implements Serializable {

    private Socket socket;
    private UserManager userManager;
    private ObjectInputStream ois;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ObjectOutputStream oos;
    private Server server;
    private GameDAO gameDAO;
    private FollowDAO followDAO;
    private NetworkModel networkModel;
    private DateFormat dateFormatHour;
    private Date dateHour;
    private DateFormat dateFormat;
    private Date date;
    private Coin coin;
    private boolean partida;
    private volatile boolean exit;
    private Timer timer;
    private int comptador;


    public DedicatedServer(Socket socket, NetworkModel networkModel, Server server) throws IOException {
        this.socket = socket;
        userManager = new UserManager(networkModel);
        this.dis = new DataInputStream(socket.getInputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.server = server;
        this.networkModel = networkModel;
        gameDAO = new GameDAO(server.getNetworkModel());
        followDAO = new FollowDAO(networkModel);
        dateFormatHour = new SimpleDateFormat("HH:mm");
        dateHour = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();
        partida = false;
        coin = new Coin();
        exit = false;
        timer = new Timer();
    }

    @Override
    public void run () {
        while (!exit) {
            try {

                Object opcio = ois.readObject();

                if (opcio instanceof UserSend) {

                    int error;

                    UserSend usersend = (UserSend) opcio;

                    if (usersend.getUserOption() == 10 || usersend.getUserOption() == 11) {

                        if (usersend.getUserOption() == 10) {

                            for (int i = 0; i < server.getGameStorage().getGamelist().getAvailableGames().size(); i++) {
                                if (usersend.getUser().getName().equals(server.getGameStorage().getGamelist().getAvailableGames().get(i).getCreatorUser().getName())) {

                                    Game game = server.getGameStorage().getGamelist().getAvailableGames().get(i);
                                    game.setId_game(gameDAO.countAllGames() + 1);

                                    game.setDate(dateFormat.format(date));
                                    game.setStartHour(dateFormatHour.format(dateHour));

                                    int idGame = gameDAO.countAllGames() + 1;
                                    server.getGameStorage().getGamelist().getAvailableGames().get(i).setId_game(idGame);
                                    server.getGameStorage().getGamelist().getAvailableGames().get(i).setAvailable(false);
                                    gameDAO.addGame(game);

                                    server.gameReady(idGame, userManager.getUser());

                                    timer = new Timer();
                                }
                            }
                        }

                        if (usersend.getUserOption() == 11) {

                            for (int i = 0; i < server.getGameStorage().getGamelist().getAvailableGames().size(); i++) {
                                if (usersend.getUser().getName().equals(server.getGameStorage().getGamelist().getAvailableGames().get(i).getCreatorUser().getName())) {

                                    server.getGameStorage().getGamelist().getAvailableGames().remove(i);
                                    i--;
                                }
                            }
                        }
                    }
                    else {

                        userManager.setUser(usersend.getUser());

                        if (usersend.getUserOption() == 0) {
                            error = userManager.user_login(usersend.getUser(), server);
                        } else {
                            error = userManager.user_register(usersend.getUser());
                        }


                        oos.reset();
                        oos.writeObject(error);
                    }
                }

                if (opcio instanceof Integer) {

                    int option = (Integer) opcio;

                    if (option == 2) { //muestra lista partidas, solicitudes y amigos al inicio

                        server.sendActualizedGameListToEveryone();
                        RequestList requestList = followDAO.getRequests(userManager.getUser());
                        oos.writeObject(requestList);
                        userManager.getUser().setFriendList(followDAO.getFriends(userManager.getUser().getId_user(), server));
                        sendFriendList();

                        for (int i = 0; i < userManager.getUser().getFriendList().getFriends().size(); i++) {
                            if (server.isConnected(userManager.getUser().getFriendList().getFriends().get(i).getId())) {
                                server.sendActualizedFriendList(userManager.getUser().getFriendList().getFriends().get(i).getId());
                            }
                        }
                    }

                    if (option == 3) { //unirse como expectador
                        int id = (Integer) ois.readObject();
                        Board board = server.spectatorJoined(id, userManager.getUser());
                        oos.reset();
                        oos.writeObject(board);
                    }

                    if (option == 4) { //unirse como jugador

                        int id = (Integer) ois.readObject();

                        for (int i = 0, found = 0; i < server.getGameStorage().getGamelist().getAvailableGames().size() && found == 0; i++) {

                            if (server.getGameStorage().getGamelist().getAvailableGames().get(i).getId_game() == id) {

                                server.getGameStorage().getGamelist().getAvailableGames().get(i).setAvailable(false);
                                found = 1;
                            }

                            if (found == 1) {

                                server.sendActualizedGameListToEveryone();
                            }
                        }

                        server.gameReady(id, userManager.getUser());
                        gameDAO.addJoinedUser(id, userManager.getUser());
                    }

                    if (option == 5) { //crea partida privada

                        int idFriendToInvite = (Integer) ois.readObject();
                        String gameName = (String) ois.readObject();

                        Game game = new Game(userManager.getUser(), gameName);
                        game.setPrivada(true);
                        server.getGameStorage().addGameList(game);

                        server.sendPrivateGame(idFriendToInvite, userManager.getUser());
                    }

                    if (option == 6) { //crea partida publica

                        String gameName = (String) ois.readObject();

                        Game game = new Game(userManager.getUser(), gameName);
                        game.setPrivada(false);

                        game.setStartHour(dateFormatHour.format(dateHour));
                        game.setDate(dateFormat.format(date));

                        game.setId_game(gameDAO.countAllGames() + 1);

                        gameDAO.addGame(game);
                        server.getGameStorage().addGameList(game);

                        if (server.getGameStorage().updateGameList()) {

                            server.sendActualizedGameListToEveryone();
                        }
                    }

                    if (option == 8) { //sal del juego

                        for (int i = 0; i < server.getDedicatedServers().size(); i++) {
                            if (server.getDedicatedServers().get(i).getUserManager().getUser() == userManager.getUser()) {
                                server.getDedicatedServers().remove(i);
                                exit = true;
                                break;
                            }
                        }
                    }

                    if (option == 7) { //forzar salir de la partida

                        int typeOfUser  = server.quitPlay(server.findGame(userManager.getUser()).getId_game(), userManager.getUser());

                        if (typeOfUser == 11) {

                            Game game = server.findGame(userManager.getUser());
                            game.setFinishHour(dateFormatHour.format(dateHour));
                            if (game.getCreatorUser() == userManager.getUser()) {
                                game.setWinner(game.getJoinedUser().getId_user());
                            }
                            else {
                                game.setWinner(game.getCreatorUser().getId_user());
                            }
                            gameDAO.storeFinishedGame(game, game.getCreatorUser().getId_user());
                            UserDAO userDAO = new UserDAO(networkModel);
                            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                            userDAO.banUser(userManager.getUser().getId_user(), currentTimestamp);
                            server.closeTimerCoins(server.findGame(userManager.getUser()).getJoinedUser(), server.findGame(userManager.getUser()).getCreatorUser());
                            server.getGameStorage().removeGameFromList(server.findGame(userManager.getUser()));
                        }

                        if (typeOfUser == 12) {

                            sendFinishGameSpectator();
                        }

                        if (typeOfUser == 11) {
                            for (int i = 0; i < userManager.getUser().getAttackTroops().size(); i++) {
                                userManager.getUser().getAttackTroops().get(i).stopTimer();
                            }
                            for (int i = 0; i < userManager.getUser().getDefensiveTroops().size(); i++) {
                                userManager.getUser().getDefensiveTroops().get(i).stopTimer();
                            }

                            for (int i = 0; i < server.getDedicatedServers().size(); i++) {
                                if (server.getDedicatedServers().get(i).getUserManager().getUser() == userManager.getUser()) {
                                    server.getDedicatedServers().remove(i);
                                    exit = true;
                                    break;
                                }
                            }

                            server.sendActualizedGameListToEveryone();
                        }
                    }

                    if (option == 22) { //busca jugador para añadir a lista de amigos

                        String name = (String) ois.readObject();

                        try {

                            Connection conexion = DriverManager.getConnection(networkModel.getUrl(), networkModel.getAccess_User(), networkModel.getPassword());
                            Statement s = conexion.createStatement();
                            int count = 0;
                            int idTo = 0;
                            ResultSet rs;
                            rs = s.executeQuery("select id_user from User where name = '" + name + "' group by User.id_user;");

                            while (rs.next()) {
                                idTo = rs.getInt(1);
                            }
                            rs = s.executeQuery("select COUNT(creatorUser) from Follow where (creatorUser = " + idTo + " AND joinedUser = "+userManager.getUser().getId_user()+") OR (creatorUser = " +userManager.getUser().getId_user()+ " AND joinedUser = "+idTo+");");

                            while (rs.next()) {
                                count = rs.getInt(1);
                            }
                            if (count == 0) {
                                if (name.equals(userManager.getUser().getName()) || name.equals(userManager.getUser().getMail())) {
                                    oos.reset();
                                    oos.writeObject("¿Intentas petar el código crack?");
                                }
                                else {

                                    rs = s.executeQuery("select COUNT(id_user) from User where name = '"+name+"' group by User.id_user;");
                                    count = 0;
                                    Request request;
                                    while (rs.next()) {
                                        count = rs.getInt(1);
                                    }
                                    if (count != 1) {

                                        rs = s.executeQuery("select COUNT(id_user) from User where mail = '"+name+"' group by User.id_user;");
                                        count = 0;
                                        while (rs.next()) {
                                            count = rs.getInt(1); //ns si va un 0 o un 1--------------------------------
                                        }
                                        if (count != 1) {
                                            System.out.println("");
                                            oos.reset();
                                            oos.writeObject("Este usuario no existe");
                                        }
                                        else {

                                            followDAO.addRequest(userManager.getUser().getId_user(), idTo);
                                            server.sendActualizedRequestList(name);
                                            oos.reset();
                                            oos.writeObject("Solicitud enviada");

                                        }
                                    }
                                    else {

                                        followDAO.addRequest(userManager.getUser().getId_user(), idTo);
                                        server.sendActualizedRequestList(name);
                                        oos.reset();
                                        oos.writeObject("Solicitud enviada");
                                    }
                                }
                            }
                            else {
                                oos.reset();
                                oos.writeObject("Este usuario ya es tu amigo");
                            }

                        } catch (Exception e) {

                            JOptionPane.showMessageDialog(null, "Error");
                        }
                    }

                    if (option == 21) { //aceptar solicitud

                        String from = (String) ois.readObject();
                        int accept = (Integer) ois.readObject();

                        Connection conexion = DriverManager.getConnection(networkModel.getUrl(), networkModel.getAccess_User(), networkModel.getPassword());
                        Statement s = conexion.createStatement();
                        ResultSet rs = s.executeQuery("select id_user from User where name = '"+from+"';");
                        int idFrom = 0;
                        while (rs.next()) {
                            idFrom = rs.getInt(1);
                        }
                        if (accept == 1) { //guardar a bbdd

                            PreparedStatement insert;
                            insert = conexion.prepareStatement("INSERT INTO Follow (creatorUser, joinedUser) VALUES ((SELECT id_user FROM User WHERE id_user = "+idFrom+"), (SELECT id_user FROM User WHERE id_user = "+userManager.getUser().getId_user()+"));");
                            insert.execute();
                            insert.close();
                            rs.close();
                            Friend friend = new Friend(idFrom, from);
                            friend.setConnected(server.isConnected(idFrom));
                            userManager.getUser().getFriendList().getFriends().add(friend);
                            sendFriendList();

                            Friend friend1 = new Friend(userManager.getUser().getId_user(), userManager.getUser().getName());
                            server.addFriend(from, friend1);
                        }

                        followDAO.deleteRequest(idFrom, userManager.getUser().getId_user());
                        server.sendActualizedRequestList(userManager.getUser().getName());
                        server.sendActualizedRequestList(from);
                    }

                    if (option == 20) { //elimina un amigo

                        int id = (Integer) ois.readObject();

                        Connection conexion = DriverManager.getConnection(networkModel.getUrl(), networkModel.getAccess_User(), networkModel.getPassword());
                        PreparedStatement delete;
                        delete = conexion.prepareStatement("DELETE FROM Follow WHERE (joinedUser = "+id+" AND creatorUser = "+userManager.getUser().getId_user()+") OR (joinedUser = "+userManager.getUser().getId_user()+" AND creatorUser = "+id+");");
                        delete.execute();
                        delete.close();

                        int j = 0;
                        for (int i = 0; i < userManager.getUser().getFriendList().getFriends().size(); i++) {
                            if (userManager.getUser().getFriendList().getFriends().get(i).getId() == id) {
                                j = i;
                            }
                        }
                        userManager.getUser().getFriendList().getFriends().remove(j);
                        sendFriendList();
                        server.deleteFriend(id, userManager.getUser().getId_user());
                    }
                }
                if (opcio instanceof PositionSend) {
                    PositionSend positionSend = (PositionSend) opcio;
                    System.out.println("Position send X:" + positionSend.getPositionX());
                    System.out.println("Position send Y:" + positionSend.getPositionY());
                    userManager.getUser().setDedicatedServer(this);
                    server.actualizeBoard(positionSend, userManager.getUser());
                }
            } catch (IOException | SQLException | ClassNotFoundException e) {
                for (int i = 0; i < server.getDedicatedServers().size(); i++) {
                    if (server.getDedicatedServers().get(i).getUserManager().getUser() == userManager.getUser()) {
                        server.getDedicatedServers().remove(i);
                        exit = true;
                        break;
                    }
                }
                this.interrupt();
            }
        }
    }

    public void sendGameList (GameList gameList) throws IOException {

        oos.reset();
        oos.writeObject(gameList);
    }

    public void sendConfirmationStartGame(Game game) throws IOException {

        timer = new Timer();
        oos.reset();
        oos.writeObject(game);
        setTimer();
    }

    public void agafaMonedes(int coin) throws IOException {

        if (coin > 10) {
            coin = 10;
        }

        this.coin.setNumCoins(coin);

        oos.writeObject(this.coin);
        oos.reset();
    }

    public void sendBoard(Board board) throws IOException {

        oos.reset();
        oos.writeObject(board);
    }

    public void sendUserLeft() throws IOException {
        oos.reset();
        oos.writeObject(10);
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void sendUserRequestList(RequestList requestList) throws IOException {

        userManager.getUser().setRequestList(requestList);

        oos.reset();
        oos.writeObject(requestList);
    }

    public void sendFriendList() throws IOException {

        oos.reset();
        oos.writeObject(userManager.getUser().getFriendList());
    }

    public FollowDAO getFollowDAO() {
        return followDAO;
    }

    public void sendPrivateGame(UserSend userSend) throws IOException {

        oos.reset();
        oos.writeObject(userSend);
    }

    public Server getServer() {
        return server;
    }

    TimerTask coins = new TimerTask() {
        @Override
        public void run() {


            if(userManager.getUser().getCoin() < 10) {

                userManager.getUser().getCoin();
                coin.setNumCoins(userManager.getUser().getCoin() + 1);
                userManager.getUser().setCoin(coin.getNumCoins());
                try {
                    agafaMonedes(coin.getNumCoins());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            comptador++;
            System.out.println("comptador: "+comptador);
            if (comptador % 30 == 0) {
                try {
                    if (userManager.getUser() == server.findGame(userManager.getUser()).getCreatorUser()) {
                        server.findGame(userManager.getUser()).setMinutes(server.findGame(userManager.getUser()).getMinutes() + 1);
                        System.out.println("MINUTS: "+server.findGame(userManager.getUser()).getMinutes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    public void setTimer() {
        timer.schedule(coins, 2000, 2000);
    }

    public void sendFinishGameConfirmation(boolean isWinner) throws IOException {

        for (int i = 0; i < userManager.getUser().getAttackTroops().size(); i++) {
            userManager.getUser().getAttackTroops().get(i).stopTimer();
        }
        for (int i = 0; i < userManager.getUser().getDefensiveTroops().size(); i++) {
            userManager.getUser().getDefensiveTroops().get(i).stopTimer();
        }
        timer.cancel();
        timer.purge();
        Game game = server.findGame(userManager.getUser());
        if (userManager.getUser() == game.getCreatorUser()) {
            if (isWinner) {
                game.setWinner(userManager.getUser().getId_user());
            }
            else {
                game.setWinner(game.getJoinedUser().getId_user());
            }

            gameDAO.storeFinishedGame(game, userManager.getUser().getId_user());
        }

        oos.writeObject(isWinner);
        oos.reset();
    }

    public void sendFinishGameSpectator() throws IOException {

        oos.reset();
        oos.writeObject(11);
    }

    public Timer getTimer() {
        return timer;
    }
}



