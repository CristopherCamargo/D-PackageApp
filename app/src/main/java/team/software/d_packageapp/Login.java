package team.software.d_packageapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import team.software.connection.AsyncResponse;
import team.software.connection.RequestBase;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity implements AsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button sigIn = (Button)findViewById(R.id.email_sign_in_button);

        sigIn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                loginIntent();
            }
        });
    }

    protected void loginIntent(){
        String urlRequest = new String("http://api.d-packagebackend.edwarbaron.me/api/v1/login/");
        RequestBase example = new RequestBase();
        EditText email = (EditText)findViewById(R.id.email);
        EditText pass = (EditText)findViewById(R.id.password);

        example.delegate = this;
        // Especificar el tipo de solicitud: 0 GET, 1 POST, 2 DELETE, 3 PUT
        example.typeRequest = 1;
        example.data = new HashMap<String,String>();
        example.data.put("username",email.getText().toString());
        example.data.put("password",pass.getText().toString());
        example.execute(urlRequest);
    }

    @Override
    public void processFinish(String output) {
        Log.i("com.prueba",output);
        Context context = getApplicationContext();
        Gson gson = new Gson();
        Map<String, String> jsonObject = gson.fromJson(output, Map.class);

        //Verify error or connect
        String verify = jsonObject.get("status");
        if(verify != null && !verify.isEmpty()){
            Toast toast = Toast.makeText(context, getString(R.string.no_login_message), Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            //Save token
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("user_token", jsonObject.get("token"));
            editor.commit();

//            Change activity
            if(jsonObject.get("type").compareTo("client")==0) {
                Intent home_activity = new Intent(this, HomeClient.class);
                startActivity(home_activity);
            }else{
                Intent home_activity = new Intent(this, HomePrestadorServicio.class);
                startActivity(home_activity);
            }
            //Activity finish
            this.finish();
        }

    }
}
