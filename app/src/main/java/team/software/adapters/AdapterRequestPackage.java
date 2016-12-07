package team.software.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import team.software.d_packageapp.DetailsRequest;
import team.software.d_packageapp.R;
import team.software.models.RequestPackageModel;

public class AdapterRequestPackage extends ArrayAdapter<RequestPackageModel> {


    public AdapterRequestPackage(Context context, ArrayList<RequestPackageModel> requests) {
        super(context,0,requests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final RequestPackageModel requestItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter_request_package, parent, false);
        }

        TextView identificador = (TextView) convertView.findViewById(R.id.codeRequest);
        TextView tag = (TextView) convertView.findViewById(R.id.tag_request);
        TextView destine = (TextView) convertView.findViewById(R.id.request_destine);
        TextView provider_service = (TextView) convertView.findViewById(R.id.provider_service_request_client);
        TextView type = (TextView) convertView.findViewById(R.id.type_request_package);
        TextView status = (TextView) convertView.findViewById(R.id.status_request);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_request_client);

        identificador.setText(String.valueOf(requestItem.id));
        tag.setText(requestItem.tags);
        destine.setText(requestItem.destination);
        if(requestItem.status==2) {
            LinearLayout layout_ps = (LinearLayout) convertView.findViewById(R.id.layout_ps_list_request);
            layout_ps.setVisibility(View.INVISIBLE);
        }
        else{
            if(requestItem.service!=null) {
                provider_service.setVisibility(View.VISIBLE);
                provider_service.setText(requestItem.service.useraccount.first_name);
            }else{
                LinearLayout layout_ps = (LinearLayout) convertView.findViewById(R.id.layout_ps_list_request);
                layout_ps.setVisibility(View.INVISIBLE);
            }
        }
        SharedPreferences sharedPref = getContext().getSharedPreferences("D-package", Context.MODE_PRIVATE);
        status.setText(sharedPref.getString("status_request_"+requestItem.status,"null"));
        type.setText(sharedPref.getString("type_shipment_"+requestItem.packagetype,"null"));

        if(requestItem.packagetype==1)
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
