package projekat.sf272016.remote;

import java.util.ArrayList;

import projekat.sf272016.model.Comment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentRemote {

    // GET ALL BY POST_ID
    @GET(Remote.APP_PATH + "/posts/{postId}/comments")
    Call<ArrayList<Comment>> getCommentsByPostId(@Path("postId") int postId);

    // GET ALL BY POST_ID OrderBy Date
    @GET(Remote.APP_PATH + "/posts/{postId}/comments/orderByDate")
    Call<ArrayList<Comment>> getCommentsOrderByDate(@Path("postId") int postId);

    // GET ALL BY POST_ID OrderBy Likes
    @GET(Remote.APP_PATH + "/posts/{postId}/comments/orderByLikes")
    Call<ArrayList<Comment>> getCommentsOrderByLikes(@Path("postId") int postId);

    // CREATE
    @POST(Remote.APP_PATH + "/comments")
    Call<Comment> createComment(@Body Comment comment);

    // LIKE
    @POST(Remote.APP_PATH + "/comments/{id}/like")
    Call<Integer> likeComment(@Path("id") int id);

    // DISLIKE
    @POST(Remote.APP_PATH + "/comments/{id}/dislike")
    Call<Integer> dislikeComment(@Path("id") int id);
}
