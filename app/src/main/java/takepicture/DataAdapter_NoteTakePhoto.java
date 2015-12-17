package takepicture;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter_NoteTakePhoto extends BaseAdapter {
	
	private Context context;
	private ArrayList<HashMap<String, String>> ListNoteTakePhoto;
	private HashMap<String, String> res;
	TextView txtDispatchingOrderNumber,txtDispatchingOrderDate,txtCustomerNumber,txtCustomerName,txtSpecialRequirement,txtDispatchingOrderID;
	
	public DataAdapter_NoteTakePhoto(Context context,
			ArrayList<HashMap<String, String>> _ListNoteTakePhoto) {
		this.context = context;
		this.ListNoteTakePhoto = _ListNoteTakePhoto;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ListNoteTakePhoto.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ListNoteTakePhoto.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		res = ListNoteTakePhoto.get(position);
		View view = View.inflate(context, R.layout.items_notetakephoto_dispatching, null);
		String SCustomerNumber = res.get("CustomerNumber");
		String SCustomerName = res.get("CustomerName");
		String SDispatchingOrderNumber = res.get("DispatchingOrderNumber");
		String SSpecialRequirement = res.get("SpecialRequirement");
//		String SDockNumber = res.get("DockNumber");
		String SDispatchingOrderDate = res.get("DispatchingOrderDate");
		String SDispatchingOrderID = res.get("DispatchingOrderID");
		
			txtCustomerNumber = (TextView) view.findViewById(R.id.txtitems_notetakephoto_dispatching_CustomerNumber);
			txtCustomerName = (TextView) view.findViewById(R.id.txtitems_notetakephoto_dispatching_CustomerName);
			txtSpecialRequirement = (TextView) view.findViewById(R.id.txtitems_notetakephoto_dispatching_SpecialRequirement);
			txtDispatchingOrderNumber = (TextView) view.findViewById(R.id.txtitems_notetakephoto_dispatching_DispatchingOrderNumber);
			txtDispatchingOrderDate = (TextView) view.findViewById(R.id.txtitems_notetakephoto_dispatching_DispatchingOrderDate);
			txtCustomerName = (TextView) view.findViewById(R.id.txtitems_notetakephoto_dispatching_CustomerName);
			txtDispatchingOrderID = (TextView) view.findViewById(R.id.txtitems_notetakephoto_dispatching_DispatchingOrderID);
			
			
			txtCustomerNumber.setText(SCustomerNumber);
			txtCustomerName.setText(SCustomerName);
			txtSpecialRequirement.setText(SSpecialRequirement);
			txtDispatchingOrderNumber.setText(SDispatchingOrderNumber);
			txtDispatchingOrderDate.setText(SDispatchingOrderDate);
			txtCustomerName.setText(SCustomerName);
			txtDispatchingOrderID.setText(SDispatchingOrderID);
			
		
		return view;
	}

}
