package projekat.sf272016.remoteObjects;

import java.util.ArrayList;
import java.util.List;

import projekat.sf272016.model.Post;
import projekat.sf272016.model.State;
import projekat.sf272016.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface Test {

    @GET("/OsaProjekat/users")
    Call<ArrayList<User>> getAllUsers();

    @GET("/OsaProjekat/users/{username}")
    Call<User> getUserByUsername(@Path("username") String username);

    @GET("/OsaProjekat/users/{username}/{password}")
    Call<User> getUserByUsernameAndPassword(@Path("username") String username, @Path("password") String password);
}
