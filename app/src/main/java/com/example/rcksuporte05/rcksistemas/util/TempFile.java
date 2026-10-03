package com.example.rcksuporte05.rcksistemas.util;

import android.graphics.Color;

import com.example.rcksuporte05.rcksistemas.activity.ActivityGraficoFaturamento;
import com.example.rcksuporte05.rcksistemas.model.EvolucaoFaturamento;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class TempFile {
    /*private void createbarChartGroup(List<EvolucaoFaturamento> lista) {
        barChart.setDrawBarShadow(false);  // Cor de fundo ate o topo
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false); //Cor de fundo
        //barChart.setDrawingCacheBackgroundColor(Color.parseColor("#1031595F"));
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(10);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setCenterAxisLabels(true);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#000000"));
        leftAxis.setTextSize(8);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(1);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        YAxis rigthAxis = barChart.getAxisRight();
        rigthAxis.setEnabled(false);

        //leftAxis.setSpaceTop(0f);
        leftAxis.setSpaceBottom(1.5f);
        //leftAxis.set

        //xAxis.setSpaceTop(0f);
        //xAxis.setAxisMaximum(0f);


        String[] labels = new String[ ( lista.size()  ) ];
        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        int z = lista.size();
        for (int i = 0; i < lista.size() / 2; i++) {
            z--;
            labels[i] = setMesAnoAno(  lista.get(z ).getMes() , lista.get(z -1).getMes());
            barOne.add(new BarEntry(i, Float.parseFloat(
                    lista.get(z).getTotal_liquido())));
            barTwo.add(new BarEntry(i, Float.parseFloat(
                    lista.get(z-1).getTotal_liquido())));
            z--;
        }
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return labels[(int) value];
                } catch ( ArrayIndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        BarDataSet set1 = new BarDataSet(barOne, "");
        BarDataSet set2 = null;
        if ( tipoRelatorio.equalsIgnoreCase("M")) {
            set2 = new BarDataSet(barTwo, "");
        }


        ArrayList<Integer> colors1 = new ArrayList<>();
        colors1.add(Color.parseColor("#EABF96"));
        // colors1.add(Color.parseColor("#31595F"));
        ArrayList<Integer> colors2 = new ArrayList<>();
        colors2.add(Color.parseColor("#31595F"));
        //colors2.add(Color.parseColor("#EABF96"));

        set1.setColors(colors1);
        set1.setStackLabels(labels);
        //set1.setColor(colors1);

        set1.setHighlightEnabled(false);
        set1.setVisible(true);
        //set1.setDrawValues(false);
        //ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        //dataSets.add(set1);
        if ( tipoRelatorio.equalsIgnoreCase("M")) {
            set2.setColors(colors2);
            //set2.setColor(Color.RED);
            set2.setHighlightEnabled(false);
            set2.setVisible(true);
            //dataSets.add(set2);
        }
       *//* float groupSpace = 0.2f; // Space between groups
        float barSpace = 0.01f; // Space between individual bars within a group
        float barWidth = 0.46f;
        barWidth = (1 - groupSpace) / 2 - (barSpace );*//*


        float groupSpace = 0.10f;
        float barSpace = 0.06f; // x4 DataSet
        float barWidth = 0.39f; // x4 DataSet




        BarData data;
        if ( tipoRelatorio.equalsIgnoreCase("M")) {
            data =new BarData( set1, set2);
        } else {
            data = new BarData(set1);
        }

        data.setBarWidth(barWidth);


        data.setBarWidth(barWidth);

        // restrict the x-axis range
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(6);

        //barChart.setDragEnabled(true);
        //barChart.groupBars(0, groupSpace, barSpace);
        //barChart.setVisibleXRangeMinimum(7f);
        barChart.setScaleEnabled(true);barChart.setDragEnabled(true);

        data.setValueTextSize(10f);
        data.getDataSetByIndex(0);
        data.setValueFormatter(new ActivityGraficoFaturamento.CustomValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return super.getFormattedValue( value);
            }
        });
        hideShimmer();
        //xAxis.setAxisMaximum(data.getGroupWidth(groupSpace, barSpace) * lista.size() );

        barChart.setDragEnabled(true);
        barChart.setData(data);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
        barChart.animateXY(3500, 3500);
    }*/
}
