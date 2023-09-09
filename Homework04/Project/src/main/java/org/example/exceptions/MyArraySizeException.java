package org.example.exceptions;

public class MyArraySizeException extends MyArrayException{
    private static final int sizeX = 4;
    private static final int sizeY = 4;
    private static final String sizeMessage = "Допустимый размер массива " + sizeX + "x" + sizeY;


    public MyArraySizeException(String message) {
        super(message + sizeMessage);
    }

    public MyArraySizeException(int y) {
        super(sizeMessage + ". Количество строк в вашем массиве: " + y);
    }

    public MyArraySizeException(int y, int x) {
        super(sizeMessage + ". Количество элементов в " + y + " ряду вашего массива: " + x);
    }
}
