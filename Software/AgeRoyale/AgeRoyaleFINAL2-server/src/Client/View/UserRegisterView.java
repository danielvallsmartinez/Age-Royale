package Client.View;

import Client.Controller.UserRegisterController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class UserRegisterView extends JFrame {

    private JLabel jlTitle;
    private JLabel jlName;
    private JLabel jlPassword;
    private JLabel jlConfirmation;
    private JLabel jlMail;
    private JLabel jlErrorMessage;
    private JTextField jtfName;
    private JTextField jtfMail;
    private JButton jbHaveAccount;
    private JButton jbRegister;
    private JButton jbDontHaveAccount;
    private JButton jbLogin;
    private JPanel jpGridLogin;
    private JPanel jpGridRegister;
    private JPasswordField jpfPassword;
    private JPasswordField jpfConfirmation;


    public UserRegisterView() throws IOException, FontFormatException {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources\\Supercell-Magic_5.ttf")));

        Background background = new Background();
        background.setBackground("resources\\FonsAge.jpg");
        add(background);

        BoxLayout bl = new BoxLayout(background, BoxLayout.X_AXIS);
        background.setLayout(bl);

        setVisible(true);
        setTitle("Age Royale");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //getContentPane().setBackground(Color.LIGHT_GRAY);
        setResizable(true);




        JPanel jp2 = new JPanel();
        jp2.setOpaque(false);
        Dimension dimension_lateral = new Dimension((int) (screenSize.width*0.3), screenSize.height);
        jp2.setMinimumSize(dimension_lateral);
        background.add(jp2);
        //----------------
        JPanel jp1 = new JPanel();

        //jp1.setSize(60, 60);
        jp1.setBackground(Color.BLUE);
        jp1.setOpaque(false);


        BoxLayout bl1 = new BoxLayout(jp1, BoxLayout.Y_AXIS);
        jp1.setLayout(bl1);

        JLabel jltop = new JLabel();
        jltop.setOpaque(false);
        Dimension dimension_label = new Dimension((int) (screenSize.width*0.3), (int) (screenSize.height*0.2));
        jltop.setPreferredSize(dimension_label);
        jp1.add(jltop);


        //-----------------
        JPanel jpcenter = new JPanel();
        jp1.add(jpcenter);
        jpcenter.setLayout(new BoxLayout(jpcenter, BoxLayout.Y_AXIS));


        JLabel jltoptitle = new JLabel();
        Dimension dimension_subtitle = new Dimension((int) (screenSize.width*0.01), (int) (screenSize.height*0.1));
        jltoptitle.setPreferredSize(dimension_subtitle);
        jpcenter.add(jltoptitle);
        jltoptitle.setHorizontalAlignment(JLabel.CENTER);



        jlTitle = new JLabel();
        jlTitle.setHorizontalAlignment(JLabel.CENTER);
        Font font = new Font("Supercell-Magic", Font.BOLD,20);
        jlTitle.setFont(font);
        jlTitle.setPreferredSize(dimension_subtitle);
        jlTitle.setForeground(Color.CYAN);

        JPanel jptitle = new JPanel();
        GridLayout gltitle = new GridLayout(1, 3);
        jptitle.setOpaque(false);
        jptitle.setLayout(gltitle);
        jptitle.add(jlTitle);
        jpcenter.add(jptitle);
        JPanel jperrormessage = new JPanel();
        BoxLayout blerrormessage = new BoxLayout(jperrormessage, BoxLayout.X_AXIS);
        jperrormessage.setLayout(blerrormessage);
        jperrormessage.setOpaque(false);
        jlErrorMessage = new JLabel();
        jlErrorMessage.setForeground(Color.red);
        jlErrorMessage.setVisible(false);
        jlErrorMessage.setHorizontalAlignment(JLabel.CENTER);
        jperrormessage.add(jlErrorMessage);
        jpcenter.add(jperrormessage);

        JPanel jpgrid_1 = new JPanel();
        jpgrid_1.setLayout(new BoxLayout(jpgrid_1, BoxLayout.X_AXIS));
        jpgrid_1.setOpaque(false);

        JLabel jllateral = new JLabel();
        JLabel jllateral1 = new JLabel();
        Dimension dimension_label_lateral = new Dimension(150, (int) (screenSize.height*0.4));
        jllateral.setPreferredSize(dimension_label_lateral);
        jllateral.setOpaque(false);
        jllateral1.setPreferredSize(dimension_label_lateral);
        jllateral.setOpaque(false);


        JPanel jpgrid = new JPanel();
        jpgrid.setOpaque(false);
        GridLayout gl = new GridLayout(4,2);
        gl.setVgap(20);
        gl.setHgap(30);
        jpgrid.setLayout(gl);
        jpcenter.setOpaque(false);


        Font grid_font = new Font("Supercell-Magic", Font.PLAIN,15);
        jlName = new JLabel("Nombre de usuario: ");
        jlName.setForeground(Color.CYAN);
        jlName.setFont(grid_font);
        jlName.setHorizontalAlignment(JLabel.LEFT);
        jlPassword = new JLabel("Contraseña: ");
        jlPassword.setForeground(Color.CYAN);
        jlPassword.setFont(grid_font);
        jlPassword.setHorizontalAlignment(JLabel.LEFT);
        jpfPassword = new JPasswordField();
        jpfPassword.setEchoChar('*');
        jlConfirmation = new JLabel("Repite la contraseña: ");
        jlConfirmation.setForeground(Color.CYAN);
        jlConfirmation.setFont(grid_font);
        jlConfirmation.setHorizontalAlignment(JLabel.LEFT);
        jpfConfirmation = new JPasswordField();
        jpfConfirmation.setEchoChar('*');


        jlMail = new JLabel("Correo electrónico: ");
        jlMail.setForeground(Color.CYAN);
        jlMail.setFont(grid_font);
        jlMail.setHorizontalAlignment(JLabel.LEFT);

        jtfName = new JTextField();
        jtfMail = new JTextField();


        jpgrid.add(jlMail);
        jpgrid.add(jtfMail);
        jpgrid.add(jlName);
        jpgrid.add(jtfName);
        jpgrid.add(jlPassword);
        jpgrid.add(jpfPassword);
        jpgrid.add(jlConfirmation);
        jpgrid.add(jpfConfirmation);


        jpgrid_1.add(jllateral1);
        jpgrid_1.add(jpgrid);
        jpgrid_1.add(jllateral);



        //-----------------
        JLabel jlbottom = new JLabel();
        jltop.setOpaque(false);
        jp1.add(jlbottom);
        jlbottom.setPreferredSize(dimension_label);

        jpGridRegister = new JPanel();
        jpGridRegister.setOpaque(false);
        GridLayout glbottom = new GridLayout(1, 3);
        jpGridRegister.setLayout(glbottom);
        jbHaveAccount = new JButton("Ya tengo cuenta");
        jbHaveAccount.setBorderPainted(false);
        jbHaveAccount.setContentAreaFilled(false);
        jbRegister = new JButton("Crear");
        Font fHaveAccount = new Font("Supercell-Magic", Font.PLAIN,10);
        jbHaveAccount.setFont(fHaveAccount);
        jbHaveAccount.setHorizontalAlignment(JLabel.CENTER);
        jbHaveAccount.setForeground(Color.BLUE);
        JLabel jl3column = new JLabel();
        jbRegister.setForeground(Color.CYAN);
        jbRegister.setBackground(Color.orange);
        jbRegister.setFont(font);

        jpGridRegister.add(jbHaveAccount);
        jpGridRegister.add(jbRegister);
        jpGridRegister.add(jl3column);

        jpcenter.add(jpgrid_1);

        jpGridLogin = new JPanel();
        jpGridLogin.setOpaque(false);
        GridLayout gdLogin = new GridLayout(1, 3);
        jpGridLogin.setLayout(gdLogin);
        jbDontHaveAccount = new JButton("No tengo cuenta");
        jbDontHaveAccount.setBorderPainted(false);
        jbDontHaveAccount.setContentAreaFilled(false);
        jbDontHaveAccount.setFont(fHaveAccount);
        jbDontHaveAccount.setHorizontalAlignment(JLabel.CENTER);
        jbDontHaveAccount.setForeground(Color.BLUE);
        jbLogin = new JButton("Iniciar");
        jbLogin.setForeground(Color.CYAN);
        jbLogin.setBackground(Color.orange);
        jbLogin.setFont(font);
        jpGridLogin.add(jbDontHaveAccount);
        jpGridLogin.add(jbLogin);
        jpGridLogin.add(jl3column = new JLabel());


        jpcenter.add(jpGridLogin);
        jpcenter.add(jpGridRegister);


        background.add(jp1);
        //----------------
        JPanel jp3 = new JPanel();
        jp3.setOpaque(false);
        jp3.setMinimumSize(dimension_lateral);
        background.add(jp3);

        showLogin();
    }

    public void showLogin() {
        jlTitle.setText("Inicia sesión");
        jlMail.setVisible(false);
        jtfMail.setVisible(false);
        jlConfirmation.setVisible(false);
        jpfConfirmation.setVisible(false);
        jpGridRegister.setVisible(false);
        jpGridLogin.setVisible(true);
        jbDontHaveAccount.setVisible(true);
        jbLogin.setVisible(true);
        jlErrorMessage.setVisible(false);
    }

    public void showRegister() {

        jlTitle.setText("Regístrate");
        jpGridRegister.setVisible(true);
        jlMail.setVisible(true);
        jtfMail.setVisible(true);
        jlConfirmation.setVisible(true);
        jpfConfirmation.setVisible(true);
        jbDontHaveAccount.setVisible(false);
        jbLogin.setVisible(false);
        jlErrorMessage.setVisible(false);
    }

    public void showRegisterSuccesMessage() {

        jlErrorMessage.setText("Cuenta creada con éxito");
        jlErrorMessage.setBackground(Color.GREEN);
        jlErrorMessage.setVisible(true);
    }

    public void showLoginSuccesMessage() {

        jlErrorMessage.setText("Sesión iniciada con éxito");
        jlErrorMessage.setBackground(Color.GREEN);
        jlErrorMessage.setVisible(true);
    }

    public void showRegisterErrorMailMessage() {

        jlErrorMessage.setText("Este mail ya está asociado a un usuario");
        jlErrorMessage.setVisible(true);
    }

    public void showRegisterErrorNameMessage() {

        jlErrorMessage.setText("Nombre de usuario no disponible");
        jlErrorMessage.setVisible(true);
    }

    public void showRegisterErrorPasswordMessage() {

        jlErrorMessage.setText("Las contraseñas no coinciden");
        jlErrorMessage.setVisible(true);
    }

    public void showLoginErrorMessage() {

        jlErrorMessage.setText("Usuario y/o contraseña incorrectos");
        jlErrorMessage.setVisible(true);
    }

    public void showLoginErrorMessage2() {
        jlErrorMessage.setText("Este usuario ya está en linea");
        jlErrorMessage.setVisible(true);
    }

    public void userRegisterController(UserRegisterController controller) {
        jbHaveAccount.addActionListener(controller);
        jbDontHaveAccount.addActionListener(controller);
        jbLogin.addActionListener(controller);
        jbRegister.addActionListener(controller);
    }

    public void showRegisterErrorPasswordFormatMessage() {

        jlErrorMessage.setText("Formato de la contraseña incorrecto, debe contener como minimo 1 Mayuscula, 1 Minuscula, valores numericos y 8 caracteres");
        jlErrorMessage.setVisible(true);
    }

    public void showRegisterErrorMailFormatMessage() {

        jlErrorMessage.setText("Formato del mail incorrecto, sigue el formato X@y.z");
        jlErrorMessage.setVisible(true);
    }

    public JTextField getJtfName() {
        return jtfName;
    }

    public JTextField getJtfMail() {
        return jtfMail;
    }

    public JPasswordField getJpfPassword() {
        return jpfPassword;
    }

    public JPasswordField getJpfConfirmation() {
        return jpfConfirmation;
    }

    public void showLoginErrorMessage3() {
        jlErrorMessage.setText("Este usuario esta baneado");
        jlErrorMessage.setVisible(true);
    }
}