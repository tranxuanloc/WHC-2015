package locationchecking;

import general.ReceivingOrders;

import java.util.ArrayList;
import java.util.HashMap;

import palletchecking.PalletChecking;
import scan_nhaphang.Scan_ReceivingOrders;
import scs.whc.R;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter_LocationChecking extends BaseAdapter {
	TextView txtLocationNumber, txtCustomerNumber, txtProductNumber,
			txtProductName, txtCustomerRef, txtPalletID,
			txtReceivingOrderNumber, txtCurrentQuantity;

	private Context context;
	private ArrayList<HashMap<String, String>> ListLocation;
	HashMap<String, String> res;

	public DataAdapter_LocationChecking(Context context,
			ArrayList<HashMap<String, String>> _ListLocation) {
		this.context = context;
		this.ListLocation = _ListLocation;
	}

	@Override
	public int getCount() {
		return ListLocation.size();
	}

	@Override
	public Object getItem(int position) {
		return ListLocation.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		res = ListLocation.get(position);
		View view = View
				.inflate(context, R.layout.items_locationchecking, null);
		String SLocationNumber = res.get("LocationNumber");
		String SCustomerNumber = res.get("CustomerNumber");
		String SProductNumber = res.get("ProductNumber");
		String SProductName = res.get("ProductName");
		String SCustomerRef = res.get("CustomerRef");
		final String SPalletID = res.get("PalletID");
		final String SReceivingOrderNumber = res.get("ReceivingOrderNumber");
		String SCurrentQuantity = res.get("CurrentQuantity");
		final String SReceivingOrderID = res.get("ReceivingOrderID");
		String SProductionDate = res.get("ProductionDate");
		String SUseByDate = res.get("UseByDate");

		txtLocationNumber = (TextView) view
				.findViewById(R.id.txtItemsLocationchecking_LocationID);
		txtCustomerNumber = (TextView) view
				.findViewById(R.id.txtItemsLocationchecking_CustomerNumber);
		txtProductNumber = (TextView) view
				.findViewById(R.id.txtItemsLocationchecking_ProductNumber);
		txtProductName = (TextView) view
				.findViewById(R.id.txtItemsLocationchecking_ProductName);
		txtCustomerRef = (TextView) view
				.findViewById(R.id.txtItemsLocationchecking_CustomerRef);
		txtPalletID = (TextView) view
				.findViewById(R.id.txtItemsLocationchecking_PalletID);
		txtReceivingOrderNumber = (TextView) view
				.findViewById(R.id.txtItemsLocationchecking_ReceivingOrderNumber);
		txtCurrentQuantity = (TextView) view
				.findViewById(R.id.txtItemsLocationchecking_CurrentQuantity);

		txtLocationNumber.setText(SLocationNumber);
		txtCustomerNumber.setText(SCustomerNumber);
		txtProductNumber.setText(SProductNumber);
		txtProductName.setText(SProductName);
		txtCustomerRef.setText(SCustomerRef + SProductionDate + SUseByDate);
//		txtPalletID.setText("Plt:" + SPalletID);
//		txtReceivingOrderNumber.setText(SReceivingOrderNumber);
		txtCurrentQuantity.setText(SCurrentQuantity);
		String SPalletNumber = "Plt:";
		if (SReceivingOrderNumber.substring(0, 2).equals("RD"))
		{
			SPalletNumber = "Ctn:";
		}
		SpannableString Span_txtPalletID = new SpannableString(
				SPalletNumber + SPalletID);
		Span_txtPalletID.setSpan(new UnderlineSpan(), 0,
				SPalletID.length()+ 4, 0);
		txtPalletID.setText(Span_txtPalletID);
		
		SpannableString Span_txtReceivingOrderNumber = new SpannableString(
				SReceivingOrderNumber);
		Span_txtReceivingOrderNumber.setSpan(new UnderlineSpan(), 0,
				SReceivingOrderNumber.length(), 0);
		txtReceivingOrderNumber.setText(Span_txtReceivingOrderNumber);
		
		
		
		txtReceivingOrderNumber.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent _NewItenRO;
				if (SReceivingOrderNumber.substring(0, 2).equals("RD"))
				{
					_NewItenRO = new Intent(context,Scan_ReceivingOrders.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					_NewItenRO.putExtra("OrderNumber", SReceivingOrderNumber);
					 context.startActivity(_NewItenRO);
				}
				else
				{
					_NewItenRO = new Intent(context,ReceivingOrders.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					_NewItenRO.putExtra("Intent_RO", SReceivingOrderID);
					 context.startActivity(_NewItenRO);
				}
			}
		});
		
		txtPalletID.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (SReceivingOrderNumber.substring(0, 2).equals("RD"))
				{
					 Intent mintentPlt = new Intent(context,PalletChecking.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 mintentPlt.putExtra("Intent_PalletID","CT"+ SPalletID);
					 context.startActivity(mintentPlt);
				}
				else
				{
					 Intent mintentPlt = new Intent(context,PalletChecking.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 mintentPlt.putExtra("Intent_PalletID","PI"+ SPalletID);
					 context.startActivity(mintentPlt);
				}
				
				
				
				
			}
		});
		return view;
	}

}
