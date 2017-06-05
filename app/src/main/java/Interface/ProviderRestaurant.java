package Interface;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import DAO.RestaurantDAO;
import Model.Attraction;

/**
 * Created by Linh on 8/2/2015.
 */
public class ProviderRestaurant implements IProvider {
    RestaurantDAO restaurantDAO;
    public ProviderRestaurant(Context context){
        restaurantDAO=new RestaurantDAO(context);
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
        return restaurantDAO.getRestaurantbyID(id);
    }

    @Override
    public int updateRating(int id, float rating, String type) {
        return 0;
    }

    @Override
    public long insertAttraction(Attraction attraction) {
        return restaurantDAO.insertCountry(attraction);
    }

    @Override
    public void close() {
        restaurantDAO.close();
    }
    public long update(ContentValues contentValues,int id){
        return restaurantDAO.updateRestaurant(contentValues,id);
    }
}
