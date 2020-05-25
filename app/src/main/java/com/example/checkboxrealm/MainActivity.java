package com.example.checkboxrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ListView lvData;
    Realm realm;
    EditText edtName,edtPrice,edtDescription;
    Button btnSave,button;
    DataAdapter dataAdapter;
    MyHelper myHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvData = findViewById(R.id.lvData);
        realm = Realm.getDefaultInstance();
        button = findViewById(R.id.button);

//        realm.beginTransaction();
//        realm.deleteAll();
//        realm.commitTransaction();

        myHelper = new MyHelper(realm);
        myHelper.selectFromDB();
        dataAdapter = new DataAdapter(myHelper.dataArrayList(), MainActivity.this,this);
        lvData.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add);


                edtName = dialog.findViewById(R.id.edtName);
                edtPrice = dialog.findViewById(R.id.edtPrice);
                edtDescription = dialog.findViewById(R.id.edtDescription);
                btnSave = dialog.findViewById(R.id.btnSave);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        savaData();
                        fillData();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    private void savaData(){
     realm.executeTransactionAsync(new Realm.Transaction() {
                                       @Override
                                       public void execute(Realm realm) {
                                           Number number = realm.where(Data.class).max("id");
                                           int newKey = (number == null) ? 1 : number.intValue() + 1;

                                           Data data = realm.createObject(Data.class, newKey);
                                           data.setName(edtName.getText().toString().trim());
                                           data.setPrice(edtPrice.getText().toString().trim());
                                           data.setDescription(edtDescription.getText().toString().trim());
                                       }
                                   }, new Realm.Transaction.OnSuccess() {
                                       @Override
                                       public void onSuccess() {
                                           Toast.makeText(MainActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                                       }
                                   }, new Realm.Transaction.OnError() {
                                       @Override
                                       public void onError(Throwable error) {
                                           Toast.makeText(MainActivity.this,"Khong thanh cong",Toast.LENGTH_SHORT).show();
                                       }
                                   }
     );
    }

    private void fillData(){
        RealmChangeListener changeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                dataAdapter = new DataAdapter(myHelper.dataArrayList(), MainActivity.this,MainActivity.this);
                lvData.setAdapter(dataAdapter);
                dataAdapter.notifyDataSetChanged();
            }
        };
        realm.addChangeListener(changeListener);
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
