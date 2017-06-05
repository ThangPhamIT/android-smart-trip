package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.linh.smarttrip.R;

/**
 * Created by Linh on 8/1/2015.
 */
public class OptionsAdapter extends BaseAdapter {
    Context mContext;
    String [] name;
    int [] icon;

    public OptionsAdapter(Context mContext, String[] name, int[] icon) {
        this.mContext = mContext;
        this.name = name;
        this.icon = icon;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.options_adapter,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.OptionsName.setText(name[position]);
        holder.OptionsIcon.setBackgroundResource(icon[position]);
        return convertView;
    }
    public class ViewHolder {
        TextView OptionsName;
        ImageView OptionsIcon;
        public ViewHolder(View src){
            OptionsName= (TextView) src.findViewById(R.id.tv_optname);
            OptionsIcon= (ImageView) src.findViewById(R.id.iv_icon);
        }
    }
}
