package org.fooshtech.quizzapp_mvvm.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import org.fooshtech.quizzapp_mvvm.Adapter.QuizListAdapter;
import org.fooshtech.quizzapp_mvvm.Model.QuizListModel;
import org.fooshtech.quizzapp_mvvm.R;
import org.fooshtech.quizzapp_mvvm.viewmodel.QuizViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements QuizListAdapter.onQuizListItemClicked {

    private RecyclerView mRecyclerView;
    private QuizViewModel mViewModel;
    private QuizListAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Animation mFadeInAnim,mFadeOutAnim;

    private NavController mNavController;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.list_view);
        mProgressBar = view.findViewById(R.id.list_progress);

        mFadeInAnim = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        mFadeOutAnim = AnimationUtils.loadAnimation(getContext(),R.anim.fade_out);

        mNavController = Navigation.findNavController(view);

        mAdapter = new QuizListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(QuizViewModel.class);
        mViewModel.getQuizList().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModels) {

                mRecyclerView.startAnimation(mFadeInAnim);
                mProgressBar.startAnimation(mFadeOutAnim);

                mAdapter.setList(quizListModels);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        ListFragmentDirections.ActionListFragmentToDetailsFragment action = ListFragmentDirections.actionListFragmentToDetailsFragment();
        action.setPosition(position);
        mNavController.navigate(action);
    }
}
