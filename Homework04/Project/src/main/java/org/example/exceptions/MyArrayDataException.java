package org.example.exceptions;

public class MyArrayDataException extends MyArrayException{
    private final int x;
    private final int y;

    public MyArrayDataException(int x, int y) {
        super("Неправильный формат данных. Ошибка в элементе массива [" + x + "][" + y + "]");
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getProblemPoint (){
        return String.format("[%d][%d]", x, y);
    }
}
