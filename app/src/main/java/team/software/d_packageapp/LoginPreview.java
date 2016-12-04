package team.software.d_packageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPreview extends AppCompatActivity {

    @BindView(R.id.next2)
    FloatingActionButton next;

    @OnClick(R.id.next2)
    public void floating(){
        Intent intent = new Intent(this, LoginPreview2.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_preview);
        ButterKnife.bind(this);
    }


}
