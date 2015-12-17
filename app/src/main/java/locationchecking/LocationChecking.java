package locationchecking;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;

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
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class LocationChecking extends Activity implements OnItemClickListener {
	private int CAMERA_SCANNER_REQUEST_CODE = 100;
	private DrawerLayout drawerLayout;
	private General pStatusinternet;
	Connection Mycon;
	DataAdapter_LocationChecking SAd;
	CallableStatement proc_stmt = null;
	ListView LV_LocationCheciking;

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_location_checking);
		// Custome actionbar
		// getActionBar().setIcon(R.drawable.collections_view_as_grid);
		getActionBar().setTitle("Kiểm Vị Trí");
		// show home buttom
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor(getResources().getString(
						R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);

		Intent Intent_LocationID = getIntent();
		String Str_LocationID = Intent_LocationID.getStringExtra("Intent_LocationID").toString().trim();
		if (!(Str_LocationID == null)) {
			try {
				Str_LocationID.trim();
				Str_LocationID = Str_LocationID.substring(2,
						Str_LocationID.length());
				Toast.makeText(this, Str_LocationID, Toast.LENGTH_SHORT).show();
				inicializar(Str_LocationID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				inicializar("01-00");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		try {
			if (requestCode == CAMERA_SCANNER_REQUEST_CODE) {
				if (resultCode == RESULT_OK) {
					String scanContent = data.getStringExtra("SCAN_RESULT");
					if (scanContent.substring(0, 2).toString().equals("LO")) {
						inicializar(scanContent);
					} else {
						inicializar("0");
						Toast.makeText(getApplicationContext(),
								"Please Scan LO.", Toast.LENGTH_SHORT).show();
					}
				} else if (resultCode == RESULT_CANCELED) {
					inicializar("0");
					Toast.makeText(getApplicationContext(), "Scan cancelled.",
							Toast.LENGTH_SHORT).show();
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		searchView.setInputType(InputType.TYPE_CLASS_PHONE);

		SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {
				// even Text change
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String StrSeach) {
				try {
					inicializar(StrSeach);
				} catch (Exception e) {
				}
				return true;
			}
		};
		searchView.setOnQueryTextListener(textChangeListener);
		return super.onCreateOptionsMenu(menu);
	}

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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	private void inicializar(String pSreach) throws Exception {
		pStatusinternet = new General(getApplicationContext());
		LV_LocationCheciking = (ListView) findViewById(R.id.LVLocationChecking);
		if (pStatusinternet.CheckingInternet()) {
			Mycon = new ConnectionSQL(getApplicationContext()).ConnSwire();
			// QueryString("SELECT ContainerNum, CustomerName FROM tpmContainerChecking");
			pSreach = pSreach.replace("LO000", "");
			pSreach.trim();
			do {
				pSreach = pSreach.replace("-", "");
			} while (pSreach.contains("-"));
			if ((pSreach.length() < 4) || (pSreach.length() > 6)) {
				AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);
				alertDialog2.setMessage("Please Entry 4-6 characters." + pSreach);
				alertDialog2.show();
			} else {
				int i = pSreach.length();
				switch (i) {
				case 4:
					pSreach = pSreach.substring(0, 2) + "-"
							+ pSreach.substring(2, 4);
					break;
				case 5:
					pSreach = pSreach.substring(0, 2) + "-"
							+ pSreach.substring(2, 4) + "-"
							+ pSreach.substring(4, 5);
					break;
				case 6:
					pSreach = pSreach.substring(0, 2) + "-"
							+ pSreach.substring(2, 4) + "-"
							+ pSreach.substring(4, 5) + "-"
							+ pSreach.substring(5, 6);
					break;

				default:
					AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
							this);
					alertDialog2.setMessage("The Location not Invalid");
					alertDialog2.show();
					break;
				}
				String StrQR = "WebWHCLocation N'" + pSreach + "',0,0,0";
				new LocationDetails_AsyncTask(this).execute(StrQR);
			}
		} else {
			Toast MsgInternet = Toast.makeText(getApplicationContext(),
					"Not Access Internet.", Toast.LENGTH_SHORT);
			MsgInternet.show();
		}
	}

	
	/**
	 * Load listview
	 */
		class LocationDetails_AsyncTask extends AsyncTask<String, String, String> {
			private ProgressDialog mProgressDialog;
			ProgressDialog dialog;
			Activity _Activity;
			private General pStatusinternet;
			private Connection Mycon;
			LocationDetails_AsyncTask(Activity PActivity ){
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
									rus = Statement1.executeQuery(CommandoSQL);
									ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

									// day dl len listview
									while (rus.next()) {
										HashMap<String, String> datacolum = new HashMap<String, String>();
										datacolum.put("LocationID", rus.getString("LocationID"));
										datacolum
												.put("LocationNumber", rus.getString("LocationNumber"));
										datacolum
												.put("CustomerNumber", rus.getString("CustomerNumber"));
										datacolum.put("ProductNumber", rus.getString("ProductNumber"));
										datacolum.put("ProductName", rus.getString("ProductName"));
										datacolum.put("PalletID", rus.getString("PalletID"));
										datacolum.put("ReceivingOrderID",
												rus.getString("ReceivingOrderID"));
										datacolum.put("ReceivingOrderNumber",
												rus.getString("ReceivingOrderNumber"));
										datacolum.put("ProductID", rus.getString("ProductID"));
										datacolum.put("CurrentQuantity",
												rus.getString("CurrentQuantity"));
										datacolum.put("CustomerRef", rus.getString("CustomerRef"));
										if (rus.getString("ProductionDate") == null) {
											datacolum.put("ProductionDate", "");
										} else {
											datacolum.put("ProductionDate"," NXS:"+ rus.getString("ProductionDate").substring(0, 10));
										}

										if (rus.getString("UseByDate") == null) {
											datacolum.put("UseByDate", "");
										} else {
											datacolum.put("UseByDate"," HSD:"+ rus.getString("UseByDate").substring(0,
																	10));
										}
										
										data.add(datacolum);
									}
									SAd = new DataAdapter_LocationChecking(getApplicationContext(),
											data);
								

								
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
					LV_LocationCheciking.setAdapter(SAd);
					getActionBar().setTitle("Tổng " + SAd.getCount() + " Vị Trí");
				}
				else
				{
					new General(_Activity).showAlert(_Activity, unused);
				}
			}
		}
	
	
	
	// End Class
}
