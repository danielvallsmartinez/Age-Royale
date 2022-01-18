package Server.View;

import javax.swing.*;

public class Top10View extends JFrame {


    public Top10View(String[][] data) {

        String column[] = {"Position", "User Name", "Number of Games Won", "% of Victories", "Avg. Time per Game Won"};

        JTable jt = new JTable(data, column);
        jt.setBounds(30, 40, 800, 300);
        JScrollPane sp = new JScrollPane(jt);
        super.add(sp);
        super.setSize(800, 210);
        super.setVisible(true);
    }
}
