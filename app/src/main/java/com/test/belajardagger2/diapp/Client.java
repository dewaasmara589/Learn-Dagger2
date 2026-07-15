package com.test.belajardagger2.diapp;

public class Client {

    // Constructor Injection
    private final Service1 mService1;

    // Method Injection
    private Service2 mService2;

    // Field Injection
    public Service3 mService3;

    public Client(Service1 mService1){
        this.mService1 = mService1;
    }

    public Service2 getmService2() {
        return mService2;
    }

    public void doAnyThing(){
        mService1.doSomething();
        mService2.doSomething();
        mService3.doSomething();
    }
}
