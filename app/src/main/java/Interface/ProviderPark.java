package Interface;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import DAO.ParkDAO;
import Model.Attraction;

/**
 * Created by Linh on 8/2/2015.
 */
public class ProviderPark implements IProvider{
    ParkDAO ParkDAO;
    public ProviderPark(Context context){
        ParkDAO=new ParkDAO(context);
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
        return ParkDAO.getParkbyID(id);
    }

    @Override
    public int updateRating(int id, float rating, String type) {
        return 0;
    }

    @Override
    public long insertAttraction(Attraction attraction) {
        return ParkDAO.insertCountry(attraction);
    }

    @Override
    public void close() {
        ParkDAO.close();
    }
    public long update(ContentValues contentValues,int id){
        return ParkDAO.updatePark(contentValues,id);
    }
}
