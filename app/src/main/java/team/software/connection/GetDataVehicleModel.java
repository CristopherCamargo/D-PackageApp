package team.software.connection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDataVehicleModel {
    @SerializedName("model") private List<VehicleModel> model;

    public List<VehicleModel> getModel() {
        return model;
    }

    public void setModel(List<VehicleModel> model) {
        this.model = model;
    }
}
