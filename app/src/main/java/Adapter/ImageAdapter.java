package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.linh.smarttrip.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Linh on 8/3/2015.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    int []photo;
    Context mContext;

    public ImageAdapter(int[] photo, Context mContext) {
        this.photo = photo;
        this.mContext = mContext;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_iem_adapter,parent,false);
        ImageViewHolder viewHolder=new ImageViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Picasso.with(mContext).load(photo[position]).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return photo.length;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView photo;
        public ImageViewHolder(View itemView) {
            super(itemView);
            photo= (ImageView) itemView.findViewById(R.id.iv_photo);
        }
    }
}
