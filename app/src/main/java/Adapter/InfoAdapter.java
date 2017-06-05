package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.linh.smarttrip.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Attraction;
import ViewHolder.InfoViewHolder;

/**
 * Created by Linh on 8/2/2015.
 */
public class InfoAdapter extends RecyclerView.Adapter<InfoViewHolder> {
    //int []icon={R.drawable.address_icon,R.drawable.phone_icon};
    List<String> info;
    Context mContext;
    Attraction attraction;
    String kind;
    int k=0;
    public void add(String s,int position) {
        position = position == -1 ? getItemCount()  : position;
        info.add(position,s);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            info.remove(position);
            notifyItemRemoved(position);
        }
    }
    public InfoAdapter(Context context,List<String> info, Attraction attraction,String kind){
        mContext=context;
        this.info=info;
        this.attraction=attraction;
        this.kind=kind;
    }
    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.info_adapter,parent,false);
        InfoViewHolder viewHolder=new InfoViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
            holder.info.setText(info.get(position));
            holder.bind(position,attraction,kind);
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

}
