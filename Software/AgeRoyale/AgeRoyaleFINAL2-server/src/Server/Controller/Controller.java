package Server.Controller;

import Server.View.GameEvolutionView;
import Server.Model.DataBase.DAO.GameDAO;
import Server.Model.DataBase.DAO.UserDAO;
import Server.Model.Network.NetworkModel;
import Server.View.Top10View;
import Shared.Entity.Game;
import Shared.Entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.LinkedList;

public class Controller implements ActionListener {

    private LinkedList<Game> games;

    private GameDAO gameDAO;
    private UserDAO userDAO;

    private LinkedList<Integer> week;
    private LinkedList<Integer> month;
    private LinkedList<Integer> year;

    private SimpleDateFormat dateFormat;
    private Date date;

    private GameEvolutionView gameEvolutionView;
    private Top10View top10View;

    public Controller(NetworkModel networkModel) {
        games = new LinkedList<>();
        gameDAO = new GameDAO(networkModel);
        userDAO = new UserDAO(networkModel);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();

        if(button.getText().equals("Week's Graphic")){


            try {
                gameEvolutionView = new GameEvolutionView(gamesPerWeek());
                gameEvolutionView.createAndShowGui();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        if(button.getText().equals("Month's Graphic")){
            try {


                gameEvolutionView = new GameEvolutionView(gamesPerMonth());
                gameEvolutionView.createAndShowGui();


            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }

        if(button.getText().equals("Year's Graphic")){

            try {

                gameEvolutionView = new GameEvolutionView(gamesPerYear());
                gameEvolutionView.createAndShowGui();


            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if(button.getText().equals("Ranking")){

            top10View = new Top10View(top10());

        }


    }

    public String[][] top10(){

        int [][] top10 = gameDAO.findTop10Users();
        int[][] games = gameDAO.gamesPlayedPerUSer();
        LinkedList<User> users;
        users = userDAO.getAllUsers();
        int nUsers = userDAO.countAllUsers();

        String[] position = new String[11];
        String[] user = new String[11];
        String[] gamesWon = new String[11];
        String[] winRate = new String[11];
        String[] avgTime = new String[11];

        for(int i = 0; i < 10; i++){

            position[i] = Integer.toString(i+1);
            System.out.println("abans for");
            for(int j = 0; j < nUsers; j++){
                if(users.get(j).getId_user() == top10[i][1]){
                    user [i] = users.get(j).getName();
                }
            }
            System.out.println("abans hola");
            System.out.println("Hola: "+games[top10[i][1]-1][0]);
            gamesWon [i] = Integer.toString(top10[i][0]);
            float wins = ((float) top10[i][0]/(float)games[top10[i][1]-1][0]) * 100;
            winRate [i] = Double.toString(wins);
            avgTime [i] = Integer.toString((top10[i][2]/top10[i][0]));
            System.out.println("arriba");

        }


        String data[][] = {{position[0], user[0], gamesWon[0],winRate[0],avgTime[0]},
                {position[1], user[1], gamesWon[1],winRate[1],avgTime[1]},
                {position[2], user[2], gamesWon[2],winRate[2],avgTime[2]},
                {position[3], user[3], gamesWon[3],winRate[3],avgTime[3]},
                {position[4], user[4], gamesWon[4],winRate[4],avgTime[4]},
                {position[5], user[5], gamesWon[5],winRate[5],avgTime[5]},
                {position[6], user[6], gamesWon[6],winRate[6],avgTime[6]},
                {position[7], user[7], gamesWon[7],winRate[7],avgTime[7]},
                {position[8], user[8], gamesWon[8],winRate[8],avgTime[8]},
                {position[9], user[9], gamesWon[9],winRate[9],avgTime[9]},
        };


        return data;

    }


    public LinkedList<Integer> gamesPerWeek()throws SQLException {
        games = gameDAO.getAllGames();
        week = new LinkedList<>();


        for (int i = 0; i < 8; i++) {
            week.add(i, 0);
        }

        for(int i = 0; i < games.size(); i++){

            LocalDate now = LocalDate.now();
            LocalDate gameDate = LocalDate.parse(games.get(i).getDate());

            Period period = Period.between(gameDate, now);
            int diffMonths = period.getMonths();
            int diffDays = period.getDays();


            if(diffMonths == 0) {
                if (diffDays < 8) {
                    week.set(diffDays, week.get(diffDays) + 1);

                }
            }

        }
        return week;
    }

    public LinkedList<Integer> gamesPerMonth() throws SQLException{
        games = gameDAO.getAllGames();
        month = new LinkedList<>();

        for (int i = 0; i < 32; i++) {
            month.add(i, 0);
        }

        for(int i = 0; i < games.size(); i++){

            LocalDate now = LocalDate.now();
            LocalDate gameDate = LocalDate.parse(games.get(i).getDate());

            Period period = Period.between(gameDate, now);
            int diff =  period.getDays();
            int diffMonth = period.getMonths();

            if(diffMonth > 0){
                diff = diff + 30 * diffMonth;
            }
            if(diff < 32){
                month.set(diff, (month.get(diff))+1);
            }

        }
        return month;
    }

    public LinkedList<Integer> gamesPerYear()throws SQLException {
        games = gameDAO.getAllGames();
        year = new LinkedList<>();

        for (int i = 0; i < 366; i++) {
            year.add(i, 0);
        }

        for(int i = 0; i < games.size(); i++){

            LocalDate now = LocalDate.now();
            LocalDate gameDate = LocalDate.parse(games.get(i).getDate());

            Period period = Period.between(gameDate, now);
            int diff = period.getDays();
            int diffMonth = period.getMonths();
            int diffYear = period.getYears();

            if(diffYear == 0){
                diff = diff + 30 * diffMonth;
            }

            if(diff < 366){
                year.set(diff, (year.get(diff))+1);

            }

        }
        return year;
    }

}
