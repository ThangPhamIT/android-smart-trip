package Model;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Linh on 8/21/2015.
 */
public class NormalizeAddress {
    String address;
    Context mContext;
    String country;
    String city;
    public NormalizeAddress(String src,Context context){
        this.mContext=context;
        normalize();
        this.address=src+" "+city+" "+country;
    }
    public void normalize(){
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location myLocation = locationManager.getLastKnownLocation(provider);
        LatLng current=new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
        Geocoder geocoder=new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses=new ArrayList<>();
        try {
            addresses=geocoder.getFromLocation(current.latitude,current.longitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses.size()>0){
            country=addresses.get(0).getCountryName();
            city=addresses.get(0).getLocality();
        }
        else{
            Toast tmp=Toast.makeText(mContext, "Can not find location on Map. The address will change but the location will not change.Please check your network connection", Toast.LENGTH_SHORT);
            tmp.setGravity(Gravity.TOP,0,0);
            tmp.show();
        }
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
