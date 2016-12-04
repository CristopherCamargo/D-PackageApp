package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPreview2 extends AppCompatActivity {


    @BindView(R.id.next3)
    FloatingActionButton next3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_preview2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.next3)
    public void floating(){
        Intent intent = new Intent(this, LoginPreview3.class);
        startActivity(intent);
        finish();
    }
}
