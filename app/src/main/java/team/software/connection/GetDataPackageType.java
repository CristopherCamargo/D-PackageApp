package team.software.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import team.software.models.PackageTypeModel;

public class GetDataPackageType{
    @SerializedName("packagetype") private List<PackageType> packagetype = null;

    public List<PackageType> getPackagetype() {
        return packagetype;
    }

    public void setPackagetype(List<PackageType> packagetype) {
        this.packagetype = packagetype;
    }

}
