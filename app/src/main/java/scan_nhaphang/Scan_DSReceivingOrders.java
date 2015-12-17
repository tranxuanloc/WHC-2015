package scan_nhaphang;

import general.function.NavigationDrawer;
import scs.whc.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class Scan_DSReceivingOrders extends FragmentActivity implements
ActionBar.TabListener {
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;

private ActionBar actionBar;
private TextView txtDSReceivingOrders;
// Tab titles
private DrawerLayout drawerLayout;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.layout_scan_ds_receivingorders);

// Initilization
viewPager = (ViewPager) findViewById(R.id.pagerScan_DSReceivingOrders);
actionBar = getActionBar();
mAdapter = new TabsPagerAdapter(getSupportFragmentManager(),getApplicationContext());
actionBar.setTitle("Scan RD");
actionBar.setDisplayHomeAsUpEnabled(true);
actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));

NavigationDrawer pNavigationDrawe = new NavigationDrawer();
pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), Scan_DSReceivingOrders.this);
drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);



viewPager.setAdapter(mAdapter);
//actionBar.setHomeButtonEnabled(false);
actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

actionBar.addTab(actionBar.newTab().setTabListener(this));
actionBar.addTab(actionBar.newTab().setTabListener(this));

txtDSReceivingOrders = (TextView)findViewById(R.id.txtDSReceivingOrders_RD);

/**
 * on swiping the viewpager make respective tab selected
 * */

viewPager.setCurrentItem(1);
viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

	private TextView txtFragment0;
	private TextView txtFragment1;
	private ListView LV_Scan_DSReceivingOrders;

	@Override
	public void onPageSelected(int position) {
//		actionBar.setSelectedNavigationItem(position);
//		Toast.makeText(getApplicationContext(),"" +  position, Toast.LENGTH_SHORT).show();
		txtFragment0 = (TextView) findViewById(R.id.seleDSReceivingOrders);
		txtFragment1 = (TextView) findViewById(R.id.seleScan_DSReceivingOrders);
		position = position % 2;
		if (position ==0)
		{
			txtFragment0.setBackgroundColor(Color.parseColor("#0000FF"));
			txtFragment1.setBackgroundColor(Color.parseColor("#BEBEBE"));
		}
		else
		{
			txtFragment0.setBackgroundColor(Color.parseColor("#BEBEBE"));
			txtFragment1.setBackgroundColor(Color.parseColor("#0000FF"));
			if (txtDSReceivingOrders.getText().toString().trim().length()> 0 )
			{
				
				LV_Scan_DSReceivingOrders = (ListView)viewPager.findViewById(R.id.LV_DSReceivingOrders_Scan);
				new LoadListViewScan_AsyncTask(Scan_DSReceivingOrders.this, LV_Scan_DSReceivingOrders).execute(txtDSReceivingOrders.getText().toString().trim());
			}
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
});
}



@Override
public void onTabReselected(Tab tab, FragmentTransaction ft) {
}

@Override
public void onTabSelected(Tab tab, FragmentTransaction ft) {
viewPager.setCurrentItem(tab.getPosition());
}

@Override
public void onTabUnselected(Tab tab, FragmentTransaction ft) {
}



@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.menu_setting, menu);
	return true;
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
		finish();
		break;
	
	default:
		break;
	}
	return super.onOptionsItemSelected(item);
}
// End Class
}