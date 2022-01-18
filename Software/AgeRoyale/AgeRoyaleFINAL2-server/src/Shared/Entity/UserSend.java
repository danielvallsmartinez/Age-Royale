package Shared.Entity;

import java.io.Serializable;

public class UserSend implements Serializable {

    private static final long serialVersionUID = 123456L;

    private User user;
    private int userOption;

    public UserSend(User user, int userOption) {
        this.user = user;
        this.userOption = userOption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserOption() {
        return userOption;
    }

    public void setUserOption(int userOption) {
        this.userOption = userOption;
    }
}
