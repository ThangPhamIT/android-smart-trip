package com.example.linh.smarttrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import Adapter.ImageAdapter;
import Adapter.OptionsAdapter;
import Model.Attraction;
import Model.Hotels;
import Model.Park;
import Model.Restaurant;
import Model.Shop;
import Model.Theater;

public class MenuActivity extends AppCompatActivity {
    String []name;
    int []photo;
    int id;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_menu);
        Bundle b=getIntent().getExtras();
        type=b.getString(Attraction.TYPE);
        String Photo=b.getString(Attraction.PHOTO);
        id=b.getInt(Attraction.ID);
        if(type.equals(Restaurant.TABLE)){
            GridView gridView=new GridView(this);
            gridView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            gridView.setNumColumns(2);
            gridView.setColumnWidth(GridView.AUTO_FIT);
            if(initialData()) {
                OptionsAdapter adapter = new OptionsAdapter(this, name, photo);
                gridView.setAdapter(adapter);
                setContentView(gridView);
            }
            else{
                setContentView(R.layout.activity_menu);

            }
        }
        else if(!type.equals(Theater.TABLE) && !type.equals(Hotels.TABLE)){
            setContentView(R.layout.shop_list);
            recyclerView= (RecyclerView) findViewById(R.id.rv_list_shop);
            layoutManager=new LinearLayoutManager(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            initialData2();
            ImageAdapter adapter=new ImageAdapter(photo,this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
    public boolean initialData(){
        int array_id=getResources().getIdentifier("menu_r" + id, "array", getPackageName());
        if(array_id!=0) {
            name = getResources().getStringArray(array_id);
            photo = new int[name.length];
            for (int i = 1; i <= name.length; ++i) {
                int photo_id = getResources().getIdentifier("r" + id + "_m" + i, "drawable", getPackageName());
                photo[i - 1] = photo_id;
            }
            return true;
        }
        return false;
    }
    public void initialData2(){
        List<Integer> pID=new ArrayList<>();
        String photo_name="";
        int i=1;
        switch (type){
            case Shop.TABLE:
                photo_name="s";
                break;
            case Park.TABLE:
                photo_name="p";
                break;
        }
        int photo_id = getResources().getIdentifier(photo_name + id + "_" + i, "drawable", getPackageName());
        while (photo_id!=0){
            pID.add(photo_id);
            ++i;
            photo_id = getResources().getIdentifier(photo_name + id + "_" + i, "drawable", getPackageName());

        }
        photo=new int[pID.size()];
        for(int k=0;k<pID.size();++k){
            photo[k]=pID.get(k);
        }
    }
}
