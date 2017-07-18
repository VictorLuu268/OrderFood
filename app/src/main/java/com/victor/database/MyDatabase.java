package com.victor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Victor on 07/07/2017.
 */

public class MyDatabase extends SQLiteOpenHelper {

    // Table name
    public static String TB_EMPLOYEES = "Employees";
    public static String TB_FOOD = "Food";
    public static String TB_FOODTYPE = "FoodType";
    public static String TB_DINTABLE = "DiningTable";
    public static String TB_ORDER = "Orders";
    public static String TB_ORDERDETAILS = "OrderDetails";
    public static String TB_RULES = "Rules";
    // Employees
    public static String TB_EMPLOYEE_ID = "EmployeeID";
    public static String TB_EMPLOYEE_RULEID = "RuleID";
    public static String TB_EMPLOYEE_USERNAME = "Username";
    public static String TB_EMPLOYEE_PASSWORD = "Password";
    public static String TB_EMPLOYEE_GENRE = "Genre";
    public static String TB_EMPLOYEE_BIRTHDAY = "Birthday";
    public static String TB_EMPLOYEE_PHONE = "Phone";
    // Food
    public static String TB_FOOD_ID = "FoodID";
    public static String TB_FOOD_NAME = "Name";
    public static String TB_FOOD_PRICE = "Price";
    public static String TB_FOOD_TYPE = "FoodTypeID";
    public static String TB_FOOD_IMAGE = "Image";
    // Food type
    public static String TB_FOODTYPE_ID = "FoodTypeID";
    public static String TB_FOODTYPE_NAME = "Name";
    // Table
    public static String TB_TABLE_ID = "DiningTableID";
    public static String TB_TABLE_NAME = "Name";
    public static String TB_TABLE_STATUS = "Status";
    // Order
    public static String TB_ORDER_ID = "OrderID";
    public static String TB_ORDER_EMPLOYEEID = "EmployeeID";
    public static String TB_ORDER_TABLEID = "TableID";
    public static String TB_ORDER_ORDERDATE = "OrderDate";
    public static String TB_ORDER_STATUS = "Status";
    // Order details
    public static String TB_ORDERDETAILS_ID = "OrderDetailsID";
    public static String TB_ORDERDETAILS_ORDERID = "OrderID";
    public static String TB_ORDERDETAILS_FOODID = "FoodID";
    public static String TB_ORDERDETAILS_QUANTITY = "Quantity";
    // Rules
    public static String TB_RULE_ID = "RuleID";
    public static String TB_RULE_NAME = "Name";


    public MyDatabase(Context context) {
        super(context, "dbOrderFood", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbEmploy = "CREATE TABLE " + TB_EMPLOYEES + " ( " + TB_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_EMPLOYEE_RULEID + " INTEGER, " +TB_EMPLOYEE_USERNAME + " TEXT, " + TB_EMPLOYEE_PASSWORD + " TEXT, " + TB_EMPLOYEE_GENRE + " INTEGER, "
                + TB_EMPLOYEE_BIRTHDAY + " TEXT, " + TB_EMPLOYEE_PHONE + " INTEGER )";
        String tbTable = "CREATE TABLE " + TB_DINTABLE + " ( " + TB_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + TB_TABLE_NAME + " TEXT, " + TB_TABLE_STATUS + " TEXT )";
        String tbFood = "CREATE TABLE " + TB_FOOD + " ( " + TB_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_FOOD_NAME + " TEXT, " + TB_FOOD_TYPE + " INTEGER, " + TB_FOOD_PRICE + " TEXT, "
                + TB_FOOD_IMAGE + " TEXT )";
        String tbFoodType = "CREATE TABLE " + TB_FOODTYPE + " ( " + TB_FOODTYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_FOODTYPE_NAME + " TEXT )";
        String tbOrder = "CREATE TABLE " + TB_ORDER + " ( " + TB_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_ORDER_TABLEID + " INTEGER, " + TB_ORDER_EMPLOYEEID + " INTEGER, " + TB_ORDER_ORDERDATE + " TEXT, "
                + TB_ORDER_STATUS + " TEXT )";
        String tbOrderDetails = "CREATE TABLE " + TB_ORDERDETAILS + " ( " + TB_ORDERDETAILS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_ORDERDETAILS_ORDERID + " INTEGER, " + TB_ORDERDETAILS_FOODID + " INTEGER, " + TB_ORDERDETAILS_QUANTITY + " INTEGER )";
        String tbRule = "CREATE TABLE " + TB_RULES + " ( " + TB_RULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_RULE_NAME + " TEXT )";

        sqLiteDatabase.execSQL(tbEmploy);
        sqLiteDatabase.execSQL(tbTable);
        sqLiteDatabase.execSQL(tbFood);
        sqLiteDatabase.execSQL(tbFoodType);
        sqLiteDatabase.execSQL(tbOrder);
        sqLiteDatabase.execSQL(tbOrderDetails);
        sqLiteDatabase.execSQL(tbRule);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
