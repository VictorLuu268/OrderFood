package com.victor.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.Adapter.AdapterFoodType;
import com.victor.DAO.FoodDAO;
import com.victor.DAO.FoodTypeDAO;
import com.victor.DTO.FoodDTO;
import com.victor.DTO.FoodTypeDTO;

import java.io.IOException;
import java.util.List;

/**
 * Created by Victor on 09/07/2017.
 */

public class InsertMenuFoodActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etFoodName, etFoodPrice;
    Spinner spinFoodType;
    ImageButton btAddType;
    ImageView imgFood;
    Button btInsert,btExit;
    TextView tvTitle;
    public static int REQUESTCODE_INSERT_FOODTYPE = 301;
    public static int REQUESTCODE_INSERT_IMAGEFOOD = 201;
    List<FoodTypeDTO> listFoodType;
    FoodTypeDAO foodTypeDAO;
    FoodDAO foodDAO;
    AdapterFoodType adapter;
    String urlImageFood;
    int idFood;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_insert_menufood);

        addControls();

        idFood = getIntent().getIntExtra("T_idFood",0);

        if(idFood != 0){
            // update food
            FoodDTO foodDTO = foodDAO.getFoodWithID(idFood);

            tvTitle.setText(getResources().getString(R.string.update_info));
            etFoodName.setText(foodDTO.getName());
            etFoodPrice.setText(foodDTO.getPrice());
            imgFood.setImageURI(Uri.parse(foodDTO.getImage()));
            urlImageFood = foodDTO.getImage();
            int idType = foodDTO.getId_foodType();
            for(int i = 0; i<listFoodType.size();i++){
                if(listFoodType.get(i).getId() == idType){
                    spinFoodType.setSelection(i);
                }
            }
        }

    }

    private void addControls() {
        etFoodName = (EditText) findViewById(R.id.et_insert_foodName);
        etFoodPrice = (EditText) findViewById(R.id.et_insert_foodPrice);
        spinFoodType = (Spinner) findViewById(R.id.spin_menuTypeFood);
        imgFood = (ImageView) findViewById(R.id.imgView_food);
        btAddType = (ImageButton) findViewById(R.id.bt_addTypeFood);
        btInsert = (Button) findViewById(R.id.bt_insertFood);
        btExit = (Button) findViewById(R.id.bt_exitFood);
        tvTitle = (TextView) findViewById(R.id.tv_food_title);

        btAddType.setOnClickListener(this);
        btInsert.setOnClickListener(this);
        btExit.setOnClickListener(this);
        imgFood.setOnClickListener(this);

        foodTypeDAO = new FoodTypeDAO(this);
        foodDAO = new FoodDAO(this);
        showListFoodtype();
    }

    private void showListFoodtype(){
        listFoodType = foodTypeDAO.getAllList();
        adapter = new AdapterFoodType(InsertMenuFoodActivity.this,R.layout.custom_layout_spinfoodtype,listFoodType);
        spinFoodType.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.bt_addTypeFood:
                Intent intent = new Intent(InsertMenuFoodActivity.this,InsertFoodTypeActivity.class);
                startActivityForResult(intent,REQUESTCODE_INSERT_FOODTYPE);
                break;
            case R.id.bt_insertFood:
                if(idFood != 0){
                    // update
                    processUpdate();
                } else{
                    // insert
                    processInsert();
                }
                break;
            case R.id.bt_exitFood:
                finish();
                break;
            case R.id.imgView_food:
                Intent intentImg = new Intent();
                intentImg.setType("image/*");
                intentImg.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentImg,"Chọn hình ảnh"),REQUESTCODE_INSERT_IMAGEFOOD);
                break;
        }
    }

    public void processUpdate(){
        int position = spinFoodType.getSelectedItemPosition();
        int idType = listFoodType.get(position).getId();
        String name = etFoodName.getText().toString();
        String price = etFoodPrice.getText().toString();

        if (name != null && price != null && !name.equals("") && !price.equals("")) {
            FoodDTO foodDTO = new FoodDTO();
            foodDTO.setId(idFood);
            foodDTO.setName(name);
            foodDTO.setPrice(price);
            foodDTO.setId_foodType(idType);
            foodDTO.setImage(urlImageFood);
            boolean rs = foodDAO.update(foodDTO);
            if(rs){
                Toast.makeText(this, getResources().getString(R.string.update_success), Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, getResources().getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, getResources().getString(R.string.err_input_datas), Toast.LENGTH_SHORT).show();
        }
    }

    private void processInsert() {
        int position = spinFoodType.getSelectedItemPosition();
        int idType = listFoodType.get(position).getId();
        String name = etFoodName.getText().toString();
        String price = etFoodPrice.getText().toString();

        if (name != null && price != null && !name.equals("") && !price.equals("")) {
            FoodDTO foodDTO = new FoodDTO();
            foodDTO.setName(name);
            foodDTO.setPrice(price);
            foodDTO.setId_foodType(idType);
            foodDTO.setImage(urlImageFood);
            boolean rs = foodDAO.insert(foodDTO);
            if(rs){
                Toast.makeText(this, getResources().getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, getResources().getString(R.string.insert_fail), Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, getResources().getString(R.string.err_input_datas), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE_INSERT_FOODTYPE){
            if(resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean rs = intent.getBooleanExtra("Result_insertFoodType",false);
                if (rs){
                    showListFoodtype();
                    Toast.makeText(this, getResources().getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this, getResources().getString(R.string.insert_fail), Toast.LENGTH_SHORT).show();
                }
            }
        } else if(requestCode == REQUESTCODE_INSERT_IMAGEFOOD){
            if(resultCode == Activity.RESULT_OK && data != null){
                urlImageFood = data.getData().toString();
                imgFood.setImageURI(data.getData());
                Log.d("Path image",data.getData() + "");
                // other way
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
//                    imgFood.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

}
