package scan_nhaphang;

import general.function.ConnectionSQL;
import general.function.General;
import general.java.PullToRefreshListView;
import general.java.PullToRefreshListView.OnRefreshListener;

import java.sql.Connection;

import scs.whc.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import database.UserLogin;

public class Fragment_Scan_DSReceivingOrders extends Fragment implements OnKeyboardActionListener {
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private Button CmdScan_Camera;
	Activity _activity;
	Context _Context;
	Connection Mycon;
	TextView txtOrder,txtType;
	PullToRefreshListView LV_Scan_DSReceivingOrders;
	@SuppressWarnings("unused")
	private TextView txtDSReceivingOrdersRD;
	private Button CmdScan_Scan;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.layout_frm_scan_dsreceivingrders, container, false);
		LV_Scan_DSReceivingOrders = (PullToRefreshListView)rootView.findViewById(R.id.LV_DSReceivingOrders_Scan);
		CmdScan_Camera = (Button)rootView.findViewById(R.id.Cmd_DSReceivingOrders_Camera);
		CmdScan_Scan = (Button)rootView.findViewById(R.id.Cmd_DSReceivingOrders_Light);
		txtDSReceivingOrdersRD = (TextView)_activity.findViewById(R.id.txtDSReceivingOrders_RD);
		txtOrder =  (TextView)rootView.findViewById(R.id.txtFragment_Scan_DSReceivingOrders_OrderNumber);
		txtType =  (TextView)rootView.findViewById(R.id.txtFragment_Scan_DSReceivingOrders_Type);
		_Context = container.getContext();
		/**
		 * On Click Scan Camera
		 */
		CmdScan_Camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
				intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE"); 
				startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
			}
		});

		
		LV_Scan_DSReceivingOrders.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new LoadListViewScan_AsyncTask(_activity, LV_Scan_DSReceivingOrders).execute(txtType.getText().toString() + txtOrder.getText().toString() );
				LV_Scan_DSReceivingOrders.onRefreshComplete();
			}
		});
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
							"unitech.scanservice.software_scankey").putExtras(bundle);
					_activity.sendBroadcast(mIntent);
					CmdScan_Scan.setText("Scan");
				}
			}
		});
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		_activity = activity;
		super.onAttach(activity);
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
//		rootView
		if (requestCode ==CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
		{
			if (resultCode == Activity.RESULT_OK)
			{
				String ScanResult = data.getStringExtra("SCAN_RESULT");
				
				if (ScanResult.toString().trim().length()== 11)
				{
					processingResult(ScanResult);
				}
			}
		}
	}

	/**
	 * processing
	 * @return
	 */
	private void processingResult(String PScanResult) {

		String SType = PScanResult.substring(0,2);
		String SOrderNumber ="" + Integer.parseInt(PScanResult.replace(SType,"")) ;
		switch (SType) {
		case "LO":
			if  (txtType.getText().toString().trim().equals("CT") || txtType.getText().toString().trim().equals("PL"))
			{
//				Toast.makeText(_activity, SType, Toast.LENGTH_SHORT).show();
				showSettingsAlert(SOrderNumber,txtOrder.getText().toString().trim(),txtType.getText().toString().trim());
			}
			else
			{
				txtOrder.setText(SOrderNumber);
				txtType.setText(SType);
			}
			break;
		case "RD":
			txtType.setText(SType);
			txtOrder.setText(SOrderNumber);
			new LoadListViewScan_AsyncTask(_activity, LV_Scan_DSReceivingOrders).execute(PScanResult);
			break;
		case "CT":
			txtType.setText(SType);
			txtOrder.setText(SOrderNumber);
			new LoadListViewScan_AsyncTask(_activity, LV_Scan_DSReceivingOrders).execute(PScanResult);
			break;
		case "PL":
			txtType.setText(SType);
			txtOrder.setText(SOrderNumber);
			new LoadListViewScan_AsyncTask(_activity, LV_Scan_DSReceivingOrders).execute(PScanResult);
			break;
		default:
			break;
		}
	}

/**
 * Alert Dailog
 */
	 public void showSettingsAlert(final String pLocationNumber, final String pCartonID, final String PType) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_activity);

	        // Setting Dialog Title
	        alertDialog.setTitle("Cập nhật vị trí.");

	        // Setting Dialog Message
	        if (PType.equals("CT"))
	        {
	        	  alertDialog.setMessage("Bạn có muốn cập nhật carton "+ pCartonID +" này vào vị trí "+ pLocationNumber +" hay không?");
	        }
	        if (PType.equals("PL"))
	        {
	        	  alertDialog.setMessage("Bạn có muốn cập nhật pallet "+ pCartonID +" này vào vị trí "+ pLocationNumber +" hay không?");
	        }
	      

	        // On pressing Settings button
	        alertDialog.setPositiveButton("Cập nhật",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
								ExecSP_SQL(pLocationNumber, pCartonID, PType);
								new LoadListViewScan_AsyncTask(_activity, LV_Scan_DSReceivingOrders).execute(PType + pCartonID);
								LV_Scan_DSReceivingOrders.onRefreshComplete();
		                    	Toast.makeText(_activity, "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
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
	 private void ExecSP_SQL(String pLocationNumber, String pCartonID,String PType) {
			boolean Status = false;
			String StringCmd = "";
			UserLogin User = new UserLogin(_Context);
			ConnectionSQL SQLCmd = new ConnectionSQL(_Context);
			if (PType.equals("CT"))
			{
				StringCmd = "STAndroid_BarcodeScan_UpdateLocation N'"+ pLocationNumber + "'," + pCartonID + ",N'"+ User.getUser() +"'";
			}
			else
				if (PType.equals("PL"))
				{
//					StringCmd = "STAndroid_BarcodeScan_UpdateLocation N'"+ pLocationNumber + "'," + pCartonID + ",N'"+ User.getUser() +"'";
				}
			Status = SQLCmd.ExecuteString(StringCmd);
			if (!Status)
			{
				new General(_activity).showAlert(_activity, "Cập nhật không thành công vui lòng kiểm tra lại");
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
	//End Class

		@Override
		public void onPress(int primaryCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRelease(int primaryCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			// TODO Auto-generated method stub
			new General(_activity).showAlert(_activity, "Cập nhật không thành công vui lòng kiểm tra lại");
		}

		@Override
		public void onText(CharSequence text) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeLeft() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeRight() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeDown() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeUp() {
			// TODO Auto-generated method stub
			
		}

	
}
