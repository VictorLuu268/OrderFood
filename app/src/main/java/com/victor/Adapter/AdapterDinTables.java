package com.victor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.DAO.DinTableDAO;
import com.victor.DAO.OrdersDAO;
import com.victor.DTO.DinTableDTO;
import com.victor.DTO.OrdersDTO;
import com.victor.Fragment.ListFoodFragment;
import com.victor.Fragment.MenuFoodFragment;
import com.victor.orderfood.MainActivity;
import com.victor.orderfood.PaymentActivity;
import com.victor.orderfood.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Victor on 09/07/2017.
 */

public class AdapterDinTables extends BaseAdapter implements View.OnClickListener {

    Context context;
    int resource;
    DinTableDAO dinTableDAO;
    List<DinTableDTO> objects;
    ViewHolderDinTable holderDinTable;
    OrdersDAO ordersDAO;
    FragmentManager fragmentManager;

    public AdapterDinTables(Context context, int resource, List<DinTableDTO> objects){
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        dinTableDAO = new DinTableDAO(context);
        ordersDAO = new OrdersDAO(context);
        fragmentManager = ((MainActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int i) {
        return objects.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holderDinTable = new ViewHolderDinTable();
            // attach custom layout to view
            view = inflater.inflate(R.layout.custom_layout_dintables,viewGroup,false);
            holderDinTable.imgTable = (ImageView) view.findViewById(R.id.img_table);
            holderDinTable.imgOrder = (ImageView) view.findViewById(R.id.img_table_order);
            holderDinTable.imgPayment = (ImageView) view.findViewById(R.id.img_payment);
            holderDinTable.imgHideButton = (ImageView) view.findViewById(R.id.img_hideButton);
            holderDinTable.tvTableName = (TextView) view.findViewById(R.id.tv_table_name);

            view.setTag(holderDinTable);
        } else {
            holderDinTable = (ViewHolderDinTable) view.getTag();
        }

        if(objects.get(position).isChoose()){
            showButton();
        } else{
            hideButton(false);
        }
        DinTableDTO dto = objects.get(position);
        String statusTable = dinTableDAO.getStatusTableById(dto.getId());
        if(statusTable.equals("true")){
            holderDinTable.imgTable.setImageResource(R.drawable.banantrue);
        } else{
            holderDinTable.imgTable.setImageResource(R.drawable.banan);
        }
        holderDinTable.tvTableName.setText(dto.getName());
        holderDinTable.imgTable.setTag(position);

        holderDinTable.imgTable.setOnClickListener(this);
        holderDinTable.imgOrder.setOnClickListener(this);
        holderDinTable.imgPayment.setOnClickListener(this);
        holderDinTable.imgHideButton.setOnClickListener(this);

        return view;
    }

    private void showButton(){
        holderDinTable.imgOrder.setVisibility(View.VISIBLE);
        holderDinTable.imgPayment.setVisibility(View.VISIBLE);
        holderDinTable.imgHideButton.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.effect_show_button);
        holderDinTable.imgOrder.startAnimation(animation);
        holderDinTable.imgPayment.startAnimation(animation);
        holderDinTable.imgHideButton.startAnimation(animation);
    }

    private void hideButton(boolean effect){
        holderDinTable.imgOrder.setVisibility(View.INVISIBLE);
        holderDinTable.imgPayment.setVisibility(View.INVISIBLE);
        holderDinTable.imgHideButton.setVisibility(View.INVISIBLE);

        if(effect){
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.effect_hide_button);
            holderDinTable.imgOrder.startAnimation(animation);
            holderDinTable.imgPayment.startAnimation(animation);
            holderDinTable.imgHideButton.startAnimation(animation);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        // fix click show imageView on position has clicked
        holderDinTable = (ViewHolderDinTable) ((View)view.getParent()).getTag();
        int pos1 = (int) holderDinTable.imgTable.getTag();
        int idTable = objects.get(pos1).getId();

        switch (id){
            case R.id.img_table:
                int position = (int) view.getTag();
                objects.get(position).setChoose(true);
                showButton();
                break;
            case R.id.img_table_order:
                Intent intentMain = ((MainActivity)context).getIntent();
                int idEmploy = intentMain.getIntExtra("T_idEmploy",0);
                String status = dinTableDAO.getStatusTableById(idTable);
                if(status.equals("false")){
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String dateOrder = dateFormat.format(calendar.getTime());
                    OrdersDTO ordersDTO = new OrdersDTO();
                    ordersDTO.setTableID(idTable);
                    ordersDTO.setEmployID(idEmploy);
                    ordersDTO.setDateOrder(dateOrder);
                    ordersDTO.setStatus("false");
                    long rs = ordersDAO.insert(ordersDTO);
                    dinTableDAO.setStatusTableById(idTable,"true");
                    if(rs == 0){
                        Toast.makeText(context, context.getResources().getString(R.string.insert_fail), Toast.LENGTH_SHORT).show();
                    }
                }

                FragmentTransaction transactionMenu = fragmentManager.beginTransaction();
                MenuFoodFragment menuFoodFragment = new MenuFoodFragment();
                Bundle bundleData = new Bundle();
                bundleData.putInt("B_data_idTable",idTable);
                menuFoodFragment.setArguments(bundleData);
                transactionMenu.replace(R.id.content, menuFoodFragment).addToBackStack("To_DinTable");
                transactionMenu.commit();
                break;
            case R.id.img_payment:
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("T_idTable",idTable);
                context.startActivity(intent);
                break;
            case R.id.img_hideButton:
                hideButton(true);
                break;
        }
    }

    public class ViewHolderDinTable{
        ImageView imgTable, imgOrder, imgPayment, imgHideButton;
        TextView tvTableName;
    }
}
