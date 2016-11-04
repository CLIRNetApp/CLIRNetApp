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

//this class is used import ailments.db file from asset folder into database for further use
public class DatabaseClass extends SQLiteOpenHelper {
    @SuppressLint("SdCardPath")
    private static final String DB_PATH = "/data/data/app.clirnet.com.clirnetapp/databases/";

    private static final String DB_NAME = "Ailments";
    public static final String DATABASE_NAME = "clirnetApp.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;
    private SQLiteHandler dbHelper;
    private SQLiteDatabase database;

    public DatabaseClass(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        if (dbHelper == null) {
            dbHelper = new SQLiteHandler(myContext);
            database = dbHelper.getWritableDatabase();
        } else {
            Log.e("DB Opended1", "Database is allready opened");
        }
    }


    public void createDataBase() {

        boolean dbExist = checkDataBase();

        boolean tblExist = isTableExists(database, "temp_ailment_table");

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
        }*/

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
        String outFileName = DB_PATH + DB_NAME;

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
        String myPath = DB_PATH + DB_NAME;
        File file = new File(myPath);
        if (file.exists() && !file.isDirectory())
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();
        SQLiteDatabase.releaseMemory();
        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public int getMaxAimId() throws CustomeException {
        SQLiteDatabase db1 =null;
        SQLiteStatement stmt =null;
        try {
            db1 = this.getReadableDatabase();
            stmt = db1.compileStatement("select max(id) from temp_ailment_table");
        }catch (Exception e){
            throw new CustomeException("Error while getting max id");
        }
        return Integer.parseInt(stmt.simpleQueryForString());

    }

    public Cursor getAilmentsList() throws CustomeException {
        SQLiteDatabase db1 = null;
        String[] cols = {"id", "ailment_name"};
        try {

            db1 = this.getReadableDatabase();
        } catch (Exception e) {
            throw new CustomeException("Error while getting records");
        }

        //	c.close();
        return db1.query("temp_ailment_table", cols, null,
                null, null, null, null);
    }

    public void addAilments(String ailments, int ailid) {
        SQLiteDatabase db = null;
        long id = 0;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("ailment_name", ailments);
            values.put("id", ailid);
            id = db.insert("temp_ailment_table", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }


        Log.d("addedailemnt", "New ailment inserted into sqlite: " + id);


    }

    boolean isTableExists(SQLiteDatabase db, String tableName) {
        if (tableName == null || db == null || !db.isOpen()) {
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

