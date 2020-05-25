package com.example.checkboxrealm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyHelper {
    Realm realm;
    RealmResults<Data> datas;

    public MyHelper(Realm realm) {
        this.realm = realm;
    }

    public void selectFromDB(){
        datas = realm.where(Data.class).findAll();
    }
    public ArrayList<Data> dataArrayList(){
        ArrayList<Data> dataArrayList = new ArrayList<>();
        for (Data data: datas){
            dataArrayList.add(data);
        }
        return dataArrayList;
    }
}
