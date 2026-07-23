package com.test.belajardagger2.questionslist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.belajardagger2.quetions.Question;
import com.test.belajardagger2.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionListViewMVCImpl  extends BaseViewMVC<QuestionListViewMvc.Listener> implements QuestionListViewMvc {

    private RecyclerView mRecyclerView;
    private QuestionsAdapter mQuestionAdapter;

    public QuestionListViewMVCImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.activity_main, container, false));

        //Initializing RecyclerView

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mQuestionAdapter = new QuestionsAdapter(new OnQuestionClickListener() {
            @Override
            public void onQuestionClicked(Question question) {
                for (Listener listener : getListeners()){
                    listener.onQuestionClicked(question);
                }
            }
        });

        mRecyclerView.setAdapter(mQuestionAdapter);
    }


    @Override
    public void bindQuestions(List<Question> questions) {
        mQuestionAdapter.bindData(questions);
    }

    public interface OnQuestionClickListener{
        void onQuestionClicked(Question question);
    }

    public static class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>{
        private final OnQuestionClickListener mOnQuestionClickListener;
        private List<Question> mQuestionList = new ArrayList<>(0);

        // View Holder
        public class QuestionsViewHolder extends RecyclerView.ViewHolder{
            public TextView mTitle;

            public QuestionsViewHolder(@NonNull View itemView){
                super(itemView);

                mTitle = itemView.findViewById(R.id.txt_title);
            }
        }

        public QuestionsAdapter(OnQuestionClickListener onQuestionClickListener){
            mOnQuestionClickListener = onQuestionClickListener;
        }

        public void bindData(List<Question> questions){
            mQuestionList = new ArrayList<>(questions);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_question_list_item, parent, false);
            return new QuestionsViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
            holder.mTitle.setText(mQuestionList.get(position).getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnQuestionClickListener.onQuestionClicked(mQuestionList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mQuestionList.size();
        }

    }

}
