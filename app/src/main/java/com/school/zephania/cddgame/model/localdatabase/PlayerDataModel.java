package com.school.zephania.cddgame.model.localdatabase;

import android.content.Context;

/**
 * Created by user0308 on 5/30/17.
 */

public class PlayerDataModel {
    private Context context;
    public interface onModelListener{//Controller中实现该接口,达到控制器获取Event之后通知model改变数据执行操作
        void onSuccess(String result);
        void onError();
    }
    private onModelListener listener;
    private LocalDatabaseController dbController;

    public PlayerDataModel(Context context,onModelListener listener){
        this.context = context;
        this.listener = listener;
        dbController = new LocalDatabaseController(context);
    }

    public void insertData(String username,int competition,int win,int credit){
        dbController.insertData(username,competition,win,credit);
        //listener.onSuccess();
        queryData();
    }

    public void deleteData(String username){
        dbController.deleteData(username);
        //listener.onSuccess();
        queryData();
    }

    public void updateData(String username,int competition,int win,int credit){
        dbController.updateData(username,competition,win,credit);
        //listener.onSuccess();
        queryData();
    }

    public void queryData(){
        String result = dbController.query();
        listener.onSuccess(result);
    }

}
