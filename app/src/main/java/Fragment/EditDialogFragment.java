package Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.linh.smarttrip.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import Interface.ProviderAttraction;
import Interface.ProviderHotel;
import Interface.ProviderPark;
import Interface.ProviderRestaurant;
import Interface.ProviderShop;
import Interface.ProviderTheater;
import Model.Attraction;
import Model.GeoCodingAsyncTask;
import Model.Hotels;
import Model.NormalizeAddress;
import Model.Park;
import Model.Restaurant;
import Model.Shop;
import Model.Theater;

/**
 * Created by Linh on 8/13/2015.
 */
public class EditDialogFragment extends DialogFragment implements Dialog.OnClickListener{
    EditText Name,Address,Phone;
    String type;
    int id;
    long check=0;
    Interface.iCallBackUpdateList iCallBackUpdateList;
    public  EditDialogFragment(String type,int id,Interface.iCallBackUpdateList iCallBackUpdateList){
        this.type=type;
        this.id=id;
        this.iCallBackUpdateList=iCallBackUpdateList;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater= (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root=inflater.inflate(R.layout.edit_dialog_fragment, null);
        Name= (EditText) root.findViewById(R.id.et_name);
        Address= (EditText) root.findViewById(R.id.et_address);
        Phone= (EditText) root.findViewById(R.id.et_phone);
        AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setTitle("Edit").setPositiveButton("Edit",this)
                .setNegativeButton("Cancle",this).setView(root).create();
        return alertDialog;
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which==Dialog.BUTTON_POSITIVE){
            if(update()!=0){
                getActivity().finish();
            }
        }
        else{
            dialog.dismiss();
        }
    }
    public long update(){
        ContentValues contentValues=new ContentValues();
        if(!Name.getText().toString().equals("")){
            contentValues.put(Attraction.NAME,Name.getText().toString());
        }
        if(!Address.getText().toString().equals("")){
            NormalizeAddress normalizeAddress=new NormalizeAddress(Address.getText().toString(),getActivity());
            contentValues.put(Attraction.ADDRESS,normalizeAddress.getAddress());
            Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<android.location.Address> addresses=geoCoder.getFromLocationName(normalizeAddress.getAddress().toLowerCase(),1);
                if(addresses.size()>0){
                    double lat=addresses.get(0).getLatitude();
                    double lng=addresses.get(0).getLongitude();
                    contentValues.put(Attraction.LAT,lat);
                    if(type.equals(Restaurant.TABLE)){
                        contentValues.put("Longtitude",lng);
                    }else {
                        contentValues.put(Attraction.LONG, lng);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!Phone.getText().toString().equals("")){
            contentValues.put(Attraction.PHONE,Phone.getText().toString());
        }
        /*GeoCodingAsyncTask geoCodingAsyncTask=new GeoCodingAsyncTask();
        geoCodingAsyncTask.execute(Address.getText().toString());
        try {
            List<Double> location=geoCodingAsyncTask.get();
            contentValues.put(Attraction.LAT,location.get(0));
            if(type.equals(Hotels.TABLE)){
                contentValues.put("Longtitude",location.get(1));
            }else {
                contentValues.put(Attraction.LONG, location.get(1));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        switch (type){
            case Hotels.TABLE:
                ProviderHotel hotel=new ProviderHotel(getActivity());
                check= hotel.update(contentValues,id);
                break;
            case Restaurant.TABLE:
                ProviderRestaurant restaurant=new ProviderRestaurant(getActivity());
                check= restaurant.update(contentValues,id);
                break;
            case Shop.TABLE:
                ProviderShop shop=new ProviderShop(getActivity());
                check= shop.update(contentValues,id);
                break;
            case Park.TABLE:
                ProviderPark park=new ProviderPark(getActivity());
                check= park.update(contentValues,id);
                break;
            case Theater.TABLE:
                ProviderTheater theater=new ProviderTheater(getActivity());
                check= theater.update(contentValues,id);
                break;
        }
        return check;
    }
}
