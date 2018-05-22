package projekat.sf272016.misc;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSerialization extends TypeAdapter<Date> {

    private static final TypeAdapter<Date> dateTypeAdapter = new DateSerialization();

    private DateSerialization(){

    }

    @Override
    public void write(JsonWriter out, Date date) throws IOException {
        if(date != null) {
            out.value(date.getTime());
        }
        else{
            out.jsonValue("null");
        }
    }

    @Override
    public Date read(JsonReader in) throws IOException {

        if(in.peek() != JsonToken.NULL){
            return new Date(in.nextLong());
        }
        return null;
    }

    public static TypeAdapter<Date> getDateTypeAdapter(){
        return dateTypeAdapter;
    }
}
