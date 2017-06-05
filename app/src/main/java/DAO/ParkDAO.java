package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

import Model.Attraction;
import Model.Hotels;
import Model.Park;

/**
 * Created by Linh on 8/2/2015.
 */
public class ParkDAO {
    SQLiteDatabase ParkDB;
    Context mContext;
    public ParkDAO(Context context){
        mContext=context;
        File dbFile=mContext.getDatabasePath(Park.TABLE);
        ParkDB =SQLiteDatabase.openDatabase(dbFile.getPath(),null,SQLiteDatabase.OPEN_READWRITE);
    }
    public Park getParkbyID(int id){
        Cursor cursor=ParkDB.query(Park.TABLE, null, Park.ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        return fetch(cursor);
    }
    public Park fetch(Cursor cursor){
        Park tmp=new Park();
        if(cursor.moveToFirst()) {
            tmp.setId(cursor.getInt(cursor.getColumnIndex(Attraction.ID)));
            tmp.setName(cursor.getString(cursor.getColumnIndex(Park.NAME)));
            tmp.setAddress(cursor.getString(cursor.getColumnIndex(Park.ADDRESS)));
            tmp.setPhone(cursor.getString(cursor.getColumnIndex(Park.PHONE)));
            tmp.setDescription(cursor.getString(cursor.getColumnIndex(Park.DESCRIPTION)));
            tmp.setPrice(cursor.getString(cursor.getColumnIndex(Park.PRICE)));
            tmp.setRating(cursor.getString(cursor.getColumnIndex(Park.RATING)));
            tmp.setFavorite(cursor.getInt(cursor.getColumnIndex(Park.FAVORITE)));
            tmp.setPhoto(cursor.getString(cursor.getColumnIndex(Park.PHOTO)));
            tmp.setLink(cursor.getString(cursor.getColumnIndex(Park.LINK)));
            tmp.setLatitude(cursor.getDouble(cursor.getColumnIndex(Park.LAT)));
            tmp.setLongitude(cursor.getDouble(cursor.getColumnIndex(Park.LONG)));
        }
        cursor.close();
        ParkDB.close();
        return tmp;
    }
    public long insertCountry(Attraction attraction)
    {
        return ParkDB.insert(Park.TABLE, null, getContentValues(attraction));
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
        contentValues.put(Attraction.LONG,attraction.getLongitude());
        return contentValues;
    }
    public void close(){
        ParkDB.close();
    }
    public long updatePark(ContentValues contentValues,int id){
        return ParkDB.update(Park.TABLE,contentValues,Park.ID + "=?", new String[]{String.valueOf(id)});
    }
}
