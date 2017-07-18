package com.victor.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.victor.DAO.DinTableDAO;

/**
 * Created by Victor on 08/07/2017.
 */

public class InsertDinTableActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etTableName;
    Button btInsert;
    DinTableDAO dinTableDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_insert_dintable);

        addControls();
    }

    private void addControls() {
        etTableName = (EditText) findViewById(R.id.et_insert_tableName);
        btInsert = (Button) findViewById(R.id.bt_insertDinTable);
        dinTableDAO = new DinTableDAO(this);

        btInsert.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = etTableName.getText().toString();
        if(name != null || name.equals("")){
            boolean rs = dinTableDAO.insert(name);
            Intent intent = new Intent();
            intent.putExtra("Result_insertTable",rs);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }
}
