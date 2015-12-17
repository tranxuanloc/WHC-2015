package workerinput;

import general.function.ConnectionSQL;
import general.function.General;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import database.UserLogin;

public class TimePickerFragment extends DialogFragment  implements TimePickerDialog.OnTimeSetListener{
	
	TextView txtTime;
	private UserLogin User;
	private String _Date;
	private String _OrderID;
	private int _Colum;
	private Activity _Activity;
	
	public TimePickerFragment(TextView txtTime,String POrderID,String PDate ,int PColum,Activity PActivity ) {
		super();
		this.txtTime = txtTime;
		_Date =PDate;
		_OrderID= POrderID;
		_Colum= PColum;
		_Activity= PActivity;
		
	}

	@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current time as the default values for the picker
	        final Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        int minute = c.get(Calendar.MINUTE);

	        // Create a new instance of TimePickerDialog and return it
	        return new TimePickerDialog(getActivity(), this, hour, minute,
	                DateFormat.is24HourFormat(getActivity()));
	    }

	    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	    	String _Hour = new DecimalFormat("00").format(hourOfDay);
	    	String _Minute = new DecimalFormat("00").format(minute);
	       txtTime.setText(_Hour+":"+_Minute);
	       
	       _Date = _Date + " "+ _Hour+":"+_Minute + ":00";
	       Exec_Cmd(_OrderID,_Date , "" + _Colum);
	      
	    }
	    private void Exec_Cmd(String POrderID, String PString,String PFlag) {
			 User = new UserLogin(_Activity);
			ConnectionSQL SQLCmd = new ConnectionSQL(_Activity);
			String  StringCmd = "STAndroid_EmployeeWorkings_Update " + POrderID + ",N'" + User.getUser() +"',0,N'" + PString + "'," + PFlag;
			boolean Status = SQLCmd.ExecuteStringSwire(StringCmd);
			if (!Status)
			{
				txtTime.setText("");
				new General(_Activity).showAlert(_Activity,
						"Cập nhật không thành công vui lòng kiểm tra lại");
			}

		}
}
