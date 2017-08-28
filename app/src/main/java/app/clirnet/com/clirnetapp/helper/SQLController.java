package app.clirnet.com.clirnetapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import app.clirnet.com.clirnetapp.models.Counts;
import app.clirnet.com.clirnetapp.models.LoginModel;
import app.clirnet.com.clirnetapp.models.RegistrationModel;
import app.clirnet.com.clirnetapp.reports.GenderWiseDataModel;

//this class is most imp class which used to query the database
public class SQLController {


    private final Context ourcontext;
    private SQLiteHandler dbHelper;
    private SQLiteDatabase database;


    public SQLController(Context c) {
        ourcontext = c;
    }

    public synchronized SQLController open() throws SQLException {

        if (dbHelper == null) {
          dbHelper=new SQLiteHandler(ourcontext);
         // dbHelper =  new SQLiteHandler.getInstance(ourcontext);
            database = dbHelper.getWritableDatabase();
        }
        return this;

    }
    public void close() {

        if (dbHelper != null) {
            dbHelper.close();
            database.close();
        }
    }

    //method to fetch user name and password
    public ArrayList<LoginModel> getUserLoginRecrodsNew() throws ClirNetAppException {
        ArrayList<LoginModel> loginList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT name,password  FROM user order by id desc limit 1";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    LoginModel user = new LoginModel(cursor.getString(0), cursor.getString(1));

                    loginList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting login records");
        } finally {
            //create method & pass cursor & db1 ref.
            if (cursor != null) {
                cursor.close();
            }
            if (database1 != null) {
                database1.close();
            }
        }
        return loginList;
    }

    //get all the patient imp data from db, which will used in Consultation fragments and home fragments
    public ArrayList<RegistrationModel> getPatientList(String date) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,p.uid,ph.drugs,p.alternate_no,ph.height,ph.bmi,sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd,ph.refered_by,ph.refered_to,p.email,ph.follow_up_status,ph.spo2,ph.respiration,p.family_history,p.hospitalization_surgery,ph.obesity  FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.visit_date = '" + date + "' and ph.added_on = '" + date + "' or ph.modified_on='" + date + "' order by ph.key_visit_id desc";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31),
                            cursor.getString(32), cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41),
                            cursor.getString(42), cursor.getString(43), cursor.getString(44), cursor.getString(45), cursor.getString(46), cursor.getString(47),cursor.getString(48),cursor.getString(49),cursor.getString(50),cursor.getString(51),cursor.getString(52),cursor.getString(53));

                    hotelList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {

            throw new ClirNetAppException("Something went wrong while getting getPatientList");
        } finally {
            //create method & pass cursor & db1 ref.
            if (cursor != null) {
                cursor.close();
            }
            if (database1 != null) {
                database1.close();
            }
        }

        return hotelList;

    }

    //get all the patient imp data from db, via fod which will used in Consultation fragments and home fragments
    public ArrayList<RegistrationModel> getPatientListnew(String date) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {

            // String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.actual_follow_up_date = '" + date + "' order by ph.key_visit_id  desc";
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,ph.tests,ph.drugs,p.alternate_no,ph.height,ph.bmi,sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd,ph.refered_by,ph.refered_to,p.email FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.actual_follow_up_date = '" + date + "' order by ph.key_visit_id  desc ";
            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31),
                            cursor.getString(32), cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(43), cursor.getString(44), cursor.getString(45), cursor.getString(46), cursor.getString(47));

                    hotelList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {

            throw new ClirNetAppException("Something went wrong while getting getPatientListnew");
        } finally {
            //create method & pass cursor & db1 ref.
            if (cursor != null) {
                cursor.close();
            }
            if (database1 != null) {
                database1.close();
            }
        }

        return hotelList;

    }

    //get all the patient imp data from db, which will used in Consultation fragments and home fragments
    public ArrayList<RegistrationModel> getPatientListVisitDateSearch(String date) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,ph.tests,ph.drugs,p.alternate_no,ph.height,ph.bmi,sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.visit_date = '" + date + "' order by ph.key_visit_id  desc ";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31),
                            cursor.getString(32), cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(43), cursor.getString(44));

                    hotelList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {

            throw new ClirNetAppException("Something went wrong while getting getPatientListVisitDateSearch");
        } finally {
            //create method & pass cursor & db1 ref.
            if (cursor != null) {
                cursor.close();
            }
            if (database1 != null) {
                database1.close();
            }
        }

        return hotelList;

    }


    //get max count of patient id
    public int getPatientIdCount() throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        int returnValue = 0;

        try {
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery("select max(patient_id) from patient", null);
            if (cursor.moveToFirst()) {
                returnValue = cursor.getInt(0);
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdCount");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnValue;
    }


    //get max visit count  from patient_history table
    public int getPatientVisitIdCount() throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        int returnValue = 0;

        try {
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery("select max(key_visit_id) from patient_history", null);
            if (cursor.moveToFirst()) {
                returnValue = cursor.getInt(0);
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting max visit id from patient_history");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnValue;
    }

    //used to fileter data from Patient history module
    public ArrayList<RegistrationModel> getFilterDatanew(String fname, String lname, String phoneno, ArrayList sex, ArrayList ageGap, ArrayList ailment, int page, int limit, Integer weightMinValue, Integer weightMaxValue, Integer heightMinValue, Integer heightMaxValue, Integer bmiMinValue, Integer bmiMaxValue, Integer pulseMinValue, Integer pulseMaxValue, Integer tempMinValue, Integer tempMaxValue, Integer systoleMinValue, Integer systoleMaxValue,
                                                         Integer distoleMinValue, Integer distoleMaxValue, Integer sugarFpgMinValue, Integer sugarFpgMaxValue, Integer sugarPpgMinValue, Integer sugarPpgMaxValue, String strLipidTC, String strLipidTCMax, String strLipidTG, String strLipidTGMax, String strLipidLDL, String strLipidLDLMax, String strLipidVHDL, String strLipidVHDLMax, String strLipidHDL, String strLipidHDLMax, Integer strHbA1c, Integer strHbA1cMax, String strSerumUrea, String strSerumUreaMax, String strAcer, String strAcerMax, String strEcg, String strPft, String strSmoking, String noOfSticksPerYear, String strLeftSmokingSinceYear, String strAlcoholConsumption, String noOfPegsPerYear, String strLeftAlcoholSinceYear, String strTobaco, String otherTobacoTaking, String strDrug, String otherDrugTaking, String strFoodHabit, String strFoodPreference, String strBingeEating, String strLactoseTolerance, String strLifeStyle, String strSleepStatus, String strStressLevel, String strSexuallyActive, String strExcercise, String strAllergie, String strPallor, String strPallorDescription, String strCyanosis, String strCyanosisDescription, String strTremors, String strTremorsDescription, String strIcterus, String strIcterusDescription
            , String strClubbing, String strClubbingDescription, String strOedema, String strOedemaDescription, String strCalfTenderness, String strCalfTendernessDescription, String strLymphadenopathy, String strLymphadenopathyDescription, Integer spo2MinValue,Integer spo2MaxValue,Integer respirationMinValue,Integer respirationMaxValue ) throws ClirNetAppException {

        SQLiteDatabase db1 = null;
        Cursor cursor = null;

        Set<RegistrationModel> pList1 = new LinkedHashSet<>();
        try {
            //open();
            String selectQuery = "select p.patient_id,p.first_name,p.middle_name,p.last_name ,p.dob ,p.gender,p.age,p.phonenumber,p.language,p.photo,ph.follow_up_date,ph.days,ph.months,ph.weeks,ph.ailment,ph.prescription,ph.clinical_notes,ph.added_on,ph.modified_on,ph.actual_follow_up_date,ph.action, p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,p.alternate_no,p.alternate_phone_type,p.phone_type,ph.visit_date,p.email,p.uid,p.family_history,p.hospitalization_surgery  from  patient p  LEFT JOIN patient_history ph ON p.patient_id = ph.patient_id LEFT JOIN table_investigation ti ON ph.key_visit_id = ti.key_visit_id LEFT JOIN health_and_lifestyle hl ON p.patient_id = hl.patient_id LEFT JOIN observation obs ON obs.key_visit_id = ph.key_visit_id  where p.patient_id=ph.patient_id";//and  p.first_name like '%" + fname + "%' and p.last_name like '%" + lname + "%'";

            // String countQuery = "select  COUNT (*) as count from patient p,patient_history ph   where p.patient_id=ph.patient_id ";

                     /*Selecting count of query for the pagination filter*/
         //   String countQuery = "select count(distinct(p.patient_id)) as count from patient p  LEFT JOIN patient_history ph ON p.patient_id = ph.patient_id LEFT JOIN table_investigation ti where ph.key_visit_id = ti.key_visit_id";
              String countQuery="select count(distinct(p.patient_id)) as count from patient p LEFT JOIN patient_history ph ON p.patient_id = ph.patient_id LEFT JOIN table_investigation ti ON ph.key_visit_id = ti.key_visit_id  LEFT JOIN health_and_lifestyle hl ON p.patient_id = hl.patient_id LEFT JOIN observation obs ON obs.key_visit_id = ph.key_visit_id  where p.patient_id=ph.patient_id";

            if (fname != null && !fname.equals("")){
                selectQuery = selectQuery.concat(" and p.first_name like '%" + fname + "%'");
                countQuery = countQuery.concat(" and p.first_name like '%" + fname + "%'");
            }
            if (lname != null && !lname.equals("")) {
                selectQuery = selectQuery.concat(" and p.last_name like '%" + lname + "%'");
                countQuery = countQuery.concat(" and p.last_name like '%" + lname + "%'");
            }
            if (sex.size() > 0) {
                for (int i = 0; i < sex.size(); i++) {
                    if (i != 0) {
                        selectQuery = selectQuery.concat(" OR p.gender = '" + sex.get(i) + "'");
                        countQuery = countQuery.concat(" OR p.gender = '" + sex.get(i) + "'");
                    } else {
                        selectQuery = selectQuery.concat("  AND ( p.gender = '" + sex.get(i) + "'");
                        countQuery = countQuery.concat("  AND ( p.gender = '" + sex.get(i) + "'");
                    }
                }
                selectQuery = selectQuery.concat(" ) ");
                countQuery = countQuery.concat(" ) ");
            }

            if (ageGap.size() > 0) {
                for (int i = 0; i < ageGap.size(); i++) {
                    String[] parts = ageGap.get(i).toString().split("-"); //split the string 0-5 to 0 and 5 resp
                    String part1 = parts[0];  //0
                    String part2 = parts[1]; // 5
                    if (part2.equals("Above")) {
                        part2 = "300";
                    }
                    if (i != 0) {

                        selectQuery = selectQuery.concat(" OR cast(p.age as INTEGER) BETWEEN " + part1 + " and " + part2 + " ");
                        countQuery = countQuery.concat(" OR cast(p.age as INTEGER) BETWEEN " + part1 + " and " + part2 + " ");

                    } else {

                        selectQuery = selectQuery.concat(" AND ( cast( p.age as INTEGER ) BETWEEN " + part1 + " and " + part2 + " ");
                        countQuery = countQuery.concat(" AND ( cast( p.age as INTEGER ) BETWEEN " + part1 + " and " + part2 + " ");

                    }
                }
                selectQuery = selectQuery.concat(" ) ");
                countQuery = countQuery.concat(" ) ");
            }

            if (ailment.size() > 0) {

                for (int i = 0; i < ailment.size(); i++) {
                    String value = ailment.get(i).toString();
                    if (i != 0) {
                        selectQuery = selectQuery.concat(" OR  ph.ailment like '%" + value + "%'" + " OR  ph.symptoms like '%" + value + "%'" + " OR  ph.diagnosis like '%" + value + "%'");

                        countQuery = countQuery.concat(" OR  ph.ailment like '%" + value + "%'" + " OR  ph.symptoms like '%" + value + "%'" + " OR  ph.diagnosis like '%" + value + "%'");

                    } else {
                        selectQuery = selectQuery.concat(" AND ( ph.ailment like '%" + value + "%'" + " OR  ph.symptoms like '%" + value + "%'" + " OR  ph.diagnosis like '%" + value + "%'");

                        countQuery = countQuery.concat(" AND ( ph.ailment like '%" + value + "%'" + " OR  ph.symptoms like '%" + value + "%'" + " OR  ph.diagnosis like '%" + value + "%'");
                    }
                }

                selectQuery = selectQuery.concat(" ) ");
                countQuery = countQuery.concat(" ) ");
            }

            if (weightMinValue != null && weightMaxValue != null) {

                selectQuery = selectQuery.concat(" AND cast(ph.weight as INTEGER) BETWEEN " + weightMinValue + " and " + weightMaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ph.weight as INTEGER) BETWEEN " + weightMinValue + " and " + weightMaxValue + " ");
            }

            if (heightMinValue != null && heightMaxValue != null) {
                selectQuery = selectQuery.concat(" AND cast(ph.height as INTEGER) BETWEEN " + heightMinValue + " and " + heightMaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ph.height as INTEGER) BETWEEN " + heightMinValue + " and " + heightMaxValue + " ");
            }
            //bmi filter query
            if (bmiMinValue != null && bmiMaxValue != null) {
                selectQuery = selectQuery.concat(" AND cast(ph.bmi as INTEGER) BETWEEN " + bmiMinValue + " and " + bmiMaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ph.bmi as INTEGER) BETWEEN " + bmiMinValue + " and " + bmiMaxValue + " ");
            }

            if (pulseMinValue != null && pulseMaxValue != null) {
                selectQuery = selectQuery.concat(" AND cast(ph.pulse as INTEGER) BETWEEN " + pulseMinValue + " and " + pulseMaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ph.pulse as INTEGER) BETWEEN " + pulseMinValue + " and " + pulseMaxValue + " ");
            }

            if (tempMinValue != null && tempMaxValue != null) {
                selectQuery = selectQuery.concat(" AND cast(ph.temperature as INTEGER) BETWEEN " + tempMinValue + " and " + tempMaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ph.temperature as INTEGER) BETWEEN " + tempMinValue + " and " + tempMaxValue + " ");
            }


            if (systoleMinValue != null && systoleMaxValue != null) {
                selectQuery = selectQuery.concat(" AND cast(ph.bp_low as INTEGER) BETWEEN " + systoleMinValue + " and " + systoleMaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ph.bp_low as INTEGER) BETWEEN " + systoleMinValue + " and " + systoleMaxValue + " ");
            }

            if (distoleMinValue != null && distoleMaxValue != null) {
                selectQuery = selectQuery.concat(" AND cast(ph.bp_high as INTEGER) BETWEEN " + distoleMinValue + " and " + distoleMaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ph.bp_high as INTEGER) BETWEEN " + distoleMinValue + " and " + distoleMaxValue + " ");
            }

            if (spo2MinValue != null && !spo2MinValue.toString().equals("")&& spo2MaxValue!=null) {

                selectQuery = selectQuery.concat(" AND cast(ph.spo2 as INTEGER) BETWEEN " + spo2MinValue + " and " + spo2MaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ph.spo2 as INTEGER) BETWEEN " + spo2MinValue + " and " + spo2MaxValue + " ");
            }

            if (respirationMinValue != null && !respirationMinValue.equals("")&& respirationMaxValue!=null) {

                selectQuery = selectQuery.concat(" AND cast(ph.respiration as INTEGER) BETWEEN " + respirationMinValue + " and " + respirationMaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ph.respiration as INTEGER) BETWEEN " + respirationMinValue + " and " + respirationMaxValue + " ");
            }

            if (sugarFpgMinValue != null && sugarFpgMaxValue != null) {
                selectQuery = selectQuery.concat(" AND cast(ti.sugar_fasting as INTEGER) BETWEEN " + sugarFpgMinValue + " and " + sugarFpgMaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ti.sugar_fasting  as INTEGER) BETWEEN " + sugarFpgMinValue + " and " + sugarFpgMaxValue + " ");
            }
            if (sugarPpgMinValue != null && sugarPpgMaxValue != null) {
                selectQuery = selectQuery.concat(" AND cast(ti.sugar as INTEGER) BETWEEN " + sugarPpgMinValue + " and " + sugarPpgMaxValue + " ");
                countQuery = countQuery.concat(" AND cast(ti.sugar as INTEGER) BETWEEN " + sugarPpgMinValue + " and " + sugarPpgMaxValue + " ");
            }
            if (strLipidTC != null && !strLipidTC.equals("") && strLipidTCMax!=null) {
                selectQuery = selectQuery.concat(" AND cast(ti.lipid_profile_tc as INTEGER) BETWEEN " + strLipidTC + " and " + strLipidTCMax + " ");
                countQuery = countQuery.concat(" AND cast(ti.lipid_profile_tc as INTEGER) BETWEEN " + strLipidTC + " and " + strLipidTCMax + " ");

            }
            if (strLipidTG != null && !strLipidTG.equals("") && strLipidTGMax!=null) {
                selectQuery = selectQuery.concat(" AND cast(ti.lipid_profile_tg as INTEGER) BETWEEN " + strLipidTG + " and " + strLipidTGMax + " ");
                countQuery = countQuery.concat(" AND cast(ti.lipid_profile_tg as INTEGER) BETWEEN " + strLipidTG + " and " + strLipidTGMax + " ");
            }
            if (strLipidLDL != null && !strLipidLDL.equals("") && strLipidLDLMax!=null) {
                selectQuery = selectQuery.concat(" AND cast(ti.lipid_profile_ldl as INTEGER) BETWEEN " + strLipidLDL + " and " + strLipidLDLMax + " ");
                countQuery = countQuery.concat(" AND cast(ti.lipid_profile_ldl as INTEGER) BETWEEN " + strLipidLDL + " and " + strLipidLDLMax + " ");
            }
            if (strLipidVHDL != null && !strLipidVHDL.equals("")&& strLipidVHDLMax!=null) {

                selectQuery = selectQuery.concat(" AND cast(ti.lipid_profile_vhdl as INTEGER) BETWEEN " + strLipidVHDL + " and " + strLipidVHDLMax + " ");
                countQuery = countQuery.concat(" AND cast(ti.lipid_profile_vhdl as INTEGER) BETWEEN " + strLipidVHDL + " and " + strLipidVHDLMax + " ");

            }
            if (strLipidHDL != null && !strLipidHDL.equals("") && strLipidHDLMax!=null) {
                selectQuery = selectQuery.concat(" AND cast(ti.lipid_profile_hdl as INTEGER) BETWEEN " + strLipidHDL + " and " + strLipidHDLMax + " ");
                countQuery = countQuery.concat(" AND cast(ti.lipid_profile_hdl as INTEGER) BETWEEN " + strLipidHDL + " and " + strLipidHDLMax + " ");

            }
            if (strHbA1c != null  && !strHbA1c.equals("") && strHbA1cMax!=null) {
                selectQuery = selectQuery.concat(" AND cast(ti.hba1c as INTEGER) BETWEEN " + strHbA1c + " and " + strHbA1cMax + " ");
                countQuery = countQuery.concat(" AND cast(ti.hba1c as INTEGER) BETWEEN " + strHbA1c + " and " + strHbA1cMax + " ");

            }
            if (strSerumUrea != null && !strSerumUrea.equals("") && strSerumUreaMax !=null ) {
                selectQuery = selectQuery.concat(" AND cast(ti.serem_urea as INTEGER) BETWEEN " + strSerumUrea + " and " + strSerumUreaMax + " ");
                countQuery = countQuery.concat(" AND cast(ti.serem_urea as INTEGER) BETWEEN " + strSerumUrea + " and " + strSerumUreaMax + " ");

            }
            if (strAcer != null && !strAcer.equals("") && strAcerMax !=null) {
                selectQuery = selectQuery.concat(" AND cast(ti.acer as INTEGER) BETWEEN " + strAcer + " and " + strAcerMax + " ");
                countQuery = countQuery.concat(" AND cast(ti.acer as INTEGER) BETWEEN " + strAcer + " and " + strAcerMax + " ");

            }
            if (strEcg != null && !strEcg.equals("")) {
                selectQuery = selectQuery.concat(" and ti.ecg like '%" + strEcg + "%'");
                countQuery = countQuery.concat(" and ti.ecg like '%" + strEcg + "%'");
            }
            if (strPft != null && !strPft.equals("")) {
                selectQuery = selectQuery.concat(" and ti.pft like '%" + strPft + "%'");
                countQuery = countQuery.concat(" and ti.pft like '%" + strPft + "%'");
            }

            // filter from  health table
            if (strSmoking != null && !strSmoking.equals("")) {
                selectQuery = selectQuery.concat(" and hl.smoker_type = '" + strSmoking + "'");
                countQuery = countQuery.concat("  and hl.smoker_type = '" + strSmoking + "'");
            }
            if (noOfSticksPerYear != null && !noOfSticksPerYear.equals("")) {
                selectQuery = selectQuery.concat(" and hl.stick_count = '" + noOfSticksPerYear + "'");
                countQuery = countQuery.concat("  and hl.stick_count = '" + noOfSticksPerYear + "'");
            }
            if (strLeftSmokingSinceYear != null && !strLeftSmokingSinceYear.equals("")) {
                selectQuery = selectQuery.concat(" and hl.last_smoke_year = '" + strLeftSmokingSinceYear + "'");
                countQuery = countQuery.concat("  and hl.last_smoke_year = '" + strLeftSmokingSinceYear + "'");
            }
            if (strAlcoholConsumption != null && !strAlcoholConsumption.equals("")) {
                selectQuery = selectQuery.concat(" and hl.alcohol_consumption = '" + strAlcoholConsumption + "'");
                countQuery = countQuery.concat("  and hl.alcohol_consumption = '" + strAlcoholConsumption + "'");
            }
            if (noOfPegsPerYear != null && !noOfPegsPerYear.equals("")) {
                selectQuery = selectQuery.concat(" and hl.pegs_count = '" + noOfPegsPerYear + "'");
                countQuery = countQuery.concat("  and hl.pegs_count = '" + noOfPegsPerYear + "'");
            }
            if (strLeftAlcoholSinceYear != null && !strLeftAlcoholSinceYear.equals("")) {
                selectQuery = selectQuery.concat(" and hl.last_drink_year = '" + strLeftAlcoholSinceYear + "'");
                countQuery = countQuery.concat("  and hl.last_drink_year = '" + strLeftAlcoholSinceYear + "'");
            }
            if (strTobaco != null && !strTobaco.equals("")) {
                selectQuery = selectQuery.concat(" and hl.chewing_tobaco = '" + strTobaco + "'");
                countQuery = countQuery.concat("  and hl.chewing_tobaco = '" + strTobaco + "'");
            }
            if (otherTobacoTaking != null && !otherTobacoTaking.equals("")) {
                selectQuery = selectQuery.concat(" and hl.tobaco_other like '%" + otherTobacoTaking + "%'");
                countQuery = countQuery.concat("  and hl.tobaco_other like '%" + otherTobacoTaking + "%'");
            }
            if (strDrug != null && !strDrug.equals("")) {
                selectQuery = selectQuery.concat(" and hl.drug_consumption = '" + strDrug + "'");
                countQuery = countQuery.concat("  and hl.drug_consumption = '" + strDrug + "'");
            }
            if (otherDrugTaking != null && !otherDrugTaking.equals("")) {
                selectQuery = selectQuery.concat(" and hl.drug_consumption_type like '%" + otherDrugTaking + "%'");
                countQuery = countQuery.concat("  and hl.drug_consumption_type like '%" + otherDrugTaking + "%'");
            }
            if (strFoodHabit != null && !strFoodHabit.equals("")) {
                selectQuery = selectQuery.concat(" and hl.food_habit = '" + strFoodHabit + "'");
                countQuery = countQuery.concat("  and hl.food_habit = '" + strFoodHabit + "'");
            }
            if (strFoodPreference != null && !strFoodPreference.equals("")) {
                selectQuery = selectQuery.concat(" and hl.food_preference = '" + strFoodPreference + "'");
                countQuery = countQuery.concat("  and hl.food_preference = '" + strFoodPreference + "'");
            }
            if (strBingeEating != null && !strBingeEating.equals("")) {
                selectQuery = selectQuery.concat(" and hl.binge_eating = '" + strBingeEating + "'");
                countQuery = countQuery.concat("  and hl.binge_eating = '" + strBingeEating + "'");
            }
            if (strLactoseTolerance != null && !strLactoseTolerance.equals("")) {

                selectQuery = selectQuery.concat(" and hl.lactose_tolerance = '" + strLactoseTolerance + "'");
                countQuery = countQuery.concat("  and hl.lactose_tolerance = '" + strLactoseTolerance + "'");
            }
            if (strLifeStyle != null && !strLifeStyle.equals("")) {
                selectQuery = selectQuery.concat(" and hl.life_style = '" + strLifeStyle + "'");
                countQuery = countQuery.concat("  and hl.life_style = '" + strLifeStyle + "'");
            }
            if (strSleepStatus != null && !strSleepStatus.equals("")) {
                selectQuery = selectQuery.concat(" and hl.sleep_status = '" + strSleepStatus + "'");
                countQuery = countQuery.concat("  and hl.sleep_status = '" + strSleepStatus + "'");
            }
            if (strStressLevel != null && !strStressLevel.equals("")) {
                selectQuery = selectQuery.concat(" and hl.stress_level = '" + strStressLevel + "'");
                countQuery = countQuery.concat("  and hl.stress_level = '" + strStressLevel + "'");
            }
            if (strSexuallyActive != null && !strSexuallyActive.equals("")) {
                selectQuery = selectQuery.concat(" and hl.sexually_active = '" + strSexuallyActive + "'");
                countQuery = countQuery.concat("  and hl.sexually_active = '" + strSexuallyActive + "'");
            }
            if (strExcercise != null && !strExcercise.equals("")) {
                selectQuery = selectQuery.concat(" and hl.excercise = '" + strExcercise + "'");
                countQuery = countQuery.concat("  and hl.excercise = '" + strExcercise + "'");
            }
            if (strAllergie != null && !strAllergie.equals("")) {
                selectQuery = selectQuery.concat(" and hl.allergies like '%" + strAllergie + "%'");
                countQuery = countQuery.concat("  and hl.allergies like '%" + strAllergie +"%'");
            }
           // Log.e("",""+strPallor);
            if (strPallor != null && !strPallor.equals("")) {
                selectQuery = selectQuery.concat(" and obs.pallor like '%" + strPallor + "%'");
                countQuery = countQuery.concat("  and obs.pallor like '%" + strPallor +"%'");
            }
            if (strPallorDescription != null && !strPallorDescription.equals("")) {
                selectQuery = selectQuery.concat(" and obs.pallor_description like '%" + strPallorDescription + "%'");
                countQuery = countQuery.concat("  and obs.pallor_description like '%" + strPallorDescription +"%'");
            }
            if (strCyanosis != null && !strCyanosis.equals("")) {
                selectQuery = selectQuery.concat(" and obs.cyanosis like '%" + strCyanosis + "%'");
                countQuery = countQuery.concat("  and obs.cyanosis like '%" + strCyanosis +"%'");
            }
            if (strCyanosisDescription != null && !strCyanosisDescription.equals("")) {
                selectQuery = selectQuery.concat(" and obs.cyanosis_description like '%" + strCyanosisDescription + "%'");
                countQuery = countQuery.concat("  and obs.cyanosis_description like '%" + strCyanosisDescription +"%'");
            }
            if (strTremors != null && !strTremors.equals("")) {
                selectQuery = selectQuery.concat(" and obs.tremors like '%" + strTremors + "%'");
                countQuery = countQuery.concat("  and obs.tremors like '%" + strTremors +"%'");
            }
            if (strTremorsDescription != null && !strTremorsDescription.equals("")) {
                selectQuery = selectQuery.concat(" and obs.tremors_description like '%" + strTremorsDescription + "%'");
                countQuery = countQuery.concat("  and obs.tremors_description like '%" + strTremorsDescription +"%'");
            }
            if (strIcterus != null && !strIcterus.equals("")) {
                selectQuery = selectQuery.concat(" and obs.icterus like '%" + strIcterus + "%'");
                countQuery = countQuery.concat("  and obs.icterus like '%" + strIcterus +"%'");
            }
            if (strIcterusDescription != null && !strIcterusDescription.equals("")) {
                selectQuery = selectQuery.concat(" and obs.icterus_description like '%" + strIcterusDescription + "%'");
                countQuery = countQuery.concat("  and obs.icterus_description like '%" + strIcterusDescription +"%'");
            }
            if (strClubbing != null && !strClubbing.equals("")) {
                selectQuery = selectQuery.concat(" and obs.clubbing like '%" + strClubbing + "%'");
                countQuery = countQuery.concat("  and obs.clubbing like '%" + strClubbing +"%'");
            }
            if (strClubbingDescription != null && !strClubbingDescription.equals("")) {
                selectQuery = selectQuery.concat(" and obs.clubbing_description like '%" + strClubbingDescription + "%'");
                countQuery = countQuery.concat("  and obs.clubbing_description like '%" + strClubbingDescription +"%'");
            }
            if (strOedema != null && !strOedema.equals("")) {
                selectQuery = selectQuery.concat(" and obs.oedema like '%" + strOedema + "%'");
                countQuery = countQuery.concat("  and obs.oedema like '%" + strOedema +"%'");
            }
            if (strOedemaDescription != null && !strOedemaDescription.equals("")) {
                selectQuery = selectQuery.concat(" and obs.oedema_description like '%" + strOedemaDescription + "%'");
                countQuery = countQuery.concat("  and obs.oedema_description like '%" + strOedemaDescription +"%'");
            }
            if (strCalfTenderness != null && !strCalfTenderness.equals("")) {
                selectQuery = selectQuery.concat(" and obs.calf_tenderness like '%" + strCalfTenderness + "%'");
                countQuery = countQuery.concat("  and obs.calf_tenderness like '%" + strCalfTenderness +"%'");
            }
            if (strCalfTendernessDescription != null && !strCalfTendernessDescription.equals("")) {
                selectQuery = selectQuery.concat(" and obs.calf_tenderness_description like '%" + strCalfTendernessDescription + "%'");
                countQuery = countQuery.concat("  and obs.calf_tenderness_description like '%" + strCalfTendernessDescription +"%'");
            }
            if (strLymphadenopathy != null && !strLymphadenopathy.equals("")) {
                selectQuery = selectQuery.concat(" and obs.lympadenopathy like '%" + strLymphadenopathy + "%'");
                countQuery = countQuery.concat("  and obs.lympadenopathy like '%" + strLymphadenopathy +"%'");
            }
            if (strLymphadenopathyDescription != null && !strLymphadenopathyDescription.equals("")) {
                selectQuery = selectQuery.concat(" and obs.lympadenopathy_description like '%" + strLymphadenopathyDescription + "%'");
                countQuery = countQuery.concat("  and obs.lympadenopathy_description like '%" + strLymphadenopathyDescription +"%'");
            }


            //count query here to get no of records fetch by query for pagination in page.
            countQuery = countQuery.concat("  and p.phonenumber like '%" + phoneno + "%' order by ph.key_visit_id desc ");
            //   String queryforCount=selectQuery.concat("  and p.phonenumber like '%" + phoneno + "%' order by ph.key_visit_id desc ");

            selectQuery = selectQuery.concat("  and p.phonenumber like '%" + phoneno + "%' group by p.patient_id order by ph.key_visit_id desc limit " + page + "," + limit + ";");

            new Counts().setCountQuery(countQuery);

           // Log.e("selectQuery", "" + selectQuery);
         //   Log.e("countQueryQuery", "" + countQuery);
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);


            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22), cursor.getString(23),
                            cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31),cursor.getString(32),cursor.getString(33));

                    pList1.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting recrds from getFilterDatanew method");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return new ArrayList<>(pList1);
    }

    public int getCountResult() {
        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        int numRows = 0;
        try {
            String countQuery = new Counts().getCountQuery();
            // Log.d("count", "" + countQuery);

            if (countQuery != null && countQuery.length() > 10) {

                numRows = (int) DatabaseUtils.longForQuery(db1, countQuery, null);
             //   Log.d("count", "" + countQuery + "  " + numRows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db1 != null) {
                db1.close();

            }
        }

        return numRows;
    }

    public int getCountResultforAssociate() {

        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        int numRows = 0;
        try {
            numRows = (int) DatabaseUtils.longForQuery(db1, "SELECT Count(*) FROM associate_master", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db1 != null) {
                db1.close();

            }
        }

        return numRows;
    }

    public ArrayList<RegistrationModel> getPatientListForPhoneNumberFilter(String number, int page, int limit) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {                                                                                                                                                                                                                                                                                                                                                                                          //p.first_name like '%" + fname + "%'

            String countQuery = "select COUNT (*) as count from patient p,patient_history ph   where p.patient_id=ph.patient_id and p.phonenumber like '%" + number + "%' order by ph.key_visit_id desc ";

            String selectQuery = "select p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,\n" +
                    " p.age,p.phonenumber,p.gender,p.language,p.photo,\n" +
                    " ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,\n" +
                    " ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,\n" +
                    " ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,\n" +
                    " p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,\n" +
                    " ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,\n" +
                    " p.uid,ph.drugs,p.alternate_no,ph.height,ph.bmi,\n" +
                    " ph.sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd,\n" +
                    " ph.refered_by,ph.refered_to,p.email,ph.follow_up_status,ph.spo2,ph.respiration,p.family_history,p.hospitalization_surgery,ph.obesity from patient p , patient_history ph where ph.patient_id=p.patient_id and p.phonenumber like '%" + number + "%' group by ph.patient_id having count(*)>0   order by ph.key_visit_id desc limit " + page + " , " + limit + ";";

            new Counts().setCountQueryforHomeFragmentNoFilter(countQuery);

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31),
                            cursor.getString(32), cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38),
                            cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(43), cursor.getString(44), cursor.getString(45), cursor.getString(46), cursor.getString(47),cursor.getString(48),cursor.getString(49),cursor.getString(50),cursor.getString(51),cursor.getString(52),cursor.getString(53));

                    hotelList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientListForPhoneNumberFilter");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return hotelList;

    }

    public int getCountResultforgetPatientListForPhoneNumberFilter() {

        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        int numRows = 0;
        try {
            String countQuery = Counts.getCountQueryforHomeFragmentNoFilter();

            if (countQuery.length() > 1) {

                numRows = (int) DatabaseUtils.longForQuery(db1, countQuery, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db1 != null) {
                db1.close();
            }
        }
        return numRows;
    }

    //This will show all the visit  history of patient to show on AddPatientUpdate and ShowPaersonalDetals page
    //ashish
    public ArrayList<RegistrationModel> getPatientHistoryListAll(String patient_id) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {

            //String selectQuery = "select  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,ph.symptoms,ph.diagnosis  from patient_history  ph , patient p where p.patient_id=" + patient_id + " and  p.patient_id=ph.patient_id order by ph.key_visit_id desc ";
           String selectQuery="SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date, p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ti.sugar,ph.symptoms, ph.diagnosis,p.email,p.uid,p.alternate_no,ph.height,ph.bmi,ti.sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd ,ph.refered_by,ph.refered_to from patient p" +
                   " LEFT JOIN patient_history ph ON p.patient_id = ph.patient_id LEFT JOIN table_investigation ti ON ph.key_visit_id = ti.key_visit_id where p.patient_id= " + patient_id + " order by ph.key_visit_id desc ";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22), cursor.getString(23));
                    hotelList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientHistoryListAll");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return hotelList;

    }

    //This will show all the visit  history of patient to show on AddPatientUpdate and ShowPaersonalDetals page
    //ashish
    public ArrayList<RegistrationModel> getPatientHistoryListAll1(String patient_id) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date, p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ti.sugar,ph.symptoms, ph.diagnosis,p.email," +
                    "p.uid,p.alternate_no,ph.height,ph.bmi,ti.sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd ,ph.refered_by,ph.refered_to,ph.spo2,ph.respiration,ti.hba1c,ti.acer,ti.serem_urea,ti.lipid_profile_hdl,ti.lipid_profile_tc,lipid_profile_tg,ti.lipid_profile_ldl,ti.lipid_profile_vhdl,ti.ecg,ti.pft,obs.pallor,obs.cyanosis,obs.tremors,obs.icterus,obs.clubbing,obs.oedema,obs.calf_tenderness,obs.lympadenopathy,ph.obesity from  patient p  LEFT JOIN patient_history ph ON p.patient_id = ph.patient_id LEFT JOIN table_investigation ti ON ph.key_visit_id = ti.key_visit_id LEFT JOIN observation obs ON obs.key_visit_id = ph.key_visit_id where p.patient_id=" + patient_id + "  order by ph.key_visit_id desc ";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31),
                            cursor.getString(32), cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(43), cursor.getString(44), cursor.getString(45), cursor.getString(46),cursor.getString(47),cursor.getString(48),
                            cursor.getString(49), cursor.getString(50), cursor.getString(51), cursor.getString(52), cursor.getString(53), cursor.getString(54), cursor.getString(55), cursor.getString(56), cursor.getString(57),cursor.getString(58), cursor.getString(59), cursor.getString(60), cursor.getString(61), cursor.getString(62), cursor.getString(63), cursor.getString(64), cursor.getString(65), cursor.getString(66),cursor.getString(67));
                    hotelList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientHistoryListAll");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return hotelList;

    }

    ///////////////////////////////////////////////////////////////
    //get all patients which is yet to send to server

    public ArrayList<RegistrationModel> getPatientIdsFalg0() throws ClirNetAppException {

        ArrayList<RegistrationModel> idList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select  patient_id from patient where flag = 0  ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel ids = new RegistrationModel(cursor.getString(0));

                    idList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdsFalg0");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return idList;

    }

    //get all patients  visits data which is yet to send to server
    public ArrayList<RegistrationModel> getPatientVisitIdsFalg0() throws ClirNetAppException {

        ArrayList<RegistrationModel> VisitidList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select patient_id, key_visit_id from patient_history where flag = 0 ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel ids = new RegistrationModel(cursor.getString(0), cursor.getString(1));

                    VisitidList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientVisitIdsFalg0");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return VisitidList;

    }
    //get all patients  visits data which is yet to send to server
    public ArrayList<RegistrationModel> getPatientIdInvestigationFalg0() throws ClirNetAppException {

        ArrayList<RegistrationModel> VisitidList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select patient_id from table_investigation where flag = 0 ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel ids = new RegistrationModel(cursor.getString(0));

                    VisitidList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdInvestigationFalg0");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return VisitidList;

    }

    //get all patients  visits data which is yet to send to server
    public ArrayList<RegistrationModel> getPatientIdHealthAndLifestyleFalg0() throws ClirNetAppException {

        ArrayList<RegistrationModel> VisitidList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select patient_id from health_and_lifestyle where flag = 0 ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel ids = new RegistrationModel(cursor.getString(0));

                    VisitidList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdInvestigationFalg0");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return VisitidList;

    }

    //get all patients  visits data which is yet to send to server
    public ArrayList<RegistrationModel> getObservationListIdsFalg0() throws ClirNetAppException {

        ArrayList<RegistrationModel> ObservationList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select patient_id from observation where flag = 0 ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel ids = new RegistrationModel(cursor.getString(0));

                    ObservationList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdInvestigationFalg0");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return ObservationList;

    }
    //get docot membership id from db
    public String getDoctorMembershipIdNew() throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();
            String query = "select doc_mem_id from doctor_perInfo order by doc_mem_id desc limit 1";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("doc_mem_id"));
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


    //get doctor id from db
    public String getDoctorId() throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();

            String query = "select doctor_id from doctor_perInfo order by doctor_id desc limit 1";


            cursor = db1.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("doctor_id"));
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getDoctorId");
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

    //doctor first and last name to set to navigation bar
    public String getDocdoctorName() throws ClirNetAppException {

        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String firstName = null;
        String lastName = null;
        try {
            db1 = dbHelper.getReadableDatabase();
            String query = "select first_name,last_name from doctor_perInfo order by doctor_id desc limit 1";

            cursor = db1.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                firstName = cursor.getString(cursor.getColumnIndex("first_name"));
                lastName = cursor.getString(cursor.getColumnIndex("last_name"));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new ClirNetAppException("something went wrong while getting getDocdoctorName");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        //  Log.e("ashishu"," "+firstNaame + ""+lastName);
        return firstName + " " + lastName;


    }

    public String getPhoneNumber() throws ClirNetAppException {
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        SQLiteDatabase db1 = null;


        try {
            db1 = dbHelper.getReadableDatabase();
            // stmt = db1.compileStatement("select phonenumber from doctor_perInfo order by phonenumber desc limit 1");
            String query = "select phonenumber from doctor_perInfo order by phonenumber desc limit 1";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("phonenumber"));
            }

        } catch (Exception e) {
            throw new ClirNetAppException("something went wrong while getting phonenumber");
        } finally {

            if (cursor != null) {
                //close statment
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnString;
    }

    //get doctor mail id
    public String getDocdoctorEmail() throws ClirNetAppException {


        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();

            String query = "select email from doctor_perInfo order by email desc limit 1";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("email"));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new ClirNetAppException("something went wrong while getting getDocdoctorEmail");
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

    public String getAsyncvalue() throws ClirNetAppException {
        Cursor cursor = null;
        String returnString = null; // Your default if none is found
        SQLiteDatabase db1 = null;
        try {
            db1 = dbHelper.getReadableDatabase();

            String query = "select value from async order by id desc limit 1";

            cursor = db1.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("value"));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new ClirNetAppException("no values found for getAsyncvalue ");
        } finally {
            if (cursor != null) {
                //close statment
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnString;
    }

    //This will close the Cursor and Database 1-11-2016
    private void closeConnection(Cursor cursor, SQLiteDatabase db2) {
        if (cursor != null) {
            cursor.close();
        }
        if (db2 != null) {
            db2.close();
        }
    }

    //To validate user from sqlite stored values
    public boolean validateUser(String username, String password, String phoneNumber) throws ClirNetAppException {

        int count;
        Cursor c = null;
        SQLiteDatabase db1 = null;
        try {
            db1 = dbHelper.getReadableDatabase();
            c = db1.rawQuery(
                    "SELECT * FROM " + SQLiteHandler.TABLE_USER + " WHERE ("
                            + SQLiteHandler.KEY_NAME + "='" + username + "' OR " + SQLiteHandler.PHONE_NUMBER + "='" + phoneNumber + "' )AND " + SQLiteHandler.KEY_PASSWORD + "='" + password + "'", null);
            count = c.getCount();
        } catch (Exception e) {
            throw new ClirNetAppException("Not able to search records while validateUser ");
        } finally {
            if (c != null) {
                c.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return count > 0;
    }


    public ArrayList<Counts> countPerDay(String fromdate, String todate) throws ClirNetAppException {

        ArrayList<Counts> VisitidList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {

            String selectQuery = "SELECT pvd.visit_date , count( * ) AS tot FROM patient dpr, patient_history pvd WHERE dpr.patient_id = pvd.patient_id AND" +
                    " date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2)) Between Date('" + fromdate + "') AND Date('" + todate + "') GROUP BY pvd.visit_date";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    Counts ids = new Counts(cursor.getString(0), cursor.getString(1));

                    VisitidList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting countPerDay");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return VisitidList;

    }

    public ArrayList<String> getCountTopTenSymptoms(String fromdate, String todate) throws ClirNetAppException {

        ArrayList<String> ailmnetList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT distinct pvd.symptoms as symptoms FROM \n" +
                    "patient dpr , patient_history pvd \n" +
                    "WHERE dpr.patient_id = pvd.patient_id \n" +
                    "AND date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2)) \n" +
                    "Between Date('" + fromdate + "') AND Date('" + todate + "') ";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    String value = cursor.getString(cursor.getColumnIndex("symptoms"));
                    ailmnetList.add(value);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getCountTopTenAilment");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return ailmnetList;

    }

    public ArrayList<String> getCountTopTenDiagnosis(String fromdate, String todate) throws ClirNetAppException {

        ArrayList<String> ailmnetList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {

            String selectQuery = "SELECT pvd.diagnosis as diagnosis FROM \n" +
                    "patient dpr , patient_history pvd \n" +
                    "WHERE dpr.patient_id = pvd.patient_id \n" +
                    "AND date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2)) \n" +
                    "Between Date('" + fromdate + "') AND Date('" + todate + "') and pvd.diagnosis !='null' and pvd.diagnosis !=''  limit 0,20 \n";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            //Log.e("getCountTopTenDiagnosis", " "+selectQuery);


            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    String value = cursor.getString(cursor.getColumnIndex("diagnosis"));
                    ailmnetList.add(value);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getCountTopTenAilment");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return ailmnetList;

    }

    public ArrayList<String> getCountTopTenAilments(String fromdate, String todate) throws ClirNetAppException {

        ArrayList<String> ailmentList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT pvd.ailment as ailment FROM \n" +
                    "patient dpr , patient_history pvd \n" +
                    "WHERE dpr.patient_id = pvd.patient_id  and pvd.ailment != 'null' \n" +
                    "AND date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2)) \n" +
                    "Between Date('" + fromdate + "') AND Date('" + todate + "') limit 20 \n";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    String value = cursor.getString(cursor.getColumnIndex("ailment"));
                    ailmentList.add(value);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getCountTopTenAilment");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return ailmentList;

    }


    public ArrayList<GenderWiseDataModel> genderWiseData(String fromdate, String todate) throws ClirNetAppException {

        ArrayList<GenderWiseDataModel> genderList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT COUNT(CASE WHEN UPPER(dpr.gender) = 'MALE' THEN 1 END) Male,\n" +
                    "COUNT(CASE WHEN UPPER(dpr.gender) = 'FEMALE' THEN 1 END) Female,\n" +
                    "  CASE\n" +
                    "\tWHEN CAST(dpr.age AS Integer) >=0 AND CAST(dpr.age AS Integer) <5 THEN '00-05'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) >=5 AND CAST(dpr.age AS Integer) <15 THEN '05-15'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) >=15 AND CAST(dpr.age AS Integer) <25 THEN '15-25'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) >=25 AND CAST(dpr.age AS Integer) <35 THEN '25-35'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) >=35 AND CAST(dpr.age AS Integer) <45 THEN '35-45'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) >=45 AND CAST(dpr.age AS Integer) <55 THEN '45-55'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) >=55 AND CAST(dpr.age AS Integer) <65 THEN '55-65'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) >=65 AND CAST(dpr.age AS Integer) <1000 THEN '65-Above'\n" +
                    "  END AS ageband\n" +
                    "FROM\n" +
                    " patient dpr , patient_history pvd WHERE dpr.patient_id = pvd.patient_id  \n" +
                    " AND  date(substr(pvd.visit_date,7,4)||'-'||substr(pvd.visit_date,4,2)||'-'||substr(pvd.visit_date,1,2)) \n" +
                    "Between Date('" + fromdate + "') AND Date('" + todate + "')\n" +
                    "GROUP BY ageband ORDER by ageband";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);


            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    String maleCount = cursor.getString(cursor.getColumnIndex("Male"));
                    String femaleCount = cursor.getString(cursor.getColumnIndex("Female"));
                    String ageBound = cursor.getString(cursor.getColumnIndex("ageband"));
                    GenderWiseDataModel ids = new GenderWiseDataModel(maleCount, femaleCount, ageBound);
                    genderList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting genderWiseData");

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return genderList;

    }

    public String getCompany_id() throws ClirNetAppException {

        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        SQLiteDatabase db1 = null;

        try {
            db1 = dbHelper.getReadableDatabase();
            String query = "select company_id from doctor_perInfo order by company_id desc limit 1";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("company_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();

            throw new ClirNetAppException("something went wrong while getting company Id");
        } finally {
            if (cursor != null) {
                //close statment
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }

        }

        return returnString;
    }


    public HashMap<String, String> retrieveMarketed_byandMmanufactured_by(String imgName) throws ClirNetAppException {
        HashMap<String, String> user = new HashMap<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            String query = "select marketed_by,manufactured_by,clinical_trial_link,link_to_page,product_image_name,product_image2,brand_name,generic_name,type from company_banners where image_name='" + imgName + "' order by company_id  desc limit 1  ";
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    user.put("manufactured_by", cursor.getString(0));
                    user.put("marketed_by", cursor.getString(1));
                    user.put("clinical_trial_link", cursor.getString(2));
                    user.put("link_to_page", cursor.getString(3));
                    user.put("product_image_name", cursor.getString(4));
                    user.put("product_image2", cursor.getString(5));
                    user.put("brand_name", cursor.getString(6));
                    user.put("generic_name", cursor.getString(7));
                    user.put("banner_type", cursor.getString(8));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting countPerDa");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return user;
    }

    public HashMap<String, String> getDoctorInformation() throws ClirNetAppException {
        HashMap<String, String> user = new HashMap<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            String query = "select first_name,last_name,phonenumber,email,doctor_id,doc_mem_id from doctor_perInfo order by doctor_id desc limit 1 ";
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    user.put("first_name", cursor.getString(0));
                    user.put("last_name", cursor.getString(1));
                    user.put("phonenumber", cursor.getString(2));
                    user.put("email", cursor.getString(3));
                    user.put("doctor_id", cursor.getString(4));
                    user.put("doc_mem_id", cursor.getString(5));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting countPerDa");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return user;
    }

    //get max count of patient id
    public int getTotalPatientIdCount() throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        int returnValue = 0;

        try {
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery("SELECT COUNT(*) FROM patient", null);
            if (cursor.moveToFirst()) {
                returnValue = cursor.getInt(0);
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdCount");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnValue;
    }

    public int getTotalPatientHistoryIdCount() throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        int returnValue = 0;

        try {
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery("SELECT COUNT(*) FROM patient_history", null);
            if (cursor.moveToFirst()) {
                returnValue = cursor.getInt(0);
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdCount");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnValue;
    }

    public HashMap<String, String> getBannerInformation(String imgName) throws ClirNetAppException {
        HashMap<String, String> user = new HashMap<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            String query = "SELECT cb.brand_name,cb.generic_name,cb.company_id,dpi.doctor_id,doc_mem_id,cb.banner_id from company_banners cb,doctor_perInfo dpi where image_name='" + imgName + "' ";
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    user.put("brand_name", cursor.getString(0));
                    user.put("generic_name", cursor.getString(1));
                    user.put("company_id", cursor.getString(2));
                    user.put("doctor_id", cursor.getString(3));
                    user.put("doc_mem_id", cursor.getString(4));
                    user.put("banner_id", cursor.getString(5));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting countPerDa");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return user;
    }


    public String getBannerId(String banner_image) throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        int returnValue = 0;

        try {
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery("select banner_id from company_banners where image_name ='" + banner_image + "'", null);
            if (cursor.moveToFirst()) {
                returnValue = cursor.getInt(0);
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdCount");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return String.valueOf(returnValue);
    }

    public String getFolderName(String banner_image) throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnValue = null;

        try {
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery("select folder_name from company_banners where image_name ='" + banner_image + "'", null);
            if (cursor.moveToFirst()) {
                returnValue = cursor.getString(0);
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdCount");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnValue;
    }

    //get all patients which is yet to send to server
    public ArrayList<String> getBannerDisplaysFalg0() throws ClirNetAppException {

        ArrayList<String> idList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select  id from banner_display where flag = 0";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    String ids = cursor.getString(0);

                    idList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getBannerDisplaysFalg0");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return idList;

    }

    //get all patients which is yet to send to server
    public ArrayList<String> getBannerClickedFalg0() throws ClirNetAppException {

        ArrayList<String> idList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select  id from banner_clicked where flag = 0 ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    String ids = cursor.getString(0);

                    idList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getBannerClickedFalg0");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return idList;

    }


    //get company id from banner table to store in bannder display and clicked table
    public String getBannerCompany_id() throws ClirNetAppException {

        Cursor cursor = null;
        String returnString = ""; // Your default if none is found

        SQLiteDatabase db1 = null;

        try {
            db1 = dbHelper.getReadableDatabase();
            String query = "select company_id from company_banners order by company_id desc limit 1";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("company_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();

            throw new ClirNetAppException("something went wrong while getting company Id");
        } finally {

            if (cursor != null) {
                //close statment
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }

        }

        return returnString;
    }

    //get company id from banner table to store in bannder display and clicked table
    public int getBannerTypeId(String banner_image) throws ClirNetAppException {

        Cursor cursor = null;
        int returnValue = 0;
        SQLiteDatabase db1 = null;

        try {
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery("select banner_type from company_banners where image_name ='" + banner_image + "'", null);
            if (cursor.moveToFirst()) {
                returnValue = cursor.getInt(0);
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdCount");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnValue;
    }

    //get company image total count  from banner table to store in bannder display and clicked table
    public int getBanneImagesCount() throws ClirNetAppException {

        Cursor cursor = null;
        int returnValue = 0;
        SQLiteDatabase db1 = null;

        try {
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery("select count(banner_id) from company_banners where start_time<= DateTime('now') and end_time >= DateTime('now');", null);
            if (cursor.moveToFirst()) {
                returnValue = cursor.getInt(0);
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting banner images Count");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnValue;
    }

    //get prescription image total count  from banner table to store in bannder display and clicked table
    public int getPrescriptionImagesCount() throws ClirNetAppException {

        Cursor cursor = null;
        int returnValue = 0;
        SQLiteDatabase db1 = null;

        try {
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery("select count(prescription)from patient_history where length(prescription)>0;", null);
            if (cursor.moveToFirst()) {
                returnValue = cursor.getInt(0);
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting banner images Count");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnValue;
    }

    //get all patients  visits data which is yet to send to server
    public ArrayList<RegistrationModel> getAsynTaskStatus() throws ClirNetAppException {

        ArrayList<RegistrationModel> VisitidList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select process, start_time,end_time from asynctascrun_status ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel ids = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2));

                    VisitidList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientVisitIdsFalg0");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return VisitidList;
    }

    //get all patients  visits data which is yet to send to server
    public ArrayList<RegistrationModel> getDownloadImageStatus() throws ClirNetAppException {

        ArrayList<RegistrationModel> VisitidList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select banner_id,image_name,img_download_start_time,img_download_end_time from company_banners ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel ids = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

                    VisitidList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientVisitIdsFalg0");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return VisitidList;

    }

    //get all the patient imp data from db, which will used in Consultation fragments and home fragments 111
    public ArrayList<RegistrationModel> getIncompleteRecordList(String formatedDate) throws ClirNetAppException {

        ArrayList<RegistrationModel> incompletePrescrList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select id,prescription,added_on,added_by,status,phonenumber,email,refered_by,refered_to from prescription_queue where added_on = '" + formatedDate + "';";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));

                    incompletePrescrList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {

            throw new ClirNetAppException("Something went wrong while getting getPatientList");
        } finally {
            //create method & pass cursor & db1 ref.
            if (cursor != null) {
                cursor.close();
            }
            if (database1 != null) {
                database1.close();
            }
        }

        return incompletePrescrList;

    }

    //get all the patient imp data from db, which will used in Consultation fragments and home fragments 111
    public ArrayList<RegistrationModel> getIncompleteRecordList() throws ClirNetAppException {

        ArrayList<RegistrationModel> incompletePrescrList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select id,prescription,added_on,added_by,status,phonenumber,email,refered_by,refered_to from prescription_queue;";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));

                    incompletePrescrList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {

            throw new ClirNetAppException("Something went wrong while getting getPatientList");
        } finally {
            //create method & pass cursor & db1 ref.
            if (cursor != null) {
                cursor.close();
            }
            if (database1 != null) {
                database1.close();
            }
        }

        return incompletePrescrList;

    }


    //get all the patient imp data from db, which will used in Consultation fragments and home fragments
    public ArrayList<LoginModel> getPatientVisitDate() throws ClirNetAppException {

        ArrayList<LoginModel> hotelList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select key_visit_id,visit_date,added_on,actual_follow_up_date from patient_history;";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    LoginModel user = new LoginModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

                    hotelList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {

            throw new ClirNetAppException("Something went wrong while getting getPatientList");
        } finally {
            //create method & pass cursor & db1 ref.
            if (cursor != null) {
                cursor.close();
            }
            if (database1 != null) {
                database1.close();
            }
        }

        return hotelList;
    }

    //update the flag once data send to server successfully
    public void updateVisitDate(String visit_date, String key_visit_id, String actual_follow_up_date, String added_on) {
        SQLiteDatabase db = null;

        try {
            db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("visit_date", visit_date);
            values.put("actual_follow_up_date", actual_follow_up_date);
            values.put("added_on", added_on);

            db.update("patient_history", values, "key_visit_id=?", new String[]{key_visit_id});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //get max count of patient id
    public int getPrescriptionQueueCount() throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        int returnValue = 0;

        try {
            db1 = dbHelper.getReadableDatabase();
            //stmt = db1.compileStatement("select max(patient_id) from patient");
            cursor = db1.rawQuery("select count(id) from prescription_queue", null);
            if (cursor.moveToFirst()) {
                returnValue = cursor.getInt(0);
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getPatientIdCount");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return returnValue;
    }

    //get all the patient imp data from db, which will used in Consultation fragments and home fragments
    public ArrayList<RegistrationModel> getAssociateData(int page, int limit) throws ClirNetAppException {

        ArrayList<RegistrationModel> associateList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select id,name,associate_type,phonenumber,speciality,added_on,modified_counter,phone_type,isd_code,email,associate_address,city,associate_state" +
                    ",pin_code,district,title,contactforpatient,selectedIsd_code_altType,selectedcontactForPatientType from associate_master limit " + page + "," + limit + ";";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18));

                    associateList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getAssociateData");
        } finally {
            //create method & pass cursor & db1 ref.
            if (cursor != null) {
                cursor.close();
            }
            if (database1 != null) {
                database1.close();
            }
        }

        return associateList;
    }

    public ArrayList<RegistrationModel> getAssociateDataIdName() throws ClirNetAppException {

        ArrayList<RegistrationModel> associateList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {

            String selectQuery = "select id,name,speciality from associate_master";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel ids = new RegistrationModel(cursor.getString(0), cursor.getString(1));

                    associateList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getAssociateDataIdName");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return associateList;

    }

    public ArrayList<HashMap<String, String>> getAllDataAssociateMaster() throws ClirNetAppException {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "select id,name,speciality from associate_master";
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("ID", cursor.getString(0));
                    map.put("NAME", cursor.getString(1));
                    map.put("SPECIALITY", cursor.getString(2));
                    wordList.add(map);
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

    //get docot membership id from db
    public String getNameByIdAssociateMaster(String id) throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();
            String query = "select title,name from associate_master  where id='" + id + "' limit 1";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                returnString = cursor.getString(cursor.getColumnIndex("name"));
                //returnString=nameAlias+""+returnString;
            }

        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getNameByIdAssociateMaster");
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

    public ArrayList<RegistrationModel> getAssociateMasterFalg0() throws ClirNetAppException {

        ArrayList<RegistrationModel> idList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select  id from associate_master where flag = 0  ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel ids = new RegistrationModel(cursor.getString(0));

                    idList.add(ids);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getAssociateMasterFalg0");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return idList;
    }

    public List<String> getSpecialityName() throws ClirNetAppException {

        List<String> idList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select speciality_name from Speciality  ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    idList.add(cursor.getString(0));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getSpecialityName");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return idList;
    }

    public ArrayList<HashMap<String, String>> getIdNameDataAssociateMaster(String name) throws ClirNetAppException {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();
        String selectQuery = "select id,speciality from associate_master where name='" + name + "'";
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("ID", cursor.getString(0));
                    map.put("SPECIALITY", cursor.getString(1));
                    // map.put("SPECIALITY", cursor.getString(2));
                    wordList.add(map);
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

    public String getTitleByNameAssociateMaster(String name) throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();
            String query = "select title from associate_master  where name='" + name + "' limit 1";


            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                /*String nameAlias=cursor.getString(0);
                if (nameAlias == null) {
                    nameAlias = "";
                }*/
                returnString = cursor.getString(cursor.getColumnIndex("title"));
                //returnString=nameAlias+""+returnString;
            }

        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getNameByIdAssociateMaster");
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

    public ArrayList<HashMap<String, Integer>> getMinMaxVitals() throws ClirNetAppException {
        ArrayList<HashMap<String, Integer>> vitalsList;
        vitalsList = new ArrayList<>();
        //String selectQuery = "select min(cast(ph.weight as INTEGER))  as minWeight,max(cast(ph.weight as INTEGER))  as maxWeight,min(cast(ph.height as INTEGER))  as minHeight, max(cast(ph.height as INTEGER)) as maxHeight, ROUND(min(cast(ph.bmi as FLOAT)))  as minBmi , ROUND(max(cast(bmi as FLOAT))) as maxBmi,min(cast(ph.pulse as INTEGER))  as minPulse,max(cast(ph.pulse as INTEGER))  as maxPulse,min(cast(temperature as integer))  as minTemp,max(cast(ph.temperature as integer))  as maxTemp,min(cast(bp_low as integer))  as minSystole,max(cast(ph.bp_low as integer))  as maxSystole,min(cast(bp_high as integer))  as minDistole,max(cast(ph.bp_high as integer))  as maxDistole, min(cast(ph.sugar_fasting as integer))  as minSugarFPG,max(cast(ph.sugar_fasting as integer))  as maxSugarFPG, min(cast(ph.sugar as integer))  as minSugarPPG,max(cast(ph.sugar as integer))as maxSugarPPG  from patient_history ph,table_investigation ti";

        String selectQuery = "select min(cast(ph.weight as INTEGER))  as minWeight, max(cast(ph.weight as INTEGER))  as maxWeight,min(cast(ph.height as INTEGER))  as minHeight, max(cast(ph.height as INTEGER)) as maxHeight, ROUND(min(cast(ph.bmi as FLOAT)))  as minBmi , ROUND(max(cast(bmi as FLOAT))) as maxBmi,min(cast(ph.pulse as INTEGER))  as minPulse,max(cast(ph.pulse as INTEGER))  as maxPulse,min(cast(temperature as integer))  as minTemp,max(cast(ph.temperature as integer))  as maxTemp,min(cast(bp_low as integer))  as minSystole,max(cast(ph.bp_low as integer))  as maxSystole,min(cast(bp_high as integer))  as minDistole,max(cast(ph.bp_high as integer))  as maxDistole, min(cast(ti.sugar_fasting as integer))  as minSugarFPG,max(cast(ti.sugar_fasting as integer))  as maxSugarFPG, min(cast(ti.sugar as integer))  as minSugarPPG,max(cast(ti.sugar as integer))as maxSugarPPG,min(ph.spo2) as minSpo2,max(ph.spo2) as maxSpo2,min(ph.respiration)  as minRespiration,max(ph.respiration)as maxRespiration  from patient_history ph LEFT JOIN table_investigation ti";
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(selectQuery, null);
            Integer returnStringMin, returnStringMax;
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, Integer> map = new HashMap<>();
                    returnStringMin = cursor.getInt(cursor.getColumnIndex("minWeight"));
                    returnStringMax = cursor.getInt(cursor.getColumnIndex("maxWeight"));
                   int returnStringMax1 = cursor.getInt(cursor.getColumnIndex("maxWeight"));
                 //   Log.e("returnStringMax1", "  " + returnStringMin + "  " + returnStringMax1);
                    if (returnStringMin != null && !returnStringMin.equals("")) {
                        map.put("MINWEIGHT", returnStringMin);
                        map.put("MAXWEIGHT", returnStringMax);
                    } else {
                        map.put("MINWEIGHT", 0);
                        map.put("MAXWEIGHT", 0);
                    }
                    Integer minHeight = cursor.getInt(cursor.getColumnIndex("minHeight"));
                    Integer maxHeight = cursor.getInt(cursor.getColumnIndex("maxHeight"));
                    if (minHeight != null && !minHeight.equals("")) {
                        map.put("MINHEIGHT", minHeight);
                        map.put("MAXHEIGHT", maxHeight);
                    } else {
                        map.put("MINHEIGHT", 0);
                        map.put("MAXHEIGHT", 0);
                    }
                    Integer minBmi = cursor.getInt(cursor.getColumnIndex("minBmi"));
                    Integer maxBmi = cursor.getInt(cursor.getColumnIndex("maxBmi"));

                    if (minBmi != null && !minBmi.equals("") && !maxBmi.equals("")) {
                        map.put("MINBMI", minBmi);
                        map.put("MAXBMI", maxBmi);
                    } else {
                        map.put("MINBMI", 0);
                        map.put("MAXBMI", 0);
                    }

                    Integer minPulse = cursor.getInt(cursor.getColumnIndex("minPulse"));
                    Integer maxPulse = cursor.getInt(cursor.getColumnIndex("maxPulse"));

                    if (minPulse != null && !minPulse.equals("") && !maxPulse.equals("")) {
                        map.put("MINPULSE", minPulse);
                        map.put("MAXPULSE", maxPulse);
                    } else {
                        map.put("MINPULSE", 0);
                        map.put("MAXPULSE", 0);
                    }
                    Integer minTemp = cursor.getInt(cursor.getColumnIndex("minTemp"));
                    Integer maxTemp = cursor.getInt(cursor.getColumnIndex("maxTemp"));

                    if (minTemp != null && !minTemp.equals("") && !maxTemp.equals("")) {
                        map.put("MINTEMP", minTemp);
                        map.put("MAXTEMP", maxTemp);
                    } else {
                        map.put("MINTEMP", 0);
                        map.put("MAXTEMP", 0);
                    }

                    Integer minSystole = cursor.getInt(cursor.getColumnIndex("minSystole"));
                    Integer maxSystole = cursor.getInt(cursor.getColumnIndex("maxSystole"));

                    if (minSystole != null && !minSystole.equals("") && !maxSystole.equals("")) {
                        map.put("MINSYSTOLE", minSystole);
                        map.put("MAXSYSTOLE", maxSystole);
                    } else {
                        map.put("MINSYSTOLE", 0);
                        map.put("MAXSYSTOLE", 0);
                    }

                    Integer minDistole = cursor.getInt(cursor.getColumnIndex("minDistole"));
                    Integer maxDistole = cursor.getInt(cursor.getColumnIndex("maxDistole"));

                    if (minDistole.toString() != null && !minDistole.toString().equals("") && !maxDistole.toString().equals("")) {
                        map.put("MINDISTOLE", minDistole);
                        map.put("MAXDISTOLE", maxDistole);
                    } else {
                        map.put("MINSISTOLE", 0);
                        map.put("MAXDISTOLE", 0);
                    }
                    Integer minSugarFPG = cursor.getInt(cursor.getColumnIndex("minSugarFPG"));
                    Integer maxSugarFPG = cursor.getInt(cursor.getColumnIndex("maxSugarFPG"));

                    if (minSugarFPG != null && !minSugarFPG.equals("") && !maxSugarFPG.equals("")) {
                        map.put("MINSUGARFPG", minSugarFPG);
                        map.put("MAXSUGARFPG", maxSugarFPG);
                    } else {
                        map.put("MINSUGARFPG", 0);
                        map.put("MAXSUGARFPG", 0);
                    }
                    Integer minSugarPPG = cursor.getInt(cursor.getColumnIndex("minSugarPPG"));
                    Integer maxSugarPPG = cursor.getInt(cursor.getColumnIndex("maxSugarPPG"));

                    if (minSugarPPG != null && !minSugarPPG.equals("") && !maxSugarPPG.equals("")) {
                        map.put("MINSUGARPPG", minSugarPPG);
                        map.put("MAXSUGARPPG", maxSugarPPG);
                    } else {
                        map.put("MINSUGARPPG", 0);
                        map.put("MAXSUGARPPG", 0);
                    }
                    Integer minSpo2 = cursor.getInt(cursor.getColumnIndex("minSpo2"));
                    Integer maxSpo2= cursor.getInt(cursor.getColumnIndex("maxSpo2"));

                    if (minSpo2.toString() != null && !maxSpo2.toString().equals("") && !maxSpo2.toString().equals("")) {
                        map.put("MINSPO2", minSpo2);
                        map.put("MAXSPO2", maxSpo2);
                    } else {
                        map.put("MINSPO2", 0);
                        map.put("MAXSPO2", 0);
                    }
                    Integer minRespiration = cursor.getInt(cursor.getColumnIndex("minRespiration"));
                    Integer maxRespiration= cursor.getInt(cursor.getColumnIndex("maxRespiration"));

                    if (minRespiration.toString() != null && !maxRespiration.toString().equals("") && !maxRespiration.toString().equals("")) {
                        map.put("MINRESPIRATION", minRespiration);
                        map.put("MAXRESPIRATION", maxRespiration);
                    } else {
                        map.put("MINRESPIRATION", 0);
                        map.put("MAXRESPIRATION", 0);
                    }
                    vitalsList.add(map);
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
        return vitalsList;
    }

    public ArrayList<String> getImages() throws ClirNetAppException {

        ArrayList<String> imageList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select prescription  from patient_history where prescription!='null' and prescription !=''";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    String value = cursor.getString(cursor.getColumnIndex("prescription"));
                    imageList.add(value);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting Images");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return imageList;

    }
    public ArrayList<String> getPatientImages() throws ClirNetAppException {

        ArrayList<String> imageList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select photo  from patient where photo!='null' and photo !=''";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    String value = cursor.getString(cursor.getColumnIndex("photo"));
                    imageList.add(value);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting Images");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return imageList;

    }

   /* public ArrayList<RegistrationModel> getInvestigationDeatils(String visit_id) throws ClirNetAppException {

        ArrayList<RegistrationModel> investigationList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
           String selectQuery="select ecg,sugar,sugar_fasting,acer,pft,hba1c,serem_urea,lipid_profile_tc,lipid_profile_tg,lipid_profile_ldl,lipid_profile_vhdl,lipid_profile_hdl  from table_investigation where key_visit_id =" + visit_id + " limit 1;";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11));
                    investigationList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getInvestigationDeatils");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return investigationList;

    }*/

    public ArrayList<RegistrationModel> getInvestigationDeatils(String visit_id) throws ClirNetAppException {

        ArrayList<RegistrationModel> investigationList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery="select ecg,sugar,sugar_fasting,sugar_rbs,acer,pft,hba1c,serem_urea,lipid_profile_tc,lipid_profile_tg,lipid_profile_ldl,lipid_profile_vhdl,lipid_profile_hdl,lipid_profile_tch,hdl_ldl_ratio,hb,platelet_count,esr,dcl,dcn,dce,dcm,dcb,total_bilirubin,direct_bilirubin,indirect_bilirubin,sgpt,sgot,ggt," +
                    "total_protein,albumin,globulin,ag_ratio,urine_pus_cell,urine_rbc,urine_cast,urine_protein,urine_crystal,microalbuminuria,serum_creatinine,acr,tsh,t3,t4  from table_investigation where key_visit_id =" + visit_id + " limit 1;";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31),
                            cursor.getString(32), cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(43));
                    investigationList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getInvestigationDeatils");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return investigationList;

    }
    public ArrayList<RegistrationModel> getHealthAndLifestyle(String patientId) throws ClirNetAppException {

        ArrayList<RegistrationModel> healthLifestyleList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery="select alcohol_consumption,pegs_count,stress_level,smoker_type,stick_count,life_style,lactose_tolerance,food_preference,food_habit,excercise,chewing_tobaco,binge_eating,allergies,sexually_active,sticks_selected_gap,packs_selected_gap,tobaco_other,drug_consumption,drug_consumption_type,sleep_status,last_smoke_year,last_drink_year from health_and_lifestyle where patient_id = " + patientId + " order by  id desc limit 1;";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15),cursor.getString(16),cursor.getString(17),cursor.getString(18),cursor.getString(19),cursor.getString(20),cursor.getString(21));

                    healthLifestyleList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getInvestigationDeatils");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return healthLifestyleList;

    }
    public HashMap<String, String> getReligionOccupation(String patientId ) throws ClirNetAppException {
        HashMap<String, String> user = new HashMap<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            String query = "select religion,occupation,blood_group,maritial_status,income_range,age_calculated from patient where patient_id="+patientId+"";
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    user.put("religion", cursor.getString(0));
                    user.put("occupation", cursor.getString(1));
                    user.put("bloodGroup", cursor.getString(2));
                    user.put("maritalStatus", cursor.getString(3));
                    user.put("incomeRange", cursor.getString(4));
                    user.put("ageCalculated", cursor.getString(5));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getReligionOccupation");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return user;
    }
    public ArrayList<RegistrationModel> getObservation(String strVisitId) throws ClirNetAppException {

        ArrayList<RegistrationModel> observationList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {

            String selectQuery="select pallor,pallor_description,cyanosis,cyanosis_description,clubbing,clubbing_description,tremors,tremors_description,icterus,icterus_description,oedema,oedema_description,calf_tenderness,calf_tenderness_description,lympadenopathy,lympadenopathy_description from observation where key_visit_id = " + strVisitId + " order by  id desc limit 1;";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15));
                    observationList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getInvestigationDeatils");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return observationList;

    }
    public ArrayList<HashMap<String, Integer>> getMinMaxInvestigation() throws ClirNetAppException {
        ArrayList<HashMap<String, Integer>> vitalsList;
        vitalsList = new ArrayList<>();
        //String selectQuery = "select min(cast(ph.weight as INTEGER))  as minWeight,max(cast(ph.weight as INTEGER))  as maxWeight,min(cast(ph.height as INTEGER))  as minHeight, max(cast(ph.height as INTEGER)) as maxHeight, ROUND(min(cast(ph.bmi as FLOAT)))  as minBmi , ROUND(max(cast(bmi as FLOAT))) as maxBmi,min(cast(ph.pulse as INTEGER))  as minPulse,max(cast(ph.pulse as INTEGER))  as maxPulse,min(cast(temperature as integer))  as minTemp,max(cast(ph.temperature as integer))  as maxTemp,min(cast(bp_low as integer))  as minSystole,max(cast(ph.bp_low as integer))  as maxSystole,min(cast(bp_high as integer))  as minDistole,max(cast(ph.bp_high as integer))  as maxDistole, min(cast(ph.sugar_fasting as integer))  as minSugarFPG,max(cast(ph.sugar_fasting as integer))  as maxSugarFPG, min(cast(ph.sugar as integer))  as minSugarPPG,max(cast(ph.sugar as integer))as maxSugarPPG  from patient_history ph,table_investigation ti";

       // String selectQuery = "select min(cast(ph.weight as INTEGER))  as minWeight, max(cast(ph.weight as INTEGER))  as maxWeight,min(cast(ph.height as INTEGER))  as minHeight, max(cast(ph.height as INTEGER)) as maxHeight, ROUND(min(cast(ph.bmi as FLOAT)))  as minBmi , ROUND(max(cast(bmi as FLOAT))) as maxBmi,min(cast(ph.pulse as INTEGER))  as minPulse,max(cast(ph.pulse as INTEGER))  as maxPulse,min(cast(temperature as integer))  as minTemp,max(cast(ph.temperature as integer))  as maxTemp,min(cast(bp_low as integer))  as minSystole,max(cast(ph.bp_low as integer))  as maxSystole,min(cast(bp_high as integer))  as minDistole,max(cast(ph.bp_high as integer))  as maxDistole, min(cast(ti.sugar_fasting as integer))  as minSugarFPG,max(cast(ti.sugar_fasting as integer))  as maxSugarFPG, min(cast(ti.sugar as integer))  as minSugarPPG,max(cast(ti.sugar as integer))as maxSugarPPG  from patient_history ph LEFT JOIN table_investigation ti";
       String selectQuery="select min(cast(ti.hba1c as integer))as minHbA1c,max(cast(ti.hba1c as integer))as maxHbA1c,min(cast(ti.acer as integer))as minAcer,max(cast(ti.acer as integer))as maxAcer, \n" +
               "min(cast(ti.serem_urea as integer))as minSerumUrea,max(cast(ti.serem_urea as integer))as maxSerumUrea,min(cast(ti.lipid_profile_hdl as integer))as minHDL,max(cast(ti.lipid_profile_hdl as integer))as maxHDL,\n" +
               "min(cast(ti.lipid_profile_tc as integer))as minTC,max(cast(ti.lipid_profile_tc as integer))as maxTC,min(cast(ti.lipid_profile_tg as integer))as minTG,max(cast(ti.lipid_profile_tg as integer))as maxTG,\n" +
               "min(cast(ti.lipid_profile_ldl as integer))as minLDL,max(cast(ti.lipid_profile_ldl as integer))as maxLDL,min(cast(ti.lipid_profile_vhdl as integer))as minVHDL,max(cast(ti.lipid_profile_vhdl as integer))as maxVHDL from table_investigation ti";

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(selectQuery, null);
            Integer minHbA1c, maxHbA1c;
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, Integer> map = new HashMap<>();
                    minHbA1c = cursor.getInt(cursor.getColumnIndex("minHbA1c"));
                    maxHbA1c = cursor.getInt(cursor.getColumnIndex("maxHbA1c"));

                   // Log.e("returnStringMax1", "  " + returnStringMin + "  " + returnStringMax1);
                    if (minHbA1c != null && !minHbA1c.equals("")) {
                        map.put("MINHbA1c", minHbA1c);
                        map.put("MAXHbA1c", maxHbA1c);
                    } else {
                        map.put("MINHbA1c", 0);
                        map.put("MAXHbA1c", 0);
                    }
                    Integer minAcer = cursor.getInt(cursor.getColumnIndex("minAcer"));
                    Integer maxAcer = cursor.getInt(cursor.getColumnIndex("maxAcer"));
                    if (minAcer != null && !maxAcer.equals("")) {
                        map.put("MINAcer", minAcer);
                        map.put("MAXAcer", maxAcer);
                    } else {
                        map.put("MINAcer", 0);
                        map.put("MAXAcer", maxAcer);
                    }
                    Integer minSerumUrea = cursor.getInt(cursor.getColumnIndex("minSerumUrea"));
                    Integer maxSerumUrea = cursor.getInt(cursor.getColumnIndex("maxSerumUrea"));

                    if (minSerumUrea != null && !minSerumUrea.equals("") && !maxSerumUrea.equals("")) {
                        map.put("MINSerumUrea", minSerumUrea);
                        map.put("MAXSerumUrea", maxSerumUrea);
                    } else {
                        map.put("MINSerumUrea", 0);
                        map.put("MAXSerumUrea", 0);
                    }


                    Integer minTC = cursor.getInt(cursor.getColumnIndex("minTC"));
                    Integer maxTC = cursor.getInt(cursor.getColumnIndex("maxTC"));

                    if (minTC != null && !minTC.equals("") && !maxTC.equals("")) {
                        map.put("MINTC", minTC);
                        map.put("MAXTC", 0);
                    } else {
                        map.put("MINTC", 0);
                        map.put("MAXTC", maxTC);
                    }

                    Integer minTG = cursor.getInt(cursor.getColumnIndex("minTG"));
                    Integer maxTG = cursor.getInt(cursor.getColumnIndex("maxTG"));

                    if (minTG != null && !minTG.equals("") && !maxTG.equals("")) {
                        map.put("MINTG", minTG);
                        map.put("MAXTG", maxTG);
                    } else {
                        map.put("MINTG", 0);
                        map.put("MAXTG", 0);
                    }
                    Integer minHDL = cursor.getInt(cursor.getColumnIndex("minHDL"));
                    Integer maxHDL = cursor.getInt(cursor.getColumnIndex("maxHDL"));

                    if (minHDL != null && !minHDL.equals("") && !maxHDL.equals("")) {
                        map.put("MINHDL", minHDL);
                        map.put("MAXHDL", maxHDL);
                    } else {
                        map.put("MINHDL", 0);
                        map.put("MAXHDL", 0);
                    }
                    Integer minLDL = cursor.getInt(cursor.getColumnIndex("minLDL"));
                    Integer maxLDL = cursor.getInt(cursor.getColumnIndex("maxLDL"));

                    if (minLDL.toString() != null && !minLDL.toString().equals("") && !maxLDL.toString().equals("")) {
                        map.put("MINLDL", minLDL);
                        map.put("MAXLDL", 0);
                    } else {
                        map.put("MINLDL", 0);
                        map.put("MAXLDL", 0);
                    }
                    Integer minVHDL = cursor.getInt(cursor.getColumnIndex("minVHDL"));
                    Integer maxVHDL = cursor.getInt(cursor.getColumnIndex("maxVHDL"));

                    if (minVHDL != null && !minVHDL.equals("") && !maxVHDL.equals("")) {
                        map.put("MINVHDL", minVHDL);
                        map.put("MAXVHDL", maxVHDL);
                    } else {
                        map.put("MINVHDL", 0);
                        map.put("MAXVHDL", 0);
                    }

                    vitalsList.add(map);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getMinMaxInvestigation");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return vitalsList;

    }

    public int getTodaysPatientCount(String date) {

        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        int numRows = 0;
        try {
            numRows = (int) DatabaseUtils.longForQuery(db1, "SELECT Count(*) FROM patient_history where  visit_date = '" + date + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db1 != null) {
                db1.close();

            }
        }

        return numRows;
    }

    public int getTomorrowsFollowUpCount(String date) {

        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        int numRows = 0;
        try {
            numRows = (int) DatabaseUtils.longForQuery(db1, "SELECT Count(*) FROM patient_history where  actual_follow_up_date  = '" + date + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (db1 != null) {
                db1.close();

            }
        }

        return numRows;
    }

    public ArrayList<String> getCurrentDateFollowUpPatientIds(String date) throws ClirNetAppException {

        ArrayList<String> patientList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select patient_id from patient_history where actual_follow_up_date= '" + date + "'";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    String value = cursor.getString(cursor.getColumnIndex("patient_id"));
                    patientList.add(value);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException(" Something went wrong while geting getCurrentDateFollowUpPatientIds ");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }

        return patientList;
    }

    public int getTodaysSuccessfulFollowups(String date,String[] list) {

        SQLiteDatabase db1 = dbHelper.getReadableDatabase();


        int numRows = 0;
        String query = "select count(*) from patient_history "
                + " WHERE patient_id IN (" + makePlaceholders(list.length) + ") and visit_date = '" + date + "' ";

        try {
            numRows = (int) DatabaseUtils.longForQuery(db1, query, list);
           // Log.e("query","  "+numRows);

        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            if (db1 != null) {
                db1.close();

            }
        }

        return numRows;
    }
    private String makePlaceholders(int len) {

        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    public  ArrayList<Counts> getMostConnectedDr(String columnName) throws ClirNetAppException {

        ArrayList<Counts> list = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {

            String query = "SELECT " + columnName + " as id , COUNT(*) AS `num`,status FROM patient_history where " + columnName + " !='null' and " + columnName + " !='' GROUP BY " + columnName + " order by num desc limit 2 ";
            db = dbHelper.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    String id=cursor.getString(cursor.getColumnIndex("id"));
                    String num =cursor.getString(cursor.getColumnIndex("num"));

                    Counts rm=new Counts(id, num,cursor.getString(1));
                    list.add(rm);

                } while (cursor.moveToNext());
            }


        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting countPerDa");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return list;
    }
    public  ArrayList<Counts> getMostConnectedDr(String columnName,String fromDate,String toDate) throws ClirNetAppException {

        ArrayList<Counts> list = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {

            String query = "SELECT " + columnName + " as id , COUNT(*) AS `num`,status FROM patient_history where " + columnName + " !='null' and " + columnName + " !=''  and date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2)) Between '" + fromDate + "' AND '" + toDate + "' GROUP BY " + columnName + " order by num desc limit 20 ";
            db = dbHelper.getReadableDatabase();

            //Log.e("query","  "+query);

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    String id=cursor.getString(cursor.getColumnIndex("id"));
                    String num =cursor.getString(cursor.getColumnIndex("num"));

                    Counts rm=new Counts(id, num,cursor.getString(1));
                    list.add(rm);

                } while (cursor.moveToNext());
            }


        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting countPerDa");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return list;
    }
    //get docot membership id from db
    public String getAssociateName(String id) throws ClirNetAppException {

        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();

            String query = "select name , title from associate_master where id="+ id +" ";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("name"));
                String  title = cursor.getString(cursor.getColumnIndex("title"));
                if(title!=null){
                    returnString= title + " " +returnString;
                }else{
                    returnString= " " +returnString;
                }



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
    //get docot membership id from db
    public String getLastWeekSuccessfulFollowups(String fromDate,String toDate) throws ClirNetAppException {

        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();
           // follow_up_status ='FollowUp' and
            String query=" SELECT count(ph.id) as count FROM patient p , patient_history ph   WHERE p.patient_id = ph.patient_id and date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2)) Between '" + fromDate + "' AND '" + toDate + "' ";
           // String query = "select count(id) as count from patient_history where  visit_date Between '" + fromDate + "' AND '" + toDate + "' ";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {

                returnString = cursor.getString(cursor.getColumnIndex("count"));

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

    //get docot membership id from db
    public String getSpciality(String id) throws ClirNetAppException {

        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();

            String query = "select speciality from associate_master where id="+ id +" ";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("speciality"));
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
    public String getAssociateType(String id) throws ClirNetAppException {

        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();

            String query = "select associate_type from associate_master where id="+ id +" ";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("associate_type"));
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getAssociateType");
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

    public ArrayList<RegistrationModel> getMasterSession(String date) throws ClirNetAppException {

        ArrayList<RegistrationModel> healthLifestyleList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery="select ms_id,ms_topic,ms_brief,ms_date_time,doctor_name,specialities_name,ms_cat_name,ms_url from recent_data_msession where ms_date_time > '" + date + "' order by  ms_date_time asc limit 10;";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            //Log.e("selectQuery","  "+selectQuery);

            // looping through all rows and adding to list

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7));

                    healthLifestyleList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getInvestigationDeatils");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        return healthLifestyleList;

    }

    public HashMap<String, String> getCompandiumFaq() throws ClirNetAppException {
        HashMap<String, String> compData = new HashMap<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            String query = "select kcomp_qa_question,comp_qa_answer,comp_url from recent_data_companium  order by  id desc limit 1";
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    compData.put("kcomp_qa_question", cursor.getString(0));
                    compData.put("comp_qa_answer", cursor.getString(1));
                    compData.put("comp_url",cursor.getString(2));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getReligionOccupation");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return compData;
    }

    public HashMap<String, String> getAge(String patId) throws ClirNetAppException {
        HashMap<String, String> compData = new HashMap<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            //String query = "select added_on,age from patient  where patient_id ="+ patId +" ";
            String query="select ph.added_on,p.age,ph.key_visit_id from patient p,patient_history ph  where p.patient_id=ph.patient_id and  p.patient_id = "+ patId +" order by ph.key_visit_id desc limit 1";
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    compData.put("added_on", cursor.getString(0));
                    compData.put("age", cursor.getString(1));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while geting getReligionOccupation");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return compData;
    }

    //get docot membership id from db
    public String getKCapData() throws ClirNetAppException {

        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();

            String query = "select kc_text from recent_data_kcap order by kc_id  desc limit 1";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("kc_text"));
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getKCapData");
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

    public String getLastUpdatedDate() throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();

            String query = "select added_on from recent_data_msession order by id desc limit 1";

            cursor = db1.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("added_on"));
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong while getting getKCapData");
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
}





