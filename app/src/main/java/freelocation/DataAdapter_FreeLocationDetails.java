package freelocation;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter_FreeLocationDetails extends BaseAdapter {
	Context _Context;
	private ArrayList<HashMap<String, String>> _ListFreeLocationDetails;
	HashMap<String, String> res;
	private TextView txtLocationNumber;
	DataAdapter_FreeLocationDetails(Context PContext,ArrayList<HashMap<String, String>> PListFreeLocationDetails)
	{
		_Context = PContext;
		_ListFreeLocationDetails =PListFreeLocationDetails;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _ListFreeLocationDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return _ListFreeLocationDetails.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		res = _ListFreeLocationDetails.get(position);
		String SLocationNumber = res.get("LocationNumber");
		
		convertView = View.inflate(_Context, R.layout.items_freelocation_details,
				null);
		txtLocationNumber = (TextView) convertView.findViewById(R.id.txt_ItemsFreeLocationDetails_LocationNumber);
		txtLocationNumber.setText(SLocationNumber);
		
		
		
		return convertView;
	}

}
