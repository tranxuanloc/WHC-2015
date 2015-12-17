package containerchecking;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;
import general.swipemenulayout.SwipeMenuListView;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
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

public class ContainerChecking extends Activity implements OnItemClickListener{
	Connection Mycon;
	DataAdapter_ContainerChecking SAd;
	CallableStatement proc_stmt = null;
	SwipeMenuListView LV_ContainerCheciking;
	TextView txtCheckingID, txtTimeDiff;
	General pStatusinternet;
	private DrawerLayout drawerLayout;
	Menu nenuMain;
	private SwipeRefreshLayout mSwipeLayout;
	
	// On Create
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_container_checking);
//		Custome actionbar
//		getActionBar().setIcon(R.drawable.collections_view_as_grid);
		getActionBar().setTitle("Container Checking");
//		show home buttom
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		LV_ContainerCheciking = (SwipeMenuListView) findViewById(R.id.LVContainerChecking);
		new ContainerChecking_AsyncTask(ContainerChecking.this).execute("0");
		/**
		 * Refresh layout
		 */
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		LV_ContainerCheciking.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
			        int visibleItemCount, int totalItemCount) {
			    boolean enable = false;
			    if(LV_ContainerCheciking != null && LV_ContainerCheciking.getChildCount() > 0){
			        // check if the first item of the list is visible
			        boolean firstItemVisible = LV_ContainerCheciking.getFirstVisiblePosition() == 0;
			        // check if the top of the first item is visible
			        boolean topOfFirstItemVisible = LV_ContainerCheciking.getChildAt(0).getTop() == 0;
			        // enabling or disabling the refresh layout
			        enable = firstItemVisible && topOfFirstItemVisible;
			    }
			    mSwipeLayout.setEnabled(enable);
			}});
		
		mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
//				new Handler().postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						
//					}
//				}, 5000);
				MenuItem item = nenuMain.findItem(R.id.Action_GateChange);
				if (item.getTitle().equals("All"))
				{
					new ContainerChecking_AsyncTask(ContainerChecking.this).execute("0");
				}
				else
					if (item.getTitle().equals("G-1"))
					{
						new ContainerChecking_AsyncTask(ContainerChecking.this).execute("1");
					}
					else
						if (item.getTitle().equals("G-2"))
						{
							new ContainerChecking_AsyncTask(ContainerChecking.this).execute("2");
						}
						LV_ContainerCheciking.deferNotifyDataSetChanged();
						mSwipeLayout.setRefreshing(false);
				
				
				
			}
		});
		
		
		
		
		LV_ContainerCheciking.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				txtTimeDiff =(TextView) view.findViewById(R.id.txtContChechingTime);
				long LTimeDiff = Integer.parseInt(txtTimeDiff.getText().toString());
				txtCheckingID = (TextView) view.findViewById(R.id.txtContChechingID);
				if (LTimeDiff > 150)
				{
					Intent MyIntent = new Intent(ContainerChecking.this,ContainerChecking_Detail.class);
					MyIntent.putExtra("CheckingID", txtCheckingID.getText());
					startActivity(MyIntent);
				}
				else
				{
					if (LTimeDiff ==0)
					{
						Intent MyIntent = new Intent(ContainerChecking.this,ContainerChecking_Detail.class);
						MyIntent.putExtra("CheckingID", txtCheckingID.getText());
						startActivity(MyIntent);
						finish();
					}
					else
					{
						showSettingsAlert("Container này đã được kiểm " + LTimeDiff + " phút trước. Bạn có muốn kiểm tra không ?");
//						Toast.makeText(getApplicationContext(), "< 150", Toast.LENGTH_SHORT).show();
					}
				
				}
				

			}
		});
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
			break;
		case R.id.Action_GateChange:
			if (item.getTitle().equals("All"))
			{
				item.setTitle("G-1");
				new ContainerChecking_AsyncTask(ContainerChecking.this).execute("1");
			}
			else
				if (item.getTitle().equals("G-1"))
				{
					item.setTitle("G-2");
					new ContainerChecking_AsyncTask(ContainerChecking.this).execute("2");
				}
				else
					if (item.getTitle().equals("G-2"))
					{
						item.setTitle("All");
						new ContainerChecking_AsyncTask(ContainerChecking.this).execute("0");
					}
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
		inflater.inflate(R.menu.menu_checking, menu);
		nenuMain = menu;
		return super.onCreateOptionsMenu(menu);
	}
	

	private void showSettingsAlert(String _Mess){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
alertDialog.setTitle("Container Checking.");
	// Setting Dialog Message
		alertDialog.setMessage(_Mess);
		alertDialog.setPositiveButton("Tiếp Tục", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				
				Intent MyIntent = new Intent(ContainerChecking.this,ContainerChecking_Detail.class);
				MyIntent.putExtra("CheckingID",txtCheckingID.getText() );
				startActivity(MyIntent);
				
				}
			});
alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int which) {
	dialog.cancel();
	}
	});
		alertDialog.show();
		}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
/**
 * Load listview
 */
	class ContainerChecking_AsyncTask extends AsyncTask<String, String, String> {
		private ProgressDialog mProgressDialog;
		ProgressDialog dialog;
		Activity _Activity;
		ContainerChecking_AsyncTask(Activity PActivity ){
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
			Mycon = new ConnectionSQL(_Activity).ConnDC();
				if (Mycon != null)
				{
						ResultSet rus;
							Statement Statement1;
							try {
								Statement1 = Mycon.createStatement();
								rus = Statement1.executeQuery("SP_WebContainerChecking " + CommandoSQL);
								ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

								// day dl len listview
								while (rus.next()) {
									HashMap<String, String> datacolum = new HashMap<String, String>();
									datacolum.put("CheckingID", rus.getString("CheckingID"));
									datacolum.put("ContainerNum", rus.getString("ContainerNum"));
									datacolum.put("ContainerType", rus.getString("ContainerType"));
									datacolum.put("CustomerName", rus.getString("CustomerName"));
									datacolum.put("Operation", rus.getString("Operation"));
									datacolum.put("TimeIn", new General(_Activity).FormatDate_ddMMYYYY( rus.getString("TimeIn")));
									datacolum.put("DockNumber", rus.getString("DockNumber"));
									datacolum.put("Finish", rus.getString("Finish"));
									if (rus.getString("Time") == null)
									{
										datacolum.put("Time", "");
									}
									else
									{
										datacolum.put("Time", new General(_Activity).FormatDateFull( rus.getString("Time")));
									}
									if (rus.getString("Time") == null)
									{
										datacolum.put("TimeCheck", "");
									}
									else
									{
										datacolum.put("TimeCheck", new General(_Activity).FormatDateLastChek( rus.getString("Time")));
									}
						
									if (rus.getString("ProductQty") == null)
									{
										datacolum.put("ProductQty", "");
									}
									else
									{
										datacolum.put("ProductQty",";Qty-" + rus.getString("ProductQty"));
									}
									
									if (rus.getString("UserCheck") == null)
									{
										datacolum.put("UserCheck", "");
									}
									else
									{
										datacolum.put("UserCheck",";By:" + rus.getString("UserCheck"));
									}
									data.add(datacolum);
								}
								SAd = new DataAdapter_ContainerChecking(_Activity, data);
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
				LV_ContainerCheciking.setAdapter(SAd);
			}
			else
			{
				new General(_Activity).showAlert(_Activity, unused);
			}
		}
	}
	
	
	
	
}
