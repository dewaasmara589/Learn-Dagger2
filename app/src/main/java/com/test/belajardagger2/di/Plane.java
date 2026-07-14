package com.test.belajardagger2.di;


import android.util.Log;
import android.widget.Toast;

public class Plane {

    Engine e = new Engine();
    Wings w = new Wings();
    PlaneType t = new PlaneType();

    public Plane(Engine e, Wings w, PlaneType t){
        this.e = e;
        this.w = w;
        this.t = t;
    }
    
    public void TakeOff(){
        Log.i("TAG", "Taking Off");
    }
}
