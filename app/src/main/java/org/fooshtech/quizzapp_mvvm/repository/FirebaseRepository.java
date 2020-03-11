package org.fooshtech.quizzapp_mvvm.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.fooshtech.quizzapp_mvvm.Model.QuizListModel;

import java.util.List;

public class FirebaseRepository {

    private OnFirestoreTaskComplete mOnFirestoreTaskComplete;

    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference mQuizeRef = mFirebaseFirestore.collection("QuizList");

    public FirebaseRepository(OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.mOnFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void getQuizData() {

        mQuizeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    mOnFirestoreTaskComplete.quizListDataAdded(task.getResult().toObjects(QuizListModel.class));
                } else {
                    mOnFirestoreTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public interface OnFirestoreTaskComplete {
        void quizListDataAdded(List<QuizListModel> quiz);

        void onError(Exception e);
    }
}
