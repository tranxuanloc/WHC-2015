package scs.whc;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter_WorkerEmployeeID_Input extends BaseAdapter {
	/**
	 * Declare
	 */
	private Context context;
	private ArrayList<HashMap<String, String>> _ListWorkerEmployee;
	private HashMap<String, String> res;
	HashMap<String, Drawable> datacolumIMG = new HashMap<String, Drawable>();
	private TextView 		txtSEmployeeID, txtSPercentage, txtSRemark, txtSEmployeeName, txtSProductionQuantity;

	
	public DataAdapter_WorkerEmployeeID_Input(Context context,
			ArrayList<HashMap<String, String>> PListWorkerEmployee) {
		this.context = context;
		this._ListWorkerEmployee = PListWorkerEmployee;
	}

	@Override
	public int getCount() {
		return _ListWorkerEmployee.size();
	}

	@Override
	public HashMap<String, String> getItem(int position) {
		return _ListWorkerEmployee.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		res = _ListWorkerEmployee.get(position);
			View view = View.inflate(context, R.layout.items_workeremployeeinput,null);
		
			String SEmployeeID = res.get("EmployeeID");
			String SPercentage = res.get("Percentage");
			@SuppressWarnings("unused")
			String SOrderNumber = res.get("OrderNumber");
			String SRemark = res.get("Remark");
			String SEmployeeName = res.get("EmployeeName");
			String SProductionQuantity = res.get("ProductionQuantity");
	
			txtSEmployeeID =  (TextView) view.findViewById(R.id.txt_ItemsWorkerEmployeeInput_ID);
			txtSPercentage = (TextView) view.findViewById(R.id.txt_ItemsWorkerEmployeeInput_Percent);
			txtSRemark = (TextView) view.findViewById(R.id.txt_ItemsWorkerEmployeeInput_Remark);		
			txtSEmployeeName = (TextView) view.findViewById(R.id.txt_ItemsWorkerEmployeeInput_Name);		
			txtSProductionQuantity = (TextView) view.findViewById(R.id.txt_ItemsWorkerEmployeeInput_Qty);		
			
			txtSEmployeeID.setText(SEmployeeID);
			txtSPercentage.setText(SPercentage);
			txtSRemark.setText(SRemark);
			txtSEmployeeName.setText(SEmployeeName);
			txtSProductionQuantity.setText(SProductionQuantity);
			
		return view;
	}

	// End Class
}
