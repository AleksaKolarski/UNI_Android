package projekat.sf272016.remote;

import projekat.sf272016.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoginRemote {

    // LOGIN
    @GET("/OsaProjekat/login/{username}/{password}")
    Call<User> login(@Path("username") String username, @Path("password") String password);
}
