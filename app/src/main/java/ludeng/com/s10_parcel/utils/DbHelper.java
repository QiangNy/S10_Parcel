package ludeng.com.s10_parcel.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chen on 16-10-22.
 */
public class DbHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "first.db";
    private final static int   DATABASE_VERSION = 1;
    private static final String TAG = DbHelper.class.getName();

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* db.execSQL("CREATE TABLE IF NOT EXISTS person" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age INTEGER, info TEXT)");  */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS login_table" +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(20), password varchar(20))");
        Log.i(TAG, "db.execSQL   onCreate");
    }
    /* db.execSQL("ALTER TABLE person ADD COLUMN other STRING"); */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "ALTER TABLE login_table ADD COLUMN other STRING";
        db.execSQL(sql);
        Log.i(TAG, "db.execSQL   onUpgrade");
    }
}
