package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Model.Attraction;
import Model.Hotels;
import Model.Park;
import Model.Restaurant;
import Model.Shop;
import Model.Theater;

/**
 * Created by Linh on 8/1/2015.
 */
public class AttractionDAO {
    SQLiteDatabase AttractionDB;
    Context mContext;
    String table_name;
    int count1;
    int count2;
    int count3;
    int count4;
    public AttractionDAO(Context context,String type){
        this.table_name=type;
        mContext=context;
        File dbFile=mContext.getDatabasePath(type);
        AttractionDB =SQLiteDatabase.openDatabase(dbFile.getPath(),null,SQLiteDatabase.OPEN_READWRITE);
    }
    public List<Attraction> get(){
        Cursor cursor;
        if(table_name.equals("Restaurant")){
            cursor=AttractionDB.query(table_name,null,null,null,null,null, Restaurant.TYPE);
            count1=0;
            count2=0;
            count3=0;
            count4=0;
        }
        else {
            cursor = AttractionDB.query(table_name, null, null, null, null, null, null);
        }
        return fetchAll(cursor);
    }
    public int[] countType(){
        int [] countType=new int[4];
        countType[0]=count1;
        countType[1]=count2;
        countType[2]=count3;
        countType[3]=count4;
        return countType;
    }
    public List<Attraction> fetchAll(Cursor cursor){
        List<Attraction> list=new ArrayList<>();

        if(cursor.moveToFirst())
        {
            do{
                list.add(fetch(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();

        AttractionDB.close();
        return list;

    }
    public List<Attraction> fetchFavorite(){
        List<Attraction> list=new ArrayList<>();
        Cursor cursor=null;
        cursor=AttractionDB.query(table_name,null,Attraction.FAVORITE+"=?",new String[]{"1"},null,null,null);
        if(cursor.moveToFirst())
        {
            do{
                list.add(fetchFav(cursor));
            }while (cursor.moveToNext());
        }
        cursor.close();

        AttractionDB.close();
        return list;
    }
    public Attraction fetchFav(Cursor cursor){
        Attraction tmp = new Attraction();
        tmp.setId(cursor.getInt(cursor.getColumnIndex(Attraction.ID)));
        tmp.setName(cursor.getString(cursor.getColumnIndex(Attraction.NAME)));
        tmp.setFavorite(cursor.getInt(cursor.getColumnIndex(Attraction.FAVORITE)));
        tmp.setPhoto(cursor.getString(cursor.getColumnIndex(Attraction.PHOTO)));
        tmp.setRating(cursor.getString(cursor.getColumnIndex(Attraction.RATING)));
        return tmp;
    }
    public Attraction fetch(Cursor cursor){
        Attraction tmp=new Attraction();
        tmp.setId(cursor.getInt(cursor.getColumnIndex(Attraction.ID)));
        tmp.setName(cursor.getString(cursor.getColumnIndex(Attraction.NAME)));
        tmp.setFavorite(cursor.getInt(cursor.getColumnIndex(Attraction.FAVORITE)));
        tmp.setPhoto(cursor.getString(cursor.getColumnIndex(Attraction.PHOTO)));
        tmp.setRating(cursor.getString(cursor.getColumnIndex(Attraction.RATING)));
        tmp.setLatitude(cursor.getDouble(cursor.getColumnIndex(Attraction.LAT)));
        if(table_name.equals("Restaurant")) {
            tmp.setLongitude(cursor.getDouble(cursor.getColumnIndex("Longtitude")));
            switch (cursor.getInt(cursor.getColumnIndex(Restaurant.TYPE))) {
                case 1:
                    ++count1;
                    break;
                case 2:
                    ++count2;
                    break;
                case 3:
                    ++count3;
                    break;
                case 4:
                    ++count4;
                    break;
            }
        }
        else{
            tmp.setLongitude(cursor.getDouble(cursor.getColumnIndex(Attraction.LONG)));
        }
        return tmp;
    }



    public int updateRating(int id, float rating,String type)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Attraction.RATING, rating);
        return AttractionDB.update(type, contentValues, Attraction.ID + " =?", new String[]{id+""});
    }
    public int updateFavorite(int id, int fav,String type)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Attraction.FAVORITE,fav);
        return AttractionDB.update(type, contentValues, Attraction.ID + " =?", new String[]{id+""});
    }

    private ContentValues getContentValues(Attraction attraction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Attraction.ID,attraction.getId());
        contentValues.put(Attraction.NAME, attraction.getName());
        contentValues.put(Attraction.ADDRESS, attraction.getAddress());
        contentValues.put(Attraction.PHONE, attraction.getPhone());
        contentValues.put(Attraction.RATING, attraction.getRating());
        contentValues.put(Attraction.FAVORITE, attraction.getFavorite());
        return contentValues;
    }
    public void close(){
        AttractionDB.close();
    }

}
