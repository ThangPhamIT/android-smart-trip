package Parcellable;

import android.os.Parcel;
import android.os.Parcelable;

import Model.Attraction;

/**
 * Created by Linh on 8/1/2015.
 */
public class AttractionParcellable implements Parcelable {
    Attraction attraction;
    @Override
    public int describeContents() {
        return hashCode();
    }
    public AttractionParcellable(Attraction src){
        attraction=src;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(attraction.getId());
        dest.writeString(attraction.getName());
        dest.writeString(attraction.getPhoto());
        dest.writeInt(attraction.getFavorite());
    }
    public Attraction getAttraction(){
        return attraction;
    }
    public AttractionParcellable(Parcel b){
        attraction.setId(b.readInt());
        attraction.setName(b.readString());
        attraction.setPhone(b.readString());
        attraction.setFavorite(b.readInt());

    }
    public static final Parcelable.Creator<AttractionParcellable> CREATOR=new Creator<AttractionParcellable>() {
        @Override
        public AttractionParcellable createFromParcel(Parcel source) {
            return new AttractionParcellable(source);
        }

        @Override
        public AttractionParcellable[] newArray(int size) {
            return new AttractionParcellable[size];
        }
    };


}
