package Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.method.CharacterPickerDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linh.smarttrip.MapsActivity;
import com.example.linh.smarttrip.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Interface.ProviderAttraction;
import Interface.ProviderHotel;
import Interface.ProviderPark;
import Interface.ProviderRestaurant;
import Interface.ProviderShop;
import Interface.ProviderTheater;
import Interface.iCallBackUpdateList;
import Model.Attraction;
import Model.Hotels;
import Model.Park;
import Model.Restaurant;
import Model.Shop;
import Model.Theater;

/**
 * Created by Linh on 8/3/2015.
 */
public class AttractionDialogFragment extends DialogFragment implements Dialog.OnClickListener {
    EditText Name;
    EditText Address;
    Spinner attraction_type;
    RatingBar ratingBar;
    TextView tv_ratingbar;
    ImageView photo;
    String [] kind_attraction={"Restaurant","Hotel","Shop","Park","Garden","Theater","Museum"};
    String type;
    Attraction attraction;
    long check=0;
    String photoPath;
    LatLng position;
    boolean selectImage=false;
    boolean selectAddress=false;
    Interface.iCallBackUpdateList iCallBackUpdateList;
    final public static int REQUEST_CODE_CAPTURE_IMAGE=101;
    final public static int REQUEST_CODE_SELECT_IMAGE=102;
    final public static int REQUEST_CODE_MAP=103;
    final public static String REQUIRE="require location";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public AttractionDialogFragment(){}
    public AttractionDialogFragment(Interface.iCallBackUpdateList iCallBackUpdateList) {
        this.iCallBackUpdateList = iCallBackUpdateList;
    }
    public AttractionDialogFragment(LatLng src){
        position=src;
        this.iCallBackUpdateList=null;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_fragment, null);
        Name= (EditText) view.findViewById(R.id.et_name);
        Address= (EditText) view.findViewById(R.id.et_address);
        attraction_type= (Spinner) view.findViewById(R.id.spinner_attraction_type);
        ratingBar= (RatingBar) view.findViewById(R.id.ratingBar);
        tv_ratingbar= (TextView) view.findViewById(R.id.tv_rating);
        photo= (ImageView) view.findViewById(R.id.iv_photo);
        if(iCallBackUpdateList==null){
            Geocoder geocoder=new Geocoder(getActivity(),Locale.getDefault());
            List<android.location.Address> addresses=new ArrayList<android.location.Address>();
            try {
                addresses=geocoder.getFromLocation(position.latitude,position.longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String location="";
            if(addresses.size()>0) {
                for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); ++i) {
                    location += addresses.get(0).getAddressLine(i) + ", ";
                }
            }
            else{
                Toast.makeText(getActivity(),"Can not get the location/Please check the network connection",Toast.LENGTH_SHORT).show();
            }
            Address.setText(location);
        }
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                        "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add Attraction Photo");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Take Photo")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE);
                        } else if (items[which].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_CODE_SELECT_IMAGE);
                        } else if (items[which].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,kind_attraction);
        attraction_type.setAdapter(adapter);
        attraction_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==3 || position==4){
                    type= Park.TABLE;
                }
                else if(position==5 || position==6){
                    type= Theater.TABLE;
                }
                else {
                    type = kind_attraction[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type=kind_attraction[0];
            }
        });
        Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"My Location","Choose from Map",
                        "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Location of Attraction");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("My Location")) {
                            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                            Criteria criteria = new Criteria();
                            String provider = locationManager.getBestProvider(criteria, true);
                            Location myLocation = locationManager.getLastKnownLocation(provider);
                            position=new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
                            Geocoder geocoder=new Geocoder(getActivity(),Locale.getDefault());
                            List<android.location.Address> addresses=new ArrayList<android.location.Address>();
                            try {
                                addresses=geocoder.getFromLocation(myLocation.getLatitude(),myLocation.getLongitude(),1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String location="";
                            if(addresses.size()>0) {
                                for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); ++i) {
                                    location += addresses.get(0).getAddressLine(i) + ", ";
                                }
                                selectAddress=true;
                            }
                            else{
                                Toast toast=Toast.makeText(getActivity(), "Can not get the location/Please check the network connection", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,0);
                                toast.show();
                            }
                            Address.setText(location);
                        } else if (items[which].equals("Choose from Map")) {
                            Intent to_map=new Intent(getActivity(), MapsActivity.class);
                            to_map.putExtra("map",REQUIRE);
                            startActivityForResult(to_map, REQUEST_CODE_MAP);
                        } else if (items[which].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tv_ratingbar.setText(rating+"");
            }
        });
        AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setTitle("Add Attraction").setPositiveButton("Add",this)
                .setNegativeButton("Cancle",this).setView(view).create();
        Address.setFocusableInTouchMode(true);
        return alertDialog;
    }



    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE){
            if(Name.getText().toString().equals("")){
                Toast.makeText(getActivity(),"Please input name",Toast.LENGTH_SHORT).show();
            }
            else if(!selectImage){
                Toast.makeText(getActivity(),"Please select photo",Toast.LENGTH_SHORT).show();
            }
            else {
                openDatabase();
                if (check > 0) {
                    if (iCallBackUpdateList != null) {
                        iCallBackUpdateList.iCallBackUpdate(attraction, type);
                    }
                    dismiss();
                }
            }
        }
        else if(which==DialogInterface.BUTTON_NEGATIVE)
            dialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null) {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean wantToCloseDialog = false;
                    if(Name.getText().equals("")){
                        Toast toast=Toast.makeText(getActivity(), "Please input name", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,0);
                        toast.show();

                    }
                    else if(!selectImage){
                        Toast toast=Toast.makeText(getActivity(), "Please select photo", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,0);
                        toast.show();
                    }
                    else if(!selectAddress){
                        Toast toast=Toast.makeText(getActivity(), "Please input Address", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,0);
                        toast.show();
                    }
                    else {
                        openDatabase();
                        if (check > 0) {
                            if (iCallBackUpdateList != null) {
                                iCallBackUpdateList.iCallBackUpdate(attraction, type);
                            }
                            wantToCloseDialog=true;
                        }
                    }
                    if (wantToCloseDialog)
                        d.dismiss();
                }
            });
        }
    }

    public void openDatabase(){
        String aName=Name.getText().toString();
        String address=Address.getText().toString();
        String Rating=tv_ratingbar.getText().toString();
        attraction=new Attraction();
        attraction.setName(aName);
        attraction.setAddress(address);
        attraction.setRating(Rating);
        attraction.setFavorite(1);
        attraction.setPhoto(photoPath);
        attraction.setLatitude(position.latitude);
        attraction.setLongitude(position.longitude);
        switch (type){
            case Restaurant.TABLE:
                ProviderRestaurant providerRestaurant=new ProviderRestaurant(getActivity());
                check=providerRestaurant.insertAttraction(attraction);
                providerRestaurant.close();
                break;
            case Hotels.TABLE:
                ProviderHotel providerHotel=new ProviderHotel(getActivity());
                check=providerHotel.insertAttraction(attraction);
                providerHotel.close();
                break;
            case Shop.TABLE:
                ProviderShop providerShop=new ProviderShop(getActivity());
                check=providerShop.insertAttraction(attraction);
                providerShop.close();
                break;
            case "Park":
                ProviderPark providerPark=new ProviderPark(getActivity());
                check=providerPark.insertAttraction(attraction);
                providerPark.close();
                break;
            case "Theater":
                ProviderTheater providerTheater=new ProviderTheater(getActivity());
                check=providerTheater.insertAttraction(attraction);
                providerTheater.close();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==getActivity().RESULT_OK){
            if(requestCode==REQUEST_CODE_CAPTURE_IMAGE && data!=null){
                photo.setBackground(null);
                Bitmap thumbnail= (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes=new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG,90,bytes);
                String path= Environment.getExternalStorageDirectory()+"/Picture";
                File dir=new File(path);
                if(!dir.exists()){
                    dir.mkdir();
                }
                String name=System.currentTimeMillis()+".jpg";
                photoPath=path+"/"+name;
                File destination=new File(path,name);
                FileOutputStream fout;
                try {
                    destination.createNewFile();
                    fout=new FileOutputStream(destination);
                    fout.write(bytes.toByteArray());
                    fout.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selectImage=true;
                photo.setImageBitmap(thumbnail);
            }
            else if(requestCode==REQUEST_CODE_SELECT_IMAGE && data!=null){
                photo.setBackground(null);
                Uri selectedImageUri=data.getData();
                String []FilePathColumn={MediaStore.Images.Media.DATA};
                Cursor cursor=getActivity().getContentResolver().query(selectedImageUri,FilePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(FilePathColumn[0]);
                String PicturePath=cursor.getString(columnIndex);
                photoPath=PicturePath;
                cursor.close();
                photo.setImageBitmap(BitmapFactory.decodeFile(PicturePath));
                selectImage=true;
            }
            else if(requestCode==REQUEST_CODE_MAP && data!=null){
                Bundle bundle=data.getExtras();
                double Lat=bundle.getDouble(Attraction.LAT);
                double Long=bundle.getDouble(Attraction.LONG);
                position=new LatLng(Lat,Long);
                Geocoder geocoder=new Geocoder(getActivity(),Locale.getDefault());
                List<android.location.Address> addresses=new ArrayList<android.location.Address>();
                try {
                    addresses=geocoder.getFromLocation(Lat,Long,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String location="";
                if (addresses.size()>0) {
                    for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); ++i) {
                        location += addresses.get(0).getAddressLine(i) + ", ";
                    }
                    selectAddress=true;
                }
                else{
                    Toast toast=Toast.makeText(getActivity(), "Can not get the location/Please check the network connection", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,0);
                    toast.show();
                }
                Address.setText(location);
            }
        }
    }
}
