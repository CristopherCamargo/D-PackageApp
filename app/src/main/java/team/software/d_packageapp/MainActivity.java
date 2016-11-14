package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonRegisterClient, buttonRegisterProviderService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initial Event OnClick
        buttonRegisterClient = (Button) findViewById(R.id.buttonRegisterClient);
        buttonRegisterProviderService = (Button) findViewById(R.id.buttonRegisterProviderService);

        buttonRegisterClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivityRegisterClient();
            }
        });

        buttonRegisterProviderService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivityRegisterProviderService();
            }
        });
    }

    private void launchActivityRegisterProviderService() {
        Intent ActivityRegisterProviderService = new Intent(this,RegisterProviderServiceOne.class);
        startActivity(ActivityRegisterProviderService);
    }

    private void launchActivityRegisterClient() {
        Intent ActivityRegisterClient = new Intent(this,RegisterClient.class);
        startActivity(ActivityRegisterClient);
    }

}
