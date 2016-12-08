package team.software.connection;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import team.software.models.ShipmentTypeModel;

public class GetDataShipmentType{
    @SerializedName("shipmenttype") private List<ShipmentType> shipmenttype = null;

    public List<ShipmentType> getShipmenttype() {
        return shipmenttype;
    }

    public void setShipmenttype(List<ShipmentType> shipmenttype) {
        this.shipmenttype = shipmenttype;
    }
}
