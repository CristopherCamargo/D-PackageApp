package team.software.models;

/**
 * Created by Caceres on 06-12-2016.
 */
import com.google.gson.annotations.SerializedName;

public class ProviderModel {
    @SerializedName("useraccount")
    public UserAccountModel useraccount;
    @SerializedName("phone")
    public String phone;
    @SerializedName("photo")
    public String photo;
    @SerializedName("birthdate")
    public String birthdate;
    @SerializedName("address")
    public String address;
    @SerializedName("identity_card")
    public String identity_card;
    @SerializedName("driver_license")
    public String driver_license;
    @SerializedName("vehicle")
    public VehicleModel vehicle;
}
