package general;

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
import database.UserLogin;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class DriverMovementChecking extends Activity {

	private DrawerLayout drawerLayout;
	private General pStatusinternet;
	Connection Mycon;
	DataAdapter_DriverMovementChecking DataAdapter;
	CallableStatement proc_stmt = null;
	ListView LV_DriverMovementChecking;
	private UserLogin UserLogin;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_drivermovementchecking);
		// Custome actionbar
		// getActionBar().setIcon(R.drawable.collections_view_as_grid);
		getActionBar().setTitle("Driver Movement");
		// show home buttom
		getActionBar().setDisplayHomeAsUpEnabled(true);
		 getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		UserLogin = new UserLogin(getApplicationContext());
		if (UserLogin.getRowCount()> 0 )
		{
			inicializar(UserLogin.getUser());
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_find, menu);

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
				 inicializar(StrSeach);
				// Toast.makeText(getApplicationContext(), query,
				// Toast.LENGTH_SHORT).show();
				return true;
			}
		};
		searchView.setOnQueryTextListener(textChangeListener);
		return super.onCreateOptionsMenu(menu);
	}

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
			break;
		case R.id.search:
//			Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void inicializar(String pSreach) {
		pStatusinternet = new General(getApplicationContext());
		LV_DriverMovementChecking = (ListView) findViewById(R.id.LVDriverMovementChecking);
		if (pStatusinternet.CheckingInternet()) {
			Mycon = new ConnectionSQL(getApplicationContext()).ConnSwire();
			// QueryString("SELECT ContainerNum, CustomerName FROM tpmContainerChecking");
			 QueryString("STStockMovementReviewByEmployeeID " + pSreach);

			
		} else {
			Toast MsgInternet = Toast.makeText(getApplicationContext(),
					"Not Access Internet.", Toast.LENGTH_SHORT);
			MsgInternet.show();
		}
	}

	public void QueryString(String CommandoSQL) {

		ResultSet rus;
		try {
			Statement Statement1 = Mycon.createStatement();
			rus = Statement1.executeQuery(CommandoSQL);
			ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
			// day dl len listview
			while (rus.next()) {
				HashMap<String, String> datacolum = new HashMap<String, String>();
				datacolum.put("DateMovement", rus.getString("DateMovement"));
				datacolum.put("PalletID", rus.getString("PalletID"));
				datacolum.put("AuthorisedBy", rus.getString("AuthorisedBy"));
				datacolum.put("ReasonMovement", rus.getString("ReasonMovement"));
				datacolum.put("FromLocation", rus.getString("FromLocation"));
				datacolum.put("ToLocation", rus.getString("ToLocation"));
				datacolum.put("ProductNumber", rus.getString("ProductNumber"));
				datacolum.put("ProductName", rus.getString("ProductName"));
				datacolum.put("ProductID", rus.getString("ProductID"));
				datacolum.put("ReceivingOrderNumber",rus.getString("ReceivingOrderNumber"));
				data.add(datacolum);
			}
			DataAdapter = new DataAdapter_DriverMovementChecking(getApplicationContext(), data);
			LV_DriverMovementChecking.setAdapter(DataAdapter);

		} catch (java.sql.SQLException e) {
//			Log.e("Exception ", e.getMessage());
		}
	}

	// End class
}
