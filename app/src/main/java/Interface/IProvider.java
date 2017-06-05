package Interface;

import java.util.List;

import Model.Attraction;

/**
 * Created by Linh on 8/1/2015.
 */
public interface IProvider {
    List<Attraction> get();
    int [] getCount();
    Attraction getById(int id);
    int updateRating(int id, float rating,String type);
    long insertAttraction(Attraction attraction);
    void close();
}
