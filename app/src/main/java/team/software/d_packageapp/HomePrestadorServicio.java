package team.software.d_packageapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import team.software.adapters.TabProviderServiceAdapter;
import team.software.connection.API;
import team.software.connection.logout;
import team.software.connection.GetDataPackageType;
import team.software.connection.GetDataShipmentType;
import team.software.connection.GetDataStatus;

public class HomePrestadorServicio extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_prestador_servicio);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabs_provider);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.container_provider);

        //Creating our pager adapter
        TabProviderServiceAdapter adapter = new TabProviderServiceAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);


        new GetDataStatus(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_provider, menu);
        return true;
    }

    private API getInstance() {
        String BASE_URL = "http://api.d-packagebackend.edwarbaron.me/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        return api;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_provider_edit_profile) {
            Intent intent = new Intent(this, EditProfileProviderService.class);
            startActivity(intent);
        }

        if (id == R.id.menu_provider_change_password) {
            Intent intent = new Intent(this, ChangePassword.class);
            startActivity(intent);
        }

        if (id == R.id.menu_provider_logout) {

            sharedPref = getApplicationContext().getSharedPreferences("D-package", Context.MODE_PRIVATE);
            logout log = new logout(sharedPref.getString("user_token",""));
            Call<ResponseBody> response = getInstance().logout("Token "+sharedPref.getString("user_token",""), log);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 204) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("sesion_open", false);
                        editor.putBoolean("logout", true);
                        editor.commit();

                        Intent intent = new Intent(HomePrestadorServicio.this, SelectRegister.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("register",true);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(HomePrestadorServicio.this, "Error Al Cerrar Sesión", Toast.LENGTH_SHORT).show();
                        try {
                            Log.e("Body Error: ", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
