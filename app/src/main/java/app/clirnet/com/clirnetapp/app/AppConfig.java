package app.clirnet.com.clirnetapp.app;

public class AppConfig {

    // Server user login url
   public static final String LOCAL_DEMO_URL2 = "http://43.242.212.136/clirnetapplicationv2/public/doctor/webapi/syncpatientdatav2";//49.50.76.125/
      //public static final String   LOCAL_DEMO_URL = "http://dev.clirnet.com/";//49.50.76.125/
     // static final String LIVE_MAIN_URL = "http://192.168.1.53/";
     // static final String PRODUCTION_MAIN_URL = "http://104.199.232.104/clirnetapplicationv3/public/doctor/webapi/checkdoctorvalidity";

    public static final String LOCAL_DEMO_URL = "https://doctor.clirnet.com";

    public static final String URL_LOGIN = LOCAL_DEMO_URL + "/doctor/webapi/checkdoctorvalidity";


    public static final String URL_PATIENT_RECORDS = LOCAL_DEMO_URL + "/doctor/webapi/initialpatientdata";


    public static final String URL_SYCHRONISED_TOSERVER = LOCAL_DEMO_URL + "/doctor/webapi/syncpatientdatav2";


    static final String URL_DOCTORINFO = LOCAL_DEMO_URL + "/doctor/webapi/doctordetailedinformation";


    //public static String URL_CHANGE_PASSWORD="http://doctor.clirnet.com/doctor/webapi/updatepassword";

     // public static String URL_UPDATE_PASSOWORD = LOCAL_DEMO_URL + "clirnetapplicationv3/public/doctor/webapi/updatepassword";

    //static String URL_CHANGE_PASSWORD = LOCAL_DEMO_URL + "/doctor/webapi/changepassword";

    static String URL_CHANGE_PASSWORD = LOCAL_DEMO_URL + "/doctor/webapi/appchangepassword";//31-08-2017


    public static String UPLOAD_LOG_FILE = LOCAL_DEMO_URL + "/doctor/webapi/logfiles";


    static final String URL_LAST_NAME = LOCAL_DEMO_URL + "/doctor/webapi/lastnameMaster";


    static final String URL_BANNER_API = LOCAL_DEMO_URL + "/doctor/webapi/sendbannerurldata";


    // public static final String KNOWLEDGE_PAGE_URL = LOCAL_DEMO_URL + "/clirnetapplicationv3/public/doctor";


    static final String KNOWLEDGE_CALLMEMMEETME_URL = LOCAL_DEMO_URL + "/doctor/webapi/callmeeturldata";


    static final String SAMPLEREQUEST_URL = LOCAL_DEMO_URL + "/doctor/webapi/sendsamplerequestdata";


    static final String UPLOAD_BANNER_DATA = LOCAL_DEMO_URL + "/doctor/webapi/bannersstatisticsv2";

    //static final String MASTER_SESSION_DATA= "http://43.242.212.136/clirnetapplicationv2/public/doctor/webapi/askKSdata";
    static final String MASTER_SESSION_DATA = LOCAL_DEMO_URL + "/doctor/webapi/askKSdata";

    public static final String PATIENT_ANNOUNCEMENT = LOCAL_DEMO_URL + "/doctor/webapi/addannouncements";
    //public static final String PATIENT_ANNOUNCEMENT ="https://dev.clirnet.com/clirnetapplicationv2/public/doctor/webapi/addannouncements";
   // public static final String HELP_ME = "https://dev.clirnet.com/clirnetapplicationv2/public/doctor/webapi/addhelp";

    static final String HELP_ME = LOCAL_DEMO_URL +"/doctor/webapi/addhelp";

    static final String SDCARD_PATH = "sdcard/BannerImages/";
}
