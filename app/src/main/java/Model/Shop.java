package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Linh on 8/1/2015.
 */
public class Shop extends Attraction {
    public final static String TABLE="Shop";
    public Shop(){
        super();
    }
    public static List<String> gettitle(){
        List<String> titles=new ArrayList<>();
        Collections.addAll(titles, title());
        return titles;
    }
    public Shop(int id, String name, String address, String phone, String rating, String photo, String link, double latitude, double longitude, int favorite) {
        super(id, name, address, phone, rating, photo, link, latitude, longitude, favorite);
    }
}
