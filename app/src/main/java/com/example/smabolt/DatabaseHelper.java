package com.example.smabolt;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "appdt1.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;
    /*
    public static final String TABLE_NAME ="excel";
    public static final String COL0 = "id";
    public static final String COL1 = "name_obj";
    public static final String COL2 = "auditory";
    public static final String COL3 = "teacher";
    public static final String COL4 = "type_obj";
    public static final String COL5 = "week_coming";
    public static final String COL6 = "subgroup";
    public static final String COL7 = "day";
    public static final String COL8 = "num_less";
    public static final String COL9 = "group_name";
    private static final String CREATE_TABLE="create table if not exists "+ TABLE_NAME + "(" + COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COL1+ " TEXT, " +COL2+" TEXT," + COL3 + " TEXT, " +COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " INTEGER, " + COL7 + " INTEGER, " +
            COL8 + " INTEGER, "+ COL9 + " INTEGER);";
    */

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();//////////////

        this.getReadableDatabase();
        Log.d("MY_TAG", ". (DatabaseHelper.java:40) DatabaseHelper worked");
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        Log.d("MY_TAG", ". (DatabaseHelper.java:66) checkDatabase worked");
        return dbFile.exists();

    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
        Log.d("MY_TAG", ". (DatabaseHelper.java:83) copyDatabase worked");
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
        Log.d("MY_TAG", ". (DatabaseHelper.java:86) copyDatabase worked");
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Log.d("MY_TAG", ". (DatabaseHelper.java:99) openDataBase worked");
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE 'group' (key_id integer primary key, gr text, course integer)");
        //db.execSQL("CREATE TABLE 'student' (_id integer primary key, name text, age integer)");

        //name_obj - название предмета; auditory - аудитория; teacher - преподаватель;
        //type_obj - тип предмета,; week_coming - недели, когда нужно приходить; subgroup - подгруппа;
        //day - день недели; num_less - номер пары, включая пустые; group_name - группа
        db.execSQL("CREATE TABLE web (" +
                "subjectName TEXT, " +
                "teacher TEXT, " +
                "activityType TEXT, " +
                "date TEXT, " +
                "time TEXT, " +
                "location TEXT, " +
                "`groups` TEXT, " +  // ← запятая здесь НУЖНА, потому что дальше идёт PRIMARY KEY
                "PRIMARY KEY (subjectName, date, time)" +
                ");");
        /*
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
        }*/
        Log.d("MY_TAG", ". (DatabaseHelper.java:112) Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;

        Log.d("MY_TAG", ". (DatabaseHelper.java:138) copyDatabase updated");
    }
}