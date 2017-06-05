package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

import Model.Attraction;
import Model.Hotels;
import Model.Shop;

/**
 * Created by Linh on 8/2/2015.
 */
public class ShopDAO {
    SQLiteDatabase ShopDB;
    Context mContext;
    public ShopDAO(Context context){
        mContext=context;
        File dbFile=mContext.getDatabasePath(Shop.TABLE);
        ShopDB =SQLiteDatabase.openDatabase(dbFile.getPath(),null,SQLiteDatabase.OPEN_READWRITE);
    }
    public Shop getShopbyID(int id){
        Cursor cursor=ShopDB.query(Shop.TABLE, null, Shop.ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        return fetch(cursor);
    }
    public Shop fetch(Cursor cursor){
        Shop tmp=new Shop();
        if(cursor.moveToFirst()) {
            tmp.setId(cursor.getInt(cursor.getColumnIndex(Attraction.ID)));
            tmp.setName(cursor.getString(cursor.getColumnIndex(Shop.NAME)));
            tmp.setAddress(cursor.getString(cursor.getColumnIndex(Shop.ADDRESS)));
            tmp.setPhone(cursor.getString(cursor.getColumnIndex(Shop.PHONE)));
            tmp.setRating(cursor.getString(cursor.getColumnIndex(Shop.RATING)));
            tmp.setFavorite(cursor.getInt(cursor.getColumnIndex(Shop.FAVORITE)));
            tmp.setPhoto(cursor.getString(cursor.getColumnIndex(Shop.PHOTO)));
            tmp.setLink(cursor.getString(cursor.getColumnIndex(Shop.LINK)));
            tmp.setLatitude(cursor.getDouble(cursor.getColumnIndex(Shop.LAT)));
            tmp.setLongitude(cursor.getDouble(cursor.getColumnIndex(Shop.LONG)));
        }
        cursor.close();
        ShopDB.close();
        return tmp;
    }
    public long insertCountry(Attraction attraction)
    {
        return ShopDB.insert(Shop.TABLE, null, getContentValues(attraction));
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
        ShopDB.close();
    }
    public long updateShop(ContentValues contentValues,int id){
        return ShopDB.update(Shop.TABLE,contentValues,Shop.ID + "=?", new String[]{String.valueOf(id)});
    }
}
