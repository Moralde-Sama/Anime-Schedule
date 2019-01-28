package epm.animeschedule.CustomClasses;

import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

import epm.animeschedule.Main;

public class CountDownTimer extends android.os.CountDownTimer {

    private long mTimeLeftInMillis;
    private TextView Timer;
    private Calendar c;
    private Calendar ca;
    private Calendar Final;
    private String Time;
    private int Hour;
    private int Minutes;
    private int Month;
    private int currentMonth;
    private int currentDay;
    private int Day;
    private double totalDays;
    private Main context;

    public CountDownTimer(long millisInFuture, long countDownInterval, TextView Timer, String Time, String Month, String Day, Main context) {
        super(millisInFuture, countDownInterval);
        mTimeLeftInMillis = millisInFuture;
        this.context = context;
        this.Timer = Timer;
        this.Time = Time;
        this.Month = Integer.parseInt(Month);
        this.Day = Integer.parseInt(Day);
        ca = Calendar.getInstance();
        c = Calendar.getInstance();
        c.set(Calendar.MONTH, (this.Month-1));

        Timer.setText("Episode "+getEpisode());
        Log.d("getRemainingDay", getRemainingDays()+"");
    }

    @Override
    public void onTick(long l) {
        /*mTimeLeftInMillis = l;
        updateCountDownText();*/
        Calendar b = Calendar.getInstance();
        Log.d("Seconds", b.get(Calendar.SECOND)+"");
        if(b.get(Calendar.SECOND) == 0){
            updateTime();
        }
    }

    @Override
    public void onFinish() {

    }
    private void updateTime() {
        Timer.setText("Episode "+getEpisode());
    }
    public int getEpisode(){
        double getTotalDays = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, c.get(Calendar.MONTH));
        for (int i = calendar.get(Calendar.MONTH); i < ca.get(Calendar.MONTH); i++) {
            getTotalDays += calendar.getActualMaximum(Calendar.DATE);
            Log.d("getMonthMax", "Date = " + calendar.getActualMaximum(Calendar.DATE));
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
            if (calendar.get(Calendar.MONTH) == ca.get(Calendar.MONTH)) {
                getTotalDays += ca.get(Calendar.DATE);
            }
        }


        int Episode = (int) Math.round(getTotalDays/7);
        return Episode;
    }
    private int getRemainingDays() {
        int dayReleased = Day;
        int currentDay = ca.get(Calendar.DATE);
        int Result = 0;
        for (int i = dayReleased; i < (currentDay+7); i+=7) {
            if(i > currentDay){
                Result = i-currentDay;
                break;
            }
        }
        return Result;
    }
}
