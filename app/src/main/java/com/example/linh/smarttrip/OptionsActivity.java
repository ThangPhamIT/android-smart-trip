package com.example.linh.smarttrip;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;

import Adapter.OptionsAdapter;


public class OptionsActivity extends ActionBarActivity {
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        gridView= (GridView) findViewById(R.id.gridview);
        String []name=getResources().getStringArray(R.array.options);
        int []icon=new int[] {R.drawable.restaurant_icon,R.drawable.hotels_icon,
                            R.drawable.shop_icon,R.drawable.park_icon,R.drawable.museum_icon,
                            R.drawable.map_icon,R.drawable.list_icon};
        OptionsAdapter adapter=new OptionsAdapter(this,name,icon);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==5){
                    Intent to_map=new Intent(OptionsActivity.this,MapsAllActivity.class);
                    startActivity(to_map);
                }
                else if(position==6){
                    Intent to_fav=new Intent(OptionsActivity.this,FavoriteActivity.class);
                    startActivity(to_fav);
                }
                else {
                Intent to_attraction = new Intent(OptionsActivity.this, ListAttractionActivity.class);
                to_attraction.putExtra("position", position);
                startActivity(to_attraction);
            }
            }

    });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
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
}
