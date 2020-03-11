package org.fooshtech.quizzapp_mvvm.fragments;

import android.os.Bundle;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.fooshtech.quizzapp_mvvm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    private static final String TAG = "StartFragment";
    private ProgressBar mStartProgress;
    private TextView mStartFeedBack;

    private FirebaseAuth mFirebaseAuth;

    private NavController mNavController;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();

        mNavController = Navigation.findNavController(view);

        mStartProgress = view.findViewById(R.id.start_progressBar);
        mStartFeedBack = view.findViewById(R.id.start_feedBack);

        mStartFeedBack.setText("Checking User Account...");
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if(currentUser == null){
            //Create a New Account
            mStartFeedBack.setText("Creating An Account...");

            mFirebaseAuth .signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //that mean the new account has been created
                        mStartFeedBack.setText("Account has benn Created...");
                        mNavController.navigate(R.id.action_startFragment_to_listFragment);
                    } else{
                        // there is an error
                        mStartFeedBack.setText("Error Creating An Account...");
                        Log.d(TAG,"ErrorLogin : "+task.getException());
                    }
                }
            });
        } else  {
            // Navigate to HomePage
            mStartFeedBack.setText("Logged in...");
            mNavController.navigate(R.id.action_startFragment_to_listFragment);
        }
    }
}
