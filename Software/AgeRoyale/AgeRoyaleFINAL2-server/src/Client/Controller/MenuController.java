package Client.Controller;

import Client.Model.ChangeBackground;
import Client.Model.GameManager;
import Client.Model.MenuManager;
import Client.View.GameView;
import Client.View.MenuView;
import Shared.Entity.*;
import Client.Network.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

public class MenuController implements ActionListener {

    private MenuView menuView;
    private ChangeBackground changeBackground;
    private MenuManager menuManager;

    public MenuController(MenuView menuView, MenuManager menuManager) {

        this.menuView = menuView;
        this.menuManager = menuManager;
        changeBackground = new ChangeBackground();
        changeBackground.setMenuController(this);
        changeBackground.start();
        this.menuManager.startAskingAvailableGames();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        JButton button = (JButton) actionEvent.getSource();

        if (button.getText().equals("JUGAR")) {

            try {
                JOptionPane.getRootFrame().dispose();
                menuManager.joinPrivateGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (button.getText().equals("DENEGAR")) {

            try {
                JOptionPane.getRootFrame().dispose();
                menuManager.cancelPrivateGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (button.getText().equals("BUSCAR PARTIDA")) {

            menuView.showGameList();
        }

        if (button.getText().equals("ESPECTAR PARTIDA")) {

            menuView.showSpectatorGameList();
        }

        if (button.getText().equals("CREAR PARTIDA")) {

            menuView.showCreateGameOptions();
        }

        if (button.getText().equals("PÚBLICA")) {

            try {
                menuView.showWaitRoom();
                menuManager.createPublicGame(menuView.getJtfNombrePartida().getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (button.getText().equals("PRIVADA")) {

            menuView.showPrivateGameMenu();
        }

        if (button.getText().equals("Atrás")) {

            if (button.getActionCommand().equals("AtrasInvitarAmigos")) {
                menuView.showCreateGameOptions();
            }
            else {
                menuView.showGameOptions();
            }
        }

        if (button.getText().equals("ENVIAR")) {

            try {
                menuManager.sendFriendRequest(menuView.getJtfAmigo().getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            menuView.getJtfAmigo().setText("");
        }

        if (button.getText().equals("SALIR")) {

            try {
                menuManager.exitGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (button.getActionCommand().contains("unirse")) {
            for (int i = 0; i < menuView.getAvailableGamesJPanels().size(); i++) {
                if (button.getActionCommand().equals(menuView.getAvailableGamesJButtons().get(i).getActionCommand())) {

                    try {
                        menuManager.joinAsPlayer(menuView.getAvailableGamesAuxiliar().get(i).getId_game());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (button.getActionCommand().contains("espectar")) {
            for (int i = 0; i < menuView.getAvailableSpectatorGamesJPanels().size(); i++) {
                if (button.getActionCommand().equals(menuView.getAvailableSpectatorGamesJButtons().get(i).getActionCommand())) {

                    try {
                        menuManager.joinAsSpectator(menuView.getAvailableGamesSpectatorAuxiliar().get(i).getId_game());  //Falta conseguir el id de la partida seleccionada
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (button.getActionCommand().contains("eliminar")) {
            for (int i = 0; i < menuView.getFriendsJPanels().size(); i++) {
                if (button.getActionCommand().equals(menuView.getFriendsJButtons().get(i).getActionCommand())) {

                    try {
                        menuManager.deleteFriend(menuView.getFriendsAuxiliar().get(i).getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (button.getActionCommand().contains("aceptar")) {
            for (int i = 0; i < menuView.getRequestsJPanels().size(); i++) {
                if (button.getActionCommand().equals(menuView.getRequestsJButtonsAccept().get(i).getActionCommand())) {

                    try {
                        menuManager.acceptRequest(menuView.getRequestsAuxiliar().get(i).getFrom());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (button.getActionCommand().contains("declinar")) {
            for (int i = 0; i < menuView.getRequestsJPanels().size(); i++) {
                if (button.getActionCommand().equals(menuView.getRequestsJButtonsDecline().get(i).getActionCommand())) {

                    try {
                        menuManager.declineRequest(menuView.getRequestsAuxiliar().get(i).getFrom());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (button.getActionCommand().contains("invitar")) {
            for (int i = 0; i < menuView.getFriendsInviteJPanels().size(); i++) {
                if (button.getActionCommand().equals(menuView.getFriendsInviteJButtons().get(i).getActionCommand())) {

                    try {
                        menuManager.inviteFriend(menuView.getFriendsInviteAuxiliar().get(i).getId(), menuView.getJtfNombrePartida().getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void changeBackground(String path) {
        menuView.setBackground(path);
    }

    public void actualizeAvailableGames(GameList gameList) {
        menuView.showAvailableGames(gameList.getAvailableGames(), this);
        menuView.showSpectatorAvailableGames(gameList.getAvailableGames(), this);
    }

    public void actualizeFriends(FriendList friendList) {

        menuView.showFriendsToInvite(friendList.getFriends(), this);
        menuView.showFriends(friendList.getFriends(), this);
    }

    public void actualizeRequests(RequestList requestList) {
        menuView.showFriendRequests(requestList.getRequests(), this);
    }

    public void showGameView(String playerName, String enemyName) {

        menuView.setVisible(false); //Hacemos invisible el menú, pero no hacemos un dispose ya que luego volverá al menú

        try {
            GameView gameView = new GameView(enemyName);
            gameView.setPlayerName(playerName);
            GameManager gameManager = new GameManager(menuManager.getServer(), menuManager.getUser());
            GameController gameController = new GameController(gameView, gameManager);
            gameView.gameController(gameController);
            gameManager.setGameController(gameController);
            menuManager.getServer().setGameManager(gameManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeRequestMessage(String requestMessage) {

        menuView.setJtfAmigoText(requestMessage);
    }

    public void showDialogPrivateGame(UserSend userSend) {

        menuView.showInvitationMessage(this, userSend.getUser());
    }

    public void cancelPrivateGame() {

        try {
            JOptionPane.getRootFrame().dispose();
            menuManager.cancelPrivateGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public void showVictoriaDerrotaMessage(boolean isWinner) throws InterruptedException {

        menuView.showVictoriaDerrotaMessage();

        if (isWinner) {
            menuView.setColorJlVictoriaDerrota(Color.GREEN);
            menuView.setTextJlVictoriaDerrota("VICTORIA");
        }
        else {
            menuView.setColorJlVictoriaDerrota(Color.RED);
            menuView.setTextJlVictoriaDerrota("DERROTA");
        }

        Thread.sleep(3000);
        menuView.showGameOptions();
    }

    public void showVictoriaDisconnected() throws InterruptedException {
        menuView.showVictoriaDerrotaMessage();

        menuView.setColorJlVictoriaDerrota(Color.GREEN);
        menuView.setTextJlVictoriaDerrota("VICTORIA POR DESCONEXIÓN ENEMIGA");

        Thread.sleep(3000);
        menuView.showGameOptions();
    }
}




