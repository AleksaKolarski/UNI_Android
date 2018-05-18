package projekat.sf272016.remote;

import java.util.ArrayList;

import projekat.sf272016.model.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostRemote {

    // GET ALL
    @GET(Remote.APP_PATH + "/posts")
    Call<ArrayList<Post>> getAllPosts();

    // GET BY ID
    @GET(Remote.APP_PATH + "/posts/{id}")
    Call<Post> getPostById(@Path("id") Integer id);

    // EDIT
    @PUT(Remote.APP_PATH + "/posts")
    Call<Post> editPost(@Body Post post);

    // CREATE
    @POST(Remote.APP_PATH + "/posts")
    Call<Post> createPost(@Body Post post);
}
