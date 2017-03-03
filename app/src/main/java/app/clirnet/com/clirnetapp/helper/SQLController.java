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
            dbHelper = new SQLiteHandler(ourcontext);
            database = dbHelper.getWritableDatabase();
        }
        return this;

    }

    public void close() {

        if (database != null) {
            dbHelper.close();
        }

    }

    //method to fetch user name and password
    public ArrayList<LoginModel> getUserLoginRecrodsNew() throws ClirNetAppException {
        ArrayList<LoginModel> loginList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = " SELECT name,password  FROM user  order by id desc limit 1 ";

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
    public ArrayList<RegistrationModel> getPatientList() throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id order by ph.key_visit_id desc";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21));

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

    //get all the patient imp data from db, which will used in Consultation fragments and home fragments
    public ArrayList<RegistrationModel> getPatientList(String date) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,ph.tests,ph.drugs,p.alternate_no,ph.height,ph.bmi,sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd  FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.visit_date = '" + date + "' and ph.added_on = '" + date + "' or ph.modified_on='"+date+"' order by ph.key_visit_id desc";

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


    //get all the patient imp data from db, which will used in Consultation fragments and home fragments
    public ArrayList<RegistrationModel> getPatientListnew(String date) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {

            // String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.actual_follow_up_date = '" + date + "' order by ph.key_visit_id  desc";
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,ph.tests,ph.drugs,p.alternate_no,ph.height,ph.bmi,sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.actual_follow_up_date = '" + date + "' order by ph.key_visit_id  desc ";
            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                   /* RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21));
*/
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
            //  String selectQuery ="SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.visit_date like '"+ "%" + date +"'order by ph.key_visit_id  desc limit 25";
            //  String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.visit_date = '" + date + "' order by ph.key_visit_id  desc";
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,ph.tests,ph.drugs,p.alternate_no,ph.height,ph.bmi,sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.visit_date = '" + date + "' order by ph.key_visit_id  desc ";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                   /* RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21));*/
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
            //stmt = db1.compileStatement("select max(patient_id) from patient");
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
            //stmt = db1.compileStatement("select max(patient_id) from patient");
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
    public ArrayList<RegistrationModel> getFilterDatanew(String fname, String lname, String gender, String phoneno, String age, ArrayList sex, ArrayList ageGap, ArrayList ailment, int page, int limit) throws ClirNetAppException {

        SQLiteDatabase db1 = null;
        Cursor cursor = null;

        Set<RegistrationModel> pList1 = new LinkedHashSet<>();
        try {
            open();
            String selectQuery = "select p.patient_id,p.first_name,p.middle_name,p.last_name ,p.dob ,p.gender,p.age,p.phonenumber,p.language,p.photo,ph.follow_up_date,ph.days,ph.months,ph.weeks,ph.ailment,ph.prescription,ph.clinical_notes,ph.added_on,ph.modified_on,ph.actual_follow_up_date,ph.action, p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,p.alternate_no,p.alternate_phone_type,p.phone_type  from patient p,patient_history ph   where p.patient_id=ph.patient_id";//and  p.first_name like '%" + fname + "%' and p.last_name like '%" + lname + "%'";

            String countQuery = "select  COUNT (*) as count from patient p,patient_history ph   where p.patient_id=ph.patient_id ";

            //  String selectQuery = "select p.patient_id,p.first_name,p.middle_name,p.last_name ,p.dob ,p.gender,p.age,p.phonenumber,p.language,p.photo,ph.follow_up_date,ph.days,ph.months,ph.weeks,ph.ailment,ph.prescription,ph.clinical_notes,ph.added_on,ph.modified_on,ph.actual_follow_up_date,ph.action from patient p,patient_history ph   where p.patient_id=ph.patient_id and  p.first_name like '%" + fname + "%' and p.last_name like '%" + lname + "%' and ( p.gender like '%" + male + "%' or p.gender like '%" + female + "%' and p.gender like '%" + other + "%' or p.gender like '%" + na + "%' )and p.phonenumber like '%" + phoneno + "%' and p.age like '%" + age + "%'  order by ph.key_visit_id desc limit 30;";
            if (fname != null) {
                selectQuery = selectQuery.concat(" and p.first_name like '%" + fname + "%'");
                countQuery = countQuery.concat(" and p.first_name like '%" + fname + "%'");
            }

            if (lname != null) {
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
                    // int val= Integer.parseInt(part1);
                    // int val2= Integer.parseInt(part2);
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
                        //countQuery = countQuery.concat(" OR  ph.symptoms like '%" + value + "%'");
                        // countQuery = countQuery.concat(" OR  ph.diagnosis like '%" + value + "%'");
                    } else {
                        selectQuery = selectQuery.concat(" AND ( ph.ailment like '%" + value + "%'" + " OR  ph.symptoms like '%" + value + "%'" + " OR  ph.diagnosis like '%" + value + "%'");

                        countQuery = countQuery.concat(" AND ( ph.ailment like '%" + value + "%'" + " OR  ph.symptoms like '%" + value + "%'" + " OR  ph.diagnosis like '%" + value + "%'");
                    }
                }

                selectQuery = selectQuery.concat(" ) ");
                countQuery = countQuery.concat(" ) ");
            }
            //count query here to get no of records fetch by query for pagination in page.
            countQuery = countQuery.concat("  and p.phonenumber like '%" + phoneno + "%' order by ph.key_visit_id desc ");
            //   String queryforCount=selectQuery.concat("  and p.phonenumber like '%" + phoneno + "%' order by ph.key_visit_id desc ");

            selectQuery = selectQuery.concat("  and p.phonenumber like '%" + phoneno + "%' order by ph.key_visit_id desc limit " + page + "," + limit + ";");

            new Counts().setCountQuery(countQuery);

            //  Log.e("selectQuery", "" + selectQuery);
            // Log.e("countQueryQuery", "" + countQuery);
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);


            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22), cursor.getString(23),
                            cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28));

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


    public ArrayList<RegistrationModel> getPatientListForPhoneNumberFilter(String number, int page, int limit) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {                                                                                                                                                                                                                                                                                                                                                                                          //p.first_name like '%" + fname + "%'
            // String selectQuery = "select  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date  from patient p , patient_history ph where ph.patient_id=p.patient_id and p.phonenumber like  '% " + number + " %'  group by ph.patient_id having count(*)>0   order by ph.key_visit_id desc " ;


            String countQuery = "select COUNT (*) as count from patient p,patient_history ph   where p.patient_id=ph.patient_id and p.phonenumber like '%" + number + "%' order by ph.key_visit_id desc ";

            String selectQuery = "select  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,ph.tests,ph.drugs,ph.tests,ph.drugs,p.alternate_no,ph.height,ph.bmi,sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd from patient p , patient_history ph where ph.patient_id=p.patient_id and p.phonenumber like '%" + number + "%' group by ph.patient_id having count(*)>0   order by ph.key_visit_id desc limit " + page + " , " + limit + ";";
            new Counts().setCountQueryforHomeFragmentNoFilter(countQuery);

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            //Log.d("selectQuery", "" + selectQuery);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31),
                            cursor.getString(32), cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36), cursor.getString(37), cursor.getString(38), cursor.getString(39), cursor.getString(40), cursor.getString(41), cursor.getString(42), cursor.getString(45), cursor.getString(46));

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
            String countQuery = new Counts().getCountQueryforHomeFragmentNoFilter();
            // Log.d("count", "" + countQuery);

            if (countQuery.length() > 1) {

                numRows = (int) DatabaseUtils.longForQuery(db1, countQuery, null);
                /// Log.d("count", "" + countQuery + "  " + numRows);
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

            String selectQuery = "select  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,ph.symptoms,ph.diagnosis  from patient_history  ph , patient p where p.patient_id=" + patient_id + " and  p.patient_id=ph.patient_id order by ph.key_visit_id desc ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            //Log.d("cursor", "" + cursor.getCount());

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

            //   String selectQuery = "select  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,ph.tests,ph.drugs,ph.tests,ph.drugs,ph.bmi,ph.height,p.alternate_no,sugar_fasting,p.alternate_phone_type  from patient_history  ph , patient p where p.patient_id=" + patient_id + " and  p.patient_id=ph.patient_id order by ph.key_visit_id desc ";

            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date, p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms, ph.diagnosis,ph.tests,ph.drugs,p.alternate_no,ph.height,ph.bmi,sugar_fasting,p.alternate_phone_type,p.phone_type,p.isd_code,p.alternate_no_isd from patient_history  ph, patient p where p.patient_id=" + patient_id + " and  p.patient_id=ph.patient_id order by ph.key_visit_id desc ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            // Log.d("cursor", "" + cursor.getCount());

            // looping through all rows and adding to list

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
            String selectQuery = "select patient_id, key_visit_id from patient_history where flag = 0  ";
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


            // stmt = db1.compileStatement("select phonenumber from doctor_perInfo order by phonenumber desc limit 1");
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
                //close statment
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
            // stmt = db1.compileStatement("select value from async order by id desc limit 1");

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
                            + SQLiteHandler.KEY_NAME + "='" + username + "'OR " + SQLiteHandler.PHONE_NUMBER + "='" + phoneNumber + "' )AND " + SQLiteHandler.KEY_PASSWORD + "='" + password + "'", null);
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
            // String selectQuery = "SELECT pvd.ailment as ailment FROM patient dpr , patient_history pvd WHERE dpr.patient_id = pvd.patient_id and pvd.is_deleted = 0 and pvd.is_disabled = 0 AND date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2))  Between Date('"+fromdate+"') AND Date('"+todate+"') GROUP BY pvd.visit_date LIMIT 0 , 30";
            String selectQuery = "SELECT pvd.symptoms as symptoms FROM \n" +
                    "patient dpr , patient_history pvd \n" +
                    "WHERE dpr.patient_id = pvd.patient_id \n" +
                    "AND date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2)) \n" +
                    "Between Date('" + fromdate + "') AND Date('" + todate + "')\n";

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
            // String selectQuery = "SELECT pvd.ailment as ailment FROM patient dpr , patient_history pvd WHERE dpr.patient_id = pvd.patient_id and pvd.is_deleted = 0 and pvd.is_disabled = 0 AND date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2))  Between Date('"+fromdate+"') AND Date('"+todate+"') GROUP BY pvd.visit_date LIMIT 0 , 30";
            String selectQuery = "SELECT pvd.diagnosis as diagnosis FROM \n" +
                    "patient dpr , patient_history pvd \n" +
                    "WHERE dpr.patient_id = pvd.patient_id \n" +
                    "AND date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2)) \n" +
                    "Between Date('" + fromdate + "') AND Date('" + todate + "')\n";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);


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
            // String selectQuery = "SELECT pvd.ailment as ailment FROM patient dpr , patient_history pvd WHERE dpr.patient_id = pvd.patient_id and pvd.is_deleted = 0 and pvd.is_disabled = 0 AND date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2))  Between Date('"+fromdate+"') AND Date('"+todate+"') GROUP BY pvd.visit_date LIMIT 0 , 30";
            String selectQuery = "SELECT pvd.ailment as ailment FROM \n" +
                    "patient dpr , patient_history pvd \n" +
                    "WHERE dpr.patient_id = pvd.patient_id \n" +
                    "AND date(substr(visit_date,7,4)||'-'||substr(visit_date,4,2)||'-'||substr(visit_date,1,2)) \n" +
                    "Between Date('" + fromdate + "') AND Date('" + todate + "')\n";

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
            //stmt = db1.compileStatement("select max(patient_id) from patient");
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
            //stmt = db1.compileStatement("select max(patient_id) from patient");
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
            //stmt = db1.compileStatement("select max(patient_id) from patient");
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
            //stmt = db1.compileStatement("select max(patient_id) from patient");
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
            // stmt = db1.compileStatement("select phonenumber from doctor_perInfo order by phonenumber desc limit 1");
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
            //stmt = db1.compileStatement("select max(patient_id) from patient");
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
            //stmt = db1.compileStatement("select max(patient_id) from patient");
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
            //stmt = db1.compileStatement("select max(patient_id) from patient");
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
    //get all the patient imp data from db, which will used in Consultation fragments and home fragments
    public ArrayList<RegistrationModel> getIncompleteRecordList(String formatedDate) throws ClirNetAppException {

        ArrayList<RegistrationModel> incompletePrescrList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select id,prescription,added_on,added_by,status,phonenumber,email from prescription_queue where added_on = '" + formatedDate + "';";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));

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

    //get all the patient imp data from db, which will used in Incomplete List  fragments
    public ArrayList<RegistrationModel> getIncompleteRecordList() throws ClirNetAppException {

        ArrayList<RegistrationModel> incompletePrescrList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select id,prescription,added_on,added_by,status,phonenumber,email from prescription_queue ;";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));

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
                    LoginModel user = new LoginModel(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3));

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
    public void updateVisitDate(String visit_date,String key_visit_id,String actual_follow_up_date, String added_on){
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
}





