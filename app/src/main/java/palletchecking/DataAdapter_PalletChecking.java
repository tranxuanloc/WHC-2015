package palletchecking;

import general.ReceivingOrders;

import java.util.ArrayList;
import java.util.HashMap;

import locationchecking.LocationChecking;
import scan_nhaphang.Scan_ReceivingOrders;
import scs.whc.R;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DataAdapter_PalletChecking extends BaseAdapter {
	TextView txtLocationNumber, txtCustomerNumber, txtProductNumber,
			txtProductName, txtCustomerRef, txtPalletID,
			txtReceivingOrderNumber, txtCurrentQuantity;
	TextView txtHeadGroup;
	TextView txtDateMovement,txtAuthorisedBy,txtReasonMovement,txtFromLocation,txtRef,txtName,txtToLocation ;
	private Context context;
	private ArrayList<HashMap<String, String>> ListPallet;
	HashMap<String, String> res;
	private ImageView ImgReason;

	public DataAdapter_PalletChecking(Context context,
			ArrayList<HashMap<String, String>> _ListPallet) {
		this.context = context;
		this.ListPallet = _ListPallet;
	}

	@Override
	public int getCount() {
		return ListPallet.size();
	}

	@Override
	public Object getItem(int position) {
		return ListPallet.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		res = ListPallet.get(position);
		int Stype = Integer.parseInt(res.get("Type").toString());
		switch (Stype) {
		case 1:
			convertView = View.inflate(context, R.layout.items_palletchecking,
					null);
//			final String SLocationID = res.get("LocationID");
			final String SLocationNumber = res.get("LocationNumber");
			String SCustomerNumber = res.get("CustomerNumber");
			String SProductNumber = res.get("ProductNumber");
			String SProductName = res.get("ProductName");
			String SCustomerRef = res.get("CustomerRef");
			String SPalletID = res.get("PalletID");
			final String SReceivingOrderNumber = res.get("ReceivingOrderNumber");
//			String SProductID = res.get("ProductID");
			String SCurrentQuantity = res.get("CurrentQuantity");
			final String SReceivingOrderID = res.get("ReceivingOrderID");
			
			txtLocationNumber = (TextView) convertView
					.findViewById(R.id.txtItemsPalletChecking_LocationNumber);
			txtCustomerNumber = (TextView) convertView
					.findViewById(R.id.txtItemsPalletChecking_CustomerNumber);
			txtProductNumber = (TextView) convertView
					.findViewById(R.id.txtItemsPalletChecking_ProductNumber);
			txtProductName = (TextView) convertView
					.findViewById(R.id.txtItemsPalletChecking_ProductName);
			txtCustomerRef = (TextView) convertView
					.findViewById(R.id.txtItemsPalletChecking_CustomerRef);
			txtPalletID = (TextView) convertView
					.findViewById(R.id.txtItemsPalletChecking_PalletID);
			txtReceivingOrderNumber = (TextView) convertView
					.findViewById(R.id.txtItemsPalletChecking_ReceivingOrderNumber);
			txtCurrentQuantity = (TextView) convertView
					.findViewById(R.id.txtItemsPalletChecking_CurrentQuantity);

			// txtLocationNumber.setText(SLocationNumber);
			txtCustomerNumber.setText(SCustomerNumber);
			txtProductNumber.setText(SProductNumber);
			txtProductName.setText(SProductName);
			txtCustomerRef.setText(SCustomerRef);
			txtPalletID.setText(SPalletID);
			txtCurrentQuantity.setText(SCurrentQuantity);

			SpannableString CReceivingOrderNumber = new SpannableString(
					SReceivingOrderNumber);
			CReceivingOrderNumber.setSpan(new UnderlineSpan(), 0,
					SReceivingOrderNumber.length(), 0);
			txtReceivingOrderNumber.setText(CReceivingOrderNumber);

			SpannableString CLocationNumber = new SpannableString(
					SLocationNumber);
			CLocationNumber.setSpan(new UnderlineSpan(), 0,
					SLocationNumber.length(), 0);
			txtLocationNumber.setText(CLocationNumber);
			
			txtReceivingOrderNumber.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent NewIntent;
					if (SReceivingOrderNumber.substring(0, 2).toString().equals("RO"))
					{
						NewIntent = new Intent(context,ReceivingOrders.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						NewIntent.putExtra("Intent_RO", SReceivingOrderID);
						 context.startActivity(NewIntent);
					}
					else
					{
						NewIntent = new Intent(context,Scan_ReceivingOrders.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						NewIntent.putExtra("OrderNumber", SReceivingOrderNumber);
						 context.startActivity(NewIntent);
					}
				}
			});
			
			txtLocationNumber.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					 Intent mintentPlt = new Intent(context,LocationChecking.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 mintentPlt.putExtra("Intent_LocationID", SLocationNumber);
					 context.startActivity(mintentPlt);
				}
			});
			
			
			
			
			
			break;
		case 2:
			String SHeadGroup = res.get("Tilte");
			convertView = View
					.inflate(context, R.layout.items_headgroup, null);
			txtHeadGroup = (TextView) convertView
					.findViewById(R.id.txtItemsHeadGroup);
			txtHeadGroup.setText(SHeadGroup);
			break;
		case 3:
			String SQuantity = res.get("Quantity");
			String SRemark = res.get("Remark");
			String SReasonMovement = res.get("ReasonMovement");
			String SFromLocation = res.get("LocationNumber");
			String SLocationTo = res.get("LocationTo");
			String SAuthorisedBy = res.get("AuthorisedBy");
			String SName = res.get("Name");
			String SDateMovement = res.get("DateMovement");
			convertView = View
					.inflate(context, R.layout.items_palletmovementhistory, null);
			
			txtDateMovement = (TextView) convertView.findViewById(R.id.txtItemsPalletMovementHistory_DateMovement);
			txtAuthorisedBy = (TextView) convertView.findViewById(R.id.txtItemsPalletMovementHistory_AuthorisedBy);
			txtReasonMovement = (TextView) convertView.findViewById(R.id.txtItemsPalletMovementHistory_ReasonMovement);
			txtFromLocation = (TextView) convertView.findViewById(R.id.txtItemsPalletMovementHistory_FromLocation);
			txtToLocation = (TextView) convertView.findViewById(R.id.txtItemsPalletMovementHistory_ToLocation);
			txtRef = (TextView) convertView.findViewById(R.id.txtItemsPalletMovementHistory_Ref);
			txtName = (TextView) convertView.findViewById(R.id.txtItemsPalletMovementHistory_Name);
			ImgReason = (ImageView) convertView.findViewById(R.id.ImgItemsPalletMovementHistory_IMGReason);
			if (SReasonMovement.equals("Moved")) {
				ImgReason.setImageResource(R.drawable.left_arrow24x24);
			} else {
				if (SReasonMovement.equals("Reversed")) {
					ImgReason.setImageResource(R.drawable.adept_update);
				}
				else
				{
					if  (SReasonMovement.equals("Joined")){
						ImgReason.setImageResource(R.drawable.left_24x24);
					}
					else
					{
						SReasonMovement = "Mass Movement";
						ImgReason.setImageResource(R.drawable.actionbar_back_indicator);
					}
				}
			}
			txtDateMovement.setText(SDateMovement);
			txtAuthorisedBy.setText(SAuthorisedBy);
			txtReasonMovement.setText(SReasonMovement);
			txtFromLocation.setText(SFromLocation);
			txtToLocation.setText(SLocationTo);
			txtRef.setText("Qty: " +SQuantity + " Ref: " + SRemark );
			txtName.setText(SName);
			
			break;
		default:
			break;
		}

		return convertView;
	}

	// End Class
}
