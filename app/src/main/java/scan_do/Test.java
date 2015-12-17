package scan_do;


import general.function.General;
import scs.whc.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Test extends Activity {
   
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private Button startBtn;
   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        startBtn = (Button)findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new OnClickListener(){
            @SuppressWarnings("unused")
			private General insetnet;

			public void onClick(View v) {
				
//				boolean a = new General(getApplicationContext()).ping("195.184.11.230");
//				new General(getApplicationContext()).showAlert(Test.this,""+ a);
				
			}
        });
    }
  
   
}