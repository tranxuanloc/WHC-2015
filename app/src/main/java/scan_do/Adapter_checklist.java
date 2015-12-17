package scan_do;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class Adapter_checklist extends BaseAdapter {
	Context context;
	ArrayList<HashMap<String, String>> listsanpham;
	HashMap<String, String> res;

	public Adapter_checklist(Context context,
			ArrayList<HashMap<String, String>> listsanpham) {
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
		final View view = View.inflate(context, R.layout.do_details, null);
		res = listsanpham.get(position);
		String AA = res.get("AA");
		String BB = res.get("BB");
		String CC = res.get("CC");
		String DD = res.get("DD");
		String EE = res.get("EE");
		
		String GG = res.get("GG");

		TextView PalletID = (TextView) view.findViewById(R.id.txt_PalletID);
		PalletID.setText(AA);
		TextView ProductNumber1 = (TextView) view
				.findViewById(R.id.txt_ProductNumber1);
		ProductNumber1.setText(BB);
		TextView ProductName1 = (TextView) view
				.findViewById(R.id.txt_ProductName1);
		ProductName1.setText(CC);
		TextView Quantity = (TextView) view.findViewById(R.id.txt_Quantity);
		Quantity.setText(DD);
		TextView Result = (TextView) view.findViewById(R.id.txt_Result1);
		Result.setText(EE);

		if (EE.equals("OK") && GG.equals("1")) {

			LinearLayout row = (LinearLayout) view.findViewById(R.id.row1);
			row.setBackgroundColor(Color.YELLOW);
			 final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 40);
		     tg.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP);
		     
			// Handler handler = new Handler();
			// handler.postDelayed(new Runnable() {
			//
			// @Override
			// public void run() {
			// // TODO Auto-generated method stub
			// LinearLayout row = (LinearLayout) view
			// .findViewById(R.id.row1);
			// row.setBackgroundColor(Color.GREEN);
			// }
			// }, 20000);

		} else if (EE.equals("OK")) {
			LinearLayout row = (LinearLayout) view.findViewById(R.id.row1);
			row.setBackgroundColor(Color.GREEN);

		} else {
			if (EE.equals("NO")) {
				LinearLayout row = (LinearLayout) view.findViewById(R.id.row1);
				row.setBackgroundColor(Color.RED);
			} else {
				LinearLayout row = (LinearLayout) view.findViewById(R.id.row1);
				row.setBackgroundColor(Color.WHITE);
			}
		}

		return view;
	}
}
