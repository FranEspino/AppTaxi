package motoservice.com.Model;

public class PictureCard {
    private String picture;
    private String username;
    private String like_driver = "0";

    public PictureCard(String picture, String username, String like_driver) {
        this.picture = picture;
        this.username = username;
        this.like_driver = like_driver;
    }

    //GETTERS
    public String getPicture() {
        return picture;
    }

    public String getUsername() {
        return username;
    }

    public String getLike_driver() {
        return like_driver;
    }

    //SETTERS
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLike_driver(String like_driver) {
        this.like_driver = like_driver;
    }
}
