package team.software.connection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import team.software.d_packageapp.HomeClient;
import team.software.d_packageapp.HomePrestadorServicio;
import team.software.d_packageapp.R;

/**
 * Created by carlos on 12/7/16.
 */

public class PostUpdateRequest implements AsyncResponse {
    Context context;

    public PostUpdateRequest(Context context,int idStatus,int idRequest){
        String urlRequest = new String("http://api.d-packagebackend.edwarbaron.me/api/v1/shipment/update_shipment/");
        RequestBase example = new RequestBase();
        this.context = context;
        example.delegate = this;
        // Especificar el tipo de solicitud: 0 GET, 1 POST, 2 DELETE, 3 PUT
        example.typeRequest = 1;
        example.data = new HashMap<String,String>();
        example.data.put("status",String.valueOf(idStatus));
        example.data.put("id",String.valueOf(idRequest));
        SharedPreferences sharedPref = this.context.getSharedPreferences("D-package", Context.MODE_PRIVATE);
        example.TokenAuthorization = sharedPref.getString("user_token",null);
        example.execute(urlRequest);
    }
    @Override
    public void processFinish(String output) throws JSONException {
        Gson gson = new Gson();
        Log.i("com.prueba",output);
        Map<String, String> jsonObject = gson.fromJson(output, Map.class);
        String verify = jsonObject.get("status");
        if(verify != null && !verify.isEmpty()){
            Toast toast = Toast.makeText(this.context, context.getString(R.string.error_conection), Toast.LENGTH_SHORT);
            toast.show();
        }else{
            SharedPreferences sharedPref = this.context.getSharedPreferences("D-package", Context.MODE_PRIVATE);
            if(sharedPref.getString("type_user","null").compareTo("service")==0) {
                Intent home_activity = new Intent(context, HomePrestadorServicio.class);
                home_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(home_activity);
            }else{
                Intent home_activity = new Intent(context, HomeClient.class);
                home_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(home_activity);
            }
        }
    }
}
