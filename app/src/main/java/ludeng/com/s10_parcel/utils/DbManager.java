package ludeng.com.s10_parcel.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 16-10-22.
 */
public class DbManager {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        dbHelper = new DbHelper(context);
    }

    /*db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{person.name, person.age, person.info});  */
    public void add(List<LoginInfo> loginInfoList) {
        db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();//开始事物

            for (LoginInfo item : loginInfoList) {
                db.execSQL("INSERT INTO login_table VALUES(null, ?, ?)", new Object[]{item.name, item.passWd});
            }
            db.setTransactionSuccessful();//设置事物成功完成
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    public void add (LoginInfo loginInfo) {
        db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO login_table VALUES(null, ?, ?)", new Object[]{loginInfo.name,loginInfo.passWd});
        db.close();
    }

    public void updatePasswd(LoginInfo loginInfo) {
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", loginInfo.passWd);
        db.update("login_table", contentValues, "name=?", new String[]{loginInfo.name});
        db.close();
    }

    public void deletOldLogin(LoginInfo loginInfo) {
        db = dbHelper.getWritableDatabase();
        db.delete("login_table", "name=?", new String[]{loginInfo.name});
        db.close();
    }

    public List<LoginInfo> query() {
        db = dbHelper.getReadableDatabase();
        List<LoginInfo> mList = new ArrayList<LoginInfo>();
        Cursor cursor = db.rawQuery("SELECT * FROM login_table", null);
        while (cursor.moveToNext()) {
            LoginInfo item = new LoginInfo(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("password")));
            mList.add(item);
        }
        cursor.close();
        db.close();
        return mList;
    }
}
