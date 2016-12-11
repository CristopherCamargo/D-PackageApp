package team.software.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import team.software.adapters.AdapterRequestPackage;
import team.software.d_packageapp.ListRequestPackage;
import team.software.d_packageapp.R;
import team.software.models.RequestPackageModel;


/**
 * Created by Caceres on 06-12-2016.
 */

public class GetShipmentsClient implements AsyncResponse{
    private Context context;
    private ListRequestPackage object;

    public GetShipmentsClient(Context context,ListRequestPackage object){
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
        if(output.compareTo("__error_conection")!=0) {
            Log.i("com.prueba", output);
            Gson gson = new Gson();
            Map<String, String> jsonObject = gson.fromJson(output, Map.class);
            String element = gson.toJson(jsonObject.get("results"));
            RequestPackageModel[] data = gson.fromJson(element, RequestPackageModel[].class);
            this.object.circle_progress.setVisibility(View.INVISIBLE);
            this.object.request = new AdapterRequestPackage(this.object.getContext(), new ArrayList<RequestPackageModel>(Arrays.asList(data)));
            this.object.listView.setAdapter(this.object.request);
        }else{
            this.object.circle_progress.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(context, context.getString(R.string.error_connection_server), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
