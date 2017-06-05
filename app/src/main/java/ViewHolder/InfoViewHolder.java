package ViewHolder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linh.smarttrip.MapsActivity;
import com.example.linh.smarttrip.R;


import Model.Attraction;

/**
 * Created by Linh on 8/2/2015.
 */
public class InfoViewHolder extends RecyclerView.ViewHolder{
    public TextView info;
    Attraction attraction;
    String kind;
    int type;
    public InfoViewHolder(View itemView) {
        super(itemView);
        info= (TextView) itemView.findViewById(R.id.tv_info);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case 0:
                        Intent to_map=new Intent(v.getContext(), MapsActivity.class);
                        to_map.putExtra("map","map");
                        to_map.putExtra("Lattitude",attraction.getLatitude());
                        to_map.putExtra("Longitude",attraction.getLongitude());
                        to_map.putExtra("Name",attraction.getName());
                        to_map.putExtra("id",attraction.getId());
                        to_map.putExtra("kind",kind);
                        to_map.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().getApplicationContext().startActivity(to_map);
                        break;
                    case 1:
                        if(!attraction.getPhone().equals("Updating")) {
                            Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + attraction.getPhone()));
                            call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            v.getContext().getApplicationContext().startActivity(call);
                        }
                        break;
                    case 2:
                        if(!attraction.getLink().equals("Updating")) {
                            Intent to_web = new Intent(Intent.ACTION_VIEW, Uri.parse(attraction.getLink()));
                            to_web.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            v.getContext().getApplicationContext().startActivity(to_web);
                        }
                        break;
                }
            }
        });
    }
    public void bind(int type, Attraction attraction,String kind){
        this.type=type;
        this.attraction=attraction;
        this.kind=kind;
    }
}
