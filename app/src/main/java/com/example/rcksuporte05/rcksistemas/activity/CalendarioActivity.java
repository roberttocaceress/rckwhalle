package com.example.rcksuporte05.rcksistemas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import com.example.rcksuporte05.rcksistemas.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Date;
import java.util.TimeZone;

public class CalendarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        showCalendar();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED, new Intent());
        finish();
    }

    private void showCalendar() {
        MaterialDatePicker materialDatePicker =
                MaterialDatePicker
                        .Builder
                        .dateRangePicker()
                        .setTheme(R.style.MaterialTheme)
                        .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                        .setTitleText("* Obrigátorio informar data inicial e final")
                        .setSelection(
                                Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds() ,
                                        MaterialDatePicker.todayInUtcMilliseconds() ))
                        .build();
        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER_RANGE");
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                Long dataIniLong = selection.first  - TimeZone.getDefault().getOffset(selection.first);
                Long dataFinLong = selection.second - TimeZone.getDefault().getOffset(selection.second);
                String dataIni = DateFormat.format("yyyy-MM-dd", new Date(dataIniLong)).toString();
                String dataFin   = DateFormat.format("yyyy-MM-dd", new Date(dataFinLong)).toString();
                setResult(RESULT_OK, new Intent()
                        .putExtra("dataInicial", dataIni)
                        .putExtra("dataFinal", dataFin));
                finish();
            }

        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Object>() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                finish();
            }

        });
        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}