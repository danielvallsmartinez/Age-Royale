package Server.View;

/*


import Controlador.MenuListenerBotons;
import Vista.Graph.VistaGraficaAnyo;
import Vista.Graph.VistaGraficaMes;*/

import Server.Controller.Controller;

import javax.swing.*;
import java.awt.*;

/**Classe que forma la vista del server*/
public class ServerView extends JFrame {

    private JButton jbyear;
    private JButton jbweek;
    private JButton jbmonth;
    private JButton jbranking;

    public ServerView(){
        JPanel graficas = new JPanel();
        graficas.setLayout(new GridLayout(2,2));

        jbranking = new JButton("Ranking");
        jbranking.setActionCommand("Ranking");
        jbyear = new JButton("Year's Graphic");
        jbyear.setActionCommand("Year's Graphic");
        jbmonth = new JButton("Month's Graphic");
        jbmonth.setActionCommand("Month's Graphic");
        jbweek = new JButton("Week's Graphic");
        jbweek.setActionCommand("Week's Graphic");

        graficas.add(jbyear);
        graficas.add(jbmonth);
        graficas.add(jbweek);
        graficas.add(jbranking);

        super.add(graficas);

        Dimension dim = new Dimension();
        super.setSize(1500,750);
        super.setTitle("AGE ROYALE SERVER");
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setLocationByPlatform(true);
        super.setVisible(true);
        super.setResizable(false);
        super.setFocusable(true);


    }

    public void Controller(Controller controller){
        jbweek.addActionListener(controller);
        jbmonth.addActionListener(controller);
        jbyear.addActionListener(controller);
        jbranking.addActionListener(controller);
    }


}

