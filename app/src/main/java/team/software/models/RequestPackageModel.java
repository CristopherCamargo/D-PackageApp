package team.software.models;

/**
 * Created by Carlos on 12/4/16.
 */
import com.google.gson.annotations.SerializedName;
public class RequestPackageModel {
    @SerializedName("id")
    public int id;
    @SerializedName("client")
    public ClientModel client;
    @SerializedName("service")
    public int service;
    @SerializedName("shipmenttype")
    public int shipmenttype;
    @SerializedName("packagetype")
    public int packagetype;
    @SerializedName("photo1")
    public String photo1;
    @SerializedName("photo2")
    public String photo2;
    @SerializedName("photo3")
    public String photo3;
    @SerializedName("tags")
    public String tags;
    @SerializedName("receiver")
    public String receiver;
    @SerializedName("origin")
    public String origin;
    @SerializedName("destination")
    public String destination;
    @SerializedName("insured")
    public boolean insured;
    @SerializedName("price")
    public double price;
    @SerializedName("status")
    public int status;
}
