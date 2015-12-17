package general;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
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

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") public class ReceivingOrders extends Activity {

	private DrawerLayout drawerLayout;
	private General pStatusinternet;
	Connection Mycon;
	DataAdapter_ReceivingOrders DataAdapter;
	CallableStatement proc_stmt = null;
	ListView LV_ReceivingOrders;
	
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_receiving_orders);
		

//		Custome actionbar
//		getActionBar().setIcon(R.drawable.collections_view_as_grid);
		getActionBar().setTitle("Receiving Order");
//		show home buttom
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		Intent Intent_RO = getIntent();
		String Str_RO = Intent_RO.getStringExtra("Intent_RO");
		if (!(Str_RO == null)){
			inicializar(Str_RO);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_find, menu);
		
		
		  SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

	            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	            searchView.setIconifiedByDefault(false);   
	            searchView.setInputType(InputType.TYPE_CLASS_PHONE);

	        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() 
	        {
	            @Override
	            public boolean onQueryTextChange(String newText) 
	            {
	                //even Text change 
	                return true;
	            }
	            @Override
	            public boolean onQueryTextSubmit(String StrSeach) 
	            {
	            	inicializar(StrSeach);
//	            	Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
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
		LV_ReceivingOrders = (ListView) findViewById(R.id.LVReceivingOrders);
		if (pStatusinternet.CheckingInternet())
		{
			Mycon = new ConnectionSQL(getApplicationContext()).ConnSwire();
			// QueryString("SELECT ContainerNum, CustomerName FROM tpmContainerChecking");
			QueryString("WebReceivingOrderDetails " + pSreach );
		}
		else
		{
			Toast MsgInternet = Toast.makeText(getApplicationContext(), "Not Access Internet.", Toast.LENGTH_SHORT);
			MsgInternet.show();
		}
	}
	
	public void QueryString(String CommandoSQL) {
		ResultSet rus;
		try {
			Statement Statement1 = Mycon.createStatement();
			rus = Statement1.executeQuery(CommandoSQL);
			ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
			int SumTotalPackages = 0;
			// day dl len listview
			while (rus.next()) {
				HashMap<String, String> datacolum = new HashMap<String, String>();
				datacolum.put("ProductID", rus.getString("ProductID"));
				datacolum.put("ProductNumber", rus.getString("ProductNumber"));
				datacolum.put("ProductName", rus.getString("ProductName"));
				datacolum.put("CustomerRef", rus.getString("CustomerRef"));
				datacolum.put("Remark", rus.getString("Remark"));
				datacolum.put("ReceivingOrderNumber", rus.getString("ReceivingOrderNumber"));
				datacolum.put("CustomerNumber", rus.getString("CustomerNumber"));
				datacolum.put("CustomerName", rus.getString("CustomerName"));
				datacolum.put("SpecialRequirement", rus.getString("SpecialRequirement"));
				
				datacolum.put("TypeColum", "1");
				SumTotalPackages = SumTotalPackages + Integer.parseInt(rus.getString("TotalPackages"));
				datacolum.put("SumTotalPackages","" + SumTotalPackages);
				
				if (rus.getString("ReceivingOrderDate") == null)
				{
					datacolum.put("ReceivingOrderDate","");
				}
				else{
					datacolum.put("ReceivingOrderDate",rus.getString("ReceivingOrderDate").substring(0, 10));
				}
				
				if (rus.getString("TotalPackages") == null)
				{
					datacolum.put("TotalPackages","0");
				}
				else{
					
					datacolum.put("TotalPackages",rus.getString("TotalPackages"));
				}
				
				
				if (rus.getString("ProductionDate") == null)
				{
					datacolum.put("ProductionDate","");
				}
				else
				{
					datacolum.put("ProductionDate",rus.getString("ProductionDate").substring(0, 10));
				}
				if (rus.getString("UseByDate") == null)
				{
					datacolum.put("UseByDate","");
				}
				else
				{
					datacolum.put("UseByDate", rus.getString("UseByDate").substring(0, 10));
				}
				data.add(datacolum);
				if (rus.isLast()){
					HashMap<String, String> datacolumLast = new HashMap<String, String>();
					datacolumLast.put("ProductID", rus.getString("ProductID"));
					datacolumLast.put("ProductNumber", rus.getString("ProductNumber"));
					datacolumLast.put("ProductName", rus.getString("ProductName"));
					datacolumLast.put("CustomerRef", rus.getString("CustomerRef"));
					datacolumLast.put("Remark", rus.getString("Remark"));
					datacolumLast.put("ReceivingOrderNumber", rus.getString("ReceivingOrderNumber"));
					datacolumLast.put("CustomerNumber", rus.getString("CustomerNumber"));
					datacolumLast.put("CustomerName", rus.getString("CustomerName"));
					datacolumLast.put("SpecialRequirement", rus.getString("SpecialRequirement"));
					
					datacolumLast.put("TypeColum", "2");
//					SumTotalPackages = SumTotalPackages + Integer.parseInt(rus.getString("TotalPackages"));
					datacolumLast.put("SumTotalPackages","" + SumTotalPackages);
					
					if (rus.getString("ReceivingOrderDate") == null)
					{
						datacolumLast.put("ReceivingOrderDate","");
					}
					else{
						datacolumLast.put("ReceivingOrderDate",rus.getString("ReceivingOrderDate").substring(0, 10));
					}
					
					if (rus.getString("TotalPackages") == null)
					{
						datacolumLast.put("TotalPackages","0");
					}
					else{
						
						datacolumLast.put("TotalPackages",rus.getString("TotalPackages"));
					}
					
					
					if (rus.getString("ProductionDate") == null)
					{
						datacolumLast.put("ProductionDate","");
					}
					else
					{
						datacolumLast.put("ProductionDate",rus.getString("ProductionDate").substring(0, 10));
					}
					if (rus.getString("UseByDate") == null)
					{
						datacolumLast.put("UseByDate","");
					}
					else
					{
						datacolumLast.put("UseByDate", rus.getString("UseByDate").substring(0, 10));
					}
					data.add(datacolumLast);
				}
				
				Collections.reverse(data);
			
			}
			DataAdapter = new DataAdapter_ReceivingOrders(getApplicationContext(),data);
			LV_ReceivingOrders.setAdapter(DataAdapter);

		} catch (java.sql.SQLException e) {
//			Log.e("Exception ", e.getMessage());
		}
	}
	
//	End class
}
