package containerchecking;

import general.function.General;

import java.util.ArrayList;
import java.util.HashMap;

import scs.whc.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DataAdapter_ContainerChecking extends BaseAdapter  {
	
	TextView txtContNumbet,txtCustomerName,txtOperation, txtTimeIn,txtDockNumber, txtCheckingID,txtDiffTime;
	ProgressBar CBStatus;
	Context context;
    ArrayList<HashMap<String, String>> listsanpham;
    HashMap<String, String> res;
    General PGeneral;
    RelativeLayout LayoutContain;
    
    
    public DataAdapter_ContainerChecking(Context context, ArrayList<HashMap<String, String>> listsanpham) {
        this.context = context;
        this.listsanpham = listsanpham;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listsanpham.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listsanpham.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("static-access")
	@SuppressLint({ "SimpleDateFormat", "ViewHolder" }) @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
        res = listsanpham.get(position);
        View view = View.inflate(context, R.layout.items_containerchecking, null);
        String SCheckingID = res.get("CheckingID");
        String SConNumber = res.get("ContainerNum") +"~"+res.get("ContainerType");
        String SCustomerName = res.get("CustomerName");
        String SOperation = res.get("Operation");
        String STimeIn =  res.get("TimeIn") + res.get("TimeCheck") + res.get("ProductQty") + res.get("UserCheck");
        String SDockNumber =  res.get("DockNumber");
        String SFinish =  res.get("Finish");
        String STime = res.get("Time");
        
        txtCheckingID = (TextView) view.findViewById(R.id.txtContChechingID);
        txtContNumbet = (TextView) view.findViewById(R.id.txtContainerNumber_cont);
        txtCustomerName = (TextView) view.findViewById(R.id.txtCustomerName_cont);
        txtOperation = (TextView) view.findViewById(R.id.txtOperation_Cont);
        txtTimeIn = (TextView) view.findViewById(R.id.txtGeneralInformation);
        txtDockNumber = (TextView) view.findViewById(R.id.txtDock_Cont);
        CBStatus = (ProgressBar) view.findViewById(R.id.PrSFinish);
        txtDiffTime =  (TextView) view.findViewById(R.id.txtContChechingTime);
        LayoutContain = (RelativeLayout) view.findViewById(R.id.ContainerChecking_Layout);
        
        txtCheckingID.setText(SCheckingID);
        txtContNumbet.setText(SConNumber);
        txtCustomerName.setText(SCustomerName);
        txtOperation.setText(SOperation);
        txtTimeIn.setText(STimeIn);
        txtDockNumber.setText(SDockNumber);
        
        if (SFinish.equals("0"))
        {
        	CBStatus.setVisibility(view.GONE);
        }
        else
        {
        	CBStatus.setVisibility(view.VISIBLE);
        }
        if (STime == "") {
        	txtDiffTime.setText("0");
        }
        else
        {
        	PGeneral = new General(context);
        	long LTime = PGeneral.calculationTimeMinutes(STime);
        	txtDiffTime.setText(""+LTime );
        	if ((LTime > 3) || (LTime == 3))
        	{
        		LayoutContain.setBackgroundColor(Color.parseColor("#FFFF66"));
        	}
        	if (LTime < 3)
        	{
        		LayoutContain.setBackgroundColor(Color.parseColor("#CCFFCC"));
        	}
        }
        
      
        return view;

	}

}
