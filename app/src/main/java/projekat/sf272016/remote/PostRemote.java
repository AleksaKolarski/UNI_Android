package projekat.sf272016.remote;

import java.util.ArrayList;
import java.util.Date;

import projekat.sf272016.model.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostRemote {

    // GET ALL
    @GET(Remote.APP_PATH + "/posts")
    Call<ArrayList<Post>> getAllPosts();

    // GET ALL OrderByDate
    @POST(Remote.APP_PATH + "/posts/orderByDate")
    Call<ArrayList<Post>> getAllPostsOrderByDate(@Body Date date);

    // GET ALL OrderByDate
    @POST(Remote.APP_PATH + "/posts/orderByLikes")
    Call<ArrayList<Post>> getAllPostsOrderByLikes(@Body Date date);

    // GET BY ID
    @GET(Remote.APP_PATH + "/posts/{id}")
    Call<Post> getPostById(@Path("id") Integer id);

    // EDIT
    @PUT(Remote.APP_PATH + "/posts")
    Call<Post> editPost(@Body Post post);

    // CREATE
    @POST(Remote.APP_PATH + "/posts")
    Call<Post> createPost(@Body Post post);

    @DELETE(Remote.APP_PATH + "/posts/{id}")
    Call<Void> deletePost(@Path("id") Integer id);

    // LIKE
    @POST(Remote.APP_PATH + "/posts/{id}/like")
    Call<Integer> likePost(@Path("id") int id);

    // DISLIKE
    @POST(Remote.APP_PATH + "/posts/{id}/dislike")
    Call<Integer> dislikePost(@Path("id") int id);
}
