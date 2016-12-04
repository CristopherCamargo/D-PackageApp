package team.software.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import team.software.d_packageapp.ListRequestPackage;
import team.software.d_packageapp.PaymentCardManagement;
import team.software.d_packageapp.Tracing;

/**
 * Created by Caceres on 03-12-201  6.
 */

public class TabHomeClientAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    public TabHomeClientAdapter(FragmentManager fm,int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ListRequestPackage();
            case 1:
                return new Tracing();
            case 2:
                return new ListRequestPackage();
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
