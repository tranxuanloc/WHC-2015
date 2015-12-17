package scs.whc;

import general.function.ConnectionSQL;
import general.function.General;
import general.swipemenulayout.SwipeMenuListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import database.UserLogin;

public class WorkerEmployeeID_InPut extends Activity {

	private General pStatusinternet;
	private Connection Mycon;
	private UserLogin User;
	private ArrayAdapter<String> WorkerIDAdapter;
	private AutoCompleteTextView AutoCPWorkerID;
	private TextView txtOrderNumber, txtDate;
	private String OrderNumber;
	private SwipeMenuListView LV_WorkerEmployee;
	private Activity _Activity;
	private Button CmdOK;

	
	float historicX = Float.NaN, historicY = Float.NaN;
	private String SDate;
	static final int DELTA = 50;
	enum Direction {LEFT, RIGHT;}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_workeremployeeidinput);
		
		/**
		 *  Actionbar Custome
		 */
				getActionBar().setTitle("Phiếu Của Bạn");
				getActionBar().setDisplayHomeAsUpEnabled(true);
				getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
//				NavigationDrawer pNavigationDrawe = new NavigationDrawer();
//				pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
//				drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
				_Activity = WorkerEmployeeID_InPut.this;
				txtOrderNumber = (TextView)findViewById(R.id.txt_WorkerEmployeeIDInput_OrderNumber);
				txtDate = (TextView)findViewById(R.id.txt_WorkerEmployeeIDInput_DateTime);
				
				LV_WorkerEmployee = (SwipeMenuListView)findViewById(R.id.LVMyOrdersToDay);
				CmdOK = (Button)findViewById(R.id.Cmd_WorkerEmployeeIDInput_Confirm);
				
				Intent Myintent = getIntent();
				OrderNumber = Myintent.getStringExtra("OrderNumber");
				SDate = Myintent.getStringExtra("Date");
				txtOrderNumber.setText(OrderNumber);
				txtDate.setText(SDate);
				if (OrderNumber.length() > 0)
				{
					new LoadListView_AsyncTask(_Activity).execute(OrderNumber);
				}
				
		WorkerIDAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, GetEmployee("2"));
		AutoCPWorkerID = (AutoCompleteTextView) findViewById(R.id.AutoCP_WorkerEmployeeIDInput_EmployssID);
		AutoCPWorkerID.setAdapter(WorkerIDAdapter);
		CmdOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (AutoCPWorkerID.getText().toString().trim().length() > 0)
				{
					if (Exec_Cmd(AutoCPWorkerID.getText().toString().trim(), txtOrderNumber.getText().toString().trim()))
					{
						new LoadListView_AsyncTask(_Activity).execute(OrderNumber);
					}
					else
					{
						AutoCPWorkerID.setText("");
					}
				}
				else
				{
					new General(_Activity).showAlert(_Activity,
							"Vui lòng nhập ID nhân viên");
				}
			}
		});   
		
		LV_WorkerEmployee.setOnTouchListener(new OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) 
		    {
		        // TODO Auto-generated method stub
		        switch (event.getAction()) 
		        {
		            case MotionEvent.ACTION_DOWN:
		            historicX = event.getX();
		            historicY = event.getY();
		            break;

		            case MotionEvent.ACTION_UP:
		            if (event.getX() - historicX < -DELTA) 
		            {
//		                FunctionDeleteRowWhenSlidingLeft();
		                return true;
		            }
		            else if (event.getX() - historicX > DELTA)  
		            {
//		                FunctionDeleteRowWhenSlidingRight();
		                return true;
		            } break;
		            default: return false;
		        }
		        return false;
		    }
		});
		
		// End Create
	}
	private boolean Exec_Cmd(String PEmployeeID,String POrderID) {
		 User = new UserLogin(getApplicationContext());
		ConnectionSQL SQLCmd = new ConnectionSQL(getApplicationContext());

		String  StringCmd = "STAndroid_EmployeeWorkingAssignInsert "+ PEmployeeID + ",N'" + POrderID + "',N'" + User.getUser() + "',N'Kiem hang'";
		new General(_Activity).showAlert(_Activity, StringCmd);
		Log.e("123123123", StringCmd);
		boolean Status = SQLCmd.ExecuteStringSwire(StringCmd);
		if (!Status)
		{
			
			new General(_Activity).showAlert(_Activity,
					"Cập nhật không thành công vui lòng kiểm tra lại");
		}
		return Status;
	
	}
	
	 private ArrayList<String> GetEmployee(String CommandoSQL) {
		 ArrayList<String>  List = new ArrayList<String>();
			pStatusinternet = new General(getApplicationContext());
			if (pStatusinternet.CheckingInternet()) {
				Mycon = new ConnectionSQL(getApplicationContext()).ConnSwire();
				if (Mycon !=null)
				{
					ResultSet rus;
					try {
						 User = new UserLogin(getApplicationContext());
						Statement Statement1 = Mycon.createStatement();
						rus = Statement1.executeQuery("STAndroid_EmployeePresent " + CommandoSQL );
						List.add("ID");
						while (rus.next()) {
							List.add(rus.getString("EmployeeID"));
						}
					} catch (java.sql.SQLException e) {
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Kết nối database không thành công", Toast.LENGTH_SHORT).show();
				}
				
			} else {
				Toast MsgInternet = Toast.makeText(getApplicationContext(),
						"Not Access Internet.", Toast.LENGTH_SHORT);
				MsgInternet.show();
			}
			return List;
		}
	
	 /**
		 * Menu
		 */
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu_45_123, menu);
			return super.onCreateOptionsMenu(menu);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			switch (item.getItemId()) {
			case R.id.Action_Setting:
//				Toast.makeText(this, "Settings", Toast.LENGTH_SHORT)
//						.show();
//				if (drawerLayout.isDrawerVisible(Gravity.START)) {
//					drawerLayout.closeDrawer(Gravity.START);
//				} else {
//					drawerLayout.openDrawer(Gravity.START);
//				}
				break;
			case android.R.id.home:
//				Toast.makeText(this, "home", Toast.LENGTH_SHORT)
//						.show();
				onBackPressed();
				finish();
				break;
			case R.id.Action_ChangePlace:
				
				break;
			default:
				break;
			}
			return super.onOptionsItemSelected(item);
		}

	 
		class LoadListView_AsyncTask extends AsyncTask<String, String, String> {
			private ProgressDialog mProgressDialog;
			ProgressDialog dialog;
			Activity _Activity;
			private DataAdapter_WorkerEmployeeID_Input DAter;
			LoadListView_AsyncTask(Activity PActivity ){
				_Activity = PActivity;
			}
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mProgressDialog = new ProgressDialog(_Activity);
				mProgressDialog.setIndeterminate(true);
				mProgressDialog.setCancelable(true);
				mProgressDialog.show();
				mProgressDialog.setContentView(R.layout.layout_my_progress);
			}

			@SuppressLint("SdCardPath") @Override
			protected String doInBackground(String... aurl) {
				String result = null;
				String CommandoSQL = new String(aurl[0]);
			
			pStatusinternet = new General(_Activity);
			if (pStatusinternet.CheckingInternet()) {
				Mycon = new ConnectionSQL(_Activity).ConnSwire();
					if (Mycon != null)
					{
							ResultSet rus;
								Statement Statement1;
								try {
									Statement1 = Mycon.createStatement();
									rus = Statement1.executeQuery("STEmployeeWorkingView N'" + CommandoSQL +"'");
									ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
									// day dl len listview
									while (rus.next()) {
										HashMap<String, String> datacolum = new HashMap<String, String>();
//										datacolum.put("DispatchingOrderID", rus.getString("DispatchingOrderID"));
										datacolum.put("EmployeeID", rus.getString("EmployeeID"));
										datacolum.put("Percentage", rus.getString("Percentage"));
										datacolum.put("OrderNumber", rus.getString("OrderNumber"));
										datacolum.put("Remark", rus.getString("Remark"));
										datacolum.put("EmployeeName", rus.getString("EmployeeName"));
										datacolum.put("ProductionQuantity", rus.getString("ProductionQuantity"));
										
										data.add(datacolum);
										
									}
									DAter = new DataAdapter_WorkerEmployeeID_Input(getApplicationContext(), data);

								
									//End
									} catch (SQLException e) {
									result = e.getMessage().toString();
								}
					}
					else
					{
						result ="Không thể kết nối được dữ liệu, vui lòng thử lại sau.";
					}
			} else {
				result ="Không có mạng, vui lòng kiểm tra wifi.";
			}
			
			
			return result;

			}
			protected void onProgressUpdate(String... progress) {
				 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
			}

			@Override
			protected void onPostExecute(String unused) {
				mProgressDialog.dismiss();
				if (unused== null)
				{
					LV_WorkerEmployee.setAdapter(DAter);
				}
				else
				{
					new General(_Activity).showAlert(_Activity, unused);
				}
			}
		}
	 
	// End Class
}
