package team.software.models;

/**
 * Created by carlos on 12/6/16.
 */
import com.google.gson.annotations.SerializedName;
import java.io.File;

public class VehicleModel {
    @SerializedName("license_plate")
    public String license_plate;
    @SerializedName("model")
    public int model;
    @SerializedName("category")
    public int category;
    @SerializedName("color")
    public String color;
    @SerializedName("photo1")
    public String photo1;
}
