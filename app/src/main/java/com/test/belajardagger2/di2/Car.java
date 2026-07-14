package com.test.belajardagger2.di2;

public class Car {
    // Cara 1 umum dilakukan untuk inisialisasi
//    private EngineCar engineCar = new EngineCar();

    // Cara 2 dengan injection
    private EngineCar engineCar;

    public Car(EngineCar engineCar){
        this.engineCar = engineCar;
    }

    public void start(){
        engineCar.start();
    }
}
