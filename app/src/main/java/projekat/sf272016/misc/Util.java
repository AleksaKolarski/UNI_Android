package projekat.sf272016.misc;

import projekat.sf272016.remoteObjects.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Util {
    public static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.google.com").addConverterFactory(GsonConverterFactory.create()).build();
    public static Test test = retrofit.create(Test.class);
}
