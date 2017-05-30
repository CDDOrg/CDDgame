package com.school.zephania.cddgame.controler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by user0308 on 5/30/17.
 */

public class LocalDatabaseController {
    private Context context;
    private SQLiteDatabase db = null;
    private LocalDatabaseHelper helper = null;

    //constructor
    public LocalDatabaseController(Context context){
        this.context = context;
        helper = new LocalDatabaseHelper(context);
        //by calling getWritableDatabase(),helper runs onCreate()
        db = helper.getWritableDatabase();
        //db = openOrCreateDatabase("Data.db", null);
    }

    /*
        table 原型: userTable
        用户名   参赛场次    胜利场次    积分
        username  competition win credit
     */

    //create a table,it should never use, because helper did create a table
    private void createTable(){
        //创建表SQL语句
        String createTableSQLstr="create table Data(" +
                "username text primary key not null," +
                "competition integer," +
                "win integer," +
                "credit integer)";
        //执行SQL语句
        db.execSQL(createTableSQLstr);
    }

    //insert data to this table
    public void insertData(String username,int competition,int win,int credit){
        //---方法一
        //实例化常量值
        //ContentValues cValue = new ContentValues();
        //添加用户名
        //cValue.put("sname","xiaoming");
        //添加密码
        //cValue.put("snumber","01005");
        //调用insert()方法插入数据
        //db.insert("stu_table",null,cValue);

        //方法二
        //插入数据SQL语句
        String sql="insert into Data(username,competition,win,credit)" +
                " values('" +
                username +
                "'," +
                competition +
                "," +
                win +
                "," +
                credit +
                ")";
        System.out.println(sql);
        //执行SQL语句
        db.execSQL(sql);
    }

    //delete data from table
    public void deleteData(String username){
        //---方法一
        //删除条件
        //String whereClause = "id=?";
        //删除条件参数
        //String[] whereArgs = {String.valueOf(2)};
        //执行删除
        //db.delete("stu_table",whereClause,whereArgs);

        //---方法二
        //删除SQL语句
        String sql = "delete from Data where username='" +
                username +
                "'";
        System.out.println(sql);
        //执行SQL语句
        db.execSQL(sql);
    }

    //update data in the table
    public void updateData(String username,int competition,int win,int credit){
        //---方法一:
        //实例化内容值
        //ContentValues values = new ContentValues();
        //在values中添加内容
        //values.put("snumber","101003");
        //修改条件
        //String whereClause = "id=?";
        //修改添加参数
        //String[] whereArgs={String.valuesOf(1)};
        //修改
        //db.update("usertable",values,whereClause,whereArgs);

        //---方法二
        //修改SQL语句
        String sql = "update Data set competition = " +
                competition +
                "," +
                "win = " +
                win +
                "," +
                "credit = " +
                credit +
                " " +
                "where username = '" +
                username +
                "'";
        System.out.println(sql);
        //执行SQL
        db.execSQL(sql);
    }

    //查询数据
    public String query() {
        //Toast.makeText(context,"calling query",Toast.LENGTH_SHORT).show();
        String result = "";
        //查询获得游标
        Cursor cursor = db.query ("Data",null,null,null,null,null,null);
        Log.d("LocalDatabaseController","how many rows "+cursor.getCount());
        //判断游标是否为空
        if(cursor.moveToFirst()) {
            //遍历游标
            for(int i=0;i<cursor.getCount();i++){
                cursor.move(i);
                //获得username
                String username = cursor.getString(0);
                //获得参赛场次
                int competition = cursor.getInt(1);
                //获得胜利场次
                int win = cursor.getInt(2);
                //获得积分
                int credit = cursor.getInt(3);

                //输出用户信息
                //System.out.println(username + " : " + competition + " : " + win + " : " + credit );
                result =  "LocalDatabaseController :" + username + " : " + competition + " : " + win + " : " + credit;
                Log.d("LocalDatabaseController",result);
            }
        }
        cursor.close();
        return result;
    }

}