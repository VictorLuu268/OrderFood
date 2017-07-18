package com.victor.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.victor.DAO.FoodTypeDAO;
import com.victor.DTO.FoodTypeDTO;

/**
 * Created by Victor on 09/07/2017.
 */

public class InsertFoodTypeActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etName;
    Button btInsert;
    FoodTypeDAO foodTypeDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_insert_foodtype);

        addControls();
    }

    private void addControls() {
        etName = (EditText) findViewById(R.id.et_insert_foodtypeName);
        btInsert = (Button) findViewById(R.id.bt_insertFoodType);

        btInsert.setOnClickListener(this);
        foodTypeDAO = new FoodTypeDAO(this);
    }

    @Override
    public void onClick(View view) {
        String name = etName.getText().toString();
        if(name != null || name.equals("")){
            boolean rs = foodTypeDAO.insert(name);
            Intent intent = new Intent();
            intent.putExtra("Result_insertFoodType",rs);
            setResult(Activity.RESULT_OK,intent);
            finish();
        } else{
            Toast.makeText(this, R.string.err_input_data, Toast.LENGTH_SHORT).show();
        }
    }
}
