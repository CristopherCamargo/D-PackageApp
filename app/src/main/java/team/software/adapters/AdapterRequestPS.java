package team.software.adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import team.software.d_packageapp.R;
import team.software.models.RequestPSPackageModel;

public class AdapterRequestPS extends ArrayAdapter<RequestPSPackageModel> {

    public AdapterRequestPS(Context context, ArrayList<RequestPSPackageModel> requests) {
        super(context,0,requests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RequestPSPackageModel requestItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter_request_ps, parent, false);
        }
        TextView client = (TextView) convertView.findViewById(R.id.client_request_ps_a);
        TextView destine = (TextView) convertView.findViewById(R.id.destine_request_ps_a);
        TextView status = (TextView) convertView.findViewById(R.id.status_request_ps_a);
        TextView type = (TextView) convertView.findViewById(R.id.type_request_ps_a);



        return convertView;
    }
}
