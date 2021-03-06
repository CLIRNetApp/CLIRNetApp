package app.clirnet.com.clirnetapp.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import app.clirnet.com.clirnetapp.app.AppController;


//this class is used import ailments.db file from asset folder into database for further use
public class DatabaseClass extends SQLiteOpenHelper {
    @SuppressLint("SdCardPath")
    private static final String DB_PATH = "/data/data/app.clirnet.com.clirnetapp/databases/";

    private static final String DB_NAME = "clirnetApp.db";



    private SQLiteDatabase myDataBase;

    private final Context myContext;
    private SQLiteHandler dbHelper;
    private SQLiteDatabase database;

    private AppController appController;
    public DatabaseClass(Context context) {

        super(context, DB_NAME, null,SQLiteHandler.DATABASE_VERSION);
        this.myContext = context;


        if (dbHelper == null) {
            dbHelper = new SQLiteHandler(myContext);
            database = dbHelper.getWritableDatabase();
            appController = new AppController();
        }

    }

    public void createDataBase() {

        boolean tblExist = isTableExists(database, "Allergy");

        if (tblExist) {
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            dbHelper.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                appController.appendLog(appController.getDateTime()+"" +"/"+"Database "+e);

                throw new Error("Error copying database");
            }
        }

    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME ;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024] ;
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length) ;
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        File file = new File(myPath);
        if (file.exists() && !file.isDirectory())
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();
        //SQLiteDatabase.releaseMemory();
        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    //method to fetch ailments from db
    public ArrayList<String> getAilmentsListNew() throws ClirNetAppException {
        ArrayList<String> loginList = new ArrayList<>();
        SQLiteDatabase database1 = null;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT  ailment_name  FROM ailments ";

            database1 = dbHelper.getReadableDatabase();
            cursor = database1.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    String user=cursor.getString(0);
                  //  AilmentModel user = new AilmentModel(cursor.getString(0), cursor.getString(1) );

                    loginList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            appController.appendLog(appController.getDateTime()+"" +"/"+"Database"+e);
            throw new ClirNetAppException("Something went wrong while getting login records");
        } finally {

            if (cursor != null) {
                cursor.close();
            }
            if (database1 != null) {
                database1.close();
            }
        }
        return  loginList;
    }

    public void addSymptoms(String symptoms) {
        SQLiteDatabase db = null;


        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("symptoms_name", symptoms);

            db.insert("Symptoms", null, values);
        } catch (Exception e) {
            appController.appendLog(appController.getDateTime()+"" +"/"+"Database"+e);
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void addDiagnosis(String diagnosis) {
        SQLiteDatabase db = null;


        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("diagnosis_name", diagnosis);

            db.insert("Diagnosis", null, values);
        } catch (Exception e) {
            appController.appendLog(appController.getDateTime()+"" +"/"+"Database"+e);
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
    public void addAllergy(String diagnosis) {
        SQLiteDatabase db = null;


        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("allergy_name", diagnosis);

            db.insert("Allergy", null, values);
        } catch (Exception e) {
            appController.appendLog(appController.getDateTime()+"" +"/"+" Database  "+e);
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        if (db == null || !db.isOpen()) {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", tableName});
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }


}

