package scan_do;

import general.function.NavigationDrawer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.MainActivity;
import scs.whc.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import database.UserLogin;

public class Login extends Activity {
	Connection connect;
	boolean Check_internet;
	private UserLogin db;
	private int checkuser;
	DrawerLayout drawerLayout;
	EditText etxtUserName, etxtPassword;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		
		
		db = new UserLogin(getApplicationContext());

		// check co du lieutrong DBSQLite
		checkuser = db.getRowCount();
		// neu da login thi chay qua scan
		if (checkuser > 0) {
			Intent in = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(in);
			finish();
		}
		
		setContentView(R.layout.layout_login);
		
		getActionBar().setTitle("Login");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		
		etxtUserName = (EditText) findViewById(R.id.etxtUserName);
		etxtPassword = (EditText) findViewById(R.id.etxtPassword);
		etxtUserName.setFocusableInTouchMode(true);
		etxtUserName.setFocusable(true);
		etxtUserName.requestFocus();
		etxtPassword.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				LogIn();   
//				new General(getApplicationContext()).showAlert(Login.this, "keyCode:"+ actionId);
				return false;
			}
		});
					
			
		
		
		// keim tra initenet
		ConnectivityManager connect_wifi = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = connect_wifi.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					Check_internet = true;
				}
			}

		} else {
			Check_internet = false;
		}
		// ket thuc check internet

		// chay ham
		if (Check_internet == true) {
			connect = CONN("WMSUser", "WMSU2008", "SwireDB",
					"195.184.11.230:1433");
		}

	}

	@SuppressLint("NewApi")
	private Connection CONN(String _user, String _pass, String _DB,
			String _server) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Connection conn = null;
		String ConnURL = null;

		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			ConnURL = "jdbc:jtds:sqlserver://" + _server + ";"
					+ "databaseName=" + _DB + ";user=" + _user + ";password="
					+ _pass + ";";
			conn = DriverManager.getConnection(ConnURL);
		} catch (SQLException se) {
			Log.e("ERROR", se.getMessage());
		} catch (ClassNotFoundException e) {
			Log.e("ERROR", e.getMessage());
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
		return conn;
	}

	// click login
	public void onClickLogin(View v) {
		if (v.getId() == R.id.btnLogin) {
			LogIn();
		}
	}

	
	private void LogIn() {

		if (Check_internet == true) {
			// if login successfull then go to Main
			ResultSet rs;
			PreparedStatement cs = null;
			String varUserName = null;
			String SFullName = null;
			String SPositionGroup = null;
			try {
				cs = connect
						.prepareStatement("exec STAndroid_BarcodeScan_Login ?,?");
				cs.setEscapeProcessing(true);
				cs.setQueryTimeout(90);

				cs.setString(1, etxtUserName.getText().toString());
				cs.setString(2, etxtPassword.getText().toString());
				rs = cs.executeQuery();

				while (rs.next()) {
					varUserName = rs.getString("UserName");
					SFullName = rs.getString("VietnamName");
					SPositionGroup = rs.getString("PositionGroup");
//					Log.e("A", varUserName);
				}
			} catch (Exception e) {
				Log.e("ERROR", e.getMessage());
			}

			if (varUserName != null) {
				db.adduser(varUserName, SFullName, SPositionGroup);
				Intent in = new Intent(getApplicationContext(),
						MainActivity.class);
				in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
				finish();
				
			} else {
				TextView txtLoginMessage;
				txtLoginMessage = (TextView) findViewById(R.id.txtLoginMessage);
				txtLoginMessage.setText("Login false!!");
				etxtPassword.setText("");
				txtLoginMessage.setVisibility(View.VISIBLE);
			}
		} else {
			Toast.makeText(getApplicationContext(), "Khong co internet",
					Toast.LENGTH_LONG).show();
		}
	
	}
	
	
	
	/**
	 * Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_setting, menu);
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
	
}
