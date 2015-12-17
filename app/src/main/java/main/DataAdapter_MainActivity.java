package main;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter_MainActivity extends BaseAdapter {
	Context context;
	ArrayList<HashMap<String, String>> PListMenu;
	HashMap<String, String> res;
	private TextView txtItem;

	public DataAdapter_MainActivity(Context context, ArrayList<HashMap<String, String>> _ListMenu) {
		this.context = context;
		this.PListMenu = _ListMenu;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PListMenu.size();
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
		res = PListMenu.get(position);
		View view = View.inflate(context, R.layout.items_main, null);

		String SItem = res.get("Item");
		txtItem = (TextView) view.findViewById(R.id.txtActivetiMain_Text);
		txtItem.setText(SItem);

		return view;
	}
}
