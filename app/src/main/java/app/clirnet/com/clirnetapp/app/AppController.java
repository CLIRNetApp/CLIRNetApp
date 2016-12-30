package app.clirnet.com.clirnetapp.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.clirnet.com.clirnetapp.helper.ClirNetAppException;

public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();

    public static final String PREFS_NAME = "savedViewValue";

    private RequestQueue mRequestQueue;
    Request.Priority priority = Request.Priority.HIGH;


    private static AppController mInstance;
    private String regex = "[0-9]+";


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
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        Log.d("Adding request to ", "" + req.getUrl());
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

    public void setPriority(Request.Priority p){
        priority = p;
    }

    public void appendLog(String text) {

        File logFile = new File("sdcard/PatientsImages/log.txt");
        if (!logFile.exists()) {
            try {
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
        if (value.trim() != null) {
            try {
                val = new Integer(value).toString();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClirNetAppException("Remove leading zeros");

            }
        }

        return val;
    }


    //this will gives you a age from the date
    public int getAge(int year, int monthOfYear, int dayOfMonth) {
        Date now = new Date();
        int nowMonths = now.getMonth() + 1;
        int nowDate = now.getDate();
        int nowYear = now.getYear() + 1900;
        int result = nowYear - year;

        if (monthOfYear > nowMonths) {
            result = 0;
        } else if (dayOfMonth == nowMonths) {
            if (dayOfMonth > nowDate) {
                result = 0;
            }

        } // this will caklculate months if year <0
        /*else {

			result=nowMonths-monthOfYear;

		}*/

        return result;
    }

    public String getDateTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        return (sdf.format(cal.getTime()));
    }
    public String getDateTimenew() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return (sdf.format(cal.getTime()));
        //return "22-12-2016 04:05:07"; //for test
    }

    //This will remove commas from string

    public String removeCommaOccurance(String str) {
        //String str = ",,,,,,,,,,kushal,,,,,,,,,,,,,,,,,,mayurv,narendra,dhrumil,mark, ,,,, ";
        String splitted[] = str.split(",");
        StringBuilder sb = new StringBuilder();
        String retrieveData = "";
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
        //System.out.println(str);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);

        String dateis = sdf.format(cal.getTime());
        return dateis;
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
        return result.matches(regex);

    }

    public boolean findEmptyString(String value) {
        boolean result = (TextUtils.isEmpty(value.trim()));
        return result;

    }
    //check the input filed has any text or not
    //return true if it contains text otherwise return false.

    //how to call this function ex.Appcontroller.hasContain(EditText);
    //if(!AppController.hasText(edittext))ret=false;
    public static boolean hasContain(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        if (text.length() == 0) {
            editText.setText("put some msg here");
            return false;
        }
        return true;
    }

    public Integer findLength(String value) {
        int result = (value.length());

        return result;
    }

    public String ConvertDateFormat(String date) {
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

        String flag = "0";
        String frmtedDate = "";

        //convert visit date from 2016-11-1 to 2016-11-01
        try {

            frmtedDate = myFormat.format(fromUser.parse(date));
            Log.e("reformattedStrqq", "" + frmtedDate);

        } catch (ParseException e) {
            e.printStackTrace();
            this.appendLog(this.getDateTime() + " " + "/ " + "Add Patient" + e);
        }
        return frmtedDate;
    }

    public boolean emptyDataValidation(String value) {
        if (value.trim().equals("") || value.equals(null)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean PhoneNumberValidation(String value) {
        boolean result = true;
        try {
            long val = Integer.parseInt(value.trim());
            if (val < 1000000000) {
                result = false;
            } else {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // to do remove it and write update while inserting
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

    private long getFileSize(String strfile){
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
    public static int hoursAgo(String datetime) {
        Date date = null; // Parse into Date object
        long differenceInHours = 0;
        try {
            if(datetime != null){
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


}

