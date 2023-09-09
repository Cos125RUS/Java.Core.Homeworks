package org.example;

import org.example.exceptions.MyArrayDataException;
import org.example.exceptions.MyArrayException;
import org.example.exceptions.MyArraySizeException;

import java.util.Random;

public class Main {
    public static final Random random = new Random();

    public static void main(String[] args) {
        String[][] array = newArray();
        fillArray(array);
        print(array);
        sum(array);
    }

    public static String[][] newArray(){
        int y = giveMeSize();
        String[][] array = new String[y][];
        for (int i = 0; i < y; i++) {
            array[i] = new String[giveMeSize()];
        }
        return array;
    }

    public static int giveMeSize(){
        if (random.nextInt(5) == 1)
            return random.nextInt(2, 6);
        else
            return 4;
    }

    public static void fillArray(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (random.nextInt(10) == 1) {
                    arr[i][j] = "&";
                } else {
                    arr[i][j] = String.format("%d", random.nextInt(10));
                }
            }
        }
    }

    public static void print(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void sum(String[][] array){
        int res = 0;
        do{
            try {
                res = ArrayAnalyzer.sum(array);
                System.out.println("Сумма элементов массива = " + res);
            } catch (MyArraySizeException e){
                System.out.println(e.getMessage());
                System.out.println("Любезно создан новый массив");
                array = newArray();
                fillArray(array);
                print(array);
            } catch (MyArrayDataException e) {
                System.out.println(e.getMessage());
                System.out.println("Так уж и быть, исправим");
                array[e.getY()][e.getX()] = String.format("%d", random.nextInt(10));
                print(array);
            }
        } while (res == 0);
    }
}