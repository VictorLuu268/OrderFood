package com.victor.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.victor.Adapter.AdapterDinTables;
import com.victor.DAO.DinTableDAO;
import com.victor.DTO.DinTableDTO;
import com.victor.orderfood.InsertDinTableActivity;
import com.victor.orderfood.MainActivity;
import com.victor.orderfood.R;
import com.victor.orderfood.UpdateDinTableActivity;

import java.util.List;

/**
 * Created by Victor on 08/07/2017.
 */

public class DiningTableFragment extends Fragment {

    public static int REQUEST_CODE_INSERT = 101;
    public static int REQUEST_CODE_UPDATE = 105;
    GridView gvDinTables;
    List<DinTableDTO> listDinTable;
    DinTableDAO dinTableDAO;
    AdapterDinTables adapter;
    int idRule = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_diningtables,container,false);
        gvDinTables = (GridView) view.findViewById(R.id.gv_dinTables);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.main);
        dinTableDAO = new DinTableDAO(getActivity());
        showListDinTable();

        sharedPreferences = getActivity().getSharedPreferences("save_rule", Context.MODE_PRIVATE);
        idRule = sharedPreferences.getInt("id_rule",0);
        if(idRule == 1){
            // admin
            registerForContextMenu(gvDinTables);
            setHasOptionsMenu(true);
        }

        return view;
    }

    private void showListDinTable() {
        listDinTable = dinTableDAO.getAllList();
        adapter = new AdapterDinTables(getActivity(), R.layout.custom_layout_dintables,listDinTable);
        gvDinTables.setAdapter(adapter);
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
        int idTable = listDinTable.get(position).getId();

        switch (id){
            case R.id.item_change:
                Intent intent = new Intent(getActivity(), UpdateDinTableActivity.class);
                intent.putExtra("T_idTable",idTable);
                startActivityForResult(intent,REQUEST_CODE_UPDATE);
                break;
            case R.id.item_delete:
                boolean rs = dinTableDAO.delete(idTable);
                if(rs){
                    showListDinTable();
                    Toast.makeText(getActivity(), getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(1,R.id.itemInsertDinTable,1,R.string.insert_dintable);
        item.setIcon(R.drawable.thembanan);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itemInsertDinTable:
                Intent intent = new Intent(getActivity(), InsertDinTableActivity.class);
                startActivityForResult(intent,REQUEST_CODE_INSERT);
                break;
        }
        return true;
    }

    // use startActivityForResult: after popup close, it run to onActivityResult
    // use startActivity: after popup close, it run to onResume
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_INSERT){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean rs = intent.getBooleanExtra("Result_insertTable",false);
                if(rs){
                    showListDinTable();
                    Toast.makeText(getActivity(),getResources().getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.insert_fail), Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(requestCode == REQUEST_CODE_UPDATE){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean rs = intent.getBooleanExtra("Result_updateTable",false);
                showListDinTable();
                if(rs){
                    Toast.makeText(getActivity(),getResources().getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
