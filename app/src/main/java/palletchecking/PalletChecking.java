package palletchecking;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class PalletChecking extends Activity {
	private DrawerLayout drawerLayout;
	private General pStatusinternet;
	Connection Mycon;
	DataAdapter_PalletChecking SAd;
	CallableStatement proc_stmt = null;
	ListView LV_PalletChecking;
	private int CAMERA_SCANNER_REQUEST_CODE = 100;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_SCANNER_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String scanContent = data.getStringExtra("SCAN_RESULT");
				if (scanContent.substring(0, 2).toString().length() > 0) {
					inicializar(scanContent);
				} else {
					inicializar("0");
					Toast.makeText(getApplicationContext(), "Please Scan.",
							Toast.LENGTH_SHORT).show();
				}
			} else if (resultCode == RESULT_CANCELED) {
				inicializar("0");
				Toast.makeText(getApplicationContext(), "Scan cancelled.",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	// On create
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_pallet_checking);
		// Custome actionbar
		// getActionBar().setIcon(R.drawable.collections_view_as_grid);
		getActionBar().setTitle("Pallet-Carton Checking");
		// show home buttom
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		Intent Intent_PalletID = getIntent();
		String Str_PalletID = Intent_PalletID.getStringExtra("Intent_PalletID");
		if (!(Str_PalletID == null)) {
			inicializar(Str_PalletID);
		}

	}

	// CREATE MENU
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
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

				inicializar(StrSeach);

				// Toast.makeText(getApplicationContext(), StrSeach,
				// Toast.LENGTH_SHORT).show();
				return true;
			}
		};
		searchView.setOnQueryTextListener(textChangeListener);
		return super.onCreateOptionsMenu(menu);
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

	// SEARCH PALLET ID
	private void inicializar(String pSreach) {
		pStatusinternet = new General(getApplicationContext());
		LV_PalletChecking = (ListView) findViewById(R.id.LVPalletChecking);
		if (pStatusinternet.CheckingInternet()) {
			Mycon = new ConnectionSQL(getApplicationContext()).ConnSwire();
			if (Mycon !=null)
			{
				QueryString(pSreach);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Kết nối database không thành công", Toast.LENGTH_SHORT).show();
			}
			// QueryString("SELECT ContainerNum, CustomerName FROM tpmContainerChecking");
			
		} else {
			Toast MsgInternet = Toast.makeText(getApplicationContext(),
					"Not Access Internet.", Toast.LENGTH_SHORT);
			MsgInternet.show();
		}
	}

	// EXEC SEARCH PALLET
	public void QueryString(String CommandoSQL) {
		ResultSet rus;
		try {
			Statement Statement1 = Mycon.createStatement();
			rus = Statement1.executeQuery("WebWHCPalletID 0," + CommandoSQL);
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
				datacolum.put("Type", "1");
				if (rus.getString("ProductionDate") == null) {
					datacolum.put("CustomerRef", rus.getString("CustomerRef")
							+ "~" + rus.getString("UseByDate"));
				} else {
					datacolum.put(
							"CustomerRef",
							rus.getString("CustomerRef")
									+ "~"
									+ rus.getString("ProductionDate")
											.substring(0, 10)
									+ "~"
									+ rus.getString("UseByDate").substring(0,
											10));
				}
				data.add(datacolum);
			}
			HashMap<String, String> dataHead = new HashMap<String, String>();
			dataHead.put("Type", "2");
			dataHead.put("Tilte", "Movement History");
			data.add(dataHead);

			Mycon = new ConnectionSQL(getApplicationContext()).ConnSwire();
			rus = Statement1.executeQuery("STAndroid_MovementsHistory "
					+ CommandoSQL);
			while (rus.next()) {
				HashMap<String, String> datacolum = new HashMap<String, String>();
				datacolum.put("Quantity", rus.getString("quantity"));
				datacolum.put("Remark", rus.getString("Remark"));
				datacolum
						.put("ReasonMovement", rus.getString("ReasonMovement"));
				datacolum
						.put("LocationNumber", rus.getString("LocationNumber"));
				datacolum.put("LocationTo", rus.getString("LocationTo"));
				datacolum.put("AuthorisedBy", rus.getString("AuthorisedBy"));
				datacolum.put("Name", rus.getString("Name"));
				datacolum.put("Type", "3");
				if (rus.getString("DateMovement") == null) {
					datacolum.put("DateMovement", "");
				} else {
					datacolum.put("DateMovement", rus.getString("DateMovement")
							.substring(0, 10));
				}
				data.add(datacolum);
			}

			SAd = new DataAdapter_PalletChecking(getApplicationContext(), data);
			LV_PalletChecking.setAdapter(SAd);
		} catch (java.sql.SQLException e) {
		}
	}

	// End class
}
