package team.software.d_packageapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;

import team.software.connection.AsyncResponse;
import team.software.connection.HttpRequest;
import team.software.connection.RequestBase;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(),launchWindow());
        startActivity(intent);
        finish();
    }

    private Class<?> launchWindow() {

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("D-package", Context.MODE_PRIVATE);

        if (!sharedPref.getBoolean("presentacion", true)) {
            return SelectRegister.class;
        }

        if (sharedPref.getBoolean("sesion_open", false)) {
            if (sharedPref.getString("type_user", "").equals("client")) {
                return HomeClient.class;
            } else {
                return HomePrestadorServicio.class;
            }
        }

        return LoginPreview.class;
    }
}
