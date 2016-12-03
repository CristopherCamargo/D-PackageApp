package team.software.connection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDataVehicleCategory {
    @SerializedName("vehiclecategory") private List<VehicleCategory> vehiclecategory;

    public List<VehicleCategory> getVehiclecategory() {
        return vehiclecategory;
    }

    public void setVehiclecategory(List<VehicleCategory> vehiclecategory) {
        this.vehiclecategory = vehiclecategory;
    }
}
