package com.example.whiletrue.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.whiletrue.R;
import com.example.whiletrue.ui.home.adapter.AdapterTop;
import com.example.whiletrue.ui.home.model.TopModel;

import java.util.ArrayList;
import java.util.List;

public class WhiteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<TopModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white);
        getSupportActionBar().hide();

        list=new ArrayList<>();
        list.add(new TopModel("Батарейки","ул. Фрунзе, 26Б, Таганрог",5,1));
        list.add(new TopModel("Стекло","ул. Ленина, 14, Таганрог",4,2));
        list.add(new TopModel("Стекло","ул. Энгельса, 5, Таганрог",4,3));

        recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AdapterTop adapter = new AdapterTop(list,this);
        recyclerView.setAdapter(adapter);
    }

    public void back(View view) {
        finish();
    }
}