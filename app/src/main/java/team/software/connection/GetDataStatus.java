package team.software.connection;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.Map;

import team.software.models.ShipmentStatusModel;

/**
 * Created by Caceres on 06-12-2016.
 */

public class GetDataStatus implements AsyncResponse{
    private Context context;

    public GetDataStatus(Context context){
        this.context = context;
        String urlRequest = new String("http://api.d-packagebackend.edwarbaron.me/api/v1/shipment/getstatus/");
        RequestBase example = new RequestBase();
        example.delegate = this;
        // Especificar el tipo de solicitud: 0 GET, 1 POST, 2 DELETE, 3 PUT
        example.typeRequest = 0;
        SharedPreferences sharedPref = this.context.getSharedPreferences("D-package", Context.MODE_PRIVATE);
        example.TokenAuthorization = sharedPref.getString("user_token",null);
        example.execute(urlRequest);
    }
    @Override
    public void processFinish(String output) throws JSONException {
        Gson gson = new Gson();
        Map<String, String> jsonObject = gson.fromJson(output, Map.class);
        String element = gson.toJson(jsonObject.get("status"));
        ShipmentStatusModel[] data = gson.fromJson(element,ShipmentStatusModel[].class);
        SharedPreferences sharedPref = this.context.getSharedPreferences("D-package", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for (int i=0;i<data.length;i++){
            editor.putString("status_request_"+data[i].id,data[i].value);
        }
        editor.commit();
    }
}
