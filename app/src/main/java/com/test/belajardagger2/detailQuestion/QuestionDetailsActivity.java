package com.test.belajardagger2.detailQuestion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.test.belajardagger2.MyApplication;
import com.test.belajardagger2.R;
import com.test.belajardagger2.common.DialogsManager;
import com.test.belajardagger2.common.ServerErrorDialogFragment;
import com.test.belajardagger2.networking.StackoverflowApi;
import com.test.belajardagger2.quetions.FetchQuestionDetailsUseCase;
import com.test.belajardagger2.quetions.FetchQuestionsListUseCase;
import com.test.belajardagger2.quetions.QuestionWithBody;

import retrofit2.Retrofit;

public class QuestionDetailsActivity extends AppCompatActivity implements
        QuestionDetailsViewMVC.Listener, FetchQuestionDetailsUseCase.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";
    private TextView mTXTQuestionBody;
    private String mQuestionId;
    private QuestionDetailsViewMVC mViewMvc;

    private DialogsManager mDialogsManager;

    private FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        mViewMvc = new QestionDetailsViewMvcImpl(LayoutInflater.from(this), null);
        setContentView(mViewMvc.getRootView());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Networking
        fetchQuestionDetailsUseCase = ((MyApplication) getApplication()).fetchQuestionDetailsUseCase();

        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);

        // Dialog error
        mDialogsManager = new DialogsManager(getSupportFragmentManager());

    }

    public static void start(Context context, String questionId){
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        fetchQuestionDetailsUseCase.registerListener(this);
        fetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(mQuestionId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);

        fetchQuestionDetailsUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question) {
        mViewMvc.bindQuestion(question);
    }

    @Override
    public void onFetchOfQuestionDetailsFailed() {
        mDialogsManager.shownRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }
}