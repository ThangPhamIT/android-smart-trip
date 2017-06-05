package ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linh.smarttrip.InfoActivity;
import com.example.linh.smarttrip.R;

import DAO.AttractionDAO;
import Model.Attraction;

/**
 * Created by Linh on 8/1/2015.
 */
public class AttractionViewHolder extends RecyclerView.ViewHolder {
    public ImageView photo;
    public TextView name;
    public ImageView fav;
    public TextView rank;
    int isClick;
    Attraction attraction;
    String type;
    AttractionDAO attractionDAO;
    Context mContext;
    public final static int RATING_CHANGE=102;
    public AttractionViewHolder(View itemView) {
        super(itemView);
        photo= (ImageView) itemView.findViewById(R.id.iv_photo);
        name= (TextView) itemView.findViewById(R.id.tv_name);
        fav= (ImageView) itemView.findViewById(R.id.iv_fav);
        rank= (TextView) itemView.findViewById(R.id.tv_ranking);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClick==1){
                    attractionDAO=new AttractionDAO(mContext,type);
                    fav.setBackgroundResource(R.drawable.star_icon_grey);
                    attractionDAO.updateFavorite(attraction.getId(), 0, type);
                    attractionDAO.close();
                    isClick=0;
                }
                else{
                    attractionDAO=new AttractionDAO(mContext,type);
                    fav.setBackgroundResource(R.drawable.star_icon_yellow);
                    attractionDAO.updateFavorite(attraction.getId(), 1, type);
                    attractionDAO.close();
                    isClick=1;
                }
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_info=new Intent(v.getContext(), InfoActivity.class);
                Bundle b=new Bundle();
                b.putInt("id",attraction.getId());
                b.putString("type", type);
                to_info.putExtras(b);
                to_info.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().getApplicationContext().startActivity(to_info);
            }
        });
    }
    public void bindAttraction(Attraction src,String type){
        attraction=src;
        this.type=type;
        isClick=attraction.getFavorite();
    }
    public void setDAO(Context context){
        this.mContext=context;

    }
}
