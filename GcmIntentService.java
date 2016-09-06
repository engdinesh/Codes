package gcmnotification;

/**
 * Created by TBSDINESH on 3/30/2016.
 */

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.bahamainrenter.NotificationSearch;
import com.bahamainrenter.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.Random;

/**
 * Created by TBSDINESH on 3/30/2016.
 */
public class GcmIntentService extends IntentService {

    public GcmIntentService()
    {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty() && GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

            String title = extras.getString("title");
            String msg = extras.getString("message");
            String subtitle =  extras.getString("subtitle");
            String tickertext = extras.getString("tickerText");

            sendNotification(title,subtitle,msg,tickertext);
        }
        //GcmBroadcastReceiver.completeWakefulIntent(intent);
    }


    private void sendNotification(String title , String subtitle ,String msg ,String tickertext)
    {


        Intent intent;

        intent = new Intent(this, NotificationSearch.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);




        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSubText(subtitle)
                .setTicker(tickertext)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 , notificationBuilder.build());



    }





    private void sendNotification(String msg)
    {

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, NotificationSearch.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg).setLights(Color.GREEN, 300, 300)
                .setVibrate(new long[] { 100, 250 })
                .setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(new Random().nextInt(), mBuilder.build());
    }





}