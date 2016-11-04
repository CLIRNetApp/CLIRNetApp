package app.clirnet.com.clirnetapp.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//this class is used import lastname.db file from asset folder into database for further use
@SuppressWarnings("ALL")
public class LastnameDatabaseClass extends SQLiteOpenHelper {
    @SuppressLint("SdCardPath")
    private static final String DB_PATH = "/data/data/app.clirnet.com.clirnetapp/databases/";

    private static final String DB_NAME = "namelast";
    public static final String DATABASE_NAME = "clirnetApp.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;
    private SQLiteHandler dbHelper;
    private SQLiteDatabase database;
    public LastnameDatabaseClass(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        if (dbHelper == null) {
            dbHelper = new SQLiteHandler(myContext);
            database = dbHelper.getWritableDatabase();
        } else {
            Log.e("DB Opended2", "Database is allready opened");
        }
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        boolean tblExist=isTableExists(database,"last_name_master");

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

                throw new Error("Error copying database");

            }
        }

       /* if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }
*/
    }


    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory())
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null;
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
        String outFileName = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        File file = new File(myPath);
        if (file.exists() && !file.isDirectory())
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //Delete Database...........................
    void deletData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String deleteSQL = "DELETE FROM  table_name";
        db.execSQL(deleteSQL);

    }

    public int getMaxLastNameId() throws CustomeException {

        SQLiteDatabase db1 = null;
        SQLiteStatement stmt = null;
        try{
            db1 = dbHelper.getReadableDatabase();
            stmt = db1.compileStatement("select max(id) from last_name_master");
        }
        catch(Exception e)
        {
            throw new CustomeException("error while getting records");
        }
        finally {
            if(db1 != null){
              //  db1.close();
            }
        }

        return Integer.parseInt(stmt.simpleQueryForString());


    }

    public Cursor getLastNameList() throws CustomeException {
        SQLiteDatabase db1 = null;
        Cursor c = null;
        try {
            String[] cols = {"id", "last_name"};
            db1 = dbHelper.getReadableDatabase();

            c = db1.query("last_name_master", cols, null,
                    null, null, null, null);
        } catch (Exception e) {
            throw new CustomeException("Error getting last name");

        }

        return c;

    }

    public void addLastName(String lastName, int nameid) {
        SQLiteDatabase db = null;
        long id = 0;
        try {
            db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("last_name", lastName);
            values.put("id", nameid);
            id = db.insert("last_name_master", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        Log.d("addedailemnt", "New last_names inserted into sqlite: " + id);

    }

    boolean isTableExists(SQLiteDatabase db, String tableName)
    {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }
}

