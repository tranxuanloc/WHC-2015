package general.function;

import general.DriverMovementChecking;
import general.EmployeePerformance;
import general.java.DrawerArrowDrawable;

import java.util.ArrayList;
import java.util.HashMap;

import locationchecking.LocationChecking;
import main.DataAdapter_MainActivity;
import main.MainActivity;
import my_order.Dispatching_AssignList;
import palletchecking.PalletChecking;
import scan_do.Scan_DispatchingOrder;
import scs.whc.R;
import takepicture.Note_TakePhoto;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import changepassword.ChangePassWord;
import database.UserLogin;

public class NavigationDrawer {
	private UserLogin UserLogin;
	private TextView txtItemsMain;
	DrawerLayout drawerLayout;
	ListView listView;
	ImageView imageView;
	DrawerArrowDrawable arrowDrawable;
	
	int pos;
	private DataAdapter_MainActivity SAd;

	public NavigationDrawer() {

	}

	public void CallNavigationDrawer(Context pContext, final Activity _activity) {
		/**
		 * Convert Dp to px
		 */
		 DisplayMetrics metrics = pContext.getResources().getDisplayMetrics();
		 int _Width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, metrics);
		 
		drawerLayout = (DrawerLayout) _activity.findViewById(R.id.drawe_layout);
		listView = (ListView) _activity.findViewById(R.id.LVDrawe_Layout);
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.width = _Width;
		listView.setLayoutParams(params);
		listView.requestLayout();

		arrowDrawable = new DrawerArrowDrawable(_activity.getResources());
		arrowDrawable.setStrokeColor(Color.parseColor("#ffffff"));
		drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

			@Override
			public void onDrawerSlide(View arg0, float slideOffset) {
				float offset = slideOffset;
				boolean flipped;
				if (slideOffset >= .995) {
					flipped = true;
					arrowDrawable.setFlip(flipped);
				} else if (slideOffset <= .005) {
					flipped = false;
					// textMenu.setText(data[pos]);
					arrowDrawable.setFlip(flipped);
				}
				arrowDrawable.setParameter(offset);
			}
		});

	
		
			ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> datacolum = new HashMap<String, String>();
			datacolum.put("Item", "Scan Barcode");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item","My Order");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item","Container Checking");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item","Location Checking");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "Pallet Checking");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "Employee Performance");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "Driver Movement History");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "Change Password");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "Photo Notes");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "Truck Checking");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "OT Entry");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "Assignments");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "Working Schedules");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "LogOut");
			data.add(datacolum);
			datacolum = new HashMap<String, String>();
			datacolum.put("Item", "Exit");
			data.add(datacolum);
			SAd = new DataAdapter_MainActivity(pContext, data);
			listView.setAdapter(SAd);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				pos = position;
				UserLogin = new UserLogin(_activity);
//				displayView(position);
				// textMenu.setText(data[position]);
				txtItemsMain = (TextView) view.findViewById(R.id.txtActivetiMain_Text);
				Intent inten;
				
			if (txtItemsMain.getText().toString().trim().equals("Exit"))
			{
				Toast.makeText(_activity,"Goodbye " + UserLogin.getUser() , Toast.LENGTH_SHORT).show();
				UserLogin.resetTables();
				_activity.finish();
				_activity.moveTaskToBack(true);
			}
			else
			{
				if (UserLogin.getRowCount()> 0)
				{
					switch (txtItemsMain.getText().toString().trim()) {
					case "Scan Barcode":
							inten = new Intent(_activity, Scan_DispatchingOrder.class);
							_activity.startActivity(inten);
						break;
					case "My Order":
						inten = new Intent(_activity, Dispatching_AssignList.class);
						_activity.startActivity(inten);
					break;
					case "Container Checking":
						Toast.makeText(_activity,"Not Working" , Toast.LENGTH_SHORT).show();
//							inten = new Intent(_activity, ContainerChecking.class);
//							_activity.startActivity(inten);
						break;
					case "Truck Checking":
					
						break;
					case "Location Checking":
						inten = new Intent(_activity, LocationChecking.class);
						_activity.startActivity(inten);
						break;
					case "Pallet Checking":
						inten = new Intent(_activity, PalletChecking.class);
						_activity.startActivity(inten);
						break;
					case "Employee Performance":
						inten = new Intent(_activity, EmployeePerformance.class);
						_activity.startActivity(inten);
						break;
					case "Driver Movement History":
						inten = new Intent(_activity, DriverMovementChecking.class);
						_activity.startActivity(inten);
						break;
					case  "OT Entry":

						break;
					case  "Assignments":

						break;
					case "Working Schedules":

						break;
					case "Change Password":
							inten = new Intent(_activity, ChangePassWord.class);
							_activity.startActivity(inten);
						break;
					case "Photo Notes":
							inten = new Intent(_activity, Note_TakePhoto.class);
							inten.putExtra("OrderID","");
							_activity.startActivity(inten);
						break;
					case "LogOut":
						Toast.makeText(_activity,"Goodbye " + UserLogin.getUser() , Toast.LENGTH_SHORT).show();
						UserLogin.resetTables();
						_activity.moveTaskToBack(true);
						inten = new Intent(_activity, MainActivity.class);
						_activity.startActivity(inten);
						break;
					case "Exit":
//						Exit
						Toast.makeText(_activity,"Goodbye " + UserLogin.getUser() , Toast.LENGTH_SHORT).show();
						UserLogin.resetTables();
						_activity.finish();
						_activity.moveTaskToBack(true);
						
					break;
					default:
						break;
					}
				}
				else
				{
					Toast.makeText(_activity,"Please Login Or Register To View Scan."  , Toast.LENGTH_SHORT).show();
				}
			}
			

			}
		});
	}



}
