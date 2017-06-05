package ViewHolder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linh.smarttrip.InfoActivity;
import com.example.linh.smarttrip.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh on 8/2/2015.
 */
public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ViewGroup horizontalScrollView;
    public TextView type;
    List<View> viewList;
    public RestaurantViewHolder(View itemView) {
        super(itemView);
        horizontalScrollView= (ViewGroup) itemView.findViewById(R.id.scrollView);
        type= (TextView) itemView.findViewById(R.id.tv_type);
        viewList=new ArrayList<>();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<viewList.size();++i){
                    viewList.get(i).setOnClickListener(this);
                }
            }
        });
    }
    public void bindFrame(View v){
        viewList.add(v);
    }

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
}
