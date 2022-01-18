package Client.View;

import Client.Controller.GameController;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

public class GameView extends JFrame{
    private JButton jbExit;
    private JPanel jpMiddle;
    private String enemyName;
    private String playerName;
    private Background card1;
    private Background card2;
    private Background card3;
    private Background card4;
    private Background square;
    private Background[][] squares;
    private JLabel jlCoin;

    public GameView(String enemyName) throws IOException {

        Background background = new Background();
        add(background);
        BoxLayout bl = new BoxLayout(background, BoxLayout.X_AXIS);
        background.setLayout(bl);

        setVisible(true);
        setTitle("Age Royale");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        Dimension dimensionLateral = new Dimension((int) (screenSize.width*0.3), screenSize.height);
        Background backgroundLeft = new Background();
        backgroundLeft.setBackground("resources/foto_esquerra_partida.jpg");
        backgroundLeft.setPreferredSize(dimensionLateral);

        Background backgroundPitch = new Background();
        backgroundPitch.setBackground("resources/tablero.jpeg");
        backgroundPitch.setPreferredSize(dimensionLateral);
        BoxLayout blCenter = new BoxLayout(backgroundPitch, BoxLayout.Y_AXIS);
        backgroundPitch.setLayout(blCenter);


        Font font = new Font("Supercell-Magic", Font.BOLD,15);

        Dimension dimensionTop = new Dimension((int) (screenSize.width*0.3), (int) (screenSize.height*0.12));
        JPanel jpTop = new JPanel();
        jpTop.setOpaque(false);
        BoxLayout blTop = new BoxLayout(jpTop, BoxLayout.X_AXIS);
        GridLayout glTop = new GridLayout(1,2);
        jpTop.setLayout(glTop);
        jbExit = new JButton("Exit");
        Font fontButton = new Font("Supercell-Magic", Font.BOLD,10);
        jbExit = new JButton("Exit");
        //jbExit.setBorderPainted(false);
        //jbExit.setContentAreaFilled(false);
        jbExit.setFont(fontButton);
        jbExit.setForeground(Color.DARK_GRAY);
        Dimension dimensionJBTop = new Dimension((int) (screenSize.width*0.05), (int) (screenSize.height*0.07));
        jbExit.setMaximumSize(dimensionJBTop);
        jbExit.setHorizontalAlignment(JButton.RIGHT);
        JPanel jpTopLeft = new JPanel();
        jpTopLeft.setOpaque(false);
        jpTopLeft.add(jbExit);
        JLabel jlAux = new JLabel();
        jlAux.setPreferredSize(dimensionJBTop);
        jpTopLeft.add(jlAux);
        jpTop.add(jpTopLeft);
        Dimension dimensionJLTop = new Dimension((int) (screenSize.width*0.2), (int) (screenSize.height*0.12));
        JLabel jlTop = new JLabel(""+enemyName+"           ");
        jlTop.setPreferredSize(dimensionJLTop);
        jlTop.setFont(font);
        jlTop.setForeground(Color.white);
        jlTop.setBackground(Color.white);
        jlTop.setHorizontalAlignment(JLabel.RIGHT);
        jlTop.setOpaque(false);
        jpTop.add(jlTop);

        jpTop.setPreferredSize(dimensionTop);
        backgroundPitch.add(jpTop);

        jpMiddle = new JPanel();
        JPanel jpMiddle1 = new JPanel();
        BoxLayout blMiddle1 = new BoxLayout(jpMiddle1, BoxLayout.X_AXIS);
        jpMiddle1.setLayout(blMiddle1);
        jpMiddle1.setOpaque(false);
        Dimension dimensionBorder = new Dimension((int) (screenSize.width*0.045),(int) (screenSize.height*0.6));
        JLabel jlMiddle1Right = new JLabel();
        jlMiddle1Right.setPreferredSize(dimensionBorder);
        jlMiddle1Right.setOpaque(false);
        JLabel jlMiddle1Left = new JLabel();
        jlMiddle1Left.setPreferredSize(dimensionBorder);
        jlMiddle1Left.setOpaque(false);
        jpMiddle1.add(jlMiddle1Left);
        jpMiddle1.add(jpMiddle);
        jpMiddle1.add(jlMiddle1Right);
        Dimension dimensionMiddle = new Dimension((int) (screenSize.width*0.3),(int) (screenSize.height*0.9));
        //BoxLayout blMiddle = new BoxLayout(jpMiddle, BoxLayout.Y_AXIS);
        //jpMiddle.setLayout(blMiddle);
        jpMiddle.setPreferredSize(dimensionMiddle);

        //----------
        GridLayout gdMiddle = new GridLayout(20, 10);
        Border border = BorderFactory.createLineBorder(Color.white, 1);
        jpMiddle.setLayout(gdMiddle);
        squares = new Background[20][10];
        for (int i = 0; i < 20; i++) {
            for(int j = 0; j < 10; j++) {
                square = new Background();
                square.setPositionX(j);
                square.setPositionY(i);
                square.setBorder(border);
                square.setOpaque(false);
                //square.setBackground(square.getImagePath());
                squares[i][j] = square;
                jpMiddle.add(squares[i][j]);
            }
        }

        //jpMiddle.setBackground(Color.black);
        jpMiddle.setOpaque(false);
        backgroundPitch.add(jpMiddle1);


        //----------

        JPanel jpBottom = new JPanel();
        //jlBottom.setOpaque(false);
        Dimension dimensionBottom = new Dimension((int) (screenSize.width*0.3), (int) (screenSize.height*0.39));
        jpBottom.setOpaque(false);
        //jpBottom.setBackground(Color.blue);
        jpBottom.setPreferredSize(dimensionBottom);
        BorderLayout blBottom = new BorderLayout();
        jpBottom.setLayout(blBottom);
        JPanel jpBottomCenter = new JPanel(blBottom);
        BoxLayout blBottomCenter = new BoxLayout(jpBottomCenter, BoxLayout.X_AXIS);
        jpBottomCenter.setLayout(blBottomCenter);
        //jpBottomCenter.setBackground(Color.black);
        jpBottomCenter.setOpaque(false);
        //JLabel jlBottomTop = new JLabel(""+playerName+"");
        JLabel jlBottomTop = new JLabel();
        jlBottomTop.setFont(font);
        jlBottomTop.setForeground(Color.white);
        jlBottomTop.setHorizontalAlignment(JLabel.CENTER);
        //jlBottomTop.setVerticalAlignment(J);
        //jlBottomTop.setBackground(Color.black);
        Dimension dimensionBottomTop = new Dimension((int) (screenSize.width), (int) (screenSize.height*0.06));
        jlBottomTop.setPreferredSize(dimensionBottomTop);
        jpBottom.add(jlBottomTop, BorderLayout.NORTH);
        jpBottom.add(jpBottomCenter, BorderLayout.CENTER);
        jlCoin = new JLabel("0");
        Font fontCoin = new Font("Supercell-Magic", Font.BOLD,21);
        jlCoin.setFont(fontCoin);
        jlCoin.setHorizontalAlignment(JLabel.CENTER);
        Dimension dimensionCoin = new Dimension((int) (screenSize.width*0.13), (int) (screenSize.height*0.1));
        jlCoin.setPreferredSize(dimensionCoin);
        jlCoin.setForeground(Color.WHITE);
        jpBottomCenter.add(jlCoin);
        Dimension dimensionVerticalGap = new Dimension((int) (screenSize.width*0.01), (int) (screenSize.height));
        JLabel jlVerticalGap = new JLabel();
        jlVerticalGap.setPreferredSize(dimensionVerticalGap);
        jlVerticalGap.setBorder(border);
        JLabel jlVerticalGap1 = new JLabel();
        jlVerticalGap1.setPreferredSize(dimensionVerticalGap);
        jlVerticalGap1.setBorder(border);
        JLabel jlVerticalGap2 = new JLabel();
        jlVerticalGap2.setPreferredSize(dimensionVerticalGap);
        jlVerticalGap2.setBorder(border);
        JLabel jlVerticalGap3 = new JLabel();
        jlVerticalGap3.setPreferredSize(dimensionVerticalGap);
        jlVerticalGap3.setBorder(border);
        JLabel jlVerticalGap4 = new JLabel();
        jlVerticalGap4.setPreferredSize(dimensionVerticalGap);
        jlVerticalGap4.setBorder(border);
        jpBottomCenter.add(jlVerticalGap4);
        Dimension dimensionCard = new Dimension((int) (screenSize.width*0.16), (int) (screenSize.height*0.16));
        /*jlCard1 = new JLabel("Card1");
        jlCard1.setPreferredSize(dimensionCard);
        jlCard1.setBorder(border);
        jbCard1 = new JButton();
        jbCard1.setPreferredSize(dimensionCard);
        jbCard1.setOpaque(false);
        jbCard1.setContentAreaFilled(false);
        jbCard1.add(jlCard1);*/
        card1 = new Background();
        //card1.setBackground("resources\\card1.jpg");
        card1.setPositionX(1);
        card1.setPositionY(50);
        card1.setPreferredSize(dimensionCard);
        card1.setVisible(true);
        card1.setOpaque(false);

        card2 = new Background();
        card2.setPositionX(2);
        card2.setPositionY(50);
        card2.setPreferredSize(dimensionCard);
        card2.setVisible(true);
        card2.setOpaque(false);

        card3 = new Background();
        card3.setPositionX(3);
        card3.setPositionY(50);
        card3.setPreferredSize(dimensionCard);
        card3.setVisible(true);
        card3.setOpaque(false);

        card4 = new Background();
        card4.setPositionX(4);
        card4.setPositionY(50);
        card4.setPreferredSize(dimensionCard);
        card4.setVisible(true);
        card4.setOpaque(false);

        jpBottomCenter.add(card1);
        jpBottomCenter.add(jlVerticalGap);
        jpBottomCenter.add(card2);
        jpBottomCenter.add(jlVerticalGap1);
        jpBottomCenter.add(card3);
        jpBottomCenter.add(jlVerticalGap2);
        jpBottomCenter.add(card4);
        //jpBottomCenter.add(jlVerticalGap3);
        backgroundPitch.add(jpBottom);


        Background backgroundRight = new Background();
        backgroundRight.setBackground("resources/foto_dreta_partida.jpg");
        backgroundRight.setPreferredSize(dimensionLateral);
        //JPanel jpRight = new JPanel();
        //jpRight.setBackground(Color.GREEN);
        //jpRight.setPreferredSize(dimensionLateral);

        background.add(backgroundLeft);
        background.add(backgroundPitch);
        background.add(backgroundRight);

    }

    public void gameController(GameController gameController) {
        jbExit.addActionListener(gameController);
        card1.addMouseListener(gameController);
        card2.addMouseListener(gameController);
        card3.addMouseListener(gameController);
        card4.addMouseListener(gameController);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                squares[i][j].addMouseListener(gameController);
            }
        }
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Background[][] getSquares() {
        return squares;
    }

    public void setCard1(Background card1) {
        this.card1 = card1;
    }

    public void setCard2(Background card2) {
        this.card2 = card2;
    }

    public void setCard3(Background card3) {
        this.card3 = card3;
    }

    public void setCard4(Background card4) {
        this.card4 = card4;
    }



    public void setSquares(Background[][] squares) {
        for (int i = 0; i < 20; i++) {
            for(int j = 0; j < 10; j++) {
                this.squares[i][j].setBackground(squares[i][j].getImagePath());
                this.squares[i][j].setOpaque(false);
            }
        }
    }

    public void setNumCoins(int numCoins) {
        jlCoin.setText(""+numCoins+"");
    }
}