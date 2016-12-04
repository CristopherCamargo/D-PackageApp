package team.software.connection;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

public class Vehicle implements Serializable{
    @SerializedName("licensePlate") private String licensePlate;
    @SerializedName("model") private String model;
    @SerializedName("licensePlate") private String category;
    @SerializedName("color") private String color;
    @SerializedName("photo") File photo1;

    public Vehicle(String licensePlate, String model, String category, String color) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.category = category;
        this.color = color;
        this.photo1 = photo1;
    }

    public Vehicle() {

    }

    public File getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String uri) {
        this.photo1 = new File(uri);
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
