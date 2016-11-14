package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RegisterProviderServiceTwo extends AppCompatActivity {

    private Button buttonContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider_service_two);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        buttonContinue = (Button) findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivityRegisterProviderServiceThree();
            }
        });
    }

    private void launchActivityRegisterProviderServiceThree() {
        Intent ActivityRegisterProviderServiceThree = new Intent(this, RegisterProviderServiceThree.class);
        startActivity(ActivityRegisterProviderServiceThree);
    }
}
