/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package app.clirnet.com.clirnetapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;


public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = "SQLHandler";
    private final Context myContext;


    private SQLiteDatabase db;
    private static SQLiteHandler sInstance;

    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 2;

    // Database Name
    public static final String DATABASE_NAME = "clirnetApp.db";


    // Login table name
    public static final String TABLE_USER = "user";
    private static final String TABLE_PATIENT = "patient";
    private static final String TABLE_PATIENT_HISTORY = "patient_history";
    private static final String MASTER_AILMENT = "table_MasterAilment";
    private static final String LAST_NAMETBL = "table_LastNames";
    private static final String TABLE_PATIENTPERSONALINFO_HIOTORY = "table_persoplushistorydata";

    private static final String TABLE_AILMENT = "ailment_new";
    private static final String TABLE_DOCTORINFO = "doctor_perInfo";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String ADDED_TIME = "added_time";
    private static final String MODIFIED_TIME = "modified_time";
    private static final String SYCHRONIZED = "flag";

    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    //Patient personal Info Table
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String DOCTOR_ID = "doctor_id";
    private static final String DOCTOR_MEMBERSHIP_ID = "doc_mem_id";
    private static final String PATIENT_INFO_TYPE_FORM = "patient_info_type";
    private static final String PATIENT_ADDRESS = "patient_address";
    private static final String PATIENT_CIT_CITY_TOWN = "patient_city_town";
    private static final String PATIENT_STATE = "patient_state";
    private static final String PIN_CODE = "pin_code";
    private static final String DISTRICT = "district";

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
    public static final String PHONE_NUMBER = "phonenumber";
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
    private static final String MMHG = "mmhg";
    private static final String TEMP = "temperature";
    private static final String SUGAR = "sugar";
    private static final String SYMPTOMS = "symptoms";
    private static final String DIGNOSIS = "dignosis";
    private static final String TESTS = "tests";
    private static final String DRUGS = "drugs";

    //table definations

    private final String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_PASSWORD + " TEXT,"
            + KEY_EMAIL + " TEXT UNIQUE,"
            + KEY_UID + " TEXT,"
            + KEY_CREATED_AT + " TEXT" + ")";

    private static final String TABLE_ASYNC = "async";





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


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

       /* db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_TABLE_PATIENT);
        db.execSQL(CREATE_TABLE_AILMENT);
        db.execSQL(CREATE_TABLE_LASTNAME);
        db.execSQL(CREATE_PATIENT_HISTORY);
        db.execSQL(CREATE_TABLE_PATIENT_INFO_HIOTORY);
        db.execSQL(CREATE_DOCTORPERINFO_TABLE);
        db.execSQL(CREATE_ASYNC);

        //addMe();
        Log.d(TAG, "Database tables created");*/

    }
   /* private static final String DATABASE_ALTER_TEAM_1 = "ALTER TABLE "
            + + " ADD COLUMN " + COLUMN_COACH + " string;";*/

    private static final String DATABASE_ALTER_TABLE_USER = "ALTER TABLE user"
            + " ADD COLUMN phonenumber text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT = "ALTER TABLE patient"
            + " ADD COLUMN patient_state text;";

    private static final String  DATABASE_ALTER_TABLE_PATIENT_HISTORY_WEIGHT = " ALTER TABLE patient_history" +" ADD COLUMN weight text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_PULSE = " ALTER TABLE patient_history" +" ADD COLUMN pulse text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_BP = " ALTER TABLE patient_history" +" ADD COLUMN bp_high text;";

    private static final String  DATABASE_ALTER_TABLE_PATIENT_HISTORY_MMHG = " ALTER TABLE patient_history" +" ADD COLUMN mmhg text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_TEMP = " ALTER TABLE patient_history" +" ADD COLUMN temperature text;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_SUGAR = " ALTER TABLE patient_history" +" ADD COLUMN sugar text;";

    private static final String  DATABASE_ALTER_TABLE_PATIENT_HISTORY_SYMPTOMS = " ALTER TABLE patient_history" +" ADD COLUMN symptoms text;";

    private static final String  DATABASE_ALTER_TABLE_PATIENT_HISTORY_DIGNOSIS =" ALTER TABLE patient_history" +" ADD COLUMN dignosis text;";

    private static final String  DATABASE_ALTER_TABLE_PATIENT_HISTORY_TESTS = " ALTER TABLE patient_history" +" ADD COLUMN tests text;";

    private static final String  DATABASE_ALTER_TABLE_PATIENT_HISTORY_DRUGS = " ALTER TABLE patient_history" +" ADD COLUMN drugs text;";

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        /*if (oldVersion < 2) {
         //   db.execSQL(DATABASE_ALTER_TEAM_2);
        }*/
        if (oldVersion < 3) {
            db.execSQL(DATABASE_ALTER_TABLE_USER);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT);

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

        }
    }

    /**
     * Updating Patient Personal details in database
     */
    public void updatePatientPersonalInfo(String keyid, String firstname, String middlename, String lastname, String gender, String dateofbirth, String age, String phNo, String language, String imgPath, String modified_on_date, String modified_by, String modifiedTime, String action, String flag, String docId) throws ClirNetAppException {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
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

            // Inserting Row
            id = db.update(TABLE_PATIENT, values, KEY_PATIENT_ID + "=" + keyid, null);
        } catch (Exception e) {
            throw new ClirNetAppException("Error inserting data");
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }


        Log.d("update", " user data updatedinto sqlite: " + id);
    }


    /**
     * Re crate database Delete all tables and create them again
     */
    //never used in app
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.delete(TABLE_PATIENT, null, null);
        db.delete(TABLE_PATIENT_HISTORY, null, null);
        db.delete(MASTER_AILMENT, null, null);
        db.delete(LAST_NAMETBL, null, null);
        db.delete(TABLE_PATIENTPERSONALINFO_HIOTORY, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }


    //add user login credentails into db
    public void addLoginRecord(String username, String password,String phoneNumber) throws ClirNetAppException {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try {
            ContentValues contentValue = new ContentValues();

            contentValue.put(KEY_NAME, username);
            contentValue.put(KEY_PASSWORD, password);
            contentValue.put(PHONE_NUMBER,phoneNumber);

            // Inserting Row
            db.delete(TABLE_USER, KEY_NAME + " = ?", new String[]{username});
            id = db.insert(TABLE_USER, null, contentValue);
        } catch (Exception e) {
            throw new ClirNetAppException("Error inserting data");
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }


        Log.d(TAG, "New Login Info into sqlite: " + id);


    }


    //update the patient visit records into db
    public void updatePatientOtherInfo(String strId, String visitId, String usersellectedDate, String strfollow_up_date, String daysSel, String fowSel, String monthSel, String clinical_note, String patientImagePath, String ailments, String modified_dateon, String modified_time, String modified_by, String action, String patInfoType, String flag) throws ClirNetAppException {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
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

            // Inserting Row
            id = db.update(TABLE_PATIENT_HISTORY, values, KEY_PATIENT_ID + "=" + strId + " AND " + KEY_VISIT_ID + "=" + visitId, null);
        } catch (Exception e) {
            throw new ClirNetAppException("Error inserting data");
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }

            Log.d("update", " user data updatedinto sqlite: " + id);
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
        long id = 0;
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

            id = db.insert(TABLE_PATIENT, null, contentValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }

            Log.d("update", " user data updatedinto sqlite: " + id);


        }
    }


    //this is used to add the patient visits from server info int db
    public void addPatientHistoryRecords(String visit_id, String pat_id, String ailment, String visit_date, String follow_up_date,
                                         String follow_up_days, String follow_up_weeks, String follow_up_months,
                                         String act_follow_up_date, String notes, String added_by, String added_on, String addedTime,
                                         String modified_by, String modified_on, String is_disabled, String disabled_by,
                                         String disabled_on, String is_deleted, String deleted_by, String deleted_on, String flag, String patient_info_type_form) {

        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
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

            db.delete(TABLE_PATIENT_HISTORY, KEY_PATIENT_ID + " = ?" + " AND " + KEY_VISIT_ID + " = ? ", new String[]{pat_id, visit_id});
            // Inserting Row
            id = db.insert(TABLE_PATIENT_HISTORY, null, contentValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }


        Log.d(TAG, "New Login Info into sqlite: " + id);


    }

    //This will add records from registration page into patient table  28/8/2016 ashish u
    public void addPatientPersonalfromLocal(int patient_id, String doctor_id, String first_name, String middle_name, String last_name, String sex, String strdate_of_birth, String current_age, String phone_number, String selectedLanguage, String patientImagePath, String create_date, String doctor_membership_number, String flag, String patientInfoType, String addedTime, String added_by, String action,
                                            String address,String city,String district,String pinno,String state  ) {

        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_PATIENT_ID, patient_id);
            values.put(DOCTOR_ID, doctor_id);
            values.put(FIRST_NAME, first_name); // Name
            values.put(MIDDLE_NAME, middle_name); // Email
            values.put(LAST_NAME, last_name); // Email
            values.put(GENDER, sex); // Created At

            values.put(DOB, strdate_of_birth); // Name
            values.put(AGE, current_age); // Email

            values.put(PHONE_NUMBER, phone_number); // Email
            values.put(LANGUAGE, selectedLanguage); // Email
            values.put(PHOTO, patientImagePath); // Created At

            values.put(ADDED_ON, create_date);

            values.put(DOCTOR_MEMBERSHIP_ID, doctor_membership_number);
            values.put(SYCHRONIZED, flag);
            values.put(PATIENT_INFO_TYPE_FORM, patientInfoType);
            values.put(ADDED_TIME, addedTime);
            values.put(ADDED_BY, added_by);
            values.put(ACTION, action);
            values.put(PATIENT_ADDRESS,address);
            values.put(PATIENT_CIT_CITY_TOWN,city);
            values.put(DISTRICT,district);
            values.put(PIN_CODE,pinno);
            values.put(PATIENT_STATE,state);


            // Inserting Row
            id = db.insert(TABLE_PATIENT, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }


        Log.d(TAG, "New patient inserted into sqlite: " + id);


    }

    //add  new patient records into db from registration page
    public void addHistoryPatientRecords(int visit_id, int patient_id, String usersellectedDate, String follow_up_dates, String daysSel, String fowSel, String monthSel, String ailments, String prescriptionImgPath, String clinical_note, String added_on_date, String visit_date, String doc_id, String doc_mem_id, String flag, String addedTime, String patientInfoType, String added_by, String action,
         String weight,String pulse,String bp,String mmhg,String temparature,String sugar,String symptoms,String dignosis,String tests,String drugs) {

        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_VISIT_ID, visit_id);
            values.put(KEY_PATIENT_ID, patient_id);
            values.put(ACTUAL_FOLLOW_UP_DATE, usersellectedDate);
            values.put(FOLLOW_UP_DATE, follow_up_dates);
            values.put(DAYS, daysSel);
            values.put(WEEKS, fowSel);

            values.put(MONTHS, monthSel);
            values.put(AILMENT, ailments);
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
            values.put(WEIGHT,weight);
            values.put(PULSE,pulse);
            values.put(BP,bp);
            values.put(MMHG,mmhg);
            values.put(TEMP,temparature);
            values.put(SUGAR,sugar);
            values.put(SYMPTOMS,symptoms);
            values.put(DIGNOSIS,dignosis);
            values.put(TESTS,tests);
            values.put(DRUGS,drugs);

            id = db.insert(TABLE_PATIENT_HISTORY, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }


        Log.d(TAG, "New patient inserted into sqlite: " + id);
    }

    //add patient records from add pateint update page
    public void addPatientNextVisitRecord(String visit_id, String strPatientId, String usersellectedDate, String follow_up_dates, String daysSel, String fowSel, String monthSel, String clinical_note, String patientImagePath, String ailments, String visit_date, String doc_id, String doc_mem_id, String addedOnDate, String addedTime, String flag, String added_by, String action, String patInfoType) {

        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
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
            values.put(PRESCRIPTION, patientImagePath);
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

            // Inserting Row
            id = db.insert(TABLE_PATIENT_HISTORY, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }


        Log.d("update", " user Visit data added into sqlite: " + id);
    }


    //this will give u a json array of patient records where flag =o
    public JSONArray getResultsForPatientInformation() {

        //or you can use `context.getDatabasePath("my_db_test.db")`

        SQLiteDatabase db1 = getReadableDatabase();
        //Cursor cursor = db1.rawQuery(selectQuery, null);

        //SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);

        String searchQuery = "SELECT  * FROM patient where flag = 0 ";
        Cursor cursor = db1.rawQuery(searchQuery, null);

        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }


                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        db1.close();
        Log.d("TAG_NAME", resultSet.toString());
        return resultSet;
    }

    //this will give u a json array of patient records where flag =o
    public JSONArray getResultsForPatientHistory() {

//or you can use `context.getDatabasePath("my_db_test.db")`

        SQLiteDatabase db1 = getReadableDatabase();
        //Cursor cursor = db1.rawQuery(selectQuery, null);

        //SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);

        String searchQuery = "SELECT  * FROM patient_history where flag=0 ";
        Cursor cursor = db1.rawQuery(searchQuery, null);

        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }

                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        db1.close();
        Log.d("TAG_NAME", resultSet.toString());
        return resultSet;
    }


    //update the flag once data send to server successfully
    public void FlagupdatePatientPersonal(String strPatientId, String flag) {
        SQLiteDatabase db = null;
        long id = 0;
        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            // Inserting Row
            id = db.update(TABLE_PATIENT, values, KEY_PATIENT_ID + "=" + strPatientId, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        Log.d("update", "patient data modified into sqlite: " + id);
    }

    //update the flag once data send to server successfully
    public void FlagupdatePatientVisit(String strVisitId, String flag) {
        long id = 0;
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            // Inserting Row
            id = db.update(TABLE_PATIENT_HISTORY, values, KEY_VISIT_ID + "=" + strVisitId, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }

        Log.d("update", "patient data modified into sqlite: " + id);
    }

    //add doctor personal info into db
    public void addDoctorPerInfo(String doc_id, String doctor_login_id, String membership_id, String first_name, String middle_name, String last_name, String email_id, String phone_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try {

            ContentValues values = new ContentValues();

            values.put(DOCTOR_ID, doc_id);
            values.put(DOCTOR_LOGIN_ID, doctor_login_id);
            values.put(DOCTOR_MEMBERSHIP_ID, membership_id);
            values.put(FIRST_NAME, first_name); // Name
            values.put(MIDDLE_NAME, middle_name); // Email
            values.put(LAST_NAME, last_name); // Email
            values.put(KEY_EMAIL, email_id);
            values.put(PHONE_NUMBER, phone_no);

            //delete all previous records if there any in doctor info table.
          //  db.delete(TABLE_DOCTORINFO, DOCTOR_ID + " = ? " + " AND " + DOCTOR_LOGIN_ID + " = ? ", new String[]{doc_id, doctor_login_id, DOCTOR_MEMBERSHIP_ID});
            db.delete(TABLE_DOCTORINFO, DOCTOR_LOGIN_ID + " = ? ", new String[]{doctor_login_id});

            /// Inserting Row
            id = db.insert(TABLE_DOCTORINFO, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {

                db.close(); // Closing database connection
            }
        }


        Log.d(TAG, "New doctor personal info inserted into sqlite: " + id);


    }

    public void addAsync() {

        SQLiteDatabase db = this.getWritableDatabase();
        long id = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_VALUE, "true");

            id = db.insert(TABLE_ASYNC, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {

                db.close(); // Closing database connection
            }
        }


        Log.d("addedailemnt", "New patient inserted into sqlite: " + id);

    }

    public void addAilments(String ailments) {
        SQLiteDatabase db = null;
        long id = 0;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("ailment", ailments);

            // id =db.insertWithOnConflict("temp_ailment_table", null, values, SQLiteDatabase.CONFLICT_REPLACE);
            id = db.insert(MASTER_AILMENT, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }


        Log.d("addedailemnt", "New ailment inserted into sqlite: " + id);


    }

}
