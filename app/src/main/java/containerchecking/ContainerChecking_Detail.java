package containerchecking;

import general.function.ConnectionSQL;
import general.function.NavigationDrawer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import takepicture.CallTakePhotos;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ContainerChecking_Detail extends Activity {
	Connection Mycon;
	DataAdapter_ContDetail SAd;
	CallableStatement proc_stmt = null;
	ListView LV_ContainerChecikingDetail;
	Intent myIntent = getIntent();
	String sCheckingID;
	EditText txtEContdetail_Value, txtEContdetail_Value_String;
	private DrawerLayout drawerLayout;
	private Button CmdContainerCheckingDetail_Finish;
	//Camera
	public static final int MEDIA_TYPE_IMAGE = 1;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_contchecking_detail);
		
		getActionBar().setTitle("Container Checking");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		Intent myIntent = getIntent();
		sCheckingID = myIntent.getStringExtra("CheckingID");

		LV_ContainerChecikingDetail = (ListView) findViewById(R.id.LVContainerCheckingDetail);
		CmdContainerCheckingDetail_Finish = (Button) findViewById(R.id.CmdContainerCheckingDetail_Finish);
		
		QueryString(sCheckingID);
		
		CmdContainerCheckingDetail_Finish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HashMap<String, String> res = SAd.getItem(2);
				
				if (res.get("String").toString().trim().length() > 0)
				{
					if (SAd.ThucThi( "",res.get("CheckingID"),(byte) 100))
					{
						Intent MyIntent = new Intent(ContainerChecking_Detail.this,ContainerChecking.class);
						startActivity(MyIntent);
						finish();
					}
				}
				else
				{
					showSettingsAlert(res.get("CheckingID"));
				}
			}
		});
		
		LV_ContainerChecikingDetail.setOnItemClickListener(new OnItemClickListener() {
			private TextView txtCheckingID,txtColumnID;
			private CheckBox CBCChecking;
			
			@SuppressLint("CutPasteId") @Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				txtCheckingID = (TextView) view.findViewById(R.id.txtContDetail_ID);
				txtColumnID = (TextView) view.findViewById(R.id.txtContColumID);
				CBCChecking = (CheckBox) view.findViewById(R.id.CBContdetail_Check);
				byte BColum = Byte.parseByte(txtColumnID.getText().toString().trim()) ;
				//items Checked 
				if ((BColum < 11) && (BColum > 3) || (BColum == 15))
				{
					if (CBCChecking.isChecked())
					{
						if (SAd.ThucThi( "0", txtCheckingID.getText().toString().trim(),Byte.parseByte(txtColumnID.getText().toString().trim())))
						{
							QueryString(txtCheckingID.getText().toString().trim());
							CBCChecking.setChecked(false);
						}
					}
					else
					{
						if (SAd.ThucThi( "1", txtCheckingID.getText().toString().trim(),Byte.parseByte(txtColumnID.getText().toString().trim())))
						{
							QueryString(txtCheckingID.getText().toString().trim());
							CBCChecking.setChecked(true);
						}
					}
					
				}
				else
				{
					// items Camera
					if (BColum ==12) 
					{
						Intent i = new Intent(ContainerChecking_Detail.this, CallTakePhotos.class);
				        i.putExtra("IsImage","Image");
				        i.putExtra("MyOrder","CC-" + txtCheckingID.getText().toString().trim());
				        startActivity(i);
				      
					}
					
				}
					
			}
		});
		
	}
	
    
/**
 * 
 *  Menu
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
	

	private void QueryString(String CommandoSQL) {
		ResultSet rus;
		try {
			Mycon = new ConnectionSQL(getApplicationContext()).ConnDC();
			Statement Statement1 = Mycon.createStatement();
			rus = Statement1.executeQuery("SP_WebContainerChecking_Detail " + CommandoSQL);
			ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

			// day dl len listview
			while (rus.next()) {
				HashMap<String, String> datacolum = new HashMap<String, String>();
				datacolum.put("CheckingID", rus.getString("CheckingID"));
				datacolum.put("ColumnID", rus.getString("ColumnID"));
				datacolum.put("ColumnName", rus.getString("ColumnName"));
				datacolum.put("ColumnType", rus.getString("ColumnType"));
				if (rus.getString("Value") == null)
				{
					datacolum.put("Value", "");
				}
				else
				{
					datacolum.put("Value", rus.getString("Value"));
				}
				
				if (rus.getString("String") == null)
				{
					datacolum.put("String", "");
				}
				else
				{
					datacolum.put("String", rus.getString("String"));
				}
				data.add(datacolum);
			}
			SAd = new DataAdapter_ContDetail(getApplicationContext(), data,this);
			LV_ContainerChecikingDetail.setAdapter(SAd);

		} catch (java.sql.SQLException e) {
			Log.e("Exception Load", e.getMessage());
		}
	}

	 private void showSettingsAlert(final String pCheckingID) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

	        // Setting Dialog Title
	        alertDialog.setTitle("Container Checking");

	        // Setting Dialog Message
	        alertDialog.setMessage("Bạn chưa nhập nhiệt độ cont, Bạn có chắc chắn muốn finish nó không?");

	        // On pressing Settings button
	        alertDialog.setPositiveButton("Finish",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                    	if (SAd.ThucThi( "",pCheckingID ,(byte) 100))
	    					{
	    						Intent MyIntent = new Intent(ContainerChecking_Detail.this,ContainerChecking.class);
	    						startActivity(MyIntent);
	    						finish();
	    					}
	                    }
	                });

	        // on pressing cancel button
	        alertDialog.setNegativeButton("Cancel",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.cancel();
	                    }
	                });

	        // Showing Alert Message
	        alertDialog.show();
	    }
	
}
