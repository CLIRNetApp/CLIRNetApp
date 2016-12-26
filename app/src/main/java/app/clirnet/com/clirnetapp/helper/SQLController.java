package app.clirnet.com.clirnetapp.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
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
        } else {
            Log.e("DB Opended", "Database is allready opened");
        }

        return this;

    }

    public void close() {

        if (database != null) {
            dbHelper.close();
        }

    }


    //method to fetch user name and password
    public Cursor getUserLoginRecords() throws ClirNetAppException {

        String[] cols = {SQLiteHandler.KEY_NAME, SQLiteHandler.KEY_PASSWORD};
        Cursor cursor = null;
        SQLiteDatabase database1 = null;

        try {
            database1 = dbHelper.getReadableDatabase();
            cursor = database1.query(SQLiteHandler.TABLE_USER, cols, null,
                    null, null, null, null);
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong");
        }
        return cursor;
    }

    //method to fetch user name and password
    public ArrayList<LoginModel> getUserLoginRecrodsNew() throws ClirNetAppException {
        ArrayList<LoginModel> loginList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT name,password  FROM user limit 1   ";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    LoginModel user = new LoginModel(cursor.getString(0), cursor.getString(1));

                    loginList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            //TODO Create cutom exception and throw from here
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
            //TODO Create cutom exception and throw from here
            throw new ClirNetAppException("Something went wrong");
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
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,ph.tests,ph.drugs FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.visit_date = '" + date + "'  order by ph.key_visit_id desc";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31),
                            cursor.getString(32), cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36));

                    hotelList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            //TODO Create cutom exception and throw from here
            throw new ClirNetAppException("Something went wrong");
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

            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.actual_follow_up_date = '" + date + "' order by ph.key_visit_id  desc";

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
            //TODO Create cutom exception and throw from here
            throw new ClirNetAppException("Something went wrong");
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
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id where ph.visit_date = '" + date + "' order by ph.key_visit_id  desc";
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
            //TODO Create cutom exception and throw from here
            throw new ClirNetAppException("Something went wrong");
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
            throw new ClirNetAppException("Something went wrong");
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
    public ArrayList<RegistrationModel> getFilterDatanew(String fname, String lname, String gender, String phoneno, String age, ArrayList sex,ArrayList ageGap,ArrayList ailment) throws ClirNetAppException {

        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        Cursor cursor1 = null;
        ArrayList<RegistrationModel> pList = new ArrayList<>();
        Set<RegistrationModel> pList1 = new LinkedHashSet<>();
        try {
            open();
            String selectQuery = "select p.patient_id,p.first_name,p.middle_name,p.last_name ,p.dob ,p.gender,p.age,p.phonenumber,p.language,p.photo,ph.follow_up_date,ph.days,ph.months,ph.weeks,ph.ailment,ph.prescription,ph.clinical_notes,ph.added_on,ph.modified_on,ph.actual_follow_up_date,ph.action from patient p,patient_history ph   where p.patient_id=ph.patient_id and  p.first_name like '%" + fname + "%' and p.last_name like '%" + lname + "%'";
          //  String selectQuery = "select p.patient_id,p.first_name,p.middle_name,p.last_name ,p.dob ,p.gender,p.age,p.phonenumber,p.language,p.photo,ph.follow_up_date,ph.days,ph.months,ph.weeks,ph.ailment,ph.prescription,ph.clinical_notes,ph.added_on,ph.modified_on,ph.actual_follow_up_date,ph.action from patient p,patient_history ph   where p.patient_id=ph.patient_id and  p.first_name like '%" + fname + "%' and p.last_name like '%" + lname + "%' and ( p.gender like '%" + male + "%' or p.gender like '%" + female + "%' and p.gender like '%" + other + "%' or p.gender like '%" + na + "%' )and p.phonenumber like '%" + phoneno + "%' and p.age like '%" + age + "%'  order by ph.key_visit_id desc limit 30;";
         if(sex.size() > 0) {
             for (int i = 0; i < sex.size(); i++) {
                 if (i != 0) {
                     selectQuery = selectQuery.concat(" OR p.gender = '" + sex.get(i) + "'");

                 } else {
                     selectQuery = selectQuery.concat("  AND ( p.gender = '" + sex.get(i) + "'");
                 }
             }
             selectQuery=selectQuery.concat(" ) ");
         }

            if(ageGap.size() > 0) {
                for (int i = 0; i < ageGap.size(); i++) {
                    String[] parts = ageGap.get(i).toString().split("-"); //split the string 0-5 to 0 and 5 resp
                    String part1 = parts[0];
                    String part2 = parts[1]; // 034556
                    if (i != 0) {
                        selectQuery = selectQuery.concat(" OR  p.age BETWEEN '"+ part1 + "' and '"+ part2 +"' ");

                    } else {
                        selectQuery = selectQuery.concat(" AND ( p.age BETWEEN '"+ part1 + "' and '"+ part2 +"' ");
                    }
                }
                selectQuery=selectQuery.concat(" ) ");
            }

            if(ailment.size() > 0)
            {

                for(int i=0 ; i<ailment.size() ; i++){
                    String value=ailment.get(i).toString();
                    if(i != 0)
                    {
                        selectQuery = selectQuery.concat(" OR  ph.ailment like '%"+ value+"%'");
                    }
                    else
                    {
                        selectQuery = selectQuery.concat(" AND ( ph.ailment like '%"+ value+"%'");

                    }


                }

                selectQuery=selectQuery.concat(" ) ");
            }

             selectQuery=selectQuery.concat("  and p.phonenumber like '%" + phoneno + "%' order by ph.key_visit_id desc limit 30;");
            Log.e("selectQuery",""+selectQuery);
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            int count = cursor.getCount();
            // Log.d("count", "" + count);


            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20));

                    pList1.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        ArrayList<RegistrationModel> list = new ArrayList<>(pList1);
        return list;
    }

   //used to fileter data from Patient history module
    public ArrayList<RegistrationModel> getFilterDatanew(String fname, String lname, String gender, String phoneno, String age) throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        Cursor cursor1 = null;
        ArrayList<RegistrationModel> pList = new ArrayList<>();
        Set<RegistrationModel> pList1 = new LinkedHashSet<>();
        try {
            open();
            String selectQuery = "select p.patient_id,p.first_name,p.middle_name,p.last_name ,p.dob ,p.gender,p.age,p.phonenumber,p.language,p.photo,ph.follow_up_date,ph.days,ph.months,ph.weeks,ph.ailment,ph.prescription,ph.clinical_notes,ph.added_on,ph.modified_on,ph.actual_follow_up_date,ph.action from patient p,patient_history ph   where p.patient_id=ph.patient_id and  p.first_name like '%" + fname + "%' and p.last_name like '%" + lname +  "%' and p.gender like '" + gender + "' and p.phonenumber like '%" + phoneno + "%' and p.age like '%" + age + "%'  order by ph.key_visit_id desc limit 30";
           //String selectQuery = "select p.patient_id,p.first_name,p.middle_name,p.last_name ,p.dob ,p.gender,p.age,p.phonenumber,p.language,p.photo,ph.follow_up_date,ph.days,ph.months,ph.weeks,ph.ailment,ph.prescription,ph.clinical_notes,ph.added_on,ph.modified_on,ph.actual_follow_up_date,ph.action from patient p,patient_history ph   where p.patient_id=ph.patient_id and  p.first_name like '%" + fname + "%' and p.last_name like '%" + lname + "%' and ( p.gender like '%" + male + "%' or p.gender like '%" + female + "%' and p.gender like '%" + other + "%' and p.gender like '%" + na + "%' )and p.phonenumber like '%" + phoneno + "%' and p.age like '%" + age + "%'  order by ph.key_visit_id desc limit 200;";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

            int count = cursor.getCount();
            // Log.d("count", "" + count);


            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20));

                    pList1.add(user);

                } while (cursor.moveToNext());

            }
            pList.addAll(pList1);
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db1 != null) {
                db1.close();
            }
        }
        ArrayList<RegistrationModel> list = new ArrayList<>(pList);
        return list;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//this will search for user entered phone number from Patient Central Page
    public ArrayList<RegistrationModel> getPatientListForPhoneNumberFilter() throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date  from patient p , patient_history ph where ph.patient_id=p.patient_id  group by ph.patient_id having count(*)>0   order by ph.key_visit_id desc  ";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            // Log.d("cursor", "" + cursor.getCount());


            int count = cursor.getCount();
            //  Log.d("count", "" + count);
            // looping through all rows and adding to list
            // looping through all rows and adding to list
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
            throw new ClirNetAppException("Something went wrong");
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


    public ArrayList<RegistrationModel> getPatientListForPhoneNumberFilter(String number) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {                                                                                                                                                                                                                                                                                                                                                                                          //p.first_name like '%" + fname + "%'
            // String selectQuery = "select  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date  from patient p , patient_history ph where ph.patient_id=p.patient_id and p.phonenumber like  '% " + number + " %'  group by ph.patient_id having count(*)>0   order by ph.key_visit_id desc " ;
            String selectQuery = "select  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date,p.patient_address,p.patient_city_town,p.district,p.pin_code,p.patient_state,ph.weight,ph.pulse,ph.bp_high,ph.bp_low,ph.temperature,ph.sugar,ph.symptoms,ph.diagnosis,ph.tests,ph.drugs from patient p , patient_history ph where ph.patient_id=p.patient_id and p.phonenumber like '%" + number + "%' group by ph.patient_id having count(*)>0   order by ph.key_visit_id desc ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            // Log.d("cursor", "" + cursor.getCount());


            int count = cursor.getCount();
            // Log.d("count", "" + count);
            // looping through all rows and adding to list
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23), cursor.getString(24), cursor.getString(25), cursor.getString(26), cursor.getString(27), cursor.getString(28), cursor.getString(29), cursor.getString(30), cursor.getString(31),
                            cursor.getString(32), cursor.getString(33), cursor.getString(34), cursor.getString(35), cursor.getString(36));

                    hotelList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong");
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
    public ArrayList<RegistrationModel> getPatientHistoryListAll(String patient_id) throws ClirNetAppException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {

            String selectQuery = "select  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date  from patient_history  ph , patient p where p.patient_id=" + patient_id + " and  p.patient_id=ph.patient_id order by ph.added_on desc ";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            Log.d("cursor", "" + cursor.getCount());

            // looping through all rows and adding to list

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
            throw new ClirNetAppException("Something went wrong");
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
            throw new ClirNetAppException("Something went wrong");
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
            throw new ClirNetAppException("Something went wrong");
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
            throw new ClirNetAppException("Something went wrong");
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

    public String getUserMailId() throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String returnString = ""; // Your default if none is found
        try {
            db1 = dbHelper.getReadableDatabase();
            //   stmt = db1.compileStatement("select doctor_id from doctor_perInfo order by doctor_id desc limit 1");

            String query = "select name from user order by id desc limit 1";


            cursor = db1.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("doctor_id"));
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong");
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
            //   stmt = db1.compileStatement("select doctor_id from doctor_perInfo order by doctor_id desc limit 1");

            String query = "select doctor_id from doctor_perInfo order by doctor_id desc limit 1";


            cursor = db1.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("doctor_id"));
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong");
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
        // SQLiteStatement stmt = null;
        // SQLiteStatement stmt2 = null;
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        String firstName = null;
        String lastName = null;
        try {
            db1 = dbHelper.getReadableDatabase();
            String query = "select first_name,last_name from doctor_perInfo order by doctor_id desc limit 1";
           /* stmt = db1.compileStatement("select first_name from doctor_perInfo order by doctor_id desc limit 1");
            stmt2 = db1.compileStatement("select last_name from doctor_perInfo order by doctor_id desc limit 1");*/
            cursor = db1.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                firstName = cursor.getString(cursor.getColumnIndex("first_name"));
                lastName = cursor.getString(cursor.getColumnIndex("last_name"));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new ClirNetAppException("something went wrong");
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
        database = dbHelper.getReadableDatabase();

        try {

            // stmt = db1.compileStatement("select phonenumber from doctor_perInfo order by phonenumber desc limit 1");
            String query = "select phonenumber from doctor_perInfo order by phonenumber desc limit 1";


            cursor = database.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                returnString = cursor.getString(cursor.getColumnIndex("phonenumber"));
            }

        } catch (Exception e) {
            throw new ClirNetAppException("something went wrong");
        } finally {

            if (cursor != null) {
                //close statment
                cursor.close();
            }
            if (database != null) {
                database.close();
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
            throw new ClirNetAppException("something went wrong");
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
            throw new ClirNetAppException("no values found");
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

        database = dbHelper.getReadableDatabase();
        int count;
        Cursor c = null;
        try {
            c = database.rawQuery(
                    "SELECT * FROM " + SQLiteHandler.TABLE_USER + " WHERE ("
                            + SQLiteHandler.KEY_NAME + "='" + username + "'OR " + SQLiteHandler.PHONE_NUMBER + "='" + phoneNumber + "' )AND " + SQLiteHandler.KEY_PASSWORD + "='" + password + "'", null);
            count = c.getCount();
        } catch (Exception e) {
            throw new ClirNetAppException("Not able to search records");
        } finally {
            if (c != null) {
                c.close();
            }
            if (database != null) {
                database.close();
            }
            // todo close db here
        }
        if (count > 0)
            return true;
        return false;

    }

    public Cursor getAilmentsListSql() throws ClirNetAppException {
        SQLiteDatabase db1 = null;
        String[] cols = {"id", "ailment"};
        Cursor cursor = null;
        try {

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.query("table_MasterAilment", cols, null,
                    null, null, null, null);
        } catch (Exception e) {
            throw new ClirNetAppException("Error while getting records");
        }

        //	c.close();
        return cursor;
    }

    public ArrayList<Counts> countPerDay() throws ClirNetAppException {

        ArrayList<Counts> VisitidList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT added_on as date,COUNT(id) as count FROM patient WHERE added_on<  DATE('now','-7 days') GROUP BY added_on ORDER BY id DESC limit 7";
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
            throw new ClirNetAppException("Something went wrong");
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
            throw new ClirNetAppException("Something went wrong");
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

    public ArrayList<String> getCountTopTenAilment(String fromdate, String todate) throws ClirNetAppException {

        ArrayList<String> ailmnetList = new ArrayList<>();
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
                    ailmnetList.add(value);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new ClirNetAppException("Something went wrong");
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

    public ArrayList<GenderWiseDataModel> genderWiseData(String fromdate, String todate) throws ClirNetAppException {

        ArrayList<GenderWiseDataModel> genderList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT COUNT(CASE WHEN UPPER(dpr.gender) = 'MALE' THEN 1 END) Male,\n" +
                    "COUNT(CASE WHEN UPPER(dpr.gender) = 'FEMALE' THEN 1 END) Female,\n" +
                    "  CASE\n" +
                    "\tWHEN CAST(dpr.age AS Integer) BETWEEN 0 AND 5 THEN '00-05'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) BETWEEN 5 AND 15 THEN '05-15'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) BETWEEN 15 AND 25 THEN '15-25'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) BETWEEN 25 AND 35 THEN '25-35'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) BETWEEN 35 AND 45 THEN '35-45'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) BETWEEN 45 AND 55 THEN '45-55'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) BETWEEN 55 AND 65 THEN '55-65'\n" +
                    "\tWHEN CAST(dpr.age AS Integer) BETWEEN 65 AND 1000 THEN '65-Above'\n" +
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
            throw new ClirNetAppException("Something went wrong");
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

}





