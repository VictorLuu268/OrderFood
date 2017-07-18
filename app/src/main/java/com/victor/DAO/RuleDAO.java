package com.victor.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.victor.DTO.RuleDTO;
import com.victor.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 15/07/2017.
 */

public class RuleDAO {
    SQLiteDatabase database;

    public RuleDAO(Context context){
        MyDatabase myDatabase = new MyDatabase(context);
        database = myDatabase.open();
    }

    public List<RuleDTO> getAllList(){
        List<RuleDTO> list = new ArrayList<>();
        String query = "Select * from " + MyDatabase.TB_RULES;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            RuleDTO dto = new RuleDTO();
            dto.setId(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_RULE_ID)));
            dto.setName(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_RULE_NAME)));
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }

    public void insert(String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_RULE_NAME,name);
        database.insert(MyDatabase.TB_RULES,null,contentValues);
    }
}
