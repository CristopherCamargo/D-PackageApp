package team.software.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import team.software.d_packageapp.R;
import team.software.models.RequestPackageModel;

public class AdapterRequestInbox extends ArrayAdapter<RequestPackageModel> {

    private Context context;

    public AdapterRequestInbox(Context context, ArrayList<RequestPackageModel> requests) {
        super(context,0,requests);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RequestPackageModel requestItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter_request_inbox, parent, false);
        }
        TextView client = (TextView) convertView.findViewById(R.id.origin_request_inbox_a);
        TextView destine = (TextView) convertView.findViewById(R.id.destine_request_inbox_a);
        TextView status = (TextView) convertView.findViewById(R.id.tag_request_inbox_a);
        TextView type = (TextView) convertView.findViewById(R.id.text_type_request_ps);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_type_request_ps);

        client.setText(requestItem.client.useraccount.first_name);
        destine.setText(requestItem.destination);
        SharedPreferences sharedPref = context.getSharedPreferences("D-package", Context.MODE_PRIVATE);
        status.setText(sharedPref.getString("status_request_"+requestItem.status,"null"));
        type.setText(sharedPref.getString("type_shipment_"+requestItem.packagetype,"null"));

        if(requestItem.packagetype==1)
            image.setImageResource(R.mipmap.ic_express_request);
        else
            image.setImageResource(R.mipmap.ic_standard_request);

        return convertView;
    }
}
