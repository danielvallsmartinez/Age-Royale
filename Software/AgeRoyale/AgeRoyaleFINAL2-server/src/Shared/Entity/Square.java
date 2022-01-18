package Shared.Entity;
import java.io.Serializable;


public class Square implements Serializable{
    //private boolean occupiedByAttackTroop; //si te tropa atacant
    //private boolean occupiedByDefensiveTroop; //si te tropa defensiva
    private boolean castle; //si es una de les caselles que ocupa els castell
    private boolean enemyTerritory; //si es territori enemic
    private boolean occupied;
    private int row;
    private int column;
    private String photoURL;
    //private int health;
    private Troop troop;


    public Square (boolean castle, boolean enemyTerritory, int row, int column, String photoURL, boolean occupied) {
        this.castle = castle;
        this.row = row;
        this.enemyTerritory = enemyTerritory;
        this.column = column;
        this.photoURL = photoURL;
        this.occupied = occupied;
    }
    public Square(){

    }

    public boolean isOccupied() {
        return occupied;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public boolean isEnemyTerritory() {
        return enemyTerritory;
    }

    public boolean isCastle() {
        return castle;
    }

    /* public boolean isOccupiedByAttackTroop() {
         return occupiedByAttackTroop;
     }

     public boolean isOccupiedByDefensiveTroop() {
         return occupiedByDefensiveTroop;
     }
 */
    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCastle(boolean castle) {
        this.castle = castle;
    }

    public void setEnemyTerritory(boolean enemyTerritory) {
        this.enemyTerritory = enemyTerritory;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    /*
    public void setOccupiedByAttackTroop(boolean occupiedByAttackTroop) {
        this.occupiedByAttackTroop = occupiedByAttackTroop;
    }

    public void setOccupiedByDefensiveTroop(boolean occupiedByDefensiveTroop) {
        this.occupiedByDefensiveTroop = occupiedByDefensiveTroop;
    }*/

    /*public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }*/
}
