package general.function;

import scs.whc.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint({ "InflateParams", "NewApi" })
public class SimpleFragment extends Fragment {
	 static String data;

	public static SimpleFragment fragment(String text) {
		SimpleFragment fragment = new SimpleFragment();
		data = text;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.simple_fragment, null);
//		TextView textView = (TextView) view.findViewById(R.id.textView);
//		textView.setText(data);
		return view;
	}

}
