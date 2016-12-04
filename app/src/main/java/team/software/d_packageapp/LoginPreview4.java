package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPreview4 extends AppCompatActivity {


    @BindView(R.id.next5)
    FloatingActionButton next5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_preview4);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.next5)
    public void click(){
        Intent intent = new Intent(this, SelectRegister.class);
        startActivity(intent);
        finish();
    }
}
