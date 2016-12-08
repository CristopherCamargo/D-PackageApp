package team.software.d_packageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PaysClient extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_pays_client, container, false);
        final TextView mount = (TextView) rootView.findViewById(R.id.current_mount);
        final SharedPreferences sharedPref = getContext().getSharedPreferences("D-package", Context.MODE_PRIVATE);
        mount.setText(String.valueOf(sharedPref.getInt("mount",0)));
        Button button = (Button) rootView.findViewById(R.id.credit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                EditText credit_input = (EditText) rootView.findViewById(R.id.credit_input);
                int mount_current = sharedPref.getInt("mount",0)+Integer.valueOf(credit_input.getText().toString());
                editor.putInt("mount",mount_current);
                editor.commit();
                mount.setText(String.valueOf(mount_current));
                credit_input.setText("");
                Toast toast = Toast.makeText(getContext(), getString(R.string.credit_successfull), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return rootView;
    }
}
