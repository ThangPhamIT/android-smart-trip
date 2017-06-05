package Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linh.smarttrip.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by Linh on 8/4/2015.
 */
public class AttractionIfoWindow implements GoogleMap.InfoWindowAdapter {
    Context mContext;
    TextView Name;
    TextView Rating;
    List<Attraction> list1;
    List<Attraction> list2;
    List<Attraction> list3;
    List<Attraction> list4;
    List<Attraction> list5;
    ImageView photo;
    ImageView star;
    public AttractionIfoWindow(Context mContext, List<Attraction> list1, List<Attraction> list2, List<Attraction> list3, List<Attraction> list4, List<Attraction> list5) {
        this.mContext = mContext;
        this.list1 = list1;
        this.list3 = list3;
        this.list2 = list2;
        this.list4 = list4;
        this.list5 = list5;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        String name=marker.getSnippet();
        if(name!=null) {
            int id = Integer.parseInt(name.split("_")[1]);
            String kind = name.split("_")[0];
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View root = inflater.inflate(R.layout.attraction_adapter, null, false);
            Name = (TextView) root.findViewById(R.id.tv_name);
            Rating = (TextView) root.findViewById(R.id.tv_ranking);
            photo = (ImageView) root.findViewById(R.id.iv_photo);
            star = (ImageView) root.findViewById(R.id.iv_fav);
            star.setVisibility(View.GONE);
            String aName = "";
            String aRating = "";
            String photo_link = "";
            switch (kind) {
                case Restaurant.TABLE:
                    aName = list1.get(id).getName();
                    aRating = list1.get(id).getRating();
                    photo_link = list1.get(id).getPhoto();
                    break;
                case Hotels.TABLE:
                    aName = list2.get(id).getName();
                    aRating = list2.get(id).getRating();
                    photo_link = list2.get(id).getPhoto();
                    break;
                case Shop.TABLE:
                    aName = list3.get(id).getName();
                    aRating = list3.get(id).getRating();
                    photo_link = list3.get(id).getPhoto();
                    break;
                case Park.TABLE:
                    aName = list4.get(id).getName();
                    aRating = list4.get(id).getRating();
                    photo_link = list4.get(id).getPhoto();
                    break;
                case Theater.TABLE:
                    aName = list5.get(id).getName();
                    aRating = list5.get(id).getRating();
                    photo_link = list5.get(id).getPhoto();
                    break;
            }
            Name.setText(aName);
            Rating.setText(aRating);
            if (photo_link.contains(".gif")) {
                int photo_id = mContext.getResources().getIdentifier(photo_link.substring(0, photo_link.length() - 4), "drawable", mContext.getPackageName());
                if(photo_id!=0) {
                    Picasso.with(mContext).load(photo_id).into(photo);
                }
            } else {
                Picasso.with(mContext).load(new File(photo_link)).into(photo);
            }
            return root;
        }
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
