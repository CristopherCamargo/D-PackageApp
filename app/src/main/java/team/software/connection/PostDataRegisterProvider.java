package team.software.connection;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

public class PostDataRegisterProvider implements Serializable{
    @SerializedName("phone") private String phone;
    @SerializedName("birthdate") private String birthdate;
    @SerializedName("address") private String address;
    @SerializedName("identity_card") private String identity_card;
    @SerializedName("photo") private File photo;
    @SerializedName("driver_license") private File driver_license;
    @SerializedName("useraccount") private Useraccount useraccount;
    @SerializedName("vehicle") private Vehicle vehicle;

    public PostDataRegisterProvider() {

    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(String uri) {
        this.photo = new File(uri);
    }

    public File getDriver_license() {
        return driver_license;
    }

    public void setDriver_license(String uri) {
        this.driver_license = new File(uri);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public Useraccount getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(Useraccount useraccount) {
        this.useraccount = useraccount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


}
