package projekat.sf272016.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {
    @SerializedName("statename")
    @Expose
    private String statename;

    @SerializedName("abbreviation")
    @Expose
    private String abbreviation;

    @SerializedName("capital")
    @Expose
    private String capital;

    @SerializedName("statehoodyear")
    @Expose
    private String statehoodyear;

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getStatehoodyear() {
        return statehoodyear;
    }

    public void setStatehoodyear(String statehoodyear) {
        this.statehoodyear = statehoodyear;
    }

}
