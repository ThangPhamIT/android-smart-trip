package Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import Interface.ProviderAttraction;
import Model.Attraction;
import Model.Hotels;

/**
 * Created by Linh on 8/1/2015.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
    Context mContext;
    String []tabs={"Restaurants","Hotels","Shops","Parks and Gardens","Theaters and Museums"};
    AttractionListFragment fragment;
    public TabPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                //CopyDataBaseFromAsset("Restaurant");
                ProviderAttraction providerAttraction=new ProviderAttraction(mContext,"Restaurant");
                List<Attraction> list=providerAttraction.get();
                int [] count=providerAttraction.getCount();
                return RestaurantListFragment.newInstance(list,count);
            case 1:
                //CopyDataBaseFromAsset("Hotel");
                ProviderAttraction providerAttraction2=new ProviderAttraction(mContext,"Hotel");
                List<Attraction> list2=providerAttraction2.get();
                fragment= AttractionListFragment.newInstance(list2,"Hotel");
                return fragment;
            case 2:
                //CopyDataBaseFromAsset("Shop");
                ProviderAttraction providerAttraction3=new ProviderAttraction(mContext,"Shop");
                List<Attraction> list3=providerAttraction3.get();
                return AttractionListFragment.newInstance(list3,"Shop");
            case 3:
                //CopyDataBaseFromAsset("Park");
                ProviderAttraction providerAttraction4=new ProviderAttraction(mContext,"Park");
                List<Attraction> list4=providerAttraction4.get();
                return AttractionListFragment.newInstance(list4,"Park");
            case 4:
                //CopyDataBaseFromAsset("Theater");
                ProviderAttraction providerAttraction5=new ProviderAttraction(mContext,"Theater");
                List<Attraction> list5=providerAttraction5.get();
                return AttractionListFragment.newInstance(list5,"Theater");
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabs[position];
    }
    public void CopyDataBaseFromAsset(String DBName) {
        File file=mContext.getDatabasePath(DBName);
        if(!file.exists()){
            try {
                InputStream inputStream=mContext.getAssets().open(DBName);
                File dir=new File (mContext.getApplicationInfo().dataDir+"/databases");
                if(!dir.exists()){
                    dir.mkdir();
                }
                OutputStream outputStream=new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
