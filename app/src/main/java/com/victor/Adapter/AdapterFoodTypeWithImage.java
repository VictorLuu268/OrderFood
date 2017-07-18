package com.victor.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.victor.DAO.FoodTypeDAO;
import com.victor.DTO.FoodTypeDTO;
import com.victor.orderfood.R;

import java.util.List;

/**
 * Created by Victor on 10/07/2017.
 */

public class AdapterFoodTypeWithImage extends BaseAdapter {

    Context context;
    int resources;
    List<FoodTypeDTO> objects;
    ViewHolderFoodType holderFoodType;
    FoodTypeDAO foodTypeDAO;

    public AdapterFoodTypeWithImage(Context context, int resources, List<FoodTypeDTO> objects){
        this.context = context;
        this.resources = resources;
        this.objects = objects;
        foodTypeDAO = new FoodTypeDAO(context);
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

    public class ViewHolderFoodType{
        ImageView imgFoodType;
        TextView tvName;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null){
            holderFoodType = new ViewHolderFoodType();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resources,viewGroup,false);
            
            holderFoodType.imgFoodType = (ImageView) view.findViewById(R.id.img_showFood);
            holderFoodType.tvName = (TextView) view.findViewById(R.id.tv_foodName);

            view.setTag(holderFoodType);
        } else{
            holderFoodType = (ViewHolderFoodType) view.getTag();
        }

        FoodTypeDTO foodTypeDTO = objects.get(i);
        int foodTypeID = foodTypeDTO.getId();
        String image = foodTypeDAO.getListImageFoodType(foodTypeID);
        Uri uri = Uri.parse(image);
        holderFoodType.imgFoodType.setImageURI(uri);
        holderFoodType.tvName.setText(foodTypeDTO.getName());

        return view;
    }
}
