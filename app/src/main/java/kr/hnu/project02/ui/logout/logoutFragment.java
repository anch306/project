package kr.hnu.project02.ui.logout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.hnu.project02.R;

public class logoutFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        getActivity().moveTaskToBack(true);
        getActivity().finish();
        android.os.Process.killProcess(android.os.Process.myPid());


        return inflater.inflate(R.layout.logout_fragment, container, false);
    }


}