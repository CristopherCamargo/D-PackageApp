package team.software.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;

import java.util.ArrayList;

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
        RequestPackageModel requestItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapter_request_inbox, parent, false);
        }
        TextView origin = (TextView) convertView.findViewById(R.id.origin_request_inbox_a);
        TextView destine = (TextView) convertView.findViewById(R.id.destine_request_inbox_a);
        TextView tag = (TextView) convertView.findViewById(R.id.tag_request_inbox_a);
        TextView price = (TextView) convertView.findViewById(R.id.cost_request_inbox_a);
        TextView type = (TextView) convertView.findViewById(R.id.text_type_request_ps);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_type_request_ps);

        origin.setText(requestItem.origin);
        destine.setText(requestItem.destination);
        tag.setText(requestItem.tags);
        DecimalFormat precision = new DecimalFormat("0.0");
        price.setText(precision.format(requestItem.price)+" "+R.string.currency_symbol);
        SharedPreferences sharedPref = context.getSharedPreferences("D-package", Context.MODE_PRIVATE);
        type.setText(sharedPref.getString("type_shipment_"+requestItem.packagetype,"null"));

        if(requestItem.packagetype==1)
            image.setImageResource(R.mipmap.ic_express_request);
        else
            image.setImageResource(R.mipmap.ic_standard_request);

        return convertView;
    }
}
