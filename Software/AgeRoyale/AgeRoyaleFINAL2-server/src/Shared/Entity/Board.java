package Shared.Entity;

import java.io.Serializable;

public class Board implements Serializable {

    private Square squareMatrice[][];


    public Board() {
        squareMatrice = new Square[20][10];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                squareMatrice[i][j] = new Square();
                squareMatrice[i][j].setColumn(j);
                squareMatrice[i][j].setRow(i);
                squareMatrice[i][j].setOccupied(false);
                squareMatrice[i][j].setPhotoURL("");

            }
        }
    }

    public void setSquareMatrice(Square[][] squareMatrice) {
        this.squareMatrice = squareMatrice;
    }

    public Square[][] getSquareMatrice() {
        return squareMatrice;
    }
}
