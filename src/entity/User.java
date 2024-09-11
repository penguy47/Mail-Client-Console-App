package src.entity;

public class User {
    public String username;
    public String encodedPassword;
    public String email;

    public User(String username, String password, String email){
        this.username = username;
        this.encodedPassword = password;
        this.email = email;
    }
}
