package app.clirnet.com.clirnetapp.app;

public class AppConfig {
    // Server user login url
    static final String   LOCAL_DEMO_URL = "http://49.50.76.125/";
    static final String   LIVE_MAIN_URL = "http://192.168.1.53/";
    static final String   PRODUCTION_MAIN_URL = "http://104.199.232.104/clirnetapplicationv2/public/doctor/webapi/checkdoctorvalidity";


    //public static final String URL_LOGIN = "http://doctor.clirnet.com/doctor/webapi/checkdoctorvalidity";
      public static final String URL_LOGIN = LOCAL_DEMO_URL+"clirnetapplication/public/doctor/webapi/checkdoctorvalidity";


    // public static final String URL_PATIENT_RECORDS = "http://doctor.clirnet.com/doctor/webapi/initialpatientdata";
    public static final String URL_PATIENT_RECORDS = LOCAL_DEMO_URL+"clirnetapplication/public/doctor/webapi/initialpatientdata";

    //public static final String URL_SYCHRONISED_TOSERVER = "http://doctor.clirnet.com/doctor/webapi/syncpatientdata";
    public static final String URL_SYCHRONISED_TOSERVER = LOCAL_DEMO_URL+"clirnetapplication/public/doctor/webapi/syncpatientdata";

    // public static final String URL_DOCTORINFO = "http://doctor.clirnet.com/doctor/webapi/doctordetailedinformation";
     public static final String URL_DOCTORINFO = LOCAL_DEMO_URL+"clirnetapplication/public/doctor/webapi/doctordetailedinformation";

    //public static String URL_CHANGE_PASSWORD="http://doctor.clirnet.com/doctor/webapi/updatepassword";

    public static String UPLOAD_LOGFILE_TOSERVER = LOCAL_DEMO_URL+"clirnetapplication/public/doctor/webapi/logfiles";

    public static String URL_UPDATE_PASSOWORD = LOCAL_DEMO_URL+"clirnetapplication/public/doctor/webapi/updatepassword";

    public static String URL_CHANGE_PASSWORD = LOCAL_DEMO_URL+"clirnetapplication/public/doctor/webapi/changepassword";

     public static String UPLOAD_LOG_FILE = "http://192.168.1.53/sendFile.php";
   // public static String UPLOAD_LOG_FILE = "http://104.199.232.104/clirnetapplicationv2/public/doctor/webapi/logfiles";


    public static final String URL_LAST_NAME = LOCAL_DEMO_URL+"clirnetapplication/public/doctor/webapi/lastnameMaster";

    public static final String URL_BANNER_API = LOCAL_DEMO_URL+"clirnetapplication/public/doctor/webapi/sendbannerurldata";

}
