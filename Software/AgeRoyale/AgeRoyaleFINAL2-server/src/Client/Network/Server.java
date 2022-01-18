package Client.Network;

import Client.Controller.UserRegisterController;
import Client.Model.GameManager;
import Client.Model.UserManager;
import Client.View.UserRegisterView;
import Shared.Entity.*;
import Client.Model.MenuManager;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Server extends Thread {
    private Socket socket;
    private ObjectOutputStream ooStream;
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private ObjectInputStream oiStream;
    private UserManager userManager;
    private MenuManager menuManager;
    private GameManager gameManager;
    private boolean logged;
    private boolean wantLogin;

    public Server () throws IOException {
        socket = new Socket("localhost", 3457);
        ooStream = new ObjectOutputStream(socket.getOutputStream());
        diStream = new DataInputStream(socket.getInputStream());
        doStream = new DataOutputStream(socket.getOutputStream());
        oiStream = new ObjectInputStream(socket.getInputStream());
        wantLogin = true;
        logged = false;
    }

    @Override
    public void run() {  //Se supone que escuchara constantemente el server para recibir las actualizaciones de la lista

        while (true) {
            try {

                Object o = oiStream.readObject();

                if (o instanceof Integer) {
                    returnServerResponse(o);
                }

                if (logged) {
                    if (o instanceof GameList) {

                        getAvailableGamesList(o);
                    }

                    if (o instanceof FriendList) {

                        getFriendList(o);
                    }

                    if (o instanceof RequestList) {

                        getRequestList(o);
                    }

                    if (o instanceof Game) {

                        getGame(o);
                    }

                    if (o instanceof String) {

                        writeRequestMessage(o);
                    }

                    if (o instanceof Coin) {
                        getCoins(o);
                    }

                    if (o instanceof UserSend) {

                        UserSend userSend = (UserSend) o;

                        if (userSend.getUserOption() == 0) {

                            showDialogPrivateGame(userSend);
                        }
                    }

                    if (o instanceof Board) {
                        getBoard(o);
                    }

                    if (o instanceof Boolean) {
                        finishGame(o);
                    }
                }

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendReadyToStartMenu() throws IOException {

        ooStream.reset();
        ooStream.writeObject(2);  //El servidor, en caso de recibir un 2, ha de enviar al cliente la lista de partidas (esto es para la primera vez que se muestra la lista)
    }

    public void sendUser(UserSend userSend) throws IOException {

        ooStream.reset();
        ooStream.writeObject(userSend);
    }

    public void returnServerResponse(Object o) throws InterruptedException {

        int serverResponse = (Integer) o;

        if (serverResponse == 10) {
            gameManager.getGameController().getGameView().dispose();
            menuManager.getMenuController().getMenuView().setVisible(true);
            menuManager.showVictoriaDisconnected();
        }
        else if (serverResponse == 11) {

            menuManager.getMenuController().getMenuView().dispose();

            UserRegisterView userRegisterView = null;

            try {
                userRegisterView = new UserRegisterView();
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }

            UserManager userManager = null;

            try {
                userManager = new UserManager();
                userManager.getServer().setUserManager(userManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UserRegisterController userRegisterController = new UserRegisterController(userRegisterView, userManager);
            assert userRegisterView != null;
            userRegisterView.userRegisterController(userRegisterController);
            assert userManager != null;
            userManager.setUserRegisterController(userRegisterController);

            logged = false;
        }
        else {
            if (wantLogin) {

                userManager.setUserLoginResponse(serverResponse);
            } else {
                userManager.setUserRegisterResponse(serverResponse);
            }
        }
    }

    public void joinAsPlayer(int id_game) throws IOException {

        ooStream.reset();
        ooStream.writeObject(4);  //Envia un 3 para avisar de que el cliente quiere unirse como jugador
        ooStream.reset();
        ooStream.writeObject(id_game);  //Envia el id de la partida a la que el cliente quiere unirse como jugador
    }

    public void joinAsSpectator(int id_game) throws IOException {

        ooStream.reset();
        ooStream.writeObject(3);  //Envia un 3 para avisar de que el cliente quiere unirse como espectador
        ooStream.reset();
        ooStream.writeObject(id_game);  //Envia el id de la partida a la que el cliente quiere unirse como espectador
        menuManager.showGameForEspectator();
    }

    public void deleteFriend(int idUser) throws IOException {
        ooStream.reset();
        ooStream.writeObject(20);
        ooStream.reset();
        ooStream.writeObject(idUser);
    }

    public void createPublicGame (String gameName) throws IOException {

        ooStream.reset();
        ooStream.writeObject(6);  //Envia un 6 para avisar de que el cliente quiere crear una partida publica
        ooStream.reset();
        ooStream.writeObject(gameName);  //Envia el nombre de la partida que quiere crear
    }

    public void getAvailableGamesList (Object o) {

        GameList gameList = (GameList) o;
        menuManager.actualizeAvailableGames(gameList);
    }

    public void getFriendList (Object o) {

        FriendList friendList = (FriendList) o;
        menuManager.actualizeFriends(friendList);
    }

    private void getRequestList(Object o) {
        RequestList requestList = (RequestList) o;
        menuManager.actualizeRequests(requestList);
    }

    public void  sendFriendRequest(String friendName) throws IOException {
        ooStream.reset();
        ooStream.writeObject(22);
        ooStream.reset();
        ooStream.writeObject(friendName);
    }

    public void declineRequest(String userName) throws IOException {
        ooStream.reset();
        ooStream.writeObject(21);
        ooStream.reset();
        ooStream.writeObject(userName);
        ooStream.reset();
        ooStream.writeObject(0);
    }

    public void acceptRequest(String userName) throws IOException {
        ooStream.reset();
        ooStream.writeObject(21);
        ooStream.reset();
        ooStream.writeObject(userName);
        ooStream.reset();
        ooStream.writeObject(1);
    }
    public void inviteFriend(int idFriend, String gameName) throws IOException {
        ooStream.reset();
        ooStream.writeObject(5); //Avisa de que quiere crear una partida privada
        ooStream.reset();
        ooStream.writeObject(idFriend); //Envia el id del amigo al que quiere invitar
        ooStream.reset();
        ooStream.writeObject(gameName);
    }

    public void writeRequestMessage(Object o) {

        String requestMessage = (String) o;
        menuManager.writeRequestMessage(requestMessage);
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public void sendBoardPosition(PositionSend positions) throws IOException, ClassNotFoundException {
        ooStream.reset();
        ooStream.writeObject(positions);
    }

    public void userQuitGame() throws IOException {
        ooStream.reset();
        ooStream.writeObject(7);

        if (gameManager != null) {
            gameManager.getGameController().getGameView().dispose();
        }

        if (gameManager != null) {
            menuManager.getMenuController().getMenuView().dispose();
        }

        UserRegisterView userRegisterView = null;

        try {
            userRegisterView = new UserRegisterView();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        UserManager userManager = null;

        try {
            userManager = new UserManager();
            userManager.getServer().setUserManager(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserRegisterController userRegisterController = new UserRegisterController(userRegisterView, userManager);
        assert userRegisterView != null;
        userRegisterView.userRegisterController(userRegisterController);
        assert userManager != null;
        userManager.setUserRegisterController(userRegisterController);

        logged = false;
    }

    public void getCoins(Object o) {
        Coin coin = (Coin) o;
        gameManager.getCoins(coin.getNumCoins());
    }

    public void getBoard(Object o) throws InterruptedException {
        Board board = (Board) o;
        while (gameManager == null) {
            sleep(10);
        }
        gameManager.addBoard(board);
    }

    public void getGame(Object o) {
        Game game = (Game) o;
        menuManager.showGameView(game);
    }

    public void showDialogPrivateGame(UserSend userSend) {

        menuManager.showDialogPrivateGame(userSend);
    }

    public void joinPrivateGame(User friendPrivateGame) throws IOException {

        UserSend userSend = new UserSend(friendPrivateGame, 10);
        ooStream.reset();
        ooStream.writeObject(userSend);
    }

    public void cancelPrivateGame(User friendPrivateGame) throws IOException {

        UserSend userSend = new UserSend(friendPrivateGame, 11);
        ooStream.reset();
        ooStream.writeObject(userSend);
    }

    public void exitGame() throws IOException {

        ooStream.reset();
        ooStream.writeObject(8);

        if (gameManager != null) {
            gameManager.getGameController().getGameView().dispose();
        }

        menuManager.getMenuController().getMenuView().dispose();

        UserRegisterView userRegisterView = null;

        try {
            userRegisterView = new UserRegisterView();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        UserManager userManager = null;

        try {
            userManager = new UserManager();
            userManager.getServer().setUserManager(userManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserRegisterController userRegisterController = new UserRegisterController(userRegisterView, userManager);
        assert userRegisterView != null;
        userRegisterView.userRegisterController(userRegisterController);
        assert userManager != null;
        userManager.setUserRegisterController(userRegisterController);

        logged = false;
    }

    public void finishGame(Object o) throws InterruptedException {

        boolean isWinner = (Boolean) o;
        gameManager.getGameController().getGameView().dispose();
        menuManager.getMenuController().getMenuView().setVisible(true);
        menuManager.showVictoriaDerrotaMessage(isWinner);
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setWantLogin(boolean wantLogin) {
        this.wantLogin = wantLogin;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}