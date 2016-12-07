package team.software.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import team.software.adapters.AdapterRequestPS;
import team.software.d_packageapp.ListRequestPS;
import team.software.models.RequestPackageModel;

/**
 * Created by Carlos on 12/4/16.
 */

public class GetShipmentsPS implements AsyncResponse{
    private Context context;
    private ListRequestPS object;

    public GetShipmentsPS(Context context,ListRequestPS object) {
        this.context = context;
        this.object = object;
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
        Log.i("com.prueba",element);
        RequestPackageModel[] data= gson.fromJson(element,RequestPackageModel[].class);
        this.object.request = new AdapterRequestPS(this.object.getContext(),new ArrayList<RequestPackageModel>(Arrays.asList(data)));
        this.object.listView.setAdapter(this.object.request);
    }

}
