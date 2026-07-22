package com.test.belajardagger2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.belajardagger2.ComponentAndInject.Coffee2;
import com.test.belajardagger2.ComponentAndInject.CoffeeComponent;
import com.test.belajardagger2.ComponentAndInject.DaggerCoffeeComponent;
import com.test.belajardagger2.ComponentAndInject.Farm2;
import com.test.belajardagger2.ComponentAndInject.River2;
import com.test.belajardagger2.Introduction.Coffee;
import com.test.belajardagger2.Introduction.Farm;
import com.test.belajardagger2.Introduction.River;
import com.test.belajardagger2.di.Engine;
import com.test.belajardagger2.di.Plane;
import com.test.belajardagger2.di.PlaneType;
import com.test.belajardagger2.di.Wings;
import com.test.belajardagger2.di2.Car;
import com.test.belajardagger2.di2.EngineCar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<QuestionsListResponseSchema> {
    private RecyclerView mRecyclerView;
    private QuestionsAdapter mQuestionAdapter;

    private StackoverflowApi mStackoverflowApi;

    private Call<QuestionsListResponseSchema> mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Boilerplate
        Farm farm = new Farm();
        River river =  new River();
        Coffee coffee =  new Coffee();
        coffee.getCoffeeCup(farm, river);

        // Ilustrasi 1
//                  Coffee
//              |             |
//        MineralWAter    CoffeeBean
//              |             |
//            River          Farm

//----------------------------------------------------------------------------------------------------

        // Manual Dependency Injection
//        Farm2 farm2 = new Farm2();
//        River2 river2 = new River2();
//        Coffee2 coffee2 = new Coffee2(farm2, river2);

        // Dagger
        // DaggerCoffeeComponent error just run app
        CoffeeComponent coffeeComponent = DaggerCoffeeComponent.create();
        coffeeComponent.getCoffeeCup();
        Log.d("TAG", "onCreate: " + coffeeComponent.getCoffeeCup().getCoffeCup());

        // Ilustrasi 2
//                              CoffeeShop
//                     |                            |
//                  Coffee(*)                   Waiters(**)
//              |                 |
//        MineralWAter(*)     CoffeeBean(*)
//              |                 |
//            River(*)           Farm(*)
//
// (*)@Inject                                   (**)@Component
//              Dependecies Labels              Creator Labels

//----------------------------------------------------------------------------------------------------

        Engine e = new Engine();
        Wings w = new Wings();
        PlaneType t = new PlaneType();

        Plane p = new Plane(e, w, t);
        p.TakeOff();

//----------------------------------------------------------------------------------------------------

        // Functional engine (Electric or GasOil)
        EngineCar engineCar = new EngineCar();
        Car car = new Car(engineCar);
        car.start();


        //Ilustrasi

//        ---------------------------------
//        |                    _________  |
//        |                    | Engine|  |
//        |                    |_______|  |
//        |         Car                   |
//        |                               |
//        ---------------------------------

//----------------------------------------------------------------------------------------------------

        //Ilustrasi diapp

//      | Class A | ---Make Use Of---> | Class B | ---Make Use Of---> | Class C |

//         [Class B -> Class A = Service]      [Class B -> Class C = Client]

//      Dependence injection is the act of injecting service into clients from outside. class A = client
//      class b = service, class a depends on b.

//        Dependency Inject
//        1. Constructor Injection
//          - Simple
//          - Inject Fields can be finalized
//          - Easy to Mock services in Unit Test
//
//        2. Methods
//          - Method signature reflects dependency
//          - Can happen after constructor
//
//        3. Field Injection
//          - Can happen after constructor

//----------------------------------------------------------------------------------------------------

        //Initializing RecyclerView

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mQuestionAdapter = new QuestionsAdapter(new OnQuestionClickListener() {
            @Override
            public void onQuestionClicked(Question question) {
                QuestionDetailsActivity.start(MainActivity.this, question.getId());
            }
        });

        mRecyclerView.setAdapter(mQuestionAdapter);

        //Initializing Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackoverflowApi = retrofit.create(StackoverflowApi.class);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mCall = mStackoverflowApi.lastActiveQuestions(20);
        mCall.enqueue(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCall!=null){
            mCall.cancel();
        }
    }

    @Override
    public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
        QuestionsListResponseSchema responseSchema;
        if (response.isSuccessful() && (responseSchema = response.body()) != null){
            mQuestionAdapter.bindData(responseSchema.getQuestions());
        }else {
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(Call<QuestionsListResponseSchema> call, Throwable throwable) {
        QuestionsListResponseSchema responseSchema;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss();
    }

    //----------------------------------------------------------------------------------------------------

    /************** RecyclerView Adapter ****************/
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