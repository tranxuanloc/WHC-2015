package freelocation;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;

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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

public class FreeLocation_Details extends Activity {

	private DrawerLayout drawerLayout;
	private GridView GV_FreeLocationDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_freelocation_details);
		
		getActionBar().setTitle("Vị Trí Trống");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
		new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		GV_FreeLocationDetails = (GridView) findViewById(R.id.GV_FreeLocationDetails);
		
		Intent _NewIntent = getIntent();
		String _RoomID = _NewIntent.getStringExtra("RoomID");
		if(_RoomID.trim().length() > 0)
		{
			new FreeLocationDetails_AsyncTask(FreeLocation_Details.this).execute(_RoomID);
		}
		
		
		
	}
	/**
	 * Menu
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_setting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
	
	/**
	 * Load listview
	 */
		class FreeLocationDetails_AsyncTask extends AsyncTask<String, String, String> {
			private ProgressDialog mProgressDialog;
			ProgressDialog dialog;
			Activity _Activity;
			private General pStatusinternet;
			private Connection Mycon;
			private DataAdapter_FreeLocationDetails SAd;
			FreeLocationDetails_AsyncTask(Activity PActivity ){
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
									rus = Statement1.executeQuery("WebFreeLocationDetails " + CommandoSQL);
									ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
									// day dl len listview
									while (rus.next()) {
										HashMap<String, String> datacolum = new HashMap<String, String>();
										datacolum.put("RoomID", rus.getString("RoomID"));
										datacolum.put("LocationNumber", rus.getString("LocationNumber"));
										data.add(datacolum);
									}
									SAd = new DataAdapter_FreeLocationDetails(_Activity, data);
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
					GV_FreeLocationDetails.setAdapter(SAd);
				}
				else
				{
					new General(_Activity).showAlert(_Activity, unused);
				}
			}
		}
	
	
	//End Class
}
