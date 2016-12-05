package team.software.connection;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.Map;

import team.software.models.RequestPSPackageModel;

/**
 * Created by Carlos on 12/4/16.
 */

public class GetShipmentsPS implements AsyncResponse {
    private Context context;
    private RequestPSPackageModel[] data;

    public GetShipmentsPS(Context context) {
        this.context = context;
        String urlRequest = new String("http://api.d-packagebackend.edwarbaron.me/api/v1/shipment/");
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
        String element = gson.toJson(jsonObject.get("results"));
        data = gson.fromJson(element,RequestPSPackageModel[].class);
    }

    public RequestPSPackageModel[] getData() {
        return data;
    }
}
