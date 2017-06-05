package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.List;

import Model.Attraction;
import Model.Hotels;

/**
 * Created by Linh on 8/1/2015.
 */
public class HotelDAO {
    SQLiteDatabase HotelDB;
    Context mContext;
    public HotelDAO(Context context){
        mContext=context;
        File dbFile=mContext.getDatabasePath(Hotels.TABLE);
        HotelDB =SQLiteDatabase.openDatabase(dbFile.getPath(),null,SQLiteDatabase.OPEN_READWRITE);
    }
    public Hotels getHotelbyID(int id){
        Cursor cursor=HotelDB.query(Hotels.TABLE, null, Hotels.ID + " =?", new String[]{String.valueOf(id)}, null, null, null);
        return fetch(cursor);
    }
    public Hotels fetch(Cursor cursor){
        Hotels tmp=new Hotels();
        if(cursor.moveToFirst()) {
            tmp.setId(cursor.getInt(cursor.getColumnIndex(Attraction.ID)));
            tmp.setName(cursor.getString(cursor.getColumnIndex(Hotels.NAME)));
            tmp.setAddress(cursor.getString(cursor.getColumnIndex(Hotels.ADDRESS)));
            tmp.setPhone(cursor.getString(cursor.getColumnIndex(Hotels.PHONE)));
            tmp.setAccomodation(cursor.getString(cursor.getColumnIndex(Hotels.ACCOMODATION)));
            tmp.setDescription(cursor.getString(cursor.getColumnIndex(Hotels.DESCRIPTION)));
            tmp.setHotel_type(cursor.getString(cursor.getColumnIndex(Hotels.HOTEL_TYPE)));
            tmp.setRoom_type(cursor.getString(cursor.getColumnIndex(Hotels.ROOM_TYPE)));
            tmp.setPrice(cursor.getString(cursor.getColumnIndex(Hotels.PRICE)));
            tmp.setRating(cursor.getString(cursor.getColumnIndex(Hotels.RATING)));
            tmp.setFavorite(cursor.getInt(cursor.getColumnIndex(Hotels.FAVORITE)));
            tmp.setPhoto(cursor.getString(cursor.getColumnIndex(Hotels.PHOTO)));
            tmp.setLink(cursor.getString(cursor.getColumnIndex(Hotels.LINK)));
            tmp.setLatitude(cursor.getDouble(cursor.getColumnIndex(Hotels.LAT)));
            tmp.setLongitude(cursor.getDouble(cursor.getColumnIndex(Hotels.LONG)));
        }
        cursor.close();
        HotelDB.close();
        return tmp;
    }
    public long insertCountry(Attraction attraction)
    {
        return HotelDB.insert(Hotels.TABLE, null, getContentValues(attraction));
    }
    private ContentValues getContentValues(Attraction attraction)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Attraction.NAME, attraction.getName());
        contentValues.put(Attraction.ADDRESS, attraction.getAddress());
        contentValues.put(Attraction.RATING, attraction.getRating());
        contentValues.put(Attraction.PHOTO,attraction.getPhoto());
        contentValues.put(Attraction.FAVORITE, attraction.getFavorite());
        contentValues.put(Attraction.LAT,attraction.getLatitude());
        contentValues.put(Attraction.LONG,attraction.getLongitude());
        return contentValues;
    }
    public long updateHotel(ContentValues contentValues,int id){
        return HotelDB.update(Hotels.TABLE,contentValues,Hotels.ID + "=?", new String[]{String.valueOf(id)});
    }
    public void close(){
        HotelDB.close();
    }

}
