package Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linh.smarttrip.InfoActivity;
import com.example.linh.smarttrip.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import Model.Attraction;
import ViewHolder.AttractionViewHolder;
import ViewHolder.RestaurantViewHolder;

/**
 * Created by Linh on 8/2/2015.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {
    List<Attraction> list;
    Context mContext;
    int [] count;
    int k;
    String [] types ={"Restaurants","Buffet","Coffee and Drinks","My Restaurant"};
    public RestaurantAdapter(List<Attraction> list, Context mContext, int[] count) {
        this.list = list;
        this.mContext = mContext;
        this.count = count;
        k=0;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_adapter,parent,false);
        RestaurantViewHolder viewHolder=new RestaurantViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.type.setText(types[position]);
        for (int i=0;i<count[position];++i){
            final View Single=LayoutInflater.from(mContext).inflate(R.layout.item_layout,null,false);
            TextView name = (TextView) Single.findViewById(R.id.tv_name);
            ImageView photo= (ImageView) Single.findViewById(R.id.iv_photo);
            name.setText(list.get(k).getName());
            String photo_name=list.get(k).getPhoto();
            if(photo_name.contains("gif")) {
                int id = mContext.getResources().getIdentifier(photo_name.substring(0, photo_name.length() - 4), "drawable", mContext.getPackageName());
                Picasso.with(mContext).load(id).into(photo);
            }
            else{
                Picasso.with(mContext).load(new File(photo_name)).into(photo);
            }
            Single.setId(list.get(k).getId());
            Single.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent to_info=new Intent(v.getContext(), InfoActivity.class);
                    Bundle b=new Bundle();
                    b.putInt("id",v.getId());
                    b.putString("type", "Restaurant");
                    to_info.putExtras(b);
                    to_info.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().getApplicationContext().startActivity(to_info);
                }
            });
            holder.horizontalScrollView.addView(Single);
            holder.bindFrame(Single);
            ++k;
        }
    }


    @Override
    public int getItemCount() {
        return count.length;
    }
}
