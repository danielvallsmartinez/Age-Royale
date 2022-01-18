package Shared.Entity;

import java.io.Serializable;

public class Coin implements Serializable {

    private int numCoins;

    public Coin() {
        numCoins = 0;
    }

    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }

    public int getNumCoins() {
        return numCoins;
    }
}
