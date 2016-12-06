package team.software.models;

/**
 * Created by Caceres on 06-12-2016.
 */
import com.google.gson.annotations.SerializedName;
public class ClientModel {
    @SerializedName("useraccount")
    public UserAccountModel useraccount;
    @SerializedName("phone")
    public String phone;
}
