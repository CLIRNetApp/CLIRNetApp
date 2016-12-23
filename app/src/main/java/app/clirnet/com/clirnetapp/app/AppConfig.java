package app.clirnet.com.clirnetapp.app;

public class AppConfig {
	// Server user login url


	public static final String URL_LOGIN = "http://doctor.clirnet.com/doctor/webapi/checkdoctorvalidity";


	//Patient all records
	// public static final String URL_PATIENT_RECORDS = "http://doctor.clirnet.com/doctor/webapi/initialpatientdata";
	public static final String URL_PATIENT_RECORDS = "http://192.168.1.53/clirnetapplication/public/doctor/webapi/initialpatientdata";

	//public static final String URL_SYCHRONISED_TOSERVER = "http://doctor.clirnet.com/doctor/webapi/syncpatientdata";
	 public static final String URL_SYCHRONISED_TOSERVER = "http://192.168.1.53/clirnetapplication/public/doctor/webapi/syncpatientdata";
	public static final String URL_DOCTORINFO="http://doctor.clirnet.com/doctor/webapi/doctordetailedinformation";

	//public static String URL_CHANGE_PASSWORD="http://doctor.clirnet.com/doctor/webapi/updatepassword";

	public  static String UPLOAD_LOGFILE_TOSERVER = "http://192.168.1.53/clirnetapplication/public/doctor/webapi/logfiles";

	public static String URL_UPDATE_PASSOWORD= "http://192.168.1.53/clirnetapplication/public/doctor/webapi/updatepassword";

	public static String URL_CHANGE_PASSWORD= "http://192.168.1.53/clirnetapplication/public/doctor/webapi/changepassword";

	public static String UPLOAD_LOG_FILE = "http://192.168.1.98/sendFile.php";
}
