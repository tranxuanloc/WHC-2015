package general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import scs.whc.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DataAdapter_EmployeePerformance extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, String>> ListEmployeePerformance = new ArrayList<HashMap<String, String>>();
	private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	HashMap<String, String> res;
	private TextView txtOrderDate, txtOrderNumber, txtTOTAL, txtTime;
	@SuppressWarnings("unused")
	private LinearLayout LoutHeadDay;
	@SuppressWarnings("unused")
	private LinearLayout LoutDetail;
	String pDate = "";
	int _position = 0;
	@SuppressWarnings("unused")
	private LayoutInflater mInflater;
	public DataAdapter_EmployeePerformance(Context context,ArrayList<HashMap<String, String>> _ListEmployeePerformance) {
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.ListEmployeePerformance = _ListEmployeePerformance;
	}

	
	public void addItem(final HashMap<String, String> item) {
		ListEmployeePerformance.add(item);
		notifyDataSetChanged();
	}

	public void addSectionHeaderItem(final HashMap<String, String> item) {
		sectionHeader.add(ListEmployeePerformance.size());
		ListEmployeePerformance.add(item);
		notifyDataSetChanged();
	}
	
	
	@Override
	public int getItemViewType(int position) {
		return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}
	@Override
	public int getCount() {
		return ListEmployeePerformance.size();
	}

	@Override
	public Object getItem(int position) {
		return ListEmployeePerformance.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		res = ListEmployeePerformance.get(position);
//		ViewHolder holder = null;
//		int rowType = getItemViewType(position);
//		
//		if (convertView ==null){
//			holder = new ViewHolder();
//			switch (rowType) {
//			case TYPE_ITEM:
//				String SOrderDate = res.get("OrderDate");
//				String SOrderNumber = res.get("OrderNumber");
//				String STOTAL = res.get("TOTAL");
//				String SStartTime = res.get("StartTime");
//				String SEndTime = res.get("EndTime");
//				
//				
//				convertView = mInflater.inflate(R.layout.items_employeeperformance, null);
//				holder.HoldertxtOrderDate = (TextView) convertView
//						.findViewById(R.id.txtItemsEmployeePerformance_Date);
//				holder.HoldertxtOrderNumber = (TextView) convertView
//						.findViewById(R.id.txtItemsEmployeePerformance_OrderNumber);
//				holder.HoldertxtTOTAL = (TextView) convertView
//						.findViewById(R.id.txtItemsEmployeePerformance_TOTAL);
//				holder.HoldertxtTime = (TextView) convertView
//						.findViewById(R.id.txtItemsEmployeePerformance_Time);
//				
//				
//				Double Tam = Double.parseDouble(STOTAL);
//				Tam = (double) Math.round(Tam * 10) / 10;
//				holder.HoldertxtOrderDate.setText(SOrderDate);
//				holder.HoldertxtOrderNumber.setText(SOrderNumber);
//				holder.HoldertxtTOTAL.setText(Tam.toString());
//				holder.HoldertxtTime.setText(SStartTime + "~" + SEndTime);
//				
//				
//				break;
//			case TYPE_SEPARATOR:
////				Show head group
//				String SHeadGroup = res.get("OrderDate");
//				convertView = mInflater.inflate(R.layout.items_headgroup, null);
//				holder.HeadGroup = (TextView) convertView.findViewById(R.id.txtItemsHeadGroup);
//				holder.HeadGroup.setText(SHeadGroup);
//				holder.HeadGroup.setVisibility(View.GONE);
//				break;
//			}
//			convertView.setTag(holder);
//		}
//		else
//		{
//			
//		}
		
		
		
		View view = View.inflate(context, R.layout.items_employeeperformance,
				null);
		String SOrderDate = res.get("OrderDate");
		String SOrderNumber = res.get("OrderNumber");
		String STOTAL = res.get("TOTAL");
		String SStartTime = res.get("StartTime");
		String SEndTime = res.get("EndTime");

		txtOrderDate = (TextView) view
				.findViewById(R.id.txtItemsEmployeePerformance_Date);
		txtOrderNumber = (TextView) view
				.findViewById(R.id.txtItemsEmployeePerformance_OrderNumber);
		txtTOTAL = (TextView) view
				.findViewById(R.id.txtItemsEmployeePerformance_TOTAL);
		txtTime = (TextView) view
				.findViewById(R.id.txtItemsEmployeePerformance_Time);
		
		LoutHeadDay = (LinearLayout) view
				.findViewById(R.id.Layout_EmployeePerformance_HeadDay);
		LoutDetail = (LinearLayout) view
				.findViewById(R.id.Layout_EmployeePerformance_Detail);

		
//			if (pDate.equals(SOrderDate)) {
//				LoutHeadDay.setVisibility(view.GONE);
//				LoutDetail.setVisibility(view.VISIBLE);
//			} else {
//				LoutHeadDay.setVisibility(view.VISIBLE);
//				LoutDetail.setVisibility(view.VISIBLE);
//				pDate = SOrderDate;
//			}

		
		Double Tam = Double.parseDouble(STOTAL);
		Tam = (double) Math.round(Tam * 10) / 10;
		txtOrderDate.setText(SOrderDate);
		txtOrderNumber.setText(SOrderNumber);
		txtTOTAL.setText(Tam.toString());
		txtTime.setText(SStartTime + "~" + SEndTime);
		return view;
	}
	public static class ViewHolder {
		public TextView HeadGroup;
		@SuppressWarnings("unused")
		private TextView HoldertxtOrderDate, HoldertxtQty,HoldertxtCount, HoldertxtOrderNumber, HoldertxtTOTAL, HoldertxtTime;
	}
	// End class
}
