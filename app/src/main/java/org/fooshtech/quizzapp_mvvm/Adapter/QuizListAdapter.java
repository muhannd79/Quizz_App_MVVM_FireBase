package org.fooshtech.quizzapp_mvvm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.fooshtech.quizzapp_mvvm.Model.QuizListModel;
import org.fooshtech.quizzapp_mvvm.R;
import org.fooshtech.quizzapp_mvvm.viewmodel.QuizViewModel;

import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.QuizViewHolder> {

    private List<QuizListModel> list;

    private onQuizListItemClicked mOnQuizListItemClicked;

    public QuizListAdapter(onQuizListItemClicked mOnQuizListItemClicked) {
        this.mOnQuizListItemClicked = mOnQuizListItemClicked;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {

        holder.listTitle.setText(list.get(position).getName());

        String imgUrl = list.get(position).getImage();
        Glide.with(holder.itemView.getContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(holder.listImage);

        holder.listDesc.setText(list.get(position).getDesc());
        holder.listLevel.setText(list.get(position).getLevel());
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public List<QuizListModel> getList() {
        return list;
    }

    public void setList(List<QuizListModel> list) {
        this.list = list;
    }


    public class QuizViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView listImage;
        private TextView listTitle;
        private TextView listDesc;
        private TextView listLevel;
        private Button listBtn;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            listImage = itemView.findViewById(R.id.list_image);
            listTitle = itemView.findViewById(R.id.list_title);
            listDesc = itemView.findViewById(R.id.list_desc);
            listLevel = itemView.findViewById(R.id.list_difficulty);
            listBtn = itemView.findViewById(R.id.list_btn);

            listBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnQuizListItemClicked.onItemClicked(getAdapterPosition());
        }
    }


    public interface onQuizListItemClicked{

        void onItemClicked(int position);
    }
}
