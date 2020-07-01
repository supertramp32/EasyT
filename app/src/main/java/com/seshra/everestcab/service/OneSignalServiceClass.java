package com.seshra.everestcab.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.seshra.everestcab.DriverWaitingActivity;
import com.seshra.everestcab.R;

import com.seshra.everestcab.ReceiptActivity;
import com.seshra.everestcab.SplashActivity;
import com.seshra.everestcab.TermsAndConditionActivity;
import com.seshra.everestcab.models.ModelNotificationPromoton;
import com.seshra.everestcab.models.ModelNotificationType;
import com.seshra.everestcab.models.ModelRideNotifications;
import com.seshra.everestcab.notifications.NotificationsActivity;
import com.seshra.everestcab.utils.IntentKeys;
import com.seshra.everestcab.utils.STATUS;
import com.seshra.everestcab.utils.SingletonGson;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;




public class OneSignalServiceClass extends NotificationExtenderService {

    private String TAG = "OneSignalServiceClass";
    private int MAINACTIVITY = 1, TRACKING_ACTIVITY = 2;

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
        try {
            Log.d("" + TAG, "RECEIVED_RESPONSE--> " + receivedResult.payload.additionalData);

            ModelNotificationType modelNotificationType = SingletonGson.getInstance()
                    .fromJson("" + receivedResult.payload.additionalData, ModelNotificationType.class);


            switch (modelNotificationType.getType()) {
                case 1:
                    ModelRideNotifications modelRideNotifications = SingletonGson.getInstance().
                            fromJson("" + receivedResult.payload.additionalData, ModelRideNotifications.class);
                        if (modelRideNotifications.getData().getBooking_status() == STATUS.ACCEPTED ||
                                modelRideNotifications.getData().getBooking_status() == STATUS.ARRIVED ||
                                modelRideNotifications.getData().getBooking_status() == STATUS.CHANGE_DESTINATION ||
                                modelRideNotifications.getData().getBooking_status() == STATUS.ADMIN_CANCELLED ||
                                modelRideNotifications.getData().getBooking_status() == STATUS.DRIVER_CANCELLED ||
                                modelRideNotifications.getData().getBooking_status() == STATUS.STARTED) {
                            generateNotification("" + getString(R.string.app_name), receivedResult.payload.body,
                                    new Intent(this, DriverWaitingActivity.class)
                                            .putExtra("" + IntentKeys.BOOKING_ID,
                                                    "" + modelRideNotifications.getData().getBooking_id())
                                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));

                        }  else {
                            generateNotification("" + getString(R.string.app_name), receivedResult.payload.body,
                                    new Intent(this, ReceiptActivity.class)
                                    .putExtra("" + IntentKeys.BOOKING_ID, "" +
                                            modelRideNotifications.getData().getBooking_id())
                                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }

                    break;
                case 2: // promotional notification
                    ModelNotificationPromoton modelNotificationPromoton =
                            SingletonGson.getInstance().fromJson("" + receivedResult.payload.additionalData,
                                    ModelNotificationPromoton.class);
                    generateNotification(modelNotificationPromoton.getData().getTitle(),
                            modelNotificationPromoton.getData().getMessage(),
                            new Intent(this, NotificationsActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    break;
//                case 3: // Add money
//                    ModelNotificationPromoton modelNotificationPromoton2 = SingletonGson.getInstance().fromJson("" + receivedResult.payload.additionalData, ModelNotificationPromoton.class);
//                    generateNotification(modelNotificationPromoton2.getData().getTitle(),
//                            modelNotificationPromoton2.getData().getMessage(), new Intent(this, WalletActivity.class)
//                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                    break;
                case 4: // Driver Input
                    ModelRideNotifications modelRideNotifications1 =
                            SingletonGson.getInstance().fromJson("" + receivedResult.payload.additionalData,
                                    ModelRideNotifications.class);
                    if(modelRideNotifications1.getData().getBooking_status()==10005)
                    {
                        generateNotification("" + getString(R.string.app_name), receivedResult.payload.body,
                                new Intent(this, ReceiptActivity.class)
                                .putExtra("" + IntentKeys.BOOKING_ID,
                                        "" + modelRideNotifications1.getData().getBooking_id())
                                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                    break;
//                case 5:
//                    //chat notification
//                    ModelForChatNotification modelRideNotifications2 = SingletonGson.getInstance().fromJson("" + receivedResult.payload.additionalData, ModelForChatNotification.class);
//                    generateNotification("" + getString(R.string.app_name), receivedResult.payload.body, new Intent(this, ChatActivity.class)
//                            .putExtra("" + IntentKeys.DRIVER_NAME, "" + modelRideNotifications2.getData().getUsername())
//                            .putExtra("" + IntentKeys.BOOKING_ID, "" + modelRideNotifications2.getData().getBooking_id()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                    break;
                case 6: // delete user notification
                    generateNotification("" + getString(R.string.app_name), receivedResult.payload.body,
                            new Intent(this, SplashActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    break;

                case 7 :  // Accept Terms and Conditions
                    generateNotification(""+getString(R.string.app_name),
                            receivedResult.payload.body, new Intent(this, TermsAndConditionActivity.class)
                    .putExtra(IntentKeys.TERMS_CONDITION,"accept")
                    .putExtra("from","1"));
                    break;
                case 19 : // Cashback
                    generateNotification(""+getString(R.string.app_name), receivedResult.payload.body, new Intent(this,
                            SplashActivity.class));
                    break;
                case 20 : // Cashback
                    generateNotification(""+getString(R.string.app_name), receivedResult.payload.body, new Intent(this,
                            SplashActivity.class));
                    break;
            }
        } catch (Exception e) {
            Log.d("" + TAG, "Exception caught while parsing " + e.getMessage());
        }

        return true;
    }


    private void generateNotification(String tittle, String message, Intent intent) {


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.app_logo)
                    .setLargeIcon(largeIcon)
                    .setContentTitle(tittle)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(alarmSound)
                    .setVibrate(pattern)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(0, notificationBuilder.build());
        } else {


            NotificationChannel channel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = new NotificationChannel("my_channel_01",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
            }

            String mChannel = "Message";
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(channel);
            }

//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.app_logo)
                    .setLargeIcon(largeIcon)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(message)
                    .setAutoCancel(true)
//                    .setColor(Color.parseColor("#0x008000"))
                    .setSound(alarmSound)
                    .setVibrate(pattern)
                    .setContentIntent(pendingIntent)
                    .setChannelId("my_channel_01");
            notificationManager.notify(0, notificationBuilder.build());
        }


//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
//            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);
//
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                    .setSmallIcon(R.drawable.app_logo)
//                    .setLargeIcon(largeIcon)
//                    .setContentTitle(tittle)
//                    .setContentText(message)
//                    .setAutoCancel(true)
////                    .setColor(Color.parseColor("#0x008000"))
//                    .setSound(alarmSound)
//                    .setVibrate(pattern)
//                    .setContentIntent(pendingIntent);
//            notificationManager.notify(0, notificationBuilder.build());


    }
}

