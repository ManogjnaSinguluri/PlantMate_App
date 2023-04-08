package com.example.proj1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLDataException;

public class DatabaseManager {
/*    private ConnectionHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public DatabaseManager(Context ctx){
        context = ctx;
    }
    public DatabaseManager open() throws SQLDataException {
        dbHelper = new ConnectionHelper(context);
        database=dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }
    public void insert(String username,String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConnectionHelper.User_name,username);
        contentValues.put(ConnectionHelper.User_password,password);
        database.insert(ConnectionHelper.Database_table,null,contentValues)
    }
    public Cursor fetch(){
           String [] columns = new String[] {ConnectionHelper.User_id,ConnectionHelper.User_name,ConnectionHelper.User_password};
           Cursor cursor = database.query((ConnectionHelper.Database_table,columns,null,null,null,null,null));
           if(cursor!=null){
               cursor.moveToFirst();
           }
           return cursor;
    }
    public int update(long _id,String username,String password){
        ContentValues contentValues=new ContentValues();
        contentValues.put(ConnectionHelper.User_name,username);
        contentValues.put(ConnectionHelper.User_password,password);
        int ret=database.update(ConnectionHelper.Database_table,contentValues,ConnectionHelper.User_id+"="+_id,null)
        return ret;
    }

    public void delete(long _id){
        database.delete(ConnectionHelper.Database_table,ConnectionHelper.User_id+"="+_id,null);

    }*/
}
