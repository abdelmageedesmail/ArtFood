package com.art4musilm.artfoodCustomer.commons;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;
import com.art4musilm.artfoodCustomer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

public class Utils {
    public static void autoScrollRecycler(final RecyclerView recyclerView, PageIndicatorView pageIndicatorView, final int dataListSize) {
        final int speedScroll = 3500;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (recyclerView != null) {
                    if (count > dataListSize + 1) {
                        count = 0;
                    }
                    if (count <= dataListSize + 1) {
                        recyclerView.smoothScrollToPosition(count);
                        pageIndicatorView.onPageSelected(count);
                        count++;
                        handler.postDelayed(this, speedScroll);
                    }
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }


    public static void animateCollapse(ImageView arrow) {
        RotateAnimation rotate =
                new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.clearAnimation();
        arrow.setAnimation(rotate);
    }


    // parse error body
    public static String parseResponse(Response response) {
        String errorMessage = "";
        try {
            JSONObject jsonObject = new JSONObject(response.errorBody().string());
            JSONObject errorObject = jsonObject.getJSONObject("error");
            errorMessage = errorObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorMessage;
    }

    // parse status
    public static String parseStatusMessage(Object ob) {
        String msg = "";
        try {
            JSONObject jsonObject = new JSONObject(ob.toString());
            msg = jsonObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }


    @BindingAdapter("imageBinding")
    public static void bindUser(ImageView view, String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty())
            return;
        Picasso.get()
                .load(imageUrl).fit()
                .placeholder(R.drawable.ic_logo)
                .into(view);
    }

    @BindingAdapter("imageAvatarBinding")
    public static void avatarBinding(ImageView view, String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty())
            return;
        Picasso.get()
                .load(imageUrl).fit()
                .placeholder(R.drawable.ic_user_icon)
                .fit()
                .into(view);
    }

    public static void share(Context context, String url) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Ads4You");
            String shareMessage = "" + url;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share)));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public static void dialPhoneNumber(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    @BindingAdapter("notNullText")
    public static void text(TextView textView, String str) {
        textView.setText(str == null || str.isEmpty() || str.trim().isEmpty() || str == "null" ? "" : str);
    }

    @BindingAdapter("notNullHtmlText")
    public static void textHtml(TextView textView, String str) {
        String txt = str == null || str.isEmpty() || str.trim().isEmpty() || str == "null" ? "" : str;
        textView.setText(Html.fromHtml(txt));
    }


    public static RequestBody createPartFromString(String str) {
        RequestBody cstr = RequestBody.create(MediaType.parse("text/plain"), str);
        return cstr;
    }


    public static String formatDate(int dayOfMonth, int monthOfYear, int year) {
        return (dayOfMonth <= 9 ? "0" + dayOfMonth : dayOfMonth) + "/" + ((monthOfYear + 1) <= 9 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + year;
    }


    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return df.format(c);
    }


    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    @BindingAdapter("bindServerDate")
    public static void bindServerDate(@NonNull TextView textView, String datetxt) {
        String dateStr = "00/00/0000";
        if (datetxt != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
            try {
                date = dateFormat.parse(datetxt);
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //If you need time just put specific format for time like 'HH:mm:ss'
                dateStr = formatter.format(date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        textView.setText(dateStr);
    }

    public static void openDatePicker(Context context, DateAction dateAction) {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateAction.onGetDate(new StringBuilder()
                        .append(dayOfMonth).append("/").append(monthOfYear + 1).append("/").append(year).append(" ").toString());
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public static void openUrl(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }

    public static void openTimePicker(Context context, TimeAction timeAction) {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeAction.onGetTime(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public interface DateAction {
        void onGetDate(String date);
    }

    public interface TimeAction {
        void onGetTime(String date);
    }

    public static void addPermissions(Activity context) {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
        };
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions(context, PERMISSIONS, PERMISSION_ALL);
        }

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
