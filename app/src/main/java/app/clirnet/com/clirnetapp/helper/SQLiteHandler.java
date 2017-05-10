
package app.clirnet.com.clirnetapp.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SQLiteHandler extends SQLiteOpenHelper {

    //private static final String TAG = "SQLHandler";
    private final Context myContext;
    @SuppressLint("StaticFieldLeak")
    static SQLiteHandler sInstance;

    private SQLiteDatabase db;
    // All Static variables
    // Database Version
    static final int DATABASE_VERSION = 3;

    // Database Name
    public static final String DATABASE_NAME = "clirnetApp.db";


    // Login table name
    static final String TABLE_USER = "user";
    private static final String TABLE_PATIENT = "patient";
    private static final String TABLE_PATIENT_HISTORY = "patient_history";
    private static final String MASTER_AILMENT = "table_MasterAilment";
    private static final String LAST_NAMETBL = "table_LastNames";
    private static final String TABLE_PATIENTPERSONALINFO_HIOTORY = "table_persoplushistorydata";

    private static final String TABLE_DOCTORINFO = "doctor_perInfo";

    private static final String TABLE_BANNER_CLICKED = "banner_clicked";
    private static final String TABLE_BANNER_DISPLAY = "banner_display";
    private static final String TABLE_CALLMEET_REASON = "sample_call_meet_reasons";
    static final String TABLE_SYNC_STATUS = "sync_status";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String ADDED_TIME = "added_time";
    private static final String MODIFIED_TIME = "modified_time";
    private static final String SYCHRONIZED = "flag";

    static final String KEY_NAME = "name";
    static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";

    //Patient personal Info Table
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String DOCTOR_ID = "doctor_id";
    private static final String DOCTOR_MEMBERSHIP_ID = "doc_mem_id";
    private static final String PATIENT_INFO_TYPE_FORM = "patient_info_type";
    private static final String PATIENT_ADDRESS = "patient_address";
    private static final String ASSOCIATE_ADDRESS = "associate_address";
    private static final String PATIENT_CIT_CITY_TOWN = "patient_city_town";
    private static final String PATIENT_STATE = "patient_state";
    private static final String ASSOCIATE_STATE = "associate_state";
    private static final String PIN_CODE = "pin_code";


    private static final String CONSENT = "consent";
    private static final String SPECIAL_INSTRUCTION = "special_instruction";
    private static final String ADDED_BY = "added_by";
    private static final String ADDED_ON = "added_on";
    private static final String MODIFIED_BY = "modified_by";
    private static final String MODIFIED_ON = "modified_on";
    private static final String IS_DISABLED = "is_disabled";
    private static final String DISABLED_BY = "disabled_by";
    private static final String DISABLED_ON = "disabled_on";
    private static final String IS_DELETED = "is_deleted";
    private static final String DELETED_BY = "deleted_by";
    private static final String DELETED_ON = "deleted_on";
    private static final String VISIT_DATE = "visit_date";

    private final String KEY_VISIT_ID = "key_visit_id";
    private final String ACTUAL_FOLLOW_UP_DATE = "actual_follow_up_date";

    private final String DOCTOR_LOGIN_ID = "doc_login_id";

    private static final String FIRST_NAME = "first_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String LAST_NAME = "last_name";
    private static final String GENDER = "gender";
    private static final String DOB = "dob";


    private static final String AGE = "age";
    static final String PHONE_NUMBER = "phonenumber";

    private static final String LANGUAGE = "language";
    private static final String PHOTO = "photo";
    private static final String FOLLOW_UP_DATE = "follow_up_date";
    private static final String KEY_VALUE = "value";


    private static final String DAYS = "days";
    private static final String WEEKS = "weeks";
    private static final String MONTHS = "months";
    private static final String AILMENT = "ailment";
    private static final String PRESCRIPTION = "prescription";
    private static final String CLINICAL_NOTES = "clinical_notes";
    private static final String ACTION = "action";

    private static final String WEIGHT = "weight";
    private static final String PULSE = "pulse";
    private static final String BP = "bp_high";
    private static final String BP_LOW = "bp_low";
    private static final String TEMP = "temperature";
    private static final String SUGAR = "sugar";
    private static final String SYMPTOMS = "symptoms";
    private static final String DIGNOSIS = "diagnosis";
    private static final String TESTS = "tests";
    private static final String DRUGS = "drugs";
    private static final String PHONE_TYPE = "phone_type";

    private static final String ALTERNATE_PHONE_TYPE = "alternate_phone_type";
    private static final String ISD_CODE = "isd_code";
    private static final String ATERNATE_NO_ISD_CODE = "alternate_no_isd";

    private static final String CALLED_FROM = "called_from";
    private static final String PREFERED_TIME = "preferred_time";
    private static final String LOCATION = "location";

    private static final String REQUEST_ON = "requested_on";
    private static final String REQUEST_FULLFILLED = "request_fullfilled";
    private static final String FLAG = "flag";
    private static final String ALTERNATE_PHONE_NO = "alternate_no";

    private static final String UID = "uid";
    private static final String UIDTYPE = "uid_type";

    private static final String STATUS = "status";
    private static final String TABLE_ASYNC = "async";
    private static final String MEMBERSHIP_ID = "membershipid";
    private static final String DISTRICT = "district";
    private static String CITY_TOWN = "city";
    private static final String NAME_TITLE = "title";
    static final String PATIENT_SEND = "patient_send";
    static final String VISIT_SEND = "visit_send";
    static final String RESPONSE_CODE = "response_code";
    static final String RESPONSE_TIME = "response_time";
    static final String RECORD_SOURCE = "rec_source";
    private static final String CONTACT_FOR_PATIENT = "contactforpatient";



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    public static synchronized SQLiteHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SQLiteHandler(context.getApplicationContext());
        }
        return sInstance;
    }


    private static final String BANNER_ID = " banner_id";
    private static String COMPANY_ID = "company_id";
    private static final String BRAND_NAME = "brand_name";
    private static final String FOLDER_NAME = "banner_folder";
    private static final String MODULE = "module";

    private static final String REFERED_BY = "refered_by";
    private static final String REFERED_TO = "refered_to";

    private static final String BANNER_IMAGE1 = "banner_image";
    private static final String BMI = "bmi";
    private static final String SUGAR_FASTING = "sugar_fasting";
    private static final String HEIGHT = "height";
    private static final String TABLE_ASSOCIATE_MASTER = "associate_master";
    private static final String SPECIALTY = "speciality";
    private static final String ASSOCIATE_TYPE = "associate_type";
    private static final String MODIFIED_COUNTER = "modified_counter";
    private static final String ASSOCIATE_VALUE = "type";


    static final String CREATE_ASSOCIATE_MASTER_TABLE =
            "CREATE TABLE " + TABLE_ASSOCIATE_MASTER + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT, " +
                    PHONE_NUMBER + " TEXT, " +
                    PHONE_TYPE + " TEXT, " +
                    ISD_CODE + " TEXT, " +
                    KEY_EMAIL + " TEXT, " +
                    SPECIALTY + " TEXT, " +
                    ASSOCIATE_TYPE + " TEXT, " +
                    ASSOCIATE_ADDRESS + " TEXT, " +
                    CITY_TOWN + " TEXT, " +
                    ASSOCIATE_STATE + " TEXT, " +
                    PIN_CODE + " NUMERIC, " +
                    DISTRICT + " TEXT ," +
                    ADDED_BY + " text , " +
                    ADDED_ON + " text , " +
                    MODIFIED_BY + " text, " +
                    MODIFIED_ON + " text, " +
                    IS_DELETED + " text , " +
                    IS_DISABLED + " text, " +
                    DISABLED_BY + " text, " +
                    DISABLED_ON + " text , " +
                    DELETED_ON + " text ," +
                    MODIFIED_COUNTER + " NUMERIC ," +
                    FLAG + " text ) ";

    static String CREATE_TABLE_ASSOCTAE_TYPE_MASTER = "create table "
            + ASSOCIATE_TYPE + "(" + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + ASSOCIATE_VALUE + " text, "
            + ADDED_BY + " text , "
            + ADDED_ON + " text , "
            + MODIFIED_BY + " text, "
            + FLAG + " text , "
            + DELETED_ON + " text ) ";

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    private static final String DATABASE_ALTER_TABLE_USER = "ALTER TABLE user"
            + " ADD COLUMN phonenumber text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_ADDSTATE = "ALTER TABLE patient"
            + " ADD COLUMN patient_state text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_PHONE_TYPE = "ALTER TABLE patient"
            + " ADD COLUMN phone_type text;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_ISD_CODE = "ALTER TABLE patient"
            + " ADD COLUMN isd_code text;";


    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_WEIGHT = " ALTER TABLE patient_history" + " ADD COLUMN weight text;";


    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_PULSE = " ALTER TABLE patient_history" + " ADD COLUMN pulse text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_BP = " ALTER TABLE patient_history" + " ADD COLUMN bp_high text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_MMHG = " ALTER TABLE patient_history" + " ADD COLUMN bp_low text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_TEMP = " ALTER TABLE patient_history" + " ADD COLUMN temperature text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_SUGAR = " ALTER TABLE patient_history" + " ADD COLUMN sugar text;";


    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_SYMPTOMS = " ALTER TABLE patient_history" + " ADD COLUMN symptoms text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_DIGNOSIS = " ALTER TABLE patient_history" + " ADD COLUMN diagnosis text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_TESTS = " ALTER TABLE patient_history" + " ADD COLUMN tests text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_DRUGS = " ALTER TABLE patient_history" + " ADD COLUMN drugs text;";

    private static final String DATABASE_ALTER_TABLE_DOCTORINFO = " ALTER TABLE doctor_perInfo" + " ADD COLUMN company_id text;";


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //addDummyColumn()

        if (oldVersion < 2) {
            db.execSQL(DATABASE_ALTER_TABLE_USER);
            db.execSQL(DATABASE_ALTER_TABLE_DOCTORINFO);//adding column company_id
        }
        if (oldVersion < 3) {
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_ADDSTATE);

            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_WEIGHT);

            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_PULSE);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_BP);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_MMHG);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_TEMP);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_SUGAR);

            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_SYMPTOMS);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_DIGNOSIS);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_TESTS);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_DRUGS);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_PHONE_TYPE);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_ISD_CODE);
        }

    }


    /**
     * Updating Patient Personal details in database
     */
    public void updatePatientPersonalInfo(String keyid, String firstname, String middlename, String lastname, String gender, String dateofbirth, String age, String phNo, String language, String imgPath, String modified_on_date, String modified_by, String modifiedTime, String action, String flag, String docId,
                                          String address, String cityortown, String district, String pin, String state, String phoneType, String alternatephoneType, String alternatePhoneNumber, String uid, String uidType, String isd_code, String alternateisd_code, String status, String email) throws ClirNetAppException {

        SQLiteDatabase db = this.getWritableDatabase();

        try {

            ContentValues values = new ContentValues();
            values.put(MODIFIED_ON, modified_on_date);
            values.put(FIRST_NAME, firstname); // Name
            values.put(MIDDLE_NAME, middlename);
            values.put(LAST_NAME, lastname);
            values.put(GENDER, gender);
            values.put(DOB, dateofbirth);
            values.put(AGE, age);
            values.put(PHONE_NUMBER, phNo);
            values.put(LANGUAGE, language);
            values.put(PHOTO, imgPath);
            values.put(MODIFIED_BY, modified_by);
            values.put(MODIFIED_TIME, modifiedTime);
            values.put(ACTION, action);
            values.put(SYCHRONIZED, flag);
            values.put(DOCTOR_ID, docId);
            values.put(PATIENT_ADDRESS, address);
            values.put(PATIENT_CIT_CITY_TOWN, cityortown);
            values.put(DISTRICT, district);
            values.put(PIN_CODE, pin);
            values.put(PATIENT_STATE, state);
            values.put(PHONE_TYPE, phoneType);
            values.put(ALTERNATE_PHONE_NO, alternatePhoneNumber);
            values.put(ALTERNATE_PHONE_TYPE, alternatephoneType);
            values.put(UID, uid);
            values.put(UIDTYPE, uidType);
            values.put(ISD_CODE, isd_code);
            values.put(ATERNATE_NO_ISD_CODE, alternateisd_code);
            values.put(STATUS, status);
            values.put(KEY_EMAIL, email);


            // Inserting Row
            db.update(TABLE_PATIENT, values, KEY_PATIENT_ID + "=" + keyid, null);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ClirNetAppException("Error inserting data");
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }

    }

    //add user login credentails into db
    public void addLoginRecord(String username, String password, String phoneNumber) throws ClirNetAppException {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValue = new ContentValues();

            contentValue.put(KEY_NAME, username);
            contentValue.put(KEY_PASSWORD, password);
            contentValue.put(PHONE_NUMBER, phoneNumber);

            // Inserting Row
            db.delete(TABLE_USER, null, null);
            db.insert(TABLE_USER, null, contentValue);
        } catch (Exception e) {
            throw new ClirNetAppException("Error inserting data");
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }

    }


    //update the patient visit records into db
    public void updatePatientOtherInfo(String strId, String visitId, String usersellectedDate, String strfollow_up_date, String daysSel, String fowSel, String monthSel, String clinical_note, String patientImagePath, String ailments, String modified_dateon, String modified_time, String modified_by, String action, String patInfoType, String flag,
                                       String weight, String pulse, String bphigh, String bplow, String temparature, String sugar, String symptoms, String dignosis, String tests, String drugs, String strHeight, String strbmi, String strSugarFasting, String status, String visit_date, String referedBy, String referedTo) throws ClirNetAppException {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(ACTUAL_FOLLOW_UP_DATE, usersellectedDate); // Name
            values.put(FOLLOW_UP_DATE, strfollow_up_date); // Name
            values.put(DAYS, daysSel); // Email
            values.put(WEEKS, fowSel); // Email
            values.put(MONTHS, monthSel); // Created At
            values.put(CLINICAL_NOTES, clinical_note);
            values.put(PRESCRIPTION, patientImagePath);
            values.put(AILMENT, ailments);
            values.put(MODIFIED_ON, modified_dateon);
            values.put(MODIFIED_TIME, modified_time);
            values.put(MODIFIED_BY, modified_by);
            values.put(ACTION, action);
            values.put(PATIENT_INFO_TYPE_FORM, patInfoType);
            values.put(SYCHRONIZED, flag);
            values.put(WEIGHT, weight);
            values.put(PULSE, pulse);
            values.put(BP, bphigh);
            values.put(BP_LOW, bplow);
            values.put(TEMP, temparature);
            values.put(SUGAR, sugar);
            values.put(SYMPTOMS, symptoms);
            values.put(DIGNOSIS, dignosis);
            values.put(TESTS, tests);
            values.put(DRUGS, drugs);
            values.put(HEIGHT, strHeight);
            values.put(BMI, strbmi);
            values.put(SUGAR_FASTING, strSugarFasting);
            values.put(VISIT_DATE, visit_date);
            values.put(STATUS, status);
            values.put(REFERED_BY, referedBy);
            values.put(REFERED_TO, referedTo);


            // Inserting Row
            db.update(TABLE_PATIENT_HISTORY, values, KEY_PATIENT_ID + "=" + strId + " AND " + KEY_VISIT_ID + "=" + visitId, null);
        } catch (Exception e) {
            throw new ClirNetAppException("Error inserting data");
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }
    }

    //this is used to add the patient prsonal info  from serevr int db
    public void addPatientPersoanlRecords(String pat_id, String doctor_id, String doc_membership_id, String patient_info_type_form,
                                          String pat_first_name, String pat_middle_name, String pat_last_name, String pat_gender,
                                          String pat_date_of_birth, String pat_age, String pat_mobile_no, String pat_address,
                                          String pat_city_town, String pat_pincode, String pat_district, String pref_lang,
                                          String photo_name, String consent, String special_instruction, String added_by,
                                          String added_on, String addedTime, String modified_by, String modified_on,
                                          String is_disabled, String disabled_by, String disabled_on, String is_deleted,
                                          String deleted_by, String deleted_on, String flag) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValue = new ContentValues();

            contentValue.put(KEY_PATIENT_ID, pat_id);
            contentValue.put(DOCTOR_ID, doctor_id);
            contentValue.put(DOCTOR_MEMBERSHIP_ID, doc_membership_id);
            contentValue.put(PATIENT_INFO_TYPE_FORM, patient_info_type_form);
            contentValue.put(FIRST_NAME, pat_first_name);
            contentValue.put(MIDDLE_NAME, pat_middle_name);
            contentValue.put(LAST_NAME, pat_last_name);


            contentValue.put(GENDER, pat_gender);
            contentValue.put(DOB, pat_date_of_birth);
            contentValue.put(AGE, pat_age);
            contentValue.put(PHONE_NUMBER, pat_mobile_no);
            contentValue.put(PATIENT_ADDRESS, pat_address);
            contentValue.put(PATIENT_CIT_CITY_TOWN, pat_city_town);


            contentValue.put(PIN_CODE, pat_pincode);
            contentValue.put(DISTRICT, pat_district);
            contentValue.put(LANGUAGE, pref_lang);
            contentValue.put(PHOTO, photo_name);
            contentValue.put(CONSENT, consent);
            contentValue.put(SPECIAL_INSTRUCTION, special_instruction);


            contentValue.put(ADDED_BY, added_by);
            contentValue.put(ADDED_ON, added_on);
            contentValue.put(ADDED_TIME, addedTime);
            contentValue.put(MODIFIED_BY, modified_by);
            contentValue.put(MODIFIED_ON, modified_on);
            contentValue.put(IS_DISABLED, is_disabled);
            contentValue.put(DISABLED_BY, disabled_by);

            contentValue.put(DISABLED_ON, disabled_on);
            contentValue.put(IS_DELETED, is_deleted);
            contentValue.put(DELETED_BY, deleted_by);
            contentValue.put(DELETED_ON, deleted_on);
            contentValue.put(SYCHRONIZED, flag);


            db.delete(TABLE_PATIENT, KEY_PATIENT_ID + " = ?" + " AND " + DOB + " = ? " + " AND " + PHONE_NUMBER + " = ? " + " AND " + ADDED_ON + " = ? ", new String[]{pat_id, pat_date_of_birth, pat_mobile_no, added_on});
            // Inserting Row
            db.insert(TABLE_PATIENT, null, contentValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }
    }


    //this is used to add the patient visits from server info int db
    public void addPatientHistoryRecords(String visit_id, String pat_id, String ailment, String visit_date, String follow_up_date,
                                         String follow_up_days, String follow_up_weeks, String follow_up_months,
                                         String act_follow_up_date, String notes, String added_by, String added_on, String addedTime,
                                         String modified_by, String modified_on, String is_disabled, String disabled_by,
                                         String disabled_on, String is_deleted, String deleted_by, String deleted_on, String flag, String patient_info_type_form,
                                         String precription, String weight, String pulse, String bphigh, String bplow, String temparature, String sugar, String symptoms, String dignosis, String tests, String drugs, String doc_mem_id, String doc_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues contentValue = new ContentValues();

            contentValue.put(KEY_VISIT_ID, visit_id);
            contentValue.put(KEY_PATIENT_ID, pat_id);
            contentValue.put(AILMENT, ailment);
            contentValue.put(VISIT_DATE, visit_date);
            contentValue.put(FOLLOW_UP_DATE, follow_up_date);
            contentValue.put(DAYS, follow_up_days);


            contentValue.put(WEEKS, follow_up_weeks);
            contentValue.put(MONTHS, follow_up_months);
            contentValue.put(ACTUAL_FOLLOW_UP_DATE, act_follow_up_date);
            contentValue.put(CLINICAL_NOTES, notes);

            contentValue.put(ADDED_BY, added_by);
            contentValue.put(ADDED_ON, added_on);
            contentValue.put(ADDED_TIME, addedTime);
            contentValue.put(MODIFIED_BY, modified_by);
            contentValue.put(MODIFIED_ON, modified_on);
            contentValue.put(IS_DISABLED, is_disabled);
            contentValue.put(DISABLED_BY, disabled_by);

            contentValue.put(DISABLED_ON, disabled_on);
            contentValue.put(IS_DELETED, is_deleted);
            contentValue.put(DELETED_BY, deleted_by);
            contentValue.put(DELETED_ON, deleted_on);
            contentValue.put(SYCHRONIZED, flag);
            contentValue.put(PATIENT_INFO_TYPE_FORM, patient_info_type_form);
            contentValue.put(WEIGHT, weight);
            contentValue.put(PRESCRIPTION, precription);
            contentValue.put(PULSE, pulse);
            contentValue.put(BP, bphigh);
            contentValue.put(BP_LOW, bplow);
            contentValue.put(TEMP, temparature);
            contentValue.put(SUGAR, sugar);
            contentValue.put(SYMPTOMS, symptoms);
            contentValue.put(DIGNOSIS, dignosis);
            contentValue.put(TESTS, tests);
            contentValue.put(DRUGS, drugs);
            contentValue.put(DOCTOR_MEMBERSHIP_ID, doc_mem_id);
            contentValue.put(DOCTOR_ID, doc_id);


            db.delete(TABLE_PATIENT_HISTORY, KEY_PATIENT_ID + " = ?" + " AND " + KEY_VISIT_ID + " = ? ", new String[]{pat_id, visit_id});
            // Inserting Row
            db.insert(TABLE_PATIENT_HISTORY, null, contentValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //This will add records from registration page into patient table  28/8/2016 ashish u
    public void addPatientPersonalfromLocal(int patient_id, String doctor_id, String first_name, String middle_name, String last_name, String sex, String strdate_of_birth, String current_age, String phone_number, String selectedLanguage, String patientImagePath, String create_date, String doctor_membership_number, String flag, String patientInfoType, String addedTime, String added_by, String action,
                                            String address, String city, String district, String pinno, String state, String phoneType, String alternatePhone_no, String selectedPhoneTypealternate_no, String uid, String uidType, String selectedIsd_codeType, String alternate_no_isdcode, String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_PATIENT_ID, patient_id);
            values.put(DOCTOR_ID, doctor_id);
            values.put(FIRST_NAME, first_name); // Name
            values.put(MIDDLE_NAME, middle_name);
            values.put(LAST_NAME, last_name);
            values.put(GENDER, sex);

            values.put(DOB, strdate_of_birth);
            values.put(AGE, current_age);

            values.put(PHONE_NUMBER, phone_number);
            values.put(LANGUAGE, selectedLanguage);
            values.put(PHOTO, patientImagePath);

            values.put(ADDED_ON, create_date);

            values.put(DOCTOR_MEMBERSHIP_ID, doctor_membership_number);
            values.put(SYCHRONIZED, flag);
            values.put(PATIENT_INFO_TYPE_FORM, patientInfoType);
            values.put(ADDED_TIME, addedTime);
            values.put(ADDED_BY, added_by);
            values.put(ACTION, action);
            values.put(PATIENT_ADDRESS, address);
            values.put(PATIENT_CIT_CITY_TOWN, city);
            values.put(DISTRICT, district);
            values.put(PIN_CODE, pinno);
            values.put(PATIENT_STATE, state);
            values.put(PHONE_TYPE, phoneType);
            values.put(ALTERNATE_PHONE_NO, alternatePhone_no);
            values.put(ALTERNATE_PHONE_TYPE, selectedPhoneTypealternate_no);
            values.put(UID, uid);
            values.put(UIDTYPE, uidType);
            values.put(ISD_CODE, selectedIsd_codeType);
            values.put(ATERNATE_NO_ISD_CODE, alternate_no_isdcode);
            values.put(KEY_EMAIL, email);

            // Inserting Row
            db.insert(TABLE_PATIENT, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        // Log.d(TAG, "New patient inserted into sqlite: " + id);
    }

    //add  new patient records into db from registration page
    public void addHistoryPatientRecords(int visit_id, int patient_id, String usersellectedDate, String follow_up_dates, String daysSel, String fowSel, String monthSel, String prescriptionImgPath, String clinical_note, String added_on_date, String visit_date, String doc_id, String doc_mem_id, String flag, String addedTime, String patientInfoType, String added_by, String action,
                                         String weight, String pulse, String bphigh, String bplow, String temparature, String sugar, String symptoms, String dignosis, String tests, String drugs, String strHeight, String bmi, String strSugarFasting, String referedBy, String referedto, String rec_source) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_VISIT_ID, visit_id);
            values.put(KEY_PATIENT_ID, patient_id);
            values.put(ACTUAL_FOLLOW_UP_DATE, usersellectedDate);
            values.put(FOLLOW_UP_DATE, follow_up_dates);
            values.put(DAYS, daysSel);
            values.put(WEEKS, fowSel);

            values.put(MONTHS, monthSel);
            values.put(PRESCRIPTION, prescriptionImgPath);
            values.put(CLINICAL_NOTES, clinical_note);
            values.put(ADDED_ON, added_on_date);
            values.put(VISIT_DATE, visit_date);
            values.put(DOCTOR_ID, doc_id);
            values.put(DOCTOR_MEMBERSHIP_ID, doc_mem_id);
            values.put(SYCHRONIZED, flag);
            values.put(ADDED_TIME, addedTime);
            values.put(PATIENT_INFO_TYPE_FORM, patientInfoType);
            values.put(ACTION, action);
            values.put(ADDED_BY, added_by);
            values.put(WEIGHT, weight);
            values.put(PULSE, pulse);
            values.put(BP, bphigh);
            values.put(BP_LOW, bplow);
            values.put(TEMP, temparature);
            values.put(SUGAR, sugar);
            values.put(SYMPTOMS, symptoms);
            values.put(DIGNOSIS, dignosis);
            values.put(TESTS, tests);
            values.put(DRUGS, drugs);
            values.put(HEIGHT, strHeight);
            values.put(BMI, bmi);
            values.put(SUGAR_FASTING, strSugarFasting);
            values.put(REFERED_BY, referedBy);
            values.put(REFERED_TO, referedto);
            values.put(RECORD_SOURCE, rec_source);


           long id= db.insert(TABLE_PATIENT_HISTORY, null, values);
            Log.e("id"," "+id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //add patient records from add pateint update page
    public void addPatientNextVisitRecord(String visit_id, String strPatientId, String usersellectedDate, String follow_up_dates, String daysSel, String fowSel, String monthSel, String clinical_note, String prescriptionimgPath, String ailments, String visit_date, String doc_id, String doc_mem_id, String addedOnDate, String addedTime, String flag, String added_by, String action, String patInfoType,
                                          String weight, String pulse, String bp, String mmhg, String temparature, String sugar, String symptoms, String dignosis, String tests, String drugs, String strHeight, String strbmi, String strSugarFasting, String referedBy, String referedTo, String rec_source) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            values.put(KEY_VISIT_ID, visit_id);
            values.put(KEY_PATIENT_ID, strPatientId);
            values.put(ACTUAL_FOLLOW_UP_DATE, usersellectedDate); // Name
            values.put(FOLLOW_UP_DATE, follow_up_dates);
            values.put(DAYS, daysSel); // Email
            values.put(WEEKS, fowSel); // Email
            values.put(MONTHS, monthSel); // Created At
            values.put(CLINICAL_NOTES, clinical_note);
            values.put(PRESCRIPTION, prescriptionimgPath);
            values.put(AILMENT, ailments);
            values.put(VISIT_DATE, visit_date);
            values.put(ADDED_ON, addedOnDate);
            values.put(ADDED_TIME, addedTime);
            values.put(DOCTOR_ID, doc_id);
            values.put(DOCTOR_MEMBERSHIP_ID, doc_mem_id);
            values.put(SYCHRONIZED, flag);
            values.put(ADDED_BY, added_by);
            values.put(ACTION, action);
            values.put(PATIENT_INFO_TYPE_FORM, patInfoType);
            values.put(WEIGHT, weight);
            values.put(PULSE, pulse);
            values.put(BP, bp);
            values.put(BP_LOW, mmhg);
            values.put(TEMP, temparature);
            values.put(SUGAR, sugar);
            values.put(SYMPTOMS, symptoms);
            values.put(DIGNOSIS, dignosis);
            values.put(TESTS, tests);
            values.put(DRUGS, drugs);
            values.put(HEIGHT, strHeight);
            values.put(BMI, strbmi);
            values.put(SUGAR_FASTING, strSugarFasting);
            values.put(REFERED_BY, referedBy);
            values.put(REFERED_TO, referedTo);
            values.put(RECORD_SOURCE, rec_source);

            db.insert(TABLE_PATIENT_HISTORY, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //this will give u a json array of patient records where flag =o
    public JSONArray getResultsForPatientInformation() {

        JSONArray resultSet;

        Cursor cursor = null;

        SQLiteDatabase db1 = getReadableDatabase();

        try {
            resultSet = new JSONArray();
            String searchQuery = "SELECT  * FROM patient where flag = 0 ";

            cursor = db1.rawQuery(searchQuery, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            if (cursor.getString(i) != null) {

                                rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                            } else {
                                rowObject.put(cursor.getColumnName(i), "");
                            }
                        } catch (Exception e) {//. e.printStackTrace();
                        }

                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }

            // Log.e("resultSet"," "+ resultSet.toString());
            return resultSet;


        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
    }

    //this will give u a json array of patient records where flag =0
    public JSONArray getResultsForPatientHistory() {


        SQLiteDatabase db1 = getReadableDatabase();
        Cursor cursor = null;

        try {
            String searchQuery = "SELECT  * FROM patient_history where flag = 0 ";
            cursor = db1.rawQuery(searchQuery, null);

            JSONArray resultSet = new JSONArray();

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            if (cursor.getString(i) != null) {

                                rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                            } else {
                                rowObject.put(cursor.getColumnName(i), "");
                            }
                        } catch (Exception e) {
                            //  Log.d("TAG_NAME", e.getMessage());
                        }

                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            return resultSet;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
    }


    //update the flag once data send to server successfully
    public void FlagupdatePatientPersonal(String strPatientId, String flag) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            // Inserting Row
            db.update(TABLE_PATIENT, values, KEY_PATIENT_ID + "=" + strPatientId, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //update the flag once data send to server successfully
    public void FlagupdatePatientVisit(String strVisitId, String flag) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            // Inserting Row
            db.update(TABLE_PATIENT_HISTORY, values, KEY_VISIT_ID + "=" + strVisitId, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }
    }

    //add doctor personal info into db
    public void addDoctorPerInfo(String doc_id, String doctor_login_id, String membership_id, String first_name, String middle_name, String last_name, String email_id, String phone_no, String company_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();

            values.put(DOCTOR_ID, doc_id);
            values.put(DOCTOR_LOGIN_ID, doctor_login_id);
            values.put(DOCTOR_MEMBERSHIP_ID, membership_id);
            values.put(FIRST_NAME, first_name); // Name
            values.put(MIDDLE_NAME, middle_name);
            values.put(LAST_NAME, last_name);
            values.put(KEY_EMAIL, email_id);
            values.put(PHONE_NUMBER, phone_no);
            values.put(COMPANY_ID, company_id);
            //this will update record if exist else create new record 02-03-2017
            int u = db.update(TABLE_DOCTORINFO, values, "doctor_id=?", new String[]{doc_id});
            if (u == 0) {
                db.insertWithOnConflict(TABLE_DOCTORINFO, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {

                db.close(); // Closing database connection
            }
        }
    }

    public void addAsync() {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_VALUE, "true");

            db.insert(TABLE_ASYNC, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {

                db.close(); // Closing database connection
            }
        }
    }


    //update the flag once data send to server successfully
    public void FlagupdatePassword(String password) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(KEY_PASSWORD, password); // Name

            // Inserting Row
            db.update(TABLE_USER, values, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }
    }

    public void addBannerDisplayData(String doc_id, String membership_id, String company_id, String banner_id, String banner_folder, String banner_image_url,
                                     int banner_image_type, String module, String is_disabled, String is_deleted, String added_on, String flag, String source_page) {

        SQLiteDatabase db = null;
        try {

            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DOCTOR_ID, doc_id);
            values.put(MEMBERSHIP_ID, membership_id);
            values.put(COMPANY_ID, company_id);
            values.put(BANNER_ID, banner_id);


            values.put(FOLDER_NAME, banner_folder);
            values.put(BANNER_IMAGE1, banner_image_url);
            values.put("banner_type_id", banner_image_type);
            values.put(MODULE, module);

            values.put(IS_DISABLED, is_disabled);
            values.put(IS_DELETED, is_deleted);
            values.put(ADDED_ON, added_on);
            values.put(FLAG, flag);
            values.put("source_page", source_page);

            db.insert(TABLE_BANNER_DISPLAY, null, values);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void addBannerClickedData(String doc_id, String membership_id, String company_id, String banner_id, String banner_folder, String banner_image_url,
                                     int banner_image_type, String module, String is_disabled, String is_deleted, String added_on, String flag, String source_page) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DOCTOR_ID, doc_id);
            values.put(MEMBERSHIP_ID, membership_id);
            values.put(COMPANY_ID, company_id);
            values.put(BANNER_ID, banner_id);


            values.put(FOLDER_NAME, banner_folder);
            values.put(BANNER_IMAGE1, banner_image_url);
            values.put("banner_type_id", banner_image_type);
            values.put(MODULE, module);

            values.put(IS_DISABLED, is_disabled);
            values.put(IS_DELETED, is_deleted);
            values.put(ADDED_ON, added_on);
            values.put(FLAG, flag);
            values.put("source_page", source_page);

            db.insert(TABLE_BANNER_CLICKED, null, values);

        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    public void FlagupdatePatientVisit(String flag) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name
            // String update="UPDATE patient_history SET flag = '1'";
            // Inserting Row
            //  db.execSQL(update);
            db.update(TABLE_PATIENT_HISTORY, values, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }
    }

    //update the flag once data send to server successfully
    public void FlagupdatePatientPersonal(String flag) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            // Inserting Row
            db.update(TABLE_PATIENT, values, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

    }

    //this will give u a json array of patient records where flag =o
    public JSONArray getResultsForBannerDisplay() {

        JSONArray resultSet;
        //or you can use `context.getDatabasePath("my_db_test.db")`
        Cursor cursor = null;

        SQLiteDatabase db1 = getReadableDatabase();
        try {
            resultSet = new JSONArray();
            String searchQuery = "SELECT  * FROM banner_display where flag = 0 ";

            cursor = db1.rawQuery(searchQuery, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            if (cursor.getString(i) != null) {
                                rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                            } else {
                                rowObject.put(cursor.getColumnName(i), "");
                            }
                        } catch (Exception e) {
                            //Log.d("TAG_NAME", e.getMessage());
                        }
                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            return resultSet;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
    }

    //this will give u a json array of patient records where flag =o
    public JSONArray getResultsForBannerClicked() {

        JSONArray resultSet;
        //or you can use `context.getDatabasePath("my_db_test.db")`
        Cursor cursor = null;

        SQLiteDatabase db1 = getReadableDatabase();

        try {
            resultSet = new JSONArray();
            String searchQuery = "SELECT  * FROM banner_clicked where flag = 0 ";

            cursor = db1.rawQuery(searchQuery, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            if (cursor.getString(i) != null) {

                                rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                            } else {
                                rowObject.put(cursor.getColumnName(i), "");
                            }
                        } catch (Exception e) {
                            // e.printStackTrace();
                        }

                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            return resultSet;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
    }

    //update the flag once data send to server successfully
    public void updateBannerDisplayDataFlag(String banner_id, String flag) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            // Inserting Row
            db.update(TABLE_BANNER_DISPLAY, values, KEY_ID + "=" + banner_id, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

    }

    //update the flag once data send to server successfully
    public void updateBannerClickedDataFlag(String bid, String flag) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            // Inserting Row
            db.update(TABLE_BANNER_CLICKED, values, KEY_ID + "=" + bid, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        //Log.d("updated", "banner flag data modified into sqlite: " + id);
    }

    public void addAsynctascRun_status(String process, String start_time, String end_time, String update_on) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("process", process);
            values.put("start_time", start_time);
            values.put("end_time", end_time);
            values.put("update_on", update_on);


            boolean isProcessExistsboolean = isProcessExists(process);

            if (!isProcessExistsboolean) {
                db.insert("asynctascrun_status", null, values);
            } else {

                db.update("asynctascrun_status", values, "process=?", new String[]{process});
            }


        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //checking if record exist in db  or not if not add it
    private boolean isProcessExists(String process) {
        SQLiteDatabase db;


        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = null;
            String selectQuery = "SELECT  *  FROM asynctascrun_status WHERE process ='" + process + "';";


            cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            //Note  I did not close db connection here bcs we r calling this method from another  whr w r closing db, if we
            //close connection here it will throw excption that we r try to open allready closed connection  30-1-2017
        }
        return false;
    }

    //This will add records from registration page into patient table  28/8/2016 ashish u
    public void addQuickAddPatientPersonalfromLocal(String create_date, String prescriptionImgPath, String added_by, String status, String phnumber, String email, String referedBy, String referedTo) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(PRESCRIPTION, prescriptionImgPath);
            values.put(ADDED_ON, create_date);
            values.put(ADDED_BY, added_by);
            values.put(STATUS, status);
            values.put(PHONE_NUMBER, phnumber);
            values.put(KEY_EMAIL, email);
            values.put(REFERED_BY, referedBy);
            values.put(REFERED_TO, referedTo);

            // Inserting Row
            db.insert("prescription_queue", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    public void setDocMemId(String docMemId, String doc_id) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            String selectQuery = "UPDATE patient_history SET doc_mem_id = '" + docMemId + "', doctor_id ='" + doc_id + "' where patient_info_type='Electronics' or patient_info_type='Electronic' or doc_mem_id = \"\" or doc_mem_id = null;";

            db.rawQuery(selectQuery, null);

            // Inserting Row

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void deletePrescriptionImageQueue(String prescriptionimgId, String prescriptionimgPath) {

        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            String deleteQuery = "DELETE FROM prescription_queue WHERE id =" + prescriptionimgId + " and prescription='" + prescriptionimgPath + "';";//+ prescriptionimgId + "' ";

            db.execSQL(deleteQuery);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    public void addAssociates(String added_by, String mCounter, String strname, String strmob_no, String selectedPhoneType, String selectedIsd_codeType, String strEmail, String selectedSpeciality, String selectedAssociateType, String straddress, String strcity, String selectedState, String strpin, String strdistrict, String dateTimenew, String flag, String nameTitle,String contactForPatient,String selectedcontactForPatientType,String selectedIsd_code_altType ) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ADDED_BY, added_by);
            values.put(PHONE_NUMBER, strmob_no);
            values.put(KEY_NAME, strname);
            values.put(PHONE_TYPE, selectedPhoneType);
            values.put(ISD_CODE, selectedIsd_codeType);
            values.put(KEY_EMAIL, strEmail);
            values.put(SPECIALTY, selectedSpeciality);
            values.put(ASSOCIATE_TYPE, selectedAssociateType);
            values.put(ASSOCIATE_ADDRESS, straddress);
            values.put(CITY_TOWN, strcity);
            values.put(ASSOCIATE_STATE, selectedState);
            values.put(PIN_CODE, strpin);
            values.put(DISTRICT, strdistrict);
            values.put(ADDED_ON, dateTimenew);
            values.put(MODIFIED_COUNTER, mCounter);
            values.put(NAME_TITLE, nameTitle);
            values.put(FLAG, flag);
            values.put(CONTACT_FOR_PATIENT, contactForPatient);
            values.put("selectedIsd_code_altType", selectedIsd_code_altType);
            values.put("selectedcontactForPatientType", selectedcontactForPatientType);




            db.insert(TABLE_ASSOCIATE_MASTER, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //this will give u a json array of patient records where flag =o
    public JSONArray getAssociateMasterDataDisplay() {

        JSONArray resultSet;
        //or you can use `context.getDatabasePath("my_db_test.db")`
        Cursor cursor = null;

        SQLiteDatabase db1 = getReadableDatabase();
        //Cursor cursor = db1.rawQuery(selectQuery, null);

        //SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);'
        try {
            resultSet = new JSONArray();
            String searchQuery = "SELECT  * FROM associate_master where flag = 0 ";

            cursor = db1.rawQuery(searchQuery, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for (int i = 0; i < totalColumn; i++) {
                    if (cursor.getColumnName(i) != null) {
                        try {
                            if (cursor.getString(i) != null) {
                                rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                            } else {
                                rowObject.put(cursor.getColumnName(i), "");
                            }
                        } catch (Exception e) {
                            //Log.d("TAG_NAME", e.getMessage());
                        }
                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            return resultSet;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
    }

    public void updateAssociates(String uid, String modified_by, String modiedCounter, String strname, String strmob_no, String selectedPhoneType, String selectedIsd_codeType, String strEmail, String selectedSpeciality, String selectedAssociateType, String straddress, String strcity, String selectedState, String strpin, String strdistrict, String dateTimeddmmyyyy, String flag, String nameTitle,String contactForPatient,String selectedIsd_code_altType,String selectedcontactForPatientType) throws ClirNetAppException {

        //update the patient visit records into db
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MODIFIED_BY, modified_by);
            values.put(PHONE_NUMBER, strmob_no);
            values.put(KEY_NAME, strname);
            values.put(PHONE_TYPE, selectedPhoneType);
            values.put(ISD_CODE, selectedIsd_codeType);
            values.put(KEY_EMAIL, strEmail);
            values.put(SPECIALTY, selectedSpeciality);
            values.put(ASSOCIATE_TYPE, selectedAssociateType);
            values.put(ASSOCIATE_ADDRESS, straddress);
            values.put(CITY_TOWN, strcity);
            values.put(ASSOCIATE_STATE, selectedState);
            values.put(PIN_CODE, strpin);
            values.put(DISTRICT, strdistrict);
            values.put(MODIFIED_ON, dateTimeddmmyyyy);
            values.put(FLAG, flag);
            values.put(MODIFIED_COUNTER, modiedCounter);
            values.put(NAME_TITLE, nameTitle);
            values.put(CONTACT_FOR_PATIENT, contactForPatient);
            values.put("selectedIsd_code_altType", selectedIsd_code_altType);
            values.put("selectedcontactForPatientType", selectedcontactForPatientType);

            // Inserting Row
            db.update(TABLE_ASSOCIATE_MASTER, values, KEY_ID + "=" + uid, null);

        } catch (Exception e) {
            throw new ClirNetAppException("Error inserting data");
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }
    }

    public void FlagupdateAssociateMater(String strVisitId, String flag) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(FLAG, flag); // Name

            // Inserting Row
            db.update(TABLE_ASSOCIATE_MASTER, values, KEY_ID + "=" + strVisitId, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }
    }

    public String getPhoneNumberStatus(String strmob_no) throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = this.getReadableDatabase();
            String query = "select count(id) as id from associate_master where phonenumber='" + strmob_no + "' ";


            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("id"));
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getDoctorMembershipIdNew");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnString;
    }

    public String getTableCount(String tableName) throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = this.getReadableDatabase();
            String query = "select count(id) as id from  " + tableName + " ";


            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("id"));
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getTableCount");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnString;
    }

    public ArrayList<String> getAllCompanyBanner() throws ClirNetAppException {
        ArrayList<String> bannerList;
        bannerList = new ArrayList<>();
        String selectQuery = "select banner_id,banner_image1  from company_banners where status_name= 'Active'";
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = this.getWritableDatabase();

            cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    bannerList.add(cursor.getString(0));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getAllDataAssociateMaster");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return bannerList;
    }

    //update the flag once data send to server successfully
    public void FlagupdateBannerClicked(String flag) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            db.update(TABLE_BANNER_CLICKED, values, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

    }

    public void FlagupdateBannerDisplay(String flag) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            db.update(TABLE_BANNER_DISPLAY, values, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

    }

    //This will add records from registration page into patient table  28/8/2016 ashish u
    public void addSyncStatus(String added_on, int patient_nos, int visit_nos, String responseCode, String responseTime) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(ADDED_ON, added_on);
            values.put(PATIENT_SEND, patient_nos);
            values.put(VISIT_SEND, visit_nos);
            values.put(RESPONSE_CODE, responseCode);
            values.put(RESPONSE_TIME, responseTime);

            // Inserting Row
           long id= db.insert(TABLE_SYNC_STATUS, null, values);
            Log.e("Valuesis",""+id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public ArrayList<String> getReferals() throws ClirNetAppException {
        ArrayList<String> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "select name from associate_master";
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = this.getWritableDatabase();

            cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    wordList.add(cursor.getString(0));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getAllDataAssociateMaster");

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return wordList;
    }

    public ArrayList<String> getReferalsnew() throws ClirNetAppException {
        ArrayList<String> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "select title,name from associate_master";
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = this.getWritableDatabase();

            cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    String nameAlias = cursor.getString(0);

                    if (nameAlias == null) {
                        nameAlias = "";
                    }
                    wordList.add(nameAlias + " " + cursor.getString(1));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getAllDataAssociateMaster");

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return wordList;
    }
}

