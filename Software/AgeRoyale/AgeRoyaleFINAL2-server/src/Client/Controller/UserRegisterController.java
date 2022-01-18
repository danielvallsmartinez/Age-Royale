package Client.Controller;

import Client.Model.MenuManager;
import Client.Model.UserManager;
import Client.View.MenuView;
import Client.View.UserRegisterView;
import Shared.Entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UserRegisterController implements ActionListener {

    private UserRegisterView userRegisterView;
    private UserManager userManager;
    private int userRegisterResponse;
    private int userLoginResponse;

    public UserRegisterController(UserRegisterView userRegisterView, UserManager userManager) {
        this.userRegisterView = userRegisterView;
        this.userManager = userManager;
        userRegisterResponse = -1;
        userLoginResponse = -1;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton button = (JButton) actionEvent.getSource();
        User user;

        if (button.getText().equals("No tengo cuenta")) {
            userRegisterView.showRegister();
        }
        if (button.getText().equals("Iniciar")) {
            user = new User(userRegisterView.getJtfName().getText(), userRegisterView.getJpfPassword().getText());
            userManager.setUser(user);
            userManager.setUserOption(0);
            try {

                userManager.userLogin();

                while(userLoginResponse == -1)
                {
                    Thread.sleep(10);
                }

                if (userLoginResponse == 1) {
                    userRegisterView.showLoginErrorMessage();
                } else {
                    if (userLoginResponse == 4) {
                        userRegisterView.showLoginErrorMessage2();
                    } else {
                        if (userLoginResponse == 5) {
                            userRegisterView.showLoginErrorMessage3();
                        }
                        else {
                            userRegisterView.showLoginSuccesMessage();
                            userRegisterView.setVisible(false);
                            userRegisterView.dispose();

                            MenuView menuView = new MenuView();
                            MenuManager menuManager = new MenuManager(userManager.getUser(), userManager.getServer());
                            menuManager.setMenuManagerOfServer(menuManager);
                            MenuController menuController = new MenuController(menuView, menuManager);  //Creamos un controlador para el menú después de cerrar la ventana de login/registro
                            menuView.menuController(menuController);
                            menuManager.setMenuController(menuController);
                            userManager.sendReadyToStartMenu();
                        }
                    }
                }
            } catch (IOException | FontFormatException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (button.getText().equals("Ya tengo cuenta")) {
            userRegisterView.showLogin();
        }
        if (button.getText().equals("Crear")) {

            if(!userManager.checkPassword(userRegisterView.getJpfPassword().getText())){

                userRegisterView.showRegisterErrorPasswordFormatMessage();

            }
            else {
                if (!userRegisterView.getJpfConfirmation().getText().equals(userRegisterView.getJpfPassword().getText())) {
                    userRegisterView.showRegisterErrorPasswordMessage();
                }
                else {
                    if (!userManager.checkEmail(userRegisterView.getJtfMail().getText())) {
                        userRegisterView.showRegisterErrorMailFormatMessage();
                    }
                    else {
                        user = new User(0, userRegisterView.getJtfName().getText(), userRegisterView.getJtfMail().getText(), userRegisterView.getJpfPassword().getText());
                        userManager.setUser(user);
                        userManager.setUserOption(1);
                        try {

                            userManager.userRegister();

                            while (userRegisterResponse == -1) {
                                Thread.sleep(10);
                            }

                            if (userRegisterResponse == 0) {
                                userRegisterView.showRegisterSuccesMessage();
                                userRegisterView.setVisible(false);
                                userRegisterView.dispose();

                                MenuView menuView = new MenuView();
                                MenuManager menuManager = new MenuManager(userManager.getUser(), userManager.getServer());
                                menuManager.setMenuManagerOfServer(menuManager);
                                MenuController menuController = new MenuController(menuView, menuManager);  //Creamos un controlador para el menú después de cerrar la ventana de login/registro
                                menuView.menuController(menuController);
                                menuManager.setMenuController(menuController);
                                userManager.sendReadyToStartMenu();
                            }

                            if (userRegisterResponse == 1) {
                                userRegisterView.showRegisterErrorNameMessage();
                            }

                            if (userRegisterResponse == 2) {
                                userRegisterView.showRegisterErrorMailMessage();
                            }
                        } catch (IOException | FontFormatException | ClassNotFoundException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void setUserLoginResponse(int userLoginResponse) {
        this.userLoginResponse = userLoginResponse;
    }

    public void setUserRegisterResponse(int userRegisterResponse) {
        this.userRegisterResponse = userRegisterResponse;
    }
}
