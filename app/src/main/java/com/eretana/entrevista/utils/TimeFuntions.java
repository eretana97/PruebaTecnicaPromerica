package com.eretana.entrevista.utils;

import java.util.Random;

public class TimeFuntions {

    public static int getRandMinutes(){
        Random rand = new Random();
        return rand.nextInt(10)+1;
    }

}
