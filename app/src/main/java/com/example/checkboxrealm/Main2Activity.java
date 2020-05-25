package com.example.checkboxrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;

public class Main2Activity extends AppCompatActivity {

    EditText edtName,edtPrice,edtDescription;
    Realm realm;
    Data data;
    Button btnDelete,btnUpdate;
    DataAdapter dataAdapter;
    List<Data> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        realm = Realm.getDefaultInstance();

        Intent getIntent = getIntent();
        int position = getIntent.getIntExtra("numPosition",0);
        data = realm.where(Data.class).equalTo("id",position).findFirst();

        edtName.setText(data.getName());
        edtPrice.setText(data.getPrice());
        edtDescription.setText(data.getDescription());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        data.deleteFromRealm();
                        Toast.makeText(Main2Activity.this, "Thanh cong",Toast.LENGTH_SHORT).show();
                    }
                });
                onBackPressed();
                }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                finish();
            }
        });

    }
    private void update(){
        realm.beginTransaction();
        data.setName(edtName.getText().toString().trim());
        data.setPrice(edtPrice.getText().toString().trim());
        data.setDescription(edtDescription.getText().toString().trim());
        realm.commitTransaction();
    }
}
