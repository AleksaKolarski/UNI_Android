package projekat.sf272016.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Remote {
    public static final String APP_PATH = "/OsaProjekat";

    public static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.8:8080/").addConverterFactory(GsonConverterFactory.create()).build();

    public static LoginRemote loginRemote = retrofit.create(LoginRemote.class);
    public static PostRemote postRemote = retrofit.create(PostRemote.class);
}