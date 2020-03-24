package org.fooshtech.quizzapp_mvvm.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.fooshtech.quizzapp_mvvm.Model.QuestionsModel;
import org.fooshtech.quizzapp_mvvm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    private static final String TAG = "QuizFragment";
    private FirebaseFirestore mFirebaseFirestore;

    private String quizId;

    private TextView quizTitle;

    // Firebase Data
    private List<QuestionsModel> allQList = new ArrayList<>();
    private long totalQ =4;
    private List<QuestionsModel> questionToAns = new ArrayList<>();

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        quizTitle = view.findViewById(R.id.quiz_title);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        quizId = QuizFragmentArgs.fromBundle(getArguments()).getQuizid();
        totalQ =  QuizFragmentArgs.fromBundle(getArguments()).getTotalQuestions() -1;
        mFirebaseFirestore.collection("QuizList")
                .document(quizId).collection("Questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            allQList = task.getResult().toObjects(QuestionsModel.class);
                            pickQ();
                        } else {
                            quizTitle.setText("Error.. loading Data");
                        }
                    }
                });

    }

    private void pickQ() {
        for (int i = 0; i < totalQ; i++) {

            int randomnumber = getRandomInteger(allQList.size(),0);
            questionToAns.add(allQList.get(randomnumber));
            allQList.remove(randomnumber);
            Log.d("Soso", "randomnumber = " +randomnumber+ "  Questions :" + i + " - " + questionToAns.get(i).getQuestion() );

        }
    }

    public static int getRandomInteger(int max, int min){
        return ((int) (Math.random()*(max-min))) + min;
    }
}
