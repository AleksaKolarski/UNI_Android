package projekat.sf272016.remote;

import java.util.ArrayList;

import projekat.sf272016.model.Tag;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TagRemote {

    // GET ALL
    @GET(Remote.APP_PATH + "/tags")
    Call<ArrayList<Tag>> getAllTags();

    // GET BY ID
    @GET(Remote.APP_PATH + "/tags/{id}")
    Call<Tag> getById(@Path("id") Integer id);

    // GET BY POST ID
    @GET(Remote.APP_PATH + "/tags/byPost/{postId}")
    Call<ArrayList<Tag>> getByPostId(@Path("postId") Integer postId);

    // CREATE
    @POST(Remote.APP_PATH + "/tags")
    Call<Tag> create(@Body Tag tag);

    // EDIT
    @PUT(Remote.APP_PATH + "/tags/{id}")
    Call<Tag> update(@Body Tag tag);

    // DELETE
    @DELETE(Remote.APP_PATH + "/tags/{id}")
    Call<Void> delete(@Path("id") Integer id);
}
