package com.test.belajardagger2.ComponentAndInject;

// think about this class as the waiter in coffee shop

import dagger.Component;

@Component
public interface CoffeeComponent {
    Coffee2 getCoffeeCup();
}
