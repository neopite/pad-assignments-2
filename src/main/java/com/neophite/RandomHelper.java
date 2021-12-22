package com.neophite;

import java.util.Random;

public class RandomHelper {
    public static int getRandomNumber(int to){
        var random = new Random();
        var randomNumber = random.nextInt(to);
        return randomNumber;
    }
}
