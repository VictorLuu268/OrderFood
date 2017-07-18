package com.victor.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.victor.DAO.OrdersDAO;
import com.victor.DTO.OrderDetailDTO;

/**
 * Created by Victor on 12/07/2017.
 */

public class FoodQuantityActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etQuantity;
    Button btInsert;
    int idTable, idFood;
    OrdersDAO ordersDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_insert_foodquantity);

        Intent intent = getIntent();
        idTable = intent.getIntExtra("T_idTable",0);
        idFood = intent.getIntExtra("T_idFood",0);

        addControls();

    }

    private void addControls() {
        etQuantity = (EditText) findViewById(R.id.et_insert_quantityFood);
        btInsert = (Button) findViewById(R.id.bt_insertQuantity);

        btInsert.setOnClickListener(this);

        ordersDAO = new OrdersDAO(this);
    }

    @Override
    public void onClick(View view) {
        int idOrder = (int) ordersDAO.getIdOrderByIdTable(idTable,"false");
        boolean rs = ordersDAO.checkFoodHasExist(idOrder,idFood);
        if(rs){
            // update order detail
            int quantityOld = ordersDAO.getQuantityFoodByOrder(idOrder,idFood);
            int quantityNew = Integer.parseInt(etQuantity.getText().toString());

            int sum = quantityOld + quantityNew;

            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setIdOrder(idOrder);
            orderDetailDTO.setIdFood(idFood);
            orderDetailDTO.setQuantity(sum);

            boolean result = ordersDAO.updateQuantity(orderDetailDTO);
            if (result){
                Toast.makeText(this, getResources().getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, getResources().getString(R.string.insert_fail), Toast.LENGTH_SHORT).show();
            }

        }else{
            // insert order detail
            int quantity = Integer.parseInt(etQuantity.getText().toString());

            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setIdOrder(idOrder);
            orderDetailDTO.setIdFood(idFood);
            orderDetailDTO.setQuantity(quantity);

            boolean result = ordersDAO.insertDetail(orderDetailDTO);
            if (result){
                Toast.makeText(this, getResources().getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, getResources().getString(R.string.insert_fail), Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }

}
