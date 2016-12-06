package team.software.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;

import java.util.Map;

/**
 * Created by Caceres on 28-11-2016.
 */

public class RequestBase extends AsyncTask<String,Long,String> {

    public static final String TAG = "com.team.software";
    public AsyncResponse delegate = null;
    public String TokenAuthorization;
    public int typeRequest;
    public Map<String, String> data;

    protected String doInBackground(String... request) {
        try {
            String response = null;

            if(typeRequest == 0){ //GET
                if(this.TokenAuthorization!=null) {
                    response = HttpRequest.get(request[0]).accept("application/json").authorization("token "+this.TokenAuthorization)
                            .body();
                }
                else{
                    response = HttpRequest.get(request[0]).accept("application/json")
                            .body();
                }
            }
            if(typeRequest == 1){ //POST
                response = HttpRequest.post(request[0]).form(data).body();
            }
            if(typeRequest == 2){ //DELETE
                response = HttpRequest.delete(request[0]).accept("application/json")
                        .body();
            }
            if(typeRequest == 3){ //PUT
                response = HttpRequest.put(request[0]).accept("application/json")
                        .body();
            }

            return response;
        } catch (HttpRequest.HttpRequestException exception) {
            Log.i(TAG,"Hubo un error en HttpRequest: "+exception);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response);
        try {
            delegate.processFinish(gson.toJson(element));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
