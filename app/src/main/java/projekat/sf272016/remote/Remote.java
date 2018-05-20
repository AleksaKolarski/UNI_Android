package projekat.sf272016.remote;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import projekat.sf272016.misc.ImageSerialization;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Remote {
    public static final String APP_PATH = "/OsaProjekat";

    public static OkHttpClient httpClient() {
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

    static Gson gson = new GsonBuilder().registerTypeAdapter(Bitmap.class, ImageSerialization.getBitmapTypeAdapter()).create();

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.8:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient())
            .build();

    public static LoginRemote loginRemote = retrofit.create(LoginRemote.class);
    public static PostRemote postRemote = retrofit.create(PostRemote.class);
    public static UserRemote userRemote = retrofit.create(UserRemote.class);
}