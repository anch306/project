package kr.hnu.project02.ui.home;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kr.hnu.project02.R;

public class HomeViewModel extends ViewModel {




    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
    //    mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}