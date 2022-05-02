package kr.hnu.project02.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

import kr.hnu.project02.DBHelper;
import kr.hnu.project02.EmailDBHelper;
import kr.hnu.project02.Mail;
import kr.hnu.project02.R;
import kr.hnu.project02.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    String time;
    TextView myName, mailContent;
    TextView IDTextView, contentTextView, timeTextView;
    DBHelper dbHelper;
    EmailDBHelper emailDBHelper;
    SQLiteDatabase readableDB, readableEmailDB;
    Button emailBtn;
    Cursor cursor, emailCursor;
    boolean test = false;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home,
                container, false);
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        dbHelper = new DBHelper(rootView.getContext());
        emailDBHelper = new EmailDBHelper(rootView.getContext());
        readableDB = dbHelper.getReadableDatabase();
        readableEmailDB = emailDBHelper.getReadableDatabase();

        emailCursor = readableEmailDB.rawQuery
                ("SELECT SendID, GetID, Time, MailHeader, MailContent FROM email", null);

        while (emailCursor.moveToNext()) {
            addMessage(inflater, rootView);
        }
        return rootView;
    }

    public void addMessage(@NonNull LayoutInflater inflater,
                           View rootView) {
        RelativeLayout rel = (RelativeLayout) View.inflate(rootView.getContext(),
                R.layout.newmessage, null);
        LinearLayout linear = (LinearLayout) rootView.findViewById(R.id.linear);
        setCalendar();
        cursor = readableDB.rawQuery
                ("SELECT _id, ID, Password, Name FROM account", null);

        Intent i = getActivity().getIntent();           //getActivity()를 써야 사용이 가능
        String data = i.getStringExtra("UID");      //메인 액티비티에서 _id의 값을 받아옴
        int parseint = Integer.parseInt(data) - 1;            //_id값 -1
        cursor.moveToPosition(parseint);
        String searchID = cursor.getString(1);      //moveToPosition으로 db에서 접속한 유저의 _id 위치를 찾은 후
        // ID를 String으로 저장

        IDTextView = (TextView) rel.findViewById(R.id.desc);         //보낸 유저 ID
        contentTextView = (TextView) rel.findViewById(R.id.content); //내용
        timeTextView = (TextView) rel.findViewById(R.id.time);       //시간

//        emailCursor = readableEmailDB.rawQuery
//                ("SELECT SendID, GetID, Time, MailHeader, MailContent FROM email", null);

        if (emailCursor.getString(1).equals(searchID)) //이메일커서로 받는사람 ID == 접속한 id 같으면 출력
        {
            IDTextView.setText(emailCursor.getString(0));
            timeTextView.setText(emailCursor.getString(2));
            contentTextView.setText(emailCursor.getString(3));
            linear.addView(rel, 1);
        }

        emailBtn = (Button) rootView.findViewById(R.id.mailBtn);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Mail) getActivity()).changeToReadMessageFragment();
            }
        });

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