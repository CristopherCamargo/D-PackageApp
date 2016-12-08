package team.software.connection;

import com.google.gson.annotations.SerializedName;

public class PackageType {
    @SerializedName("id") private int id;
    @SerializedName("value") private String value;

    public PackageType(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
