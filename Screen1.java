package com.example.adhanapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Calendar;

public class Screen1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.class.onCreate(savedInstanceState);
        
        // إنشاء زر برمي لتفعيل الأذان كمثال داخل الواجهة
        Button startButton = new Button(this);
        startButton.setText("تفعيل منبه أوقات الصلاة");
        setContentView(startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAdhanAlarm();
            }
        });
    }

    private void setAdhanAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // إعداد وقت تجريبي (مثلاً: تفعيل التنبيه بعد 5 ثوانٍ لاختبار الأذان الحديث)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);

        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 
                        calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, 
                        calendar.getTimeInMillis(), pendingIntent);
            }
            Toast.makeText(this, "تم تفعيل منبه الأذان بنجاح!", Toast.LENGTH_LONG).show();
        }
    }
}
