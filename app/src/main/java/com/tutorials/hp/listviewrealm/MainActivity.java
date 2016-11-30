package com.tutorials.hp.listviewrealm;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Space;
import android.widget.Toast;


import com.tutorials.hp.listviewrealm.m_Realm.RealmHelper;
import com.tutorials.hp.listviewrealm.m_Realm.Spacecraft;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    ArrayList<String> spacecrafts;
    ArrayAdapter adapter;
    ListView lv;
    EditText nameeditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        lv= (ListView) findViewById(R.id.lv);

        //SETUP REALM
        RealmConfiguration config=new RealmConfiguration.Builder(this).build();
        realm=Realm.getInstance(config);

        //RETRIEVE
        RealmHelper helper=new RealmHelper(realm);
        spacecrafts=helper.retrieve();

        //BIND
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,spacecrafts);
        lv.setAdapter(adapter);

        //ITEM CLICKS
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,spacecrafts.get(position),Toast.LENGTH_SHORT).show();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   displayInputDialog();
            }
        });
    }

    //DISPLAY INPUT DIALOG
    private void displayInputDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save to Realm");
        d.setContentView(R.layout.input_dialog);

        nameeditTxt= (EditText) d.findViewById(R.id.nameEditText);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                Spacecraft s=new Spacecraft();
                s.setName(nameeditTxt.getText().toString());

                //SAVE
                RealmHelper helper=new RealmHelper(realm);
                helper.save(s);
                nameeditTxt.setText("");

                //RETRIEVE
                spacecrafts=helper.retrieve();
                adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,spacecrafts);
                lv.setAdapter(adapter);

            }
        });

        d.show();
    }




}









