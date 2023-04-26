package modelo;

public class User {
    private int id;
    private String nickname;
    private String pwd;
    private String imageUrl;

    public User(int id, String nickname, String pwd, String imageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.pwd = pwd;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
