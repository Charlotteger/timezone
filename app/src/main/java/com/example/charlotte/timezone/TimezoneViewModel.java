package com.example.charlotte.timezone;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimezoneViewModel extends ViewModel {

    private String[] timezones = new String[] {"America/Cayenne", "Asia/Tokyo", "Europe/Paris"};;



    MutableLiveData<Calendar> convertedDate;
    MutableLiveData<String[]> timezoneList;

    public TimezoneViewModel() {
        localDate = new MutableLiveData<>();
        localDate.setValue(Calendar.getInstance());
        timezoneList = new MutableLiveData<>();
        timezoneList.setValue(timezones);
        convertedDate = new MutableLiveData<>();
        convertDate();
    }

    public MutableLiveData<Calendar> getLocalDate() {
        return localDate;
    }

    public MutableLiveData<Calendar> getConvertedDate() {
        return convertedDate;
    }

    public MutableLiveData<String[]> getTimezoneList() {
        return timezoneList;
    }

    public void convertDate(){
        Calendar toDate = Calendar.getInstance(TimeZone.getTimeZone(timezones[currentTimezone]));
        toDate.setTime(localDate.getValue().getTime());
        convertedDate.setValue(toDate);
    }

    public String formatHour(Calendar date){
        int hours = date.get(Calendar.HOUR_OF_DAY);
        int minutes = date.get(Calendar.MINUTE);
        return (hours < 10 ? "0" + Integer.toString(hours) : Integer.toString(hours) + ":" + (minutes < 10 ? "0" + Integer.toString(minutes) : Integer.toString(minutes) ));
    }

    public String formatDate(Calendar date){
        DateFormat parser = DateFormat.getDateInstance();
        parser.setTimeZone(date.getTimeZone());
        return parser.format(date.getTime());
    }

    public String[] getTimezones() {
        return timezones;
    }

    public int getCurrentTimezone() {
        return currentTimezone;
    }

    private int currentTimezone = 0;
    MutableLiveData<Calendar> localDate;

    public void setTimezones(String[] timezones) {
        this.timezones = timezones;
    }

    public void setCurrentTimezone(int currentTimezone) {
        this.currentTimezone = currentTimezone;
    }

    public void setLocalDate(MutableLiveData<Calendar> localDate) {
        this.localDate = localDate;
    }

    public void setConvertedDate(MutableLiveData<Calendar> convertedDate) {
        this.convertedDate = convertedDate;
    }

    public void setTimezoneList(MutableLiveData<String[]> timezoneList) {
        this.timezoneList = timezoneList;
    }
}

