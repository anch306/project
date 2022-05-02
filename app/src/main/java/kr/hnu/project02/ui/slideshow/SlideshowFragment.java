package kr.hnu.project02.ui.slideshow;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import kr.hnu.project02.DBHelper;
import kr.hnu.project02.EmailDBHelper;
import kr.hnu.project02.R;
import kr.hnu.project02.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    String[] items;
    Button cancleBtn, sendBtn;
    DBHelper dbHelper;
    EmailDBHelper emailDBHelper;
    SQLiteDatabase readableDB, writeableDB;
    Cursor cursor;

    String time;
    EditText getID, sendHeader, sendContent;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_slideshow,
                container, false);

        getID = (EditText)rootView.findViewById(R.id.getID);
        sendHeader = (EditText)rootView.findViewById(R.id.sendHeader);
        sendContent = (EditText)rootView.findViewById(R.id.sendContent);

        dbHelper = new DBHelper(rootView.getContext());
        emailDBHelper = new EmailDBHelper(rootView.getContext());
        readableDB = dbHelper.getReadableDatabase();
        writeableDB = emailDBHelper.getWritableDatabase();

        cursor = readableDB.rawQuery
                ("SELECT _id, ID, Password, Name FROM account", null);
        items = new String[cursor.getCount()];
        for(int i=0; i < cursor.getCount(); i++)
        {
            cursor.moveToNext();
            items[i] = cursor.getString(1);
        }

        Intent intent = getActivity().getIntent();           //getActivity()를 써야 사용이 가능
        String data = intent.getStringExtra("UID");      //메인 액티비티에서 _id의 값을 받아옴
        int parseint = Integer.parseInt(data) - 1;            //_id값 -1
        cursor.moveToPosition(parseint);
        String ID = cursor.getString(1);      //moveToPosition으로 db에서 접속한 유저의 _id 위치를 찾은 후
                                                        // ID를 String으로 저장

//        SlideshowViewModel slideshowViewModel =
//                new ViewModelProvider(this).get(SlideshowViewModel.class);

//        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textSlideshow;
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        cancleBtn = (Button) rootView.findViewById(R.id.cancleBtn);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override       //취소버튼을 안드로이드 뒤로가기 버튼으로 설정
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        sendBtn = (Button) rootView.findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override       //보내기 버튼을 누르면 데이터베이스에 저장
            public void onClick(View view) {
                ContentValues row;
                setCalendar();
                row = new ContentValues();
                row.put("SendID", ID);          //보내는사람
                row.put("GetID", getID.getText().toString());                     //받는사람
                row.put("Time", time);
                row.put("MailHeader", sendHeader.getText().toString());
                row.put("MailContent", sendContent.getText().toString());
                writeableDB.insert("email", null, row);
            }
        });

        Spinner spinner = rootView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getID.setText(items[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }

    public void setSpinner()
    {

    }

    public void setCalendar() {
        Calendar cal = Calendar.getInstance();
        int y, m, d, h, mi, s;
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        h = cal.get(Calendar.HOUR);
        mi = cal.get(Calendar.MINUTE);
        s = cal.get(Calendar.SECOND);

        time = y + "-" + m + "-" + d + " " + h + ":" + mi + ":" + s;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}