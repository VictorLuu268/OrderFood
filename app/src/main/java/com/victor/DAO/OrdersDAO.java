package com.victor.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.victor.DTO.OrderDetailDTO;
import com.victor.DTO.OrdersDTO;
import com.victor.DTO.PaymentDTO;
import com.victor.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 12/07/2017.
 */

public class OrdersDAO {
    SQLiteDatabase database;

    public OrdersDAO(Context context){
        MyDatabase myDatabase = new MyDatabase(context);
        database = myDatabase.open();
    }

    public long insert(OrdersDTO dto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_ORDER_EMPLOYEEID, dto.getEmployID());
        contentValues.put(MyDatabase.TB_ORDER_TABLEID,dto.getTableID());
        contentValues.put(MyDatabase.TB_ORDER_ORDERDATE,dto.getDateOrder());
        contentValues.put(MyDatabase.TB_ORDER_STATUS,dto.getStatus());

        long idOrder = database.insert(MyDatabase.TB_ORDER,null,contentValues);
        return idOrder;
    }

    public long getIdOrderByIdTable(int idTable,String status){
        String query = "Select * from " + MyDatabase.TB_ORDER + " where " + MyDatabase.TB_ORDER_TABLEID
                + " = '" + idTable + "' and " + MyDatabase.TB_ORDER_STATUS + " = '" + status + "'";
        long idOrder = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            idOrder = cursor.getLong(cursor.getColumnIndex(MyDatabase.TB_ORDER_ID));
            cursor.moveToNext();
        }
        return idOrder;
    }

    public boolean updateStatusOrderWithTable(int idTable,String status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_ORDER_STATUS,"status");

        long rs = database.update(MyDatabase.TB_ORDER,contentValues, MyDatabase.TB_ORDER_TABLEID + " = '" + idTable
                + "'",null);
        if(rs != 0)
            return true;
        return false;
    }

    public boolean checkFoodHasExist(int idOrder, int idFood){
        String query = "Select * from " + MyDatabase.TB_ORDERDETAILS + " where " + MyDatabase.TB_ORDERDETAILS_FOODID
                + " = " + idFood + " and " + MyDatabase.TB_ORDERDETAILS_ORDERID + " = " + idOrder;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() != 0)
            return true;
        return false;
    }

    public int getQuantityFoodByOrder(int idOrder, int idFood){
        int quantity = 0;
        String query = "Select * from " + MyDatabase.TB_ORDERDETAILS + " where " + MyDatabase.TB_ORDERDETAILS_ORDERID
                + " = " + idOrder + " and " + MyDatabase.TB_ORDERDETAILS_FOODID + " = " + idFood;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            quantity = cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_ORDERDETAILS_QUANTITY));
            cursor.moveToNext();
        }
        return quantity;
    }

    public boolean updateQuantity(OrderDetailDTO dto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_ORDERDETAILS_QUANTITY, dto.getQuantity());
        long rs = database.update(MyDatabase.TB_ORDERDETAILS,contentValues ,MyDatabase.TB_ORDERDETAILS_ORDERID + " = " + dto.getIdOrder()
            + " and " + MyDatabase.TB_ORDERDETAILS_FOODID + " = " + dto.getIdFood(),null);
        if(rs != 0)
            return true;
        return false;
    }

    public boolean insertDetail(OrderDetailDTO dto){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TB_ORDERDETAILS_ORDERID,dto.getIdOrder());
        contentValues.put(MyDatabase.TB_ORDERDETAILS_FOODID,dto.getIdFood());
        contentValues.put(MyDatabase.TB_ORDERDETAILS_QUANTITY,dto.getQuantity());

        long rs = database.insert(MyDatabase.TB_ORDERDETAILS,null,contentValues);
        if (rs != 0)
            return true;
        return false;
    }

    public List<PaymentDTO> getListFoodOrderWithTable(int idOrder){
        String query = "Select * from " + MyDatabase.TB_ORDERDETAILS + " od, " + MyDatabase.TB_FOOD
                + " f where od." + MyDatabase.TB_ORDERDETAILS_FOODID + " = f." + MyDatabase.TB_FOOD_ID
                + " and od." + MyDatabase.TB_ORDERDETAILS_ORDERID + " = " + idOrder;
        List<PaymentDTO> list = new ArrayList<PaymentDTO>();
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PaymentDTO dto = new PaymentDTO();
            dto.setNameFood(cursor.getString(cursor.getColumnIndex(MyDatabase.TB_FOOD_NAME)));
            dto.setQuantity(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_ORDERDETAILS_QUANTITY)));
            dto.setPrice(cursor.getInt(cursor.getColumnIndex(MyDatabase.TB_FOOD_PRICE)));
            list.add(dto);
            cursor.moveToNext();
        }
        return list;
    }

}
