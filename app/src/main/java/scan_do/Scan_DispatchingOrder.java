package scan_do;

import general.function.ConnectionSQL;
import general.function.NavigationDrawer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.EMDKResults;
import com.symbol.emdk.barcode.BarcodeManager;
import com.symbol.emdk.barcode.Scanner;

import palletchecking.PalletChecking;
//import scan_do.Scan_DispatchingOrder.AsyncDataUpdate;
//import scan_do.Scan_DispatchingOrder.AsyncStatusUpdate;
import scs.whc.R;
import takepicture.Note_TakePhoto;
import workerinput.WorkerTimeInput;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import database.UserLogin;

//TC70
//import java.util.ArrayList;
//import android.app.Activity;
import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;

//import com.symbol.basicscanningtutorial.MainActivity;
//import com.symbol.basicscanningtutorial.MainActivity.AsyncDataUpdate;
//import com.symbol.basicscanningtutorial.MainActivity.AsyncStatusUpdate;
//import scan_do.Scan_DispatchingOrder;
//import scan_do.Scan_DispatchingOrder.AsyncDataUpdate;
//import scan_do.Scan_DispatchingOrder.AsyncStatusUpdate;
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

//public class Scan_DispatchingOrder extends Activity {
public class Scan_DispatchingOrder extends Activity implements EMDKListener,
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
	
	// Button EXECUTAR;
	// EditText ValorBusca;
	DrawerLayout drawerLayout;
	ListView Lista;
	Connection connect;
	SimpleAdapter AD;
	Button varScan3D;
	ArrayList<HashMap<String, String>> data1 = new ArrayList<HashMap<String, String>>();

	ArrayList<HashMap<String, String>> data2 = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> data_check = new ArrayList<HashMap<String, String>>();
	ListView list_head;
	ArrayList<HashMap<String, String>> mylist_title;
	ListAdapter adapter_title;
	HashMap<String, String> map1;
	public static final String START_SCANSERVICE = "unitech.scanservice.start";

	private void inicializar() {
		 connect = new ConnectionSQL(getApplicationContext()).ConnSwire();
		list_head = (ListView) findViewById(R.id.list_Header);
	}

	// scan PI co layout khac
	public void scan_PalletID(String varPalletID) {
		ResultSet rs;
		PreparedStatement cs = null;
		try {
			// Display the headings
			mylist_title = new ArrayList<HashMap<String, String>>();
			map1 = new HashMap<String, String>();

			map1.put("A", "Location");
			map1.put("B", "ProductID");
			map1.put("C", "Product Name");
			map1.put("D", "CustomerID");
			map1.put("E", "Qty");
			map1.put("G", "RO");
			mylist_title.add(map1);

			try {
				adapter_title = new SimpleAdapter(this, mylist_title,
						R.layout.items_scan_palletdetails, new String[] { "A", "B", "C", "D",
								"E", "G" }, new int[] {
								R.id.txt_LocationNumber,
								R.id.txt_ProductNumber, 
								R.id.txt_ProductName,
								R.id.txt_CustomerNumber,
								R.id.txt_CurrentQuantity, 
								R.id.txt_RO });
				list_head.setAdapter(adapter_title);
			} catch (Exception e) {
			}
			// End Display the headings

			cs = connect
					.prepareStatement("exec STAndroid_BarcodeScan_PalletIDFind ?");
			cs.setEscapeProcessing(true);
			cs.setQueryTimeout(90);

			cs.setString(1, varPalletID);
			// cs.registerOutParameter(2, Types.VARCHAR);
			rs = cs.executeQuery();

			while (rs.next()) {
				HashMap<String, String> datanum = new HashMap<String, String>();
				datanum.put("A", rs.getString("LocationNumber"));
				datanum.put("B", rs.getString("ProductNumber"));
				datanum.put("C", rs.getString("ProductName"));
				datanum.put("D", rs.getString("CustomerNumber"));
				datanum.put("E", rs.getString("CurrentQuantity"));
				datanum.put("F", rs.getString("RO"));

				data1.add(datanum);
			}

			Adap AD = new Adap(getApplicationContext(), data1);
			Lista.setAdapter(AD);

		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
	}

	public void scan_Label(String varDO, String varOrderType) {
		
		ResultSet rs;
		PreparedStatement cs = null;
		data_check.clear();
		try {
			// /**********Display the headings************/
			mylist_title = new ArrayList<HashMap<String, String>>();
			map1 = new HashMap<String, String>();

			map1.put("AA", "Plt ID");
			map1.put("BB", "Pro ID");
			map1.put("CC", "Product Name");
			map1.put("DD", "Qty");
			map1.put("EE", "X");
			mylist_title.add(map1);

			try {
				adapter_title = new SimpleAdapter(this, mylist_title,
						R.layout.do_details, new String[] { "AA", "BB", "CC",
								"DD", "EE" }, new int[] { R.id.txt_PalletID,
								R.id.txt_ProductNumber1, R.id.txt_ProductName1,
								R.id.txt_Quantity, R.id.txt_Result1 });
				list_head.setAdapter(adapter_title);
			} catch (Exception e) {

			}

			cs = connect.prepareStatement("exec STBarcodeScan_Order_Results ?,?");
			cs.setEscapeProcessing(true);
			cs.setQueryTimeout(90);

			int varDOID;
			varDOID = Integer.parseInt(varDO.substring(3, varDO.length()));

			cs.setInt(1, varDOID);
			cs.setString(2, varOrderType);
			rs = cs.executeQuery();
			// List<Map<String, String>> data = null;

			while (rs.next()) {
				
				HashMap<String, String> datanum = new HashMap<String, String>();
				datanum.put("AA", rs.getString("Data_ID"));
				datanum.put("BB", rs.getString("ProductNumber"));
				
				datanum.put("CC", rs.getString("ProductName"));
				
				datanum.put("DD", rs.getString("QuantityOfPackages"));
				datanum.put("EE", rs.getString("Result"));
				
				datanum.put("GG", rs.getString("IsRecordNew"));
				
				if (rs.getString("Result") != null) {
					datanum.put("EE", rs.getString("Result"));
				} else {
					datanum.put("EE", "");
				}
				txtDate.setText(rs.getString("DispatchingOrderDate"));
				data_check.add(datanum);
			}

			Adapter_checklist adp = new Adapter_checklist(
					getApplicationContext(), data_check);
			Lista.setAdapter(adp);
			int tong = 0;
			for (int i = 0; i < data_check.size(); i++) {
				HashMap<String, String> tt = data_check.get(i);
				if (tt.get("EE").equals("OK")) {
					tong += Integer.parseInt(tt.get("DD"));
				}
			}
			tongso.setText(tong + "");


		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
	}

	
	public void Scan_DO(String varScanResult, String varDO, String varUser) {
		PreparedStatement cs = null;
		try {
			cs = connect.prepareStatement("exec STAndroid_BarcodeScan_Dispatch ?, ?, ?");
			cs.setEscapeProcessing(true);
			cs.setQueryTimeout(90);

			cs.setString(1, varScanResult);
			cs.setString(2, varDO);
			cs.setString(3, varUser);
			cs.executeUpdate();

			scan_Label(varDO, varDO.substring(0, 2));

		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
	}
	
	
	@SuppressWarnings("unused")
	private Button scanBtn;
	private TextView txtOrderNumber;// contentTxt, formatTxt,
	private EditText ScanResult1D;
	private TextView tongso;
	private String username_login;
	private Button CmdPhotoNote;
	private TextView txtDate;
	private Intent _Inten;

	// batdau
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_scando);

		getActionBar().setTitle("Scan");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		UserLogin db = new UserLogin(getApplicationContext());
		username_login = db.getUser();

		Lista = (ListView) findViewById(R.id.list_output);
		Lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg3) {
				TextView txt_ID = (TextView) view.findViewById(R.id.txt_PalletID);
				String value_paleetID = txt_ID.getText().toString();
				if (value_paleetID.toString().trim().length() > 0)
				{
					Intent in = new Intent(getApplicationContext(), PalletChecking.class);
					in.putExtra("Intent_PalletID", value_paleetID);
					startActivity(in);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Show Pallet Failed.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		txtDate = (TextView) findViewById(R.id.txt_Date);
		varScan3D = (Button) findViewById(R.id.scan_button);
		tongso = (TextView) findViewById(R.id.txt_tong);
//
//		logout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				DataSQLSccan db = new DataSQLSccan(getApplicationContext());
//				db.resetTables();
//				Intent in = new Intent(getApplicationContext(), Login.class);
//				startActivity(in);
//				finish();
//
//			}
//		});

		ScanResult1D = (EditText) findViewById(R.id.etxtScanResult1D);
		txtOrderNumber = null;

		scanBtn = (Button) findViewById(R.id.scan_button);
		// formatTxt = (TextView)findViewById(R.id.scan_format);
		// contentTxt = (TextView) findViewById(R.id.scan_content);

		txtOrderNumber = (TextView) findViewById(R.id.txtOrderNumber);

		inicializar();
		

		Intent myIntent = getIntent();
		String SDispatchingOrderID = myIntent.getStringExtra("DispatchingOrderID");
		String SType = myIntent.getStringExtra("Type");
		if (SDispatchingOrderID.toString().trim().length()> 0)
		{

			txtOrderNumber.setText(SType + "-" + SDispatchingOrderID);
			Scan_DO(SType + "-" + new DecimalFormat("000000000").format(Integer.parseInt(SDispatchingOrderID)), SType + "-" + SDispatchingOrderID, username_login);
			
		}
		
		CmdPhotoNote = (Button) findViewById(R.id.CmdScanDO_PhotoNote);
		CmdPhotoNote.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					if (txtOrderNumber.getText().toString().trim().length() > 0)
					{
						Intent IntenPhoto;
						IntenPhoto = new Intent(Scan_DispatchingOrder.this,
								Note_TakePhoto.class);
						IntenPhoto.putExtra("OrderID", txtOrderNumber.getText().toString());
						startActivity(IntenPhoto);
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Vui lòng Scan đơn hàng trước khi chụp hình.", Toast.LENGTH_SHORT).show();
					}
			}
		});
		
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

	public void onClickScan(View v) {
		if (v.getId() == R.id.scan_button) {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
			intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE"); 
			startActivityForResult(intent, 0);
			varScan3D.setText("Camera");
		}
	}

	public void StartScan1D() {
		// Press:
		Bundle bundle = new Bundle();
		bundle.putBoolean("scan", true);
		Intent mIntent = new Intent().setAction(
				"unitech.scanservice.software_scankey").putExtras(bundle);
		sendBroadcast(mIntent);
	}

	public void StartScan1D_TAT() {
		// Release:
		Bundle bundle = new Bundle();
		bundle.putBoolean("scan", false);
		Intent mIntent = new Intent().setAction(
				"unitech.scanservice.software_scankey").putExtras(bundle);
		sendBroadcast(mIntent);
	}

	public void onClickScan1D(View v) {
		if (v.getId() == R.id.btn_Scan1D) {
			Button varScan1D = (Button) findViewById(R.id.btn_Scan1D);
			
			if (varScan1D.getText().toString().compareTo("Scan") == 0) {
				StartScan1D();
				varScan1D.setText("Stop");
			
			} else {
				
				// Release
				Bundle bundle = new Bundle();
				bundle.putBoolean("scan", false);
				Intent mIntent = new Intent().setAction(
						"unitech.scanservice.software_scankey").putExtras(bundle);
				sendBroadcast(mIntent);
				varScan1D.setText("Scan");
			}
		}
	}


	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				//String scanContent = intent.getStringExtra("SCAN_RESULT");
				
				String scanContent1D = intent.getStringExtra("SCAN_RESULT");

				if (txtOrderNumber.getText().length() == 0) {// Scan lan dau
					
					// Neu khong scan DO se find PalletID info
					if (scanContent1D.substring(0, 2).compareTo("PI") == 0 || scanContent1D.substring(0, 2).compareTo("CT") == 0){
						//Nhay toi layout khac
						scan_PalletID(scanContent1D.toString());
						// Bat lai scan 1D

					} else {//DO, TB, DD, TM
						
							txtOrderNumber.setText(scanContent1D.substring(0, 2)+"-"+
									+ Integer.parseInt(scanContent1D.substring(
											scanContent1D.length() - 9,
											scanContent1D.length()).trim()));
							
							Scan_DO(scanContent1D.toString(), txtOrderNumber
									.getText().toString(), username_login.toString());

							// Bat lai scan 1D
					}
				} //End IF Da Scan
				
				else 
					
				{
					// Neu da scan DO/TM se lay OrderNumber va tiep tuc scan PalletID
					// Neu o DO/TM dang scan
					if ((scanContent1D.substring(0, 2).compareTo("PI") != 0) && (scanContent1D.substring(0, 2).compareTo("CT") != 0)) 
					{
						String newOrderNumber;
						newOrderNumber = scanContent1D.substring(0, 2) +"-"+
								+ Integer.parseInt(scanContent1D.substring(
										scanContent1D.length() - 9,
										scanContent1D.length()).trim());
						
						if (txtOrderNumber.getText().toString().compareTo(newOrderNumber.toString()) != 0)
						{
							// Chuyen sang DO khac
							//Set lai txtOrderNumber
							txtOrderNumber.setText(scanContent1D.substring(0, 2) +"-"+
									+ Integer.parseInt(scanContent1D.substring(
											scanContent1D.length() - 9,
											scanContent1D.length()).trim()));
						}
						
					}
					
					Scan_DO(scanContent1D.toString(), txtOrderNumber.getText().toString(), username_login.toString());

					// Bat lai scan 1D
				}

				/**/
//				if (txtOrderNumber.getText().length() == 0) {
//					// Neu khong scan DO se find PalletID info
//					if (scanContent.substring(0, 2).compareTo("PI") == 0) {
//						scan_PalletID(scanContent.toString());
//					}
//
//					else {
//						if (scanContent.substring(0, 2).compareTo("DO") == 0) {
//
//							txtOrderNumber.setText("DO-"
//									+ Integer.parseInt(scanContent.substring(
//											scanContent.length() - 9,
//											scanContent.length()).trim()));
//
//							Scan_DO(scanContent.toString(), txtOrderNumber
//									.getText().toString(), username_login);
//
//						} else {
//							if (scanContent.substring(0, 2).compareTo("DD") == 0) {
//							}
//						}
//					}
//				} else {
//
//					// Neu da scan DO se lay DOID va tiep tuc scan PalletID
//					// Neu o DO dang scan
//					if (scanContent.substring(0, 2).compareTo("DO") == 0) {
//						String newOrderNumber;
//						newOrderNumber = "DO-"
//								+ Integer.parseInt(scanContent.substring(
//										scanContent.length() - 9,
//										scanContent.length()).trim());
//
//						if (txtOrderNumber.getText().toString()
//								.compareTo(newOrderNumber.toString()) != 0) {
//							// Chuyen sang DO khac
//							// Set lai txtOrderNumber
//							txtOrderNumber.setText("DO-"
//									+ Integer.parseInt(scanContent.substring(
//											scanContent.length() - 9,
//											scanContent.length()).trim()));
//						}
//					}
//
//					// Neu da scan DO se lay DOID va tiep tuc scan PalletID
//					Scan_DO(scanContent.toString(), txtOrderNumber.getText()
//							.toString(), username_login);
//
//				}
				
				/*=====================================*/	
				/**
				 * Open Camera
				 */
					Intent intent1 = new Intent("com.google.zxing.client.android.SCAN");
					intent1.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE","QR_CODE_MODE");
					startActivityForResult(intent1, 0);	
					
					
				
			} //End if (resultCode == RESULT_OK)
			
			// Scan Cancelled
			else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(), "3D Scan Cancelled",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	/**
	 * Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_setting_worker, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.Action_Setting:
//			Toast.makeText(this, "Settings", Toast.LENGTH_SHORT)
//					.show();
			if (drawerLayout.isDrawerVisible(Gravity.LEFT)) {
				drawerLayout.closeDrawer(Gravity.LEFT);
			} else {
				drawerLayout.openDrawer(Gravity.LEFT);
			}
			break;
		case android.R.id.home:
//			Toast.makeText(this, "home", Toast.LENGTH_SHORT)
//					.show();
			onBackPressed();
			finish();
			break;
		case R.id.Action_Worker:
//			Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
			if (txtOrderNumber.getText().toString().trim().length() > 0)
			{
				_Inten = new Intent(Scan_DispatchingOrder.this,
						WorkerTimeInput.class);
				_Inten.putExtra("OrderNumber", txtOrderNumber.getText().toString());
				_Inten.putExtra("OrderDate", txtDate.getText().toString());
				startActivity(_Inten);
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
//	End Class
	
	
	
	
	
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
	Toast.makeText(Scan_DispatchingOrder.this,
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
		ScanningFunction(result);
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onProgressUpdate(Void... values) {
	}
	
	}
	//End class AsyncDataUpdate
	
	

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
	//class AsyncStatusUpdate
	
	
	
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
	
	//Module Scanning 
	public void ScanningFunction(String scanContent1D) {

		//String scanContent1D = ScanResult1D.getText().toString().trim().substring(0, 11);
			
		//Toast.makeText(getApplicationContext(), ScanResult1D.getText().toString().trim(), Toast.LENGTH_SHORT).show();
			

			if (txtOrderNumber.getText().length() == 0) // Scan lan dau
			{
				
				/*New 2015-09-14*/
				switch (scanContent1D.substring(0, 2)) {
				
				case "PI": 
					//Nhay toi layout khac
					scan_PalletID(scanContent1D.toString());
					
					//2015-10-17
					// Bat lai scan 1D
//					StartScan1D_TAT();
//					Handler hd = new Handler();
//					hd.postDelayed(new Runnable() {
//						@Override
//						public void run() {
//							StartScan1D();
//						}
//					}, 1500);
					break;
					
				case "DP1": 
				break;
				
				default:
				//values_not_caught_above;
					txtOrderNumber.setText(scanContent1D.substring(0, 2)+"-"+
							+ Integer.parseInt(scanContent1D.substring(
									scanContent1D.length() - 9,
									scanContent1D.length()).trim()));
					
					Scan_DO(scanContent1D.toString(), txtOrderNumber.getText().toString(), username_login.toString());
					//2015-10-17
					// Bat lai scan 1D
//					StartScan1D_TAT();
//					Handler hd1 = new Handler();
//					hd1.postDelayed(new Runnable() {
//						@Override
//						public void run() {
//							StartScan1D();
//						}
//					}, 1500);	
				}
				
			} //End IF - Scan lan dau
			
			else 
			{
				// Neu da scan DO/TM se lay OrderNumber va tiep tuc scan PalletID
				// Neu o DO/TM dang scan
				if ((scanContent1D.substring(0, 2).compareTo("PI") != 0) && (scanContent1D.substring(0, 2).compareTo("CT") != 0) && (scanContent1D.substring(0, 2).compareTo("PB") != 0)) 
				{
					String newOrderNumber;
					newOrderNumber = scanContent1D.substring(0, 2) +"-"+
							+ Integer.parseInt(scanContent1D.substring(
									scanContent1D.length() - 9,
									scanContent1D.length()).trim());
					
					if (txtOrderNumber.getText().toString().compareTo(newOrderNumber.toString()) != 0)
					{
						//Chuyen sang DO khac
						//Set lai txtOrderNumber
						txtOrderNumber.setText(scanContent1D.substring(0, 2) +"-"+
								+ Integer.parseInt(scanContent1D.substring(
										scanContent1D.length() - 9,
										scanContent1D.length()).trim()));
					}
					
				}//
					
				Scan_DO(scanContent1D.toString(), txtOrderNumber.getText().toString(), username_login.toString());
				
				//2015-10-17
				// Bat lai scan 1D
//				StartScan1D_TAT();
//				Handler hd = new Handler();
//				hd.postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						StartScan1D();
//					}
//				}, 1500);
			 
			}

			ScanResult1D.setText("");
			//return true;

	}
}
