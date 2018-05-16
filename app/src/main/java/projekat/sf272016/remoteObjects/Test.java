package projekat.sf272016.remoteObjects;

import java.util.List;

import okhttp3.ResponseBody;
import projekat.sf272016.model.State;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Test {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("/api/data?list=states&format=json&abbreviate=false")
    Call<List<State>> getTest();
}
