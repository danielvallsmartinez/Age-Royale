package Client;

import Client.Controller.UserRegisterController;
import Client.Model.UserManager;
import Client.View.UserRegisterView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainClient {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserRegisterView userRegisterView = null;
                try {
                    userRegisterView = new UserRegisterView();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FontFormatException e) {
                    e.printStackTrace();
                }
                UserManager userManager = null;
                try {
                    userManager = new UserManager();
                    userManager.getServer().setUserManager(userManager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UserRegisterController userRegisterController = new UserRegisterController(userRegisterView, userManager);
                assert userRegisterView != null;
                userRegisterView.userRegisterController(userRegisterController);
                assert userManager != null;
                userManager.setUserRegisterController(userRegisterController);




                /*
                MenuView menuView = new MenuView();
                //MenuController menuController = new MenuController(userManager, menuView);
                //UserManager userManager = null;
                try {
                    userManager = new UserManager();
                    userManager.setUser(new User(0, "Abel", "thtxfyhx", "hfthxh"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //MenuManager menuManager = null;
                MenuManager menuManager = new MenuManager(userManager.getUser(), userManager.getServer());
                menuManager.setMenuManagerOfServer(menuManager);
                MenuController menuController = new MenuController(menuView, menuManager);
                menuView.menuController(menuController);*/
            }
        });
    }
}
