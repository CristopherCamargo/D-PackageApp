package team.software.models;

/**
 * Created by Caceres on 06-12-2016.
 */
import com.google.gson.annotations.SerializedName;
public class UserAccountModel {
    @SerializedName("id")
    public int id;
    @SerializedName("first_name")
    public String first_name;
    @SerializedName("last_name")
    public String last_name;
    @SerializedName("email")
    public String email;

}
