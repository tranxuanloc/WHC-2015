package freelocation;

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

import scs.whc.R;
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
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class FreeLocation extends Activity {

	private DrawerLayout drawerLayout;
	private SwipeMenuListView LV_FreeLocation;
	private SwipeRefreshLayout mSwipeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_free_location);
		
		getActionBar().setTitle("Vị Trí Trống");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
		new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		LV_FreeLocation = (SwipeMenuListView) findViewById(R.id.LV_FreeLocation);
		
		new FreeLocation_AsyncTask(FreeLocation.this).execute("");
		LV_FreeLocation.setOnItemClickListener(new OnItemClickListener() {

			private TextView txtRoomID;

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				txtRoomID = (TextView)view.findViewById(R.id.txt_Items_FreeLocation_RoomID);
				Intent _Intent ;
				_Intent = new Intent(FreeLocation.this,FreeLocation_Details.class);
				_Intent.putExtra("RoomID", txtRoomID.getText().toString().trim());
				startActivity(_Intent);
			}
			
		});
		/**
		 * Refresh layout
		 */
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new FreeLocation_AsyncTask(FreeLocation.this).execute("");
				mSwipeLayout.setRefreshing(false);
			}
		});
		
		LV_FreeLocation.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
			        int visibleItemCount, int totalItemCount) {
			    boolean enable = false;
			    if(LV_FreeLocation != null && LV_FreeLocation.getChildCount() > 0){
			        // check if the first item of the list is visible
			        boolean firstItemVisible = LV_FreeLocation.getFirstVisiblePosition() == 0;
			        // check if the top of the first item is visible
			        boolean topOfFirstItemVisible = LV_FreeLocation.getChildAt(0).getTop() == 0;
			        // enabling or disabling the refresh layout
			        enable = firstItemVisible && topOfFirstItemVisible;
			    }
			    mSwipeLayout.setEnabled(enable);
			}});
// End
	}
	
	/**
	 * Menu
	 */
	/**
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.Action_Setting:
			if (drawerLayout.isDrawerVisible(Gravity.START)) {
				drawerLayout.closeDrawer(Gravity.START);
			} else {
				drawerLayout.openDrawer(Gravity.START);
			}
			break;
		case android.R.id.home:
			onBackPressed();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_setting, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Load listview
	 */
		class FreeLocation_AsyncTask extends AsyncTask<String, String, String> {
			private ProgressDialog mProgressDialog;
			ProgressDialog dialog;
			Activity _Activity;
			private General pStatusinternet;
			private Connection Mycon;
			private DataAdapter_FreeLocation SAd;
			FreeLocation_AsyncTask(Activity PActivity ){
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
			pStatusinternet = new General(_Activity);
			if (pStatusinternet.CheckingInternet()) {
				Mycon = new ConnectionSQL(_Activity).ConnSwire();
					if (Mycon != null)
					{
							ResultSet rus;
								Statement Statement1;
								try {
									Statement1 = Mycon.createStatement();
									rus = Statement1.executeQuery("WebFreeLocations");
									ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
									// day dl len listview
									while (rus.next()) {
										HashMap<String, String> datacolum = new HashMap<String, String>();
										datacolum.put("RoomID", rus.getString("RoomID"));
										datacolum.put("QtyLocation", rus.getString("QtyLocation"));
										datacolum.put("QtyLow", rus.getString("QtyLow"));
										datacolum.put("QtyHigh", rus.getString("QtyHigh"));
										datacolum.put("QtyOfFree", rus.getString("QtyOfFree"));
										datacolum.put("UpdateTime", new General(_Activity).FormatDate_ddMMYYYY( rus.getString("UpdateTime")));
										datacolum.put("QtyFreeAfterDP", rus.getString("QtyFreeAfterDP"));
										datacolum.put("QtyFree_Low", rus.getString("QtyFree_Low"));
										datacolum.put("QtyStandards", rus.getString("QtyStandards"));
										datacolum.put("QtyOfPallets_OnHand", rus.getString("QtyOfPallets_OnHand"));
										datacolum.put("QtyFree_VeryLow", rus.getString("QtyFree_VeryLow"));
										datacolum.put("QtyFree_VeryHigh", rus.getString("QtyFree_VeryHigh"));
										datacolum.put("QtyFree_High", rus.getString("QtyFree_High"));
										data.add(datacolum);
									}
									SAd = new DataAdapter_FreeLocation(_Activity, data);
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
					LV_FreeLocation.setAdapter(SAd);
				}
				else
				{
					new General(_Activity).showAlert(_Activity, unused);
				}
			}
		}
	
	
	
	
	// End Class
}
