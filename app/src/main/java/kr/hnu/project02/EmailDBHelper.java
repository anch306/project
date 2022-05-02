package kr.hnu.project02;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmailDBHelper extends SQLiteOpenHelper {
    public EmailDBHelper(Context context) {
        super(context, "Email.db", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE email ( SendID TEXT, GetID TEXT, Time TEXT," +
                " MailHeader TEXT, MailContent TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS email");
        onCreate(db);
    }
}
