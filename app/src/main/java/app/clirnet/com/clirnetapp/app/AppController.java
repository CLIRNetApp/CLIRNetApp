package app.clirnet.com.clirnetapp.app;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.clirnet.com.clirnetapp.R;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;
import app.clirnet.com.clirnetapp.helper.SQLController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.utility.ConnectionDetector;

import static app.clirnet.com.clirnetapp.R.id.produced_bytxt;

public class AppController extends Application {

    /*  public static RefWatcher getRefWatcher(Context context) {
          AppController application = (AppController) context.getApplicationContext();
          return application.refWatcher;
      }*/
    private static final String TAG = AppController.class.getSimpleName();

    public static final String PREFS_NAME = "savedViewValue";


    private RequestQueue mRequestQueue;
    private Request.Priority priority = Request.Priority.HIGH;

    private static AppController mInstance;

    private HashMap<String, String> listBannerInformation;
    private String savedUserName;
    private String savedUserPassword;
    private ConnectionDetector connectionDetector;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mInstance = this;
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        // Log.d("Adding request to ", "" + req.getUrl());
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public Request.Priority getPriority() {
        return priority;
    }

    public void setPriority(Request.Priority p) {
        priority = p;
    }

    public void appendLog(String text) {

        File logFile = new File("sdcard/PatientsImages/log.txt");
        if (!logFile.exists()) {
            try {

                logFile.getParentFile().mkdirs(); // Will create parent directories if not exists
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    //Remove leading zero from the age filed if any
    public static String removeLeadingZeroes(String value) throws ClirNetAppException {
        String val = null;
        if (value != null && value.trim().length() > 0) {
            try {
                val = Integer.valueOf(value).toString();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClirNetAppException("Remove leading zeros");

            }
        }

        return val;
    }


    //this will gives you a age from the date
    public int getAge(int year, int monthOfYear, int dayOfMonth) {
        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(year, monthOfYear, dayOfMonth);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if (a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

    public String getDateTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.ENGLISH);
        return (sdf.format(cal.getTime()));
    }

    public String getDateTimenew() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        return (sdf.format(cal.getTime()));
    }

    public String getDateTimeddmmyyyy() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return (sdf.format(cal.getTime()));
    }

    //This will remove commas from string

    public String removeCommaOccurance(String str) {
        //String str = ",,,,,,,,,,kushal,,,,,,,,,,,,,,,,,,mayurv,narendra,dhrumil,mark, ,,,, ";
        String splitted[] = str.split(",");
        StringBuilder sb = new StringBuilder();
        String retrieveData;
        for (int i = 0; i < splitted.length; i++) {
            retrieveData = splitted[i];
            if ((retrieveData.trim()).length() > 0) {

                if (i != 0) {
                    sb.append(",");
                }
                sb.append(retrieveData);

            }
        }

        str = sb.toString();
        str = str.startsWith(",") ? str.substring(1) : str; //this will remove , from start of the filtered string after removing middle commas from string

        return str;

    }

    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    public static String addDay(Date date, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);

        return sdf.format(cal.getTime());
    }

    public static Date addDay1(Date date, int i) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }

    //method to add days to current month
    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }


    //check if the ailment string contains all the nubers or not
    public boolean findNumbersAilment(String value) {
        String result = value.replaceAll("[,]", "");
        String regex = "[0-9]+";
        return result.matches(regex);
    }


    public Integer findLength(String value) {

        return (value.length());
    }

    public String ConvertDateFormat(String date) {
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);


        String frmtedDate = "";

        //convert visit date from 2016-11-1 to 2016-11-01
        try {

            frmtedDate = myFormat.format(fromUser.parse(date));

        } catch (ParseException e) {
            e.printStackTrace();
            this.appendLog(this.getDateTime() + " " + "/ " + "App Controller" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return frmtedDate;
    }

    public boolean PhoneNumberValidation(String value) {

        boolean result = true;
        try {
            if (value != null && !value.equals("")) {
                long val = Long.parseLong((value.trim()));
                result = val >= 1000000000;
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.appendLog(this.getDateTime() + " " + "/ " + "App Controller" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        }

        return result;

    }


    //Method to validate if there is allready records in db to check
    public boolean isDuplicate(List<String> col, String value) {
        boolean isDuplicate = false;
        for (String s : col) {
            if (s.equals(value)) {
                isDuplicate = true;
                break;
            }
        }
        return isDuplicate;
    }

    //it will remove : from string if it contains 12-13-2016
    public String removeColunChar(String val) {
        String result = null;

        if (val.length() > 0) {
            result = val.replaceAll("[-+.^:,]", "");
        }
        return result;
    }

    private long getFileSize(String strfile) {
        long length = 0;

        try {

            File file = new File(strfile);
            length = file.length();
            length = length / 1024;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return length;
    }

    //get the hours between two dates
    public static int hoursAgo(String datetime) {
        Date date; // Parse into Date object
        long differenceInHours = 0;
        try {
            if (datetime != null) {
                date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH).parse(datetime);
                Date now = Calendar.getInstance().getTime(); // Get time now
                long differenceInMillis = now.getTime() - date.getTime();
                differenceInHours = (differenceInMillis) / 1000L / 60L / 60L; // Divide by millis/sec, secs/min, mins/hr
            }

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return (int) differenceInHours;
    }


    public void showAdDialog(final Context context, final String img_name) {

        String manufactured_by = null;
        String marketed_by = null;
        String clinical_trial_link = null;
        String link_to_page = null;
        String product_image_name = null;
        String product_image_url = null;
        String brand_name = null;
        String prdctgeneric_name = null;
        String banner_type = null;

        SQLController sqlController = new SQLController(context);
        try {
            sqlController.open();
            try {
                HashMap<String, String> list = sqlController.retrieveMarketed_byandMmanufactured_by(img_name);

                manufactured_by = list.get("manufactured_by");
                marketed_by = list.get("marketed_by");
                clinical_trial_link = list.get("clinical_trial_link");
                link_to_page = list.get("link_to_page");
                product_image_name = list.get("product_image_name");
                product_image_url = list.get("product_image2");
                brand_name = list.get("brand_name");
                prdctgeneric_name = list.get("generic_name");
                banner_type = list.get("banner_type");

                listBannerInformation = sqlController.getBannerInformation(img_name);

                getUsernamePasswordFromDatabase(context);

            } catch (Exception e) {
                e.printStackTrace();
                this.appendLog(this.getDateTime() + " " + "/ " + "App Controller" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.appendLog(this.getDateTime() + " " + "/ " + "App Controller" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        }

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.ad_dialog);

        dialog.setCanceledOnTouchOutside(false);


        TextView mktcmpny_name = (TextView) dialog.findViewById(R.id.mktcmpny_name);
        TextView maketed_by = (TextView) dialog.findViewById(R.id.produced_by);
        TextView txtbrand_name = (TextView) dialog.findViewById(R.id.txtbrand_name);
        TextView txtgeneric_name = (TextView) dialog.findViewById(R.id.txtgeneric_name);
        ImageView productImage = (ImageView) dialog.findViewById(R.id.productImage);

        /////////////////////////////////////
        TextView mktByText = (TextView) dialog.findViewById(R.id.mktByText);
        TextView brand_nameText = (TextView) dialog.findViewById(R.id.brand_name);
        TextView produced_byText = (TextView) dialog.findViewById(produced_bytxt);
        TextView generic_nameText = (TextView) dialog.findViewById(R.id.generic_name);

        Button btnrequest_sample = (Button) dialog.findViewById(R.id.request_sample);

        Button btnseedrugProfile = (Button) dialog.findViewById(R.id.seedrugProfile);
        Button btnseeclinical_trial = (Button) dialog.findViewById(R.id.seeclinical_trial);

        if (!product_image_name.equals("") && product_image_url != null && product_image_url.trim().length() > 0 && product_image_name.trim().length() > 0) {

            BitmapDrawable d = new BitmapDrawable(context.getResources(), "sdcard/BannerImages/" + product_image_name + ".png"); // path is ur resultant //image

            productImage.setImageDrawable(d);
        } else {
            productImage.setImageResource(R.drawable.brand);
        }
        if (banner_type.equals("product")) {
            dialog.setTitle("Product Details");
            mktByText.setVisibility(View.VISIBLE);
            brand_nameText.setVisibility(View.VISIBLE);
            produced_byText.setVisibility(View.VISIBLE);
            generic_nameText.setVisibility(View.VISIBLE);

            mktcmpny_name.setVisibility(View.VISIBLE);
            maketed_by.setVisibility(View.VISIBLE);
            txtbrand_name.setVisibility(View.VISIBLE);
            txtgeneric_name.setVisibility(View.VISIBLE);
            btnseeclinical_trial.setVisibility(View.VISIBLE);
            if (manufactured_by != null && manufactured_by.length() > 0) {

                mktcmpny_name.setText(manufactured_by);
            }

            if (marketed_by != null && marketed_by.length() > 0) {

                maketed_by.setText(marketed_by);
            }
            if (brand_name != null && brand_name.length() > 0) {

                txtbrand_name.setText(brand_name);
            }
            if (prdctgeneric_name != null && prdctgeneric_name.length() > 0) {

                txtgeneric_name.setText(prdctgeneric_name);
            }
        } else {
            dialog.setTitle("Company Details");
            btnseedrugProfile.setText("Visit Company Website");
            mktByText.setVisibility(View.GONE);
            brand_nameText.setVisibility(View.GONE);
            produced_byText.setVisibility(View.GONE);
            generic_nameText.setVisibility(View.GONE);

            mktcmpny_name.setVisibility(View.GONE);
            maketed_by.setVisibility(View.GONE);
            txtbrand_name.setVisibility(View.GONE);
            txtgeneric_name.setVisibility(View.GONE);
            btnseeclinical_trial.setVisibility(View.INVISIBLE);
            btnrequest_sample.setVisibility(View.INVISIBLE);
        }

        //  dialog.setCancelable(false);
        Button close = (Button) dialog.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button call_me = (Button) dialog.findViewById(R.id.call_me);
        if (connectionDetector == null) {
            connectionDetector = new ConnectionDetector(context);
        }
        call_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean isInternetPresent = connectionDetector.isConnectingToInternet();//chk internet
                if (isInternetPresent) {
                    creteCallMeDialog(context, "call_me");
                } else {

                    // Toast.makeText(context1, "Please Connect to Internet and try again", Toast.LENGTH_LONG).show();
                    callNoInternetDialog(context);
                }


            }
        });


        Button meet_me = (Button) dialog.findViewById(R.id.meet_me);

        meet_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInternetPresent = connectionDetector.isConnectingToInternet();//chk internet
                if (isInternetPresent) {
                    creteCallMeDialog(context, "meet_me");
                } else {

                    // Toast.makeText(context1, "Please Connect to Internet and try again", Toast.LENGTH_LONG).show();
                    callNoInternetDialog(context);
                }

            }
        });


        final String finalLink_to_page = link_to_page;

        btnseedrugProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (finalLink_to_page != null && finalLink_to_page.trim().length() > 0) {
                    try {
                        String updatedUrl = finalLink_to_page;
                        if (!updatedUrl.startsWith("http://") && !updatedUrl.startsWith("https://")) {

                            updatedUrl = "http://" + updatedUrl;
                        }
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(updatedUrl)));
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                   /* Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalLink_to_page));
                    startActivity(browserIntent);*/
                } else {
                    Toast.makeText(context, "No Link Available ", Toast.LENGTH_LONG).show();
                }
            }
        });


        final String finalClinical_trial_link = clinical_trial_link;
        btnseeclinical_trial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalClinical_trial_link != null && finalClinical_trial_link.trim().length() > 0) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalClinical_trial_link));
                    context.startActivity(browserIntent);
                } else {
                    Toast.makeText(context, "No Link Available", Toast.LENGTH_LONG).show();
                }
            }
        });

        final String brand_name2 = listBannerInformation.get("brand_name");
        btnrequest_sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                creteSampleRequestDialog(context, brand_name2);
            }
        });

        dialog.show();
    }

    private void callNoInternetDialog(final Context context) {

        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.no_inetrnet_login_dialog);

        dialog.setTitle("Please Connect to Internet");
        //  dialog.setCancelable(false);
        TextView msgTxt = (TextView) dialog.findViewById(R.id.msgTxt);
        msgTxt.setText("Please Connect to Internet and try again");

        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);
        // Click cancel to dismiss android custom dialog box
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void creteSampleRequestDialog(final Context context1, final String brand_name) {

        final Dialog dialog = new Dialog(context1);
        dialog.setContentView(R.layout.requestsample_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Request Sample Form");

        final TextView edtQuantity = (TextView) dialog.findViewById(R.id.edtQuantity);
        final TextView edtotherTxt = (TextView) dialog.findViewById(R.id.otherTxt);
        final RadioGroup radioreasonGroup = (RadioGroup) dialog.findViewById(R.id.radioreason);
        TextView textbrandname = (TextView) dialog.findViewById(R.id.textbrandname);
        textbrandname.setText(brand_name);

        if (connectionDetector == null) {
            connectionDetector = new ConnectionDetector(context1);
        }
        //  dialog.setCancelable(false);
        Button close = (Button) dialog.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button send = (Button) dialog.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strQty = edtQuantity.getText().toString();
                String strother = edtotherTxt.getText().toString();

                String generic_name = null;
                String company_id = null;
                String doc_mem_id = null;

                int index = radioreasonGroup.indexOfChild(dialog.findViewById(radioreasonGroup.getCheckedRadioButtonId()));
                String selected_id = String.valueOf(index);

                if (index == 3) {
                    selected_id = "other";
                }
                if (listBannerInformation.size() > 0) {

                    generic_name = listBannerInformation.get("generic_name");
                    company_id = listBannerInformation.get("company_id");
                    doc_mem_id = listBannerInformation.get("doc_mem_id");
                }
                boolean isInternetPresent = connectionDetector.isConnectingToInternet();//chk internet
                if (isInternetPresent) {
                    new AskforSampleAsyncTask(context1, savedUserName, savedUserPassword, brand_name, company_id, generic_name, doc_mem_id, selected_id, strQty, strother, getDateTimenew());
                } else {

                    Toast.makeText(context1, "Please Connect to Internet and try again", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void creteCallMeDialog(final Context context1, final String called_from) {

        final Dialog dialog = new Dialog(context1);
        dialog.setContentView(R.layout.callme_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Appoinment Details");
        final TextView date = (TextView) dialog.findViewById(R.id.date);
        final TextView fromtime = (TextView) dialog.findViewById(R.id.fromtime);

        final TextView totime = (TextView) dialog.findViewById(R.id.totime);

        final EditText location = (EditText) dialog.findViewById(R.id.location);
        final EditText reason = (EditText) dialog.findViewById(R.id.reason);


        try {
            SQLController sqlController = new SQLController(context1);
            sqlController.open();
            if (connectionDetector == null) {
                connectionDetector = new ConnectionDetector(context1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Button send = (Button) dialog.findViewById(R.id.send);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(context1, date);
            }
        });

        fromtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callTimePicker(context1, fromtime);

            }
        });

        totime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callTimePicker(context1, totime);//totime we are passing the text view refrence to call picker so he can set value to that view

            }
        });
        getCurrentTime(fromtime);
        getCurrentTime(totime);

        Button close = (Button) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address = location.getText().toString();
                String strreason = reason.getText().toString();
                String strDate = date.getText().toString();
                String brand_name = null, generic_name = null;
                String company_id = null, doctor_id = null, doc_mem_id = null, modified_on = null, is_deleted = null;
                String deleted_on = null, is_disabled = null, disabled_by = null, disabled_on = null;
                String request_on = getDateTimenew();//get current time stamp


                if (strDate.length() <= 0) {
                    date.setError("Please Enter Date");
                    return;
                }
                if (address.trim().length() > 0) {
                    location.setError("");

                } else {
                    location.setError("Please Enter Value");
                    return;
                }

                String from_time = fromtime.getText().toString();
                String to_time = totime.getText().toString();
                String reqest_fullfilled = null;

                String modified_by = null;
                String flag = "0";
                if (listBannerInformation.size() > 0) {

                    brand_name = listBannerInformation.get("brand_name");
                    generic_name = listBannerInformation.get("generic_name");
                    company_id = listBannerInformation.get("company_id");
                    doctor_id = listBannerInformation.get("doctor_id");
                    doc_mem_id = listBannerInformation.get("doc_mem_id");

                }
                String added_by = doctor_id;
                String added_on = request_on;
                boolean isInternetPresent = connectionDetector.isConnectingToInternet();//chk internet
                if (isInternetPresent) {
                    new CallMeMeetMeAsynTask(context1, savedUserName, savedUserPassword, brand_name, company_id, generic_name, called_from, strDate, from_time, to_time, address, strreason, doc_mem_id, getDateTimenew());

                }
                 /* dbController.addCallMeetMeData(brand_name, company_id, generic_name, called_from, strDate, from_time, to_time,
                        address, strreason, doctor_id, doc_mem_id, request_on, reqest_fullfilled, modified_on, modified_by, is_deleted, is_disabled, disabled_by, disabled_on,deleted_on,flag,added_on,added_by);*/
                //Toast.makeText(context1,"Message send  you will recieve responce soon",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

        });

        dialog.show();
    }

    private void callTimePicker(Context context1, final TextView textView) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context1, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                // textView.setText( selectedHour + ":" + selectedMinute);
                updateTime(selectedHour, selectedMinute, textView);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void getCurrentTime(TextView fromtime) {
        final Calendar c = Calendar.getInstance();
        int hour;
        int minute;
        // Current Hour
        hour = c.get(Calendar.HOUR_OF_DAY);
        // Current Minute
        minute = c.get(Calendar.MINUTE);

        // set current time into output textview
        updateTime(hour, minute, fromtime);

    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins, TextView fromtime) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = String.valueOf(hours) + ':' + minutes + " " + timeSet;

        fromtime.setText(aTime);
    }

    private void showDatePicker(Context context, final TextView date) {
        final Calendar c2 = Calendar.getInstance();
        int mYear2 = c2.get(Calendar.YEAR);

        int mMonth2 = c2.get(Calendar.MONTH);
        int mDay2 = c2.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dpd1 = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);

                    }

                }, mYear2, mMonth2, mDay2);
        Date newDate = c2.getTime();
        dpd1.getDatePicker().setMinDate(newDate.getTime());
        //  dpd1.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd1.show();
        //show age of pateint
    }

    public void saveBannerDataIntoDb(String image_url, Context context, String doctor_membership_number, String action, String sourcepage) {

        SQLiteHandler dbController = null;

        String docId = null;
        String company_id = null;
        String banner_id = null;
        String banner_folder = null;
        String banner_image = image_url;
        int banner_type_id = 0;
        String module = "patient";
        String is_deleted = "0";
        String is_disbled = "0";
        String flag = "0";
        String display_time = getDateTimenew();
        try {
            SQLController sqlController = new SQLController(context);
            sqlController.open();
            dbController = new SQLiteHandler(context);
            docId = sqlController.getDoctorId();
            banner_id = sqlController.getBannerId(banner_image);
            banner_folder = sqlController.getFolderName(banner_image);
            company_id = sqlController.getBannerCompany_id();
            banner_type_id = sqlController.getBannerTypeId(banner_image);
            //Log.e("banner_id", "  " + banner_id+""+banner_folder);
        } catch (ClirNetAppException | SQLException e) {
            e.printStackTrace();
            appendLog(getDateTime() + " " + "/ " + "App Controller" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        try {
            if (dbController != null)
                if (action.equals("display")) {

                    dbController.addBannerDisplayData(docId, doctor_membership_number, company_id, banner_id, banner_folder, banner_image, banner_type_id, module, is_deleted, is_disbled, display_time, flag, sourcepage);

                } else {

                    dbController.addBannerClickedData(docId, doctor_membership_number, company_id, banner_id, banner_folder, banner_image, banner_type_id, module, is_deleted, is_disbled, display_time, flag, sourcepage);

                }
        } catch (NullPointerException e) {
            e.printStackTrace();
            this.appendLog(this.getDateTime() + " " + "/ " + "App Controller" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        }
    }


    private void getUsernamePasswordFromDatabase(Context context) {
        SQLController sqlController1 = null;
        try {

            sqlController1 = new SQLController(context);
            sqlController1.open();


            ArrayList<LoginModel> al;
            al = sqlController1.getUserLoginRecrodsNew();
            if (al.size() != 0) {
                savedUserName = al.get(0).getUserName();
                savedUserPassword = al.get(0).getPassowrd();
            }


        } catch (Exception e) {
            e.printStackTrace();
            appendLog(getDateTime() + " " + "/ " + "AppController" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } finally {

            if (sqlController1 != null) {
                sqlController1.close();
            }
        }
    }

    public String CalculateBMI(String strWeight, String strHeight) {

        float bmi = 0;

        if (strHeight != null && strWeight != null && strHeight.trim().length() > 0 && strWeight.trim().length() > 0) {

            float weight = Float.parseFloat(strWeight);
            float height = Float.parseFloat(strHeight) / 100;


            bmi = weight / (height * height);

        }

        return String.valueOf(bmi);
    }

    public String getCharFreq(String s) {
        int i = 0;
        Pattern p = Pattern.compile("patient_id");
        Matcher m = p.matcher(s);
        while (m.find()) {
            i++;
        }
        // System.out.println(i); // Prints 2
        return String.valueOf(i);
    }

    public boolean checkifImageExists(String imagename) {
        Bitmap b = null;
        try {
            File file = getImage("/" + imagename + ".png");
            String path = null;

            if (file != null) {
                long length = file.length();
                length = length / 1024;
                if (length > 0) {
                    path = file.getAbsolutePath();
                }
            }
            if (path != null)
                b = BitmapFactory.decodeFile(path);

            //  return !(b == null || b.equals(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !(b == null || b.equals(""));
    }

    public File getImage(String imagename) {

        File mediaImage = null;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root);
            if (!myDir.exists())
                return null;

            mediaImage = new File(AppConfig.SDCARD_PATH + imagename);
        } catch (Exception e) {
            this.appendLog(this.getDateTime() + " " + "/ " + "App Controller" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

            e.printStackTrace();
        }
        return mediaImage;
    }

    public Bitmap getBitmap(String url) {
        // initilize the default HTTP client object
        try {

            if (url != null) {

                try {
                    //String path = null;
                    String imageName = getImage(url) + ".png";
                    final Bitmap bitmap = BitmapFactory.decodeFile(imageName);
                    File file = getImage(url + ".png");

                    if (file != null) {
                        long length = file.length();
                        length = length / 1024;
                        if (length > 0) {
                            // path = file.getAbsolutePath();
                            return bitmap;
                        }
                        System.out.println("File Path : " + file + ", File size : " + length + " KB");
                    }

                } catch (Exception e) {
                    // You Could provide a more explicit error message for IOException
                    this.appendLog(this.getDateTime() + " " + "/ " + "App Controller" + e + " " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                    Log.e("ImageDownloader", "Something went wrong while" +
                            " retrieving bitmap from " + url + e.toString());
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
/*Method to get/display banner image*/
    public void setUpAdd(final Context context, ArrayList<String> bannerimgNamesList, ImageView backChangingImages, final String doctor_membership_number, final String pageTitle) {
        final String url;
        try {

            if (backChangingImages != null) {
                Random r = new Random();
                if (bannerimgNamesList.size() > 0) {
                    int n = r.nextInt(bannerimgNamesList.size());

                    url = bannerimgNamesList.get(n);
                    if (checkifImageExists(url)) {

                        final BitmapDrawable d = new BitmapDrawable(context.getResources(), AppConfig.SDCARD_PATH + url + ".png"); // path is ur resultant //image
                        backChangingImages.setImageDrawable(d);
                        backChangingImages.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String action = "clicked";

                                showAdDialog(context, url);
                                saveBannerDataIntoDb(url, context, doctor_membership_number, action, pageTitle);
                            }
                        });
                        String action = "display";
                        saveBannerDataIntoDb(url, context, doctor_membership_number, action, pageTitle);
                    }else{
                        Log.e("NoImage","No Image to Display");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setSpinnerPosition(Spinner spinner, String[] some_array, String value ){
        ArrayList<String> _stateList = new ArrayList<>();
        Collections.addAll(_stateList, some_array);
        if (value != null) {
            int postion = getCategoryPos(_stateList, value);
            spinner.setSelection(postion);
        }
    }
    private int getCategoryPos(ArrayList<String> _categories, String category) {
        return _categories.indexOf(category);
    }
}





