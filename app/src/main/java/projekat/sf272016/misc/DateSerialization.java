package projekat.sf272016.misc;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

public class DateSerialization extends TypeAdapter<Date> {

    private static final TypeAdapter<Date> dateTypeAdapter = new DateSerialization();

    private DateSerialization(){

    }

    @Override
    public void write(JsonWriter out, Date date) throws IOException {
        out.value(date.getTime());
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        return new Date(in.nextLong());
    }

    public static TypeAdapter<Date> getDateTypeAdapter(){
        return dateTypeAdapter;
    }
}
