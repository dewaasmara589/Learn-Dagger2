package com.test.belajardagger2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.test.belajardagger2.ComponentAndInject.CoffeeComponent;
import com.test.belajardagger2.ComponentAndInject.DaggerCoffeeComponent;
import com.test.belajardagger2.Introduction.Coffee;
import com.test.belajardagger2.Introduction.Farm;
import com.test.belajardagger2.Introduction.River;
import com.test.belajardagger2.common.DialogsManager;
import com.test.belajardagger2.common.ServerErrorDialogFragment;
import com.test.belajardagger2.detailQuestion.QuestionDetailsActivity;
import com.test.belajardagger2.di.Engine;
import com.test.belajardagger2.di.Plane;
import com.test.belajardagger2.di.PlaneType;
import com.test.belajardagger2.di.Wings;
import com.test.belajardagger2.di2.Car;
import com.test.belajardagger2.di2.EngineCar;
import com.test.belajardagger2.networking.StackoverflowApi;
import com.test.belajardagger2.questionslist.QuestionListViewMVCImpl;
import com.test.belajardagger2.questionslist.QuestionListViewMvc;
import com.test.belajardagger2.quetions.FetchQuestionsListUseCase;
import com.test.belajardagger2.quetions.Question;

import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements
        QuestionListViewMvc.Listener, FetchQuestionsListUseCase.Listener {

    private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;
    private FetchQuestionsListUseCase fetchQuestionsListUseCase;
    private QuestionListViewMvc mViewMVC;

    // Dialog Fragments
    private DialogsManager mDialogsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        mViewMVC = new QuestionListViewMVCImpl(LayoutInflater.from(this), null);

        setContentView(mViewMVC.getRootView());
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

        //Networking
        fetchQuestionsListUseCase = ((MyApplication) getApplication()).getCompositionRoot().fetchQuestionsListUseCase();

        // Dialog Manager
        mDialogsManager = new DialogsManager(getSupportFragmentManager());

    }

    @Override
    protected void onStart() {
        super.onStart();

        mViewMVC.registerListener(this);

        fetchQuestionsListUseCase.registerListener(this);
        fetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mViewMVC.unregisterListener(this);

        fetchQuestionsListUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchQuestionsSucceeded(List<Question> questions) {
        mViewMVC.bindQuestions(questions);
    }

    @Override
    public void onFetchQuestionsFailed() {
        mDialogsManager.shownRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(MainActivity.this, question.getId());
    }

    //----------------------------------------------------------------------------------------------------

    /************** RecyclerView Adapter ****************/

}