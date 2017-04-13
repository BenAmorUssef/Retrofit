package tn.isi.ussef.retrofit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ussef on 3/19/2017.
 */

public class Companie {
    @SerializedName("iso_3166_1")
    private String iso_3166_1;
    @SerializedName("name")
    private String name;

    public Companie(String iso_3166_1, String name) {
        this.iso_3166_1 = iso_3166_1;
        this.name = name;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
