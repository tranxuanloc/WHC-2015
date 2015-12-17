package takepicture;

import general.function.LoadingIMG;
import general.java.TouchImageView;
import scs.whc.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ViewIMG extends Activity {

	private String ImgName;
	private TouchImageView ImgView;
//	private DrawerLayout drawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActionBar().setTitle("Receiving Order");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
//		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
//		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
//		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		
		setContentView(R.layout.layout_view_img);
		Intent myIntent = getIntent();
		ImgView = (TouchImageView)findViewById(R.id.touchImageViewShowImg);
		ImgName = myIntent.getStringExtra("FileName");
	    new LoadingIMG(ImgView).execute("http://195.184.11.254:804/Photos/" + ImgName);	
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
//			if (drawerLayout.isDrawerVisible(Gravity.START)) {
//				drawerLayout.closeDrawer(Gravity.START);
//			} else {
//				drawerLayout.openDrawer(Gravity.START);
//			}
			break;
		case android.R.id.home:
			onBackPressed();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
