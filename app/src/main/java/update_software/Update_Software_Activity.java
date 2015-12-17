package update_software;

import general.function.NavigationDrawer;
import scs.whc.R;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Update_Software_Activity extends Activity {

	private Button CmdUpdate;
	private Button CmdUpdateSymbol;
	private DrawerLayout drawerLayout;
	private String deviceName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_update_software);
		
		// show home buttom
    	getActionBar().setTitle("Cập Nhật");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), Update_Software_Activity.this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		deviceName = android.os.Build.MODEL;
		//BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
	    //deviceName = myDevice.getName();
		
        CmdUpdate = (Button)findViewById(R.id.Cmd_Update_Software);
        CmdUpdate.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				
//				if (deviceName.toString().trim().substring(0, 6) == "TC700H") 
//				{
//					Toast.makeText(getApplicationContext(), deviceName, Toast.LENGTH_SHORT).show();
//					String _Path = "/sdcard/Download/WHC-2015_Symbol.apk";
//	            	String url = "http://195.184.11.254:804/File_Download/WHC-2015_Symbol.apk";
//	            	update_software_symbol Update2 =  new update_software_symbol(Update_Software_Activity.this);
//	                Update2.startDownload(url, _Path);
//
//                }
//				else {
//					Toast.makeText(getApplicationContext(), deviceName.trim().substring(0, 6)+"-ZZZ".toUpperCase(), Toast.LENGTH_SHORT).show();
//	            	String _Path = "/sdcard/Download/WHC-2015.apk";
//	            	String url = "http://195.184.11.254:804/File_Download/WHC-2015.apk";
//	                Update_Software Update =  new Update_Software(Update_Software_Activity.this);
//	                Update.startDownload(url, _Path);
//				}
            
			
			//
			switch (deviceName.toString().trim().substring(0, 6)) {
			case "TC700H":
				Toast.makeText(getApplicationContext(), deviceName, Toast.LENGTH_SHORT).show();
				String _Path2 = "/sdcard/Download/WHC-2015_Symbol.apk";
            	String url2 = "http://195.184.11.254:804/File_Download/WHC-2015_Symbol.apk";
            	update_software_symbol Update2 =  new update_software_symbol(Update_Software_Activity.this);
                Update2.startDownload(url2, _Path2);
				break;
			case "PA700":

				break;
			
			default:
				Toast.makeText(getApplicationContext(), deviceName.trim().substring(0, 6)+"-Z".toUpperCase(), Toast.LENGTH_SHORT).show();
            	String _Path = "/sdcard/Download/WHC-2015.apk";
            	String url = "http://195.184.11.254:804/File_Download/WHC-2015.apk";
                Update_Software Update =  new Update_Software(Update_Software_Activity.this);
                Update.startDownload(url, _Path);
				break;
			}
		}
			//
        });
        
        //Symbol TC70
//        CmdUpdateSymbol = (Button)findViewById(R.id.Cmd_Update_Software_SymbolTC70);
//        CmdUpdateSymbol.setOnClickListener(new OnClickListener(){
//			public void onClick(View v) {
//
//            	String _Path = "/sdcard/Download/WHC-2015_Symbol.apk";
//            	String url = "http://195.184.11.254:804/File_Download/WHC-2015_Symbol.apk";
//            	update_software_symbol Update2 =  new update_software_symbol(Update_Software_Activity.this);
//                Update2.startDownload(url, _Path);
//            }
//        });
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
	//End 
}
