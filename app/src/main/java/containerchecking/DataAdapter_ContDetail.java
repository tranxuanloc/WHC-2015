package containerchecking;

import general.function.ConnectionSQL;
import general.function.location.GPSTracker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import database.UserLogin;

@SuppressLint("CutPasteId") 
public class DataAdapter_ContDetail extends BaseAdapter {

	TextView txtRowName, txtColumnID, txtContCheckingID,txtCheckingID;
	EditText txtEValue, txtEValueString,txtEValueNumber;
	CheckBox CBCChecking;
	Context context;
	ArrayList<HashMap<String, String>> listCont;
	HashMap<String, String> res;
	private Connection Mycon;
	private Activity pActivity;


	public DataAdapter_ContDetail(Context context,
			ArrayList<HashMap<String, String>> listCont, Activity _Activity) {
		this.context = context;
		this.pActivity= _Activity;
		this.listCont = listCont;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listCont.size();
	}

	@Override
	public HashMap<String, String> getItem(int position) {
		// TODO Auto-generated method stub
		return listCont.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint({ "ViewHolder", "CutPasteId" }) @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		res = listCont.get(position);
		final String SCheckingID =  res.get("CheckingID");
		final String SColumnID =  res.get("ColumnID");
		String SColumnName =  res.get("ColumnName");
		String SValue =  res.get("Value");
		String SString =  res.get("String");
		String SColumnType =  res.get("ColumnType");

		
		byte bType, bColumID;
		bType = (byte) Integer.parseInt(SColumnType);
		bColumID = (byte) Integer.parseInt(SColumnID);
		
		switch (bType) {
		case 0:
			//Text
			convertView = View.inflate(context, R.layout.items_contdetail_head, null);
			txtCheckingID = (TextView) convertView.findViewById(R.id.txtContDetail_ID);
			txtRowName = (TextView) convertView.findViewById(R.id.txtContdetail_Name);
			txtColumnID = (TextView) convertView.findViewById(R.id.txtContColumID);
			
			txtCheckingID.setText(SCheckingID);
			txtRowName.setText(SColumnName);
			txtColumnID.setText(SColumnID);
			break;
		case 1:
//			Edit Text 
			if (bColumID == 2 || bColumID == 3)
			{
				convertView = View.inflate(context, R.layout.items_contdetail_munber, null);
				txtCheckingID = (TextView) convertView.findViewById(R.id.txtContDetail_ID);
				txtRowName = (TextView) convertView.findViewById(R.id.txtContdetail_Name);
				txtColumnID = (TextView) convertView.findViewById(R.id.txtContColumID);
				txtEValueNumber = (EditText) convertView.findViewById(R.id.txtEContdetail_Value);
				
				txtCheckingID.setText(SCheckingID);
				txtRowName.setText(SColumnName);
				txtColumnID.setText(SColumnID);
				txtEValueNumber.setText(SString);
				
				if (bColumID == 2)
				{
					txtEValueNumber.setHint("T°C");
				}
				else
				{
					txtEValueNumber.setHint("T°C");
				}
				txtEValueNumber.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						if (ThucThi(s.toString(), SCheckingID.toString(), Byte.parseByte(SColumnID.toString().trim()))) {
							QueryString(SCheckingID.toString().trim());
						}
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
			else
			{
				convertView = View.inflate(context, R.layout.items_contdetail_string, null);
				
				txtCheckingID = (TextView) convertView.findViewById(R.id.txtContDetail_ID);
				txtRowName = (TextView) convertView.findViewById(R.id.txtContdetail_Name);
				txtColumnID = (TextView) convertView.findViewById(R.id.txtContColumID);
				txtEValueString = (EditText) convertView.findViewById(R.id.txtEContdetail_Value_String);
				
				txtCheckingID.setText(SCheckingID);
				txtRowName.setText(SColumnName);
				txtColumnID.setText(SColumnID);
				txtEValueString.setText(SString);
				
				if (bColumID == 11)
				{
					txtEValueString.setHint("Ghi Chú");
				}
				else
				{
					txtEValueString.setHint("Dock");
				}
				txtEValueString.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						if (ThucThi(s.toString(), SCheckingID.toString(), Byte.parseByte(SColumnID.toString().trim()))) {
							QueryString(SCheckingID.toString().trim());
						}
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						txtEValueString.requestFocus();
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
					}
				});
			}
			break;
		case 2:
//			Checked
			convertView = View.inflate(context, R.layout.items_contdetail_check, null);
			txtCheckingID = (TextView) convertView.findViewById(R.id.txtContDetail_ID);
			txtRowName = (TextView) convertView.findViewById(R.id.txtContdetail_Name);
			txtColumnID = (TextView) convertView.findViewById(R.id.txtContColumID);
			CBCChecking = (CheckBox) convertView.findViewById(R.id.CBContdetail_Check);
			
			txtCheckingID.setText(SCheckingID);
			txtRowName.setText(SColumnName);
			txtColumnID.setText(SColumnID);
			if (SValue.equals("1")) {
				CBCChecking.setChecked(true);
			} else {
				CBCChecking.setChecked(false);
			}

			CBCChecking.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked)
					{
						if (ThucThi("1", SCheckingID.toString(), Byte.parseByte(SColumnID.toString().trim()))) {
							QueryString(SCheckingID.toString().trim());
						}
					}
					else
					{
						if (ThucThi("0", SCheckingID.toString(), Byte.parseByte(SColumnID.toString().trim()))) {
							QueryString(SCheckingID.toString().trim());
						}
					}
				}
			});
			break;
		case 3:
			convertView = View.inflate(context, R.layout.items_contdetail_head, null);
			
			txtCheckingID = (TextView) convertView.findViewById(R.id.txtContDetail_ID);
			txtRowName = (TextView) convertView.findViewById(R.id.txtContdetail_Name);
			txtColumnID = (TextView) convertView.findViewById(R.id.txtContColumID);
			
			txtCheckingID.setText(SCheckingID);
			txtRowName.setText(SColumnName);
			txtColumnID.setText(SColumnID);
			
			break;
			
		case 4:
			convertView = View.inflate(context, R.layout.items_contdetail_head, null);
			
			txtCheckingID = (TextView) convertView.findViewById(R.id.txtContDetail_ID);
			txtRowName = (TextView) convertView.findViewById(R.id.txtContdetail_Name);
			txtColumnID = (TextView) convertView.findViewById(R.id.txtContColumID);
			
			txtCheckingID.setText(SCheckingID);
			txtRowName.setText(SColumnName);
			txtColumnID.setText(SColumnID);
			
			break;
		}
		
		return convertView;

	}

	public boolean ThucThi(String PChuoiUpdate,String PCheckingID, byte pColum){
		
		 GPSTracker mGPS = new GPSTracker(pActivity);
		UserLogin User = new UserLogin(context);
		ConnectionSQL SQLCmd = new ConnectionSQL(context);
		String StringCmd = "";
		String PLocation = "null";
		//String
		 if(mGPS.canGetLocation()){
			  mGPS.getLocation();
			  PLocation = mGPS.getLatitude() +"-"+mGPS.getLongitude();
		 }
		 else
		 {
//			 mGPS.showSettingsAlert();
			 
		 }
//		 Update Text
		 if ((pColum == 2)||(pColum == 3)||(pColum == 11)||(pColum == 14))
			{
				StringCmd =  "SP_WebContainerChecking_UpdateDetail " + PCheckingID + ",N'" +User.getUser()+ "',N'" + PLocation + "',N'" + PChuoiUpdate + "'," + pColum;
			}
			else
				// Checked
				if ((pColum < 11) && (pColum > 3) || (pColum == 15))
				{
					StringCmd =  "SP_WebContainerChecking_UpdateDetail " + PCheckingID + ",N'" +User.getUser()+ "',N'" + PLocation + "'," + PChuoiUpdate + "," + pColum;
				}
				else
					// Finish
					if (pColum == 100)
					{
						StringCmd =  "SP_WebContainerCheckingUpdate " + PCheckingID;
					}
//		Toast.makeText(context, StringCmd, Toast.LENGTH_SHORT).show();
		return SQLCmd.ExecuteString(StringCmd);
				
	}
	private void QueryString(String CommandoSQL) {
		ResultSet rus;
		try {
			Mycon = new ConnectionSQL(context).ConnDC();
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
//			listCont.clear();
			
			listCont = data;
		} catch (java.sql.SQLException e) {
//			Log.e("Exception Load", e.getMessage());
		}
	}
	
//	End Class
}
