package com.victor.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.victor.Adapter.AdapterFoodTypeWithImage;
import com.victor.DAO.FoodTypeDAO;
import com.victor.DTO.FoodDTO;
import com.victor.DTO.FoodTypeDTO;
import com.victor.orderfood.InsertMenuFoodActivity;
import com.victor.orderfood.MainActivity;
import com.victor.orderfood.R;

import java.util.List;

/**
 * Created by Victor on 09/07/2017.
 */

public class MenuFoodFragment extends Fragment {

    GridView gvMenuFood;
    List<FoodTypeDTO> listFoodType;
    FoodTypeDAO foodTypeDAO;
    FragmentManager fragmentManager;
    int idTable;
    int idRule = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menufood,container,false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.menu);
        foodTypeDAO = new FoodTypeDAO(getActivity());
        listFoodType = foodTypeDAO.getAllList();
        gvMenuFood = (GridView) view.findViewById(R.id.gv_menuFood);

        fragmentManager = getActivity().getSupportFragmentManager();
        AdapterFoodTypeWithImage adapter = new AdapterFoodTypeWithImage(getActivity(),R.layout.custom_layout_foodtype,listFoodType);
        gvMenuFood.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        sharedPreferences = getActivity().getSharedPreferences("save_rule", Context.MODE_PRIVATE);
        idRule = sharedPreferences.getInt("id_rule",0);
        if(idRule == 1){
            // admin
            setHasOptionsMenu(true);
        }

        Bundle bundleDataMenu = getArguments();
        if(bundleDataMenu != null){
            idTable = bundleDataMenu.getInt("B_data_idTable");
        }

        gvMenuFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int idType = listFoodType.get(position).getId();
                ListFoodFragment listFoodFragment = new ListFoodFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("B_idFoodType",idType);
                bundle.putInt("B_data_idTable",idTable);
                listFoodFragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content,listFoodFragment).addToBackStack("To_MenuFood");
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(1,R.id.itemInsertMenuFood,1,R.string.insert_menufood);
        item.setIcon(R.drawable.logodangnhap);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itemInsertMenuFood:
                Intent intent = new Intent(getActivity(), InsertMenuFoodActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.effect_activity_in,R.anim.effect_activity_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
