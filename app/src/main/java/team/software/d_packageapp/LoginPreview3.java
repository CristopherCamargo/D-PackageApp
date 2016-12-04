package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPreview3 extends AppCompatActivity {


    @BindView(R.id.next4)
    FloatingActionButton next4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_preview3);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.next4)
    public void onClick() {
        Intent intent = new Intent(this, LoginPreview4.class);
        startActivity(intent);
        finish();
    }
}
