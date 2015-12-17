package main;

import freelocation.FreeLocation;
import general.DriverMovementChecking;
import general.EmployeePerformance;
import general.function.ConnectionSQL;
import general.function.General;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import locationchecking.LocationChecking;
import my_order.Dispatching_AssignList;
import palletchecking.PalletChecking;
import scan_do.Login;
import scan_do.Scan_DispatchPallet;
import scan_do.Scan_DispatchingOrder;
import scan_do.Scan_SymbolTC70;
import scan_nhaphang.Scan_ReceivingOrders;
import scs.whc.CheckingDocument;
import scs.whc.History_EmployeeInOut;
import scs.whc.MyOrderToDay;
import scs.whc.R;
import scs.whc.WorkingSchedules;
import takepicture.Note_TakePhoto;
import update_software.Update_Software_Activity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import changepassword.ChangePassWord;
import containerchecking.ContainerChecking;
import database.UserLogin;

public class MainActivity extends Activity implements OnItemClickListener {
	ListView LVMain;
	private UserLogin UserLogin;
	private int checkuser;
	Menu menu;
	DataAdapter_MainActivity SAd;
	private TextView txtItemsMain;
	Connection Mycon;
	private General pStatusinternet;
	private Activity _Activity;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Custome actionbar
		setContentView(R.layout.layout_main);
		_Activity = MainActivity.this;
		String _Result = CheckingStringVersion();
		if (_Result != null)
		{
			if (_Result.equals("OK"))
			{
				new General(_Activity).showAlertShowActivity(_Activity, Update_Software_Activity.class, "Đã có phiên bản mới bạn có muốn cập nhật nó không?");
			}
			else
			{
				new General(_Activity).showAlert(_Activity, _Result);
			}
			
		}
		UserLogin = new UserLogin(getApplicationContext());
		if (UserLogin.getRowCount()>0)
		{
			getActionBar().setTitle( UserLogin.getFullName().toUpperCase());
		}
		else
		{
			Intent IntenLogIn;
			IntenLogIn = new Intent(MainActivity.this,
						Login.class);
				startActivity(IntenLogIn);
		}
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		showAllContacts1();
		LVMain.setOnItemClickListener(this);
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		UserLogin = new UserLogin(getApplicationContext());
		switch (item.getItemId()) {
		case R.id.action_LogOut:
			UserLogin = new UserLogin(getApplicationContext());
			if (UserLogin.getRowCount()>0)
			{
				Toast.makeText(this,"Goodbye " + UserLogin.getFullName() , Toast.LENGTH_SHORT).show();
				UserLogin.resetTables();
			}
			Intent IntenMenu;
			IntenMenu = new Intent(MainActivity.this, Login.class);
			startActivity(IntenMenu);
			finish();
			break;
		case R.id.action_Login:
			checkuser = UserLogin.getRowCount();
			// neu da login thi chay qua scan
			if (checkuser > 0) {
				Toast.makeText(this,"Xin Chào " + UserLogin.getUser() , Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent IntenMain;
				IntenMain = new Intent(MainActivity.this,
							Login.class);
					startActivity(IntenMain);
			}
			break;
		case R.id.Menu_Main_Exit:
			UserLogin = new UserLogin(getApplicationContext());
			if (UserLogin.getRowCount()>0)
			{
				Toast.makeText(this,"Goodbye " + UserLogin.getFullName() , Toast.LENGTH_SHORT).show();
				UserLogin.resetTables();
			}
			moveTaskToBack(true);
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		UserLogin = new UserLogin(getApplicationContext());
		 MenuItem item_Out,item_In;
		if (UserLogin.getRowCount() > 0) 
		{
			item_Out = menu.findItem(R.id.action_LogOut);
			item_Out.setVisible(true);
		    item_In = menu.findItem(R.id.action_Login);
		    item_In.setVisible(false);
		}
		else
		{
			item_Out = menu.findItem(R.id.action_LogOut);
			item_Out.setVisible(false);
		    item_In = menu.findItem(R.id.action_Login);
		    item_In.setVisible(true);
		}
		return super.onCreateOptionsMenu(menu);
		
	}

	public void showAllContacts1() {
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> datacolum = new HashMap<String, String>();
		UserLogin User = new UserLogin(getApplicationContext());
		if (User.getRowCount() > 0)
		{
			switch (User.getPositionGroup()) {
			case "No position":
				datacolum = new HashMap<String, String>();
				datacolum.put("Item","Kiểm Container");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Cập Nhật Mới");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Thay Đổi Mật Khẩu");
				data.add(datacolum);
				break;
			case "Supervisor":
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Phiếu Hôm Nay");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Phiếu Của Tôi");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Scan Xuất Hàng");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Scan Nhập Hồ Sơ");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Chụp Hình Biên Bản");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item","Kiểm Container");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item","Kiểm Vị Trí");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Kiểm Pallet+Carton");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Kiểm Hồ Sơ");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item","Vị Trí Trống");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Năng Suất");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Lịch Sử Chuyển Hàng");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Thay Đổi Mật Khẩu");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Kiểm Xe");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Nhập Ngoài Giờ");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Giao Việc");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Lịch Làm Việc");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Cập Nhật Mới");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Lịch Sử Ra Vào");
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Scan BigC Pallet");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Scan Symbol TC70");
				data.add(datacolum);
				break;
			case "Product Checker":
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Phiếu Của Tôi");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Scan Xuất Hàng");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Scan Nhập Hồ Sơ");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Chụp Hình Biên Bản");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item","Kiểm Container");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item","Kiểm Vị Trí");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Kiểm Pallet+Carton");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Kiểm Hồ Sơ");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item","Vị Trí Trống");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Năng Suất");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Lịch Sử Chuyển Hàng");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Thay Đổi Mật Khẩu");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Kiểm Xe");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Nhập Ngoài Giờ");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Giao Việc");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Lịch Làm Việc");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Cập Nhật Mới");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Lịch Sử Ra Vào");

				data.add(datacolum);
				break;
				
			default:
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Phiếu Của Tôi");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Scan Xuất Hàng");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Scan Nhập Hồ Sơ");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Chụp Hình Biên Bản");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item","Kiểm Container");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item","Kiểm Vị Trí");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Kiểm Pallet+Carton");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Kiểm Hồ Sơ");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item","Vị Trí Trống");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Năng Suất");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Lịch Sử Chuyển Hàng");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Thay Đổi Mật Khẩu");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Kiểm Xe");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Nhập Ngoài Giờ");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Giao Việc");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Lịch Làm Việc");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Cập Nhật Mới");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				datacolum.put("Item", "Lịch Sử Ra Vào");
				data.add(datacolum);
				datacolum = new HashMap<String, String>();
				break;
			}
		}
		
		LVMain = (ListView) findViewById(R.id.LVWHCMain);
		SAd = new DataAdapter_MainActivity(getApplicationContext(), data);
		LVMain.setAdapter(SAd);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Intent inten;
		txtItemsMain = (TextView) view.findViewById(R.id.txtActivetiMain_Text);
		UserLogin = new UserLogin(getApplicationContext());
		if (UserLogin.getRowCount() > 0) {
			switch (txtItemsMain.getText().toString().trim()) {
			case "Scan Xuất Hàng":
				inten = new Intent(MainActivity.this, Scan_DispatchingOrder.class);
				inten.putExtra("DispatchingOrderID", "");
				startActivity(inten);
				break;
			case "Kiểm Container":
				 inten = new Intent(MainActivity.this,
				 ContainerChecking.class);
				 startActivity(inten);
//				Toast.makeText(this, "Not 		Working.", Toast.LENGTH_SHORT).show();
				break;
			case "Kiểm Vị Trí":
				inten = new Intent(MainActivity.this, LocationChecking.class);
				inten.putExtra("Intent_LocationID", "A-01-00");
				startActivity(inten);
				break;
			case "Kiểm Pallet+Carton":
				inten = new Intent(MainActivity.this, PalletChecking.class);
				startActivity(inten);
				break;
			case "Năng Suất":
				inten = new Intent(MainActivity.this, EmployeePerformance.class);
				startActivity(inten);
				break;
			case "Lịch Sử Chuyển Hàng":
				inten = new Intent(MainActivity.this,
						DriverMovementChecking.class);
				startActivity(inten);
				break;
			case "Thay Đổi Mật Khẩu":
				inten = new Intent(MainActivity.this, ChangePassWord.class);
				startActivity(inten);
				break;
			case "Chụp Hình Biên Bản":
				inten = new Intent(MainActivity.this, Note_TakePhoto.class);
				inten.putExtra("OrderID", "");
				startActivity(inten);
				break;
			case "Nhập Ngoài Giờ":
				Toast.makeText(this, "Not Working.", Toast.LENGTH_SHORT).show();
				break;
			case "Giao Việc":
				Toast.makeText(this, "Not Working.", Toast.LENGTH_SHORT).show();
				break;
			case "Lịch Làm Việc":
				inten = new Intent(MainActivity.this,
						WorkingSchedules.class);
				startActivity(inten);
				break;
			case "Kiểm Xe":
//				inten = new Intent(MainActivity.this, Test.class);
//				startActivity(inten);
				Toast.makeText(this, "Not Working.", Toast.LENGTH_SHORT).show();
				break;

			case "Phiếu Của Tôi":
				inten = new Intent(MainActivity.this,
						Dispatching_AssignList.class);
				startActivity(inten);
				break;
			case "Lịch Sử Ra Vào":
				inten = new Intent(MainActivity.this, History_EmployeeInOut.class);
				startActivity(inten);
				break;
			case "Scan Nhập Hồ Sơ":
				inten = new Intent(MainActivity.this, Scan_ReceivingOrders.class);
				inten.putExtra("OrderNumber", "");
				startActivity(inten);
				break;
			case "Cập Nhật Mới":
				inten = new Intent(MainActivity.this, Update_Software_Activity.class);
				startActivity(inten);
				break;
			case "Kiểm Hồ Sơ":
				inten = new Intent(MainActivity.this, CheckingDocument.class);
				startActivity(inten);
				break;
			case "Vị Trí Trống":
				inten = new Intent(MainActivity.this, FreeLocation.class);
				startActivity(inten);
				break;
			case "Phiếu Hôm Nay":
				inten = new Intent(MainActivity.this, MyOrderToDay.class);
				startActivity(inten);
				break;
			case "Scan BigC Pallet":
				inten = new Intent(MainActivity.this, Scan_DispatchPallet.class);
				startActivity(inten);
				break;
			case "Scan Symbol TC70":
				inten = new Intent(MainActivity.this, Scan_SymbolTC70.class);
				startActivity(inten);
				break;
			default:
				break;
			}
		} else {
			Toast.makeText(this, "Please Login Or Register To View.",
					Toast.LENGTH_SHORT).show();
			inten = new Intent(MainActivity.this, Login.class);
			startActivity(inten);
		}

	}

	public static Intent createIntent(Context context) {
		Intent i = new Intent(context, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return i;
	}
	/**
	 * Version
	 */
	protected String CheckingStringVersion() {
		String result = null;
		String CommandoSQL= "SELECT TOP (1) VersionDescription, VersionDate FROM SwireDB.dbo.AdminVersions WHERE (Application = N'WHC-2015')";
		
	pStatusinternet = new General(_Activity);
	if (pStatusinternet.CheckingInternet()) {
		Mycon = new ConnectionSQL(_Activity).ConnSwire();
			if (Mycon != null)
			{
					ResultSet rus;
						Statement Statement1;
						try {
							if (Mycon != null)
							{
								Statement1 = Mycon.createStatement();
								rus = Statement1.executeQuery(CommandoSQL);
								String VersionName = "";
//								String VersionDate = "";
								while (rus.next()) {
									VersionName = rus.getString("VersionDescription");
//									VersionDate = rus.getString("VersionDate");
								}
//								version.addVersion(VersionName,VersionDate);
								if (!VersionName.equals("3.6"))
								{
									result = "OK";
								}
							}
							else
							{
								result= "Không thể kết nối tới server.";
							}
							
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
	
	
//End Class
}
