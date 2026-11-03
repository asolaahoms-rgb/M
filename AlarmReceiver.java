package com.example.adhanapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "Adhan_Channel_16";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 1. إنشاء قناة الإشعارات المتوافقة مع أندرويد 13 حتى أندرويد 16
        createNotificationChannel(context);

        // 2. إعداد واجهة لفتح التطبيق عند الضغط على الإشعار
        Intent mainIntent = new Intent(context, Screen1.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE);

        // 3. بناء الإشعار الظاهر للمستخدم مع تفعيل الصوت
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm) // يمكنك تغييره لأيقونة تطبيقك لاحقاً
                .setContentTitle("حان الآن موعد الأذان")
                .setContentText("الله أكبر، الله أكبر...")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = 
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        if (notificationManager != null) {
            notificationManager.notify(101, builder.build());
        }

        // 4. تشغيل ملف الأذان الصوتي (adhan.mp3) المخزن في ملفات التطبيق
        try {
            int resId = context.getResources().getIdentifier("adhan", "raw", context.getPackageName());
            if (resId != 0) {
                MediaPlayer mediaPlayer = MediaPlayer.create(context, resId);
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "إشعارات الأذان";
            String description = "تشغيل صوت الأذان والاشعارات في مواقيت الصلاة";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
