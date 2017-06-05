package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Linh on 8/1/2015.
 */
public class Restaurant extends Attraction {
    public final static String DESCRIPTION="Description";
    public final static String OPEN="Open";
    public final static String CLOSE="Close";
    public final static String TYPE="Type";
    public final static String TABLE="Restaurant";
    String description;
    String open;
    String close;
    int type;
    public static List<String> gettitle(){
        List<String> titles=new ArrayList<>();
        String []title=new String[]{
                DESCRIPTION,OPEN,CLOSE
        };
        Collections.addAll(titles, title());
        Collections.addAll(titles, title);
        return titles;
    }
    public Restaurant(){
        super();
    }
    public Restaurant(int id, String name, String address, String phone, String rating, String photo, String link, double latitude, double longitude, int favorite, String description, String open, String close, int type) {
        super(id, name, address, phone, rating, photo, link, latitude, longitude, favorite);
        this.description = description;
        this.open = open;
        this.close = close;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
