package com.victor.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.victor.DTO.FoodDTO;
import com.victor.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 09/07/2017.
 */

public class FoodDAO {

    SQLiteDatabase database;

    public FoodDAO(Context context){
        MyDatabase myDatabase = new MyDatabase(context);
        database = myDatabase.open();
    }

    public FoodDTO getFoodWithID(int idFood){
        FoodDTO dto = new FoodDTO();
        String query = "Select * from " + MyDatabase.TB_FOOD + " where " + MyDatabase.TB_FOOD_ID
                + " = '" + idFood + "'";
        Cursor cursor = database.rawQuery(query,null);

        if(cursor != null){
            cursor.moveToFirst();
            dto.setId(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_FOOD_ID)));
            dto.setName(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_FOOD_NAME)));
            dto.setPrice(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_FOOD_PRICE)));
            dto.setImage(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_FOOD_IMAGE)));
            dto.setId_foodType(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_FOOD_TYPE)));
            return dto;
        } else {
            return null;
        }
    }

    public List<FoodDTO> getAllList(){
        List<FoodDTO> list = new ArrayList<FoodDTO>();
        String query = "Select * from " + MyDatabase.TB_FOOD;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            FoodDTO dto = new FoodDTO();
            dto.setId(cursor.getInt(0));
            // other way: dto.setId(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_TABLE_ID)));
            dto.setName(cursor.getString(1));
            dto.setPrice(cursor.getString(2));
            dto.setId_foodType(cursor.getInt(3));
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }

    public boolean insert(FoodDTO dto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_FOOD_NAME, dto.getName());
        contentValues.put(MyDatabase.TB_FOOD_PRICE, dto.getPrice());
        contentValues.put(MyDatabase.TB_FOOD_TYPE, dto.getId_foodType());
        contentValues.put(MyDatabase.TB_FOOD_IMAGE, dto.getImage());

        long result = database.insert(MyDatabase.TB_FOOD,null,contentValues);
        if (result != 0)
            return true;
        return false;
    }

    public boolean update(FoodDTO dto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_FOOD_NAME,dto.getName());
        contentValues.put(MyDatabase.TB_FOOD_PRICE, dto.getPrice());
        contentValues.put(MyDatabase.TB_FOOD_TYPE, dto.getId_foodType());
        contentValues.put(MyDatabase.TB_FOOD_IMAGE, dto.getImage());

        long rs = database.update(MyDatabase.TB_FOOD,contentValues,MyDatabase.TB_FOOD_ID + " = " + dto.getId(),null);
        if(rs != 0)
            return true;
        return false;
    }

    public boolean delete(int idFood){
        long rs = database.delete(MyDatabase.TB_FOOD, MyDatabase.TB_FOOD_ID + " = " + idFood,null);
        if(rs != 0 )
            return true;
        return false;
    }

    public List<FoodDTO> getAllListWithFoodType(int idFoodType){
        List<FoodDTO> list = new ArrayList<FoodDTO>();
        String query = "Select * from " + MyDatabase.TB_FOOD + " WHERE " + MyDatabase.TB_FOOD_TYPE + " = '" + idFoodType + "'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            FoodDTO dto = new FoodDTO();
            dto.setId(cursor.getInt(0));
            // other way: dto.setId(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_TABLE_ID)));
            dto.setName(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_FOOD_NAME)));
            dto.setPrice(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_FOOD_PRICE)));
            dto.setId_foodType(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_FOOD_TYPE)));
            dto.setImage(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_FOOD_IMAGE))+"");
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }
}
