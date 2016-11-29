package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;

import team.software.connection.AsyncResponse;
import team.software.connection.HttpRequest;
import team.software.connection.RequestBase;

//La clase debe implementar la interfaz AsyncResponse
public class MainActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Ejemplo - Colocar Url de solicitud
        String urlRequest = new String("http://api.d-packagebackend.edwarbaron.me/api/v1/login/");
        RequestBase example = new RequestBase();
        // Asignar que el objeto de la clase es el que va a delegar el resultado
        example.delegate = this;
        // Especificar el tipo de solicitud: 0 GET, 1 POST, 2 DELETE, 3 PUT
        example.typeRequest = 1;
        //Llenar la data que se va a enviar
        example.data = new HashMap<String,String>();
        example.data.put("username","leonel");
        example.data.put("password","123");
        //Ejecutar la llamada y enviar la url de solicitud
        example.execute(urlRequest);

        Intent intent = new Intent(getApplicationContext(),launchWindow());
        startActivity(intent);
    }

    private Class<?> launchWindow() {
        return ListRequestPackage.class;
    }

    //Este metodo lo debe tener la clase para obtener el resultado de la solicitud
    @Override
    public void processFinish(String output) {
        //Guardar la respuesta o parsear a una clase
        Log.i("com.prueba",output);
    }
}
