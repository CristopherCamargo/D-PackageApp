package team.software.connection;

import com.google.gson.annotations.SerializedName;

public class CardDefault {

    @SerializedName("id") private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
