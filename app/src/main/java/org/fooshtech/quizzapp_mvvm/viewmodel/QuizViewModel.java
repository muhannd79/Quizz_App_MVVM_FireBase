package org.fooshtech.quizzapp_mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.fooshtech.quizzapp_mvvm.Model.QuizListModel;
import org.fooshtech.quizzapp_mvvm.repository.FirebaseRepository;

import java.util.List;

public class QuizViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<QuizListModel>> mQuizList = new MutableLiveData<>();

    private FirebaseRepository mRepository = new FirebaseRepository(this);

    public QuizViewModel(){
        mRepository.getQuizData();
    }

    public LiveData<List<QuizListModel>> getQuizList() {
        return mQuizList;
    }


    @Override
    public void quizListDataAdded(List<QuizListModel> quiz) {
        mQuizList.setValue(quiz);

    }

    @Override
    public void onError(Exception e) {


    }


}
