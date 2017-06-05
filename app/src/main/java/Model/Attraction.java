package Model;

import java.util.Collection;

/**
 * Created by Linh on 8/1/2015.
 */
public class Attraction  {
    public final static String ID="id";
    public final static String NAME="Name";
    public final static String PHONE="Phone";
    public final static String RATING="Rating";
    public final static String PHOTO="Photo";
    public final static String ADDRESS="Address";
    public final static String LINK="Link";
    public final static String LAT="Lattitude";
    public final static String LONG="Longitude";
    public final static String FAVORITE="Favorite";
    public final static String TYPE="type";
    int id;
    String name;
    String address;
    String phone;
    String rating;
    String photo;
    String link;
    double latitude;
    double longitude;
    int favorite;

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
    public Attraction(){}
    public Attraction(int id, String name, String address, String phone, String rating, String photo, String link, double latitude, double longitude,int favorite) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.photo = photo;
        this.link = link;
        this.latitude = latitude;
        this.longitude = longitude;
        this.favorite=favorite;

    }
    public  static String[] title(){
        String[]title=new String[]{
                ADDRESS,PHONE,"WEBSITE"
        };
        return title;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
