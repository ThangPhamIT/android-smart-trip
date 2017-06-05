package com.example.linh.smarttrip;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

import Adapter.AttractionAdapter;
import Fragment.AttractionListFragment;
import Interface.ProviderAttraction;
import Model.Attraction;
import Model.Hotels;
import Model.Park;
import Model.Restaurant;
import Model.Shop;
import Model.Theater;

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView recyclerView1,recyclerView2,recyclerView3,recyclerView4,recyclerView5;
    RecyclerView.LayoutManager layoutManager,layoutManager2,layoutManager3,layoutManager4,layoutManager5;
    List<Attraction> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView1= (RecyclerView) findViewById(R.id.rv_list1);
        recyclerView2= (RecyclerView) findViewById(R.id.rv_list2);
        recyclerView3= (RecyclerView) findViewById(R.id.rv_list3);
        recyclerView4= (RecyclerView) findViewById(R.id.rv_list4);
        recyclerView5= (RecyclerView) findViewById(R.id.rv_list5);
        float dimen=  getResources().getDimension(R.dimen.size_icon);
        layoutManager=new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setHasFixedSize(true);
        ProviderAttraction providerAttraction=new ProviderAttraction(this, Restaurant.TABLE);
        AttractionAdapter adapter1=new AttractionAdapter(this,providerAttraction.getFav(),Restaurant.TABLE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerView1.getLayoutParams();
        params.height = (int) (adapter1.getItemCount() * getResources().getDimension(R.dimen.size_icon))+5;
        recyclerView1.setLayoutParams(params);
        recyclerView1.setAdapter(adapter1);

        providerAttraction=new ProviderAttraction(this, Hotels.TABLE);
        layoutManager2=new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setHasFixedSize(true);
        AttractionAdapter adapter2=new AttractionAdapter(this,providerAttraction.getFav(),Hotels.TABLE);
        params = (LinearLayout.LayoutParams) recyclerView2.getLayoutParams();
        params.height = (int) (adapter2.getItemCount() * getResources().getDimension(R.dimen.size_icon))+5;
        recyclerView2.setLayoutParams(params);
        recyclerView2.setAdapter(adapter2);

        providerAttraction=new ProviderAttraction(this, Shop.TABLE);
        layoutManager3=new LinearLayoutManager(this);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.setHasFixedSize(true);
        AttractionAdapter adapter3=new AttractionAdapter(this,providerAttraction.getFav(),Shop.TABLE);
        params = (LinearLayout.LayoutParams) recyclerView3.getLayoutParams();
        params.height = (int) (adapter3.getItemCount() * getResources().getDimension(R.dimen.size_icon))+5;
        recyclerView3.setLayoutParams(params);
        recyclerView3.setAdapter(adapter3);

        providerAttraction=new ProviderAttraction(this, Park.TABLE);
        layoutManager4=new LinearLayoutManager(this);
        recyclerView4.setLayoutManager(layoutManager4);
        recyclerView4.setHasFixedSize(true);
        AttractionAdapter adapter4=new AttractionAdapter(this,providerAttraction.getFav(),Park.TABLE);
        params = (LinearLayout.LayoutParams) recyclerView4.getLayoutParams();
        params.height = (int) (adapter4.getItemCount() * getResources().getDimension(R.dimen.size_icon))+5;
        recyclerView4.setLayoutParams(params);
        recyclerView4.setAdapter(adapter4);

        providerAttraction=new ProviderAttraction(this, Theater.TABLE);
        layoutManager5=new LinearLayoutManager(this);
        recyclerView5.setLayoutManager(layoutManager5);
        recyclerView5.setHasFixedSize(true);
        AttractionAdapter adapter5=new AttractionAdapter(this,providerAttraction.getFav(),Theater.TABLE);
        params = (LinearLayout.LayoutParams) recyclerView5.getLayoutParams();
        params.height = (int) (adapter5.getItemCount() * getResources().getDimension(R.dimen.size_icon))+5;
        recyclerView5.setLayoutParams(params);
        recyclerView5.setAdapter(adapter5);
      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
