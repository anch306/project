package kr.hnu.project02;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Account.db", null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE account ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ID TEXT, Password TEXT, Name TEXT);");
    } // corsor.getString(0) = _id,
    // (1) = ID,
    // (2) = Password,
    // (3) = Name

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS account");
        onCreate(db);
    }
}
