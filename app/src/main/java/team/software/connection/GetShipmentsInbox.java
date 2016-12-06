package team.software.connection;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import team.software.adapters.AdapterRequestInbox;
import team.software.d_packageapp.ListRequestInbox;
import team.software.models.RequestPackageModel;

/**
 * Created by Caceres on 06-12-2016.
 */

public class GetShipmentsInbox implements AsyncResponse{
    private Context context;
    private ListRequestInbox object;

    public GetShipmentsInbox(Context context,ListRequestInbox object){
        this.context = context;
        this.object = object;
        String urlRequest = new String("http://api.d-packagebackend.edwarbaron.me/api/v1/inbox_shipment/");
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
        RequestPackageModel[] data= gson.fromJson(element,RequestPackageModel[].class);
        this.object.request = new AdapterRequestInbox(this.object.getContext(),new ArrayList<RequestPackageModel>(Arrays.asList(data)));
        this.object.listView.setAdapter(this.object.request);
    }
}
