package scs.whc;

import general.function.General;
import general.function.NavigationDrawer;
import database.UserLogin;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

public class WorkingSchedules extends Activity {

	private DrawerLayout drawerLayout;
	private UserLogin Login;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_workingschedules);
setContentView(R.layout.layout_historyemployeeinout);
		
		getActionBar().setTitle("Lịch Làm Việc");
		// show home buttom
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor(getResources().getString(
						R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		Login = new UserLogin(getApplicationContext());
		webView = (WebView) findViewById(R.id.webViewEmployeeInOut);
		if (Login.getRowCount()> 0)
		{
			new General(getApplicationContext()).startWebView(WorkingSchedules.this,webView, "http://195.184.11.254:804/General/WorkingSchedules_Tablet.aspx");
		}
		
		
		
		
		
		
	}

	
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
	
	
	
	
}
