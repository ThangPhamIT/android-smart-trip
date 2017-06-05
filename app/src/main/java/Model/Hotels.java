package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Linh on 8/1/2015.
 */
public class Hotels extends Attraction {
    public final static String TABLE="Hotel";
    public final static String ROOM_TYPE="Room_type";
    public final static String ACCOMODATION="Accomodation";
    public final static String HOTEL_TYPE="Hotel_type";
    public final static String DESCRIPTION="Description";
    public final static String PRICE="Price";
    String room_type;
    String accomodation;
    String hotel_type;
    String description;
    String price;

    public Hotels(int id, String name, String address, String phone, String rating, String photo, String link, double latitude, double longitude, int favorite, String room_type, String accomodation, String hotel_type, String description, String price) {
        super(id, name, address, phone, rating, photo, link, latitude, longitude, favorite);
        this.room_type = room_type;
        this.accomodation = accomodation;
        this.hotel_type = hotel_type;
        this.description = description;
        this.price = price;
    }

    public Hotels() {
        super();
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(String accomodation) {
        this.accomodation = accomodation;
    }

    public String getHotel_type() {
        return hotel_type;
    }

    public void setHotel_type(String hotel_type) {
        this.hotel_type = hotel_type;
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

    public static List<String> gettitle(){
        List<String> titles=new ArrayList<>();
        String []title=new String[]{
                DESCRIPTION,ROOM_TYPE,ACCOMODATION,HOTEL_TYPE,PRICE
        };
        Collections.addAll(titles, title());
        Collections.addAll(titles, title);
        return titles;
    }
}
