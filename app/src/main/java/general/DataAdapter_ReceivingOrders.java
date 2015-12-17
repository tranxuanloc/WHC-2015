package general;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter_ReceivingOrders extends BaseAdapter {
	
	private Context context;
	private ArrayList<HashMap<String, String>> ListReceivingOrders;
	private HashMap<String, String> res;
	TextView txtProductNumber,txtTotalPackages,txtProductName,txtProductionDate,txtCustomerRef;
	TextView txtReceivingOrderNumber,txtReceivingOrderDate,txtCustomerNumber,txtCustomerName,txtSpecialRequirement,txtSumQty;
	
	public DataAdapter_ReceivingOrders(Context context,
			ArrayList<HashMap<String, String>> _ListReceivingOrders) {
		this.context = context;
		this.ListReceivingOrders = _ListReceivingOrders;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ListReceivingOrders.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ListReceivingOrders.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		res = ListReceivingOrders.get(position);

		
//		String SDateMovement = res.get("DateMovement");
//		String SProductID = res.get("ProductID");
		String SProductNumber = res.get("ProductNumber");
		String SProductName = res.get("ProductName");
		String SCustomerRef = res.get("CustomerRef");
		String SRemark = res.get("Remark");
		String SReceivingOrderNumber = res.get("ReceivingOrderNumber");
		String SCustomerNumber = res.get("CustomerNumber");
		String SCustomerName = res.get("CustomerName");
		String SSpecialRequirement = res.get("SpecialRequirement");
		String SSumTotalPackages = res.get("SumTotalPackages");
		String SReceivingOrderDate = res.get("ReceivingOrderDate");
		String STotalPackages = res.get("TotalPackages");
//		String SProductionDate = res.get("ProductionDate");
		String SUseByDate = res.get("UseByDate");
//		String STypeColum = res.get("TypeColum");
	int TypeColum = Integer.parseInt( res.get("TypeColum").toString());
		switch (TypeColum) {
		case 1:
			convertView = View.inflate(context, R.layout.items_receivingorders,null);
			txtProductNumber = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrders_ProductNumber);
			txtTotalPackages = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrders_TotalPackages);
			txtProductName = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrders_ProductName);
			txtProductionDate = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrders_ProductionDate);
			txtCustomerRef = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrders_CustomerRef);

			txtProductNumber.setText( SProductNumber);
			txtTotalPackages.setText( STotalPackages);
			txtProductName.setText( SProductName);
			txtProductionDate.setText("NSX:" + SProductNumber +"~HSD: "+ SUseByDate );
			txtCustomerRef.setText("Ref: " + SCustomerRef +"~Remark: "+ SRemark);
			break;
		case 2:
			
			convertView = View.inflate(context, R.layout.items_receivingorders_group,null);
			txtReceivingOrderNumber = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrdersGroup_ReceivingOrderNumber);
			txtReceivingOrderDate = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrdersGroup_ReceivingOrderDate);
			txtCustomerNumber = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrdersGroup_CustomerNumber);
			txtCustomerName = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrdersGroup_CustomerName);
			txtSpecialRequirement = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrdersGroup_SpecialRequirement);
			txtSumQty = (TextView) convertView.findViewById(R.id.txtItemsReceivingOrdersGroup_SumQty);
			
			txtReceivingOrderNumber.setText(SReceivingOrderNumber);
			txtReceivingOrderDate.setText(SReceivingOrderDate);
			txtCustomerNumber.setText(SCustomerNumber);
			txtCustomerName.setText(SCustomerName);
			txtSpecialRequirement.setText(SSpecialRequirement);
			txtSumQty.setText(SSumTotalPackages);
			break;
		default:
			break;
		}
		
		
		
		return convertView;
	}

}
