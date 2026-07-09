package com.test.belajardagger2.Introduction;

public class Coffee {
    String coffeeCup;

    public String getCoffeeCup(Farm farm, River river) {
        coffeeCup =  farm.getCoffeeBeans() + river.getWater();

        return coffeeCup;
    }

    // Inversion of Control (IoC)
}
