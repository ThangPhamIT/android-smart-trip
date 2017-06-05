package Interface;

import android.content.Context;

import java.util.List;

import DAO.AttractionDAO;
import Model.Attraction;

/**
 * Created by Linh on 8/1/2015.
 */
public class ProviderAttraction implements IProvider {
    AttractionDAO attractionDAO;
    public ProviderAttraction (Context context,String type){
        attractionDAO=new AttractionDAO(context,type);
    }

    @Override
    public List<Attraction> get() {
        return attractionDAO.get();
    }

    @Override
    public int[] getCount() {
        return attractionDAO.countType();
    }

    @Override
    public Attraction getById(int id) {
        return null;
    }

    @Override
    public int updateRating(int id, float rating, String type) {
        return attractionDAO.updateRating(id,rating,type);
    }

    @Override
    public long insertAttraction(Attraction attraction) {
        return 0;
    }

    @Override
    public void close() {
        attractionDAO.close();
    }

    public int updateFavorite(int id, int fav, String type) {
        return attractionDAO.updateRating(id,fav,type);
    }
    public List<Attraction> getFav(){
         return attractionDAO.fetchFavorite();
    }
}
