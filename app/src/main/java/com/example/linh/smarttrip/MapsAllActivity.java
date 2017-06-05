package com.example.linh.smarttrip;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import Fragment.AttractionDialogFragment;
import Interface.ProviderAttraction;
import Model.Attraction;
import Model.AttractionIfoWindow;
import Model.Hotels;
import Model.Park;
import Model.Restaurant;
import Model.Shop;
import Model.Theater;

public class MapsAllActivity extends FragmentActivity implements View.OnClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Button Res,Hotel,Shop,Park,Theater,List;
    List<Attraction> attractionList1;
    List<Attraction> attractionList2;
    List<Attraction> attractionList3;
    List<Attraction> attractionList4;
    List<Attraction> attractionList5;
    int size1,size2,size3,size4,size5;
    boolean p1,p2,p3,p4,p5;
    Marker []markers1=null;
    Marker []markers2=null;
    Marker []markers3=null;
    Marker []markers4=null;
    Marker []markers5=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null){
            Toast.makeText(this,"No Internet Connection.Please Connect",Toast.LENGTH_LONG).show();
            Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
            finish();
        }
        setContentView(R.layout.activity_maps_all);
        Res= (Button) findViewById(R.id.btn_restaurant);
        Res.setOnClickListener(this);
        Hotel= (Button) findViewById(R.id.btn_hotel);
        Shop= (Button) findViewById(R.id.btn_shop);
        Park= (Button) findViewById(R.id.btn_park);
        Theater= (Button) findViewById(R.id.btn_theater);
        List= (Button) findViewById(R.id.btn_show_list);
        Hotel.setOnClickListener(this);
        Shop.setOnClickListener(this);
        Park.setOnClickListener(this);
        Theater.setOnClickListener(this);
        List.setOnClickListener(this);
        ProviderAttraction providerAttraction = new ProviderAttraction(MapsAllActivity.this, Restaurant.TABLE);
        attractionList1 = providerAttraction.get();
        providerAttraction = new ProviderAttraction(MapsAllActivity.this, Hotels.TABLE);
        attractionList2 = providerAttraction.get();
        providerAttraction.close();
        providerAttraction = new ProviderAttraction(MapsAllActivity.this, Model.Shop.TABLE);
        attractionList3 = providerAttraction.get();
        providerAttraction.close();
        providerAttraction = new ProviderAttraction(MapsAllActivity.this, Model.Park.TABLE);
        attractionList4 = providerAttraction.get();
        providerAttraction.close();
        providerAttraction = new ProviderAttraction(MapsAllActivity.this, Model.Theater.TABLE);
        attractionList5 = providerAttraction.get();
        providerAttraction.close();
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);
        LatLng current=new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
        Marker mylocation=mMap.addMarker(new MarkerOptions().position(current).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_location)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                AttractionDialogFragment dialogFragment = new AttractionDialogFragment(latLng);
                dialogFragment.show(getFragmentManager(), "dialog");

            }
        });
        mMap.setInfoWindowAdapter(new AttractionIfoWindow(this, attractionList1, attractionList2, attractionList3, attractionList4, attractionList5));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent to_info=new Intent(MapsAllActivity.this,InfoActivity.class);
                Bundle b=new Bundle();
                int id= Integer.parseInt(marker.getSnippet().split("_")[1])+1;
                b.putInt("id",id);
                b.putString("type", marker.getSnippet().split("_")[0]);
                to_info.putExtras(b);
                startActivity(to_info);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_restaurant) {
            if (!p1) {
                markers1 = new Marker[attractionList1.size()];
                for (int i = 0; i < attractionList1.size(); ++i) {
                    int rId=attractionList1.get(i).getId()-1;
                    markers1[i] = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_res))
                            .title(attractionList1.get(i).getName()).position(new LatLng(attractionList1.get(i).getLatitude(),
                                    attractionList1.get(i).getLongitude())).snippet(Restaurant.TABLE + "_" + rId));
                    //markers1[i].showInfoWindow();
                }
                Res.setText("Hide All Restaurants");
            } else {
                for (int i = 0; i < markers1.length; ++i) {
                    markers1[i].remove();

                }
                markers1 = null;
                Res.setText("RESTAURANT");
            }
            p1 = !p1;
        } else if (id == R.id.btn_hotel) {
            if (!p2) {
                markers2 = new Marker[attractionList2.size()];
                for (int i = 0; i < attractionList2.size(); ++i) {
                    markers2[i] = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hotel))
                            .title(attractionList2.get(i).getName()).position(new LatLng(attractionList2.get(i).getLatitude(),
                                    attractionList2.get(i).getLongitude())).snippet(Hotels.TABLE + "_" + i));
                   // markers2[i].showInfoWindow();
                }
                Hotel.setText("Hide All Hotels");
            } else {
                for (int i = 0; i < markers2.length; ++i) {
                    markers2[i].remove();
                }
                markers2 = null;
                Hotel.setText("HOTELS");
            }
            p2 = !p2;
        } else if (id == R.id.btn_shop) {
            if (!p3) {
                markers3 = new Marker[attractionList3.size()];
                for (int i = 0; i < attractionList3.size(); ++i) {
                    markers3[i] = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_shop))
                            .title(attractionList3.get(i).getName()).position(new LatLng(attractionList3.get(i).getLatitude(),
                                    attractionList3.get(i).getLongitude())).snippet(Model.Shop.TABLE + "_" + i));
                    //markers3[i].showInfoWindow();
                }
                Shop.setText("Hide All Shops");
            } else {
                for (int i = 0; i < markers3.length; ++i) {
                    markers3[i].remove();
                }
                markers3 = null;
                Shop.setText("SHOP");
            }
            p3 = !p3;
        } else if (id == R.id.btn_park) {
            if (!p4) {
                markers4 = new Marker[attractionList4.size()];
                for (int i = 0; i < attractionList4.size(); ++i) {
                    markers4[i] = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_park))
                            .title(attractionList4.get(i).getName()).position(new LatLng(attractionList4.get(i).getLatitude(),
                                    attractionList4.get(i).getLongitude())).snippet(Model.Park.TABLE + "_" + i));
                    //markers4[i].showInfoWindow();
                }
                Park.setText("Hide All Parks and Gardens");
            } else {
                for (int i = 0; i < markers4.length; ++i) {
                    markers4[i].remove();
                }
                markers4 = null;
                Park.setText("PARK AND GARDEN");
            }
            p4 = !p4;
        } else if (id == R.id.btn_theater) {
            if (!p5) {

                markers5 = new Marker[attractionList5.size()];
                for (int i = 0; i < attractionList5.size(); ++i) {
                    markers5[i] = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_theater))
                            .title(attractionList5.get(i).getName()).position(new LatLng(attractionList5.get(i).getLatitude(),
                                    attractionList5.get(i).getLongitude())).snippet(Model.Theater.TABLE + "_" + i));
                    //markers[i].showInfoWindow();
                }
                Theater.setText("Hide All Theaters and Museums");
            } else {
                for (int i = 0; i < markers5.length; ++i) {
                    markers5[i].remove();
                }
                markers5 = null;
                Theater.setText("THEATER AND MUSEUM");
            }
            p5 = !p5;
        } else {
            Intent to_list = new Intent(MapsAllActivity.this, ListAttractionActivity.class);
            startActivity(to_list);
            finish();
        }
    }
}
