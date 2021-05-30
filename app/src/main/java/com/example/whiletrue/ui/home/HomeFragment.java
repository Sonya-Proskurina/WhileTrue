package com.example.whiletrue.ui.home;

import android.content.Intent;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    HorizontalBarChart chart;
    RecyclerView recyclerView;
    List<TopModel> list;
    Button whiteButton, blackButton;
    PieChart pieChart;

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

        pieChart = (PieChart) root.findViewById(R.id.chart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(R.color.dark);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.getLegend().setTextColor(Color.WHITE);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(30f, "Металл"));
        yValues.add(new PieEntry(20f, "Батарейки"));
        yValues.add(new PieEntry(40f, "Пластик"));
        yValues.add(new PieEntry(40f, "Бумага"));
        yValues.add(new PieEntry(30f, "Стекло"));


        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(10f);

        pieChart.setData(data);

        return root;
    }
}