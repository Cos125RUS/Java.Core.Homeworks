package org.example;

import org.example.exceptions.MyArrayDataException;
import org.example.exceptions.MyArraySizeException;

public class ArrayAnalyzer {
    private static final int sizeX = 4;
    private static final int sizeY = 4;

    public static int sum(String[][] arr) throws MyArrayDataException, MyArraySizeException {
        int sum = 0;
        if (arr.length != sizeY)
            throw new MyArraySizeException(arr.length);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != sizeX)
                throw new MyArraySizeException(i, arr[i].length);
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e){
                    throw new MyArrayDataException(j, i);
                }
            }
        }

        return sum;
    }
}
