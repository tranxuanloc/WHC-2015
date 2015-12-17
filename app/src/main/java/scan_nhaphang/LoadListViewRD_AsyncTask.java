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
import android.widget.Toast;
;

public class LoadListViewRD_AsyncTask extends AsyncTask<String, String, String> {
	private ProgressDialog mProgressDialog;
	ListView _LisViewShow;
	Activity _Activity;
	private General pStatusinternet;
	private Connection Mycon;
	private DataAdapter_DSReceivingOrders SAd;
	LoadListViewRD_AsyncTask(Activity PActivity, ListView PLisViewShow){
		_Activity= PActivity;
		_LisViewShow= PLisViewShow;
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

//	String pString = new String(aurl[0]);
	

	pStatusinternet = new General(_Activity);
	if (pStatusinternet.CheckingInternet()) {
		Mycon = new ConnectionSQL(_Activity).ConnDC();
			ResultSet rus;
				Statement Statement1;
				try {
					Statement1 = Mycon.createStatement();
					rus = Statement1.executeQuery("SP_DSReceivingOrders_NotConfirm");
					ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
					// day dl len listview
					while (rus.next()) {
						HashMap<String, String> datacolum = new HashMap<String, String>();
						datacolum.put("DSReceivingOrderID", rus.getString("DSReceivingOrderID"));
						datacolum.put("DSReceivingOrderNumber", rus.getString("DSReceivingOrderNumber"));
						datacolum.put("DSCustomerNumber", rus.getString("DSCustomerNumber"));
						datacolum.put("DSCustomerID", rus.getString("DSCustomerID"));
						datacolum.put("DSCustomerName", rus.getString("DSCustomerName"));
						datacolum.put("TotalCarton", rus.getString("TotalCarton"));
						datacolum.put("DSSpecialRequirement",rus.getString("DSSpecialRequirement"));
						if (rus.getString("DSReceivingOrderDate") == null) {
							datacolum.put("DSReceivingOrderDate", "");
						} else {
							datacolum.put("DSReceivingOrderDate",new General(_Activity).FormatDate_ddMMYY(rus.getString("DSReceivingOrderDate")) );
						}
						data.add(datacolum);
					}
					SAd = new DataAdapter_DSReceivingOrders(_Activity,data);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	} else {
		Toast MsgInternet = Toast.makeText(_Activity,
				"Not Access Internet.", Toast.LENGTH_SHORT);
		MsgInternet.show();
	}

	
	
	return null;

	}
	protected void onProgressUpdate(String... progress) {
		 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	}

	@Override
	protected void onPostExecute(String unused) {
		mProgressDialog.dismiss();
		_LisViewShow.setAdapter(SAd);
	}

}