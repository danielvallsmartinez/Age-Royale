package Shared.Entity;

public class Follow {

    private User id_user1;
    private User id_user2;

    public Follow(User id_user1, User id_user2) {
        this.id_user1 = id_user1;
        this.id_user2 = id_user2;
    }

    public User getId_user1() {
        return id_user1;
    }

    public void setId_user1(User id_user1) {
        this.id_user1 = id_user1;
    }

    public User getId_user2() {
        return id_user2;
    }

    public void setId_user2(User id_user2) {
        this.id_user2 = id_user2;
    }
}
