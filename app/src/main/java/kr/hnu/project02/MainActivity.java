package kr.hnu.project02;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    EditText IDEditText, passwordEditText;
    //    String getName, getPassword;
    String ID, password;
    SQLiteDatabase writeableDB, readableDB;
    boolean searchAccount;
    CheckBox autoLogin;
    boolean loginChecked;
    private SharedPreferences appData;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IDEditText = (EditText) findViewById(R.id.ID);
        passwordEditText = (EditText) findViewById(R.id.password);
        autoLogin = (CheckBox) findViewById(R.id.autoLogin) ;

        appData = getSharedPreferences("appdata", MODE_PRIVATE);
        load();

        if(loginChecked)
        {
            IDEditText.setText(ID);
            passwordEditText.setText(password);
            autoLogin.setChecked(loginChecked);
        }

        dbHelper = new DBHelper(this);
        writeableDB = dbHelper.getWritableDatabase();
        readableDB = dbHelper.getReadableDatabase();
        searchAccount = false;

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override           //체크박스 이벤트. 체크박스 해제시 이벤트 저장
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.autoLogin)
                {
                    if(!b)
                    {
                        save();
                    }
                }
            }
        });
    }

    public void mOnClick(View view) {
        switch (view.getId()) {
            case R.id.signUp:      //회원가입 버튼
                Intent intent = new Intent(this, CreateAccount.class);
                IDEditText.setText(null);       //회원가입창 들어갈 경우 로그인 화면의 아이디 및 패스워드 내용 제거
                passwordEditText.setText(null);
                startActivity(intent);
                break;

            case R.id.login:       //로그인 버튼
                cursor = readableDB.rawQuery
                        ("SELECT _id, ID, Password, Name FROM account", null);

                while (cursor.moveToNext()) {
                    String searchUID = cursor.getString(0);
                    String searchID = cursor.getString(1);
                    String searchPassword = cursor.getString(2);

                    if (searchID.equals(IDEditText.getText().toString()) &&
                            searchPassword.equals(passwordEditText.getText().toString())) {
                        Intent intent2 = new Intent(this, Mail.class);

                        intent2.putExtra("UID", searchUID);

                        startActivity(intent2);
                        searchAccount = true;
                        save();
                        break;
                    }
                }

                if (searchAccount == false) {
                    Toast.makeText(MainActivity.this, " Check your account again!",
                            Toast.LENGTH_SHORT).show();
                }
                searchAccount = false;
                break;

//            case R.id.changeBtn:
//                cursor = readableDB.rawQuery
//                        ("SELECT _id, ID, Password, Name FROM account", null);
//
//                Intent i = getIntent();           //getActivity()를 써야 사용이 가능
//                String data = i.getStringExtra("UID");      //메인 액티비티에서 _id의 값을 받아옴
//                int parseint = Integer.parseInt(data) - 1;            //_id값 -1
//                String toString = Integer.toString(parseint+1);
//                cursor.moveToPosition(parseint);
//
//
//                EditText changePassword = (EditText) findViewById(R.id.changePassword);
//                EditText changeName = (EditText) findViewById(R.id.changeName);
//
//                ContentValues row;
//                row = new ContentValues();
//                row.put("Password", "testtest");          //새로운 비밀번호 지정
//                writeableDB.update("account", row, "_id = " + toString, null);
//                row.put("Name", "testtest");
//                writeableDB.update("account", row, "_id = " + toString, null);

//                break;
        }
    }
    public void resetInputData()
    {
        IDEditText.setText("");
        passwordEditText.setText("");
    }


    public void save()
    {
        SharedPreferences.Editor editor = appData.edit();

        editor.putBoolean("SAVE_LOGIN_DATA", autoLogin.isChecked());
        editor.putString("ID", IDEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());

        editor.apply();
    }

    public void load()
    {
        loginChecked = appData.getBoolean("SAVE_LOGIN_DATA", false);
        ID = appData.getString("ID", "");
        password = appData.getString("password", "");
    }

//    void init() { //Person에 입력받은 값들을 받아옴
//        activityResultLauncher = registerForActivityResult(new
//                ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK) {
//                Person person = (Person) result.getData().getSerializableExtra("Out");
//                getName = person.getName();
//                //    myName.setText(person.getName());
//
//                getPassword = person.getPassword();
//                //    myPassword.setText(person.getPassword());
//            }
//        });
//    }

    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
