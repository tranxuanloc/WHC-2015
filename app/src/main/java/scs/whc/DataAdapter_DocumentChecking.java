package scs.whc;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DataAdapter_DocumentChecking extends BaseAdapter {
	Context _Context;
	private ArrayList<HashMap<String, String>> _ListDocument;
	HashMap<String, String> res;
	private TextView txtLocationNumber,txtQtySetup,txtQtyPresent,txtRemark,txtDifferentCarton;
	private RelativeLayout layout;
	DataAdapter_DocumentChecking(Context PContext,ArrayList<HashMap<String, String>> PListDocument)
	{
		_Context = PContext;
		_ListDocument =PListDocument;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _ListDocument.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return _ListDocument.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		res = _ListDocument.get(position);
		String SLocationNumber = res.get("LocationNumber");
//		String SLocationID = res.get("LocationID");
		String SStandardCarton = res.get("StandardCarton");
		String STotalCarton = res.get("TotalCarton");
		String SDifferentCarton = res.get("DifferentCarton");
		String SRemark = res.get("Remark");
		
		convertView = View.inflate(_Context, R.layout.items_checkingdocument,null);
		txtLocationNumber = (TextView) convertView.findViewById(R.id.txt_Items_DocumentChecking_Location);
		txtQtySetup = (TextView) convertView.findViewById(R.id.txt_Items_DocumentChecking_QtySetup);
		txtQtyPresent = (TextView) convertView.findViewById(R.id.txt_Items_DocumentChecking_QtyPresent);
		txtRemark = (TextView) convertView.findViewById(R.id.txt_Items_DocumentChecking_Remark);
		txtDifferentCarton = (TextView) convertView.findViewById(R.id.txt_Items_DocumentChecking_Different);
		layout = (RelativeLayout)convertView.findViewById(R.id.layout_ItemsDocumentsChecking);
		
		txtLocationNumber.setText(SLocationNumber);
		txtQtySetup.setText(SStandardCarton);
		txtQtyPresent.setText(STotalCarton);
		txtRemark.setText(SRemark);
		txtDifferentCarton.setText(SDifferentCarton);
		int Status = Integer.parseInt(SDifferentCarton);
		if (Status == 0)
		{
			layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
		else
		{
			if (Status < 0)
			{
				layout.setBackgroundColor(Color.parseColor("#FFFF00"));
			}
			else
			{
				layout.setBackgroundColor(Color.parseColor("#CCFFCC"));
			}
		}
		
		return convertView;
	}

}
