package projekat.sf272016.model;

import java.util.List;

public class Tag {
    private int id;
    private String name;
    private List<Post> posts;

    public Tag(){

    }

    public Tag(String name, List<Post> posts) {
        this.name = name;
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
