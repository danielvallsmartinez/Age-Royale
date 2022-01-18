package Client.View;

import Client.Controller.MenuController;
import Shared.Entity.Friend;
import Shared.Entity.Game;
import Shared.Entity.Request;
import Shared.Entity.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class MenuView extends JFrame {

    private Background jpUserProfileImage;
    private Background background;
    private JLabel userName;
    private JScrollPane jspPartidas;
    private JScrollPane jspPartidasEspectador;
    private JScrollPane jspFriends;
    private JScrollPane jspRequests;
    private JScrollPane jspInvitarAmigos;
    private JPanel jpPartidas;
    private JPanel jpPartidasEspectador;
    private JPanel jpSelectGameType;
    private JPanel jpScrollGames;
    private JPanel jpPrivadaPublica;
    private JPanel jpListaAmigos;
    private JPanel jpSolicitudes;
    private JPanel jpBuscandoRival;
    private JPanel jpVictoriaDerrota;
    private JPanel jpInvitarAmigos;
    private JButton jbEspectar;
    private JButton jbCrearPartida;
    private JButton jbBuscarPartida;
    private JButton jbAtrasBuscarPartida;
    private JButton jbAtrasSpectator;
    private JButton jbAtrasCrearPartida;
    private JButton jbAtrasInvitarAmigos;
    private JButton jbPublica;
    private JButton jbPrivada;
    private JButton jbEnviarSolicitud;
    private JButton jbSalir;
    private JLabel jlVictoriaDerrota;
    private JTabbedPane jtpAmigos;
    private JTextField jtfNombrePartida;
    private JTextField jtfAmigo;
    private LinkedList<Game> availableGamesAuxiliar;
    private LinkedList<Game> availableGamesSpectatorAuxiliar;
    private LinkedList<JPanel> availableGamesJPanels;
    private LinkedList<JButton> availableGamesJButtons;  //Guarda los JButtons de availableGamesJPanels para poder obtener su actionCommand en el menuController y poder relacionarlos con susu respectivas JLabels (en availableGamesJPanels)
    private LinkedList<JPanel> availableSpectatorGamesJPanels;
    private LinkedList<JButton> availableSpectatorGamesJButtons;
    private LinkedList<Friend> friendsAuxiliar;
    private LinkedList<Request> requestsAuxiliar;
    private LinkedList<JPanel> friendsJPanels;
    private LinkedList<JPanel> requestsJPanels;
    private LinkedList<JButton> friendsJButtons;
    private LinkedList<JButton> requestsJButtonsAccept;
    private LinkedList<JButton> requestsJButtonsDecline;
    private LinkedList<Friend> friendsInviteAuxiliar;
    private LinkedList<JPanel> friendsInviteJPanels;
    private LinkedList<JButton> friendsInviteJButtons;
    private JButton[] botonesJDialog;

    public MenuView() throws IOException, FontFormatException {

        BoxLayout bl = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
        getContentPane().setLayout(bl);

        setVisible(true);
        setTitle("Age Royale");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        background = new Background();
        background.setBackground("resources\\fondo_menu_1_2.png");
        background.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/4, (int)screenSize.getHeight()));
        background.setLayout(new GridLayout(1, 2));
        add(background);

        JPanel jpIzquierda = new JPanel();
        jpIzquierda.setOpaque(false);
        background.add(jpIzquierda);
        JPanel jpCentro = new JPanel();
        jpCentro.setLayout(new BoxLayout(jpCentro, BoxLayout.Y_AXIS));
        jpCentro.setOpaque(false);
        background.add(jpCentro);

        JLabel jlAuxCentroNorth = new JLabel();
        jlAuxCentroNorth.setMaximumSize(new Dimension((int)screenSize.getWidth() * 2/5, (int)screenSize.getHeight() * 1/20));

        JPanel jpAuxLogo = new JPanel();
        jpAuxLogo.setOpaque(false);
        jpAuxLogo.setLayout(new BoxLayout(jpAuxLogo, BoxLayout.X_AXIS));
        jpAuxLogo.setMaximumSize(new Dimension((int)screenSize.getWidth() * 2/5, (int)screenSize.getHeight() * 1/5));

        Background logo = new Background();
        logo.setBackground("resources\\ageroyalelogo.png");
        logo.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/4, (int)screenSize.getHeight() * 1/5));

        JLabel jlAuxLogo = new JLabel();
        jlAuxLogo.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/13, (int)screenSize.getHeight() * 1/5));

        jpAuxLogo.add(logo);
        jpAuxLogo.add(jlAuxLogo);

        JLabel jlAuxCentroSouth = new JLabel();
        jlAuxCentroSouth.setMaximumSize(new Dimension((int)screenSize.getWidth() * 2/5, (int)screenSize.getHeight() * 3/5));

        jpCentro.add(jlAuxCentroNorth);
        jpCentro.add(jpAuxLogo);
        jpCentro.add(jlAuxCentroSouth);

        JPanel jpIzqAuxCentro = new JPanel();
        jpIzqAuxCentro.setOpaque(false);
        jpIzqAuxCentro.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 3/5));
        jpIzquierda.add(jpIzqAuxCentro);

        JLabel jlIzqAuxCentroNorth = new JLabel();
        jlIzqAuxCentroNorth.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/10));
        jlIzqAuxCentroNorth.setOpaque(false);
        jpIzqAuxCentro.add(jlIzqAuxCentroNorth);


        ///////////////////////////// OPCION BUSCAR PARTIDA ///////////////////////////////

        jpPartidas = new JPanel();
        jpPartidas.setBorder(BorderFactory.createMatteBorder(5, 5, 15, 5, Color.DARK_GRAY));
        jpPartidas.setVisible(false);
        jpPartidas.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/2));
        jpPartidas.setLayout(new BoxLayout(jpPartidas, BoxLayout.Y_AXIS));
        jpIzqAuxCentro.add(jpPartidas);

        JPanel jpJlPartidas = new JPanel();
        jpJlPartidas.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/12));
        jpPartidas.add(jpJlPartidas);

        JLabel jlPartidas = new JLabel("· Partidas disponibles ·");
        jlPartidas.setOpaque(false);
        jlPartidas.setHorizontalAlignment(JLabel.CENTER);
        Font fPartidasLabel = new Font("Supercell-Magic", Font.BOLD,19);
        jlPartidas.setFont(fPartidasLabel);
        jlPartidas.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/12));
        jpJlPartidas.add(jlPartidas);

        JPanel jpAtrasActualizar = new JPanel();
        jpPartidas.add(jpAtrasActualizar);

        Font fAuxiliarButtons = new Font("Supercell-Magic", Font.PLAIN,20);
        jbAtrasBuscarPartida = new JButton("Atrás");
        jbAtrasBuscarPartida.setFont(fAuxiliarButtons);
        jbAtrasBuscarPartida.setBackground(Color.DARK_GRAY);
        jbAtrasBuscarPartida.setForeground(Color.WHITE);
        jpAtrasActualizar.add(jbAtrasBuscarPartida);
        JLabel jlAtrasActualizarAux = new JLabel();
        jlAtrasActualizarAux.setPreferredSize(new Dimension((int)screenSize.getWidth() * 2/20, (int)screenSize.getHeight() * 1/12));
        jpAtrasActualizar.add(jlAtrasActualizarAux);

        jspPartidas = new JScrollPane();
        jspPartidas.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 5/12));

        jpPartidas.add(jspPartidas);


        ///////////////////////////// OPCION INVITAR AMIGOS PARTIDA PRIVADA ///////////////////////////////

        jpInvitarAmigos = new JPanel();
        jpInvitarAmigos.setBorder(BorderFactory.createMatteBorder(5, 5, 15, 5, Color.DARK_GRAY));
        jpInvitarAmigos.setVisible(false);
        jpInvitarAmigos.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/2));
        jpInvitarAmigos.setLayout(new BoxLayout(jpInvitarAmigos, BoxLayout.Y_AXIS));
        jpIzqAuxCentro.add(jpInvitarAmigos);

        JPanel jpJlInvitarAmigos = new JPanel();
        jpJlInvitarAmigos.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/12));
        jpInvitarAmigos.add(jpJlInvitarAmigos);

        JLabel jlInvitarAmigos = new JLabel("· Invitar amigo ·");
        jlInvitarAmigos.setOpaque(false);
        jlInvitarAmigos.setHorizontalAlignment(JLabel.CENTER);
        jlInvitarAmigos.setFont(fPartidasLabel);
        jlInvitarAmigos.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/12));
        jpJlInvitarAmigos.add(jlInvitarAmigos);

        JPanel jpAtrasInvitar = new JPanel();
        jpInvitarAmigos.add(jpAtrasInvitar);

        jbAtrasInvitarAmigos = new JButton("Atrás");
        jbAtrasInvitarAmigos.setActionCommand("AtrasInvitarAmigos");
        jbAtrasInvitarAmigos.setFont(fAuxiliarButtons);
        jbAtrasInvitarAmigos.setBackground(Color.DARK_GRAY);
        jbAtrasInvitarAmigos.setForeground(Color.WHITE);
        jpAtrasInvitar.add(jbAtrasInvitarAmigos);
        JLabel jlAtrasInvitarAux = new JLabel();
        jlAtrasInvitarAux.setPreferredSize(new Dimension((int)screenSize.getWidth() * 2/20, (int)screenSize.getHeight() * 1/12));
        jpAtrasInvitar.add(jlAtrasInvitarAux);

        jspInvitarAmigos = new JScrollPane();
        jspInvitarAmigos.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 5/12));

        jpInvitarAmigos.add(jspInvitarAmigos);

        ///////////////////////////// OPCION CREAR PARTIDA ///////////////////////////////

        jpPrivadaPublica = new JPanel();
        jpPrivadaPublica.setBorder(BorderFactory.createMatteBorder(5, 5, 15, 5, Color.DARK_GRAY));
        jpPrivadaPublica.setVisible(false);
        jpPrivadaPublica.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/2));
        jpPrivadaPublica.setLayout(new BoxLayout(jpPrivadaPublica, BoxLayout.Y_AXIS));
        jpIzqAuxCentro.add(jpPrivadaPublica);

        jbAtrasCrearPartida = new JButton("Atrás");
        jbAtrasCrearPartida.setFont(fAuxiliarButtons);
        jbAtrasCrearPartida.setBackground(Color.DARK_GRAY);
        jbAtrasCrearPartida.setForeground(Color.WHITE);
        jpAtrasActualizar.add(jbAtrasCrearPartida);
        JLabel jlAtrasCrearAux = new JLabel();
        jlAtrasCrearAux.setOpaque(false);
        jlAtrasCrearAux.setPreferredSize(new Dimension((int)screenSize.getWidth() * 2/20, (int)screenSize.getHeight() * 1/12));
        jpAtrasActualizar.add(jlAtrasCrearAux);

        JPanel jpAtrasCrearAux = new JPanel();
        jpAtrasCrearAux.add(jbAtrasCrearPartida);
        jpAtrasCrearAux.setLayout(new BoxLayout(jpAtrasCrearAux, BoxLayout.X_AXIS));
        jpAtrasCrearAux.setOpaque(false);
        jpAtrasCrearAux.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/20, (int)screenSize.getHeight() * 1/10));
        jpAtrasCrearAux.add(jlAtrasCrearAux);
        jpPrivadaPublica.add(jpAtrasCrearAux);

        Font fPlayButtons = new Font("Supercell-Magic", Font.PLAIN,13);

        jbPrivada = new JButton("PRIVADA");
        jbPrivada.setBackground(Color.DARK_GRAY);
        jbPrivada.setForeground(Color.WHITE);
        jbPrivada.setFont(fPlayButtons);
        jbPrivada.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/16, (int)screenSize.getHeight() * 1/15));
        jbPublica = new JButton("PÚBLICA");
        jbPublica.setBackground(Color.DARK_GRAY);
        jbPublica.setForeground(Color.WHITE);
        jbPublica.setFont(fPlayButtons);
        jbPublica.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/16, (int)screenSize.getHeight() * 1/15));

        JPanel jpContenedorGameName = new JPanel();
        jpContenedorGameName.setLayout(new BoxLayout(jpContenedorGameName, BoxLayout.X_AXIS));
        jpContenedorGameName.setOpaque(false);
        jpContenedorGameName.setFont(fPlayButtons);
        jpContenedorGameName.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 2/15));

        JLabel jlAuxLeftContenedorGameName = new JLabel();
        jlAuxLeftContenedorGameName.setOpaque(false);
        jlAuxLeftContenedorGameName.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/32, (int)screenSize.getHeight() * 2/15));
        jpContenedorGameName.add(jlAuxLeftContenedorGameName);

        JPanel jpGameName = new JPanel();
        jpGameName.setLayout(new BoxLayout(jpGameName, BoxLayout.Y_AXIS));
        jpGameName.setOpaque(false);
        jpGameName.setFont(fPlayButtons);
        jpGameName.setMaximumSize(new Dimension((int)screenSize.getWidth() * 2/16, (int)screenSize.getHeight() * 2/15));
        jpContenedorGameName.add(jpGameName);

        Font fGameName = new Font("Supercell-Magic", Font.PLAIN,14);

        JLabel jlGameName = new JLabel("Nombre de la partida:");
        jlGameName.setHorizontalAlignment(SwingConstants.CENTER);
        jlGameName.setFont(fGameName);
        jlGameName.setOpaque(true);
        jlGameName.setForeground(Color.WHITE);
        jlGameName.setBackground(Color.DARK_GRAY);
        jlGameName.setMaximumSize(new Dimension((int)screenSize.getWidth() * 4/16, (int)screenSize.getHeight() * 1/15));
        jtfNombrePartida = new JTextField();
        jtfNombrePartida.setMaximumSize(new Dimension((int)screenSize.getWidth() * 4/16, (int)screenSize.getHeight() * 1/15));
        jpPrivadaPublica.add(jpContenedorGameName);
        jpGameName.add(jlGameName);
        jpGameName.add(jtfNombrePartida);

        JLabel jlAuxRightContenedorGameName = new JLabel();
        jlAuxRightContenedorGameName.setOpaque(false);
        jlAuxRightContenedorGameName.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/32, (int)screenSize.getHeight() * 2/15));
        jpContenedorGameName.add(jlAuxRightContenedorGameName);

        JPanel jpPrivacityOptions = new JPanel();
        jpPrivacityOptions.setLayout(new BoxLayout(jpPrivacityOptions, BoxLayout.X_AXIS));
        jpPrivacityOptions.setBackground(Color.DARK_GRAY);
        jpPrivacityOptions.setOpaque(false);
        jpPrivacityOptions.setFont(fPlayButtons);
        jpPrivacityOptions.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/15));

        JLabel jlAuxCenterPrivadaPublica2 = new JLabel();
        jlAuxCenterPrivadaPublica2.setOpaque(false);
        jlAuxCenterPrivadaPublica2.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/16, (int)screenSize.getHeight() * 1/15));
        jpPrivadaPublica.add(jlAuxCenterPrivadaPublica2);

        jpPrivadaPublica.add(jpPrivacityOptions);

        JLabel jlAuxCenterPrivadaPublica3 = new JLabel();
        jlAuxCenterPrivadaPublica3.setOpaque(false);
        jlAuxCenterPrivadaPublica3.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/16, (int)screenSize.getHeight() * 1/15));

        JLabel jlAuxCenterPrivadaPublica4 = new JLabel();
        jlAuxCenterPrivadaPublica4.setOpaque(false);
        jlAuxCenterPrivadaPublica4.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/16, (int)screenSize.getHeight() * 1/15));

        JLabel jlAuxCenterPrivadaPublica5 = new JLabel();
        jlAuxCenterPrivadaPublica5.setOpaque(false);
        jlAuxCenterPrivadaPublica5.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/16, (int)screenSize.getHeight() * 1/15));

        jpPrivacityOptions.add(jlAuxCenterPrivadaPublica3);
        jpPrivacityOptions.add(jbPublica);
        jpPrivacityOptions.add(jlAuxCenterPrivadaPublica4);
        jpPrivacityOptions.add(jbPrivada);
        jpPrivacityOptions.add(jlAuxCenterPrivadaPublica5);
        jpPrivadaPublica.add(jpPrivacityOptions);


        ///////////////////////////// OPCION ESPECTADOR ///////////////////////////////

        jpPartidasEspectador = new JPanel();
        jpPartidasEspectador.setBorder(BorderFactory.createMatteBorder(5, 5, 15, 5, Color.DARK_GRAY));
        jpPartidasEspectador.setVisible(false);
        jpPartidasEspectador.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/2));
        jpPartidasEspectador.setLayout(new BoxLayout(jpPartidasEspectador, BoxLayout.Y_AXIS));
        jpIzqAuxCentro.add(jpPartidasEspectador);

        JPanel jpJlPartidasEspectador = new JPanel();
        jpJlPartidasEspectador.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/12));
        jpPartidasEspectador.add(jpJlPartidasEspectador);

        JLabel jlPartidasEspectador = new JLabel("· Partidas disponibles ·");
        jlPartidasEspectador.setOpaque(false);
        jlPartidasEspectador.setHorizontalAlignment(JLabel.CENTER);
        jlPartidasEspectador.setFont(fPartidasLabel);
        jlPartidasEspectador.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/12));
        jpJlPartidasEspectador.add(jlPartidasEspectador);

        JPanel jpAtrasActualizarSpectator = new JPanel();
        jpPartidasEspectador.add(jpAtrasActualizarSpectator);

        jbAtrasSpectator = new JButton("Atrás");
        jbAtrasSpectator.setFont(fAuxiliarButtons);
        jbAtrasSpectator.setBackground(Color.DARK_GRAY);
        jbAtrasSpectator.setForeground(Color.WHITE);
        jpAtrasActualizarSpectator.add(jbAtrasSpectator);
        JLabel jlAtrasActualizarAuxSpectator = new JLabel();
        jlAtrasActualizarAuxSpectator.setPreferredSize(new Dimension((int)screenSize.getWidth() * 2/20, (int)screenSize.getHeight() * 1/12));
        jpAtrasActualizarSpectator.add(jlAtrasActualizarAuxSpectator);

        jspPartidasEspectador = new JScrollPane();
        jspPartidasEspectador.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 5/12));
        jpPartidasEspectador.add(jspPartidasEspectador);

        ///////////////////////////// PANTALLA ESPERA /////////////////////////////////////

        jpBuscandoRival = new JPanel();
        jpBuscandoRival.setBorder(BorderFactory.createMatteBorder(5, 5, 15, 5, Color.DARK_GRAY));
        jpBuscandoRival.setVisible(false);
        jpBuscandoRival.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/2));
        jpBuscandoRival.setLayout(new BoxLayout(jpBuscandoRival, BoxLayout.Y_AXIS));
        jpIzqAuxCentro.add(jpBuscandoRival);

        Font fBuscandoRival = new Font("Supercell-Magic", Font.PLAIN,22);
        JLabel jlBuscandoRival = new JLabel("Esperando rival...");
        jlBuscandoRival.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/4));
        jlBuscandoRival.setHorizontalAlignment(SwingConstants.CENTER);
        jlBuscandoRival.setFont(fBuscandoRival);
        jlBuscandoRival.setOpaque(true);
        jpBuscandoRival.add(jlBuscandoRival);

        ///////////////////////////// PANTALLA VICTORIA/DERROTA /////////////////////////////////////

        jpVictoriaDerrota = new JPanel();
        jpVictoriaDerrota.setBorder(BorderFactory.createMatteBorder(5, 5, 15, 5, Color.DARK_GRAY));
        jpVictoriaDerrota.setVisible(false);
        jpVictoriaDerrota.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/2));
        jpVictoriaDerrota.setLayout(new BoxLayout(jpVictoriaDerrota, BoxLayout.Y_AXIS));
        jpIzqAuxCentro.add(jpVictoriaDerrota);

        Font fVictoriaDerrota = new Font("Supercell-Magic", Font.PLAIN,22);
        jlVictoriaDerrota = new JLabel("");
        jlVictoriaDerrota.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/4));
        jlVictoriaDerrota.setHorizontalAlignment(SwingConstants.CENTER);
        jlVictoriaDerrota.setFont(fVictoriaDerrota);
        jlVictoriaDerrota.setOpaque(true);
        jpVictoriaDerrota.add(jlVictoriaDerrota);

        ///////////////////////////// 3 OPCIONES MENU PRINCIPAL ///////////////////////////////

        jpSelectGameType = new JPanel();
        jpSelectGameType.setBorder(BorderFactory.createMatteBorder(5, 5, 15, 5, Color.DARK_GRAY));
        jpSelectGameType.setLayout(new BoxLayout(jpSelectGameType, BoxLayout.X_AXIS));
        jpSelectGameType.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/2));
        jpIzqAuxCentro.add(jpSelectGameType);

        JLabel jlAuxPartidaIzq = new JLabel();
        jlAuxPartidaIzq.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/20, (int)screenSize.getHeight() * 1/2));
        jpSelectGameType.add(jlAuxPartidaIzq);

        JPanel jpBuscarPartidaCentre = new JPanel();
        jpBuscarPartidaCentre.setOpaque(false);
        jpBuscarPartidaCentre.setLayout(new BoxLayout(jpBuscarPartidaCentre, BoxLayout.Y_AXIS));
        jpSelectGameType.add(jpBuscarPartidaCentre);

        JLabel jlAuxPartidaDer = new JLabel();
        jlAuxPartidaDer.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/20, (int)screenSize.getHeight() * 1/2));
        jpSelectGameType.add(jlAuxPartidaDer);
        fPlayButtons = new Font("Supercell-Magic", Font.PLAIN,20);
        jbBuscarPartida = new JButton("BUSCAR PARTIDA");
        jbBuscarPartida.setBackground(Color.DARK_GRAY);
        jbBuscarPartida.setForeground(Color.WHITE);
        jbBuscarPartida.setFont(fPlayButtons);
        jbBuscarPartida.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/15));
        jbEspectar = new JButton("ESPECTAR PARTIDA");
        jbEspectar.setBackground(Color.DARK_GRAY);
        jbEspectar.setForeground(Color.WHITE);
        jbEspectar.setFont(fPlayButtons);
        jbEspectar.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/15));
        jbCrearPartida = new JButton("CREAR PARTIDA");
        jbCrearPartida.setFont(fPlayButtons);
        jbCrearPartida.setBackground(Color.DARK_GRAY);
        jbCrearPartida.setForeground(Color.WHITE);
        jbCrearPartida.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/15));
        jpBuscarPartidaCentre.add(jbBuscarPartida);
        JLabel jlAuxCenterGameType = new JLabel();
        jlAuxCenterGameType.setOpaque(false);
        jlAuxCenterGameType.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/16, (int)screenSize.getHeight() * 1/15));
        jpBuscarPartidaCentre.add(jlAuxCenterGameType);
        jpBuscarPartidaCentre.add(jbEspectar);
        JLabel jlAuxCenterGameType2 = new JLabel();
        jlAuxCenterGameType2.setOpaque(false);
        jlAuxCenterGameType2.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/16, (int)screenSize.getHeight() * 1/15));
        jpBuscarPartidaCentre.add(jlAuxCenterGameType2);
        jpBuscarPartidaCentre.add(jbCrearPartida);

        JPanel jpDerecha = new JPanel();
        jpDerecha.setBackground(Color.DARK_GRAY);
        add(jpDerecha);

        jpDerecha.setLayout(new BoxLayout(jpDerecha, BoxLayout.Y_AXIS));

        JPanel jpUser1 = new JPanel();
        jpUser1.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/4, (int)screenSize.getHeight() * 1/4));
        jpUser1.setBackground(Color.DARK_GRAY);
        jpDerecha.add(jpUser1);
        jpUser1.setLayout(new BoxLayout(jpUser1, BoxLayout.X_AXIS));

        JPanel jpUser2 = new JPanel();
        jpUser2.setOpaque(false);
        JLabel jlUserAuxIzq = new JLabel();
        jlUserAuxIzq.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/12, (int)screenSize.getHeight() * 1/4));
        jlUserAuxIzq.setOpaque(false);
        jpUser1.add(jlUserAuxIzq);
        jpUser1.add(jpUser2);
        jbSalir = new JButton("SALIR");
        fPlayButtons = new Font("Supercell-Magic", Font.PLAIN,11);
        jbSalir.setFont(fPlayButtons);
        jbSalir.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/20, (int)screenSize.getHeight() * 1/25));
        jbSalir.setBackground(Color.BLACK);
        jbSalir.setForeground(Color.WHITE);

        JLabel jlUserAuxAuxDerIzq = new JLabel();
        jlUserAuxAuxDerIzq.setOpaque(false);
        jlUserAuxAuxDerIzq.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/25, (int)screenSize.getHeight() * 1/25));
        JLabel jlUserAuxAuxDerDer = new JLabel();
        jlUserAuxAuxDerDer.setOpaque(false);
        jlUserAuxAuxDerDer.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/50, (int)screenSize.getHeight() * 1/50));

        JPanel jpUserAuxAuxDer = new JPanel();
        jpUserAuxAuxDer.setOpaque(false);
        jpUserAuxAuxDer.add(jlUserAuxAuxDerIzq);
        jpUserAuxAuxDer.add(jbSalir);
        jpUserAuxAuxDer.add(jlUserAuxAuxDerDer);

        JPanel jpUserAuxDer = new JPanel(); /////////////////////////////////////////////////////////////////////////////
        jpUserAuxDer.setOpaque(false);
        jpUserAuxDer.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/12, (int)screenSize.getHeight() * 1/4));
        jpUser1.add(jpUserAuxDer);
        jpUserAuxDer.add(jpUserAuxAuxDer); /////////////////////////////////////////////////////////////////////////////

        /*jlUserAuxIzq.setOpaque(true); ///
        jlUserAuxIzq.setBackground(Color.ORANGE);*/

        jpUser2.setLayout(new BoxLayout(jpUser2, BoxLayout.Y_AXIS));
        JLabel jlUserAuxNorth = new JLabel();
        jlUserAuxNorth.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/12, (int)screenSize.getHeight() * 1/24));
        jlUserAuxNorth.setOpaque(false);
        jpUser2.add(jlUserAuxNorth);

        JPanel jpProfilePictureContainer = new JPanel();
        jpProfilePictureContainer.setLayout(new BoxLayout(jpProfilePictureContainer, BoxLayout.X_AXIS));
        jpProfilePictureContainer.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/12, (int)screenSize.getHeight() * 1/8));
        jpProfilePictureContainer.setOpaque(false);
        jpUser2.add(jpProfilePictureContainer);

        JLabel jlProfilePictureLeft = new JLabel();
        jlProfilePictureLeft.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/20, (int)screenSize.getHeight() * 1/8));
        jlProfilePictureLeft.setOpaque(false);
        jpProfilePictureContainer.add(jlProfilePictureLeft);

        jpUserProfileImage = new Background();
        jpUserProfileImage.setBackground("resources\\calamardo_profile_picture.png");
        jpUserProfileImage.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/8, (int)screenSize.getHeight() * 1/8));
        jpProfilePictureContainer.add(jpUserProfileImage);

        JLabel jlProfilePictureRight = new JLabel();
        jlProfilePictureRight.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/20, (int)screenSize.getHeight() * 1/8));
        jlProfilePictureRight.setOpaque(false);
        jpProfilePictureContainer.add(jlProfilePictureRight);

        JLabel jlUserAuxMid = new JLabel();
        jlUserAuxMid.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/12, (int)screenSize.getHeight() * 1/48));
        jlUserAuxMid.setOpaque(false);
        jpUser2.add(jlUserAuxMid);

        userName = new JLabel();
        //userName.setMaximumSize(new Dimension((int)screenSize.getWidth() * 1/12, (int)screenSize.getHeight() * 1/6));
        Font fUserName = new Font("Supercell-Magic", Font.PLAIN,20);
        userName.setHorizontalAlignment(SwingConstants.CENTER);
        userName.setFont(fUserName);
        userName.setForeground(Color.WHITE);
        userName.setOpaque(false);

        JPanel jpUserName = new JPanel();
        jpUserName.add(userName);
        jpUserName.setOpaque(false);
        jpUser2.add(jpUserName);

        JLabel jlUserAuxSouth = new JLabel();
        jlUserAuxSouth.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/12, (int)screenSize.getHeight() * 1/24));
        jlUserAuxSouth.setOpaque(false);
        jpUser2.add(jlUserAuxSouth);

        jspFriends = new JScrollPane();
        jspFriends.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/13, (int)screenSize.getHeight() * 6/10));
        jspRequests = new JScrollPane();
        jspRequests.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/13, (int)screenSize.getHeight() * 6/10));

        jtpAmigos = new JTabbedPane();
        jtpAmigos.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/4, (int)screenSize.getHeight() * 3/4));
        jpListaAmigos = new JPanel();
        jpListaAmigos.setBackground(Color.LIGHT_GRAY);
        jpListaAmigos.add(jspFriends);
        jpSolicitudes = new JPanel();
        jpSolicitudes.setBackground(Color.LIGHT_GRAY);
        jpSolicitudes.add(jspRequests);
        Font fTabs = new Font("Supercell-Magic", Font.PLAIN,15);
        jtpAmigos.addTab("Lista de amigos", jpListaAmigos);
        jtpAmigos.addTab("Solicitudes de amistad", jpSolicitudes);
        jtpAmigos.setFont(fTabs);
        jtpAmigos.setBackground(Color.LIGHT_GRAY);

        Font fEnviar = new Font("Supercell-Magic", Font.PLAIN,11);
        JPanel jpEnviarSolicitud = new JPanel();
        jpEnviarSolicitud.setOpaque(false);
        jbEnviarSolicitud = new JButton("ENVIAR");
        jbEnviarSolicitud.setFont(fEnviar);
        jbEnviarSolicitud.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/20, (int)screenSize.getHeight() * 1/24));
        jtfAmigo = new JTextField();
        jtfAmigo.setPreferredSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/24));
        jpEnviarSolicitud.add(jtfAmigo);
        jpEnviarSolicitud.add(jbEnviarSolicitud);
        jpDerecha.add(jpEnviarSolicitud);
        jpDerecha.add(jtpAmigos);
        jpDerecha.setLayout(new BoxLayout(jpDerecha, BoxLayout.Y_AXIS));
    }

    public void showGameList() {

        jpPartidas.setVisible(true);
        jpSelectGameType.setVisible(false);
        jpPartidasEspectador.setVisible(false);
        jpPrivadaPublica.setVisible(false);
        jpBuscandoRival.setVisible(false);
        jpInvitarAmigos.setVisible(false);
        jpVictoriaDerrota.setVisible(false);
    }

    public void showSpectatorGameList() {

        jpPartidasEspectador.setVisible(true);
        jpSelectGameType.setVisible(false);
        jpPartidas.setVisible(false);
        jpPrivadaPublica.setVisible(false);
        jpBuscandoRival.setVisible(false);
        jpInvitarAmigos.setVisible(false);
        jpVictoriaDerrota.setVisible(false);
    }

    public void showCreateGameOptions() {

        jpPrivadaPublica.setVisible(true);
        jpPartidas.setVisible(false);
        jpPartidasEspectador.setVisible(false);
        jpSelectGameType.setVisible(false);
        jpBuscandoRival.setVisible(false);
        jpInvitarAmigos.setVisible(false);
        jpVictoriaDerrota.setVisible(false);
    }

    public void showWaitRoom() {

        jpBuscandoRival.setVisible(true);
        jpPartidas.setVisible(false);
        jpSelectGameType.setVisible(false);
        jpPartidasEspectador.setVisible(false);
        jpPrivadaPublica.setVisible(false);
        jpInvitarAmigos.setVisible(false);
        jpVictoriaDerrota.setVisible(false);
    }

    public void showPrivateGameMenu() {

        jpInvitarAmigos.setVisible(true);
        jpBuscandoRival.setVisible(false);
        jpPartidas.setVisible(false);
        jpSelectGameType.setVisible(false);
        jpPartidasEspectador.setVisible(false);
        jpPrivadaPublica.setVisible(false);
        jpVictoriaDerrota.setVisible(false);
    }

    public void showGameOptions() {

        jpSelectGameType.setVisible(true);
        jpPartidas.setVisible(false);
        jpPartidasEspectador.setVisible(false);
        jpPrivadaPublica.setVisible(false);
        jpBuscandoRival.setVisible(false);
        jpVictoriaDerrota.setVisible(false);
        jpInvitarAmigos.setVisible(false);
    }

    public void showVictoriaDerrotaMessage() {

        jpVictoriaDerrota.setVisible(true);
        jpSelectGameType.setVisible(false);
        jpPartidas.setVisible(false);
        jpPartidasEspectador.setVisible(false);
        jpPrivadaPublica.setVisible(false);
        jpBuscandoRival.setVisible(false);
        jpInvitarAmigos.setVisible(false);
    }

    public void menuController (MenuController controller) {

        userName.setText(controller.getMenuManager().getUser().getName());
        jbCrearPartida.addActionListener(controller);
        jbEspectar.addActionListener(controller);
        jbBuscarPartida.addActionListener(controller);
        jbAtrasBuscarPartida.addActionListener(controller);
        jbAtrasSpectator.addActionListener(controller);
        jbAtrasCrearPartida.addActionListener(controller);
        jbAtrasInvitarAmigos.addActionListener(controller);
        jbPrivada.addActionListener(controller);
        jbPublica.addActionListener(controller);
        jbEnviarSolicitud.addActionListener(controller);
        jbSalir.addActionListener(controller);
    }

    public void setBackground(String path) {

        background.setBackground(path);
    }

    public void showAvailableGames(LinkedList<Game> availableGames, MenuController controller) {

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        availableGamesJPanels = new LinkedList<>();
        availableGamesJButtons = new LinkedList<>();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        availableGamesAuxiliar = new LinkedList<>();

        for (int u = 0; u < availableGames.size(); u++) {

            if (!availableGames.get(u).isPrivada() && availableGames.get(u).getAvailable()) {

                availableGamesAuxiliar.add(availableGames.get(u));

                JPanel jPanel1 = new JPanel();
                JLabel jlUserNameGameName = new JLabel("Partida: " + availableGames.get(u).getGameName() + " · Creador: " + availableGames.get(u).getCreatorUser().getName());
                jlUserNameGameName.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/8, (int)screenSize.getHeight() * 1/22));
                jlUserNameGameName.setOpaque(false);
                jlUserNameGameName.setHorizontalAlignment(SwingConstants.LEFT);
                JButton jbUnirse = new JButton("UNIRSE");
                jlUserNameGameName.setForeground(Color.CYAN);
                jbUnirse.addActionListener(controller);
                jbUnirse.setActionCommand("unirse" + u);
                jPanel1.add(jlUserNameGameName);
                jPanel1.add(jbUnirse);
                jPanel1.setVisible(true);
                jPanel1.setOpaque(true);
                jPanel1.setBackground(Color.ORANGE);
                jPanel1.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/16, (int)screenSize.getHeight() * 1/22));

                availableGamesJButtons.add(jbUnirse);
                availableGamesJPanels.add(jPanel1);
                jPanel.add(jPanel1);
            }
        }

        jspPartidas.getViewport().add(jPanel);
        jspPartidas.setVisible(true);
        jspPartidas.getViewport().setVisible(true);
    }

    public void showSpectatorAvailableGames(LinkedList<Game> availableGames, MenuController controller) {

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        availableSpectatorGamesJPanels = new LinkedList<>();
        availableSpectatorGamesJButtons = new LinkedList<>();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        availableGamesSpectatorAuxiliar = new LinkedList<>();

        for (int u = 0; u < availableGames.size(); u++) {

            if (!availableGames.get(u).isPrivada() && !availableGames.get(u).getAvailable()) {

                availableGamesSpectatorAuxiliar.add(availableGames.get(u));

                JPanel jPanel1 = new JPanel();
                JLabel jlUserNameGameName = new JLabel("Partida: " + availableGames.get(u).getGameName() + " · Creador: " + availableGames.get(u).getCreatorUser().getName());
                jlUserNameGameName.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/8, (int)screenSize.getHeight() * 1/22));
                jlUserNameGameName.setOpaque(false);
                jlUserNameGameName.setHorizontalAlignment(SwingConstants.LEFT);
                jlUserNameGameName.setForeground(Color.CYAN);
                JButton jbEspectar = new JButton("ESPECTAR");
                jbEspectar.addActionListener(controller);
                jbEspectar.setActionCommand("espectar" + u);
                jPanel1.add(jlUserNameGameName);
                jPanel1.add(jbEspectar);
                jPanel1.setVisible(true);
                jPanel1.setOpaque(true);
                jPanel1.setBackground(Color.ORANGE);
                jPanel1.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/13, (int)screenSize.getHeight() * 1/22));

                availableSpectatorGamesJButtons.add(jbEspectar);
                availableSpectatorGamesJPanels.add(jPanel1);
                jPanel.add(jPanel1);
            }
        }

        jspPartidasEspectador.getViewport().add(jPanel);
        jspPartidasEspectador.setVisible(true);
        jspPartidasEspectador.getViewport().setVisible(true);
    }

    public void showFriends(LinkedList<Friend> friends, MenuController controller) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel jPanel = new JPanel();
        jPanel.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/13, (int)screenSize.getHeight() * 6/10));
        jPanel.setBackground(Color.DARK_GRAY);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        friendsJPanels = new LinkedList<>();
        friendsJButtons = new LinkedList<>();
        friendsAuxiliar = new LinkedList<>();

        int u;
        for (u = 0; u < friends.size(); u++) {

            if (friends.get(u).isConnected()) {

                friendsAuxiliar.add(friends.get(u));

                JPanel jPanel1 = new JPanel();
                JLabel jlUserName = new JLabel(friends.get(u).getName());
                jlUserName.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/10, (int)screenSize.getHeight() * 1/22));
                jlUserName.setOpaque(false);
                jlUserName.setHorizontalAlignment(SwingConstants.LEFT);
                JButton jbEliminar = new JButton("ELIMINAR");
                jlUserName.setForeground(Color.GREEN);
                jbEliminar.addActionListener(controller);
                jbEliminar.setActionCommand("eliminar" + u);
                jbEliminar.setForeground(Color.RED);
                jPanel1.add(jlUserName);
                jPanel1.add(jbEliminar);
                jPanel1.setVisible(true);
                jPanel1.setOpaque(true);
                jPanel1.setBackground(Color.GRAY);
                jPanel1.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/13, (int)screenSize.getHeight() * 1/22));

                friendsJButtons.add(jbEliminar);
                friendsJPanels.add(jPanel1);
                jPanel.add(jPanel1);
            }
        }

        u = friendsJPanels.size();
        for (int i = 0; i < friends.size(); i++) {

            if (!friends.get(i).isConnected()) {

                friendsAuxiliar.add(friends.get(i));

                JPanel jPanel1 = new JPanel();
                JLabel jlUserName = new JLabel(friends.get(i).getName());
                jlUserName.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/10, (int)screenSize.getHeight() * 1/22));
                jlUserName.setOpaque(false);
                jlUserName.setHorizontalAlignment(SwingConstants.LEFT);
                JButton jbEliminar = new JButton("ELIMINAR");
                jlUserName.setForeground(Color.DARK_GRAY);
                jbEliminar.addActionListener(controller);
                jbEliminar.setActionCommand("eliminar" + u);
                jbEliminar.setForeground(Color.RED);
                jPanel1.add(jlUserName);
                jPanel1.add(jbEliminar);
                jPanel1.setVisible(true);
                jPanel1.setOpaque(true);
                jPanel1.setBackground(Color.GRAY);
                jPanel1.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/13, (int)screenSize.getHeight() * 1/22));

                friendsJButtons.add(jbEliminar);
                friendsJPanels.add(jPanel1);
                jPanel.add(jPanel1);
                u++;
            }
        }

        jspFriends.getViewport().add(jPanel);
        jspFriends.setVisible(true);
        jspFriends.getViewport().setVisible(true);
    }

    public void showFriendRequests(LinkedList<Request> requests, MenuController controller) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel jPanel = new JPanel();
        jPanel.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/13, (int)screenSize.getHeight() * 6/10));
        jPanel.setBackground(Color.DARK_GRAY);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        requestsJPanels = new LinkedList<>();
        requestsJButtonsAccept = new LinkedList<>();
        requestsJButtonsDecline = new LinkedList<>();
        requestsAuxiliar = new LinkedList<>();

        int u;
        for (u = 0; u < requests.size(); u++) {

            requestsAuxiliar.add(requests.get(u));

            JPanel jPanel1 = new JPanel();
            JLabel jlUserName = new JLabel(requests.get(u).getFrom());
            jlUserName.setPreferredSize(new Dimension((int)screenSize.getWidth() * 1/13, (int)screenSize.getHeight() * 1/22));
            jlUserName.setOpaque(false);
            jlUserName.setHorizontalAlignment(SwingConstants.LEFT);
            JButton jbAceptar = new JButton("Aceptar");
            JButton jbDeclinar = new JButton("Declinar");
            jlUserName.setForeground(Color.WHITE);
            jbAceptar.addActionListener(controller);
            jbAceptar.setActionCommand("aceptar" + u);
            jbAceptar.setForeground(Color.BLUE);
            jbDeclinar.addActionListener(controller);
            jbDeclinar.setActionCommand("declinar" + u);
            jbDeclinar.setForeground(Color.RED);
            jPanel1.add(jlUserName);
            jPanel1.add(jbAceptar);
            jPanel1.add(jbDeclinar);
            jPanel1.setVisible(true);
            jPanel1.setOpaque(true);
            jPanel1.setBackground(Color.GRAY);
            jPanel1.setMaximumSize(new Dimension((int)screenSize.getWidth() * 3/13, (int)screenSize.getHeight() * 1/22));

            requestsJButtonsAccept.add(jbAceptar);
            requestsJButtonsDecline.add(jbDeclinar);
            requestsJPanels.add(jPanel1);
            jPanel.add(jPanel1);

        }

        jspRequests.getViewport().add(jPanel);
        jspRequests.setVisible(true);
        jspRequests.getViewport().setVisible(true);
    }

    public void showFriendsToInvite(LinkedList<Friend> friends, MenuController controller) {

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        friendsInviteJPanels = new LinkedList<>();
        friendsInviteJButtons = new LinkedList<>();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        friendsInviteAuxiliar = new LinkedList<>();

        int u;
        for (u = 0; u < friends.size(); u++) {

            if (friends.get(u).isConnected()) {

                friendsInviteAuxiliar.add(friends.get(u));

                JPanel jPanel1 = new JPanel();
                JLabel jlUserName = new JLabel(friends.get(u).getName());
                JButton jbInvitar = new JButton("INVITAR");
                jlUserName.setForeground(Color.GREEN);
                jbInvitar.addActionListener(controller);
                jbInvitar.setActionCommand("invitar" + u);
                jbInvitar.setForeground(Color.BLUE);
                jPanel1.add(jlUserName);
                jPanel1.add(jbInvitar);
                jPanel1.setVisible(true);
                jPanel1.setOpaque(true);
                jPanel1.setBackground(Color.GRAY);
                jPanel1.setMaximumSize(new Dimension((int)screenSize.getWidth() * 4/16, (int)screenSize.getHeight() * 1/22));

                friendsInviteJButtons.add(jbInvitar);
                friendsInviteJPanels.add(jPanel1);
                jPanel.add(jPanel1);
            }
        }

        jspInvitarAmigos.getViewport().add(jPanel);
        jspInvitarAmigos.setVisible(true);
        jspInvitarAmigos.getViewport().setVisible(true);
    }

    public void showInvitationMessage(MenuController controller, User user) {

        JButton botonAceptarPartida = new JButton("JUGAR");
        botonAceptarPartida.addActionListener(controller);
        JButton botonDenegarPartida = new JButton("DENEGAR");
        botonDenegarPartida.addActionListener(controller);
        botonesJDialog = new JButton[] {botonAceptarPartida, botonDenegarPartida};

        int option =JOptionPane.showOptionDialog(
                null,
                user.getName() + " te ha invitado a su partida",
                "Partida privada",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                botonesJDialog,
                null);

        if (option == JOptionPane.CLOSED_OPTION) {

            controller.cancelPrivateGame();
        }

    }

    public LinkedList<JPanel> getFriendsJPanels() {
        return friendsJPanels;
    }

    public LinkedList<Friend> getFriendsAuxiliar() {
        return friendsAuxiliar;
    }

    public LinkedList<JButton> getFriendsJButtons() {
        return friendsJButtons;
    }

    public LinkedList<JPanel> getRequestsJPanels() {
        return requestsJPanels;
    }

    public LinkedList<Request> getRequestsAuxiliar() {
        return requestsAuxiliar;
    }

    public LinkedList<JButton> getRequestsJButtonsAccept() {
        return requestsJButtonsAccept;
    }

    public LinkedList<JButton> getRequestsJButtonsDecline() {
        return requestsJButtonsDecline;
    }

    public LinkedList<JPanel> getAvailableGamesJPanels() {
        return availableGamesJPanels;
    }

    public LinkedList<Game> getAvailableGamesAuxiliar() {
        return availableGamesAuxiliar;
    }

    public LinkedList<JButton> getAvailableGamesJButtons() {
        return availableGamesJButtons;
    }

    public LinkedList<JButton> getAvailableSpectatorGamesJButtons() {
        return availableSpectatorGamesJButtons;
    }

    public LinkedList<JPanel> getAvailableSpectatorGamesJPanels() {
        return availableSpectatorGamesJPanels;
    }

    public LinkedList<Game> getAvailableGamesSpectatorAuxiliar() {
        return availableGamesSpectatorAuxiliar;
    }

    public JTextField getJtfNombrePartida() {
        return jtfNombrePartida;
    }

    public JTextField getJtfAmigo() {
        return jtfAmigo;
    }

    public LinkedList<Friend> getFriendsInviteAuxiliar() {
        return friendsInviteAuxiliar;
    }

    public LinkedList<JButton> getFriendsInviteJButtons() {
        return friendsInviteJButtons;
    }

    public LinkedList<JPanel> getFriendsInviteJPanels() {
        return friendsInviteJPanels;
    }

    public void setJtfAmigoText(String requestMessage) {
        this.jtfAmigo.setText(requestMessage);
    }

    public void setTextJlVictoriaDerrota(String jlVictoriaDerrotaText) {
        this.jlVictoriaDerrota.setText(jlVictoriaDerrotaText);
    }

    public void setColorJlVictoriaDerrota(Color jlVictoriaDerrotaColor) {
        this.jlVictoriaDerrota.setForeground(jlVictoriaDerrotaColor);
    }
}
