package com.victor.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.Adapter.AdapterPayment;
import com.victor.DAO.DinTableDAO;
import com.victor.DAO.OrdersDAO;
import com.victor.DTO.PaymentDTO;
import com.victor.Fragment.DiningTableFragment;

import java.util.List;

/**
 * Created by Victor on 13/07/2017.
 */

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener{

    GridView gvPayment;
    TextView tvSumPay;
    Button btPayment,btExit;
    AdapterPayment adapter;
    List<PaymentDTO> listPayment;
    OrdersDAO ordersDAO;
    DinTableDAO dinTableDAO;
    long sumPay = 0;
    int idOrder;
    int idTable;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payment);

        addControls();
        fragmentManager = getSupportFragmentManager();

        idTable = getIntent().getIntExtra("T_idTable",0);
        if(idTable != 0){
            setUpGridView();

            for(int i = 0; i < listPayment.size();i++){
                int price = listPayment.get(i).getPrice();
                int quantity = listPayment.get(i).getQuantity();
                sumPay += (quantity*price);
            }

            tvSumPay.setText(getResources().getString(R.string.sum_payment)+": \t\t"+sumPay);
        }

    }

    public void setUpGridView(){
        idOrder = (int) ordersDAO.getIdOrderByIdTable(idTable,"false");
        listPayment = ordersDAO.getListFoodOrderWithTable(idOrder);
        adapter = new AdapterPayment(this,R.layout.custom_layout_payment,listPayment);
        gvPayment.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void addControls() {
        gvPayment = (GridView) findViewById(R.id.gv_listPayment);
        tvSumPay = (TextView) findViewById(R.id.tv_sumPay);
        btPayment = (Button) findViewById(R.id.bt_payment);
        btExit = (Button) findViewById(R.id.bt_exitPay);

        btPayment.setOnClickListener(this);
        btExit.setOnClickListener(this);
        ordersDAO = new OrdersDAO(this);
        dinTableDAO = new DinTableDAO(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.bt_payment:
                boolean rsTable = dinTableDAO.setStatusTableById(idTable,"false");
                boolean rsOrder = ordersDAO.updateStatusOrderWithTable(idTable,"true");
                if(rsTable && rsOrder){
                    Toast.makeText(this, getResources().getString(R.string.payment_success), Toast.LENGTH_SHORT).show();
                    setUpGridView();
                    tvSumPay.setText(getResources().getString(R.string.sum_payment)+":");
                } else {
                    Toast.makeText(this, getResources().getString(R.string.payment_fail), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_exitPay:
                finish();
                break;
        }
    }


}
