package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

import Model.Attraction;
import Model.Hotels;
import Model.Theater;

/**
 * Created by Linh on 8/2/2015.
 */
public class TheaterDAO {
    SQLiteDatabase TheaterDB;
    Context mContext;
    public TheaterDAO(Context context){
        mContext=context;
        File dbFile=mContext.getDatabasePath(Theater.TABLE);
        TheaterDB =SQLiteDatabase.openDatabase(dbFile.getPath(),null,SQLiteDatabase.OPEN_READWRITE);
    }
    public Theater getTheaterbyID(int id){
        Cursor cursor=TheaterDB.query(Theater.TABLE, null, Theater.ID + " =?", new String[]{String.valueOf(id)}, null, null, null);
        return fetch(cursor);
    }
    public Theater fetch(Cursor cursor){
        Theater tmp=new Theater();
        if(cursor.moveToFirst()) {
            tmp.setId(cursor.getInt(cursor.getColumnIndex(Attraction.ID)));
            tmp.setName(cursor.getString(cursor.getColumnIndex(Theater.NAME)));
            tmp.setAddress(cursor.getString(cursor.getColumnIndex(Theater.ADDRESS)));
            tmp.setPhone(cursor.getString(cursor.getColumnIndex(Theater.PHONE)));
            tmp.setDescription(cursor.getString(cursor.getColumnIndex(Theater.DESCRIPTION)));
            tmp.setPrice(cursor.getString(cursor.getColumnIndex(Theater.PRICE)));
            tmp.setRating(cursor.getString(cursor.getColumnIndex(Theater.RATING)));
            tmp.setFavorite(cursor.getInt(cursor.getColumnIndex(Theater.FAVORITE)));
            tmp.setPhoto(cursor.getString(cursor.getColumnIndex(Theater.PHOTO)));
            tmp.setLink(cursor.getString(cursor.getColumnIndex(Theater.LINK)));
            tmp.setLatitude(cursor.getDouble(cursor.getColumnIndex(Theater.LAT)));
            tmp.setLongitude(cursor.getDouble(cursor.getColumnIndex(Theater.LONG)));
        }
        cursor.close();
        TheaterDB.close();
        return tmp;
    }
    public long insertCountry(Attraction attraction)
    {
        return TheaterDB.insert(Theater.TABLE, null, getContentValues(attraction));
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
        TheaterDB.close();
    }
    public long updateTheater(ContentValues contentValues,int id){
        return TheaterDB.update(Theater.TABLE,contentValues,Theater.ID + "=?", new String[]{String.valueOf(id)});
    }
}
