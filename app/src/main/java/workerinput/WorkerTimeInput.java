package workerinput;

import general.function.ConnectionSQL;
import general.function.General;
import general.function.NavigationDrawer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import scs.whc.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import database.UserLogin;

public class WorkerTimeInput extends FragmentActivity {

	private Activity _Activity;
	private DrawerLayout drawerLayout;
	private TextView txtOrderNumber, txtDateTime, txtTmpEmployee, txtISOID, txtUser;
	private Button CmdStartTime,CmdStartDate,CmdEndDate,CmdEndTime;
	private General pStatusinternet;
	private Connection Mycon;
	private EditText EdtCartonTotal, EdtTruckNumber, EdtSeal,EdtRemark;
	private String SDateTime;
	private String SOrderNumber;
	private CheckBox CbSmell, CbWet,CbLidOpening, CbClean, CbTorn, CbMissing,
	CbDenting, CbDamages, CbFall_Break, CbSoft, CbLeaks, CbDirty, CbMusty, 
	CbInsectsRisk, CbGlass_WoodFragments, CbOthers;
	private UserLogin User;
	private Button CmdComfirm;
	private Spinner SpTemperature, SpDock, SpSupervisor, SpSupervisorPercent, spWalkiePercent2,spWalkiePercent1,
	SpWorkerPercent1, SpWorkerPercent2,SpWorkerPercent3, SpWorkerPercent4, SpWorkerPercent5;
	private ArrayAdapter<String> TemperatureAdapter;
	private ArrayAdapter<String> DockAdapter;
	private ArrayAdapter<String> SuppervisorAdapter;
	private ArrayAdapter<String> SpSupervisorPercentAdapter;
	private ArrayAdapter<String> WorkerAdapter;
	private AutoCompleteTextView EdtWorkerID1, EdtWorkerID2, EdtWorkerID3, EdtWorkerID4, EdtWorkerID5,
	EdtWalkie1, EdtWalkie2 ;
	private ArrayAdapter<String> WorkerIDAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_worker_timeinput);
		_Activity = WorkerTimeInput.this;
		
		getActionBar().setTitle("Nhập Năng Suất");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
		new ColorDrawable(Color.parseColor(getResources().getString(R.color.action_bar))));
		NavigationDrawer pNavigationDrawe = new NavigationDrawer();
		pNavigationDrawe.CallNavigationDrawer(getApplicationContext(), this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
		
		Intent _NewIntent = getIntent();
		SOrderNumber = _NewIntent.getStringExtra("OrderNumber");
		SDateTime = _NewIntent.getStringExtra("OrderDate");
		
		txtTmpEmployee = (TextView) findViewById(R.id.txt_WorkerInput_tmpID);
		txtISOID = (TextView) findViewById(R.id.txt_WorkerInput_ISOID);
		txtOrderNumber = (TextView) findViewById(R.id.txt_WorkerInput_OrderNumber);
		txtDateTime = (TextView) findViewById(R.id.txt_WorkerInput_DateTime);
		txtUser = (TextView) findViewById(R.id.txt_WorkerInput_CreateBy);
		
		CmdStartDate = (Button)findViewById(R.id.cmd_WorkerInput_StartDate);
		CmdStartTime = (Button)findViewById(R.id.cmd_WorkerInput_StartTime);
		CmdEndDate = (Button)findViewById(R.id.cmd_WorkerInput_EndDate);
		CmdEndTime = (Button)findViewById(R.id.cmd_WorkerInput_EndTime);
		
		EdtRemark = (EditText) findViewById(R.id.edt_WorkerInput_Discription);
		
		EdtCartonTotal = (EditText) findViewById(R.id.edt_WorkerInput_TotalCnts);
		EdtTruckNumber = (EditText) findViewById(R.id.edt_WorkerInput_TruckNumber);
		EdtSeal = (EditText) findViewById(R.id.edt_WorkerInput_Seal);
		
		WorkerIDAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,  GetEmployee("2"));
		
        EdtWorkerID1 = (AutoCompleteTextView)findViewById(R.id.edt_WorkerInput_Worker1);
		EdtWorkerID2 = (AutoCompleteTextView) findViewById(R.id.edt_WorkerInput_Worker2);
		EdtWorkerID3 = (AutoCompleteTextView) findViewById(R.id.edt_WorkerInput_Worker3);
		EdtWorkerID4 = (AutoCompleteTextView) findViewById(R.id.edt_WorkerInput_Worker4);
		EdtWorkerID5 = (AutoCompleteTextView) findViewById(R.id.edt_WorkerInput_Worker5);
		
		EdtWalkie1  = (AutoCompleteTextView) findViewById(R.id.edt_WorkerInput_Walkie1);
		EdtWalkie2  = (AutoCompleteTextView) findViewById(R.id.edt_WorkerInput_Walkie2);
	
		EdtWorkerID1.setAdapter(WorkerIDAdapter);
		EdtWorkerID2.setAdapter(WorkerIDAdapter);
		EdtWorkerID3.setAdapter(WorkerIDAdapter);
		EdtWorkerID4.setAdapter(WorkerIDAdapter);
		EdtWorkerID5.setAdapter(WorkerIDAdapter);
		EdtWalkie1.setAdapter(WorkerIDAdapter);
		EdtWalkie2.setAdapter(WorkerIDAdapter);
        
		
		CbSmell = (CheckBox) findViewById(R.id.cb_WorkerInput_Smell);
		CbWet = (CheckBox) findViewById(R.id.cb_WorkerInput_Wet);
		CbLidOpening = (CheckBox) findViewById(R.id.cb_WorkerInput_LidOpening);
		CbClean = (CheckBox) findViewById(R.id.cb_WorkerInput_Clean);
		CbTorn = (CheckBox) findViewById(R.id.cb_WorkerInput_CartonTorn);
		CbMissing =(CheckBox) findViewById(R.id.cb_WorkerInput_Missing);
		CbDenting =(CheckBox) findViewById(R.id.cb_WorkerInput_Denting);
		CbDamages = (CheckBox) findViewById(R.id.cb_WorkerInput_Damages);
		CbFall_Break = (CheckBox) findViewById(R.id.cb_WorkerInput_FallBreak);
		CbSoft = (CheckBox) findViewById(R.id.cb_WorkerInput_Soft);
		CbLeaks = (CheckBox) findViewById(R.id.cb_WorkerInput_Leaks);
		CbDirty = (CheckBox) findViewById(R.id.cb_WorkerInput_Dirty);
		CbMusty = (CheckBox) findViewById(R.id.cb_WorkerInput_Musty);
		CbInsectsRisk = (CheckBox) findViewById(R.id.cb_WorkerInput_InsectsRisk);
		CbGlass_WoodFragments = (CheckBox) findViewById(R.id.cb_WorkerInput_Glass_WoodFragments);
		CbOthers  = (CheckBox) findViewById(R.id.cb_WorkerInput_Other);
		
		txtOrderNumber.setText(SOrderNumber);
		txtDateTime.setText(new General(_Activity).FormatDate_ddMMYYYY(SDateTime));
		
		CmdComfirm = (Button) findViewById(R.id.Cmd_WorkerInput_Confirm); 
		
		SpTemperature = (Spinner) findViewById(R.id.spinner_WorkerInput_Temperature);
		SpDock = (Spinner) findViewById(R.id.spinner_WorkerInput_Dock);
		SpSupervisor = (Spinner) findViewById(R.id.spinner_WorkerInput_Supervisor);
		SpSupervisorPercent = (Spinner) findViewById(R.id.spinner_WorkerInput_SupervisorPercent);
		
		spWalkiePercent2 = (Spinner) findViewById(R.id.spinner_WorkerInput_WalkiePercent2);
		spWalkiePercent1 = (Spinner) findViewById(R.id.spinner_WorkerInput_WalkiePercent1);
		SpWorkerPercent1 = (Spinner) findViewById(R.id.spinner_WorkerInput_WorkerPercent1);
		SpWorkerPercent2 = (Spinner) findViewById(R.id.spinner_WorkerInput_WorkerPercent2);
		SpWorkerPercent3 = (Spinner) findViewById(R.id.spinner_WorkerInput_WorkerPercent3);
		SpWorkerPercent4 = (Spinner) findViewById(R.id.spinner_WorkerInput_WorkerPercent4);
		SpWorkerPercent5 = (Spinner) findViewById(R.id.spinner_WorkerInput_WorkerPercent5);
				
		
		loadDockValueRange();
		loadTemperatureValueRange();
		loadSuppervisorValueRange();
		loadLoadPercentWorkerValueRange();
		loadLoadPercentSupervisorValueRange();
		
		inicializar(SOrderNumber);
		
		SpTemperature.setOnItemSelectedListener(new SelectChange(4,2));
		SpDock.setOnItemSelectedListener(new SelectChange(5,2));
		SpSupervisor.setOnItemSelectedListener(new SelectChange(20,1));
		SpSupervisorPercent.setOnItemSelectedListener(new SelectChange(21,1));
		spWalkiePercent2.setOnItemSelectedListener(new SelectChange(19,1));
		spWalkiePercent1.setOnItemSelectedListener(new SelectChange(17,1));
		SpWorkerPercent1.setOnItemSelectedListener(new SelectChange(7,1));
		SpWorkerPercent2.setOnItemSelectedListener(new SelectChange(9,1));
		SpWorkerPercent3.setOnItemSelectedListener(new SelectChange(11,1));
		SpWorkerPercent4.setOnItemSelectedListener(new SelectChange(13,1));
		SpWorkerPercent5.setOnItemSelectedListener(new SelectChange(15,1));
		
		EdtCartonTotal.addTextChangedListener(new TextChange(1,1));
		EdtTruckNumber.addTextChangedListener(new TextChange(2,2));
		EdtSeal.addTextChangedListener(new TextChange(3,2));
		EdtWorkerID1.addTextChangedListener(new TextChange(6,1));
		EdtWorkerID2.addTextChangedListener(new TextChange(8,1));
		EdtWorkerID3.addTextChangedListener(new TextChange(10,1));
		EdtWorkerID4.addTextChangedListener(new TextChange(12,1));
		EdtWorkerID5.addTextChangedListener(new TextChange(14,1));

		EdtWalkie1.addTextChangedListener(new TextChange(16,1));
		EdtWalkie2.addTextChangedListener(new TextChange(18,1));
		
		CbSmell.setOnCheckedChangeListener(new CheckedChange(22));
		CbWet.setOnCheckedChangeListener(new CheckedChange(23));
		CbLidOpening.setOnCheckedChangeListener(new CheckedChange(24));
		CbClean.setOnCheckedChangeListener(new CheckedChange(25));
		CbTorn.setOnCheckedChangeListener(new CheckedChange(26));
		CbMissing.setOnCheckedChangeListener(new CheckedChange(27));
		CbDenting.setOnCheckedChangeListener(new CheckedChange(28));
		CbDamages.setOnCheckedChangeListener(new CheckedChange(29));
		CbFall_Break.setOnCheckedChangeListener(new CheckedChange(30));
		CbSoft.setOnCheckedChangeListener(new CheckedChange(31));
		CbLeaks.setOnCheckedChangeListener(new CheckedChange(32));
		CbDirty.setOnCheckedChangeListener(new CheckedChange(33));
		CbMusty.setOnCheckedChangeListener(new CheckedChange(34));
		CbInsectsRisk.setOnCheckedChangeListener(new CheckedChange(35));
		CbGlass_WoodFragments.setOnCheckedChangeListener(new CheckedChange(36));
		CbOthers.setOnCheckedChangeListener(new CheckedChange(37));
		EdtRemark.addTextChangedListener(new TextChange(38,2));
		
		CmdComfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				General _General = new General(_Activity);
				if (EdtCartonTotal.getText().toString().trim().length() == 0)
				{
					_General.showAlert(_Activity, "Bạn phải nhập tổng số thùng.");
				}
				else
					if ((CmdStartDate.getText().toString().trim().length() == 0) || (CmdStartTime.getText().toString().trim().length() == 0))
					{
						_General.showAlert(_Activity, "Thời gian bắt đầu không được rỗng.");
					}
					else
						if ((CmdEndDate.getText().toString().trim().length() == 0) || (CmdEndTime.getText().toString().trim().length() == 0))
						{
							_General.showAlert(_Activity, "Thời gian kết thúc không được rỗng.");
						}
						else
							if ((CmdEndDate.getText().toString().trim().length() == 0) || (CmdEndTime.getText().toString().trim().length() == 0))
							{
								_General.showAlert(_Activity, "Thời gian kết thúc không được rỗng.");
							}
							else
								if (CheckingInput())
								{
									String String = CmdComfirm.getText().toString().trim().toUpperCase();
									switch (String) {
									case "PHÊ DUYỆT":
										if (SpSupervisor.getSelectedItem().toString().trim().equals(User.getUser()))
										{
											int PercentTotal = _General.ConvertStringToInt(SpSupervisorPercent.getSelectedItem().toString().trim());
											if (PercentTotal > 0)
											{
												if (Exec_Cmd(txtTmpEmployee.getText().toString(), "1",txtOrderNumber.getText().toString().trim() , "" + 42))
												{
													LockControl(3);
													CmdComfirm.setText("Đã phê duyệt");
												}
											}
											else
											{
												_General.showAlert(_Activity, "Vui lòng nhập tổng phần trăm năng xuất cho nhân viên.");
											}
										}
										break;
									case "CHỈNH SỬA":
										if (Exec_Cmd(txtTmpEmployee.getText().toString(), "0", "Null", "" + 41))
										{
											LockControl(2);
											CmdComfirm.setText("HOÀN THÀNH");
										}
										
										break;
									case "HOÀN THÀNH":
										if (Exec_Cmd(txtTmpEmployee.getText().toString(), "1", txtOrderNumber.getText().toString().trim(), "" + 41))
										{
											LockControl(4);
											CmdComfirm.setText("CHỈNH SỬA");
										}
										break;

									default:
										LockControl(3);
										break;
									
								}
							}
			}
		});

		CmdEndTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePickerDialog(CmdEndTime,txtTmpEmployee.getText().toString().trim(),CmdEndDate.getText().toString().trim(),40);
				
			}
		});
		CmdEndDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePickerDialog(CmdEndDate,txtTmpEmployee.getText().toString().trim(),CmdStartTime.getText().toString().trim(),40);
			}
		});
		CmdStartDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePickerDialog(CmdStartDate,txtTmpEmployee.getText().toString().trim(),CmdStartTime.getText().toString().trim(),39);
			}
		});
		CmdStartTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePickerDialog(CmdStartTime,txtTmpEmployee.getText().toString().trim(),CmdStartDate.getText().toString().trim(),39);
			}
		});
		// End
	}
	private void showDatePickerDialog(Button CmdDate, String POrderID,String PTime ,int PColum) {
	    DialogFragment newFragment = new DatePickerFragment(CmdDate,POrderID,PTime ,PColum,_Activity );
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	   
	}
	
	private void showTimePickerDialog(Button CmdDate,String POrderID,String PDate ,int PColum) {
	    DialogFragment newFragment = new TimePickerFragment(CmdDate, POrderID,PDate ,PColum,_Activity );
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_setting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
			// Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
			onBackPressed();
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private boolean CheckingInput() {
		boolean Status = true;
		if (CmdComfirm.getText().toString().trim().toLowerCase().equals("CHỈNH SỬA".toLowerCase()))
		{
			Status = true;
			return Status;
		}
		General _General = new General(_Activity);
		int TotalPercent = 0, TotalPercentWalkie = 0,IPercent1, IPercent2, IPercent3, IPercent4, IPercent5, IPercentWalkie1, IPercentWalkie2 ;
		IPercent1 = _General.ConvertStringToInt(SpWorkerPercent1.getSelectedItem().toString().trim()) ;
		IPercent2 = _General.ConvertStringToInt(SpWorkerPercent2.getSelectedItem().toString().trim()) ;
		IPercent3 = _General.ConvertStringToInt(SpWorkerPercent3.getSelectedItem().toString().trim()) ;
		IPercent4 = _General.ConvertStringToInt(SpWorkerPercent4.getSelectedItem().toString().trim()) ;
		IPercent5 = _General.ConvertStringToInt(SpWorkerPercent5.getSelectedItem().toString().trim()) ;
		IPercentWalkie1	=_General.ConvertStringToInt(spWalkiePercent1.getSelectedItem().toString().trim()) ; 
		IPercentWalkie2	=_General.ConvertStringToInt(spWalkiePercent2.getSelectedItem().toString().trim()) ;
		TotalPercent = IPercent1 + IPercent2 + IPercent3 + IPercent4 + IPercent5 ;
		TotalPercentWalkie = IPercentWalkie1 + IPercentWalkie2;
		String SStartTime = CmdStartDate.getText().toString().trim()+ " " +CmdStartTime.getText().toString().trim()+ ":00";
		String SEndTime = CmdEndDate.getText().toString().trim()+ " " +CmdEndTime.getText().toString().trim()+ ":00";
		if (EdtTruckNumber.getText().toString().trim().length() == 0)
		{
			Status = false;
			_General.showAlert(_Activity, "Vui lòng nhập số xe.");
			return Status;
		}
		if (SpTemperature.getSelectedItemPosition() == 0)
		{
			Status = false;
			SpTemperature.performClick();
//			_General.showAlert(_Activity, "Vui lòng nhập nhiệt độ.");
			return Status;
		}
		if (SpDock.getSelectedItemPosition() == 0)
		{
			Status = false;
			SpDock.performClick();
//			_General.showAlert(_Activity, "Vui lòng nhập cửa..");
			return Status;
		}
		
		if (IPercent1 > 0 && EdtWorkerID1.getText().toString().trim().length() == 0 )
		{
			_General.showAlert(_Activity, "Vui lòng nhập ID nhân viên.");
			Status = false;
			return Status;
		}
		if (IPercent2 > 0 && EdtWorkerID2.getText().toString().trim().length() == 0 )
		{
			_General.showAlert(_Activity, "Vui lòng nhập ID nhân viên.");
			Status = false;
			return Status;
		}
		if (IPercent3 > 0 && EdtWorkerID3.getText().toString().trim().length() == 0 )
		{
			Status = false;
			_General.showAlert(_Activity, "Vui lòng nhập ID nhân viên.");
			return Status;
		}
		if (IPercent4 > 0 && EdtWorkerID4.getText().toString().trim().length() == 0 )
		{
			Status = false;
			_General.showAlert(_Activity, "Vui lòng nhập ID nhân viên.");
			return Status;
		}
		if (IPercent5 > 0 && EdtWorkerID5.getText().toString().trim().length() == 0 )
		{
			Status = false;
			_General.showAlert(_Activity, "Vui lòng nhập ID nhân viên.");
			return Status;
		}
		if (IPercentWalkie1 > 0 && EdtWalkie1.getText().toString().trim().length() == 0 )
		{
			Status = false;
			_General.showAlert(_Activity, "Vui lòng nhập ID nhân viên.");
			return Status;
			
		}
		if (IPercentWalkie2 > 0 && EdtWalkie2.getText().toString().trim().length() == 0 )
		{
			Status = false;
			_General.showAlert(_Activity, "Vui lòng nhập ID nhân viên.");
			return Status;
		}
		if (TotalPercent != 100)
		{
			Status = false;
			
			_General.showAlert(_Activity, "Tổng phần trăm Bốc xếp phải bằng 100%");
			return Status;
		}
		if (TotalPercentWalkie != 100)
		{
			Status = false;
			_General.showAlert(_Activity, "Tổng phần trăm Walkie phải bằng 100%");
			return Status;
		}
		
		if (_General.calculationTimeMinutes(SStartTime) < 1 )
		{
			Status = false;
			_General.showAlert(_Activity, "Thời gian bắt đầu không lớn hơn thời gian hiện tại.");
			return Status;
		}
		if (_General.calculationTimeMinutes(SStartTime) > 900)
		{
			Status = false;
			_General.showAlert(_Activity, "Thời gian bắt đầu không nhỏ hơn thời gian hiện quá tại 15h.");
			return Status;
		}
		if (_General.calculationTimeMinutes( SStartTime,SEndTime) < 1)
		{
			Status = false;
			_General.showAlert(_Activity, "Thời gian kết thúc phải lớn hơn thời gian bắt đầu.");
			return Status;
		}
		if (_General.calculationTimeMinutes(SEndTime) < 0)
		{
			Status = false;
			_General.showAlert(_Activity, "Thời gian kết thúc phải nhỏ hơn hiện tại.");
			return Status;
		}
		return Status;
	}
	
	private boolean Exec_Cmd(String POrderID, String PNumber, String PString,String PFlag) {
		 User = new UserLogin(getApplicationContext());
		ConnectionSQL SQLCmd = new ConnectionSQL(getApplicationContext());
		
		String  StringCmd = "STAndroid_EmployeeWorkings_Update " + POrderID + ",N'" + User.getUser() +"'," + PNumber + ",N'" + PString + "'," + PFlag;
//		new General(_Activity).showAlert(_Activity, StringCmd);
		boolean Status = SQLCmd.ExecuteStringSwire(StringCmd);
		if (!Status)
		{
			
			new General(_Activity).showAlert(_Activity,
					"Cập nhật không thành công vui lòng kiểm tra lại");
		}
		return Status;
	
	}
	private class SelectChange implements OnItemSelectedListener {
		
		private int _Colum;
		private int _Group;

		SelectChange(int PColum, int PGroup)
		{
			_Colum =PColum;
			_Group =PGroup;
		}

		
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			 String selected = parent.getItemAtPosition(position).toString();
//			 new General(_Activity).showAlert(_Activity, selected);
				switch (_Group) {
				case 1:
					if (position == 0)
					{
						Exec_Cmd(txtTmpEmployee.getText().toString(), "0", "0", "" + _Colum);
					}
					else
					{
						Exec_Cmd(txtTmpEmployee.getText().toString(), selected.toString(), "NULL", "" + _Colum);
					}
					break;
				case 2:
					if (position == 0)
					{
						Exec_Cmd(txtTmpEmployee.getText().toString(), "NULL","", "" + _Colum);
					}
					else
					{
						Exec_Cmd(txtTmpEmployee.getText().toString(), "NULL", selected.toString(), "" + _Colum);
					}
					
					break;
				default:
					break;
				}
			 
			 
			 
		
			
		}
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	}
	private class CheckedChange implements OnCheckedChangeListener {
		private int _Colum;
		CheckedChange(int PColum){
			_Colum = PColum;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if (isChecked)
			{
				Exec_Cmd(txtISOID.getText().toString(), "1", "Null", "" + _Colum);
			}
			else
			{
				Exec_Cmd(txtISOID.getText().toString(), "0", "Null", "" + _Colum);
			}	
		}
	}
	private class TextChange implements TextWatcher
	{
		private int _Colum;
		private int _Group;

		TextChange(int PColum, int PGroup)
		{
			_Colum =PColum;
			_Group =PGroup;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

			switch (_Group) {
			case 1:
				if (s.toString().trim().length() > 0)
				{
					Exec_Cmd(txtTmpEmployee.getText().toString(), s.toString(), "Null", "" + _Colum);
				}
				else
				{
					Exec_Cmd(txtTmpEmployee.getText().toString(), "0", "Null", "" + _Colum);
				}
				break;
			case 2:
				Exec_Cmd(txtTmpEmployee.getText().toString(), "0", s.toString(), "" + _Colum);
				break;
			default:
				break;
			}

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	}
	
private void StatusControl (int IConfirm, int Appro) {
	User = new UserLogin(getApplicationContext());
	if (IConfirm == 1 && Appro == 1 )
	{
		LockControl(3);
		CmdComfirm.setText("Đã phê duyệt");

	}
	else
		if (User.getPositionGroup().equals("Supervisor") && Appro == 1)
		{
			LockControl(3);
			CmdComfirm.setText("Đã phê duyệt");
		}
		else
			if (User.getPositionGroup().equals("Supervisor") && Appro == 0)
			{
				LockControl(1);
				CmdComfirm.setText("phê duyệt");
			}
			else
				if (User.getUser().equals(txtUser.getText().toString().trim()) && IConfirm == 1)
				{
					LockControl(4);
					CmdComfirm.setText("Chỉnh Sửa");
				}
				else
					if (User.getUser().equals(txtUser.getText().toString().trim()) && IConfirm ==  0)
					{
						LockControl(2);
						CmdComfirm.setText("Hoàn Thành");
					}
					else
					{
						LockControl(3);
					}

}
	private void LockControl(int PType) {
	 switch (PType) {
	 
	case 1:
		 // 1/2
		EdtCartonTotal.setEnabled(true);
		EdtTruckNumber.setEnabled(true);
		EdtSeal.setEnabled(true);
		SpTemperature.setEnabled(true);
		SpDock.setEnabled(true);
		
		EdtWorkerID1.setEnabled(true);
		EdtWorkerID2.setEnabled(true);
		EdtWorkerID3.setEnabled(true);
		EdtWorkerID4.setEnabled(true);
		EdtWorkerID5.setEnabled(true);
		SpWorkerPercent1.setEnabled(true);
		SpWorkerPercent2.setEnabled(true);
		SpWorkerPercent3.setEnabled(true);
		SpWorkerPercent4.setEnabled(true);
		SpWorkerPercent5.setEnabled(true);
		EdtWalkie1.setEnabled(true);
		EdtWalkie2.setEnabled(true);
		spWalkiePercent1.setEnabled(true);
		spWalkiePercent2.setEnabled(true);
		SpSupervisor.setEnabled(true);
		SpSupervisorPercent.setEnabled(true);
	
		CbSmell.setEnabled(false);
		CbWet.setEnabled(false);
		CbLidOpening.setEnabled(false);
		CbClean.setEnabled(false);
		CbTorn.setEnabled(false);
		CbMissing.setEnabled(false);
		CbDenting.setEnabled(false);
		CbDamages.setEnabled(false);
		CbFall_Break.setEnabled(false);
		CbSoft.setEnabled(false);
		CbLeaks.setEnabled(false);
		CbDirty.setEnabled(false);
		CbMusty.setEnabled(false);
		CbInsectsRisk.setEnabled(false);
		CbGlass_WoodFragments.setEnabled(false);
		CbOthers.setEnabled(false);
		
		CmdStartTime.setEnabled(true);
		CmdStartDate.setEnabled(true);
		CmdEndDate.setEnabled(true);
		CmdEndTime.setEnabled(true);
		CmdComfirm.setEnabled(true);
		
		EdtRemark.setEnabled(true);
		break;
	case 2:
		// Open
		EdtCartonTotal.setEnabled(true);
		EdtTruckNumber.setEnabled(true);
		EdtSeal.setEnabled(true);
		SpTemperature.setEnabled(true);
		SpDock.setEnabled(true);
		
		EdtWorkerID1.setEnabled(true);
		EdtWorkerID2.setEnabled(true);
		EdtWorkerID3.setEnabled(true);
		EdtWorkerID4.setEnabled(true);
		EdtWorkerID5.setEnabled(true);
		SpWorkerPercent1.setEnabled(true);
		SpWorkerPercent2.setEnabled(true);
		SpWorkerPercent3.setEnabled(true);
		SpWorkerPercent4.setEnabled(true);
		SpWorkerPercent5.setEnabled(true);
		EdtWalkie1.setEnabled(true);
		EdtWalkie2.setEnabled(true);
		spWalkiePercent1.setEnabled(true);
		spWalkiePercent2.setEnabled(true);
		SpSupervisor.setEnabled(true);
		SpSupervisorPercent.setEnabled(true);
	
		CbSmell.setEnabled(true);
		CbWet.setEnabled(true);
		CbLidOpening.setEnabled(true);
		CbClean.setEnabled(true);
		CbTorn.setEnabled(true);
		CbMissing.setEnabled(true);
		CbDenting.setEnabled(true);
		CbDamages.setEnabled(true);
		CbFall_Break.setEnabled(true);
		CbSoft.setEnabled(true);
		CbLeaks.setEnabled(true);
		CbDirty.setEnabled(true);
		CbMusty.setEnabled(true);
		CbInsectsRisk.setEnabled(true);
		CbGlass_WoodFragments.setEnabled(true);
		CbOthers.setEnabled(true);
		
		CmdStartTime.setEnabled(true);
		CmdStartDate.setEnabled(true);
		CmdEndDate.setEnabled(true);
		CmdEndTime.setEnabled(true);
		CmdComfirm.setEnabled(true);
		
		EdtRemark.setEnabled(true);
		break;
	case 3:
		// Close
		EdtCartonTotal.setEnabled(false);
		EdtTruckNumber.setEnabled(false);
		EdtSeal.setEnabled(false);
		SpTemperature.setEnabled(false);
		SpDock.setEnabled(false);
		
		EdtWorkerID1.setEnabled(false);
		EdtWorkerID2.setEnabled(false);
		EdtWorkerID3.setEnabled(false);
		EdtWorkerID4.setEnabled(false);
		EdtWorkerID5.setEnabled(false);
		SpWorkerPercent1.setEnabled(false);
		SpWorkerPercent2.setEnabled(false);
		SpWorkerPercent3.setEnabled(false);
		SpWorkerPercent4.setEnabled(false);
		SpWorkerPercent5.setEnabled(false);
		EdtWalkie1.setEnabled(false);
		EdtWalkie2.setEnabled(false);
		spWalkiePercent1.setEnabled(false);
		spWalkiePercent2.setEnabled(false);
		SpSupervisor.setEnabled(false);
		SpSupervisorPercent.setEnabled(false);
	
		CbSmell.setEnabled(false);
		CbWet.setEnabled(false);
		CbLidOpening.setEnabled(false);
		CbClean.setEnabled(false);
		CbTorn.setEnabled(false);
		CbMissing.setEnabled(false);
		CbDenting.setEnabled(false);
		CbDamages.setEnabled(false);
		CbFall_Break.setEnabled(false);
		CbSoft.setEnabled(false);
		CbLeaks.setEnabled(false);
		CbDirty.setEnabled(false);
		CbMusty.setEnabled(false);
		CbInsectsRisk.setEnabled(false);
		CbGlass_WoodFragments.setEnabled(false);
		CbOthers.setEnabled(false);
		CmdComfirm.setEnabled(false);
		
		CmdStartTime.setEnabled(false);
		CmdStartDate.setEnabled(false);
		CmdEndDate.setEnabled(false);
		CmdEndTime.setEnabled(false);
		CmdComfirm.setEnabled(false);
		
		EdtRemark.setEnabled(false);
		break;
	case 4:
		// Close
		EdtCartonTotal.setEnabled(false);
		EdtTruckNumber.setEnabled(false);
		EdtSeal.setEnabled(false);
		SpTemperature.setEnabled(false);
		SpDock.setEnabled(false);
		
		EdtWorkerID1.setEnabled(false);
		EdtWorkerID2.setEnabled(false);
		EdtWorkerID3.setEnabled(false);
		EdtWorkerID4.setEnabled(false);
		EdtWorkerID5.setEnabled(false);
		SpWorkerPercent1.setEnabled(false);
		SpWorkerPercent2.setEnabled(false);
		SpWorkerPercent3.setEnabled(false);
		SpWorkerPercent4.setEnabled(false);
		SpWorkerPercent5.setEnabled(false);
		EdtWalkie1.setEnabled(false);
		EdtWalkie2.setEnabled(false);
		spWalkiePercent1.setEnabled(false);
		spWalkiePercent2.setEnabled(false);
		SpSupervisor.setEnabled(false);
		SpSupervisorPercent.setEnabled(false);
	
		CbSmell.setEnabled(false);
		CbWet.setEnabled(false);
		CbLidOpening.setEnabled(false);
		CbClean.setEnabled(false);
		CbTorn.setEnabled(false);
		CbMissing.setEnabled(false);
		CbDenting.setEnabled(false);
		CbDamages.setEnabled(false);
		CbFall_Break.setEnabled(false);
		CbSoft.setEnabled(false);
		CbLeaks.setEnabled(false);
		CbDirty.setEnabled(false);
		CbMusty.setEnabled(false);
		CbInsectsRisk.setEnabled(false);
		CbGlass_WoodFragments.setEnabled(false);
		CbOthers.setEnabled(false);
		CmdComfirm.setEnabled(false);
		
		CmdStartTime.setEnabled(false);
		CmdStartDate.setEnabled(false);
		CmdEndDate.setEnabled(false);
		CmdEndTime.setEnabled(false);
		CmdComfirm.setEnabled(true);
		
		EdtRemark.setEnabled(true);
		break;
	default:
		break;
	}
}
		
		private void inicializar(String CommandoSQL) {
			pStatusinternet = new General(getApplicationContext());
			if (pStatusinternet.CheckingInternet()) {
				Mycon = new ConnectionSQL(getApplicationContext()).ConnSwire();
				if (Mycon !=null)
				{
					ResultSet rus;
					try {
						 User = new UserLogin(getApplicationContext());
						Statement Statement1 = Mycon.createStatement();
						rus = Statement1.executeQuery("STAndroid_EmployeeWorkings N'" + CommandoSQL + "',N'"+ User.getUser() +"'");
						while (rus.next()) {
							if (rus.getString("TotalPackages") != null)
							{
								EdtCartonTotal.setText(rus.getString("TotalPackages"));
							}
							if (rus.getString("TruckNo") != null)
							{
								EdtTruckNumber.setText(rus.getString("TruckNo"));
							}
							if (rus.getString("SealNo") != null)
							{
								EdtSeal.setText(rus.getString("SealNo"));
							}
							if (rus.getString("Temperature") != null)
							{
								SpTemperature.setSelection(TemperatureAdapter.getPosition(rus.getString("Temperature")));
							}
							else
							{
								SpTemperature.setSelection(TemperatureAdapter.getPosition("T°C"));
							}
							if (rus.getString("StartTime") != null)
							{
								CmdStartDate.setText(new General(_Activity).FormatDate_MMddYYYY(rus.getString("StartTime")));
								CmdStartTime.setText(new General(_Activity).FormatHour(rus.getString("StartTime")));
							}
							else
							{
								CmdStartDate.setText(new General(_Activity).FormatDate_MMddYYYY(SDateTime));
							}
							if (rus.getString("EndTime") != null)
							{
								CmdEndDate.setText(new General(_Activity).FormatDate_MMddYYYY(rus.getString("EndTime")));
								CmdEndTime.setText(new General(_Activity).FormatHour(rus.getString("EndTime")));
							}
							else
							{
								CmdEndDate.setText(new General(_Activity).FormatDate_MMddYYYY(SDateTime));
							}
							if (rus.getString("Dock") != null)
							{
								SpDock.setSelection(DockAdapter.getPosition(rus.getString("Dock")));
							}
							else
							{
								SpDock.setSelection(DockAdapter.getPosition("Chọn Cửa"));
							}
							if (rus.getString("GeneralHandID1") != null)
							{
								EdtWorkerID1.setText(rus.getString("GeneralHandID1"));
							}
							if (rus.getString("GeneralHandID2") != null)
							{
								EdtWorkerID2.setText(rus.getString("GeneralHandID2"));
							}
							if (rus.getString("GeneralHandID3") != null)
							{
								EdtWorkerID3.setText(rus.getString("GeneralHandID3"));
							}
							if (rus.getString("GeneralHandID4") != null)
							{
								EdtWorkerID4.setText(rus.getString("GeneralHandID4"));
							}
							if (rus.getString("GeneralHandID5") != null)
							{
								EdtWorkerID5.setText(rus.getString("GeneralHandID5"));
							}
							if (rus.getString("PercentGH1") != null)
							{
								SpWorkerPercent1.setSelection(WorkerAdapter.getPosition(rus.getString("PercentGH1")));
							}
							if (rus.getString("PercentGH2") != null)
							{
								SpWorkerPercent2.setSelection(WorkerAdapter.getPosition(rus.getString("PercentGH2")));
							}
							if (rus.getString("PercentGH3") != null)
							{
								SpWorkerPercent3.setSelection(WorkerAdapter.getPosition(rus.getString("PercentGH3")));
							}
							if (rus.getString("PercentGH4") != null)
							{
								SpWorkerPercent4.setSelection(WorkerAdapter.getPosition(rus.getString("PercentGH4")));
							}
							if (rus.getString("PercentGH5") != null)
							{
								SpWorkerPercent5.setSelection(WorkerAdapter.getPosition(rus.getString("PercentGH5")));
							}
							if (rus.getString("tmpEmployeeWorkingID") != null)
							{
								txtTmpEmployee.setText(rus.getString("tmpEmployeeWorkingID"));
							}
							if (rus.getString("ISOOrderID") != null)
							{
								txtISOID.setText(rus.getString("ISOOrderID"));
							}
							if (rus.getString("tmpEmployeeWorkingID") != null)
							{
								txtTmpEmployee.setText(rus.getString("tmpEmployeeWorkingID"));
							}
							if (rus.getString("WalkieID1") != null)
							{
								EdtWalkie1.setText(rus.getString("WalkieID1"));
							}
							if (rus.getString("WalkieID2") != null)
							{
								EdtWalkie2.setText(rus.getString("WalkieID2"));
							}
							if (rus.getString("PercentWalkieID1") != null)
							{
								spWalkiePercent1.setSelection(WorkerAdapter.getPosition(rus.getString("PercentWalkieID1")));
							}
							if (rus.getString("PercentWalkieID2") != null)
							{
								spWalkiePercent2.setSelection(WorkerAdapter.getPosition(rus.getString("PercentWalkieID2")));
							}
							if (rus.getString("SupervisorID") != null)
							{
								SpSupervisor.setSelection(SuppervisorAdapter.getPosition(rus.getString("SupervisorID")));
							}
							else
							{
								SpSupervisor.setSelection(SuppervisorAdapter.getPosition("ID"));
							}
							if (rus.getString("Percentage") != null)
							{
								SpSupervisorPercent.setSelection(SpSupervisorPercentAdapter.getPosition(rus.getString("Percentage")));
							}
							else
							{
								SpSupervisorPercent.setSelection(SpSupervisorPercentAdapter.getPosition("0"));
							}
							txtUser.setText("" + rus.getString("CreateBy"));
							StatusControl(Integer.parseInt(rus.getString("Confirmed")),Integer.parseInt(rus.getString("Approve")));
							General _General = new General(_Activity);
								CbSmell.setChecked(_General.ConvertStringToChecked(rus.getString("Smell")));
								CbWet.setChecked(_General.ConvertStringToChecked(rus.getString("Wet")));
								CbLidOpening.setChecked(_General.ConvertStringToChecked(rus.getString("LidOpening")));
								CbClean.setChecked(_General.ConvertStringToChecked(rus.getString("Clean")));
								CbTorn.setChecked(_General.ConvertStringToChecked(rus.getString("Torn")));
								CbMissing.setChecked(_General.ConvertStringToChecked(rus.getString("Missing")));
								CbDenting.setChecked(_General.ConvertStringToChecked(rus.getString("Denting")));
								CbDamages.setChecked(_General.ConvertStringToChecked(rus.getString("Damages")));
								CbFall_Break.setChecked(_General.ConvertStringToChecked(rus.getString("Fall_Break")));
								CbSoft.setChecked(_General.ConvertStringToChecked(rus.getString("Soft")));
								CbDirty.setChecked(_General.ConvertStringToChecked(rus.getString("Dirty")));
								CbMusty.setChecked(_General.ConvertStringToChecked(rus.getString("Musty")));
								CbInsectsRisk.setChecked(_General.ConvertStringToChecked(rus.getString("InsectsRisk")));
								CbGlass_WoodFragments.setChecked(_General.ConvertStringToChecked(rus.getString("Glass_WoodFragments")));
								CbOthers.setChecked(_General.ConvertStringToChecked(rus.getString("Others")));
								CbLeaks.setChecked(_General.ConvertStringToChecked(rus.getString("Leak")));
								EdtRemark.setText(rus.getString("Remark"));
						}
					} catch (java.sql.SQLException e) {
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Kết nối database không thành công", Toast.LENGTH_SHORT).show();
				}
				// QueryString("SELECT ContainerNum, CustomerName FROM tpmContainerChecking");
				
			} else {
				Toast MsgInternet = Toast.makeText(getApplicationContext(),
						"Not Access Internet.", Toast.LENGTH_SHORT);
				MsgInternet.show();
			}
		}
	private void loadTemperatureValueRange() {
	    	 String[] Temperature = new String[62];
	         // loading 0.0 to 2.9 to the height in m/cm
	    	 int Result = 0;
	    	 Temperature[0]= "T°C";
	         for (int i = 0; i < 61; i ++) {
	        	 Result = i - 30 ;
	        	 Temperature[i + 1] ="" + Result;
	         }
	         // initialize the heightMetersAdapter with the heightMeters values
	         TemperatureAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, Temperature);
	         SpTemperature.setAdapter(TemperatureAdapter);
			// set the default value to meters
	         SpTemperature.setSelection(TemperatureAdapter.getPosition("T°C"));
	    }
	private void loadDockValueRange() {
	    	 String[] Dock = new String[29];
	         // loading 0.0 to 2.9 to the height in m/cm
	    	 int Result = 0;
	    	 Dock[0]= "Chọn Cửa";
	    	 Dock[1]= "AN";
	    	 Dock[2]= "Metro";
	         for (int i = 0; i < 26; i ++) {
	        	 Result = i ;
	        	 Dock[i + 3] ="" + Result;
	         }
	         // initialize the heightMetersAdapter with the heightMeters values
	         DockAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, Dock);
	         SpDock.setAdapter(DockAdapter);
			// set the default value to meters
	         SpDock.setSelection(DockAdapter.getPosition("Chọn Cửa"));
	    }
	 private void loadSuppervisorValueRange() {
		 ArrayList<String> Suppervisor = GetEmployee("1");

         // initialize the heightMetersAdapter with the heightMeters values
		 SuppervisorAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, Suppervisor);
		 SpSupervisor.setAdapter(SuppervisorAdapter);
		// set the default value to meters
		 SpSupervisor.setSelection(SuppervisorAdapter.getPosition("ID"));
    }
	
	private void loadLoadPercentWorkerValueRange() {
		 ArrayList<String> Worker = new ArrayList<String>() ;
		 Worker.add("0");
		 for (int i = 15; i < 101; i= i+ 5) {
			 Worker.add("" + i);
         }
		 
         // initialize the heightMetersAdapter with the heightMeters values
		 
		 WorkerAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, Worker);
		 SpWorkerPercent1.setAdapter(WorkerAdapter);
		 SpWorkerPercent1.setSelection(WorkerAdapter.getPosition("0"));
		 
		 SpWorkerPercent2.setAdapter(WorkerAdapter);
		 SpWorkerPercent2.setSelection(WorkerAdapter.getPosition("0"));
		 
		 SpWorkerPercent3.setAdapter(WorkerAdapter);
		 SpWorkerPercent3.setSelection(WorkerAdapter.getPosition("0"));
		 
		 SpWorkerPercent4.setAdapter(WorkerAdapter);
		 SpWorkerPercent4.setSelection(WorkerAdapter.getPosition("0"));
		 
		 SpWorkerPercent5.setAdapter(WorkerAdapter);
		 SpWorkerPercent5.setSelection(WorkerAdapter.getPosition("0"));
		 
//		 WorkerAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, Worker);
		 spWalkiePercent1.setAdapter(WorkerAdapter);
		 spWalkiePercent1.setSelection(WorkerAdapter.getPosition("0"));
		 
//		 WorkerAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, Worker);
		 spWalkiePercent2.setAdapter(WorkerAdapter);
		 spWalkiePercent2.setSelection(WorkerAdapter.getPosition("0"));
		 
    } 
	private void loadLoadPercentSupervisorValueRange() {
		 ArrayList<String> Worker = new ArrayList<String>() ;
		 Worker.add("0");
		 Worker.add("30");
		 Worker.add("100");
		 Worker.add("150");
		 Worker.add("200");
		 Worker.add("250");
		 Worker.add("300");

        // initialize the heightMetersAdapter with the heightMeters values
		 SpSupervisorPercentAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, Worker);
		 SpSupervisorPercent.setAdapter(SpSupervisorPercentAdapter);
		// set the default value to meters
		 SpSupervisorPercent.setSelection(SpSupervisorPercentAdapter.getPosition("0"));
		 
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
					// QueryString("SELECT ContainerNum, CustomerName FROM tpmContainerChecking");
					
				} else {
					Toast MsgInternet = Toast.makeText(getApplicationContext(),
							"Not Access Internet.", Toast.LENGTH_SHORT);
					MsgInternet.show();
				}
				return List;
			}
		
	// End Class
}
