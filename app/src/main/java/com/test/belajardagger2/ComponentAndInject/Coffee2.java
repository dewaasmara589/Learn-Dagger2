package com.test.belajardagger2.ComponentAndInject;

import javax.inject.Inject;

public class Coffee2 {
    private Farm2 farm2;
    private River2 river2;

    @Inject
    public Coffee2(Farm2 farm2, River2 river2) {
        this.farm2 = farm2;
        this.river2 = river2;
    }
}
