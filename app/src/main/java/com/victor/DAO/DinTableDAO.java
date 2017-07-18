package com.victor.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.victor.DTO.DinTableDTO;
import com.victor.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 08/07/2017.
 */

public class DinTableDAO {

    SQLiteDatabase database;

    public DinTableDAO(Context context){
        MyDatabase myDatabase = new MyDatabase(context);
        database = myDatabase.open();
    }

    public List<DinTableDTO> getAllList(){
        List<DinTableDTO> list = new ArrayList<DinTableDTO>();
        String query = "Select * from " + MyDatabase.TB_DINTABLE;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            DinTableDTO dto = new DinTableDTO();
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
        contentValues.put(MyDatabase.TB_TABLE_NAME, name);
        contentValues.put(MyDatabase.TB_TABLE_STATUS,"false");

        long result = database.insert(MyDatabase.TB_DINTABLE,null,contentValues);
        if (result != 0)
            return true;
        return false;
    }

    public String getStatusTableById(int id){
        String status = "";
        String query = "Select * from " + MyDatabase.TB_DINTABLE + " WHERE " + MyDatabase.TB_TABLE_ID + " = '" + id + "'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            status = cursor.getString(cursor.getColumnIndex(MyDatabase.TB_TABLE_STATUS));
            cursor.moveToNext();
        }
        return status;
    }

    public boolean setStatusTableById(int id, String status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_TABLE_STATUS,status);
        long rs = database.update(MyDatabase.TB_DINTABLE,contentValues,MyDatabase.TB_TABLE_ID + " = '" + id + "'",null);
        if(rs != 0)
            return true;
        return false;
    }

    public boolean update(int idTable, String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_TABLE_NAME,name);
        long rs = database.update(MyDatabase.TB_DINTABLE,contentValues,MyDatabase.TB_TABLE_ID + " = '" + idTable + "'",null);
        if(rs != 0)
            return true;
        return false;
    }

    public boolean delete(int idTable){
        long rs = database.delete(MyDatabase.TB_DINTABLE, MyDatabase.TB_TABLE_ID + " = " + idTable,null);
        if(rs != 0 )
            return true;
        return false;
    }

}
