package team.software.d_packageapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SelectRegister extends AppCompatActivity {

    private ImageButton buttonRegisterClient, buttonRegisterProviderService;
    private TextView linkLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_register);
        // Initial Event OnClick
        buttonRegisterClient = (ImageButton) findViewById(R.id.buttonRegisterClient);
        buttonRegisterProviderService = (ImageButton) findViewById(R.id.buttonRegisterProviderService);
        linkLogin = (TextView) findViewById(R.id.linkLogin);

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

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLogin();
            }
        });
    }

    private void launchLogin() {
        Intent login = new Intent(this,Login.class);
        startActivity(login);
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
