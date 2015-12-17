package changepassword;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import scan_do.Login;
import scs.whc.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import database.UserLogin;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") public class ChangePassWord extends Activity {

	EditText txtUserName, txtCurrentPass, txtNewPass, txtConfirmPass;
	TextView txtNote;
	Button CmdChangePass;
	Connection connect;
	boolean Check_internet;
	private UserLogin CheckLogin;
	private int checkuser;
	private General pStatusinternet;
	Connection Mycon;
	DrawerLayout drawerLayout;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CheckLogin = new UserLogin(getApplicationContext());
		// check co du lieutrong DBSQLite
		checkuser = CheckLogin.getRowCount();
		// neu da login thi chay qua scan
		if (checkuser > 0) {
			
//			Toast.makeText(getApplicationContext(), "DN", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.layout_changepassword);
			getActionBar().setTitle("Thay Đổi Password");
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
			NavigationDrawer pNavigationDrawe = new NavigationDrawer();
			pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
			drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
			
			
			
			txtUserName = (EditText) findViewById(R.id.etUserName);
			txtCurrentPass = (EditText) findViewById(R.id.etCurrentPassword);
			txtNewPass = (EditText) findViewById(R.id.etNewPassword);
			txtConfirmPass = (EditText) findViewById(R.id.etConfirmPassword);
			txtNote = (TextView) findViewById(R.id.txtChangePass_Infor);
			CmdChangePass = (Button) findViewById(R.id.CmdChangePassword);
			txtUserName.setText(CheckLogin.getUser());
			txtNote.setVisibility(View.INVISIBLE);

			ViewGroup.LayoutParams params = txtNote.getLayoutParams();
			params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
			txtNote.setLayoutParams(params);

			CmdChangePass.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String SUserName = txtUserName.getText().toString();
					String SCurrentPass = txtCurrentPass.getText().toString();
					String SNewPass = txtNewPass.getText().toString();
					String SConfirmPass = txtConfirmPass.getText().toString();
					
					
					
					
//					Toast.makeText(getApplicationContext(), SCurrentPass, Toast.LENGTH_SHORT).show();
//					txtNote.setText("Please entry current password.");
//					if (SCurrentPass.equals(""))
//					{
//						txtNote.setVisibility(View.VISIBLE);
//						txtNote.setText("Please entry current password.");
//					}
//					else
						if (SNewPass.equals(""))
						{
							txtNote.setVisibility(View.VISIBLE);
							txtNote.setText("Please entry current New password.");
						}
						else
							if (SConfirmPass.equals(""))
							{
								txtNote.setVisibility(View.VISIBLE);
								txtNote.setText("Please entry current Confirm password.");
							}
							else
							{
								if (SConfirmPass.equals(SNewPass))
								{
//									Toast.makeText(getApplicationContext(), "Bang", Toast.LENGTH_SHORT).show();
									txtNote.setVisibility(View.INVISIBLE);
									onChangePassword(SUserName,SCurrentPass,SNewPass);
								}
								else
								{
//									Toast.makeText(getApplicationContext(), "Ko", Toast.LENGTH_SHORT).show();
									txtNote.setVisibility(View.VISIBLE);
									txtNote.setText("The Confirm New Password must match the New Password entry.");
								}
							}
							
				}
			});
			
			
			
			
			
		}
		else
		{
			Intent in = new Intent(getApplicationContext(), Login.class);
			startActivity(in);
			super.onBackPressed();
		}
		

//	End Create	
	}

	public void onChangePassword(String _SUserName,String _SCurrentPass, String _SNewPass ) {
		pStatusinternet = new General(getApplicationContext());
		if (pStatusinternet.CheckingInternet()) {
			Mycon = new ConnectionSQL(getApplicationContext()).ConnSwire();

			ResultSet rus;
			try {
				Statement Statement1 = Mycon.createStatement();
				String SSQL = "STAndroid_BarcodeScan_ChangePassword N'" + _SUserName +"',N'"+_SCurrentPass +"',N'"+ _SNewPass+"'";
				rus = Statement1.executeQuery(SSQL);
				rus.next();
//				Toast.makeText(getApplicationContext(), SSQL, Toast.LENGTH_SHORT).show();
				txtNote.setVisibility(View.VISIBLE);
				txtNote.setText(rus.getString("Results"));
				
				txtCurrentPass.setText("");
				txtNewPass.setText("");
				txtConfirmPass.setText("");
			} catch (java.sql.SQLException e) {
//				Log.e("Exception ", e.getMessage());
				Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast MsgInternet = Toast.makeText(getApplicationContext(),
					"Not Access Internet.", Toast.LENGTH_SHORT);
			MsgInternet.show();
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
	
	
	
//End Class
}
