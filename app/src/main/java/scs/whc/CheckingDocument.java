package scs.whc;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import locationchecking.LocationChecking;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckingDocument extends Activity {

	private DrawerLayout drawerLayout;
	private ListView LV_Document;
	private DataAdapter_DocumentChecking SAd;
	Activity _Activity;
	private SwipeRefreshLayout mSwipeLayout;
	private int CAMERA_SCANNER_REQUEST_CODE = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_checking_document);
		_Activity = CheckingDocument.this;
		
		getActionBar().setTitle("Vị Trí Trống");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
		new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		
		
		LV_Document = (ListView) findViewById(R.id.LV_DocumentChecking);
		LV_Document.setOnItemClickListener(new OnItemClickListener() {

			private TextView txtLocationNumber;

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				txtLocationNumber = (TextView) view.findViewById(R.id.txt_Items_DocumentChecking_Location);
						 Intent mintentPlt = new Intent(_Activity,LocationChecking.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						 mintentPlt.putExtra("Intent_LocationID", txtLocationNumber.getText().toString());
						 startActivity(mintentPlt);
			}
			
			
		});

//		new DocumentLoad_AsyncTask().execute("DGS-25");
		/**
		 * Refresh layout
		 */
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mSwipeLayout.setRefreshing(false);
					}
				}, 5000);
			}
		});
		LV_Document.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
			        int visibleItemCount, int totalItemCount) {
			    boolean enable = false;
			    if(LV_Document != null && LV_Document.getChildCount() > 0){
			        // check if the first item of the list is visible
			        boolean firstItemVisible = LV_Document.getFirstVisiblePosition() == 0;
			        // check if the top of the first item is visible
			        boolean topOfFirstItemVisible = LV_Document.getChildAt(0).getTop() == 0;
			        // enabling or disabling the refresh layout
			        enable = firstItemVisible && topOfFirstItemVisible;
			    }
			    mSwipeLayout.setEnabled(enable);
			}});
//		new DocumentLoad_AsyncTask().execute("DPW-354");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_find_pallet, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();

		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false);
		searchView.setInputType(InputType.TYPE_CLASS_TEXT);
		SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {
				// even Text change
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String StrSeach) {

				if (StrSeach.length() < 5) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập lớn hơn 5 ký tự",
							Toast.LENGTH_SHORT).show();
				} else {
					new DocumentLoad_AsyncTask().execute(StrSeach);
				}

				// Toast.makeText(getApplicationContext(), StrSeach,
				// Toast.LENGTH_SHORT).show();
				return true;
			}
		};
		searchView.setOnQueryTextListener(textChangeListener);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_SCANNER_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String scanContent = data.getStringExtra("SCAN_RESULT");
				if (scanContent.length() < 5) {
					Toast.makeText(getApplicationContext(),
							"Vui lòng nhập lớn hơn 5 ký tự",
							Toast.LENGTH_SHORT).show();
				} else {
					new DocumentLoad_AsyncTask().execute(scanContent);
				}
			} else if (resultCode == RESULT_CANCELED) {
				new DocumentLoad_AsyncTask().execute("0");
				Toast.makeText(getApplicationContext(), "Scan cancelled.",
						Toast.LENGTH_SHORT).show();
			}
		}

	}
	// ONCLICK ITEMS MENU
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.Action_Setting:
			// Toast.makeText(this, "Settings", Toast.LENGTH_SHORT)
			// .show();
			if (drawerLayout.isDrawerVisible(Gravity.START)) {
				drawerLayout.closeDrawer(Gravity.START);
			} else {
				drawerLayout.openDrawer(Gravity.START);
			}
			break;
		case android.R.id.home:
			// Toast.makeText(this, "home", Toast.LENGTH_SHORT)
			// .show();
			onBackPressed();
			finish();
			break;
		case R.id.Action_Scanbarcode:
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			// intent.putExtra("SCAN_FORMATS",
			// "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF");
			startActivityForResult(intent, CAMERA_SCANNER_REQUEST_CODE);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Load listview
	 */
		class DocumentLoad_AsyncTask extends AsyncTask<String, String, String> {
			private ProgressDialog mProgressDialog;
			ProgressDialog dialog;
			
			private General pStatusinternet;
			private Connection Mycon;
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
									rus = Statement1.executeQuery("STAndroid_CheckingByCustomer N'" + CommandoSQL + "'");
									ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
									// day dl len listview
									while (rus.next()) {
										HashMap<String, String> datacolum = new HashMap<String, String>();
										datacolum.put("LocationNumber", rus.getString("LocationNumber"));
										datacolum.put("LocationID", rus.getString("LocationID"));
										
										datacolum.put("TotalCarton", rus.getString("TotalCarton"));
										datacolum.put("DifferentCarton", rus.getString("DifferentCarton"));
										datacolum.put("Remark", rus.getString("Remark"));
										if (rus.getString("StandardCarton") == null)
										{
											datacolum.put("StandardCarton", "null");
										}
										else
										{
											datacolum.put("StandardCarton", rus.getString("StandardCarton"));
											
										}
										if (rus.getString("DifferentCarton") == null)
										{
											datacolum.put("DifferentCarton", "-1");
										}
										else
										{
											datacolum.put("DifferentCarton", rus.getString("DifferentCarton"));
											
										}
										data.add(datacolum);
									}
									SAd = new DataAdapter_DocumentChecking(_Activity, data);
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
					LV_Document.setAdapter(SAd);
				}
				else
				{
					new General(_Activity).showAlert(_Activity, unused);
				}
			}
		}
	
	
	
	// End
}
