package projekat.sf272016.remote;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import projekat.sf272016.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserRemote {

    // GET ALL
    @GET(Remote.APP_PATH + "/users")
    Call<ArrayList<User>> getAllUsers();

    // GET BY USERNAME
    @GET(Remote.APP_PATH + "/users/{username}")
    Call<User> getUserByUsername(@Path("username") String username);

    // GET BY USERNAME AND PASSWORD
    @GET(Remote.APP_PATH + "/users/{username}/{password}")
    Call<User> getUserByUsernameAndPassword(@Path("username") String username, @Path("password") String password);

    // EDIT
    @PUT(Remote.APP_PATH + "/users")
    Call<User> editUser(@Body User user);

    // CREATE
    @POST(Remote.APP_PATH + "/users")
    Call<User> createUser(@Body User user);
}
