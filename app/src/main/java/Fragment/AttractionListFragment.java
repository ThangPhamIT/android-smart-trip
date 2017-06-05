package Fragment;

import android.content.Intent;
import android.content.IntentFilter;
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
import Interface.ProviderAttraction;
import Model.Attraction;

import Model.Hotels;
import Model.Park;
import Model.Shop;
import Model.Theater;
import Parcellable.AttractionParcellable;

/**
 * Created by Linh on 8/1/2015.
 */
public class AttractionListFragment extends Fragment implements Interface.iCallBackUpdateList{
    public final static String LIST="list";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String type;
    FloatingActionButton add;
    List<Attraction> attractionList;
    AttractionAdapter adapter;
    public void setType(String type) {
        this.type = type;
    }
    AttractionDialogFragment dialogFragment;
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
                dialogFragment=new AttractionDialogFragment(AttractionListFragment.this);
                dialogFragment.show(getActivity().getFragmentManager(),"dialog");
            }
        });
        AttractionParcellable []parcellables= (AttractionParcellable[]) getArguments().getParcelableArray(AttractionListFragment.LIST);
        attractionList=new ArrayList<>();
        for(int i=0;i<parcellables.length;++i){
            attractionList.add(parcellables[i].getAttraction());
        }
        adapter=new AttractionAdapter(rootView.getContext(), attractionList,type);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
    public static final AttractionListFragment newInstance(List<Attraction> list,String type) {
        AttractionListFragment fragment=new AttractionListFragment();
        fragment.setType(type);
        Bundle b=new Bundle();
        AttractionParcellable[]parcellableList = new AttractionParcellable[list.size()];
        for(int i=0;i<list.size();++i){
            AttractionParcellable tmp=new AttractionParcellable(list.get(i));
            parcellableList[i]=tmp;
        }
        b.putParcelableArray(AttractionListFragment.LIST, parcellableList);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void iCallBackUpdate(Attraction attraction,String type) {
        if(this.type.equals(type)) {
            attraction.setId(attractionList.size()+1);
            attractionList.add(attraction);
            adapter.notifyItemInserted(attractionList.size() - 1);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void iCallBackEdit() {
        
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Attraction> list = new ArrayList<>();
        switch (type){
            case Hotels.TABLE:
                ProviderAttraction providerAttraction2=new ProviderAttraction(getActivity(),"Hotel");
                list=providerAttraction2.get();
                break;
            case Shop.TABLE:
                ProviderAttraction providerAttraction3=new ProviderAttraction(getActivity(),"Shop");
                list=providerAttraction3.get();
                break;
            case Park.TABLE:
                ProviderAttraction providerAttraction4=new ProviderAttraction(getActivity(),"Park");
                list=providerAttraction4.get();
                break;
            case Theater.TABLE:
                ProviderAttraction providerAttraction5=new ProviderAttraction(getActivity(),"Theater");
                list=providerAttraction5.get();
                break;
        }
        AttractionAdapter attractionAdapter=new AttractionAdapter(getActivity(),list,type);
        recyclerView.setAdapter(attractionAdapter);
    }
}
