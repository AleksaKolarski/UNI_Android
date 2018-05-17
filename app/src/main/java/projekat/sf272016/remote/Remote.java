package projekat.sf272016.remote;

import projekat.sf272016.remoteObjects.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Remote {
    public static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.8:8080/").addConverterFactory(GsonConverterFactory.create()).build();

    public static LoginRemote login = retrofit.create(LoginRemote.class);
    public static Test test = retrofit.create(Test.class);
}