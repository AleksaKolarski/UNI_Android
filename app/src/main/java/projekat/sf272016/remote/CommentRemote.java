package projekat.sf272016.remote;

import java.util.ArrayList;

import projekat.sf272016.model.Comment;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommentRemote {

    // GET ALL BY POST_ID
    @GET(Remote.APP_PATH + "/comments/{postId}")
    Call<ArrayList<Comment>> getCommentsByPostId(@Path("postId") int postId);


}
