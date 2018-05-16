package projekat.sf272016.remoteObjects;

import java.util.ArrayList;
import java.util.List;

import projekat.sf272016.model.Post;
import projekat.sf272016.model.State;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface Test {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("/api/data?list=states&format=json&abbreviate=false")
    Call<ArrayList<State>> getTest();

    @GET("/{id}")
    Call<Post> getPost(@Path("id") int id);
}
