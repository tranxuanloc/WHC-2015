package scan_do;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adap extends BaseAdapter {
	Context context;
	ArrayList<HashMap<String, String>> listsanpham;
	HashMap<String, String> res;

	public Adap(Context context, ArrayList<HashMap<String, String>> listsanpham) {
		this.context = context;
		this.listsanpham = listsanpham;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listsanpham.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub

		return 0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		res = listsanpham.get(position);
		View view = View.inflate(context, R.layout.items_scan_palletdetails, null);

		String A1 = res.get("A");
		String B1 = res.get("B");
		String C1 = res.get("C");
		String D1 = res.get("D");
		String E1 = res.get("E");
		String F1 = res.get("F");

		TextView txtLocationNumber = (TextView) view.findViewById(R.id.txt_LocationNumber);
		txtLocationNumber.setText(A1);
		TextView txtProductNumber = (TextView) view.findViewById(R.id.txt_ProductNumber);
		txtProductNumber.setText(B1);
		TextView txtProductName = (TextView) view.findViewById(R.id.txt_ProductName);
		txtProductName.setText(C1);
		TextView txtCustomerNumber = (TextView) view.findViewById(R.id.txt_CustomerNumber);
		txtCustomerNumber.setText(D1);
		TextView txtCurrentQuantity = (TextView) view.findViewById(R.id.txt_CurrentQuantity);
		txtCurrentQuantity.setText(E1);
		TextView txtRO = (TextView) view.findViewById(R.id.txt_RO);
		txtRO.setText(F1);

		if (A1.equals(" N-70-08-1-1")) {
			txtLocationNumber.setTextColor(Color.RED);
			txtLocationNumber.setBackgroundColor(Color.BLUE);
		}


		return view;
	}
}
