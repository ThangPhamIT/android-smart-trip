package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.linh.smarttrip.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import DAO.AttractionDAO;
import Model.Attraction;
import ViewHolder.AttractionViewHolder;

/**
 * Created by Linh on 8/1/2015.
 */
public class AttractionAdapter extends RecyclerView.Adapter<AttractionViewHolder>{
    Context mContext;
    List<Attraction> list;
    String type;
    public AttractionAdapter(Context mContext, List<Attraction> list,String type) {
        this.mContext = mContext;
        this.list = list;
        this.type=type;
    }

    @Override
    public AttractionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_adapter,parent,false);
        AttractionViewHolder viewHolder=new AttractionViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AttractionViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        String photo=list.get(position).getPhoto();
        holder.photo.setBackground(null);
        if(photo.contains(".gif")) {
            int id = mContext.getResources().getIdentifier(photo.substring(0, photo.length() - 4), "drawable", mContext.getPackageName());
            Picasso.with(mContext).load(id).into(holder.photo);
        }
        else
        {
            Picasso.with(mContext).load(new File(photo)).into(holder.photo);
        }
        if(list.get(position).getFavorite()==0) {
            holder.fav.setBackgroundResource(R.drawable.star_icon_grey);
        }
        else
        {
            holder.fav.setBackgroundResource(R.drawable.star_icon_yellow);
        }
        holder.rank.setText(list.get(position).getRating());
        holder.setDAO(mContext);
        holder.bindAttraction(list.get(position),type);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
