package team.software.d_packageapp;

import android.content.Intent;
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
    }

    private Class<?> launchWindow() {
        return Login.class;
    }
}
