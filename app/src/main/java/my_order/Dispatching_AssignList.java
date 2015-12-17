package my_order;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;
import general.swipemenulayout.SwipeMenu;
import general.swipemenulayout.SwipeMenuCreator;
import general.swipemenulayout.SwipeMenuItem;
import general.swipemenulayout.SwipeMenuListView;
import general.swipemenulayout.SwipeMenuListView.OnMenuItemClickListener;
import general.swipemenulayout.SwipeMenuListView.OnSwipeListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import scan_do.Scan_DispatchingOrder;
import scan_nhaphang.Scan_ReceivingOrders;
import scs.whc.R;
import takepicture.Note_TakePhoto;
import workerinput.WorkerTimeInput;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import database.UserLogin;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") 
public class Dispatching_AssignList extends Activity {
	/**
	 * Declare
	 */
			DrawerLayout drawerLayout;
			private General pStatusinternet;
			private SwipeMenuListView LV_DispatchingOrder;
			private Connection Mycon;
			DataAdapter_ScanBarcode_DispatchingOrderList DAter;
			UserLogin pUser;
			private SwipeRefreshLayout mSwipeLayout;
			HashMap<String, String> StringRes;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_myorder_list);

/**
 *  Actionbar Custome
 */
		getActionBar().setTitle("Phiếu Của Bạn");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
/**
* Declare Control View
*/
		LV_DispatchingOrder = (SwipeMenuListView) findViewById(R.id.LVSCanBarcodeList_LV);
		CreateSwipeMenuCreator(LV_DispatchingOrder);
/**
 * 
 */
		pUser = new UserLogin(getApplicationContext());
		if (pUser.getRowCount()> 0 )
		{
			new LoadListViewScan_AsyncTask(Dispatching_AssignList.this).execute(pUser.getUser());
		}
		/**
		 * on click item listview
		 */
		LV_DispatchingOrder.setOnItemClickListener(new OnItemClickListener() {

			private TextView txtOrderID,txtOrderNumber;

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				txtOrderID =(TextView) view.findViewById(R.id.txtItems_DispatchingOrderList_DispatchingOrderID);
				txtOrderNumber = (TextView) view.findViewById(R.id.txtItems_DispatchingOrderList_OrderNumber);
				Intent IntenAssign;
				switch (txtOrderNumber.getText().toString().trim().substring(0, 2)) {
				case "RD":
					IntenAssign = new Intent(Dispatching_AssignList.this,
							Scan_ReceivingOrders.class);
					IntenAssign.putExtra("OrderNumber", txtOrderNumber.getText().toString().trim().replace("-", ""));
					startActivity(IntenAssign);
					break;
				case "DO":
					IntenAssign = new Intent(Dispatching_AssignList.this,
							Scan_DispatchingOrder.class);
					IntenAssign.putExtra("DispatchingOrderID", txtOrderID.getText().toString());
					IntenAssign.putExtra("Type", "DO");
					startActivity(IntenAssign);
					break;
				case "DD":
					IntenAssign = new Intent(Dispatching_AssignList.this,
							Scan_DispatchingOrder.class);
					IntenAssign.putExtra("DispatchingOrderID", txtOrderID.getText().toString());
					IntenAssign.putExtra("Type", "DD");
					startActivity(IntenAssign);
					break;
				default:
					break;
				}
				
				
				
			}
		} );
	
		/**
		 * Refresh layout
		 */
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new LoadListViewScan_AsyncTask(Dispatching_AssignList.this).execute(pUser.getUser());
				mSwipeLayout.setRefreshing(false);
			}
		});
		LV_DispatchingOrder.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
			        int visibleItemCount, int totalItemCount) {
			    boolean enable = false;
			    if(LV_DispatchingOrder != null && LV_DispatchingOrder.getChildCount() > 0){
			        // check if the first item of the list is visible
			        boolean firstItemVisible = LV_DispatchingOrder.getFirstVisiblePosition() == 0;
			        // check if the top of the first item is visible
			        boolean topOfFirstItemVisible = LV_DispatchingOrder.getChildAt(0).getTop() == 0;
			        // enabling or disabling the refresh layout
			        enable = firstItemVisible && topOfFirstItemVisible;
			    }
			    mSwipeLayout.setEnabled(enable);
			}});
		
	// End Create	
	}
	
	/**
	 * Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_dispatchingorder_scan, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.Action_Setting:
//			Toast.makeText(this, "Settings", Toast.LENGTH_SHORT)
//					.show();
			if (drawerLayout.isDrawerVisible(Gravity.START)) {
				drawerLayout.closeDrawer(Gravity.START);
			} else {
				drawerLayout.openDrawer(Gravity.START);
			}
			break;
		case android.R.id.home:
//			Toast.makeText(this, "home", Toast.LENGTH_SHORT)
//					.show();
			onBackPressed();
			finish();
			break;
		case R.id.Action_GotoScan:
			Intent IntenAssign;
			IntenAssign = new Intent(Dispatching_AssignList.this,
					Scan_DispatchingOrder.class);
			IntenAssign.putExtra("DispatchingOrderID", "");
			startActivity(IntenAssign);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	private void CreateSwipeMenuCreator(SwipeMenuListView pLV_DispatchingOrder ) {
		
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem ScanItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				ScanItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
				ScanItem.setWidth(dp2px(100));
				// set item title
				ScanItem.setTitle("Scan");
				// set item title fontsize
				ScanItem.setTitleSize(18);
				// set item title font color
				ScanItem.setTitleColor(Color.WHITE);
				// Add Icon
//				ScanItem.setIcon(R.drawable.icon_scanbarcode);
				// add to menu
				menu.addMenuItem(ScanItem);
				// create "new" item
				SwipeMenuItem PhotoItem = new SwipeMenuItem(
						getApplicationContext());
				PhotoItem.setBackground(new ColorDrawable(Color.parseColor("#B0C4DE")));
				PhotoItem.setWidth(dp2px(100));
				PhotoItem.setTitle("Photo");
				PhotoItem.setTitleSize(18);
				PhotoItem.setTitleColor(Color.WHITE);
//				PhotoItem.setIcon(R.drawable.attachment);
				menu.addMenuItem(PhotoItem);
				// create "new" item
				SwipeMenuItem WorkItem = new SwipeMenuItem(
						getApplicationContext());
				WorkItem.setBackground(new ColorDrawable(Color.parseColor("#AB82FF")));
				WorkItem.setWidth(dp2px(100));
				WorkItem.setTitle("Worker");
				WorkItem.setTitleSize(18);
				WorkItem.setTitleColor(Color.WHITE);
//				WorkItem.setIcon(R.drawable.adept_update);
				menu.addMenuItem(WorkItem);
			}
		};
		// set creator
		pLV_DispatchingOrder.setMenuCreator(creator);

		// step 2. listener item click event
		
		pLV_DispatchingOrder.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				
				
				Intent _Inten;
				switch (index) {
				case 0:
					
					_Inten = new Intent(Dispatching_AssignList.this,
							Scan_DispatchingOrder.class);
					_Inten.putExtra("DispatchingOrderID", StringRes.get("DispatchingOrderID"));
					startActivity(_Inten);
					break;
				case 1:
					_Inten = new Intent(Dispatching_AssignList.this,
							Note_TakePhoto.class);
					_Inten.putExtra("OrderID", StringRes.get("DispatchingOrderNumber").toString());
					startActivity(_Inten);
					break;
				case 2:
					_Inten = new Intent(Dispatching_AssignList.this,
							WorkerTimeInput.class);
					_Inten.putExtra("OrderNumber", StringRes.get("DispatchingOrderNumber").toString());
					_Inten.putExtra("OrderDate", StringRes.get("DispatchingOrderDateFull").toString());
					startActivity(_Inten);
					break;
				}
				return false;
			}
		});
		
		// set SwipeListener
		pLV_DispatchingOrder.setOnSwipeListener(new OnSwipeListener() {
			
			@Override
			public void onSwipeStart(int position) {
				// swipe start
				StringRes = DAter.getItem(position);
			}
			
			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});

		// other setting
//		listView.setCloseInterpolator(new BounceInterpolator());
		
		// test item long click
		pLV_DispatchingOrder.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Toast.makeText(getApplicationContext(), position + " long click", 0).show();
				return false;
			}
		});
	}
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	/**
	 * Load ListView Asynstask
	 */
	class LoadListViewScan_AsyncTask extends AsyncTask<String, String, String> {
		private ProgressDialog mProgressDialog;
		ProgressDialog dialog;
		Activity _Activity;
		LoadListViewScan_AsyncTask(Activity PActivity ){
			_Activity = PActivity;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(_Activity);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(true);
			mProgressDialog.show();
			mProgressDialog.setContentView(R.layout.layout_my_progress);
		}

		@SuppressLint("SdCardPath") @Override
		protected String doInBackground(String... aurl) {
			String result = null;
			String CommandoSQL = new String(aurl[0]);
		
		pStatusinternet = new General(_Activity);
		if (pStatusinternet.CheckingInternet()) {
			Mycon = new ConnectionSQL(_Activity).ConnSwire();
				if (Mycon != null)
				{
						ResultSet rus;
							Statement Statement1;
							try {
								Statement1 = Mycon.createStatement();
								rus = Statement1.executeQuery("STAndroid_BarcodeScan_OrderAssign " + CommandoSQL);
								ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
								// day dl len listview
								while (rus.next()) {
									HashMap<String, String> datacolum = new HashMap<String, String>();
									datacolum.put("DispatchingOrderID", rus.getString("DispatchingOrderID"));
									datacolum.put("EmployeeID", rus.getString("EmployeeID"));
									datacolum.put("DispatchingOrderNumber", rus.getString("DispatchingOrderNumber"));
									datacolum.put("DockNumber", rus.getString("DockNumber"));
									datacolum.put("CustomerNumber", rus.getString("CustomerNumber"));
									datacolum.put("CustomerName", rus.getString("CustomerName"));
									datacolum.put("ScanStatus", rus.getString("ScanStatus"));
									datacolum.put("DispatchingOrderDateFull", rus.getString("DispatchingOrderDate"));
									datacolum.put("SpecialRequirement", rus.getString("SpecialRequirement"));
									if (rus.getString("DispatchingOrderDate") == null) {
										datacolum.put("DispatchingOrderDate", "");
									} else {
										datacolum.put("DispatchingOrderDate",new General(getApplicationContext()).FormatDate_ddMMYY(rus.getString("DispatchingOrderDate")));
									}
									data.add(datacolum);
									
								}
								DAter = new DataAdapter_ScanBarcode_DispatchingOrderList(getApplicationContext(), data);

								//End
								} catch (SQLException e) {
								result = e.getMessage().toString();
							}
				}
				else
				{
					result ="Không thể kết nối được dữ liệu, vui lòng thử lại sau.";
				}
		} else {
			result ="Không có mạng, vui lòng kiểm tra wifi.";
		}
		
		
		return result;

		}
		protected void onProgressUpdate(String... progress) {
			 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			mProgressDialog.dismiss();
			if (unused== null)
			{
				LV_DispatchingOrder.setAdapter(DAter);
			}
			else
			{
				new General(_Activity).showAlert(_Activity, unused);
			}
		}
	}
//	End Class
}
