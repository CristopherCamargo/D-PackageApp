package team.software.d_packageapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterProviderServiceOne extends AppCompatActivity {

    private Button buttonContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider_service_one);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivityRegisterProviderServiceTwo();
            }
        });
    }

    private void launchActivityRegisterProviderServiceTwo() {
        Intent ActivityRegisterProviderServiceTwo = new Intent(this, RegisterProviderServiceTwo.class);
        startActivity(ActivityRegisterProviderServiceTwo);
    }
}
