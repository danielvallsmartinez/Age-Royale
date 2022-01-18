package Client.Model;

import Client.Controller.MenuController;
import Client.Network.Server;
import Shared.Entity.*;

import java.io.IOException;
import java.util.LinkedList;

public class MenuManager {

    private User user;
    private User friendPrivateGame;
    private LinkedList<Game> availableGames;
    private LinkedList<Friend> friends;
    private LinkedList<Request> requests;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    private Server server;
    private MenuController menuController;

    public MenuManager(User user, Server server) {

        this.user = user;
        this.server = server;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setMenuManagerOfServer(MenuManager menuManager) {
        server.setMenuManager(menuManager);
    }

    public User getUser() {

        return user;
    }

    public void startAskingAvailableGames() {

        if (!server.isAlive()) {
            server.start();
        }

    }

    public void joinAsPlayer(int id_game) throws IOException {

        server.joinAsPlayer(id_game);
    }

    public void joinAsSpectator(int id_game) throws IOException {

        server.joinAsSpectator(id_game);
    }

    public void deleteFriend(int idUser) throws IOException {

        server.deleteFriend(idUser);
    }

    public void createPublicGame(String gameName) throws IOException {

        server.createPublicGame(gameName);
    }

    public void actualizeAvailableGames(GameList gameList) {

        availableGames = gameList.getAvailableGames();
        menuController.actualizeAvailableGames(gameList);
    }

    public void actualizeFriends(FriendList friendList) {

        friends = friendList.getFriends();
        menuController.actualizeFriends(friendList);
    }

    public void sendFriendRequest(String friendName) throws IOException {

        server.sendFriendRequest(friendName);
    }

    public void declineRequest(String userName) throws IOException {

        server.declineRequest(userName);
    }

    public void acceptRequest(String userName) throws IOException {

        server.acceptRequest(userName);
    }

    public void actualizeRequests(RequestList requestList) {
        requests = requestList.getRequests();
        menuController.actualizeRequests(requestList);
    }

    public void showGameView(Game game) {
        if (game.getCreatorUser().getName().equals(user.getName())) {
            menuController.showGameView(game.getCreatorUser().getName(), game.getJoinedUser().getName());
        }
        else {
            menuController.showGameView(game.getJoinedUser().getName(), game.getCreatorUser().getName());
        }
    }

    public void showGameForEspectator() {
        menuController.showGameView("", "");
    }

    public void writeRequestMessage(String requestMessage) {

        menuController.writeRequestMessage(requestMessage);
    }
    public void inviteFriend(int idFriend, String gameName) throws IOException {

        server.inviteFriend(idFriend, gameName);
    }

    public void showDialogPrivateGame(UserSend userSend) {

        friendPrivateGame = userSend.getUser();
        menuController.showDialogPrivateGame(userSend);
    }

    public void joinPrivateGame() throws IOException {

        server.joinPrivateGame(friendPrivateGame);
    }

    public void cancelPrivateGame() throws IOException {

        server.cancelPrivateGame(friendPrivateGame);
    }

    public void exitGame() throws IOException {
        server.exitGame();
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public void showVictoriaDerrotaMessage(boolean isWinner) throws InterruptedException {
        menuController.showVictoriaDerrotaMessage(isWinner);
    }

    public void showVictoriaDisconnected() throws InterruptedException {
        menuController.showVictoriaDisconnected();
    }
}