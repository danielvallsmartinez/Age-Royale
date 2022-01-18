package Client.Model;

import Client.Controller.UserRegisterController;
import Client.Network.Server;
import Shared.Entity.User;
import Shared.Entity.UserSend;

import java.io.IOException;

public class UserManager {

    private User user;
    private UserRegisterController userRegisterController;
    private Server server;
    private int userOption;

    public UserManager() throws IOException {
        server = new Server();
    }

    public void setUserRegisterController(UserRegisterController userRegisterController) {
        this.userRegisterController = userRegisterController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserOption(int userOption) {
        this.userOption = userOption;
    }

    public User getUser() {
        return user;
    }

    public void userLogin() throws IOException, ClassNotFoundException {
        UserSend userSend = new UserSend(getUser(), userOption);
        if(!server.isAlive()) {
            server.start();
        }
        server.setWantLogin(true);
        server.sendUser(userSend);
    }

    public void userRegister() throws IOException, ClassNotFoundException {
        UserSend userSend = new UserSend(getUser(), userOption);
        if(!server.isAlive()) {
            server.start();
        }
        server.setWantLogin(false);
        server.sendUser(userSend);
    }

    public void setUserRegisterResponse(int userRegisterResponse) {
        userRegisterController.setUserRegisterResponse(userRegisterResponse);
    }

    public void setUserLoginResponse(int userLoginResponse) {

        userRegisterController.setUserLoginResponse(userLoginResponse);
    }

    public Server getServer() {
        return server;
    }

    public void sendReadyToStartMenu() throws IOException {
        server.setLogged(true);
        server.sendReadyToStartMenu();
    }

    public boolean checkPassword(String password){

        int length = 0;
        int numCount = 0;
        int mayCount = 0;
        int minCount = 0;

        for (int i =0; i < password.length(); i++) {

            if ((password.charAt(i) > 47 && password.charAt(i) < 58)) {
                numCount ++;
            }

            if ((password.charAt(i) > 64 && password.charAt(i) < 91)) {
                mayCount ++;
            }

            if ((password.charAt(i) > 96 && password.charAt(i) < 123)) {
                minCount ++;
            }

            length = (i + 1);

        }

        if (numCount < 1){
            return false;
        }

        if (mayCount < 1) {
            return  false;
        }

        if (minCount < 1) {
            return  false;
        }

        if (length < 8){
            return false;
        }

        return true;

    }

    public boolean checkEmail(String email){

        int atCheck = 0;
        int pointCheck  = 0;

        for(int i = 0; i < email.length(); i++){

            if(email.charAt(0) < 65 && email.charAt(0) > 90 ){
                return false;
            }

            if(email.charAt(i) == '@'){

                atCheck = 1;

                if ( i == 0){
                    return false;
                }

                if ( i == email.length()-1){
                    return false;
                }

                if (email.charAt(i+1) == '.'){
                    return false;
                }

            }

            if(email.charAt(i) == '.'){

                pointCheck = 1;

                if ( i == 0){
                    return false;
                }

                if(i == email.length()-1){
                    return false;
                }

                if(email.charAt(i-1) == '@'){
                    return false;
                }
            }
        }

        if(atCheck != 1){
            return false;
        }

        if(pointCheck != 1){
            return false;
        }

        return true;
    }
}
