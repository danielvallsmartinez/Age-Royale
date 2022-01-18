package Client.Controller;


import Shared.Entity.Board;
import Client.Model.GameManager;
import Client.View.Background;
import Client.View.GameView;
import Shared.Entity.PositionSend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class GameController implements MouseListener, ActionListener {

    private GameView gameView;
    private GameManager gameManager;
    private int numCard = 0;

    public GameController(GameView gameView, GameManager gameManager) {
        this.gameView = gameView;
        this.gameManager = gameManager;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Background position = (Background) mouseEvent.getSource();

        if (position.getPositionY() == 50) {

            numCard = position.getPositionX();
        }
        else {

            PositionSend positions = new PositionSend(position.getPositionX(), position.getPositionY(), numCard, gameManager.getUser().getName());


            try {
                if (position.getPositionY() > 9) {
                    if (((position.getPositionY() > 16) && (position.getPositionX() < 4 || position.getPositionX() > 5)) || (position.getPositionY() >= 10 && position.getPositionY() <= 16)) {
                        gameManager.sendBoardPosition(positions);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            gameManager.userQuitGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameView.dispose();
    }

    public void getCoins(Integer coins) {
        gameView.setNumCoins(coins);
    }

    public void addBoard(Board board){
        gameView.setSquares(gameManager.convertToBackground(board));
    }

    public GameView getGameView() {
        return gameView;
    }
}

