package Interface;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import DAO.TheaterDAO;
import Model.Attraction;

/**
 * Created by Linh on 8/2/2015.
 */
public class ProviderTheater  implements IProvider{
    DAO.TheaterDAO TheaterDAO;
    public ProviderTheater(Context context){
        TheaterDAO=new TheaterDAO(context);
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
        return TheaterDAO.getTheaterbyID(id);
    }

    @Override
    public int updateRating(int id, float rating, String type) {
        return 0;
    }

    @Override
    public long insertAttraction(Attraction attraction) {
        return TheaterDAO.insertCountry(attraction);
    }

    @Override
    public void close() {
        TheaterDAO.close();
    }
    public long update(ContentValues contentValues,int id){
        return TheaterDAO.updateTheater(contentValues,id);
    }
}
