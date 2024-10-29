package xyz.niflheim.stockfish.repository;

public class User {
    private String Id;
    private String password;

    public User() {
    }

    public User(String password, String id) {
        this.password = password;
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
