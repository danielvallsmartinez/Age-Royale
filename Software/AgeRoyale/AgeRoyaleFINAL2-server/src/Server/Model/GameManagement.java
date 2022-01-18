package Server.Model;

import Server.Model.Network.DedicatedServer;
import Shared.Entity.*;

import javax.swing.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.net.Socket;


public class GameManagement {

    public static double[] chooseTroop(Troop troop, Board board, User rivalUser){

        double distancia;
        double menorDistancia = 23;
        double [] posicio = new double[4];

        for (int u = 0; u < rivalUser.getAttackTroops().size(); u++) {

            distancia = troop.calcularDistancia(9 - rivalUser.getAttackTroops().get(u).getPosx(), 19 - rivalUser.getAttackTroops().get(u).getPosy());

            if(distancia < menorDistancia ){
                menorDistancia = distancia;
                posicio[0] = 9 - rivalUser.getAttackTroops().get(u).getPosx();
                posicio[1] = 19 - rivalUser.getAttackTroops().get(u).getPosy();
                posicio[2] = menorDistancia;
                posicio[3] = 1;
            }
        }

        for (int u = 0; u < rivalUser.getDefensiveTroops().size(); u++) {

            distancia = troop.calcularDistancia(9 - rivalUser.getDefensiveTroops().get(u).getPosx(), 19 - rivalUser.getDefensiveTroops().get(u).getPosy());

            if(distancia < menorDistancia ){
                menorDistancia = distancia;
                posicio[0] = 9 - rivalUser.getDefensiveTroops().get(u).getPosx();
                posicio[1] = 19 - rivalUser.getDefensiveTroops().get(u).getPosy();
                posicio[2] = menorDistancia;
                posicio[3] = 2;
            }
        }

        if (rivalUser.getTower().getHealth() > 0) {

            for (int u = 0; u < 6; u++) {

                distancia = troop.calcularDistancia(9 - rivalUser.getTower().getPosX()[u], 19 - rivalUser.getTower().getPosY()[u]);

                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    posicio[0] = 9 - rivalUser.getTower().getPosX()[u];
                    posicio[1] = 19 - rivalUser.getTower().getPosY()[u];
                    posicio[2] = menorDistancia;
                    posicio[3] = 0;
                }
            }
        }

        return posicio;
    }

    public Board addTroop(PositionSend positionSend, Board board, User user) throws IOException {
        String ruta;
        int monedes = 0;
        if(positionSend.getPositionY() <= 20){
            if(positionSend.getNumCard() == 1){
                if(user.getCoin() >= 3) {
                    ruta = "resources\\canon.png";
                    monedes = 3;

                    user.setCoin(user.getCoin()- monedes);
                    Square square = new Square(false, false, positionSend.getPositionY() , positionSend.getPositionX(), ruta, true);
                    board.getSquareMatrice()[positionSend.getPositionY()][positionSend.getPositionX()] = square;
                    Board boardGirat = giraTaulell(board);

                    DefensiveTroop canon = new DefensiveTroop("Canon",10,3,3,ruta,positionSend.getPositionX(), positionSend.getPositionY(), board, user, boardGirat, 2000 );
                    user.getDefensiveTroops().add(canon);
                    canon.startTimer();
                }
            }
            if(positionSend.getNumCard() == 2){
                if(user.getCoin() >= 6) {
                    ruta = "resources\\torreBomber.png";
                    monedes = 6;

                    user.setCoin(user.getCoin()- monedes);
                    Square square = new Square(false, false, positionSend.getPositionY() , positionSend.getPositionX(), ruta, true);
                    board.getSquareMatrice()[positionSend.getPositionY()][positionSend.getPositionX()] = square;
                    Board boardGirat = giraTaulell(board);

                    DefensiveTroop torreBomber = new DefensiveTroop("TorreBomber",20,5,5,ruta,positionSend.getPositionX(), positionSend.getPositionY(), board, user, boardGirat, 1000 );
                    user.getDefensiveTroops().add(torreBomber);
                    torreBomber.startTimer();
                }
            }
            if(positionSend.getNumCard() == 3){
                if(user.getCoin() >= 4) {
                    ruta = "resources\\barbaro.png";
                    monedes = 4;

                    user.setCoin(user.getCoin()- monedes);
                    Square square = new Square(false, false,  positionSend.getPositionY(), positionSend.getPositionX(), ruta, true);
                    board.getSquareMatrice()[positionSend.getPositionY()][positionSend.getPositionX()] = square;

                    AttackTroop barbaro = new AttackTroop("Barbaro",8,3,1,ruta, positionSend.getPositionX(), positionSend.getPositionY(), 1000, board, user);
                    user.getAttackTroops().add(barbaro);
                    barbaro.startTimer();
                }
            }
            if(positionSend.getNumCard() == 4){
                if(user.getCoin() >= 2) {
                    ruta = "resources\\archer.png";
                    monedes = 2;

                    user.setCoin(user.getCoin()- monedes);
                    Square square = new Square(false, false, positionSend.getPositionY() , positionSend.getPositionX(), ruta, true);
                    board.getSquareMatrice()[positionSend.getPositionY()][positionSend.getPositionX()] = square;

                    AttackTroop archer = new AttackTroop("Archer",6,2,3,ruta, positionSend.getPositionX(), positionSend.getPositionY(),2000, board, user);
                    user.getAttackTroops().add(archer);

                    archer.startTimer();
                }
            }
        }

        return board;
    }

    public static Board giraTaulell(Board board){

          Board board2 = new Board();

          for (int i = 0; i < 20; i++) {
              for (int j = 0; j < 10; j++) {

                  board2.getSquareMatrice()[19-i][9-j] = new Square();
                  board2.getSquareMatrice()[19-i][9-j].setPhotoURL(board.getSquareMatrice()[i][j].getPhotoURL());
                  board2.getSquareMatrice()[19-i][9-j].setOccupied(board.getSquareMatrice()[i][j].isOccupied());
                  board2.getSquareMatrice()[19-i][9-j].setCastle(board.getSquareMatrice()[i][j].isCastle());

                  if(board2.getSquareMatrice()[19-i][9-j].isOccupied()){

                      if(board2.getSquareMatrice()[19-i][9-j].getPhotoURL().equals("resources\\canon.png")){
                          board2.getSquareMatrice()[19-i][9-j].setPhotoURL("resources\\canon2.png"); //Foto girada
                      }
                      else {
                          if(board2.getSquareMatrice()[19-i][9-j].getPhotoURL().equals("resources\\canon2.png")){
                              board2.getSquareMatrice()[19-i][9-j].setPhotoURL("resources\\canon.png"); //Foto girada
                          }
                      }
                      if(board2.getSquareMatrice()[19-i][9-j].getPhotoURL().equals("resources\\torreBomber.png")){
                          board2.getSquareMatrice()[19-i][9-j].setPhotoURL("resources\\torreBomber2.png"); //Foto girada
                      }
                      else {
                          if(board2.getSquareMatrice()[19-i][9-j].getPhotoURL().equals("resources\\torreBomber2.png")){
                              board2.getSquareMatrice()[19-i][9-j].setPhotoURL("resources\\torreBomber.png"); //Foto girada
                          }
                      }
                      if(board2.getSquareMatrice()[19-i][9-j].getPhotoURL().equals("resources\\barbaro.png")){
                          board2.getSquareMatrice()[19-i][9-j].setPhotoURL("resources\\barbaro2.png"); //Foto girada
                      }
                      else {
                          if(board2.getSquareMatrice()[19-i][9-j].getPhotoURL().equals("resources\\barbaro2.png")){
                              board2.getSquareMatrice()[19-i][9-j].setPhotoURL("resources\\barbaro.png"); //Foto girada
                          }
                      }
                      if(board2.getSquareMatrice()[19-i][9-j].getPhotoURL().equals("resources\\archer.png")){
                          board2.getSquareMatrice()[19-i][9-j].setPhotoURL("resources\\archer2.png"); //Foto girada
                      }
                      else {
                          if(board2.getSquareMatrice()[19-i][9-j].getPhotoURL().equals("resources\\archer2.png")){
                              board2.getSquareMatrice()[19-i][9-j].setPhotoURL("resources\\archer.png"); //Foto girada
                          }
                      }
                  }
              }
          }

          return board2;
    }

    public static Board moveAttackTroop(Troop troop, Board board, double[] posicio, User rivalUser) throws IOException {

        if (posicio[3] != 0) {

            if (posicio[0] == troop.getPosx()) {

                if (posicio[1] < troop.getPosy()) {

                    if (troop.getAttackRange() < posicio[2]) {

                        if (!troop.isDefensive()) {
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                            troop.setPosy(troop.getPosy() - 1);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                        }
                    }
                    else {

                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                        boolean found = false;
                        for (int i = 0; i < rivalUser.getAttackTroops().size() && !found; i++) {

                            if ((9 - rivalUser.getAttackTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getAttackTroops().get(i).getPosy()) == (int) posicio[1]) {

                                rivalUser.getAttackTroops().get(i).setHealth(rivalUser.getAttackTroops().get(i).getHealth() - troop.getDamage());

                                if (rivalUser.getAttackTroops().get(i).getHealth() <= 0) {
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                    winCoins(troop.getUser());
                                    rivalUser.getAttackTroops().get(i).stopTimer();
                                    rivalUser.getAttackTroops().remove(i);
                                }

                                found = true;
                            }
                        }
                        for (int i = 0; i < rivalUser.getDefensiveTroops().size() && !found; i++) {

                            if ((9 - rivalUser.getDefensiveTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getDefensiveTroops().get(i).getPosy()) == (int) posicio[1]) {

                                rivalUser.getDefensiveTroops().get(i).setHealth(rivalUser.getDefensiveTroops().get(i).getHealth() - troop.getDamage());

                                if (rivalUser.getDefensiveTroops().get(i).getHealth() <= 0) {
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                    winCoins(troop.getUser());
                                    rivalUser.getDefensiveTroops().get(i).stopTimer();
                                    rivalUser.getDefensiveTroops().remove(i);
                                }

                                found = true;
                            }
                        }
                    }
                }
                if (posicio[1] > troop.getPosy()) {
                    if (troop.getAttackRange() < posicio[2]) {

                        if (!troop.isDefensive()) {
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");

                            troop.setPosy(troop.getPosy() + 1);

                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                        }
                    }
                    else {
                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                        boolean found = false;
                        for (int i = 0; i < rivalUser.getAttackTroops().size() && !found; i++) {

                            if ((9 - rivalUser.getAttackTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getAttackTroops().get(i).getPosy()) == (int) posicio[1]) {

                                rivalUser.getAttackTroops().get(i).setHealth(rivalUser.getAttackTroops().get(i).getHealth() - troop.getDamage());

                                if (rivalUser.getAttackTroops().get(i).getHealth() <= 0) {
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                    winCoins(troop.getUser());
                                    rivalUser.getAttackTroops().get(i).stopTimer();
                                    rivalUser.getAttackTroops().remove(i);
                                }

                                found = true;
                            }
                        }
                        for (int i = 0; i < rivalUser.getDefensiveTroops().size() && !found; i++) {

                            if ((9 - rivalUser.getDefensiveTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getDefensiveTroops().get(i).getPosy()) == (int) posicio[1]) {

                                rivalUser.getDefensiveTroops().get(i).setHealth(rivalUser.getDefensiveTroops().get(i).getHealth() - troop.getDamage());

                                if (rivalUser.getDefensiveTroops().get(i).getHealth() <= 0) {
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                    winCoins(troop.getUser());
                                    rivalUser.getDefensiveTroops().get(i).stopTimer();
                                    rivalUser.getDefensiveTroops().remove(i);
                                }

                                found = true;
                            }
                        }
                    }
                }
            }

            if (posicio[1] == troop.getPosy()) {

                if (posicio[0] < troop.getPosx()) {

                    if (troop.getAttackRange() < posicio[2]) {

                        if (!troop.isDefensive()) {
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                            troop.setPosx(troop.getPosx() - 1);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                        }
                    }
                    else {

                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                        boolean found = false;
                        for (int i = 0; i < rivalUser.getAttackTroops().size() && !found; i++) {

                            if ((9 - rivalUser.getAttackTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getAttackTroops().get(i).getPosy()) == (int) posicio[1]) {

                                rivalUser.getAttackTroops().get(i).setHealth(rivalUser.getAttackTroops().get(i).getHealth() - troop.getDamage());

                                if (rivalUser.getAttackTroops().get(i).getHealth() <= 0) {
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                    winCoins(troop.getUser());
                                    rivalUser.getAttackTroops().get(i).stopTimer();
                                    rivalUser.getAttackTroops().remove(i);
                                }

                                found = true;
                            }
                        }
                        for (int i = 0; i < rivalUser.getDefensiveTroops().size() && !found; i++) {

                            if ((9 - rivalUser.getDefensiveTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getDefensiveTroops().get(i).getPosy()) == (int) posicio[1]) {

                                rivalUser.getDefensiveTroops().get(i).setHealth(rivalUser.getDefensiveTroops().get(i).getHealth() - troop.getDamage());

                                if (rivalUser.getDefensiveTroops().get(i).getHealth() <= 0) {
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                    winCoins(troop.getUser());
                                    rivalUser.getDefensiveTroops().get(i).stopTimer();
                                    rivalUser.getDefensiveTroops().remove(i);
                                }

                                found = true;
                            }
                        }
                    }
                }
                if (posicio[0] > troop.getPosx()) {

                    if (troop.getAttackRange() < posicio[2]) {

                        if (!troop.isDefensive()) {
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                            troop.setPosx(troop.getPosx() + 1);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                        }
                    }
                    else {

                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                        boolean found = false;
                        for (int i = 0; i < rivalUser.getAttackTroops().size() && !found; i++) {

                            if ((9 - rivalUser.getAttackTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getAttackTroops().get(i).getPosy()) == (int) posicio[1]) {

                                rivalUser.getAttackTroops().get(i).setHealth(rivalUser.getAttackTroops().get(i).getHealth() - troop.getDamage());

                                if (rivalUser.getAttackTroops().get(i).getHealth() <= 0) {
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                    winCoins(troop.getUser());
                                    rivalUser.getAttackTroops().get(i).stopTimer();
                                    rivalUser.getAttackTroops().remove(i);
                                }

                                found = true;
                            }
                        }
                        for (int i = 0; i < rivalUser.getDefensiveTroops().size() && !found; i++) {

                            if ((9 - rivalUser.getDefensiveTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getDefensiveTroops().get(i).getPosy()) == (int) posicio[1]) {

                                rivalUser.getDefensiveTroops().get(i).setHealth(rivalUser.getDefensiveTroops().get(i).getHealth() - troop.getDamage());

                                if (rivalUser.getDefensiveTroops().get(i).getHealth() <= 0) {
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                    board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                    winCoins(troop.getUser());
                                    rivalUser.getDefensiveTroops().get(i).stopTimer();
                                    rivalUser.getDefensiveTroops().remove(i);
                                }

                                found = true;
                            }
                        }
                    }
                }
            }

            if (posicio[1] != troop.getPosy() && posicio[0] != troop.getPosx()) {

                if (troop.getLastMove() == 1) {

                    if (posicio[0] < troop.getPosx()) {

                        if (troop.getAttackRange() < posicio[2]) {

                            if (!troop.isDefensive()) {
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                                troop.setPosx(troop.getPosx() - 1);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                                troop.setLastMove(0);
                            }
                        }
                        else {

                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                            boolean found = false;
                            for (int i = 0; i < rivalUser.getAttackTroops().size() && !found; i++) {

                                if ((9 - rivalUser.getAttackTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getAttackTroops().get(i).getPosy()) == (int) posicio[1]) {

                                    rivalUser.getAttackTroops().get(i).setHealth(rivalUser.getAttackTroops().get(i).getHealth() - troop.getDamage());

                                    if (rivalUser.getAttackTroops().get(i).getHealth() <= 0) {
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                        winCoins(troop.getUser());
                                        rivalUser.getAttackTroops().get(i).stopTimer();
                                        rivalUser.getAttackTroops().remove(i);
                                    }

                                    found = true;
                                }
                            }
                            for (int i = 0; i < rivalUser.getDefensiveTroops().size() && !found; i++) {

                                if ((9 - rivalUser.getDefensiveTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getDefensiveTroops().get(i).getPosy()) == (int) posicio[1]) {

                                    rivalUser.getDefensiveTroops().get(i).setHealth(rivalUser.getDefensiveTroops().get(i).getHealth() - troop.getDamage());

                                    if (rivalUser.getDefensiveTroops().get(i).getHealth() <= 0) {
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                        winCoins(troop.getUser());
                                        rivalUser.getDefensiveTroops().get(i).stopTimer();
                                        rivalUser.getDefensiveTroops().remove(i);
                                    }

                                    found = true;
                                }
                            }
                        }
                    }
                    if (posicio[0] > troop.getPosx()) {

                        if (troop.getAttackRange() < posicio[2]) {

                            if (!troop.isDefensive()) {
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                                troop.setPosx(troop.getPosx() + 1);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                                troop.setLastMove(0);
                            }
                        }
                        else {

                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                            boolean found = false;
                            for (int i = 0; i < rivalUser.getAttackTroops().size() && !found; i++) {

                                if ((9 - rivalUser.getAttackTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getAttackTroops().get(i).getPosy()) == (int) posicio[1]) {

                                    rivalUser.getAttackTroops().get(i).setHealth(rivalUser.getAttackTroops().get(i).getHealth() - troop.getDamage());

                                    if (rivalUser.getAttackTroops().get(i).getHealth() <= 0) {
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                        winCoins(troop.getUser());
                                        rivalUser.getAttackTroops().get(i).stopTimer();
                                        rivalUser.getAttackTroops().remove(i);
                                    }

                                    found = true;
                                }
                            }
                            for (int i = 0; i < rivalUser.getDefensiveTroops().size() && !found; i++) {

                                if ((9 - rivalUser.getDefensiveTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getDefensiveTroops().get(i).getPosy()) == (int) posicio[1]) {

                                    rivalUser.getDefensiveTroops().get(i).setHealth(rivalUser.getDefensiveTroops().get(i).getHealth() - troop.getDamage());

                                    if (rivalUser.getDefensiveTroops().get(i).getHealth() <= 0) {
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                        winCoins(troop.getUser());
                                        rivalUser.getDefensiveTroops().get(i).stopTimer();
                                        rivalUser.getDefensiveTroops().remove(i);
                                    }

                                    found = true;
                                }
                            }
                        }
                    }
                }
                else {

                    if (posicio[1] < troop.getPosy()) {

                        if (troop.getAttackRange() < posicio[2]) {

                            if (!troop.isDefensive()) {
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                                troop.setPosy(troop.getPosy() - 1);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                                troop.setLastMove(1);
                            }
                        }
                        else {

                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                            boolean found = false;
                            for (int i = 0; i < rivalUser.getAttackTroops().size() && !found; i++) {

                                if ((9 - rivalUser.getAttackTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getAttackTroops().get(i).getPosy()) == (int) posicio[1]) {

                                    rivalUser.getAttackTroops().get(i).setHealth(rivalUser.getAttackTroops().get(i).getHealth() - troop.getDamage());

                                    if (rivalUser.getAttackTroops().get(i).getHealth() <= 0) {
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                        winCoins(troop.getUser());
                                        rivalUser.getAttackTroops().get(i).stopTimer();
                                        rivalUser.getAttackTroops().remove(i);
                                    }

                                    found = true;
                                }
                            }
                            for (int i = 0; i < rivalUser.getDefensiveTroops().size() && !found; i++) {

                                if ((9 - rivalUser.getDefensiveTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getDefensiveTroops().get(i).getPosy()) == (int) posicio[1]) {

                                    rivalUser.getDefensiveTroops().get(i).setHealth(rivalUser.getDefensiveTroops().get(i).getHealth() - troop.getDamage());

                                    if (rivalUser.getDefensiveTroops().get(i).getHealth() <= 0) {
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                        winCoins(troop.getUser());
                                        rivalUser.getDefensiveTroops().get(i).stopTimer();
                                        rivalUser.getDefensiveTroops().remove(i);
                                    }

                                    found = true;
                                }
                            }
                        }
                    }
                    if (posicio[1] > troop.getPosy()) {

                        if (troop.getAttackRange() < posicio[2]) {

                            if (!troop.isDefensive()) {
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                                troop.setPosy(troop.getPosy() + 1);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                                troop.setLastMove(1);
                            }
                        }
                        else {

                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                            boolean found = false;
                            for (int i = 0; i < rivalUser.getAttackTroops().size() && !found; i++) {

                                if ((9 - rivalUser.getAttackTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getAttackTroops().get(i).getPosy()) == (int) posicio[1]) {

                                    rivalUser.getAttackTroops().get(i).setHealth(rivalUser.getAttackTroops().get(i).getHealth() - troop.getDamage());

                                    if (rivalUser.getAttackTroops().get(i).getHealth() <= 0) {
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                        winCoins(troop.getUser());
                                        rivalUser.getAttackTroops().get(i).stopTimer();
                                        rivalUser.getAttackTroops().remove(i);
                                    }

                                    found = true;
                                }
                            }
                            for (int i = 0; i < rivalUser.getDefensiveTroops().size() && !found; i++) {

                                if ((9 - rivalUser.getDefensiveTroops().get(i).getPosx()) == (int) posicio[0] && (19 - rivalUser.getDefensiveTroops().get(i).getPosy()) == (int) posicio[1]) {

                                    rivalUser.getDefensiveTroops().get(i).setHealth(rivalUser.getDefensiveTroops().get(i).getHealth() - troop.getDamage());

                                    if (rivalUser.getDefensiveTroops().get(i).getHealth() <= 0) {
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setOccupied(false);
                                        board.getSquareMatrice()[(int) posicio[1]][(int) posicio[0]].setPhotoURL(" ");
                                        winCoins(troop.getUser());
                                        rivalUser.getDefensiveTroops().get(i).stopTimer();
                                        rivalUser.getDefensiveTroops().remove(i);
                                    }

                                    found = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        else {

            if (posicio[0] == troop.getPosx()) {

                if (posicio[1] < troop.getPosy()) {

                    if (troop.getAttackRange() < posicio[2]) {

                        if (!troop.isDefensive()) {
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                            troop.setPosy(troop.getPosy() - 1);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                        }
                    }
                    else {

                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                        rivalUser.getTower().setHealth(rivalUser.getTower().getHealth() - troop.getDamage());
                    }
                }
                if (posicio[1] > troop.getPosy()) {
                    if (troop.getAttackRange() < posicio[2]) {

                        if (!troop.isDefensive()) {
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");

                            troop.setPosy(troop.getPosy() + 1);

                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                        }
                    }
                    else {
                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                        rivalUser.getTower().setHealth(rivalUser.getTower().getHealth() - troop.getDamage());
                    }
                }
            }

            if (posicio[1] == troop.getPosy()) {

                if (posicio[0] < troop.getPosx()) {

                    if (troop.getAttackRange() < posicio[2]) {

                        if (!troop.isDefensive()) {
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                            troop.setPosx(troop.getPosx() - 1);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                        }
                    }
                    else {

                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                        rivalUser.getTower().setHealth(rivalUser.getTower().getHealth() - troop.getDamage());
                    }
                }
                if (posicio[0] > troop.getPosx()) {

                    if (troop.getAttackRange() < posicio[2]) {

                        if (!troop.isDefensive()) {
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                            troop.setPosx(troop.getPosx() + 1);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                        }
                    }
                    else {

                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                        board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                        rivalUser.getTower().setHealth(rivalUser.getTower().getHealth() - troop.getDamage());
                    }
                }
            }

            if (posicio[1] != troop.getPosy() && posicio[0] != troop.getPosx()) {

                if (troop.getLastMove() == 1) {

                    if (posicio[0] < troop.getPosx()) {

                        if (troop.getAttackRange() < posicio[2]) {

                            if (!troop.isDefensive()) {
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                                troop.setPosx(troop.getPosx() - 1);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                                troop.setLastMove(0);
                            }
                        }
                        else {

                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                            rivalUser.getTower().setHealth(rivalUser.getTower().getHealth() - troop.getDamage());
                        }
                    }
                    if (posicio[0] > troop.getPosx()) {

                        if (troop.getAttackRange() < posicio[2]) {

                            if (!troop.isDefensive()) {
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                                troop.setPosx(troop.getPosx() + 1);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                                troop.setLastMove(0);
                            }
                        }
                        else {

                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                            rivalUser.getTower().setHealth(rivalUser.getTower().getHealth() - troop.getDamage());
                        }
                    }
                }
                else {

                    if (posicio[1] < troop.getPosy()) {

                        if (troop.getAttackRange() < posicio[2]) {

                            if (!troop.isDefensive()) {
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                                troop.setPosy(troop.getPosy() - 1);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                                troop.setLastMove(1);
                            }
                        }
                        else {

                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                            rivalUser.getTower().setHealth(rivalUser.getTower().getHealth() - troop.getDamage());
                        }
                    }
                    if (posicio[1] > troop.getPosy()) {

                        if (troop.getAttackRange() < posicio[2]) {

                            if (!troop.isDefensive()) {
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(false);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(" ");
                                troop.setPosy(troop.getPosy() + 1);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                                board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());
                                troop.setLastMove(1);
                            }
                        }
                        else {

                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setOccupied(true);
                            board.getSquareMatrice()[troop.getPosy()][troop.getPosx()].setPhotoURL(troop.getPhotoURL());

                            rivalUser.getTower().setHealth(rivalUser.getTower().getHealth() - troop.getDamage());
                        }
                    }
                }
            }
        }

        return board;
    }

    public static void winCoins(User user) throws IOException {

        if (user.getCoin() < 9) {
            user.setCoin(user.getCoin() + 2);
            user.getDedicatedServer().agafaMonedes(user.getCoin() + 2);
        }
    }
}
