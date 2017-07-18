package com.victor.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.victor.Adapter.AdapterListFood;
import com.victor.DAO.FoodDAO;
import com.victor.DTO.FoodDTO;
import com.victor.orderfood.FoodQuantityActivity;
import com.victor.orderfood.InsertMenuFoodActivity;
import com.victor.orderfood.R;

import java.util.List;

/**
 * Created by Victor on 11/07/2017.
 */

public class ListFoodFragment extends Fragment {

    GridView gvListFood;
    FoodDAO foodDAO;
    List<FoodDTO> listFood;
    AdapterListFood adapter;
    int idTable;
    int idType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menufood,container,false);

        gvListFood = (GridView) view.findViewById(R.id.gv_menuFood);
        foodDAO = new FoodDAO(getActivity());

        Bundle bundle = getArguments();
        if(bundle != null){

            idType = bundle.getInt("B_idFoodType");
            idTable = bundle.getInt("B_data_idTable");

            showListFood(idType);

            registerForContextMenu(gvListFood);

            gvListFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(idTable != 0){
                        Intent intent = new Intent(getActivity(), FoodQuantityActivity.class);
                        intent.putExtra("T_idTable",idTable);
                        intent.putExtra("T_idFood",listFood.get(i).getId());
                        startActivity(intent);
                    }
                }
            });

        }

        // process key back on device
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("To_MenuFood", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        return view;
    }

    private void showListFood(int idType) {
        listFood = foodDAO.getAllListWithFoodType(idType);
        adapter = new AdapterListFood(getActivity(), R.layout.custom_layout_listfood,listFood);
        gvListFood.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edittable_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        int idFood = listFood.get(position).getId();

        switch (id){
            case R.id.item_change:
                Intent intent = new Intent(getActivity(), InsertMenuFoodActivity.class);
                intent.putExtra("T_idFood",idFood);
                startActivity(intent);
                break;
            case R.id.item_delete:
                boolean rs = foodDAO.delete(idFood);
                if(rs){
                    showListFood(idType);
                    Toast.makeText(getActivity(), getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
