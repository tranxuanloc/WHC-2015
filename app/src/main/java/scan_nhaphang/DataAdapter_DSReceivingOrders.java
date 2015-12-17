package scan_nhaphang;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter_DSReceivingOrders extends BaseAdapter {
	TextView txtDSReceivingOrderNumber,txtDSReceivingOrders_Date,txtDSCustomerNumber,txtTotal,txtDSSpecialRequirement,txtDate;

private Context context;
private ArrayList<HashMap<String, String>> LV_DSReceivingOrders;
HashMap<String, String> res;

public DataAdapter_DSReceivingOrders(Context context,
	ArrayList<HashMap<String, String>> _ListLocation) {
this.context = context;
this.LV_DSReceivingOrders = _ListLocation;
}

@Override
public int getCount() {
return LV_DSReceivingOrders.size();
}

@Override
public Object getItem(int position) {
return LV_DSReceivingOrders.size();
}

@Override
public long getItemId(int position) {
return 0;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
res = LV_DSReceivingOrders.get(position);
View view = View.inflate(context, R.layout.items_dsreceivingorders, null);

//String SDSReceivingOrderID = res.get("DSReceivingOrderID");
String SDSReceivingOrderNumber = res.get("DSReceivingOrderNumber");
String SDSCustomerNumber = res.get("DSCustomerNumber");
//String SDSCustomerID = res.get("DSCustomerID");
String SDSCustomerName = res.get("DSCustomerName");
String STotalCarton = res.get("TotalCarton");
String SDSSpecialRequirement = res.get("DSSpecialRequirement");
String SDSReceivingOrderDate = res.get("DSReceivingOrderDate");



txtDSReceivingOrderNumber = (TextView) view.findViewById(R.id.txt_ItemsDSReceivingOrders_DSReceivingOrderNumber);
txtDSReceivingOrders_Date = (TextView) view.findViewById(R.id.txt_ItemsDSReceivingOrders_Date);
txtDSCustomerNumber = (TextView) view.findViewById(R.id.txt_ItemsDSReceivingOrders_DSCustomerNumber);
txtTotal= (TextView) view.findViewById(R.id.txt_ItemsDSReceivingOrders_Total);
txtDSSpecialRequirement= (TextView) view.findViewById(R.id.txt_ItemsDSReceivingOrders_DSSpecialRequirement);


txtDSReceivingOrderNumber.setText(SDSReceivingOrderNumber);
txtDSReceivingOrders_Date.setText(SDSReceivingOrderDate);
txtDSCustomerNumber.setText(SDSCustomerNumber + "  " + SDSCustomerName);
txtTotal.setText(STotalCarton + "Ctn");
txtDSSpecialRequirement.setText(SDSSpecialRequirement);

return view;
}

}
