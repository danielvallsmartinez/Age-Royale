package Client.Model;

import Client.Controller.MenuController;

public class ChangeBackground extends Thread {

    private boolean go;
    private MenuController menuController;
    private String path;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @Override
    public void run() {

        go = true;
        path = "resources/fondo_menu_1_2.png";
        int imagen = 0;

        while(go) {

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            if (imagen == 0) {
                path = "resources/fondo_menu_6.png";
                imagen = 1;
            }
            else {
                if (imagen == 1) {

                    path = "resources/fondo_menu_7.png";
                    imagen = 2;
                }
                else {
                    if (imagen == 2) {

                        path = "resources/fondo_menu_1_2.png";
                        imagen = 0;
                    }
                }
            }

            menuController.changeBackground(path);
        }
    }
}
