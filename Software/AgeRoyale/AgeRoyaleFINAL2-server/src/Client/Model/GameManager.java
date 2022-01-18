package Client.Model;

import Client.Controller.GameController;
import Client.Network.Server;
import Client.View.Background;
import Shared.Entity.*;

import java.io.IOException;

public class GameManager {

    private GameController gameController;
    private Board board;
    private Server server;
    private User user;

    public GameManager(Server server, User user) throws IOException {

        this.server = server;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void sendBoardPosition(PositionSend positions) throws IOException, ClassNotFoundException {
         server.sendBoardPosition(positions);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Background[][] convertToBackground(Board board) {

        Background[][] squares = new Background[20][10];
        Square[][] squaresBoard= board.getSquareMatrice();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                Background square = new Background();

                if (!squaresBoard[i][j].isCastle()) {
                    if (squaresBoard[i][j].isOccupied()) {
                        square.setBackground(squaresBoard[i][j].getPhotoURL());
                        square.setImagePath(squaresBoard[i][j].getPhotoURL());

                    } else {
                        square.setBackground(" ");
                        square.setImagePath(" ");
                    }
                }
                square.setPositionX(squaresBoard[i][j].getColumn());
                square.setPositionY(squaresBoard[i][j].getRow());

                squares[i][j] = square;
            }
        }

        return squares;
    }

    public void userQuitGame() throws IOException {
        server.userQuitGame();
    }

    public void getCoins(Integer coins) {
        gameController.getCoins(coins);
    }

    public void addBoard(Board board){
        gameController.addBoard(board);
    }

    public GameController getGameController() {
        return gameController;
    }
}