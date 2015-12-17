package scan_nhaphang;

import java.util.ArrayList;
import java.util.HashMap;

import locationchecking.LocationChecking;

import scs.whc.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DataAdapter_Scan_DSReceivingOrders extends BaseAdapter {
	TextView txtSCustomerNumber,txtSProductNumber,txtSRO,txtSPalletID,txtSCustomerRef,txtSCartonSize,txtSCartonDescription,txtSLocationNumber,txtUser;

private Context context;
private ArrayList<HashMap<String, String>> LV_DSReceivingOrders;
HashMap<String, String> res;

private RelativeLayout layoutContain;

public DataAdapter_Scan_DSReceivingOrders(Context context,
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
View view = View.inflate(context, R.layout.items_scan_dsreceivingorders, null);
String SPalletID = res.get("PalletID");
String SCustomerRef = res.get("CustomerRef");
String SCartonDescription = res.get("CartonDescription");
final String SLocationNumber = res.get("LocationNumber");
//String SDispatched = res.get("Dispatched");
String SCurrentQuantity = res.get("CurrentQuantity");

String SRO = res.get("RO");
String SCustomerNumber = res.get("CustomerNumber");
//String SDSReceivingOrderDate = res.get("DSReceivingOrderDate");
//String SCustomerName = res.get("CustomerName");
//String SCartonID = res.get("CartonID");
String SProductNumber = res.get("ProductNumber");
String SScannedTime = res.get("ScannedTime");
String SRecordFirst = res.get("RecordFirst");
String SUser = res.get("ScannedUser");
String SStatus = res.get("Status");
String SLocationID = res.get("LocationID");

txtSPalletID = (TextView) view.findViewById(R.id.txt_Items_ScanDSReceivingOrders_CartonNewID);
txtSCustomerRef = (TextView) view.findViewById(R.id.txt_Items_ScanDSReceivingOrders_CrowRefID);
txtSCartonSize = (TextView) view.findViewById(R.id.txt_Items_ScanDSReceivingOrders_Size);
txtSCartonDescription = (TextView) view.findViewById(R.id.txt_Items_ScanDSReceivingOrders_CartonDescription);
txtSLocationNumber = (TextView) view.findViewById(R.id.txt_Items_ScanDSReceivingOrders_LocationNumber);
txtSCustomerNumber = (TextView) view.findViewById(R.id.txt_Items_ScanDSReceivingOrders_CustomerNumber);
txtSProductNumber = (TextView) view.findViewById(R.id.txt_Items_ScanDSReceivingOrders_ProductNumber);
txtSRO = (TextView) view.findViewById(R.id.txt_Items_ScanDSReceivingOrders_ReceivingOrderNumber);
txtUser = (TextView) view.findViewById(R.id.txt_Items_ScanDSReceivingOrders_UserUpdate);
layoutContain = (RelativeLayout)view.findViewById(R.id.layout_Items_ScanDSReceivingOrders_layout);


txtSPalletID.setText(SPalletID);
txtSCustomerRef.setText(SCustomerRef);
txtSCartonSize.setText(SCurrentQuantity);
txtSCartonDescription.setText(SCartonDescription);
txtSCustomerNumber.setText(SCustomerNumber);
txtSProductNumber.setText(SProductNumber);
txtUser.setText(SUser  +SScannedTime);
//txtSLocationNumber.setText(SLocationNumber);

SpannableString SPSLocationNumber = new SpannableString(SLocationNumber);
SPSLocationNumber.setSpan(new UnderlineSpan(), 0,SLocationNumber.length(), 0);
txtSLocationNumber.setText(SPSLocationNumber);

SpannableString SPSRO = new SpannableString(SRO);
SPSRO.setSpan(new UnderlineSpan(), 0,SRO.length(), 0);
txtSRO.setText(SPSRO);

txtSLocationNumber.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent mintentPlt = new Intent(context,LocationChecking.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 mintentPlt.putExtra("Intent_LocationID", SLocationNumber);
		 context.startActivity(mintentPlt);
	}
} );



if (SRecordFirst.equals("1"))
{
	layoutContain.setBackgroundColor(Color.parseColor("#FFFF00"));
}
else
{
	if (SStatus.equals("1") && (!SLocationID.equals("1")))
	{
		layoutContain.setBackgroundColor(Color.parseColor("#CCFFCC"));
	}
	else
	{
		layoutContain.setBackgroundColor(Color.parseColor("#FFFFFF"));
	}
}


return view;
}

}
