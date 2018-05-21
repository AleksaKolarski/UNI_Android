package projekat.sf272016.remote;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import projekat.sf272016.misc.DateSerialization;
import projekat.sf272016.misc.ImageSerialization;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Remote {
    public static final String APP_PATH = "/OsaProjekat";

    private static OkHttpClient httpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        return client;
    }

    private static Gson gson = new GsonBuilder().registerTypeAdapter(Bitmap.class, ImageSerialization.getBitmapTypeAdapter()).registerTypeAdapter(Date.class, DateSerialization.getDateTypeAdapter()).create();

    private static Retrofit retrofit;

    public static LoginRemote loginRemote;
    public static PostRemote postRemote;
    public static UserRemote userRemote;
    public static CommentRemote commentRemote;

    public static boolean init(String hostAddress){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://" + hostAddress + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient())
                .build();
        if (retrofit == null){
            Log.e("retrofit", "could not init retrofit with host adress '" + hostAddress + "'");
            return false;
        }
        else {
            loginRemote = retrofit.create(LoginRemote.class);
            postRemote = retrofit.create(PostRemote.class);
            userRemote = retrofit.create(UserRemote.class);
            commentRemote = retrofit.create(CommentRemote.class);
            return true;
        }
    }
}