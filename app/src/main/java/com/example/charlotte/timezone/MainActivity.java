package com.example.charlotte.timezone;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    Calendar localDate;
    Button dateBtn;
    Button timeZoneButton;
    TimeZone selectedTimeZone;
    TextView convertedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final String[] timezones = new String[] {"America/Cayenne", "Asia/Tokyo", "Europe/Paris"};
        localDate = Calendar.getInstance();
        timeZoneButton = findViewById(R.id.timeZoneButton);
        setContentView(R.layout.activity_main);
        SeekBar seekBar = findViewById(R.id.seekBar);
        final TextView userTime = findViewById(R.id.userTime);
        convertedTime = findViewById(R.id.convertedTime);
        seekBar.setProgress(localDate.get(Calendar.HOUR_OF_DAY));
        dateBtn = findViewById(R.id.dateButton);
        dateBtn.setText(DateFormat.getDateInstance().format(localDate.getTime()));
        userTime.setText((seekBar.getProgress() < 10 ? "0" + Integer.toString(seekBar.getProgress()) : Integer.toString(seekBar.getProgress())) + ":00");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                userTime.setText((seekBar.getProgress() < 10 ? "0" + Integer.toString(seekBar.getProgress()) : Integer.toString(seekBar.getProgress())) + ":00");
                localDate.set(Calendar.HOUR_OF_DAY, seekBar.getProgress());
                if (fromUser){
                    localDate.set(Calendar.MINUTE, 0);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, android.R.id.text1,timezones);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTimeZone = TimeZone.getTimeZone(timezones[i]);
            }
        });
    }

    public void showDatePicker(View view){
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getSupportFragmentManager(),"datePicker");
    }

    public void setLocalDate(int year, int month, int day){
        localDate.set(Calendar.YEAR, year);
        localDate.set(Calendar.MONTH, month);
        localDate.set(Calendar.DAY_OF_MONTH, day);
        dateBtn.setText(DateFormat.getDateInstance().format(localDate.getTime()));
    }

    private void convertDate(TimeZone toTimeZone){
        if (toTimeZone != null){
            Calendar toDate = Calendar.getInstance(toTimeZone);
            toDate.setTime(localDate.getTime());
            int hours = toDate.get(Calendar.HOUR_OF_DAY);
            int minutes = toDate.get(Calendar.MINUTE);
            String time = (hours < 10 ? "0" + Integer.toString(hours) : Integer.toString(hours)) + ":" + (minutes < 10 ? "0" + Integer.toString(minutes) : Integer.toString(minutes));
            convertedTime.setText(time);
            DateFormat parser = DateFormat.getDateInstance();
            parser.setTimeZone(selectedTimeZone);
            convertedTime.setText(parser.format(toDate.getTime()));
        }
    }

    public void onClickTimeZoneButton(View view){
        convertDate(selectedTimeZone);
    }

}