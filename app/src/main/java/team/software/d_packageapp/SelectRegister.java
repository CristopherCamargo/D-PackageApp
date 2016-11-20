package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectRegister extends AppCompatActivity {

    @BindView(R.id.buttonRegisterClient)
    ImageButton buttonRegisterClient;
    @BindView(R.id.buttonRegisterProviderService)
    ImageButton buttonRegisterProviderService;
    @BindView(R.id.linkLogin)
    TextView linkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.linkLogin)
    public void launchLogin() {
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }

    @OnClick(R.id.buttonRegisterProviderService)
    public void launchActivityRegisterProviderService() {
        Intent ActivityRegisterProviderService = new Intent(this, RegisterProviderServiceOne.class);
        startActivity(ActivityRegisterProviderService);
    }

    @OnClick(R.id.buttonRegisterClient)
    public void launchActivityRegisterClient() {
        Intent ActivityRegisterClient = new Intent(this, RegisterClient.class);
        startActivity(ActivityRegisterClient);
    }

}
