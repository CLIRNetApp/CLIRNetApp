
package app.clirnet.com.clirnetapp.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.clirnet.com.clirnetapp.app.AppController;


public class SQLiteHandler extends SQLiteOpenHelper {


    private final Context myContext;
    @SuppressLint("StaticFieldLeak")
    private static SQLiteHandler sInstance;

    private static SQLiteDatabase db;

    // Database Version
    static final int DATABASE_VERSION = 4;

    // Database Name
    public static final String DATABASE_NAME = "clirnetApp.db";

    // table name
    static final String TABLE_USER = "user";
    private static final String TABLE_PATIENT = "patient";
    private static final String TABLE_PATIENT_HISTORY = "patient_history";


    private static final String TABLE_HEALTH_AND_LIFESTYLE = "health_and_lifestyle";
    private static final String TABLE_INVESTIGATION = "table_investigation";

    private static final String TABLE_DOCTORINFO = "doctor_perInfo";

    private static final String TABLE_BANNER_CLICKED = "banner_clicked";
    private static final String TABLE_BANNER_DISPLAY = "banner_display";

    static final String TABLE_SYNC_STATUS = "sync_status";
    private static final String TABLE_OBSERVATIONS = "observation";

    static final String RECENT_DATA_KCAP = "recent_data_kcap";
    static final String RECENT_DATA_COMPANDIUM = "recent_data_companium";
    static final String RECENT_DATA_MSESSION = "recent_data_msession";
    static final String TABLE_RECORD_IMAGES = "records_images";
    private static final String TABLE_NOTIFICATIONS = "notification";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String ADDED_TIME = "added_time";
    private static final String MODIFIED_TIME = "modified_time";
    private static final String SYCHRONIZED = "flag";

    static final String KEY_NAME = "name";
    static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";

    //Patient personal Info Table
     static final String KEY_PATIENT_ID = "patient_id";
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

     static final String KEY_VISIT_ID = "key_visit_id";
    private static final String ACTUAL_FOLLOW_UP_DATE = "actual_follow_up_date";

    private static final String DOCTOR_LOGIN_ID = "doc_login_id";

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
    private static final String YEARS = "years";
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


    private static final String FLAG = "flag";
    private static final String ALTERNATE_PHONE_NO = "alternate_no";

    private static final String UID = "uid";
    private static final String UIDTYPE = "uid_type";

    private static final String STATUS = "status";
    private static final String TABLE_ASYNC = "async";
    private static final String MEMBERSHIP_ID = "membershipid";
    private static final String DISTRICT = "district";
    private static final String CITY_TOWN = "city";
    private static final String NAME_TITLE = "title";
    static final String PATIENT_SEND = "patient_send";
    static final String VISIT_SEND = "visit_send";
    static final String RESPONSE_CODE = "response_code";
    static final String RESPONSE_TIME = "response_time";
    private static final String RECORD_SOURCE = "rec_source";
    private static final String CONTACT_FOR_PATIENT = "contactforpatient";

    private static final String ALCOHOL_CONSUMPTION = "alcohol_consumption";
    private static final String PEGS_COUNT = "pegs_count"; //per year
    private static final String PEGS_PER_YEAR = "pegs_per_year";
    private static final String PEGS_SELECTED_GAP = "packs_selected_gap";  //days,week, or month
    private static final String ALLERGIES = "allergies";
    private static final String BINGE_EATING = "binge_eating";
    private static final String SLEEP_STATUS = "sleep_status";
    private static final String CHEWING_TOBACO = "chewing_tobaco";
    private static final String EXCERCISE = "excercise";
    private static final String DRUG_CONSUMPTION = "drug_consumption";
    private static final String DRUG_CONSUMPTION_TYPE = "drug_consumption_type";

    private static final String FOOD_HABIT = "food_habit";
    private static final String FOOD_PREFERENCE = "food_preference";
    private static final String LACATOSE_TOLERANCE = "lactose_tolerance";

    private static final String LIFE_STYLE = "life_style";
    private static final String SEXUALLY_ACTIVE = "sexually_active";

    private static final String SMOKER_TYPE = "smoker_type";
    private static final String STICK_COUNT = "stick_count"; //per year
    private static final String STICK_PER_YEAR = "stick_per_year"; //per year
    private static final String STICKS_SELECTED_GAP = "sticks_selected_gap";  //days,week, or month pr year
    private static final String STRESS_LEVEL = "stress_level";
    private static final String RELIGION = "religion";

    private static final String FOLLOW_UP = "follow_up";
    private static final String RESPIRATION = "respiration";
    private static final String SPO2 = "spo2";
    private static final String FOLLOW_UP_STATUS = "follow_up_status";

    private static final String PALLOR = "pallor";
    private static final String PALLOR_DESCRIPTION = "pallor_description";
    private static final String CYANOSIS = "cyanosis";
    private static final String CYANOSIS_DESCRIPTION = "cyanosis_description";
    private static final String CLUBBING = "clubbing";
    private static final String CLUBBING_DESCRIPTION = "clubbing_description";
    private static final String TREMORS = "tremors";
    private static final String TREMORS_DESCRIPTION = "tremors_description";
    private static final String ICTERUS = "icterus";
    private static final String ICTERUS_DESCRIPTION = "icterus_description";
    private static final String OEDEMA = "oedema";
    private static final String OEDEMA_DESCRIPTION = "oedema_description";
    private static final String CALF_TENDERNERSS = "calf_tenderness";
    private static final String CALF_TENDERNERSS_DESCRIPTION = "calf_tenderness_description";
    private static final String LYMPHADNOPATHY = "lympadenopathy";
    private static final String LYMPHADNOPATHY_DESCRIPTION = "lympadenopathy_description";

    private static final String BLOOD_GROUP = "blood_group";
    private static final String MARITIAL_STATUS = "maritial_status";
    private static final String INCOME_RANGE = "income_range";
    private static final String FAMILY_HISTORT = "family_history";
    private static final String HOSPITALIZATION_SUREGERY = "hospitalization_surgery";
    private static final String OBESITY = "obesity";
    static final String KC_ID = "kc_id";
    static final String KC_TEXT = "kc_text";
    static final String KC_SOURCE_NAME = "kc_source_name";
    static final String COMP_QA_ID = "kcomp_qa_id";
    static final String COMP_QA_QUESTION = "kcomp_qa_question";
    static final String COMP_QA_ANSWER = "comp_qa_answer";
    static final String COMP_URL = "comp_url";
    static final String MS_ID = "ms_id";
    static final String MS_TOPIC = "ms_topic";

    static final String MS_BRIEF = "ms_brief";
    static final String DOCTOR_NAME = "doctor_name";
    static final String MS_DATE_TIME = "ms_date_time";
    static final String MS_SPECIALITY_NAME = "specialities_name";
    static final String MS_CAT_NAME = "ms_cat_name";
    static final String MS_URL = "ms_url";
    static final String MS_STATUS = "ms_status";
    private static final String IS_AGE_CALCULATED = "age_calculated";
     static final String IMAGE_URL = "image_url";
    private static final String IMAGE_TYPE = "image_type";
    private static final String IMAGE_COUNT = "image_count";
    static final String GLOBAL_IMAGE_URL="global_img_url";
    static final String NEW_DOWNLOAD_IMAGE_NAME="server_image_name";
    static final String NEW_DOWNLOAD_IMAGE_PATH="downloaded_image_path";
    private  static final String STATUS_UPLOADED ="status_uploded";
    private  static final String IS_IMAGE_DELETED="is_img_deleted";
    private static final String MESSAGE = "message";
    private static final String HEADER = "header";
    private static final String ACTION_PATH = "action_path";
    private static final String TYPE = "type";
    //  private  static final String SEND_SMS_FLAG="msg_flag";
    private static final String DAYS_DOB = "dob_days";
    private static final String MONTH_DOB = "dob_month";
    private static final String YEAR_DOB = "dob_year";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    public static synchronized SQLiteHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's conTEXT.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SQLiteHandler(context.getApplicationContext());
            db = sInstance.getWritableDatabase();
        }
        return sInstance;
    }

    private static final String BANNER_ID = "banner_id";
    private static final String COMPANY_ID = "company_id";
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
    private static final String ECG = "ecg";
    private static final String HBA1C = "hba1c";
    private static final String ACER = "acer";
    private static final String PFT = "pft";
    private static final String SEREM_UREA = "serem_urea";
    private static final String LIPID_PROFILE_TC = "lipid_profile_tc";
    private static final String LIPID_PROFILE_TG = "lipid_profile_tg";
    private static final String LIPID_PROFILE_LDL = "lipid_profile_ldl";
    private static final String LIPID_PROFILE_VHDL = "lipid_profile_vhdl";
    private static final String LIPID_PROFILE_HDL = "lipid_profile_hdl";
    private static final String LIPID_PROFILE_TCH = "lipid_profile_tch";
    private static final String CHEWING_TOBACO_OTHER = "tobaco_other";
    private static final String LAST_SMOKING_YEAR = "last_smoke_year";
    private static final String LAST_DRINK_YEAR = "last_drink_year";
    private static final String OCCUPATION = "occupation";
    private static final String OCCUPATION_NAME = "name";
    private static final String HB = "hb";
    private static final String PLATELET_COUNT = "platelet_count";
    private static final String ESR = "esr";
    private static final String DCL = "dcl";
    private static final String DCN = "dcn";
    private static final String DCE = "dce";
    private static final String DCM = "dcm";
    private static final String DCB = "dcb";
    private static final String TOTAL_BILIRUBIN = "total_bilirubin";
    private static final String DIRECT_BILIRUBIN = "direct_bilirubin";
    private static final String INDIRECT_BILIRUBIN = "indirect_bilirubin";
    private static final String SGPT = "sgpt";
    private static final String SGOT = "sgot";
    private static final String GGT = "ggt";
    private static final String TOTAL_PROTEIN = "total_protein";
    private static final String ALBUMIN = "albumin";
    private static final String GLOBULIN = "globulin";
    private static final String AG_RATIO = "ag_ratio";
    private static final String HDL_LDL_RATIO = "hdl_ldl_ratio";
    private static final String SUGAR_RBS = "sugar_rbs";
    private static final String URINE_PUC_CELL = "urine_pus_cell";
    private static final String URINE_RBC = "urine_rbc";
    private static final String URINE_CAST = "urine_cast";
    private static final String URINE_PROTEIN = "urine_protein";
    private static final String URINE_CRYSTAL = "urine_crystal";
    private static final String MICROALBUMINURIA = "microalbuminuria";
    private static final String SEREM_CREATININE = "serum_creatinine";
    private static final String ACR = "acr";
    private static final String TSH = "tsh";
    private static final String T3 = "t3";
    private static final String T4 = "t4";


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
                    ADDED_BY + " TEXT , " +
                    ADDED_ON + " TEXT , " +
                    MODIFIED_BY + " TEXT, " +
                    MODIFIED_ON + " TEXT, " +
                    IS_DELETED + " TEXT , " +
                    IS_DISABLED + " TEXT, " +
                    DISABLED_BY + " TEXT, " +
                    DISABLED_ON + " TEXT , " +
                    DELETED_ON + " TEXT ," +
                    MODIFIED_COUNTER + " NUMERIC ," +
                    FLAG + " TEXT ) ";


    private static final String CREATE_HEALTH_AND_LIFESTYLE_TABLE =
            "CREATE TABLE " + TABLE_HEALTH_AND_LIFESTYLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_PATIENT_ID + " INTEGER NOT NULL UNIQUE , " +
                    ALCOHOL_CONSUMPTION + " TEXT, " +
                    PEGS_COUNT + " INETGER, " +
                    PEGS_PER_YEAR + " INTEGER, " +
                    PEGS_SELECTED_GAP + " INTEGER, " +
                    LAST_DRINK_YEAR + " TEXT , " +
                    STRESS_LEVEL + " TEXT, " +
                    SMOKER_TYPE + " TEXT, " +
                    STICK_COUNT + " INTEGER, " +
                    STICK_PER_YEAR + " INTEGER , " +
                    STICKS_SELECTED_GAP + " INTEGER , " +
                    LAST_SMOKING_YEAR + " TEXT, " +
                    SEXUALLY_ACTIVE + " TEXT, " +
                    LIFE_STYLE + " TEXT, " +
                    LACATOSE_TOLERANCE + " TEXT, " +
                    FOOD_PREFERENCE + " TEXT, " +
                    FOOD_HABIT + " TEXT, " +
                    EXCERCISE + " TEXT ," +
                    CHEWING_TOBACO + " TEXT , " +
                    CHEWING_TOBACO_OTHER + " TEXT , " +
                    DRUG_CONSUMPTION + " TEXT , " +
                    DRUG_CONSUMPTION_TYPE + " TEXT , " +
                    BINGE_EATING + " TEXT , " +
                    ALLERGIES + " TEXT, " +
                    SLEEP_STATUS + " TEXT, " +
                    MODIFIED_ON + " TEXT, " +
                    IS_DELETED + " TEXT , " +
                    IS_DISABLED + " TEXT, " +
                    DISABLED_BY + " INTEGER, " +
                    DISABLED_ON + " TEXT , " +
                    DELETED_ON + " TEXT ," +
                    DELETED_BY + " INTEGER ," +
                    FLAG + " INTEGER ) ";

    private static final String CREATE_INVESTIGATION_TABLE =
            "CREATE TABLE " + TABLE_INVESTIGATION + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_PATIENT_ID + " INTEGER NOT NULL UNIQUE, " +
                    KEY_VISIT_ID + " INTEGER NOT NULL UNIQUE, " +
                    ECG + " TEXT, " +
                    SUGAR + " INTEGER, " +
                    SUGAR_FASTING + " INTEGER, " +
                    SUGAR_RBS + " INTEGER, " +
                    ACER + " INTEGER, " +
                    PFT + " TEXT, " +
                    HBA1C + " INTEGER, " +
                    SEREM_UREA + " INTEGER, " +
                    LIPID_PROFILE_TC + " INTEGER, " +
                    LIPID_PROFILE_TG + " INTEGER, " +
                    LIPID_PROFILE_LDL + " INTEGER, " +
                    LIPID_PROFILE_VHDL + " INTEGER, " +
                    LIPID_PROFILE_HDL + " INTEGER, " +
                    LIPID_PROFILE_TCH + " INTEGER, " +
                    HB + " INTEGER, " +
                    PLATELET_COUNT + " INTEGER, " +
                    ESR + " INTEGER, " +
                    DCL + " INTEGER, " +
                    DCE + " INTEGER, " +
                    DCN + " INTEGER, " +
                    DCM + " INTEGER , " +
                    DCB + " INTEGER, " +
                    TOTAL_BILIRUBIN + " INTEGER, " +
                    DIRECT_BILIRUBIN + " INTEGER, " +
                    INDIRECT_BILIRUBIN + " INTEGER, " +
                    SGPT + " INTEGER, " +
                    SGOT + " INTEGER, " +
                    GGT + " INTEGER , " +
                    TOTAL_PROTEIN + " INTEGER, " +
                    ALBUMIN + " INTEGER, " +
                    GLOBULIN + " INTEGER, " +
                    AG_RATIO + " INTEGER , " +
                    HDL_LDL_RATIO + " INTEGER, " +
                    URINE_PUC_CELL + " INTEGER, " +
                    URINE_RBC + " INTEGER, " +
                    URINE_CAST + " INTEGER , " +
                    URINE_PROTEIN + " INTEGER, " +
                    URINE_CRYSTAL + " INTEGER, " +
                    MICROALBUMINURIA + " INTEGER , " +
                    SEREM_CREATININE + " INTEGER, " +
                    ACR + " INTEGER, " +
                    TSH + " INTEGER, " +
                    T3 + " INTEGER , " +
                    T4 + " INTEGER, " +
                    DISABLED_BY + " INTEGER, " +
                    DISABLED_ON + " TEXT , " +
                    DELETED_ON + " TEXT ," +
                    DELETED_BY + " INTEGER ," +
                    FLAG + " INTEGER ) ";

    private static final String CREATE_OBSERVATION_TABLE =
            "CREATE TABLE " + TABLE_OBSERVATIONS + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_PATIENT_ID + " INTEGER NOT NULL UNIQUE , " +
                    KEY_VISIT_ID + " INTEGER NOT NULL UNIQUE, " +
                    PALLOR + " TEXT, " +
                    PALLOR_DESCRIPTION + " TEXT, " +
                    CYANOSIS + " TEXT, " +
                    CYANOSIS_DESCRIPTION + " TEXT, " +
                    CLUBBING + " TEXT, " +
                    CLUBBING_DESCRIPTION + " TEXT, " +
                    TREMORS + " TEXT, " +
                    TREMORS_DESCRIPTION + " TEXT, " +
                    ICTERUS + " TEXT, " +
                    ICTERUS_DESCRIPTION + " TEXT, " +
                    OEDEMA + " TEXT, " +
                    OEDEMA_DESCRIPTION + " TEXT, " +
                    CALF_TENDERNERSS + " TEXT, " +
                    CALF_TENDERNERSS_DESCRIPTION + " TEXT, " +
                    LYMPHADNOPATHY + " TEXT, " +
                    LYMPHADNOPATHY_DESCRIPTION + " TEXT, " +
                    ADDED_ON + " TEXT, " +
                    MODIFIED_ON + " TEXT, " +
                    IS_DELETED + " TEXT , " +
                    IS_DISABLED + " TEXT, " +
                    DISABLED_BY + " INTEGER, " +
                    DISABLED_ON + " TEXT , " +
                    DELETED_ON + " TEXT ," +
                    DELETED_BY + " INTEGER ," +
                    FLAG + " INTEGER ) ";


   /* private static String CREATE_TABLE_OCCUPATION = "create table "
            + OCCUPATION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + OCCUPATION_NAME + " TEXT ) ";*/


    static final String CREATE_TABLE_RECORD_IMAGES =
            "CREATE TABLE " + TABLE_RECORD_IMAGES + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_VISIT_ID + " INTEGER NOT NULL , " +
                    KEY_PATIENT_ID + " INTEGER NOT NULL , " +
                    IMAGE_COUNT + " TEXT, " +
                    IMAGE_URL + " TEXT, " +
                    IMAGE_TYPE + " TEXT, " +
                    GLOBAL_IMAGE_URL + " TEXT, " +
                    NEW_DOWNLOAD_IMAGE_NAME + " TEXT, " +
                    NEW_DOWNLOAD_IMAGE_PATH + " TEXT, " +
                    STATUS_UPLOADED + " INTEGER DEFAULT 0, " +
                    IS_IMAGE_DELETED + ", " +
                    ADDED_BY + " TEXT, " +
                    ADDED_ON + " TEXT, " +
                    DELETED_ON + " TEXT ," +
                    DELETED_BY + " INTEGER ," +
                    FLAG + " INTEGER ) ";

    static final String CREATE_TABLE_NOTIFICATIONS =
            "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MESSAGE + " TEXT, " +
                    HEADER + " TEXT, " +
                    TYPE + " INTEGER, " +
                    ACTION_PATH + " TEXT, " +
                    IMAGE_URL + " TEXT, " +
                    ADDED_ON + " TEXT, " +
                    DELETED_ON + " TEXT ," +
                    DELETED_BY + " INTEGER ," +
                    FLAG + " INTEGER ) ";


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    private static final String DATABASE_ALTER_TABLE_USER = "ALTER TABLE user"
            + " ADD COLUMN phonenumber TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_ADDSTATE = "ALTER TABLE patient"
            + " ADD COLUMN patient_state TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_PHONE_TYPE = "ALTER TABLE patient"
            + " ADD COLUMN phone_type TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_ISD_CODE = "ALTER TABLE patient"
            + " ADD COLUMN isd_code TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_OCCUPATION = "ALTER TABLE patient"
            + " ADD COLUMN occupation TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_RELIGION = "ALTER TABLE patient"
            + " ADD COLUMN religion TEXT;";


    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_WEIGHT = " ALTER TABLE patient_history" + " ADD COLUMN weight TEXT;";


    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_PULSE = " ALTER TABLE patient_history" + " ADD COLUMN pulse TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_BP = " ALTER TABLE patient_history" + " ADD COLUMN bp_high TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_MMHG = " ALTER TABLE patient_history" + " ADD COLUMN bp_low TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_TEMP = " ALTER TABLE patient_history" + " ADD COLUMN temperature TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_SUGAR = " ALTER TABLE patient_history" + " ADD COLUMN sugar TEXT;";


    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_SYMPTOMS = " ALTER TABLE patient_history" + " ADD COLUMN symptoms TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_DIGNOSIS = " ALTER TABLE patient_history" + " ADD COLUMN diagnosis TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_TESTS = " ALTER TABLE patient_history" + " ADD COLUMN tests TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_DRUGS = " ALTER TABLE patient_history" + " ADD COLUMN drugs TEXT;";

    private static final String DATABASE_ALTER_TABLE_DOCTORINFO = " ALTER TABLE doctor_perInfo" + " ADD COLUMN company_id TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_WAIST = " ALTER TABLE patient_history" + " ADD COLUMN waist integer;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_OXYGEN_CONSUMPTION = " ALTER TABLE patient_history" + " ADD COLUMN oxygen_consumption integer;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_BLOOD_GROUP = " ALTER TABLE " + TABLE_PATIENT + " ADD COLUMN " + BLOOD_GROUP + " TEXT;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_MARITIAL_STATUS = " ALTER TABLE " + TABLE_PATIENT + " ADD COLUMN " + MARITIAL_STATUS + " TEXT;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_INCOME = " ALTER TABLE " + TABLE_PATIENT + " ADD COLUMN " + INCOME_RANGE + " TEXT;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_FOLLOW_UP = " ALTER TABLE " + TABLE_PATIENT_HISTORY + " ADD COLUMN " + FOLLOW_UP + " TEXT;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_RESPIRATION = " ALTER TABLE " + TABLE_PATIENT_HISTORY + " ADD COLUMN " + RESPIRATION + " integer;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_SPO2 = " ALTER TABLE " + TABLE_PATIENT_HISTORY + " ADD COLUMN " + SPO2 + " integer;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_FOLLOWUP_STATUS = " ALTER TABLE " + TABLE_PATIENT_HISTORY + " ADD COLUMN " + FOLLOW_UP_STATUS + " TEXT;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_FAMILY_HISTORY = " ALTER TABLE " + TABLE_PATIENT + " ADD COLUMN " + FAMILY_HISTORT + " TEXT;";
    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_HOSPI_SURGERY = " ALTER TABLE " + TABLE_PATIENT + " ADD COLUMN " + HOSPITALIZATION_SUREGERY + " TEXT;";

    private static final String DATABASE_ALTER_TABLE_PATIENT_HISTORY_OBESITY = " ALTER TABLE " + TABLE_PATIENT_HISTORY + " ADD COLUMN " + OBESITY + " TEXT;";

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
        // v.1.3.1.6 +
        if (oldVersion < 4) {
            //Removing old tables from  db
            db.execSQL("DROP TABLE IF EXISTS " + ASSOCIATE_TYPE);
            db.execSQL("DROP TABLE IF EXISTS  table_persoplushistorydata");
            db.execSQL("DROP TABLE IF EXISTS  table_MasterAilment");
            db.execSQL("DROP TABLE IF EXISTS  table_LastNames");

            //want to add few new tables and columns
            db.execSQL(CREATE_INVESTIGATION_TABLE);
           // db.execSQL(CREATE_TABLE_OCCUPATION);
            db.execSQL(CREATE_OBSERVATION_TABLE);

            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_OCCUPATION);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_RELIGION);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_WAIST);
            // db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_RESPIRATION_RATE);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_OXYGEN_CONSUMPTION);
            db.execSQL(CREATE_HEALTH_AND_LIFESTYLE_TABLE);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_BLOOD_GROUP);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_MARITIAL_STATUS);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_INCOME);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_FOLLOW_UP);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_RESPIRATION);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_SPO2);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_FOLLOWUP_STATUS);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_FAMILY_HISTORY);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_HOSPI_SURGERY);
            db.execSQL(DATABASE_ALTER_TABLE_PATIENT_HISTORY_OBESITY);
        }
    }

    /**
     * Updating Patient Personal details in database
     */
    public void updatePatientPersonalInfo(String keyid, String firstname, String middlename, String lastname, String gender, String dateofbirth, String age, String phNo, String language, String imgPath, String modified_on_date, String modified_by, String modifiedTime, String action, String flag, String docId,
                                          String address, String cityortown, String district, String pin, String state, String phoneType, String alternatephoneType, String alternatePhoneNumber, String uid, String uidType, String isd_code, String alternateisd_code, String status, String email, String mOccupation, String mReligion, String strFamilyHistory, String strHospiSurgery, String strBloodGroup, String strMaritalStatus, String strIncomeRange, String isAgeCalculated) throws ClirNetAppException {

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
            values.put(OCCUPATION, mOccupation);
            values.put(RELIGION, mReligion);
            values.put(FAMILY_HISTORT, strFamilyHistory);
            values.put(HOSPITALIZATION_SUREGERY, strHospiSurgery);
            values.put(BLOOD_GROUP, strBloodGroup);
            values.put(MARITIAL_STATUS, strMaritalStatus);
            values.put(INCOME_RANGE, strIncomeRange);
            values.put(IS_AGE_CALCULATED, isAgeCalculated);

            // Inserting Row
            db.update(TABLE_PATIENT, values, KEY_PATIENT_ID + "=" + keyid, null);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ClirNetAppException("Error inserting data");
        } finally {
            if (db != null) {
                db.close();    // Closing database connection.
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
    public void updatePatientOtherInfo(String strId, String visitId, String usersellectedDate, String daysSel, String fowSel, String monthSel, String clinical_note, String patientImagePath, String modified_dateon, String modified_time, String modified_by, String action, String patInfoType, String flag,
                                       String weight, String pulse, String bphigh, String bplow, String temparature, String symptoms, String dignosis, String strHeight, String strbmi, String visit_date, String referedBy, String referedTo, String strSpo2, String strRespiration, String strObesity, String strFollowUpStatus) throws ClirNetAppException {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(ACTUAL_FOLLOW_UP_DATE, usersellectedDate); // Name

            values.put(DAYS, daysSel); // Email
            values.put(WEEKS, fowSel); // Email
            values.put(MONTHS, monthSel); // Created At
            values.put(CLINICAL_NOTES, clinical_note);
            values.put(PRESCRIPTION, patientImagePath);
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

            values.put(SYMPTOMS, symptoms);
            values.put(DIGNOSIS, dignosis);

            values.put(HEIGHT, strHeight);
            values.put(BMI, strbmi);

            values.put(VISIT_DATE, visit_date);

            values.put(REFERED_BY, referedBy);
            values.put(REFERED_TO, referedTo);
            values.put(SPO2, strSpo2);
            values.put(RESPIRATION, strRespiration);
            values.put(OBESITY, strObesity);
            values.put(FOLLOW_UP_STATUS, strFollowUpStatus);
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

    //this is used to add the patient prsonal info  from server int db
    public void addPatientPersoanlRecords(String pat_id, String doctor_id, String doc_membership_id, String patient_info_type_form,
                                          String pat_first_name, String pat_middle_name, String pat_last_name, String pat_gender,
                                          String pat_date_of_birth, String pat_age, String pat_mobile_no, String pat_address,
                                          String pat_city_town, String pat_pincode, String pat_district, String pref_lang,
                                          String photo_name, String consent, String special_instruction, String added_by,
                                          String added_on, String addedTime, String modified_by, String modified_on,
                                          String is_disabled, String disabled_by, String disabled_on, String is_deleted,
                                          String deleted_by, String deleted_on, String flag, String status, String email, String phoneType, String isdCode, String alternateNumber, String alternatePhoneType, String uid, String uidType, String alternateNoIsd) {

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

            contentValue.put(KEY_EMAIL, email);
            contentValue.put(PHONE_TYPE, phoneType);
            contentValue.put(ISD_CODE, isdCode);
            contentValue.put(ALTERNATE_PHONE_NO, alternateNumber);
            contentValue.put(ALTERNATE_PHONE_TYPE, alternatePhoneType);
            contentValue.put(ATERNATE_NO_ISD_CODE, alternateNoIsd);
            contentValue.put(UID, uid);
            contentValue.put(UIDTYPE, uidType);
            contentValue.put(STATUS, status);

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
                                         String precription, String weight, String pulse, String bphigh, String bplow, String temparature, String sugar, String symptoms, String dignosis, String tests, String drugs, String doc_mem_id, String doc_id,
                                         String height, String bmi, String recordSource, String sugarFast, String referredBy, String referredTo) {

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

            contentValue.put(HEIGHT, height);
            contentValue.put(BMI, bmi);
            contentValue.put(RECORD_SOURCE, recordSource);
            contentValue.put(SUGAR_FASTING, sugarFast);
            contentValue.put(REFERED_BY, referredBy);
            contentValue.put(REFERED_TO, referredTo);

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
    public void addPatientPersonalfromLocal(@NonNull int patient_id, @NonNull String doctor_id, @NonNull String first_name, String middle_name, @NonNull String last_name, String sex, String strdate_of_birth, String current_age, String phone_number, String selectedLanguage, String patientImagePath, String create_date, String doctor_membership_number, String flag, String patientInfoType, String addedTime, String added_by, String action,
                                            String address, String city, String district, String pinno, String state, String phoneType, String alternatePhone_no, String selectedPhoneTypealternate_no, String uid, String uidType, String selectedIsd_codeType, String alternate_no_isdcode, String email, String mOccupation, String mReligion, String strFamilyHistory, String strHospitalizationSurgery, String boloodGroup, String maritalStatus, String incomeRange, int sendSmsFlag, int days_dob, int month_dob, int year_dob) {

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
            values.put(OCCUPATION, mOccupation);
            values.put(RELIGION, mReligion);
            values.put(FAMILY_HISTORT, strFamilyHistory);
            values.put(HOSPITALIZATION_SUREGERY, strHospitalizationSurgery);
            values.put(BLOOD_GROUP, boloodGroup);
            values.put(MARITIAL_STATUS, maritalStatus);
            values.put(INCOME_RANGE, incomeRange);
            values.put(CONSENT, sendSmsFlag);

            values.put(DAYS_DOB, days_dob);
            values.put(MONTH_DOB, month_dob);
            values.put(YEAR_DOB, year_dob);


            // Inserting Row
            // db.insert(TABLE_PATIENT, null, values);
            db.insertWithOnConflict(TABLE_PATIENT, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
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


            db.insert(TABLE_PATIENT_HISTORY, null, values);
            //  Log.e("id"," "+id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //add  new patient records into db from registration page
    public void addHistoryPatientRecords(int visit_id, int patient_id, String usersellectedDate, String daysSel, String fowSel, String monthSel, String prescriptionImgPath, String clinical_note, String added_on_date, String visit_date, String doc_id, String doc_mem_id, String flag, String addedTime, String patientInfoType, String added_by, String action,
                                         String weight, String pulse, String bphigh, String bplow, String temparature, String symptoms, String dignosis, String strHeight, String bmi, String referedBy, String referedto, String rec_source, String strSpo2, String strRespirationRate, String obesity) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_VISIT_ID, visit_id);
            values.put(KEY_PATIENT_ID, patient_id);
            values.put(ACTUAL_FOLLOW_UP_DATE, usersellectedDate);

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

            values.put(SYMPTOMS, symptoms);
            values.put(DIGNOSIS, dignosis);

            values.put(HEIGHT, strHeight);
            values.put(BMI, bmi);
            values.put(REFERED_BY, referedBy);
            values.put(REFERED_TO, referedto);
            values.put(RECORD_SOURCE, rec_source);
            values.put(SPO2, strSpo2);
            values.put(RESPIRATION, strRespirationRate);
            values.put(OBESITY, obesity);

            db.insert(TABLE_PATIENT_HISTORY, null, values);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //add patient records from add pateint update page
    public void addPatientNextVisitRecord(@NonNull String visit_id, @NonNull String strPatientId, String actualFod, String daysSel, String fowSel, String monthSel, String clinical_note, String prescriptionimgPath, String visit_date, String doc_id, String doc_mem_id, String addedOnDate, String addedTime, String flag, String added_by, String action, String patInfoType,
                                          String weight, String pulse, String bp, String mmhg, String temparature, String symptoms, String dignosis, String strHeight, String strbmi, String referedBy, String referedTo, String strFollowUpStatus, String rec_source, String strSpo2, String strRespirationRate, String strObesity) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            values.put(KEY_VISIT_ID, visit_id);
            values.put(KEY_PATIENT_ID, strPatientId);
            values.put(ACTUAL_FOLLOW_UP_DATE, actualFod);
            values.put(DAYS, daysSel);
            values.put(WEEKS, fowSel);
            values.put(MONTHS, monthSel); // Created At
            values.put(CLINICAL_NOTES, clinical_note);
            values.put(PRESCRIPTION, prescriptionimgPath);
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
            values.put(SYMPTOMS, symptoms);
            values.put(DIGNOSIS, dignosis);
            values.put(HEIGHT, strHeight);
            values.put(BMI, strbmi);
            values.put(REFERED_BY, referedBy);
            values.put(REFERED_TO, referedTo);
            values.put(RECORD_SOURCE, rec_source);
            values.put(FOLLOW_UP_STATUS, strFollowUpStatus);
            values.put(SPO2, strSpo2);
            values.put(RESPIRATION, strRespirationRate);
            values.put(OBESITY, strObesity);


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
           /* if (db1 != null) {
                db1.close();
            }*/
        }
    }

    //this will give u a json array of patient records where flag =0
    public JSONArray getResultsForPatientHistory() {
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = null;
        JSONArray resultSet = new JSONArray();
        try {
            String searchQuery = "SELECT  * FROM patient_history where flag = 0 ";
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
                            //  Log.d("TAG_NAME", e.getMessage());
                        }

                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            //return resultSet;
        } catch (Exception e) {
            Crashlytics.logException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
           /* if (db1 != null) {
                db1.close();
            }*/
        }
        return resultSet;
    }

    //this will give u a json array of patient records where flag =0
    public JSONArray getResultsForInvestigation() {

        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = null;
        JSONArray resultSet = new JSONArray();

        try {
            String searchQuery = "SELECT  * FROM table_investigation where flag = 0 ";
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
                            //  Log.d("TAG_NAME", e.getMessage());
                        }
                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            return resultSet;
        } catch (Exception e) {
            Crashlytics.logException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            /*if (db1 != null) {
                db1.close();
            }*/
        }
        return resultSet;
    }

    //this will give u a json array of patient records where flag =0
    public JSONArray getResultsForHealthAndLifeStyle() {

        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = null;
        JSONArray resultSet = new JSONArray();

        try {
            String searchQuery = "SELECT  * FROM health_and_lifestyle where flag = 0  ";
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
                            //  Log.d("TAG_NAME", e.getMessage());
                        }

                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            return resultSet;
        } catch (Exception e) {
            Crashlytics.logException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            /*if (db1 != null) {
                db1.close();
            }*/
        }
        return resultSet;
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

    //update the flag once data send to server successfully
    public void FlagupdateInvestigation(String strPatientId, String flag) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            // Inserting Row
            db.update(TABLE_INVESTIGATION, values, KEY_PATIENT_ID + "=" + strPatientId, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //update the flag once data send to server successfully
    public void FlagupdateHealthAndLifestyle(String strPatientId, String flag) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            // Inserting Row
            db.update(TABLE_HEALTH_AND_LIFESTYLE, values, KEY_PATIENT_ID + "=" + strPatientId, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //update the flag once data send to server successfully
    public void FlagUpdateObservation(String strPatientId, String flag) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(SYCHRONIZED, flag); // Name

            // Inserting Row
            db.update(TABLE_OBSERVATIONS, values, KEY_PATIENT_ID + "=" + strPatientId, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
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
            values.put("created_at", new AppController().getCurrentDate());
            //this will update record if exist else create new record 02-03-2017

            // db.insertWithOnConflict(TABLE_DOCTORINFO, DOCTOR_ID, values, SQLiteDatabase.CONFLICT_REPLACE);

            int u = db.update(TABLE_DOCTORINFO, values, "doctor_id=?", new String[]{doc_id});
            if (u == 0) {
                db.insertWithOnConflict(TABLE_DOCTORINFO, DOCTOR_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
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
            Log.e("addAsync", "addAsync true");
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
        //or you can use `conTEXT.getDatabasePath("my_db_test.db")`
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
            /*if (db1 != null) {
                db1.close();
            }*/
        }
    }

    //this will give u a json array of patient records where flag =o
    public JSONArray getResultsForBannerClicked() {

        JSONArray resultSet;
        //or you can use `conTEXT.getDatabasePath("my_db_test.db")`
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

            db.insertWithOnConflict("asynctascrun_status_new", process, values, SQLiteDatabase.CONFLICT_REPLACE);

           /* boolean isProcessExistsboolean = isProcessExists(process);

            if (!isProcessExistsboolean) {
                db.insert("asynctascrun_status", null, values);
            } else {

                db.update("asynctascrun_status", values, "process=?", new String[]{process});
            }*/

        } catch (Exception e) {
            e.printStackTrace();


        } finally {
           /* if (db != null && db.isOpen()) {
                db.close();
            }*/
        }
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
            //close();

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


    public void addAssociates(String added_by, String mCounter, String strname, String strmob_no, String selectedPhoneType, String selectedIsd_codeType, String strEmail, String selectedSpeciality, String selectedAssociateType, String straddress, String strcity, String selectedState, String strpin, String strdistrict, String dateTimenew, String flag, String nameTitle, String contactForPatient, String selectedcontactForPatientType, String selectedIsd_code_altType) {
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
        //or you can use `conTEXT.getDatabasePath("my_db_test.db")`
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
         /*   if (db1 != null) {
                db1.close();
            }*/
        }
    }

    //this will give u a json array of patient records where flag =o
    public JSONArray getObservationsDataDisplay() {

        JSONArray resultSet;
        //or you can use `conTEXT.getDatabasePath("my_db_test.db")`
        Cursor cursor = null;

        SQLiteDatabase db1 = getReadableDatabase();
        //Cursor cursor = db1.rawQuery(selectQuery, null);

        //SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);'
        try {
            resultSet = new JSONArray();
            String searchQuery = "SELECT  * FROM observation where flag = 0 ";

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
          /*  if (db1 != null) {
                db1.close();
            }*/
        }
    }

    public void updateAssociates(String uid, String modified_by, String modiedCounter, String strname, String strmob_no, String selectedPhoneType, String selectedIsd_codeType, String strEmail, String selectedSpeciality, String selectedAssociateType, String straddress, String strcity, String selectedState, String strpin, String strdistrict, String dateTimeddmmyyyy, String flag, String nameTitle, String contactForPatient, String selectedIsd_code_altType, String selectedcontactForPatientType) throws ClirNetAppException {

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
        String selectQuery = "select banner_id,banner_image1 from company_banners where status_name= 'Active'";
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
            throw new ClirNetAppException("Something went wrong while geting getAllCompanyBanner " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
           /* if (database != null) {
                database.close();
            }*/
        }
        return bannerList;
    }

    public ArrayList<String> getAllMasterSessionIds() throws ClirNetAppException {

        ArrayList<String> msList;
        msList = new ArrayList<>();
        String selectQuery = "select ms_id from recent_data_msession ";
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = this.getWritableDatabase();

            cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    msList.add(cursor.getString(0));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getAllMasterSessionIds " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return msList;
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

    //update the flag once data send to server successfully
    public void FlagupdateAssociateMaster(String flag) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(FLAG, flag); // Name

            db.update(TABLE_ASSOCIATE_MASTER, values, null, null);

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
            db.insert(TABLE_SYNC_STATUS, null, values);
            // Log.e("Valuesis",""+id);

        } catch (Exception e) {
            e.printStackTrace();
        }   /*finally {
          if (db != null) {
                db.close();
            }
        }*/
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
            throw new ClirNetAppException("Something went wrong while geting getReferals");

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
        ArrayList<String> referalsList;
        referalsList = new ArrayList<>();
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
                    referalsList.add(nameAlias + " " + cursor.getString(1));

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
        return referalsList;
    }

    public void addHealthAndLifestyle(String visit_id, String strPatient_id, String strSmoking, String strNoOfSticks, String strTobaco, String strAlcoholConsumption, String strPackPerWeek, String strLifeStyle, String strStressLevel, String strExcercise, String strBingeEating, String strSexuallyActive, String strFoodHabit, String strSugarFasting, String strSugar, String strHbA1c, String strEcg, String strPft, String strSerumUrea, String strAcer, String strAllergies, String strFoodPreference, String strLactoseTolerance, String strLipidTC, String strLipidTG, String strLipidLDL, String strLipidVHDL, String strLipidHDL, String strFlag) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();


            ContentValues contentValue = new ContentValues();

            contentValue.put(KEY_PATIENT_ID, strPatient_id);
            contentValue.put(SMOKER_TYPE, strSmoking);
            contentValue.put(STICK_COUNT, strNoOfSticks);
            contentValue.put(ALCOHOL_CONSUMPTION, strAlcoholConsumption);
            contentValue.put(PEGS_PER_YEAR, strPackPerWeek);
            contentValue.put(CHEWING_TOBACO, strTobaco);
            contentValue.put(LIFE_STYLE, strLifeStyle);
            contentValue.put(STRESS_LEVEL, strStressLevel);

            contentValue.put(EXCERCISE, strExcercise);
            contentValue.put(BINGE_EATING, strBingeEating);
            contentValue.put(SEXUALLY_ACTIVE, strSexuallyActive);
            contentValue.put(FOOD_HABIT, strFoodHabit);
            contentValue.put(FOOD_PREFERENCE, strFoodPreference);
            contentValue.put(ALLERGIES, strAllergies);
            contentValue.put(LACATOSE_TOLERANCE, strLactoseTolerance);
            contentValue.put(FLAG, strFlag);

            // Inserting Row
            db.insert(TABLE_HEALTH_AND_LIFESTYLE, null, contentValue);

                    /*Inserting values to Investgation Table*/
            ContentValues cv = new ContentValues();
            cv.put(KEY_VISIT_ID, visit_id);
            cv.put(KEY_PATIENT_ID, strPatient_id);
            cv.put(ECG, strEcg);
            cv.put(HBA1C, strHbA1c);
            cv.put(SEREM_UREA, strSerumUrea);
            cv.put(ACER, strAcer);
            cv.put(PFT, strPft);
            cv.put(SUGAR_FASTING, strSugarFasting);
            cv.put(SUGAR, strSugar);
            cv.put(FLAG, strFlag);
            cv.put(LIPID_PROFILE_TC, strLipidTC);
            cv.put(LIPID_PROFILE_TG, strLipidTG);
            cv.put(LIPID_PROFILE_LDL, strLipidLDL);
            cv.put(LIPID_PROFILE_VHDL, strLipidVHDL);
            cv.put(LIPID_PROFILE_HDL, strLipidHDL);

            db.insert(TABLE_INVESTIGATION, null, cv);


            // Log.e("Valuesis",""+id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void updateInvestigation(@NonNull String strPatientId, @NonNull String strVisitId, String strSugar, String strSugarFasting, String strHbA1c, String strAcer, String strSerumUrea, String strLipidHDL, String strLipidTC, String strLipidTG, String strLipidLDL, String strLipidVHDL, String strEcg, String strPft, String strFlag) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(KEY_PATIENT_ID, strPatientId);
            cv.put(KEY_VISIT_ID, strVisitId);
            cv.put(ECG, strEcg);
            cv.put(HBA1C, strHbA1c);
            cv.put(SEREM_UREA, strSerumUrea);
            cv.put(ACER, strAcer);
            cv.put(PFT, strPft);
            cv.put(SUGAR_FASTING, strSugarFasting);
            cv.put(SUGAR, strSugar);
            cv.put(LIPID_PROFILE_TC, strLipidTC);
            cv.put(LIPID_PROFILE_TG, strLipidTG);
            cv.put(LIPID_PROFILE_LDL, strLipidLDL);
            cv.put(LIPID_PROFILE_VHDL, strLipidVHDL);
            cv.put(LIPID_PROFILE_HDL, strLipidHDL);
            cv.put(FLAG, strFlag);

            db.update(TABLE_INVESTIGATION, cv, KEY_VISIT_ID + "=" + strVisitId, null);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void updateInvestigation(@NonNull String strPatientId, @NonNull String strVisitId, String strSugar, String strSugarFasting, String strHbA1c, String strAcer, String strSerumUrea, String strLipidHDL, String strLipidTC, String strTch, String strLipidTG, String strLipidLDL, String strLipidVHDL, String strEcg, String strPft, String strHb, String strPlateletCount, String strEsr, String strDcl, String strDcn, String strDce, String strDcm, String strDcb, String strTotalBiliubin, String strDirectBilirubin, String strIndirectBilirubin, String strSgpt, String strSgot, String strGgt, String strTotalProtein, String strAlbumin, String strGlobulin, String strAgRatio, String strLdlHdlRatio, String strSugarRbs, String strUrinaryPusCell, String strUrineRbc, String strUrinaryCast, String strUrineProtein, String strUrineCrystal, String strMicroalbuminuria, String strSerumCreatinine, String strAcr, String strTsh, String strT3, String strT4, String strFlag) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(KEY_PATIENT_ID, strPatientId);
            cv.put(KEY_VISIT_ID, strVisitId);
            cv.put(ECG, strEcg);
            cv.put(HBA1C, strHbA1c);
            cv.put(SEREM_UREA, strSerumUrea);
            cv.put(ACER, strAcer);
            cv.put(PFT, strPft);
            cv.put(SUGAR_FASTING, strSugarFasting);
            cv.put(SUGAR, strSugar);
            cv.put(LIPID_PROFILE_TC, strLipidTC);
            cv.put(LIPID_PROFILE_TG, strLipidTG);
            cv.put(LIPID_PROFILE_LDL, strLipidLDL);
            cv.put(LIPID_PROFILE_VHDL, strLipidVHDL);
            cv.put(LIPID_PROFILE_HDL, strLipidHDL);
            cv.put(LIPID_PROFILE_TCH, strTch);
            cv.put(HB, strHb);
            cv.put(PLATELET_COUNT, strPlateletCount);
            cv.put(ESR, strEsr);
            cv.put(DCL, strDcl);
            cv.put(DCN, strDcn);
            cv.put(DCE, strDce);
            cv.put(DCM, strDcm);
            cv.put(DCB, strDcb);
            cv.put(TOTAL_BILIRUBIN, strTotalBiliubin);
            cv.put(DIRECT_BILIRUBIN, strDirectBilirubin);
            cv.put(INDIRECT_BILIRUBIN, strIndirectBilirubin);
            cv.put(SGPT, strSgpt);
            cv.put(SGOT, strSgot);
            cv.put(GGT, strGgt);
            cv.put(TOTAL_PROTEIN, strTotalProtein);
            cv.put(ALBUMIN, strAlbumin);
            cv.put(GLOBULIN, strGlobulin);
            cv.put(AG_RATIO, strAgRatio);
            cv.put(HDL_LDL_RATIO, strLdlHdlRatio);
            cv.put(SUGAR_RBS, strSugarRbs);
            cv.put(URINE_PUC_CELL, strUrinaryPusCell);
            cv.put(URINE_RBC, strUrineRbc);
            cv.put(URINE_CAST, strUrinaryCast);
            cv.put(URINE_PROTEIN, strUrineProtein);
            cv.put(URINE_CRYSTAL, strUrineCrystal);
            cv.put(MICROALBUMINURIA, strMicroalbuminuria);
            cv.put(SEREM_CREATININE, strSerumCreatinine);
            cv.put(ACR, strAcr);
            cv.put(URINE_CRYSTAL, strUrineCrystal);
            cv.put(TSH, strTsh);
            cv.put(T3, strT3);
            cv.put(T4, strT4);
            cv.put(FLAG, strFlag);

            db.update(TABLE_INVESTIGATION, cv, KEY_VISIT_ID + "=" + strVisitId, null);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }


    public void addInvestigation(@NonNull String strPatientId, @NonNull String strVisitId, String strSugar, String strSugarFasting, String strHbA1c, String strAcer, String strSerumUrea, String strLipidHDL, String strLipidTC, String strLipidTG, String strLipidLDL, String strLipidVHDL, String strEcg, String strPft, String strFlag) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(KEY_PATIENT_ID, strPatientId);
            cv.put(KEY_VISIT_ID, strVisitId);
            cv.put(ECG, strEcg);
            cv.put(HBA1C, strHbA1c);
            cv.put(SEREM_UREA, strSerumUrea);
            cv.put(ACER, strAcer);
            cv.put(PFT, strPft);
            cv.put(SUGAR_FASTING, strSugarFasting);
            cv.put(SUGAR, strSugar);
            cv.put(LIPID_PROFILE_TC, strLipidTC);
            cv.put(LIPID_PROFILE_TG, strLipidTG);
            cv.put(LIPID_PROFILE_LDL, strLipidLDL);
            cv.put(LIPID_PROFILE_VHDL, strLipidVHDL);
            cv.put(LIPID_PROFILE_HDL, strLipidHDL);
            cv.put(FLAG, strFlag);

            db.insertWithOnConflict(TABLE_INVESTIGATION, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            // db.insert(TABLE_INVESTIGATION, null, cv);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void addInvestigation(@NonNull String strPatientId, @NonNull String strVisitId, String strSugar, String strSugarFasting, String strHbA1c, String strAcer, String strSerumUrea, String strLipidHDL, String strLipidTC, String strTch, String strLipidTG, String strLipidLDL, String strLipidVHDL, String strEcg, String strPft, String strHb, String strPlateletCount, String strEsr, String strDcl, String strDcn, String strDce, String strDcm, String strDcb, String strTotalBiliubin, String strDirectBilirubin, String strIndirectBilirubin, String strSgpt, String strSgot, String strGgt, String strTotalProtein, String strAlbumin, String strGlobulin, String strAgRatio, String strLdlHdlRatio, String strSugarRbs, String strUrinaryPusCell, String strUrineRbc, String strUrinaryCast, String strUrineProtein, String strUrineCrystal, String strMicroalbuminuria, String strSerumCreatinine, String strAcr, String strTsh, String strT3, String strT4, String strFlag) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(KEY_PATIENT_ID, strPatientId);
            cv.put(KEY_VISIT_ID, strVisitId);
            cv.put(ECG, strEcg);
            cv.put(HBA1C, strHbA1c);
            cv.put(SEREM_UREA, strSerumUrea);
            cv.put(ACER, strAcer);
            cv.put(PFT, strPft);
            cv.put(SUGAR_FASTING, strSugarFasting);
            cv.put(SUGAR, strSugar);
            cv.put(LIPID_PROFILE_TC, strLipidTC);
            cv.put(LIPID_PROFILE_TG, strLipidTG);
            cv.put(LIPID_PROFILE_LDL, strLipidLDL);
            cv.put(LIPID_PROFILE_VHDL, strLipidVHDL);
            cv.put(LIPID_PROFILE_HDL, strLipidHDL);
            cv.put(LIPID_PROFILE_TCH, strTch);
            cv.put(HB, strHb);
            cv.put(PLATELET_COUNT, strPlateletCount);
            cv.put(ESR, strEsr);
            cv.put(DCL, strDcl);
            cv.put(DCN, strDcn);
            cv.put(DCE, strDce);
            cv.put(DCM, strDcm);
            cv.put(DCB, strDcb);
            cv.put(TOTAL_BILIRUBIN, strTotalBiliubin);
            cv.put(DIRECT_BILIRUBIN, strDirectBilirubin);
            cv.put(INDIRECT_BILIRUBIN, strIndirectBilirubin);
            cv.put(SGPT, strSgpt);
            cv.put(SGOT, strSgot);
            cv.put(GGT, strGgt);
            cv.put(TOTAL_PROTEIN, strTotalProtein);
            cv.put(ALBUMIN, strAlbumin);
            cv.put(GLOBULIN, strGlobulin);
            cv.put(AG_RATIO, strAgRatio);
            cv.put(HDL_LDL_RATIO, strLdlHdlRatio);
            cv.put(SUGAR_RBS, strSugarRbs);
            cv.put(URINE_PUC_CELL, strUrinaryPusCell);
            cv.put(URINE_RBC, strUrineRbc);
            cv.put(URINE_CAST, strUrinaryCast);
            cv.put(URINE_PROTEIN, strUrineProtein);
            cv.put(URINE_CRYSTAL, strUrineCrystal);
            cv.put(MICROALBUMINURIA, strMicroalbuminuria);
            cv.put(SEREM_CREATININE, strSerumCreatinine);
            cv.put(ACR, strAcr);
            cv.put(URINE_CRYSTAL, strUrineCrystal);
            cv.put(TSH, strTsh);
            cv.put(T3, strT3);
            cv.put(T4, strT4);
            cv.put(FLAG, strFlag);

            db.insertWithOnConflict(TABLE_INVESTIGATION, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void addHealthAndLifestyle(@NonNull String strPatientId, String strSmoking, String strNoOfSticks, String strsDaysSelectedSmoking, String noOfSticksPerYear, String strLeftSmokingSinceYear, String strAlcoholConsumption, String strNoOfPegs, String strsDaysSelectedDrinking, String noOfPegsPerYear, String strLeftAlcoholSinceYear, String strTobacoStaus, String otherTobacoTaking, String strDrug, String otherDrugTaking, String strFoodHabit, String strFoodPreference, String strBingeEating, String strLactoseTolerance, String strLifeStyle, String strSleepStatus, String strStressLevel, String strSexuallyActive, String strExcercise, String strAllergie, String strFlag) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues contentValue = new ContentValues();

            contentValue.put(KEY_PATIENT_ID, strPatientId);
            contentValue.put(SMOKER_TYPE, strSmoking);
            contentValue.put(STICK_COUNT, strNoOfSticks);
            contentValue.put(STICK_PER_YEAR, noOfSticksPerYear);
            contentValue.put(STICKS_SELECTED_GAP, strsDaysSelectedSmoking);
            contentValue.put(LAST_SMOKING_YEAR, strLeftSmokingSinceYear);

            contentValue.put(ALCOHOL_CONSUMPTION, strAlcoholConsumption);
            contentValue.put(PEGS_COUNT, strNoOfPegs);
            contentValue.put(PEGS_PER_YEAR, noOfPegsPerYear);
            contentValue.put(PEGS_SELECTED_GAP, strsDaysSelectedDrinking);
            contentValue.put(LAST_DRINK_YEAR, strLeftAlcoholSinceYear);
            contentValue.put(CHEWING_TOBACO, strTobacoStaus);
            contentValue.put(CHEWING_TOBACO_OTHER, otherTobacoTaking);
            contentValue.put(DRUG_CONSUMPTION, strDrug);
            contentValue.put(DRUG_CONSUMPTION_TYPE, otherDrugTaking);
            contentValue.put(FOOD_HABIT, strFoodHabit);
            contentValue.put(FOOD_PREFERENCE, strFoodPreference);
            contentValue.put(BINGE_EATING, strBingeEating);
            contentValue.put(LACATOSE_TOLERANCE, strLactoseTolerance);
            contentValue.put(LIFE_STYLE, strLifeStyle);
            contentValue.put(SLEEP_STATUS, strSleepStatus);
            contentValue.put(STRESS_LEVEL, strStressLevel);
            contentValue.put(SEXUALLY_ACTIVE, strSexuallyActive);
            contentValue.put(EXCERCISE, strExcercise);
            contentValue.put(ALLERGIES, strAllergie);
            contentValue.put(FLAG, strFlag);

            //db.insertWithOnConflict(TABLE_HEALTH_AND_LIFESTYLE, null,contentValue);
            db.insertWithOnConflict(TABLE_HEALTH_AND_LIFESTYLE, null, contentValue, SQLiteDatabase.CONFLICT_REPLACE);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (db != null) {
                db.close();
            }
        }
    }

    public void updateHealthAndLifestyle(@NonNull String strPatientId, String strSmoking, String strNoOfSticks, String strsDaysSelectedSmoking, String noOfSticksPerYear, String strLeftSmokingSinceYear, String strAlcoholConsumption, String strNoOfPegs, String strsDaysSelectedDrinking, String noOfPegsPerYear, String strLeftAlcoholSinceYear, String strTobacoStaus, String otherTobacoTaking, String strDrug, String otherDrugTaking, String strFoodHabit, String strFoodPreference, String strBingeEating, String strLactoseTolerance, String strLifeStyle, String strSleepStatus, String strStressLevel, String strSexuallyActive, String strExcercise, String strAllergie, String strFlag) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues contentValue = new ContentValues();

            contentValue.put(KEY_PATIENT_ID, strPatientId);
            contentValue.put(SMOKER_TYPE, strSmoking);
            contentValue.put(STICK_COUNT, strNoOfSticks);
            contentValue.put(STICK_PER_YEAR, noOfSticksPerYear);
            contentValue.put(STICKS_SELECTED_GAP, strsDaysSelectedSmoking);
            contentValue.put(LAST_SMOKING_YEAR, strLeftSmokingSinceYear);

            contentValue.put(ALCOHOL_CONSUMPTION, strAlcoholConsumption);
            contentValue.put(PEGS_COUNT, strNoOfPegs);
            contentValue.put(PEGS_PER_YEAR, noOfPegsPerYear);
            contentValue.put(PEGS_SELECTED_GAP, strsDaysSelectedDrinking);
            contentValue.put(LAST_DRINK_YEAR, strLeftAlcoholSinceYear);
            contentValue.put(CHEWING_TOBACO, strTobacoStaus);
            contentValue.put(CHEWING_TOBACO_OTHER, otherTobacoTaking);
            contentValue.put(DRUG_CONSUMPTION, strDrug);
            contentValue.put(DRUG_CONSUMPTION_TYPE, otherDrugTaking);
            contentValue.put(FOOD_HABIT, strFoodHabit);
            contentValue.put(FOOD_PREFERENCE, strFoodPreference);
            contentValue.put(BINGE_EATING, strBingeEating);
            contentValue.put(LACATOSE_TOLERANCE, strLactoseTolerance);
            contentValue.put(LIFE_STYLE, strLifeStyle);
            contentValue.put(SLEEP_STATUS, strSleepStatus);
            contentValue.put(STRESS_LEVEL, strStressLevel);
            contentValue.put(SEXUALLY_ACTIVE, strSexuallyActive);
            contentValue.put(EXCERCISE, strExcercise);
            contentValue.put(ALLERGIES, strAllergie);
            contentValue.put(FLAG, strFlag);

            // Inserting Row
            db.update(TABLE_HEALTH_AND_LIFESTYLE, contentValue, KEY_PATIENT_ID + "=" + strPatientId, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void addObservations(@NonNull String strPatientId, @NonNull String strVisitId, String strPallore, String strPallorDescription, String strCyanosis, String strCyanosisDescription, String strTremors, String strTremorsDescription, String strIcterus, String strIcterusDescription, String strClubbing, String strClubbingDescription, String strOedema, String strOedemaDescription, String strCalfTenderness, String strCalfTendernessDescription, String strLymphadenopathy, String strLymphadenopathyDescription, String strAddedOn, @NonNull String flag) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues contentValue = new ContentValues();

            contentValue.put(KEY_PATIENT_ID, strPatientId);
            contentValue.put(KEY_VISIT_ID, strVisitId);
            contentValue.put(PALLOR, strPallore);
            contentValue.put(PALLOR_DESCRIPTION, strPallorDescription);
            contentValue.put(CYANOSIS, strCyanosis);
            contentValue.put(CYANOSIS_DESCRIPTION, strCyanosisDescription);
            contentValue.put(TREMORS, strTremors);
            contentValue.put(TREMORS_DESCRIPTION, strTremorsDescription);
            contentValue.put(ICTERUS, strIcterus);
            contentValue.put(ICTERUS_DESCRIPTION, strIcterusDescription);
            contentValue.put(CLUBBING, strClubbing);
            contentValue.put(CLUBBING_DESCRIPTION, strClubbingDescription);
            contentValue.put(OEDEMA, strOedema);
            contentValue.put(OEDEMA_DESCRIPTION, strOedemaDescription);
            contentValue.put(CALF_TENDERNERSS, strCalfTenderness);
            contentValue.put(CALF_TENDERNERSS_DESCRIPTION, strCalfTendernessDescription);
            contentValue.put(LYMPHADNOPATHY, strLymphadenopathy);
            contentValue.put(LYMPHADNOPATHY_DESCRIPTION, strLymphadenopathyDescription);
            contentValue.put(ADDED_ON, strAddedOn);
            contentValue.put(FLAG, flag);

            //db.insertWithOnConflict(TABLE_HEALTH_AND_LIFESTYLE, null,contentValue);
            long id = db.insertWithOnConflict(TABLE_OBSERVATIONS, null, contentValue, SQLiteDatabase.CONFLICT_REPLACE);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (db != null) {
                db.close();
            }
        }
    }

    public void updateObservations(@NonNull String strPatientId, @NonNull String strVisitId, String strPallore, String strPallorDescription, String strCyanosis, String strCyanosisDescription, String strTremors, String strTremorsDescription, String strIcterus, String strIcterusDescription, String strClubbing, String strClubbingDescription, String strOedema, String strOedemaDescription, String strCalfTenderness, String strCalfTendernessDescription, String strLymphadenopathy, String strLymphadenopathyDescription, String strAddedOn, @NonNull String flag) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues contentValue = new ContentValues();

            contentValue.put(KEY_PATIENT_ID, strPatientId);
            contentValue.put(KEY_VISIT_ID, strVisitId);
            contentValue.put(PALLOR, strPallore);
            contentValue.put(PALLOR_DESCRIPTION, strPallorDescription);
            contentValue.put(CYANOSIS, strCyanosis);
            contentValue.put(CYANOSIS_DESCRIPTION, strCyanosisDescription);
            contentValue.put(TREMORS, strTremors);
            contentValue.put(TREMORS_DESCRIPTION, strTremorsDescription);
            contentValue.put(ICTERUS, strIcterus);
            contentValue.put(ICTERUS_DESCRIPTION, strIcterusDescription);
            contentValue.put(CLUBBING, strClubbing);
            contentValue.put(CLUBBING_DESCRIPTION, strClubbingDescription);
            contentValue.put(OEDEMA, strOedema);
            contentValue.put(OEDEMA_DESCRIPTION, strOedemaDescription);
            contentValue.put(CALF_TENDERNERSS, strCalfTenderness);
            contentValue.put(CALF_TENDERNERSS_DESCRIPTION, strCalfTendernessDescription);
            contentValue.put(LYMPHADNOPATHY, strLymphadenopathy);
            contentValue.put(LYMPHADNOPATHY_DESCRIPTION, strLymphadenopathyDescription);
            contentValue.put(ADDED_ON, strAddedOn);
            contentValue.put(FLAG, flag);

            //db.insertWithOnConflict(TABLE_HEALTH_AND_LIFESTYLE, null,contentValue);
            //  long id=db.insertWithOnConflict(TABLE_OBSERVATIONS, null, contentValue, SQLiteDatabase.CONFLICT_REPLACE);
            db.update(TABLE_OBSERVATIONS, contentValue, KEY_VISIT_ID + "=" + strVisitId, null);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (db != null) {
                db.close();
            }
        }
    }

    public void addOccupation(String diagnosis) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", diagnosis);

            db.insert(OCCUPATION, null, values);

        } catch (Exception e) {
            new AppController().appendLog(new AppController().getDateTime() + " " + "/" + " Database " + e);
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //This will add records from registration page into patient table  28/8/2016 ashish u
    public void addRecentDataKcap(String kcId, String kcText, String kcSource, String addedOn) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KC_ID, kcId);
            values.put(KC_TEXT, kcText);
            values.put(KC_SOURCE_NAME, kcSource);
            values.put(ADDED_ON, addedOn);

            // Inserting Row
            db.insertWithOnConflict(RECENT_DATA_KCAP, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //This will add records from registration page into patient table  28/8/2016 ashish u
    public void addRecentDataCompandium(String compId, String compQuestion, String comp_qa_answer, String compUrl, String addedOnDate) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COMP_QA_ID, compId);
            values.put(COMP_QA_QUESTION, compQuestion);
            values.put(COMP_QA_ANSWER, comp_qa_answer);
            values.put(COMP_URL, compUrl);
            values.put(ADDED_ON, addedOnDate);

            // Inserting Row
            db.insertWithOnConflict(RECENT_DATA_COMPANDIUM, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //This will add records from registration page into patient table  28/8/2016 ashish u
    public void addRecentDataMsession(String msId, String msTopic, String msBrief, String msDoctorName, String msDateTime, String msSpecialitiesName, String msCatName, String msUrl, String addedOnDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(MS_ID, msId);
            values.put(MS_TOPIC, msTopic);
            values.put(MS_BRIEF, msBrief);
            values.put(DOCTOR_NAME, msDoctorName);

            values.put(MS_DATE_TIME, msDateTime);
            values.put(MS_SPECIALITY_NAME, msSpecialitiesName);
            values.put(MS_CAT_NAME, msCatName);
            values.put(MS_URL, msUrl);
            values.put(ADDED_ON, addedOnDate);

            // Inserting Row
            db.insertWithOnConflict(RECENT_DATA_MSESSION, msId, values, SQLiteDatabase.CONFLICT_REPLACE);

            // db.insert(RECENT_DATA_MSESSION, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //add addNotifications  into db
    public void addNotifications(String header, String message, String actionPath, String type, String imageUrl, String notifDate) throws ClirNetAppException {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValue = new ContentValues();

            contentValue.put(MESSAGE, message);
            contentValue.put(HEADER, header);
            contentValue.put(ACTION_PATH, actionPath);
            contentValue.put(TYPE, type);
            contentValue.put(IMAGE_URL, imageUrl);
            contentValue.put(ADDED_ON, notifDate);
            contentValue.put(FLAG, "0");

            db.insert(TABLE_NOTIFICATIONS, null, contentValue);
            //Log.e("notification","notification added");

        } catch (Exception e) {
            throw new ClirNetAppException("Error inserting Notifications data");
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }

    }

    public void deleteMasterSessions(String id) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(RECENT_DATA_MSESSION, "ms_id = ?", new String[]{id});

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Log.e("notiDeleted"," id is :  "+id);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    //update the flag once data send to server successfully
    public void updateDiagnosisData() {

        SQLiteDatabase db = null;
        try {

            db = this.getWritableDatabase();

            db.execSQL("UPDATE diagnosis SET diagnosis_name = replace( diagnosis_name, ',', '-' ) ");
            db.execSQL("UPDATE diagnosis SET diagnosis_name = replace( diagnosis_name, '(', '( ' ) ");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addImages(String patId, String visitId, String ImageUrl) throws ClirNetAppException {

        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues contentValue = new ContentValues();

            contentValue.put(KEY_PATIENT_ID, patId);
            contentValue.put(KEY_VISIT_ID, visitId);
            contentValue.put(IMAGE_URL, ImageUrl);

            Log.e("visitId",""+visitId);

            db.insert(TABLE_RECORD_IMAGES, null, contentValue);
            //Log.e("notification","notification added");

        } catch (Exception e) {
            throw new ClirNetAppException("Error inserting Notifications data");
        } finally {
            if (db != null) {
                db.close(); // Closing database connection
            }
        }
    }


}

