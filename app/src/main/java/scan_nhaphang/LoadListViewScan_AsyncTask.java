package scan_nhaphang;

import general.function.ConnectionSQL;
import general.function.General;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

public 	 class LoadListViewScan_AsyncTask extends AsyncTask<String, String, String> {
	private ProgressDialog mProgressDialog;
	ProgressDialog dialog;
	ListView _LV_ScanDSR;
	private DataAdapter_Scan_DSReceivingOrders SAdCheck;
	Activity _Activity;
	private General pStatusinternet;
	private Connection Mycon;
	LoadListViewScan_AsyncTask(Activity PActivity, ListView PLV_ScanDSR){
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
							rus = Statement1.executeQuery("STAndroid_BarcodeScan_ReceiveDetails N'" + pString + "'");
							ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
							while (rus.next()) {
								HashMap<String, String> datacolum = new HashMap<String, String>();
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
								if (rus.getString("ScannedUser")== null)
								{
									datacolum.put("ScannedUser", "");
								}
								else
								{
									datacolum.put("ScannedUser", rus.getString("ScannedUser"));
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
//
	}
	protected void onProgressUpdate(String... progress) {
		 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	}

	@Override
	protected void onPostExecute(String unused) {
		mProgressDialog.dismiss();
		if (unused== null)
		{
			_LV_ScanDSR.setAdapter(SAdCheck);
		}
		else
		{
			new General(_Activity).showAlert(_Activity, unused);
		}
	}

}