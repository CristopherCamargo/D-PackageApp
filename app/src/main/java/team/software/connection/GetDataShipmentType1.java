package team.software.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.Map;

import team.software.d_packageapp.R;
import team.software.models.ShipmentTypeModel;

/**
 * Created by Carlos on 12/4/16.
 */

public class GetDataShipmentType1 implements AsyncResponse{
    private Context context;

    public GetDataShipmentType1(Context context){
        this.context = context;
        String urlRequest = new String("http://api.d-packagebackend.edwarbaron.me/api/v1/getshipmenttype/");
        RequestBase example = new RequestBase();
        example.delegate = this;
        // Especificar el tipo de solicitud: 0 GET, 1 POST, 2 DELETE, 3 PUT
        example.typeRequest = 0;
        example.execute(urlRequest);
    }
    @Override
    public void processFinish(String output) throws JSONException {
        if(output.compareTo("__error_conection")!=0) {
            Gson gson = new Gson();
            Map<String, String> jsonObject = gson.fromJson(output, Map.class);
            String element = gson.toJson(jsonObject.get("shipmenttype"));
            ShipmentTypeModel[] data = gson.fromJson(element,ShipmentTypeModel[].class);
            SharedPreferences sharedPref = this.context.getSharedPreferences("D-package", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            for (int i=0;i<data.length;i++){
                editor.putString("type_shipment_"+data[i].id,data[i].value);
            }
            editor.commit();
        }else{
            Toast toast = Toast.makeText(context, context.getString(R.string.error_connection_server), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}