package app.clirnet.com.clirnetapp.app;

import android.app.Application;
import android.content.Context;

import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.clirnet.com.clirnetapp.activity.NavigationActivity;
import app.clirnet.com.clirnetapp.activity.RegistrationActivity;
import app.clirnet.com.clirnetapp.helper.ClirNetAppException;

public class AppController extends Application {

	private static final String TAG = AppController.class.getSimpleName();

	public static final String PREFS_NAME = "savedViewValue";

	private RequestQueue mRequestQueue;

	private static AppController mInstance;

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
		public void appendLog(String text) {

		File logFile = new File("sdcard/PatientsImages/log.txt");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			}catch (IOException e){
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
        String val=null;
		if(value.trim() != null){
			try {
				val = new Integer(value).toString();
			}
			catch (Exception e){
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
	public String getDateTime(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
		return( sdf.format(cal.getTime()) );
	}
	//This will remove commas from string

	public String removeCommaOccurance(String str) {
		//String str = ",,,,,,,,,,kushal,,,,,,,,,,,,,,,,,,mayurv,narendra,dhrumil,mark, ,,,, ";
		String splitted[] = str.split(",");
		StringBuffer sb = new StringBuffer();
		String retrieveData = "";
		for(int i =0; i<splitted.length; i++){
			retrieveData = splitted[i];
			if((retrieveData.trim()).length()>0){

				if(i!=0){
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




}