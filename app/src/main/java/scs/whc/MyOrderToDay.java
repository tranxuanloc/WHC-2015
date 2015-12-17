package scs.whc;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;
import general.swipemenulayout.SwipeMenuListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class MyOrderToDay extends Activity {

	private DrawerLayout drawerLayout;
	private SwipeMenuListView LV_MyOrderToDay;
	private SwipeRefreshLayout mSwipeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_myordertoday);
		
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
			LV_MyOrderToDay = (SwipeMenuListView) findViewById(R.id.LVMyOrdersToDay);
				
			new LoadListView_AsyncTask(MyOrderToDay.this).execute("1");
			/**
			 * Refresh layout
			 */
			mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
			mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light, android.R.color.holo_orange_light,
					android.R.color.holo_red_light);
			mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
				
				@Override
				public void onRefresh() {
					new LoadListView_AsyncTask(MyOrderToDay.this).execute("1");
					mSwipeLayout.setRefreshing(false);
				}
			});	
			LV_MyOrderToDay.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
				        int visibleItemCount, int totalItemCount) {
				    boolean enable = false;
				    if(LV_MyOrderToDay != null && LV_MyOrderToDay.getChildCount() > 0){
				        // check if the first item of the list is visible
				        boolean firstItemVisible = LV_MyOrderToDay.getFirstVisiblePosition() == 0;
				        // check if the top of the first item is visible
				        boolean topOfFirstItemVisible = LV_MyOrderToDay.getChildAt(0).getTop() == 0;
				        // enabling or disabling the refresh layout
				        enable = firstItemVisible && topOfFirstItemVisible;
				    }
				    mSwipeLayout.setEnabled(enable);
				}});
			
			LV_MyOrderToDay.setOnItemClickListener(new OnItemClickListener() {

				private TextView txtOrderNumber, txtDate;

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
//					 TODO Auto-generated method stub
					txtOrderNumber = (TextView) view.findViewById(R.id.txtItems_MyOrderToDay_OrderNumber);
					txtDate = (TextView) view.findViewById(R.id.txtItems_MyOrderToDay_Date);
					
					Intent Myintent = new Intent(MyOrderToDay.this, WorkerEmployeeID_InPut.class);
					Myintent.putExtra("OrderNumber", txtOrderNumber.getText().toString().trim());
					Myintent.putExtra("Date", txtDate.getText().toString().trim());
					startActivity(Myintent);
				}
			});
			
			
		//End Create
	}
	
	
	/**
	 * Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_45_123, menu);
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
		case R.id.Action_ChangePlace:
			if (item.getTitle().equals("All"))
			{
				item.setTitle("123");
				new LoadListView_AsyncTask(MyOrderToDay.this).execute("1");
			}
			else
				if (item.getTitle().equals("123"))
				{
					item.setTitle("45");
					new LoadListView_AsyncTask(MyOrderToDay.this).execute("2");
				}
				else
					if (item.getTitle().equals("45"))
					{
						item.setTitle("All");
						new LoadListView_AsyncTask(MyOrderToDay.this).execute("0");
					}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Load ListView Asynstask
	 */
	class LoadListView_AsyncTask extends AsyncTask<String, String, String> {
		private ProgressDialog mProgressDialog;
		ProgressDialog dialog;
		Activity _Activity;
		private General pStatusinternet;
		private Connection Mycon;
		private DataAdapter_MyOrderToDay DAter;
		LoadListView_AsyncTask(Activity PActivity ){
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
								rus = Statement1.executeQuery("STAndroid_InOutViewToday " + CommandoSQL);
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
									datacolum.put("ScanStatus", "0");
									datacolum.put("DispatchingOrderDateFull", rus.getString("DispatchingOrderDate"));
									datacolum.put("SpecialRequirement", rus.getString("SpecialRequirement"));
									if (rus.getString("DispatchingOrderDate") == null) {
										datacolum.put("DispatchingOrderDate", "");
									} else {
										datacolum.put("DispatchingOrderDate",new General(getApplicationContext()).FormatDate_ddMMYY(rus.getString("DispatchingOrderDate")));
									}
									data.add(datacolum);
									
								}
								DAter = new DataAdapter_MyOrderToDay(getApplicationContext(), data);
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
				getActionBar().setTitle(""+ DAter.getCount());
				LV_MyOrderToDay.setAdapter(DAter);
			}
			else
			{
				new General(_Activity).showAlert(_Activity, unused);
			}
		}
	}
	
	// End Class
}
