package com.victor.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.victor.Adapter.AdapterEmployee;
import com.victor.DAO.EmployeeDAO;
import com.victor.DTO.EmployeeDTO;
import com.victor.orderfood.LogInActivity;
import com.victor.orderfood.MainActivity;
import com.victor.orderfood.R;
import com.victor.orderfood.SignInActivity;

import java.util.List;

/**
 * Created by Victor on 14/07/2017.
 */

public class EmployeeFragment extends Fragment {

    ListView lvEmployee;
    List<EmployeeDTO> listEmployee;
    EmployeeDAO employeeDAO;
    AdapterEmployee adapter;
    int idRule = 0;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_employee,container,false);

        lvEmployee = (ListView) view.findViewById(R.id.lv_employee);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.employees);
        employeeDAO = new EmployeeDAO(getActivity());

        setUpListView();

        sharedPreferences = getActivity().getSharedPreferences("save_rule", Context.MODE_PRIVATE);
        idRule = sharedPreferences.getInt("id_rule",0);
        if(idRule == 1){
            // admin
            setHasOptionsMenu(true);
            registerForContextMenu(lvEmployee);
        }

        return view;
    }

    private void setUpListView() {
        listEmployee = employeeDAO.getAllList();
        adapter = new AdapterEmployee(getActivity(),R.layout.custom_layout_employee,listEmployee);
        lvEmployee.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(1,R.id.itemInsertEmployee,1,R.string.insert_employee);
        item.setIcon(R.drawable.nhanvien);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itemInsertEmployee:
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
                break;
        }
        return true;
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
        int idEmploy = listEmployee.get(position).getId();

        switch (id){
            case R.id.item_change:
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                intent.putExtra("T_idEmploy",idEmploy);
                startActivity(intent);
                break;
            case R.id.item_delete:
                boolean rs = employeeDAO.delete(idEmploy);
                if(rs){
                    setUpListView();
                    Toast.makeText(getActivity(), getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setUpListView();
    }
}
