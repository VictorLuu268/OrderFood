package com.victor.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.DTO.FoodDTO;
import com.victor.orderfood.R;

import java.util.List;

/**
 * Created by Victor on 11/07/2017.
 */

public class AdapterListFood extends BaseAdapter {

    Context context;
    int resources;
    List<FoodDTO> objects;
    ViewHolderListFood holderListFood;

    public AdapterListFood(Context context, int resources, List<FoodDTO> objects){
        this.context = context;
        this.resources = resources;
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

    public class ViewHolderListFood{
        ImageView imgFood;
        TextView tvNameFood, tvPriceFood;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            holderListFood = new ViewHolderListFood();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resources,viewGroup,false);

            holderListFood.imgFood = (ImageView) view.findViewById(R.id.img_showListFoodName);
            holderListFood.tvNameFood = (TextView) view.findViewById(R.id.tv_listFoodName);
            holderListFood.tvPriceFood = (TextView) view.findViewById(R.id.tv_listFoodPrice);

            view.setTag(holderListFood);
        } else{
            holderListFood = (ViewHolderListFood) view.getTag();
        }

        FoodDTO dto = objects.get(i);
        String imageOfFood = dto.getImage().toString();
        if (imageOfFood.equals("")){
            holderListFood.imgFood.setImageResource(R.drawable.backgroundheader1);
        } else {
            Uri uri = Uri.parse(imageOfFood);
            holderListFood.imgFood.setImageURI(uri);
        }
        holderListFood.tvNameFood.setText(dto.getName());
        holderListFood.tvPriceFood.setText(context.getResources().getString(R.string.food_price) + " : " + dto.getPrice());

        return view;
    }
}
