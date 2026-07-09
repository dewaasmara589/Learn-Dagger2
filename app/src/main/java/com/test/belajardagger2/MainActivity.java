package com.test.belajardagger2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.test.belajardagger2.ComponentAndInject.Coffee2;
import com.test.belajardagger2.ComponentAndInject.CoffeeComponent;
import com.test.belajardagger2.ComponentAndInject.DaggerCoffeeComponent;
import com.test.belajardagger2.ComponentAndInject.Farm2;
import com.test.belajardagger2.ComponentAndInject.River2;
import com.test.belajardagger2.Introduction.Coffee;
import com.test.belajardagger2.Introduction.Farm;
import com.test.belajardagger2.Introduction.River;

public class MainActivity extends AppCompatActivity {

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
        CoffeeComponent coffeeComponent = DaggerCoffeeComponent.create();
        coffeeComponent.getCoffeeCup();

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
    }
}