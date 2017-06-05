package Interface;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import DAO.HotelDAO;
import Model.Attraction;

/**
 * Created by Linh on 8/2/2015.
 */
public class ProviderHotel implements IProvider {
    HotelDAO hotelDAO;
    public ProviderHotel(Context context){
        hotelDAO=new HotelDAO(context);
    }
    @Override
    public List<Attraction> get() {
        return null;
    }

    @Override
    public int[] getCount() {
        return new int[0];
    }

    @Override
    public Attraction getById(int id) {
        return hotelDAO.getHotelbyID(id);
    }

    @Override
    public int updateRating(int id, float rating, String type) {
        return 0;
    }

    @Override
    public long insertAttraction(Attraction attraction) {
        return hotelDAO.insertCountry(attraction);
    }

    @Override
    public void close() {
        hotelDAO.close();
    }
    public long update(ContentValues contentValues,int id){
        return hotelDAO.updateHotel(contentValues,id);
    }
}
