package kr.hnu.project02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {
    EditText createID, createPassword, createName;
    DBHelper dbHelper;
    SQLiteDatabase readableDB, writeableDB;
    boolean checkResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createID = (EditText) findViewById(R.id.createID);
        createPassword = (EditText) findViewById(R.id.createPassword);
        createName = (EditText) findViewById(R.id.createName);
        createID.setText("");
        checkResult = true;

        dbHelper = new DBHelper(this);
        readableDB = dbHelper.getReadableDatabase();
        writeableDB = dbHelper.getWritableDatabase();
    }

    public void mOnClick(View view) {
        ContentValues row;
        switch (view.getId()) {
            case R.id.createAccount:
                row = new ContentValues();
                row.put("ID", createID.getText().toString());
                row.put("Password", createPassword.getText().toString());
                row.put("Name", createName.getText().toString());
                writeableDB.insert("account", null, row);
                finish();
                break;
            case R.id.createCheck:
                Cursor cursor = readableDB.rawQuery
                        ("SELECT _id, ID, Password, Name FROM account", null);

                while (cursor.moveToNext()) {
                    String searchID = cursor.getString(1);

                    if (searchID.equals(createID.getText().toString())) {
                        Toast.makeText(CreateAccount.this, "이미 존재하는 ID입니다.", Toast.LENGTH_SHORT).show();
                        checkResult = false;
                    } else if (createID.getText().toString().equals("")) {
                        Toast.makeText(CreateAccount.this, "아이디를 확인해 주세요!", Toast.LENGTH_SHORT).show();
                        checkResult = false;
                    }
                }
                if (checkResult == true) {
                    Toast.makeText(CreateAccount.this, "사용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
                checkResult = true;
                break;
        }
    }

    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}