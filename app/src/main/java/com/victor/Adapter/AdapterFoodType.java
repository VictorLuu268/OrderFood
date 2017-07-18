package com.victor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.victor.DTO.FoodTypeDTO;
import com.victor.orderfood.R;

import java.util.List;

/**
 * Created by Victor on 10/07/2017.
 */

public class AdapterFoodType extends BaseAdapter {

    Context context;
    int resource;
    List<FoodTypeDTO> objects;
    ViewHolderFoodType holderFoodType;

    public AdapterFoodType(Context context, int resource, List<FoodTypeDTO> objects){
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

    // spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            holderFoodType = new ViewHolderFoodType();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinfoodtype,parent,false);

            holderFoodType.tvNameFoodType = (TextView) view.findViewById(R.id.tv_spin_nameFoodtype);
            view.setTag(holderFoodType);
        } else{
            holderFoodType = (ViewHolderFoodType) view.getTag();
        }

        FoodTypeDTO foodTypeDTO = objects.get(position);
        holderFoodType.tvNameFoodType.setText(foodTypeDTO.getName());
        holderFoodType.tvNameFoodType.setTag(foodTypeDTO.getId());

        return view;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            holderFoodType = new ViewHolderFoodType();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinfoodtype,viewGroup,false);

            holderFoodType.tvNameFoodType = (TextView) view.findViewById(R.id.tv_spin_nameFoodtype);
            view.setTag(holderFoodType);
        } else{
            holderFoodType = (ViewHolderFoodType) view.getTag();
        }

        FoodTypeDTO foodTypeDTO = objects.get(i);
        holderFoodType.tvNameFoodType.setText(foodTypeDTO.getName());
        holderFoodType.tvNameFoodType.setTag(foodTypeDTO.getId());

        return view;
    }

    class ViewHolderFoodType{
        TextView tvNameFoodType;
    }
}
