package general;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DataAdapter_DriverMovementChecking extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, String>> ListDriverMovementChecking;
	HashMap<String, String> res;
	TextView txtDateMovement,txtPalletID,txtAuthorisedBy,txtProductName,txtReasonMovement,txtFromLocation,txtToLocation,txtProductNumber,txtReceivingOrderNumber;
	private ImageView ImgReason;
	public DataAdapter_DriverMovementChecking(Context context,
			ArrayList<HashMap<String, String>> _ListDriverMovementChecking) {
		this.context = context;
		this.ListDriverMovementChecking = _ListDriverMovementChecking;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ListDriverMovementChecking.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ListDriverMovementChecking.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		res = ListDriverMovementChecking.get(position);

		View view = View.inflate(context, R.layout.items_drivermovementchecking,
				null);
		
		
//		String SDateMovement = res.get("DateMovement");
		String SPalletID = res.get("PalletID");
		String SAuthorisedBy = res.get("AuthorisedBy");
		String SReasonMovement = res.get("ReasonMovement");
		String SFromLocation = res.get("FromLocation");
		String SToLocation = res.get("ToLocation");
		String SProductNumber = res.get("ProductNumber");
		String SProductName = res.get("ProductName");
//		String SProductID = res.get("ProductID");
		String SReceivingOrderNumber = res.get("ReceivingOrderNumber");


		
//		txtDateMovement = (TextView) view.findViewById(R.id.txtItemsDriverMovementChecking_DateMovement);
		txtPalletID = (TextView) view.findViewById(R.id.txtItemsDriverMovementChecking_PalletID);
		txtAuthorisedBy = (TextView) view.findViewById(R.id.txtItemsDriverMovementChecking_AuthorisedBy);
		txtReasonMovement = (TextView) view.findViewById(R.id.txtItemsDriverMovementChecking_ReasonMovement);
		txtFromLocation = (TextView) view.findViewById(R.id.txtItemsDriverMovementChecking_FromLocation);
		txtToLocation = (TextView) view.findViewById(R.id.txtItemsDriverMovementChecking_ToLocation);
		txtProductNumber = (TextView) view.findViewById(R.id.txtItemsDriverMovementChecking_ProductNumber);
		txtReceivingOrderNumber = (TextView) view.findViewById(R.id.txtItemsDriverMovementChecking_ReceivingOrderNumber);
		txtProductName = (TextView) view.findViewById(R.id.txtItemsDriverMovementChecking_ProductName);
		ImgReason = (ImageView) view.findViewById(R.id.imgItemsDriverMovementChecking_ImgReason);
			if (SReasonMovement.equals("Moved")) {
				ImgReason.setImageResource(R.drawable.left_arrow24x24);
			} else {
				if (SReasonMovement.equals("Reversed")) {
					ImgReason.setImageResource(R.drawable.adept_update);
				}
				else
				{
					ImgReason.setImageResource(R.drawable.left_24x24);
				}
			}
		
//		txtDateMovement.setText(SDateMovement);
		txtPalletID.setText("Plt:"+ SPalletID);
		txtAuthorisedBy.setText(SAuthorisedBy);
		txtReasonMovement.setText(SReasonMovement);
		txtFromLocation.setText(SFromLocation);
		txtToLocation.setText(SToLocation);
		txtProductNumber.setText(SProductNumber);
		txtReceivingOrderNumber.setText(SReceivingOrderNumber);
		txtProductName.setText(SProductName);
		return view;
	}

//	End Class
}
