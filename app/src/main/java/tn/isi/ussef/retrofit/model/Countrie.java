package tn.isi.ussef.retrofit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ussef on 3/19/2017.
 */

public class Countrie {
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;

    public Countrie(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
