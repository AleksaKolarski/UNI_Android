package projekat.sf272016.model;

import android.graphics.Bitmap;
import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Post {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    private Bitmap photo;

    @SerializedName("user")
    @Expose
    private User user;

    private Date date;

    private Location location;
    private List<Tag> tags;
    private List<Comment> comments;

    @SerializedName("likes")
    @Expose
    private int likes;

    @SerializedName("dislikes")
    @Expose
    private int dislikes;

    public Post(){

    }

    public Post(String title, String description, Bitmap photo, User user, Date date, Location location, List<Tag> tags, List<Comment> comments, int likes, int dislikes) {
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.user = user;
        this.date = date;
        this.location = location;
        this.tags = tags;
        this.comments = comments;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public User getAuthor() {
        return user;
    }

    public void setAuthor(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
