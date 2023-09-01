package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final char DOT_HUMAN = 'X'; //Фишка человека
    private static final char DOT_AI = 'O'; //Фишка компьютера
    private static final char DOT_EMPTY = '*'; //Пустое поле
    private static char[][] field; //Игровое поле
    private static int fieldSizeX; //Размер поля по X
    private static int fieldSizeY; //Размер поля по Y
    private static int winCount; //Выигрышная комбинация
    private static int turnCount; //Счётчик ходов
    private static int turnQuantity; //Количество возможных ходов
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();


    public static void main(String[] args) {
        while (true) {
            entryInitializeParams();
            printField();
            while (true) {
                humanTurn();
                turnCount++;
                printField();
                if (checkGameOver(DOT_HUMAN, "Победа!"))
                    break;
                aiTurn();
                turnCount++;
                printField();
                if (checkGameOver(DOT_AI, "Поражение"))
                    break;
            }
            System.out.print("Повторить?(y/n) ");
            if (!scanner.next().equalsIgnoreCase("y"))
                break;
        }


    }

    /**
     * Ввод стартовых параметров игры
     */
    private static void entryInitializeParams() {
        int x = enterNum("Введите размер поля по Х: ");
        int y = enterNum("Введите размер поля по Y: ");
        int win = enterNum("Введите выигрышную комбинацию: ");
        initialize(x, y, win);
    }

    /**
     * Ввод целочисленных параметров
     *
     * @param entryLine приглашение ко вводу
     * @return введённый параметр
     */
    private static int enterNum(String entryLine) {
        int num;
        while (true) {
            System.out.print(entryLine);
            if (scanner.hasNextInt()) {
                num = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Ошибка ввода");
                scanner.nextLine();
            }
        }
        return num;
    }

    /**
     * Инициализация объектов игры
     */
    private static void initialize(int x, int y, int win) {
        fieldSizeX = x;
        fieldSizeY = y;
        winCount = win;
        turnCount = 0;
        turnQuantity = fieldSizeX * fieldSizeY;
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
    }

    /**
     * Отрисовка игрового поля
     */
    private static void printField() {
        System.out.print("+");
        for (int x = 0; x < fieldSizeX * 2 + 1; x++) {
            System.out.print((x % 2 == 0) ? "-" : x / 2 + 1);
        }
        System.out.println();
        for (int y = 0; y < fieldSizeY; y++) {
            System.out.print(y + 1 + "|");
            for (int x = 0; x < fieldSizeX; x++) {
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }
        for (int x = 0; x < fieldSizeX * 2 + 2; x++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Ход игрока
     */
    private static void humanTurn() {
        int x, y;
        do {
            System.out.print("Введите координату (x y): ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[y][x] = DOT_HUMAN;
    }

    /**
     * Проверка значения ячейки
     *
     * @param x координата x
     * @param y координата y
     * @return проверка истинности
     */
    private static boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }

    /**
     * Проверка выхода за границы поля
     *
     * @param x координата x
     * @param y координата y
     * @return проверка истинности
     */
    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Обработка хода компьютера
     */
    private static void aiTurn() {
        int[] point = new int[2];
        if (!findTurnAI(DOT_HUMAN, point, 1)) {
            boolean findCheck = false;
            for (int i = 1; i < winCount - 1; i++) {
                if (findTurnAI(DOT_AI, point, i)) {
                    findCheck = true;
                    break;
                }
            }
            if (!findCheck) {
                do {
                    point[0] = random.nextInt(fieldSizeX);
                    point[1] = random.nextInt(fieldSizeY);
                } while (!isCellEmpty(point[0], point[1]));
            }
        }
        field[point[1]][point[0]] = DOT_AI;
    }

    /**
     * Поиск оптимального хода ИИ
     *
     * @return координаты
     */
    private static boolean findTurnAI(char c, int[] point, int winCoef) {
        if (checkSide(c, 0, fieldSizeX - winCount + 1, fieldSizeY, 1, 0,
                winCount - winCoef, point)) {
            return true;
        } else if (checkSide(c, 0, fieldSizeX, fieldSizeY - winCount + 1, 0,
                1, winCount - winCoef, point)) {
            return true;
        } else if (checkSide(c, 0, fieldSizeX - winCount + 1,
                fieldSizeY - winCount + 1, 1, 1, winCount - winCoef, point)) {
            return true;
        } else if (checkSide(c, winCount - 1, fieldSizeX - winCount + 1, fieldSizeY,
                1, -1, winCount - winCoef, point)) {
            return true;
        }
        return false;
    }

    /**
     * Метод проверки победы
     *
     * @param c символ походившего
     * @return наличие победы
     */
    private static boolean checkWin(char c) {
        return (checkSide(c, 0, fieldSizeX - winCount + 1, fieldSizeY, 1, 0,
                winCount, new int[2]) ||
                checkSide(c, 0, fieldSizeX, fieldSizeY - winCount + 1, 0, 1,
                        winCount, new int[2]) ||
                checkSide(c, 0, fieldSizeX - winCount + 1, fieldSizeY - winCount + 1,
                        1, 1, winCount, new int[2]) ||
                checkSide(c, winCount - 1, fieldSizeX - winCount + 1, fieldSizeY, 1,
                        -1, winCount, new int[2]));
    }

    /**
     * Проверка выигрыша по всем измерениям
     *
     * @param c     фишка
     * @param start стартовая клетка по вертикали
     * @param sizeX ограничение поля по Х
     * @param sizeY ограничение поля по У
     * @param stepX шаг по Х
     * @param stepY шаг по У
     * @return результат проверки
     */
    private static boolean checkSide(char c, int start, int sizeX, int sizeY, int stepX, int stepY,
                                     int toWin, int[] point) {
        for (int i = start; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                int checkCount = 0;
                boolean checkWin = true;
                for (int l = 0; l < winCount; l++) {
                    if (field[i + l * stepY][j + l * stepX] == c) {
                        checkCount++;
                    } else if (field[i + l * stepY][j + l * stepX] == DOT_EMPTY) {
                        point[0] = j + l * stepX;
                        point[1] = i + l * stepY;
                    } else {
                        checkWin = false;
                    }
                }
                if (checkCount == toWin && checkWin) return true;
            }
        }

        return false;
    }

    /**
     * Проверка конца игры
     *
     * @param c фишка
     * @param s победное слово
     * @return результат проверки
     */
    private static boolean checkGameOver(char c, String s) {
        if (checkWin(c)) {
            System.out.println(s);
            return true;
        } else if (turnCount == turnQuantity) {
            System.out.println("Ничья");
            return true;
        } else
            return false;
    }

}