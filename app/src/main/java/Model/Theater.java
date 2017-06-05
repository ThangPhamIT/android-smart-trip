package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Linh on 8/1/2015.
 */
public class Theater extends Attraction {
    public final static String DESCRIPTION="Description";
    public final static String PRICE="Price";
    public final static String TABLE="Theater";
    String description;
    String price;
    public static List<String> gettitle(){
        List<String> titles=new ArrayList<>();
        String []title=new String[]{
                DESCRIPTION,PRICE
        };
        Collections.addAll(titles, title());
        Collections.addAll(titles, title);
        return titles;
    }
    public Theater(){
        super();
    }
    public Theater(int id, String name, String address, String phone, String rating, String photo, String link, double latitude, double longitude, int favorite, String description, String price) {
        super(id, name, address, phone, rating, photo, link, latitude, longitude, favorite);
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
