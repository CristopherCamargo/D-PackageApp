package team.software.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import team.software.models.PackageTypeModel;


/**
 * Created by Caceres on 04-12-2016.
 */

public class GetDataPackageType implements AsyncResponse{

    private Context context;

    public GetDataPackageType(Context context){
        this.context = context;
        String urlRequest = new String("http://api.d-packagebackend.edwarbaron.me/api/v1/getpackagetype/");
        RequestBase example = new RequestBase();
        example.delegate = this;
        // Especificar el tipo de solicitud: 0 GET, 1 POST, 2 DELETE, 3 PUT
        example.typeRequest = 0;
        example.execute(urlRequest);
    }

    @Override
    public void processFinish(String output) throws JSONException {
        Gson gson = new Gson();
        Map<String, String> jsonObject = gson.fromJson(output, Map.class);
        String element = gson.toJson(jsonObject.get("packagetype"));
        PackageTypeModel[] data = gson.fromJson(element,PackageTypeModel[].class);
        SharedPreferences sharedPref = this.context.getSharedPreferences("D-package", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for (int i=0;i<data.length;i++){
            editor.putString("type_package_"+data[i].id,data[i].value);
        }
        editor.commit();
    }
}
