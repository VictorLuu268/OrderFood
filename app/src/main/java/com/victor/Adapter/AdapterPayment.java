package com.victor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.victor.DTO.FoodDTO;
import com.victor.DTO.PaymentDTO;
import com.victor.orderfood.R;

import java.util.List;

/**
 * Created by Victor on 13/07/2017.
 */

public class AdapterPayment extends BaseAdapter {

    Context context;
    int resource;
    List<PaymentDTO> objects;
    ViewHolderPayment holderPayment;

    public AdapterPayment(Context context, int resource, List<PaymentDTO> objects){
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
        return 0;
    }

    public class ViewHolderPayment{
        TextView tvName,tvQuantity,tvPrice;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null){
            holderPayment = new ViewHolderPayment();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource,viewGroup,false);

            holderPayment.tvName = (TextView) view.findViewById(R.id.tv_pay_foodName);
            holderPayment.tvQuantity = (TextView) view.findViewById(R.id.tv_pay_quantity);
            holderPayment.tvPrice = (TextView) view.findViewById(R.id.tv_pay_price);

            view.setTag(holderPayment);
        } else{
            holderPayment = (ViewHolderPayment) view.getTag();
        }

        PaymentDTO paymentDTO = objects.get(i);
        holderPayment.tvName.setText(paymentDTO.getNameFood());
        holderPayment.tvQuantity.setText(String.valueOf(paymentDTO.getQuantity()));
        holderPayment.tvPrice.setText(String.valueOf(paymentDTO.getPrice()));

        return view;
    }
}
