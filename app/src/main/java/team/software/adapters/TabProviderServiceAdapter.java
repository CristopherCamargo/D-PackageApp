package team.software.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import team.software.d_packageapp.ListRequestInbox;
import team.software.d_packageapp.ListRequestPS;
import team.software.d_packageapp.TakeMoneyPS;

/**
 * Created by Caceres on 04-12-2016.
 */

public class TabProviderServiceAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    public TabProviderServiceAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ListRequestInbox();
            case 1:
                return new ListRequestPS();
            case 2:
                return new TakeMoneyPS();
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
