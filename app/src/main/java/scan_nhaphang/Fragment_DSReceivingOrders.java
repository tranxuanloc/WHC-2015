package scan_nhaphang;

import general.java.PullToRefreshListView;

import java.sql.Connection;

import scs.whc.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class Fragment_DSReceivingOrders extends Fragment {
	Activity _activity;
	Context _Context;
	Connection Mycon;
	private PullToRefreshListView LV_DSReceivingOrders;
	private ViewPager viewPager;
	private TextView txtDSReceivingOrdersRD;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		_activity = activity;
		super.onAttach(activity);
		
	}
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.layout_frm_dsreceivingrders, container, false);
		_Context = container.getContext();
		LV_DSReceivingOrders = (PullToRefreshListView) rootView.findViewById(R.id.LV_DSReceivingOrders);
		txtDSReceivingOrdersRD = (TextView)_activity.findViewById(R.id.txtDSReceivingOrders_RD);
		viewPager = (ViewPager) container.findViewById(R.id.pagerScan_DSReceivingOrders);
		LoadListViewRD_AsyncTask LoadListview = new LoadListViewRD_AsyncTask(_activity, LV_DSReceivingOrders);
		LoadListview.execute("Null");
		LV_DSReceivingOrders.setOnItemClickListener(new OnItemClickListener() {
			private TextView txtDSReceivingOrderNumber;
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				txtDSReceivingOrderNumber = (TextView) view.findViewById(R.id.txt_ItemsDSReceivingOrders_DSReceivingOrderNumber);
				txtDSReceivingOrdersRD.setText(txtDSReceivingOrderNumber.getText().toString().trim());
				viewPager.setCurrentItem(1);
				Fragment_Scan_DSReceivingOrders frameScan = new Fragment_Scan_DSReceivingOrders();
				frameScan.onAttach(_activity);
				frameScan.onCreateView(inflater, container, savedInstanceState);
			}
			
		});
		return rootView;
	}
}
