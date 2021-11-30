package com.coen390team11.GSAAPP.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.coen390team11.GSAAPP.DisplayPastShoppingEventActivity;
import com.coen390team11.GSAAPP.R;
import com.coen390team11.GSAAPP.TinyDB;
import com.coen390team11.GSAAPP.databinding.FragmentHistoryBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private HistoryViewModel homeViewModel;
    private FragmentHistoryBinding binding;
    ListView historyListView;
    String timeStampShoppingEvent0 = "";
    String timeStampShoppingEvent1 = "";
    String timeStampShoppingEvent2 = "";
    String timeStampShoppingEvent3 = "";
    String timeStampShoppingEvent4 = "";

    GraphView graph;

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntriesArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        historyListView = binding.historyListView;
        ArrayList<String> pastShoppingEvents = new ArrayList<String>();

        TinyDB tinyDB = new TinyDB(getContext());
        /*graph = binding.graph;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, Float.parseFloat(tinyDB.getString("subtotal0"))),
                new DataPoint(1, Float.parseFloat(tinyDB.getString("subtotal1"))),
                new DataPoint(2, Float.parseFloat(tinyDB.getString("subtotal2"))),
                new DataPoint(3, Float.parseFloat(tinyDB.getString("subtotal3"))),
                new DataPoint(4, Float.parseFloat(tinyDB.getString("subtotal4")))
        });
        graph.addSeries(series);*/

        // -------

        barChart = binding.idBarChart;
        barEntriesArrayList = new ArrayList<>();

        String subtotal0 = (tinyDB.getString("subtotal0"));
        String subtotal1 = (tinyDB.getString("subtotal1"));
        String subtotal2 = (tinyDB.getString("subtotal2"));
        String subtotal3 = (tinyDB.getString("subtotal3"));
        String subtotal4 = (tinyDB.getString("subtotal4"));

        // if there are no past shopping events
        if (subtotal0.isEmpty()){
            barChart.setAlpha(0);
            barChart.clear();
            // do nothing
            // if there is one past shopping event
        } else if (((!subtotal0.isEmpty()) && subtotal1.isEmpty())){
            barChart.setAlpha(0);
            barChart.clear();
            // do nothing
            //barEntriesArrayList.add(new BarEntry(1f, Float.parseFloat(tinyDB.getString("subtotal0"))));
            //getBarChart();
        } else if ((!(subtotal1.isEmpty())) && subtotal2.isEmpty()){
            barEntriesArrayList.add(new BarEntry(1f, Float.parseFloat(subtotal0)));
            barEntriesArrayList.add(new BarEntry(2f, Float.parseFloat(subtotal1)));
            getBarChart();
        } else if ((!(subtotal2.isEmpty())) && subtotal3.isEmpty()){
            barEntriesArrayList.add(new BarEntry(1f, Float.parseFloat(subtotal0)));
            barEntriesArrayList.add(new BarEntry(2f, Float.parseFloat(subtotal1)));
            barEntriesArrayList.add(new BarEntry(3f, Float.parseFloat(subtotal2)));
            getBarChart();
        }else if ((!(subtotal3.isEmpty())) && subtotal4.isEmpty()) {
            barEntriesArrayList.add(new BarEntry(1f, Float.parseFloat(subtotal0)));
            barEntriesArrayList.add(new BarEntry(2f, Float.parseFloat(subtotal1)));
            barEntriesArrayList.add(new BarEntry(3f, Float.parseFloat(subtotal2)));
            barEntriesArrayList.add(new BarEntry(4f, Float.parseFloat(subtotal3)));
            getBarChart();
        } else if (!(subtotal4.isEmpty())){
            barEntriesArrayList.add(new BarEntry(1f, Float.parseFloat(subtotal0)));
            barEntriesArrayList.add(new BarEntry(2f, Float.parseFloat(subtotal1)));
            barEntriesArrayList.add(new BarEntry(3f, Float.parseFloat(subtotal2)));
            barEntriesArrayList.add(new BarEntry(4f, Float.parseFloat(subtotal3)));
            barEntriesArrayList.add(new BarEntry(5f, Float.parseFloat(subtotal4)));
            getBarChart();
        }


        /* here we add the past shopping events received from firebase,
        ** which are received from the complete purchase button
        ** in the checkout activity to the arraylist*/


        FirebaseFirestore.getInstance().collection("pastShoppingEventsPerUser")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    try {
                        if ((value.get("timeStamp0")).toString() != null) {
                            timeStampShoppingEvent0 = (value.get("timeStamp0")).toString();
                            if (!(timeStampShoppingEvent0.isEmpty())) {
                                pastShoppingEvents.add("Shopping Event on " + timeStampShoppingEvent0);
                            }
                        }
                        if ((value.get("timeStamp1")).toString() != null) {
                            timeStampShoppingEvent1 = (value.get("timeStamp1")).toString();
                            if (!(timeStampShoppingEvent1.isEmpty())) {
                                pastShoppingEvents.add("Shopping Event on " + timeStampShoppingEvent1);
                            }
                        }
                        if ((value.get("timeStamp2")).toString() != null) {
                            timeStampShoppingEvent2 = (value.get("timeStamp2")).toString();
                            if (!(timeStampShoppingEvent2.isEmpty())) {
                                pastShoppingEvents.add("Shopping Event on " + timeStampShoppingEvent2);
                            }
                        }
                        if ((value.get("timeStamp3")).toString() != null) {
                            timeStampShoppingEvent3 = (value.get("timeStamp3")).toString();
                            if (!(timeStampShoppingEvent3.isEmpty())) {
                                pastShoppingEvents.add("Shopping Event on " + timeStampShoppingEvent3);
                            }
                        }
                        if ((value.get("timeStamp4")).toString() != null) {
                            timeStampShoppingEvent4 = (value.get("timeStamp4")).toString();
                            if (!(timeStampShoppingEvent4.isEmpty())) {
                                pastShoppingEvents.add("Shopping Event on " + timeStampShoppingEvent4);
                            }

                        }

                        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, pastShoppingEvents);
                        historyListView.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();

                }catch (NullPointerException e){
                    e.printStackTrace();
                }




            }
        });

        /*SharedPreferences sharedPreferences = getActivity().getSharedPreferences("event_timestamps", Context.MODE_PRIVATE);
        String timeStamp0 = sharedPreferences.getString("timeStamp0","");
        String timeStamp1 = sharedPreferences.getString("timeStamp1","");
        String timeStamp2 = sharedPreferences.getString("timeStamp2","");
        String timeStamp3 = sharedPreferences.getString("timeStamp3","");
        String timeStamp4 = sharedPreferences.getString("timeStamp4","");
        pastShoppingEvents.add(timeStamp0);
        pastShoppingEvents.add(timeStamp1);
        pastShoppingEvents.add(timeStamp2);
        pastShoppingEvents.add(timeStamp3);
        pastShoppingEvents.add(timeStamp4);*/


        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent goToPastShoppingEventIntent = new Intent(getContext(), DisplayPastShoppingEventActivity.class);
                goToPastShoppingEventIntent.putExtra("position",position);
                startActivity(goToPastShoppingEventIntent);
            }
        });


        return root;
    }

    public void getBarChart(){
        barDataSet = new BarDataSet(barEntriesArrayList, "Your 5 Most Recent Shopping Totals");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.isFullyZoomedOut();
        barChart.setScaleEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setDrawLabels(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}