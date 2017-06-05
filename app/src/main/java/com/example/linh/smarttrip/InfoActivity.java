package com.example.linh.smarttrip;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Adapter.InfoAdapter;
import Adapter.InfoSectionRecyclerViewAdapter;
import DAO.AttractionDAO;
import Fragment.AttractionListFragment;
import Fragment.EditDialogFragment;
import Interface.ProviderAttraction;
import Interface.ProviderHotel;
import Interface.ProviderPark;
import Interface.ProviderRestaurant;
import Interface.ProviderShop;
import Interface.ProviderTheater;
import Model.Attraction;
import Model.Hotels;
import Model.Park;
import Model.Restaurant;
import Model.Shop;
import Model.Theater;

public class InfoActivity extends AppCompatActivity implements Interface.iCallBackUpdateList {
    CollapsingToolbarLayout toolbar;
    Toolbar anim_toolbar;
    RecyclerView recyclerView;
    ImageView photo;
    Attraction attraction;
    List<String> Title;
    List<String> info;
    RecyclerView.LayoutManager layoutManager;
    String type;
    int id;
    com.melnykov.fab.FloatingActionButton Menu;
    RatingBar ratingBar;
    boolean change = false;
    Interface.iCallBackUpdateList iCallBackUpdateList;
    float rate;
    FloatingActionButton Fav;
    int isClick;
    InfoAdapter infoAdapter;
    InfoSectionRecyclerViewAdapter viewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_layout);
        photo = (ImageView) findViewById(R.id.iv_photo_info);
        toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        anim_toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_info);
        Menu = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab);
        Fav= (FloatingActionButton) findViewById(R.id.btn_fav);
        setSupportActionBar(anim_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Menu.attachToRecyclerView(recyclerView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                Menu.hide();
            }

            @Override
            public void onScrollUp() {
                Menu.show();
            }
        });
        Bundle b = getIntent().getExtras();
        id = b.getInt("id");
        type = b.getString("type");
        Title = new ArrayList<>();
        info = new ArrayList<>();
        attraction = readFromDatabase(id, type);
        if (!type.equals(Theater.TABLE) && !type.equals(Hotels.TABLE)) {
            Menu.setVisibility(View.VISIBLE);
        } else {
            Menu.setVisibility(View.GONE);
        }
        String photo_name = attraction.getPhoto();
        if(photo_name!=null) {
            if (photo_name.contains(".gif")) {
                int photo_id = getResources().getIdentifier(photo_name.substring(0, photo_name.length() - 4), "drawable", getPackageName());
                Picasso.with(this).load(photo_id).into(photo);
            } else {
                Picasso.with(this).load(new File(photo_name)).into(photo);
            }
        }
        infoAdapter = new InfoAdapter(this, info, attraction, type);
        List<InfoSectionRecyclerViewAdapter.Section> sections = new ArrayList<>();
        for (int i = 0; i < Title.size(); ++i) {
            sections.add(new InfoSectionRecyclerViewAdapter.Section(i, Title.get(i)));
        }
        InfoSectionRecyclerViewAdapter.Section[] dummy = new InfoSectionRecyclerViewAdapter.Section[sections.size()];
        viewAdapter = new InfoSectionRecyclerViewAdapter(this, R.layout.section, R.id.section_text, infoAdapter);
        viewAdapter.setSections(sections.toArray(dummy));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(viewAdapter);
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_menu = new Intent(InfoActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Attraction.TYPE, type);
                bundle.putString(Attraction.PHOTO, attraction.getPhoto());
                bundle.putInt(Attraction.ID, attraction.getId());
                to_menu.putExtras(bundle);
                startActivity(to_menu);
            }
        });
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(Float.parseFloat(attraction.getRating()));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                change = true;
                rate = rating;
                ProviderAttraction providerAttraction = new ProviderAttraction(InfoActivity.this, type);
                providerAttraction.updateRating(id, rating, type);
                providerAttraction.close();
            }
        });
        isClick=attraction.getFavorite();
        if(isClick==1){
            Fav.setImageResource(R.drawable.fav_yellow);
        }
        Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttractionDAO attractionDAO=new AttractionDAO(InfoActivity.this,type);
                if(isClick==1){
                    Fav.setImageResource(R.drawable.fav_white);
                    attractionDAO.updateFavorite(attraction.getId(),0,type);
                    attractionDAO.close();
                    isClick=0;
                }
                else{
                    Fav.setImageResource(R.drawable.fav_yellow);
                    attractionDAO.updateFavorite(attraction.getId(), 1, type);
                    attractionDAO.close();
                    isClick=1;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int menuid = item.getItemId();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
        intent.putExtra(Intent.EXTRA_TEXT, attraction.getLink());

// See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        //noinspection SimplifiableIfStatement
        if (menuid == R.id.action_map) {
            Intent to_map = new Intent(InfoActivity.this, MapsActivity.class);
            to_map.putExtra("map", "direction");
            to_map.putExtra(Attraction.LAT, attraction.getLatitude());
            to_map.putExtra(Attraction.LONG, attraction.getLongitude());
            startActivity(to_map);
        } else if (menuid == R.id.action_share) {
            if(!attraction.getLink().equals("")) {
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                        intent.setPackage(info.activityInfo.packageName);
                        facebookAppFound = true;
                        break;
                    }
                }

// As fallback, launch sharer.php in a browser
                if (!facebookAppFound) {
                    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + attraction.getLink();
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                }

                startActivity(intent);
            }
            else{
                Toast.makeText(InfoActivity.this,"Website Updating",Toast.LENGTH_SHORT).show();
            }
        } else if (menuid == R.id.action_share_messenger) {
            if (!attraction.getLink().equals("")) {
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.orca")) {
                        intent.setPackage(info.activityInfo.packageName);
                        facebookAppFound = true;
                        break;
                    }
                }
                if (!facebookAppFound) {
                    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + attraction.getLink();
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                }
                startActivity(intent);
            }
            else
            {
                Toast.makeText(InfoActivity.this,"Website Updating",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            EditDialogFragment fragment=new EditDialogFragment(type,id,this);
            fragment.show(getSupportFragmentManager(),"dialoog");
        }

        return super.onOptionsItemSelected(item);
    }

    public Attraction readFromDatabase(int id, String type) {
        Attraction attraction;
        switch (type) {
            case Hotels.TABLE:
                ProviderHotel hotel = new ProviderHotel(this);
                Title = Hotels.gettitle();
                Hotels hotels = (Hotels) hotel.getById(id);
                String[] tmp = new String[]{hotels.getAddress(), hotels.getPhone(), hotels.getLink(),hotels.getDescription(), hotels.getRoom_type(), hotels.getAccomodation(), hotels.getHotel_type(), hotels.getPrice()};
                Collections.addAll(info, tmp);
                return hotels;
            case Restaurant.TABLE:
                ProviderRestaurant restaurant = new ProviderRestaurant(this);
                Title = Restaurant.gettitle();
                Restaurant r = (Restaurant) restaurant.getById(id);
                String[] tmp1 = new String[]{r.getAddress(), r.getPhone(), r.getLink(),r.getDescription(), r.getOpen(), r.getClose()};
                Collections.addAll(info, tmp1);
                return r;
            case Shop.TABLE:
                ProviderShop shop = new ProviderShop(this);
                Title = Shop.gettitle();
                Shop s = (Shop) shop.getById(id);
                String[] tmp2 = new String[]{s.getAddress(), s.getPhone(),s.getLink()};
                Collections.addAll(info, tmp2);
                return s;
            case Park.TABLE:
                ProviderPark park = new ProviderPark(this);
                Title = Park.gettitle();
                Park p = (Park) park.getById(id);
                String[] tmp3 = new String[]{p.getAddress(), p.getPhone(),p.getLink(), p.getDescription(), p.getPrice()};
                Collections.addAll(info, tmp3);
                return p;
            case Theater.TABLE:
                ProviderTheater theater = new ProviderTheater(this);
                Title = Theater.gettitle();
                Theater t = (Theater) theater.getById(id);
                String[] tmp4 = new String[]{t.getAddress(), t.getPhone(),t.getLink(), t.getDescription(), t.getPrice()};
                Collections.addAll(info, tmp4);
                return t;
        }
        return null;
    }

    @Override
    public void iCallBackUpdate(Attraction attraction, String type) {

    }

    @Override
    public void iCallBackEdit() {

    }
}

