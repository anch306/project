package kr.hnu.project02.ui.gallery;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import kr.hnu.project02.DBHelper;
import kr.hnu.project02.MainActivity;
import kr.hnu.project02.R;
import kr.hnu.project02.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    DBHelper dbHelper;
    SQLiteDatabase readableDB, writeableDB;
    TextView IDTextView;
    EditText password, name;
    EditText resetInputID, resetInputPassword;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_gallery,
                container, false);
        Button button = rootView.findViewById(R.id.changeBtn);
//        GalleryViewModel galleryViewModel =
//                new ViewModelProvider(this).get(GalleryViewModel.class);
//        binding = FragmentGalleryBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        dbHelper = new DBHelper(rootView.getContext());
        readableDB = dbHelper.getReadableDatabase();
        writeableDB = dbHelper.getWritableDatabase();

        IDTextView = (TextView) rootView.findViewById(R.id.changeID);
        password = (EditText) rootView.findViewById(R.id.changePassword);
        name = (EditText) rootView.findViewById(R.id.changeName);

        Cursor cursor = readableDB.rawQuery
                ("SELECT _id, ID, Password, Name FROM account", null);

        Intent i = getActivity().getIntent();           //getActivity()를 써야 사용이 가능
        String data = i.getStringExtra("UID");      //메인 액티비티에서 _id의 값을 받아옴
        int parseint = Integer.parseInt(data) - 1;            //_id값 -1
        cursor.moveToPosition(parseint);
        String searchID = cursor.getString(1);

        IDTextView.setText(searchID);


//        String toString = Integer.toString(parseint + 1);
//        TextView textView = (TextView) rootView.findViewById(R.id.textView);
//        textView.setText(toString);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getActivity().getIntent();           //getActivity()를 써야 사용이 가능
                String data = i.getStringExtra("UID");      //메인 액티비티에서 _id의 값을 받아옴
                int parseint = Integer.parseInt(data) - 1;            //_id값 -1
                String toString = Integer.toString(parseint + 1);
                cursor.moveToPosition(parseint);

                EditText changePassword = (EditText) rootView.findViewById(R.id.changePassword);
                EditText changeName = (EditText) rootView.findViewById(R.id.changeName);

                ContentValues row;

                row = new ContentValues();
                row.put("Password", changePassword.getText().toString());          //새로운 비밀번호 지정
                writeableDB.update("account", row, "_id = " + toString, null);
                row.put("Name", changeName.getText().toString());
                writeableDB.update("account", row, "_id = " + toString, null);


                Toast.makeText(getActivity(), "변경되었습니다.", Toast.LENGTH_SHORT).show();
                getActivity().finish();     //토스트 메시지 팝업 후 종료

//                resetInputID.setText("");
//                resetInputPassword.setText("");
            }
        });
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}