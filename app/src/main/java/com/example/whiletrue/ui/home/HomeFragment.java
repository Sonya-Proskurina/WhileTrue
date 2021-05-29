package com.example.whiletrue.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whiletrue.R;
import com.example.whiletrue.ui.home.adapter.AdapterTop;
import com.example.whiletrue.ui.home.model.TopModel;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    HorizontalBarChart chart;
    RecyclerView recyclerView;
    List<TopModel> list;
    Button whiteButton, blackButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        list=new ArrayList<>();
        list.add(new TopModel("Батарейки","ул. Фрунзе, 26Б, Таганрог",5,1));
        list.add(new TopModel("Стекло","ул. Ленина, 14, Таганрог",4,2));
        list.add(new TopModel("Стекло","ул. Энгельса, 5, Таганрог",4,3));

        recyclerView=root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AdapterTop adapter = new AdapterTop(list,getActivity());
        recyclerView.setAdapter(adapter);

        chart=root.findViewById(R.id.chart);
        setData(5,50);

        whiteButton=root.findViewById(R.id.white);
        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),WhiteActivity.class));
            }
        });

        blackButton=root.findViewById(R.id.black);
        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BlackActivity.class));
            }
        });
        return root;
    }

    private void setData(int count, int range){
        ArrayList<BarEntry> list=new ArrayList<>();
        float binW= 2f, spaceForBar=10f;

        for (int i = 0; i <count; i++) {
            float val=(float) Math.random()*range;
            list.add(new BarEntry(i*spaceForBar,val));
        }

        BarDataSet set;
        set=new BarDataSet(list,"Data set");

        BarData data=new BarData(set);
        data.setBarWidth(binW);

        chart.setData(data);
    }
}