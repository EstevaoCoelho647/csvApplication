package com.project.estevao.csvproject.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.estevao.csvproject.R;
import com.project.estevao.csvproject.model.SuccessInterface;
import com.project.estevao.csvproject.model.YourEntity;
import com.project.estevao.csvproject.util.CSVControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SuccessInterface {
    private Button buttonExp;
    private ArrayList<YourEntity> yourEntities;
    private Button buttonImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CSVControl.requestPermission(MainActivity.this);
        createList();
        bindButton();

    }

    private void bindButton() {
        buttonExp = (Button) findViewById(R.id.button_exp);
        buttonExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CSVControl.toArchive(yourEntities, MainActivity.this);
            }
        });
        buttonImp = (Button) findViewById(R.id.button_imp);
        buttonImp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getFromJSON(CSVControl.fromArchive());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ArrayList<YourEntity> getFromJSON(String json) throws JSONException {
        ArrayList<YourEntity> yourEntities = new ArrayList<YourEntity>();
        JSONArray lineItems = new JSONArray(json);

        for (int i = 0; i < lineItems.length(); i++) {
            YourEntity yourEntity = new YourEntity();

            JSONObject jsonObject = lineItems.getJSONObject(i);
            yourEntity.setId(jsonObject.getLong("id"));
            yourEntity.setName(jsonObject.getString("name"));
            yourEntity.setText(jsonObject.getString("text"));

            yourEntities.add(yourEntity);
        }

        return yourEntities;
    }

    private void createList() {
        yourEntities = new ArrayList<>();

        yourEntities.add(new YourEntity(0L, "name0", "text0"));
        yourEntities.add(new YourEntity(1L, "name1", "text1"));
        yourEntities.add(new YourEntity(2L, "name2", "text2"));
        yourEntities.add(new YourEntity(3L, "name3", "text3"));
        yourEntities.add(new YourEntity(4L, "name4", "text4"));
    }

    @Override
    public void onSuccess() {
        Toast.makeText(MainActivity.this, "Fim da importação", Toast.LENGTH_LONG).show();
    }
}
