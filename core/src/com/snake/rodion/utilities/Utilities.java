package com.snake.rodion.utilities;

import java.util.ArrayList;

public class Utilities {
    public static ArrayList<Integer> number2Array(int number){
        ArrayList<Integer> digits = new ArrayList<Integer>();
            while(number > 0) {
                digits.add(number % 10);
                number /= 10;
            }
            return digits;
    }
}
