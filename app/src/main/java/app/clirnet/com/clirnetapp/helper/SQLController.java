package app.clirnet.com.clirnetapp.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import app.clirnet.com.clirnetapp.models.RegistrationModel;

//this class is most imp class which used to query the database
public class SQLController {


    private final Context ourcontext;
    private SQLiteHandler dbHelper;
    private SQLiteDatabase database;
    private SQLiteDatabase db1;


    public SQLController(Context c) {
        ourcontext = c;
    }

    public SQLController open() throws SQLException {
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
    public Cursor getUserLoginRecords() throws CustomeException {

        String[] cols = {SQLiteHandler.KEY_NAME, SQLiteHandler.KEY_PASSWORD};
        Cursor cursor = null;
        try {
            open();
            cursor = database.query(SQLiteHandler.TABLE_USER, cols, null,
                    null, null, null, null);
        } catch (SQLException e) {
            throw new CustomeException("Something went wrong");
        } finally {
            if (db1 != null ) {
               // closeConnection(cursor, db1);
              //  db1.close();

            }
            if(cursor != null){
               // cursor.close();
            }

        }

        return cursor;

    }

    //get all the patient imp data from db, which will used in Consultation fragments and home fragments
    public ArrayList<RegistrationModel> getPatientList() throws CustomeException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date FROM patient p INNER JOIN patient_history ph ON p.patient_id = ph.patient_id order by ph.key_visit_id desc";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);

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
            throw new CustomeException("Something went wrong");
        } finally {
            //create method & pass cursor & db1 ref.
            closeConnection(cursor, db1);
        }

        return hotelList;

    }

    //get max count of patient id
    public int getPatientIdCount() throws CustomeException {
        SQLiteDatabase db1 = null;
        SQLiteStatement stmt = null;
        try {
            db1 = dbHelper.getReadableDatabase();
            stmt = db1.compileStatement("select max(patient_id) from patient");
        } catch (Exception e) {
            throw new CustomeException("Something went wrong");
        } finally {
            if (db1 != null && stmt != null) {
                //  closeQuietly(stmt, db1);
            }
        }
        return (int) stmt.simpleQueryForLong();

    }


    //get max visit count  from patient_history table
    public int getPatientVisitIdCount() throws CustomeException {
        SQLiteDatabase db1 = null;
        SQLiteStatement stmt = null;
        try {
            db1 = dbHelper.getReadableDatabase();
            stmt = db1.compileStatement("select max(key_visit_id) from patient_history");
        } catch (Exception e) {
            throw new CustomeException("Something went wrong");
        } finally {
            if (db1 != null && stmt != null) {
                //closeQuietly(stmt, db1);
            }
        }
        return (int) stmt.simpleQueryForLong();

    }


    //used to fileter data from Patient history module
    public ArrayList<RegistrationModel> getFilterDatanew(String fname, String lname, String gender, String phoneno, String age) throws CustomeException {
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        ArrayList<RegistrationModel> pList = new ArrayList<>();
        try {
            open();
            String selectQuery = "select p.patient_id,p.first_name,p.middle_name,p.last_name ,p.dob ,p.gender,p.age,p.phonenumber,p.language,p.photo,ph.follow_up_date,ph.days,ph.months,ph.weeks,ph.ailment,ph.prescription,ph.clinical_notes,ph.added_on,ph.modified_on,ph.actual_follow_up_date,ph.action from patient p,patient_history ph   where p.patient_id=ph.patient_id and  p.first_name like '%" + fname + "%' and p.last_name like '%" + lname + "%' and p.gender like '" + gender + "' and p.phonenumber like '%" + phoneno + "%' and p.age like '%" + age + "%'  and ph.actual_follow_up_date = (select actual_follow_up_date from patient_history ph2 where ph2.patient_id = p.patient_id order by ph2.actual_follow_up_date desc) order by ph.key_visit_id desc";
            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            Log.d("cursor", "" + cursor.getCount());

            int count = cursor.getCount();
            Log.d("count", "" + count);

            if (cursor.moveToFirst()) {
                do {
                    RegistrationModel user = new RegistrationModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17), cursor.getString(18), cursor.getString(19), cursor.getString(20));

                    pList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new CustomeException("Something went wrong");
        } finally {
            closeConnection(cursor, db1);
        }

        return pList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//this will search for user entered phone number from Patient Central Page
    public ArrayList<RegistrationModel> getPatientListForPhoneNumberFilter() throws CustomeException {

        ArrayList<RegistrationModel> hotelList = new ArrayList<>();
        SQLiteDatabase db1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "select  p.patient_id,p.first_name, p.middle_name, p.last_name,p.dob,p.age,p.phonenumber,p.gender,p.language,p.photo,ph.follow_up_date, ph.days,ph.weeks,ph.months, ph.ailment,ph.prescription,ph.clinical_notes,p.added_on,ph.visit_date,p.modified_on,ph.key_visit_id,ph.actual_follow_up_date  from patient p , patient_history ph where ph.patient_id=p.patient_id  group by ph.patient_id having count(*)>0   order by ph.key_visit_id desc  ";

            db1 = dbHelper.getReadableDatabase();
            cursor = db1.rawQuery(selectQuery, null);
            Log.d("cursor", "" + cursor.getCount());


            int count = cursor.getCount();
            Log.d("count", "" + count);
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
            throw new CustomeException("Something went wrong");
        } finally {
            closeConnection(cursor, db1);
        }
        return hotelList;

    }

    //This will show all the visit  history of patient to show on AddPatientUpdate and ShowPaersonalDetals page
    //ashish
    public ArrayList<RegistrationModel> getPatientHistoryListAll(String patient_id) throws CustomeException {

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
            throw new CustomeException("Something went wrong");
        } finally {
            closeConnection(cursor, db1);
        }
        return hotelList;

    }

    ///////////////////////////////////////////////////////////////
    //get all patients which is yet to send to server
    //get all patients which is yet to send to server
    public ArrayList<RegistrationModel> getPatientIdsFalg0() throws CustomeException {

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
            throw new CustomeException("Something went wrong");
        } finally {
            closeConnection(cursor, db1);
        }
        return idList;

    }

    //get all patients  visits data which is yet to send to server
    public ArrayList<RegistrationModel> getPatientVisitIdsFalg0() throws CustomeException {

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
            throw new CustomeException("Something went wrong");
        } finally {
            closeConnection(cursor, db1);
        }

        return VisitidList;

    }

    //get docot membership id from db
    public String getDoctorMembershipIdNew() throws CustomeException {
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
            throw new CustomeException("Something went wrong");
        } finally {
            closeConnection(cursor, db1);
        }
        return returnString;
    }


    //get doctor id from db
    public String getDoctorId() throws CustomeException {
        SQLiteStatement stmt = null;
        SQLiteDatabase db1 = null;
        try {
            db1 = dbHelper.getReadableDatabase();
            stmt = db1.compileStatement("select doctor_id from doctor_perInfo order by doctor_id desc limit 1");
        } catch (Exception e) {
            throw new CustomeException("Something went wrong");
        } finally {
            if (db1 != null) {
                /*db1.close();
                stmt.close();*/
            }
        }
        return stmt.simpleQueryForString();


    }

    //doctor first and last name to set to navigation bar
    public String getDocdoctorName() throws CustomeException {
        SQLiteStatement stmt = null;
        SQLiteStatement stmt2 = null;
        SQLiteDatabase db1 = null;
        try {
            db1 = dbHelper.getReadableDatabase();
            stmt = db1.compileStatement("select first_name from doctor_perInfo order by doctor_id desc limit 1");
            stmt2 = db1.compileStatement("select last_name from doctor_perInfo order by doctor_id desc limit 1");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new CustomeException("something went wrong");
        } finally {
            if (db1 != null) {
               // db1.close();
            }
        }
        return stmt.simpleQueryForString() + " " + stmt2.simpleQueryForString();


    }

    public String getPhoneNumber() throws CustomeException {

        db1 = dbHelper.getReadableDatabase();
        SQLiteStatement stmt = null;
        try {

            stmt = db1.compileStatement("select phonenumber from doctor_perInfo order by phonenumber desc limit 1");

        } catch (Exception e) {
            throw new CustomeException("something went wrong");
        }
        finally {
            if(db1 !=null){
                //close database
            }
            if(stmt !=null){
                //close statment
            }
        }

        return stmt.simpleQueryForString();
    }

    //get doctor mail id
    public String getDocdoctorEmail() throws CustomeException {

        SQLiteStatement stmt = null;
        SQLiteDatabase db1 = null;
        try {
            db1 = dbHelper.getReadableDatabase();
            stmt = db1.compileStatement("select email from doctor_perInfo order by email desc limit 1");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new CustomeException("something went wrong");
        } finally {
            if (db1 != null) {
                //db1.close();
            }
        }

        return stmt.simpleQueryForString();
    }

    public String getAsyncvalue() throws CustomeException {

        SQLiteStatement stmt = null;
        SQLiteDatabase db1=null;
        try {
            db1 = dbHelper.getReadableDatabase();
            stmt = db1.compileStatement("select value from async order by id desc limit 1");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new CustomeException("no value found");
        } finally {
            if (db1 != null) {
              //  db1.close();
            }
        }
        return stmt.simpleQueryForString();
    }

    //This will close the Cursor and Database 1-11-2016
    private void closeConnection(Cursor cursor, SQLiteDatabase db1) {
        if (cursor != null) {
            cursor.close();
        }
        if (this.db1 != null) {
            this.db1.close();
        }
    }
    public boolean validateUser(String username, String password) throws CustomeException {

        db1 = dbHelper.getReadableDatabase();
        int count;
        Cursor c=null;
        try {
             c = db1.rawQuery(
                    "SELECT * FROM " + SQLiteHandler.TABLE_USER + " WHERE "
                            + SQLiteHandler.KEY_NAME + "='" + username + "'AND " + SQLiteHandler.KEY_PASSWORD + "='" + password + "'", null);
            count = c.getCount();
        }catch(Exception e){
            throw new CustomeException("Not able to search records");
        }
        finally {
            if(c != null){
                c.close();
            }
        }
        if (count>0)
            return true;
        return false;
    }


}





