package anvenkat.calpoly.edu.studybuddiesv02;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by aniru on 11/22/2016.
 */

public class NotificationSenderService extends Service {
    private String workName;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle b = intent.getExtras();
        workName = b.getString("nameofwork");
        Intent i = new Intent(this, ClassListActivity.class);
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pi = PendingIntent.getActivity(this, 101, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this).setContentIntent(pi).setContentTitle("New Notification")
                .setContentText("Time for this assignment/test: "+workName).setSmallIcon(R.drawable.ic_alert).setAutoCancel(true);
        mNotificationManager.notify(101, notification.build());

        return super.onStartCommand(intent, flags, startId);
    }
}
