package com.victor.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.victor.DTO.FoodTypeDTO;
import com.victor.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 09/07/2017.
 */

public class FoodTypeDAO {

    SQLiteDatabase database;

    public FoodTypeDAO(Context context){
        MyDatabase myDatabase = new MyDatabase(context);
        database = myDatabase.open();
    }

    public List<FoodTypeDTO> getAllList(){
        List<FoodTypeDTO> list = new ArrayList<FoodTypeDTO>();
        String query = "Select * from " + MyDatabase.TB_FOODTYPE;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            FoodTypeDTO dto = new FoodTypeDTO();
            dto.setId(cursor.getInt(0));
            // other way: dto.setId(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_TABLE_ID)));
            dto.setName(cursor.getString(1));
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }

    public boolean insert(String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_FOODTYPE_NAME, name);

        long result = database.insert(MyDatabase.TB_FOODTYPE,null,contentValues);
        if (result != 0)
            return true;
        return false;
    }

    public String getListImageFoodType(int idFoodType){
        String pathImage = "";
        String query = "Select * from " + MyDatabase.TB_FOOD + " WHERE " + MyDatabase.TB_FOOD_TYPE
                + " = '" + idFoodType + "' AND " + MyDatabase.TB_FOOD_IMAGE + " != '' ORDER BY " +  MyDatabase.TB_FOOD_ID
                + " LIMIT 1 ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            pathImage = cursor.getString(cursor.getColumnIndex(MyDatabase.TB_FOOD_IMAGE));
            cursor.moveToNext();
        }
        return pathImage;
    }
}
