package freelocation;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter_FreeLocation extends BaseAdapter {
TextView txt_RoomID,txtFreeQty,txtPltQty,txtVL,txtL,txtVH,txtH,txtWF;
private Context context;
private ArrayList<HashMap<String, String>> ListFreeLocation;
HashMap<String, String> res;

public DataAdapter_FreeLocation(Context context,
	ArrayList<HashMap<String, String>> _ListFreeLocation) {
this.context = context;
this.ListFreeLocation = _ListFreeLocation;
}

@Override
public int getCount() {
return ListFreeLocation.size();
}

@Override
public Object getItem(int position) {
return ListFreeLocation.size();
}

@Override
public long getItemId(int position) {
return 0;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
res = ListFreeLocation.get(position);

	convertView = View.inflate(context, R.layout.items_freelocation,
			null);
//	final String SLocationID = res.get("LocationID");
	String SRoomID = res.get("RoomID");
//	String SQtyLocation = res.get("QtyLocation");
//	String SQtyLow = res.get("QtyLow");
//	String SQtyHigh = res.get("QtyHigh");
	String SQtyOfFree = res.get("QtyOfFree");
	String SQtyFreeAfterDP = res.get("QtyFreeAfterDP");
//	String SUpdateTime = res.get("UpdateTime");
	String SQtyFree_Low = res.get("QtyFree_Low");
//	String SQtyStandards = res.get("QtyStandards");
	String SQtyOfPallets_OnHand = res.get("QtyOfPallets_OnHand");
	String SQtyFree_VeryLow = res.get("QtyFree_VeryLow");
	String SQtyFree_VeryHigh = res.get("QtyFree_VeryHigh");
	String SQtyFree_High = res.get("QtyFree_High");
	
	
	txt_RoomID = (TextView) convertView.findViewById(R.id.txt_Items_FreeLocation_RoomID);
	txtFreeQty = (TextView) convertView.findViewById(R.id.txt_Items_FreeLocation_FreeQty);
	txtPltQty = (TextView) convertView.findViewById(R.id.txt_Items_FreeLocation_PaletQty);
	txtVL = (TextView) convertView.findViewById(R.id.txt_Items_FreeLocation_VL);
	txtL = (TextView) convertView.findViewById(R.id.txt_Items_FreeLocation_L);
	txtVH = (TextView) convertView.findViewById(R.id.txt_Items_FreeLocation_VH);
	txtH = (TextView) convertView.findViewById(R.id.txt_Items_FreeLocation_H);
	txtWF = (TextView) convertView.findViewById(R.id.txt_Items_FreeLocation_WF);

	txt_RoomID.setText(SRoomID);
	txtFreeQty.setText("F-"+SQtyOfFree);
	txtVL.setText("VL-"+SQtyFree_VeryLow);
	txtL.setText("L-"+SQtyFree_Low);
	txtVH.setText("VH-"+SQtyFree_VeryHigh);
	txtH.setText("H-"+SQtyFree_High);
	txtWF.setText("WF-"+SQtyFreeAfterDP);
	if (SRoomID.equals("L"))
	{
		txtPltQty.setText("Ctn:"+SQtyOfPallets_OnHand);
	}
	else
	{
		txtPltQty.setText("Plt:"+SQtyOfPallets_OnHand);
	}

	

return convertView;
}

// End Class
}
