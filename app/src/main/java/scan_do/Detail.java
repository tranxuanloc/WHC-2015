package scan_do;

import scs.whc.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Detail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);

		Intent in = getIntent();
		String giatri = in.getStringExtra("giatri");

		TextView textView = (TextView) findViewById(R.id.giatrilayqua);
		textView.setText(giatri);
	}

}
