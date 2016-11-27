package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(),launchWindow());
        startActivity(intent);
        finish();
    }

    private Class<?> launchWindow() {
        return edit_profile_provider_service.class;
    }

}
