package src.config;

public class ClientConfig {
    String username;
    String email;
    String password;
    int timeRefresh;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setTimeRefresh(int timeRefresh){
        this.timeRefresh = timeRefresh;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public int getTimeRefresh(){
        return this.timeRefresh;
    }

    @Override
    public String toString() {
        return "ClientCofig{" +
                "username='" + username + '\'' +
                ", email='" + email + '\''+
                ", password='" + password + '\''+
                ", refresh=" + timeRefresh +
                '}';
    }
}
