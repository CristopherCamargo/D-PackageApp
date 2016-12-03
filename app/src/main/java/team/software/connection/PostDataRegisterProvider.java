package team.software.connection;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

public class PostDataRegisterProvider implements Serializable{
    @SerializedName("phone") private String phone;
    @SerializedName("birthdate") private String birthdate;
    @SerializedName("address") private String address;
    @SerializedName("identity_card") private String identity_card;
//    @SerializedName("photo") private File photo;

    @SerializedName("useraccount") private Useraccount useraccount;
    @SerializedName("vehicle") private Vehicle vehicle;

    public PostDataRegisterProvider() {

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
