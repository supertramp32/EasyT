//package com.seshra.user.utils;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.seshra.user.R;
//
//public class ToastUtils {
//
//
//    /**
//     * Normal Toast Display Method
//     * @param activity : caller activity
//     * @param context  : activity context
//     * @param message  : toast message
//     */
//    public static void displayToast(Activity activity, Context context, String message) {
//        Toast toast = new Toast(context);
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View layout = inflater.inflate(R.layout.custom_toast, null);
//        toast.setView(layout);
//        TextView toastMessageTextView = (TextView) layout.findViewById(R.id.customToastText);
//        toastMessageTextView.setText(message);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();
//    }
//    /**
//     * Success Toast Display Method
//     * @param activity : caller activity
//     * @param context  : activity context
//     * @param message  : toast message
//     */
//    public static void displaySuccessToast(Activity activity, Context context, String message) {
//        Toast toast = new Toast(context);
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View layout = inflater.inflate(R.layout.custom_toast_success, null);
//        toast.setView(layout);
//        TextView toastMessageTextView = (TextView) layout.findViewById(R.id.customToastText);
//        toastMessageTextView.setText(message);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();
//    }
//    /**
//     * Failure Toast Display Method
//     * @param activity : caller activity
//     * @param context  : activity context
//     * @param message  : toast message
//     */
//    public static void displayFailureToast(Activity activity, Context context, String message) {
//        Toast toast = new Toast(context);
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View layout = inflater.inflate(R.layout.custom_toast_failure, null);
//        toast.setView(layout);
//        TextView toastMessageTextView = (TextView) layout.findViewById(R.id.customToastText);
//        toastMessageTextView.setText(message);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();
//    }
//
//
//}
