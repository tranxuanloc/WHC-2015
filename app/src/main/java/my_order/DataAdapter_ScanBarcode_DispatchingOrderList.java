package my_order;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DataAdapter_ScanBarcode_DispatchingOrderList extends BaseAdapter {
	/**
	 * Declare
	 */
	private Context context;
	private ArrayList<HashMap<String, String>> ListDispatchingOderList;
	private HashMap<String, String> res;
	HashMap<String, Drawable> datacolumIMG = new HashMap<String, Drawable>();
	private TextView txtDispatchingOrderNumber,txtCustomerNumber,txtDispatchingOrderDate,txtSpecialRequirement,txtDispatchingOrderID,txtDockNumber,txtDateFull;
	private RelativeLayout LayoutDispatchingOrderList;

	
	public DataAdapter_ScanBarcode_DispatchingOrderList(Context context,
			ArrayList<HashMap<String, String>> _ListDispatchingOderList) {
		this.context = context;
		this.ListDispatchingOderList = _ListDispatchingOderList;
	}

	@Override
	public int getCount() {
		return ListDispatchingOderList.size();
	}

	@Override
	public HashMap<String, String> getItem(int position) {
		return ListDispatchingOderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		res = ListDispatchingOderList.get(position);
			View view = View.inflate(context, R.layout.items_dispatchingorder_list,
					null);
			String SDispatchingOrderID = res.get("DispatchingOrderID");
			String SDispatchingOrderNumber = res.get("DispatchingOrderNumber");
			String SCustomerNumber = res.get("CustomerNumber");
			String SDispatchingOrderDate = res.get("DispatchingOrderDate");
			String SSpecialRequirement = res.get("SpecialRequirement");
			String SDockNumber = res.get("DockNumber");
			String SCustomerName = res.get("CustomerName");
			String SScanStatus = res.get("ScanStatus");
			String SDateFull = res.get("DispatchingOrderDateFull");

			txtDispatchingOrderID =  (TextView) view.findViewById(R.id.txtItems_DispatchingOrderList_DispatchingOrderID);
			txtDispatchingOrderNumber = (TextView) view.findViewById(R.id.txtItems_DispatchingOrderList_OrderNumber);
			txtCustomerNumber = (TextView) view.findViewById(R.id.txtItems_DispatchingOrderList_CustomerNumber);		
			txtDispatchingOrderDate = (TextView) view.findViewById(R.id.txtItems_DispatchingOrderList_Date);		
			txtSpecialRequirement = (TextView) view.findViewById(R.id.txtItems_DispatchingOrderList_SpecialRequirement);		
			txtDockNumber = (TextView) view.findViewById(R.id.txtItems_DispatchingOrderList_DockNumber);
			txtDateFull = (TextView) view.findViewById(R.id.txtItems_DispatchingOrderList_DateFull);
			LayoutDispatchingOrderList = (RelativeLayout) view.findViewById(R.id.layout_DispatchingOrderList_Main);
			

			txtDispatchingOrderID.setText(SDispatchingOrderID);
			txtDispatchingOrderNumber.setText(SDispatchingOrderNumber);
			txtCustomerNumber.setText(SCustomerNumber +"  "+ SCustomerName);
			txtDispatchingOrderDate.setText(SDispatchingOrderDate);
			txtSpecialRequirement.setText(SSpecialRequirement);
			txtDockNumber.setText(SDockNumber);
			txtDateFull.setText(SDateFull);
			

			switch (SScanStatus) {
			case "0":
				LayoutDispatchingOrderList.setBackgroundColor(Color.parseColor("#FFFFFF"));
				break;
			case "1":
				LayoutDispatchingOrderList.setBackgroundColor(Color.parseColor("#CCFFCC"));
				break;
			default:
				LayoutDispatchingOrderList.setBackgroundColor(Color.parseColor("#FFFF66"));
				break;
			}
			

		return view;
	}

	// End Class
}
