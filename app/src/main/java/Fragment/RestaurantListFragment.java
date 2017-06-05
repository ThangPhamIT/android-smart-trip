package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.linh.smarttrip.R;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.AttractionAdapter;
import Adapter.RestaurantAdapter;
import Interface.ProviderAttraction;
import Interface.iCallBackUpdateList;
import Model.Attraction;
import Model.Hotels;
import Model.Park;
import Model.Restaurant;
import Model.Shop;
import Model.Theater;
import Parcellable.AttractionParcellable;

/**
 * Created by Linh on 8/2/2015.
 */
public class RestaurantListFragment extends Fragment implements iCallBackUpdateList{
    public final static String LIST="list";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton add;
    AttractionDialogFragment dialogFragment;
    List<Attraction> attractionList;
    RestaurantAdapter adapter;
    int []count;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=null;
        rootView=inflater.inflate(R.layout.hotel_list_fragment,container,false);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.rv_list);
        layoutManager=new LinearLayoutManager(rootView.getContext());
        add= (FloatingActionButton) rootView.findViewById(R.id.btn_add);
        add.attachToRecyclerView(recyclerView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                add.show();
            }

            @Override
            public void onScrollUp() {
                add.hide();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment=new AttractionDialogFragment(RestaurantListFragment.this);
                dialogFragment.show(getActivity().getFragmentManager(),"dialog");
            }
        });
        AttractionParcellable[]parcellables= (AttractionParcellable[])getArguments().getParcelableArray(AttractionListFragment.LIST);
        attractionList=new ArrayList<>();
        for(int i=0;i<parcellables.length;++i){
            attractionList.add(parcellables[i].getAttraction());
        }
        count=getArguments().getIntArray("count");
        adapter=new RestaurantAdapter(attractionList, rootView.getContext(),count);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
    public static final RestaurantListFragment newInstance(List<Attraction> list,int [] count) {
        RestaurantListFragment fragment=new RestaurantListFragment();
        Bundle b=new Bundle();
        AttractionParcellable[]parcellableList = new AttractionParcellable[list.size()];
        for(int i=0;i<list.size();++i){
            AttractionParcellable tmp=new AttractionParcellable(list.get(i));
            parcellableList[i]=tmp;
        }
        b.putParcelableArray(AttractionListFragment.LIST, parcellableList);
        b.putIntArray("count",count);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void iCallBackUpdate(Attraction attraction,String type) {
        attractionList.add(attraction);
        count[3]+=1;
        RestaurantAdapter restaurantAdapter=new RestaurantAdapter(attractionList,getActivity(),count);
        recyclerView.setAdapter(restaurantAdapter);
    }

    @Override
    public void iCallBackEdit() {

    }

}
