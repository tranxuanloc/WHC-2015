package scan_nhaphang;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;
import general.swipemenulayout.SwipeMenuListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

//import scan_do.Scan_DispatchingOrder;
//import scan_do.Scan_DispatchingOrder.AsyncDataUpdate;
//import scan_do.Scan_DispatchingOrder.AsyncStatusUpdate;
import scs.whc.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import database.UserLogin;

//TC70
import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.EMDKManager.EMDKListener;
import com.symbol.emdk.EMDKManager.FEATURE_TYPE;
import com.symbol.emdk.EMDKResults;
import com.symbol.emdk.barcode.BarcodeManager;
import com.symbol.emdk.barcode.BarcodeManager.DeviceIdentifier;
import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.ScanDataCollection.LabelType;
import com.symbol.emdk.barcode.ScanDataCollection.ScanData;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.barcode.Scanner.DataListener;
import com.symbol.emdk.barcode.Scanner.StatusListener;
import com.symbol.emdk.barcode.Scanner.TriggerType;
import com.symbol.emdk.barcode.ScannerConfig;
import com.symbol.emdk.barcode.ScannerException;
import com.symbol.emdk.barcode.ScannerResults;
import com.symbol.emdk.barcode.StatusData;
import com.symbol.emdk.barcode.StatusData.ScannerStates;
//TC70
//public class Scan_ReceivingOrders extends Activity {
public class Scan_ReceivingOrders extends Activity implements EMDKListener,
StatusListener, DataListener {
	//TC70
	// Declare a variable to store EMDKManager object
	private EMDKManager emdkManager = null;

	// Declare a variable to store Barcode Manager object
	private BarcodeManager barcodeManager = null;

	// Declare a variable to hold scanner device to scan
	private Scanner scanner = null;

	// Text view to display status of EMDK and Barcode Scanning Operations
	private TextView statusTextView = null;

	// Edit Text that is used to display scanned barcode data
	private EditText dataView = null;

	// Boolean to explain whether the scanning is in progress or not at any
	// specific point of time
	boolean isScanning = false;
	//TC70
	
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int SCAN_CAMERA = 10;
	private static final int SCAN_LIGHT = 20;
	private DataAdapter_Scan_DSReceivingOrders SAdCheck;
	private Button CmdScan_Camera;
	Activity _activity;
	Connection Mycon;
	TextView txtOrder, txtType, txtTotalSelect, txtRD,txtCartonID;
	SwipeMenuListView LV_Scan_DSReceivingOrders;
	private TextView txtReceivingOrdersRO;
	private Button CmdScan_Scan;
	private DrawerLayout drawerLayout;
	private SwipeRefreshLayout mSwipeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_scanreceivingorder);
		getActionBar().setTitle("Nhập hàng (DS)");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor(getResources().getString(
						R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);

		Intent myIntent = getIntent();
		String SOrderNumber = myIntent.getStringExtra("OrderNumber");
		
		_activity = Scan_ReceivingOrders.this;
		LV_Scan_DSReceivingOrders = (SwipeMenuListView) findViewById(R.id.LV_DSReceivingOrders_Scan);
		CmdScan_Camera = (Button) findViewById(R.id.Cmd_DSReceivingOrders_Camera);
		CmdScan_Scan = (Button) findViewById(R.id.Cmd_DSReceivingOrders_Light);
		txtReceivingOrdersRO = (EditText) findViewById(R.id.txt_ReceivingOrders_Edittext);
		txtOrder = (TextView) findViewById(R.id.txtFragment_Scan_DSReceivingOrders_OrderNumber);
		txtType = (TextView) findViewById(R.id.txtFragment_Scan_DSReceivingOrders_Type);
		txtTotalSelect = (TextView) findViewById(R.id.txt_Fragment_Scan_DSReceivingOrders_TotalSelect);
		txtRD = (TextView) findViewById(R.id.txtFragment_Scan_DSReceivingOrders_RD);
		txtCartonID = (TextView) findViewById(R.id.txtFragment_Scan_DSReceivingOrders_CartonIDSelect);
		
		/**
		 * On Click Scan Camera
		 */
		CmdScan_Camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE","QR_CODE_MODE");
				startActivityForResult(intent,CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
			}
		});
		
		/**
		 * Refresh layout
		 */
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
//					new LoadListView_AsyncTask(_activity,
//							LV_Scan_DSReceivingOrders).execute(txtType.getText().toString() + txtOrder.getText().toString());
				mSwipeLayout.setRefreshing(false);
			}
		});
		LV_Scan_DSReceivingOrders.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
			        int visibleItemCount, int totalItemCount) {
			    boolean enable = false;
			    if(LV_Scan_DSReceivingOrders != null && LV_Scan_DSReceivingOrders.getChildCount() > 0){
			        // check if the first item of the list is visible
			        boolean firstItemVisible = LV_Scan_DSReceivingOrders.getFirstVisiblePosition() == 0;
			        // check if the top of the first item is visible
			        boolean topOfFirstItemVisible = LV_Scan_DSReceivingOrders.getChildAt(0).getTop() == 0;
			        // enabling or disabling the refresh layout
			        enable = firstItemVisible && topOfFirstItemVisible;
			    }
			    mSwipeLayout.setEnabled(enable);
			}});
		CmdScan_Scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (CmdScan_Scan.getText().toString().compareTo("Scan") == 0) {
					StartScan1D();
					CmdScan_Scan.setText("Stop");

				} else {

					// Release
					Bundle bundle = new Bundle();
					bundle.putBoolean("scan", false);
					Intent mIntent = new Intent().setAction(
							"unitech.scanservice.software_scankey").putExtras(
							bundle);
					_activity.sendBroadcast(mIntent);
					CmdScan_Scan.setText("Scan");
				}
			}
		});

		if (SOrderNumber.trim().length() > 0)
		{
			processingResult(SOrderNumber,SCAN_CAMERA);
		}
		// End Create
		
		//2015-11-24 buu
		//Symbol TC70
		// Reference to UI elements
		statusTextView = (TextView) findViewById(R.id.textViewStatus);
		//buu
		//dataView = (EditText) findViewById(R.id.editText1);
		dataView = (EditText) findViewById(R.id.editTextDataScanned);
		// The EMDKManager object will be created and returned in the callback.
		EMDKResults results = EMDKManager.getEMDKManager(
				getApplicationContext(), this);
		// Check the return status of getEMDKManager and update the status Text
		// View accordingly
		if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
			statusTextView.setText("EMDKManager Request Failed");
		}
		//Symbol TC70
	}
/*buu-2015-11-24
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
	if (keyCode == KeyEvent.KEYCODE_ENTER)
	{
//		new General(_activity).showAlert(_activity,txtReceivingOrdersRO.getText().toString().trim());
		if (txtReceivingOrdersRO.getText().toString().trim().length() == 11) {
			processingResult(txtReceivingOrdersRO.getText().toString().trim(),SCAN_LIGHT);
			CmdScan_Scan.setText("Scan");
		}
	}

		return super.onKeyUp(keyCode, event);
	}
*/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// rootView
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				String ScanResult = data.getStringExtra("SCAN_RESULT");

				if (ScanResult.toString().trim().length() == 11) {
					processingResult(ScanResult,SCAN_CAMERA);
				}
			}
		}
	}

	/**
	 * processing
	 * 
	 * @return
	 */
	private void processingResult(String PScanResult,int pTypeScan) {
		
		txtReceivingOrdersRO.setText("");
		String SType = PScanResult.substring(0, 2);
		String SOrderNumber = ""
				+ Integer.parseInt(PScanResult.replace(SType, ""));
		
		switch (SType) {
		case "LO":
			if (txtType.getText().toString().trim().equals("CT")
					|| txtType.getText().toString().trim().equals("PL")) {
				// Toast.makeText(_activity, SType, Toast.LENGTH_SHORT).show();
				showSettingsAlert(SOrderNumber, txtCartonID.getText().toString()
						.trim(), txtType.getText().toString().trim());
			} else {
				txtOrder.setText(SOrderNumber);
				txtType.setText(SType);
			}
			break;
		case "RD":
			txtType.setText(SType);
			txtOrder.setText(SOrderNumber);
			if (txtRD.getText().toString().trim().length() > 0)
			{
				new LoadListView_AsyncTask(_activity, LV_Scan_DSReceivingOrders)
				.execute("N'" + PScanResult + "'" );
			}
			else
			{
				new LoadListView_AsyncTask(_activity, LV_Scan_DSReceivingOrders)
				.execute("N'" + PScanResult + "'");
			}
			break;
		case "CT":
			txtType.setText(SType);
			txtOrder.setText(SOrderNumber);
			if (txtRD.getText().toString().trim().length() > 0)
			{
				byte TotalCarton = Byte.parseByte(txtTotalSelect.getText().toString());
				if (TotalCarton < 5)
				{
					new LoadListView_AsyncTask(_activity, LV_Scan_DSReceivingOrders)
					.execute("N'" + PScanResult + "'," + txtRD.getText().toString().trim());
				}
				else
				{
					new General(_activity).showAlert(_activity, "Bạn đã chọn 5 thùng, vui lòng chọn vị trí.");
				}
			
			}
			else
			{
				new LoadListView_AsyncTask(_activity, LV_Scan_DSReceivingOrders)
				.execute("N'" + PScanResult + "'");
			}
//			if (pTypeScan == SCAN_LIGHT)
//			{
//				CmdScan_Scan.setText("Stop");
//				StartScan1D();
//			}
//			else
//			{
//				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
//				intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE","QR_CODE_MODE");
//				startActivityForResult(intent,CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
//			}
			break;
		case "PL":
			txtType.setText(SType);
			txtOrder.setText(SOrderNumber);
			new LoadListView_AsyncTask(_activity, LV_Scan_DSReceivingOrders)
					.execute("N'" + PScanResult + "'" );
			break;
		default:
			break;
		}
	}

	/**
	 * Alert Dailog
	 */
	public void showSettingsAlert(final String pLocationNumber,
			final String pCartonID, final String PType) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(_activity);

		// Setting Dialog Title
		alertDialog.setTitle("Cập nhật vị trí.");

		// Setting Dialog Message
		if (PType.equals("CT")) {
			alertDialog.setMessage("Bạn có muốn cập nhật carton " + pCartonID
					+ " này vào vị trí " + pLocationNumber + " hay không?");
		}
		if (PType.equals("PI")) {
			alertDialog.setMessage("Bạn có muốn cập nhật pallet " + pCartonID
					+ " này vào vị trí " + pLocationNumber + " hay không?");
		}

		// On pressing Settings button
		alertDialog.setPositiveButton("Cập nhật",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						ExecSP_SQL(pLocationNumber, pCartonID, PType);
						
						switch (txtType.getText().toString().trim()) {
						case "CT":
							new LoadListView_AsyncTask(_activity,
									LV_Scan_DSReceivingOrders).execute("N'RD0" + txtRD.getText().toString().trim() + "'");
							break;
						case "PI":
							new LoadListView_AsyncTask(_activity,
									LV_Scan_DSReceivingOrders).execute("N'PL0" + txtRD.getText().toString().trim() + "'");
							break;

						default:
							new LoadListView_AsyncTask(_activity, LV_Scan_DSReceivingOrders)
							.execute("N'" + PType+ pCartonID + "'");
							break;
						}

//						LV_Scan_DSReceivingOrders.onRefreshComplete();
						// Toast.makeText(_activity, "Cập nhật thành công.",
						// Toast.LENGTH_SHORT).show();
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

	private void ExecSP_SQL(String pLocationNumber, String pCartonID,
			String PType) {
		boolean Status = false;
		String StringCmd = "";
		UserLogin User = new UserLogin(getApplicationContext());
		ConnectionSQL SQLCmd = new ConnectionSQL(getApplicationContext());
		if (PType.equals("CT")) {
			String[] _String = pCartonID.split("/");
			for (String Pstring : _String) {
				StringCmd = "STAndroid_BarcodeScan_UpdateLocation N'"
						+ pLocationNumber + "'," + Pstring + ",N'"
						+ User.getUser() + "'";
				Status = SQLCmd.ExecuteString(StringCmd);
				if (!Status) {
					new General(_activity).showAlert(_activity,
							"Cập nhật không thành công vui lòng kiểm tra lại");
				}
			}
			
		} else if (PType.equals("PI")) {
			// StringCmd = "STAndroid_BarcodeScan_UpdateLocation N'"+
			// pLocationNumber + "'," + pCartonID + ",N'"+ User.getUser() +"'";
		}

	}

	private void StartScan1D() {
		// Press:
		Bundle bundle = new Bundle();
		bundle.putBoolean("scan", true);
		Intent mIntent = new Intent().setAction(
				"unitech.scanservice.software_scankey").putExtras(bundle);
		_activity.sendBroadcast(mIntent);
	}

	/**
	 * menu
	 */

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
	class LoadListView_AsyncTask extends AsyncTask<String, String, String> {
		private ProgressDialog mProgressDialog;
		ProgressDialog dialog;
		ListView _LV_ScanDSR;
		Activity _Activity;
		private General pStatusinternet;
		private Connection Mycon;
		int _TotalSelect = 0;
		String _CartonSelectID = "";
		String _ReceivingOrder = "";
		LoadListView_AsyncTask(Activity PActivity, ListView PLV_ScanDSR){
			_Activity = PActivity;
			_LV_ScanDSR=PLV_ScanDSR;
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
		String pString = new String(aurl[0]);
		
		pStatusinternet = new General(_Activity);
		if (pStatusinternet.CheckingInternet()) {
			Mycon = new ConnectionSQL(_Activity).ConnSwire();
				if (Mycon != null)
				{
						ResultSet rus;
						Statement Statement1;
							try {
								Statement1 = Mycon.createStatement();
								rus = Statement1.executeQuery("STAndroid_BarcodeScan_ReceiveDetails " + pString );
								ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
								
								while (rus.next()) {
									HashMap<String, String> datacolum = new HashMap<String, String>();
									_ReceivingOrder = rus.getString("DSROID");
									datacolum.put("PalletID", rus.getString("PalletID"));
									datacolum.put("CustomerRef", rus.getString("CustomerRef"));
									datacolum.put("ProductNumber", rus.getString("ProductNumber"));
									datacolum.put("CartonDescription", rus.getString("ProductName"));
									datacolum.put("LocationNumber", rus.getString("LocationNumber"));
									datacolum.put("CurrentQuantity", rus.getString("CurrentQuantity"));
									datacolum.put("RO", rus.getString("RO"));
									datacolum.put("DSReceivingOrderDate", rus.getString("DSReceivingOrderDate"));
									datacolum.put("CustomerNumber", rus.getString("CustomerNumber"));
									datacolum.put("CartonID", rus.getString("CartonID"));
									datacolum.put("Dispatched", rus.getString("Dispatched"));
									datacolum.put("RecordStatus", rus.getString("RecordStatus"));
									datacolum.put("RecordFirst", rus.getString("RecordFirst"));
									datacolum.put("LocationID", rus.getString("LocationID"));
									if (rus.getString("ScannedTime")== null)
									{
										datacolum.put("ScannedTime", "");
									}
									else
									{
										datacolum.put("ScannedTime","-" + new General(_Activity).FormatDateIn_ddMMHHMM(rus.getString("ScannedTime")) );
									}
									if (rus.getString("Status")== null)
									{
										datacolum.put("Status", "0");
									}
									else
									{
										datacolum.put("Status", rus.getString("Status"));
									}
									
									if (rus.getString("ScannedUser") == null)
									{
										datacolum.put("ScannedUser", "");
									}
									else
									{
										datacolum.put("ScannedUser", rus.getString("ScannedUser"));
									}
									
									if (rus.getString("RecordFirst").equals("1"))
									{
										_TotalSelect = _TotalSelect + 1;
										if (_CartonSelectID.equals(""))
										{
											_CartonSelectID = rus.getString("PalletID");
										}
										else
										{
											_CartonSelectID = _CartonSelectID + "/"+ rus.getString("PalletID");
										}
									}
									
									data.add(datacolum);
								}
								SAdCheck = new DataAdapter_Scan_DSReceivingOrders(_Activity,data);
								
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
		protected void onPostExecute(String unused) {//tin
			mProgressDialog.dismiss();
			if (unused== null)
			{
				_LV_ScanDSR.setAdapter(SAdCheck);
				txtTotalSelect.setText(""+ _TotalSelect);
				getActionBar().setTitle("Tổng "+SAdCheck.getCount() + "Cnt");
				txtRD.setText(_ReceivingOrder);
				txtCartonID.setText(_CartonSelectID);
			}
			else
			{
				new General(_Activity).showAlert(_Activity, unused);
			}
		}

	}


	
	
	
	//2015-11-24 buu
	//Motorola Symbol TC70
		@Override
		protected void onDestroy() {
		super.onDestroy();
		if (emdkManager != null) {

			// Clean up the objects created by EMDK manager
			emdkManager.release();
			emdkManager = null;
		}
		}

		@Override
		protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			if (scanner != null) {
				// releases the scanner hardware resources for other application
				// to use. You must call this as soon as you're done with the
				// scanning.
				scanner.disable();
				scanner = null;
			}
		} catch (ScannerException e) {
			e.printStackTrace();
		}
		}
		
		@Override
		public void onClosed() {
		// TODO Auto-generated method stub
		// The EMDK closed abruptly. // Clean up the objects created by EMDK
		// manager
		if (this.emdkManager != null) {

			this.emdkManager.release();
			this.emdkManager = null;
		}
		}

		@Override
		public void onOpened(EMDKManager emdkManager) {
		// TODO Auto-generated method stub
		this.emdkManager = emdkManager;
		// Method call to set some decoder parameters to scanner
		setScannerParameters();

		// Toast to indicate that the user can now start scanning
		Toast.makeText(Scan_ReceivingOrders.this,
				"Press Hard Scan Button to start scanning...",
				Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onData(ScanDataCollection scanDataCollection) {
		// TODO Auto-generated method stub
		// Use the scanned data, process it on background thread using AsyncTask
		// and update the UI thread with the scanned results
		new AsyncDataUpdate().execute(scanDataCollection);

		}

		// Update the scan data on UI
		int dataLength = 0;

		// AsyncTask that configures the scanned data on background
		// thread and updated the result on UI thread with scanned data and type of
		// label
		private class AsyncDataUpdate extends
			AsyncTask<ScanDataCollection, Void, String> {

		@Override
		protected String doInBackground(ScanDataCollection... params) {
			ScanDataCollection scanDataCollection = params[0];

			// Status string that contains both barcode data and type of barcode
			// that is being scanned
			String statusStr = "";

			// The ScanDataCollection object gives scanning result and the
			// collection of ScanData. So check the data and its status
			if (scanDataCollection != null
					&& scanDataCollection.getResult() == ScannerResults.SUCCESS) {

				ArrayList<ScanData> scanData = scanDataCollection.getScanData();

				// Iterate through scanned data and prepare the statusStr
				for (ScanData data : scanData) {
					// Get the scanned data
					String barcodeData = data.getData();
					// Get the type of label being scanned
					LabelType labelType = data.getLabelType();
					// Concatenate barcode data and label type
					//buu statusStr = barcodeData + " " + labelType;
					//String deviceName = android.os.Build.MODEL;
					statusStr = barcodeData;
					
					//ScanningFunction();
				}
			}

			// Return result to populate on UI thread
			return statusStr;
		}

		@Override
		protected void onPostExecute(String result) {
			// Update the dataView EditText on UI thread with barcode data and
			// its label type
			if (dataLength++ > 11) {
				// Clear the cache after 50 scans
				dataView.getText().clear();
				dataLength = 0;
			}
			//dataView.append(result + "\n");
			dataView.setText(result);
			//buu
			//ScanningFunction(result);
			processingResult(result,SCAN_LIGHT);
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		
		}//End class AsyncDataUpdate

		@Override
		public void onStatus(StatusData statusData) {
		// TODO Auto-generated method stub
		// process the scan status event on the background thread using
		// AsyncTask and update the UI thread with current scanner state
		new AsyncStatusUpdate().execute(statusData);

		}

		// AsyncTask that configures the current state of scanner on background
		// thread and updates the result on UI thread
		private class AsyncStatusUpdate extends AsyncTask<StatusData, Void, String> {

		@Override
		protected String doInBackground(StatusData... params) {
			// Get the current state of scanner in background
			StatusData statusData = params[0];
			String statusStr = "";
			ScannerStates state = statusData.getState();
			// Different states of Scanner
			switch (state) {
			// Scanner is IDLE
			case IDLE:
				//statusStr = "The scanner enabled and its idle";
				statusStr = "The scanner enabled";
				isScanning = false;
				break;
			// Scanner is SCANNING
			case SCANNING:
				statusStr = "Scanning...";
				isScanning = true;
				break;
			// Scanner is waiting for trigger press
			case WAITING:
				statusStr = "Waiting for trigger press...";
				break;
			default:
				break;
			}
			// Return result to populate on UI thread
			return statusStr;
		}

		@Override
		protected void onPostExecute(String result) {
			// Update the status text view on UI thread with current scanner
			// state
			statusTextView.setText(result);
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
		}

		// This is a callback method when user presses any hardware button on the
		// device
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {

		// check for scanner hard key press.
		if ((keyCode == KeyEvent.KEYCODE_BUTTON_L1)
				|| (keyCode == KeyEvent.KEYCODE_BUTTON_R1)) {

			// Skip the key press if the repeat count is not zero.
			if (event.getRepeatCount() != 0) {
				return true;
			}

			try {
				if (scanner == null) {
					initializeScanner();
				}

				if ((scanner != null) && (isScanning == false)) {
					// Starts an asynchronous Scan. The method will not turn on
					// the scanner. It will, however, put the scanner in a state
					// in which the scanner can be turned ON either by pressing
					// a hardware trigger or can be turned ON automatically.
					scanner.read();
				}

			} catch (Exception e) {
				// Display if there is any exception while performing operation
				statusTextView.setText(e.getMessage());
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
		}

		// This is a callback method when user releases any hardware button on the
		// device
		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			
		// check for scanner trigger key press.
		if ((keyCode == KeyEvent.KEYCODE_BUTTON_L1)
				|| (keyCode == KeyEvent.KEYCODE_BUTTON_R1)) {

			// Skip the key press if the repeat count is not zero.
			if (event.getRepeatCount() != 0) {
				return true;
			}

			try {
				if ((scanner != null) && (isScanning == true)) {
					// This Cancels any pending asynchronous read() calls
					scanner.cancelRead();
				}
			} catch (Exception e) {
				statusTextView.setText(e.getMessage());
			}
			return true;
		}
		
		return super.onKeyUp(keyCode, event);
		}

		// Method to set some decoder parameters in the ScannerConfig object
		public void setScannerParameters() {
		try {

			if (scanner == null) {
				// Method call to initialize the scanner parameters
				initializeScanner();
			}

			ScannerConfig config = scanner.getConfig();
			// Set the code128
			config.decoderParams.code128.enabled = true;
			// set code39
			config.decoderParams.code39.enabled = true;
			// set UPCA
			config.decoderParams.upca.enabled = true;
			scanner.setConfig(config);

		} catch (Exception e) {
			statusTextView.setText(e.getMessage());
		}
		}

		// Method to initialize and enable Scanner and its listeners
		private void initializeScanner() throws ScannerException {

		if (scanner == null) {

			// Get the Barcode Manager object// buu ADD Barcode type: Code 128/39...
			barcodeManager = (BarcodeManager) this.emdkManager
					.getInstance(FEATURE_TYPE.BARCODE);

			// Get default scanner defined on the device
			scanner = barcodeManager.getDevice(DeviceIdentifier.DEFAULT);
			// scanner = barcodeManager.getDevice(list.get(0));
			
			// Add data and status listeners
			scanner.addDataListener(this);
			scanner.addStatusListener(this);

			// The trigger type is set to HARD by default and HARD is not
			// implemented in this release.
			// So set to SOFT_ALWAYS
			scanner.triggerType = TriggerType.SOFT_ALWAYS;

			// Enable the scanner
			scanner.enable();
		}

		}
	// End Class

}
