package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

import Model.Attraction;
import Model.Hotels;
import Model.Restaurant;
import Model.Restaurant;

/**
 * Created by Linh on 8/2/2015.
 */
public class RestaurantDAO {
    SQLiteDatabase RestaurantDB;
    Context mContext;
    public RestaurantDAO(Context context){
        mContext=context;
        File dbFile=mContext.getDatabasePath(Restaurant.TABLE);
        RestaurantDB =SQLiteDatabase.openDatabase(dbFile.getPath(),null,SQLiteDatabase.OPEN_READWRITE);
    }
    public Restaurant getRestaurantbyID(int id){
        Cursor cursor=RestaurantDB.query(Restaurant.TABLE, null, Restaurant.ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        return fetch(cursor);
    }
    public Restaurant fetch(Cursor cursor){
        Restaurant tmp=new Restaurant();
        if(cursor.moveToFirst()) {
            tmp.setId(cursor.getInt(cursor.getColumnIndex(Attraction.ID)));
            tmp.setName(cursor.getString(cursor.getColumnIndex(Attraction.NAME)));
            tmp.setAddress(cursor.getString(cursor.getColumnIndex(Attraction.ADDRESS)));
            tmp.setPhone(cursor.getString(cursor.getColumnIndex(Attraction.PHONE)));
            tmp.setDescription(cursor.getString(cursor.getColumnIndex(Restaurant.DESCRIPTION)));
            tmp.setOpen(cursor.getString(cursor.getColumnIndex(Restaurant.OPEN)));
            tmp.setClose(cursor.getString(cursor.getColumnIndex(Restaurant.CLOSE)));
            tmp.setRating(cursor.getString(cursor.getColumnIndex(Attraction.RATING)));
            tmp.setFavorite(cursor.getInt(cursor.getColumnIndex(Attraction.FAVORITE)));
            tmp.setPhoto(cursor.getString(cursor.getColumnIndex(Attraction.PHOTO)));
            tmp.setLink(cursor.getString(cursor.getColumnIndex(Attraction.LINK)));
            tmp.setLatitude(cursor.getDouble(cursor.getColumnIndex(Attraction.LAT)));
            tmp.setLongitude(cursor.getDouble(cursor.getColumnIndex("Longtitude")));
        }
        cursor.close();
        RestaurantDB.close();
        return tmp;
    }
    public long insertCountry(Attraction attraction)
    {
        return RestaurantDB.insert(Restaurant.TABLE, null, getContentValues(attraction));
    }
    private ContentValues getContentValues(Attraction attraction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Attraction.NAME, attraction.getName());
        contentValues.put(Attraction.ADDRESS, attraction.getAddress());
        contentValues.put(Attraction.RATING, attraction.getRating());
        contentValues.put(Attraction.FAVORITE, attraction.getFavorite());
        contentValues.put(Attraction.PHOTO,attraction.getPhoto());
        contentValues.put(Attraction.LAT,attraction.getLatitude());
        contentValues.put("Longtitude",attraction.getLongitude());
        contentValues.put(Restaurant.TYPE,4);
        return contentValues;
    }
    public void close(){
        RestaurantDB.close();
    }
    public long updateRestaurant(ContentValues contentValues,int id){
        return RestaurantDB.update(Restaurant.TABLE,contentValues,Restaurant.ID + "=?", new String[]{String.valueOf(id)});

    }
}
