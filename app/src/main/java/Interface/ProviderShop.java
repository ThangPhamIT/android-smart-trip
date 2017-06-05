package Interface;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import DAO.ShopDAO;
import Model.Attraction;

/**
 * Created by Linh on 8/2/2015.
 */
public class ProviderShop implements IProvider{
    ShopDAO ShopDAO;
    public ProviderShop(Context context){
        ShopDAO=new ShopDAO(context);
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
        return ShopDAO.getShopbyID(id);
    }

    @Override
    public int updateRating(int id, float rating, String type) {
        return 0;
    }

    @Override
    public long insertAttraction(Attraction attraction) {
        return ShopDAO.insertCountry(attraction);
    }
    public void close(){
        ShopDAO.close();
    }
    public long update(ContentValues contentValues,int id){
        return ShopDAO.updateShop(contentValues,id);
    }
}
