package team.software.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DecimalFormat;

import java.util.ArrayList;

import team.software.d_packageapp.DetailsRequest;
import team.software.d_packageapp.R;
import team.software.models.RequestPackageModel;

public class AdapterRequestPS extends ArrayAdapter<RequestPackageModel> {

    private Context context;

    public AdapterRequestPS(Context context, ArrayList<RequestPackageModel> requests) {
        super(context,0,requests);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RequestPackageModel requestItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter_request_ps, parent, false);
        }
        TextView client = (TextView) convertView.findViewById(R.id.client_request_ps_a);
        TextView destine = (TextView) convertView.findViewById(R.id.destine_request_ps_a);
        TextView status = (TextView) convertView.findViewById(R.id.status_request_ps_a);
        TextView type = (TextView) convertView.findViewById(R.id.type_request_ps_a);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_type_request_ps_a);

        client.setText(requestItem.client.useraccount.first_name);
        destine.setText("SDRB:23 POINT(100 100)");
        SharedPreferences sharedPref = context.getSharedPreferences("D-package", Context.MODE_PRIVATE);
        status.setText(sharedPref.getString("status_request_"+requestItem.status,"null"));
        type.setText(sharedPref.getString("type_shipment_"+requestItem.shipmenttype,"null"));

        switch (requestItem.status){
            case 2:
                status.setBackgroundColor(getContext().getResources().getColor(R.color.status2));
                break;
            case 3:
                status.setBackgroundColor(getContext().getResources().getColor(R.color.status3));
                break;
            case 4:
                status.setBackgroundColor(getContext().getResources().getColor(R.color.status4));
                break;
            case 5:
                status.setBackgroundColor(getContext().getResources().getColor(R.color.status5));
                break;
            default:
                break;
        }

        if(requestItem.shipmenttype==1)
            image.setImageResource(R.mipmap.ic_express_request);
        else
            image.setImageResource(R.mipmap.ic_standard_request);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DetailsRequest.class);
                Gson gson = new Gson();
                String requestObject = gson.toJson(requestItem);
                intent.putExtra("requestObject",requestObject);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
