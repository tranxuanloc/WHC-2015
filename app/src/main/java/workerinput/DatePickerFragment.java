package workerinput;

import general.function.ConnectionSQL;
import general.function.General;

import java.text.DecimalFormat;
import java.util.Calendar;

import database.UserLogin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

	TextView txtDate;
		private UserLogin User;
		private String _Time;
		private String _OrderID;
		private int _Colum;
		private Activity _Activity;
		
		public DatePickerFragment(TextView txtDate,String POrderID,String PTime ,int PColum,Activity PActivity ) {
			super();
			this.txtDate = txtDate;
			_Time = PTime;
			_OrderID= POrderID;
			_Colum= PColum;
			_Activity= PActivity;
			
		}
	@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current date as the default date in the picker
	        final Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR);
	        int month = c.get(Calendar.MONTH);
	        int day = c.get(Calendar.DAY_OF_MONTH);

	        // Create a new instance of DatePickerDialog and return it
	        return new DatePickerDialog(getActivity(), this, year, month, day);
	    }
	
	    public void onDateSet(DatePicker view, int year, int month, int day) {
	    	String _Month = new DecimalFormat("00").format(month +1);
	    	String _Year = new DecimalFormat("0000").format(year);
	    	String _Day = new DecimalFormat("00").format(day);
	    	String PChuoi = _Month +"/"+ _Day +"/"+ year + " "+ _Time;
	    	
	    	Exec_Cmd(_OrderID,PChuoi , "" + _Colum);
	    	
	    	txtDate.setText(new StringBuilder().append(_Month)
	 			   .append("/").append(_Day).append("/").append(_Year));
	    }
	    private void Exec_Cmd(String POrderID, String PString,String PFlag) {
			 User = new UserLogin(_Activity);
			ConnectionSQL SQLCmd = new ConnectionSQL(_Activity);
			String  StringCmd = "STAndroid_EmployeeWorkings_Update " + POrderID + ",N'" + User.getUser() +"',0,N'" + PString + "'," + PFlag;
			boolean Status = SQLCmd.ExecuteStringSwire(StringCmd);
			if (!Status)
			{
				new General(_Activity).showAlert(_Activity,
						"Cập nhật không thành công vui lòng kiểm tra lại");
				txtDate.setText("");
			}
		}
	
	
}
