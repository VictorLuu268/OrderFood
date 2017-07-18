package com.victor.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.victor.DTO.EmployeeDTO;
import com.victor.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 07/07/2017.
 */

public class EmployeeDAO {

    SQLiteDatabase database;

    public EmployeeDAO(Context context){
        MyDatabase myDatabase = new MyDatabase(context);
        database = myDatabase.open();
    }

    public EmployeeDTO getEmployeeWithID(int idEmploy){
        EmployeeDTO dto = new EmployeeDTO();
        String query = "Select * from " + MyDatabase.TB_EMPLOYEES + " where " + MyDatabase.TB_EMPLOYEE_ID
                + " = '" + idEmploy + "'";
        Cursor cursor = database.rawQuery(query,null);

        if(cursor != null){
            cursor.moveToFirst();
            dto.setId(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_ID)));
            dto.setUsername(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_USERNAME)));
            dto.setPassword(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_PASSWORD)));
            dto.setGenre(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_GENRE)));
            dto.setBirthday(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_BIRTHDAY)));
            dto.setPhone(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_PHONE)));
            dto.setIdRule(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_RULEID)));
            return dto;
        } else {
            return null;
        }
    }

    public int checkEmployees(String username, String password){
        String query = "Select * from " + MyDatabase.TB_EMPLOYEES + " where " + MyDatabase.TB_EMPLOYEE_USERNAME
                + " = '" + username + "' and " + MyDatabase.TB_EMPLOYEE_PASSWORD + " = '" + password + "'";
        int idEmploy = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            idEmploy = cursor.getInt(0);
            cursor.moveToNext();
        }
        return idEmploy;
    }

    public List<EmployeeDTO> getAllList(){
        List<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
        String query = "Select * from " + MyDatabase.TB_EMPLOYEES;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_ID)));
            dto.setUsername(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_USERNAME)));
            dto.setPassword(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_PASSWORD)));
            dto.setGenre(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_GENRE)));
            dto.setBirthday(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_BIRTHDAY)));
            dto.setPhone(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_PHONE)));
            dto.setIdRule(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_RULEID)));
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }

    public long insert(EmployeeDTO employeeDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_EMPLOYEE_USERNAME, employeeDTO.getUsername());
        contentValues.put(MyDatabase.TB_EMPLOYEE_PASSWORD, employeeDTO.getPassword());
        contentValues.put(MyDatabase.TB_EMPLOYEE_GENRE, employeeDTO.getGenre());
        contentValues.put(MyDatabase.TB_EMPLOYEE_BIRTHDAY, employeeDTO.getBirthday());
        contentValues.put(MyDatabase.TB_EMPLOYEE_PHONE, employeeDTO.getPhone());
        contentValues.put(MyDatabase.TB_EMPLOYEE_RULEID,employeeDTO.getIdRule());

        long result = database.insert(MyDatabase.TB_EMPLOYEES,null,contentValues);
        return result;
    }

    public boolean update(EmployeeDTO employeeDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_EMPLOYEE_USERNAME,employeeDTO.getUsername());
        contentValues.put(MyDatabase.TB_EMPLOYEE_PASSWORD,employeeDTO.getPassword());
        contentValues.put(MyDatabase.TB_EMPLOYEE_GENRE,employeeDTO.getGenre());
        contentValues.put(MyDatabase.TB_EMPLOYEE_BIRTHDAY,employeeDTO.getBirthday());
        contentValues.put(MyDatabase.TB_EMPLOYEE_PHONE,employeeDTO.getPhone());
        contentValues.put(MyDatabase.TB_EMPLOYEE_RULEID,employeeDTO.getIdRule());

        long rs = database.update(MyDatabase.TB_EMPLOYEES,contentValues,MyDatabase.TB_EMPLOYEE_ID + " = " + employeeDTO.getId(),null);
        if(rs != 0)
            return true;
        return false;
    }

    public boolean delete(int idEmploy){
        long rs = database.delete(MyDatabase.TB_EMPLOYEES,MyDatabase.TB_EMPLOYEE_ID + " = " + idEmploy,null);
        if (rs != 0)
            return true;
        return false;
    }

    public int getRule(int idEmploy){
        String query = "Select * from " + MyDatabase.TB_EMPLOYEES + " where " + MyDatabase.TB_EMPLOYEE_ID
                + " = '" + idEmploy + "'";
        int idRule = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            idRule = cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_EMPLOYEE_RULEID));
            cursor.moveToNext();
        }
        return idRule;
     }
}
