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

import com.victor.DAO.DinTableDAO;

/**
 * Created by Victor on 14/07/2017.
 */

public class UpdateDinTableActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etTableName;
    Button btUpdate;
    DinTableDAO dinTableDAO;
    int idTable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_dintable);

        addControls();

        idTable = getIntent().getIntExtra("T_idTable",0);

    }

    private void addControls() {
        etTableName = (EditText) findViewById(R.id.et_update_tableName);
        btUpdate = (Button) findViewById(R.id.bt_updateDinTable);

        dinTableDAO = new DinTableDAO(this);

        btUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = etTableName.getText().toString();
        if(name.trim().equals("") || name.trim() != null){
            boolean rs = dinTableDAO.update(idTable,name);
            Intent intent = new Intent();
            intent.putExtra("Result_updateTable",rs);
            setResult(Activity.RESULT_OK,intent);
            finish();
        } else{
            Toast.makeText(this, getResources().getString(R.string.err_input_data), Toast.LENGTH_SHORT).show();
        }
    }
}
