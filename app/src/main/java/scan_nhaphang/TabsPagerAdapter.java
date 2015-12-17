package scan_nhaphang;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
Context _ConText;
	public TabsPagerAdapter(FragmentManager fm,Context PConText) {
		super(fm);
		this._ConText = PConText;
	}

	@Override
	public Fragment getItem(int index) {
		if (index % 2 ==0)
		{
			return new Fragment_DSReceivingOrders();
		}
		else
		{
			return new Fragment_Scan_DSReceivingOrders();
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2000 ;
	}

}
