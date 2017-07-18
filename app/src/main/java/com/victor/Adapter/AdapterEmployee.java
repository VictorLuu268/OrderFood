package com.victor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.DTO.EmployeeDTO;
import com.victor.orderfood.R;

import java.util.List;

/**
 * Created by Victor on 14/07/2017.
 */

public class AdapterEmployee extends BaseAdapter {

    Context context;
    int resource;
    List<EmployeeDTO> objects;
    ViewHolderEmployee holderEmployee;

    public AdapterEmployee(Context context, int resource, List<EmployeeDTO> objects){
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return objects.get(i).getId();
    }

    public class ViewHolderEmployee{
        ImageView imgEmploy;
        TextView tvName, tvPhone;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null){
            holderEmployee = new ViewHolderEmployee();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource,viewGroup,false);

            holderEmployee.imgEmploy = (ImageView) view.findViewById(R.id.img_employ);
            holderEmployee.tvName = (TextView) view.findViewById(R.id.tv_empl_name);
            holderEmployee.tvPhone = (TextView) view.findViewById(R.id.tv_empl_phone);

            view.setTag(holderEmployee);
        } else{
            holderEmployee = (ViewHolderEmployee) view.getTag();
        }

        EmployeeDTO dto = objects.get(i);

        holderEmployee.tvName.setText(dto.getUsername());
        holderEmployee.tvPhone.setText(String.valueOf(dto.getPhone()));

        return view;
    }
}
