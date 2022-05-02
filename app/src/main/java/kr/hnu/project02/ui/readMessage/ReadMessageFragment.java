package kr.hnu.project02.ui.readMessage;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.hnu.project02.DBHelper;
import kr.hnu.project02.EmailDBHelper;
import kr.hnu.project02.R;
import kr.hnu.project02.databinding.FragmentSlideshowBinding;

public class ReadMessageFragment extends Fragment {

//    TextView sendIDTextView, getIDTextView, titleTextView, getTimeTextView, contentTextView;
//    DBHelper dbHelper;
//    EmailDBHelper emailDBHelper;
//    SQLiteDatabase readableDB, readableEmailDB;


    private FragmentSlideshowBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_read_message,
                container, false);



        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}