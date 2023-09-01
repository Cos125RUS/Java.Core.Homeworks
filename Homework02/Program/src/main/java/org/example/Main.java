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
    private static int turnCount; //Количество возможных ходов
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();


    public static void main(String[] args) {
        while (true) {
            entryInitializeParams();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (checkGameOver(DOT_HUMAN, "Победа!"))
                    break;
                aiTurn();
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
        int x, y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[y][x] = DOT_AI;
    }

    /**
     * Метод проверки победы
     *
     * @param c символ походившего
     * @return наличие победы
     */
    private static boolean checkWin(char c) {
        return (checkSide(c, 0, fieldSizeX - winCount + 1, fieldSizeY, 1, 0) ||
                checkSide(c, 0, fieldSizeX, fieldSizeY - winCount + 1, 0, 1) ||
                checkSide(c, 0, fieldSizeX - winCount + 1, fieldSizeY- winCount + 1,
                        1, 1) ||
                checkSide(c, winCount - 1, fieldSizeX - winCount + 1, fieldSizeY, 1,
                        -1));
    }

    /**
     * Проверка выигрыша по всем измерениям
     * @param c фишка
     * @param start стартовая клетка по вертикали
     * @param sizeX ограничение поля по Х
     * @param sizeY ограничение поля по У
     * @param stepX шаг по Х
     * @param stepY шаг по У
     * @return результат проверки
     */
    private static boolean checkSide(char c, int start, int sizeX, int sizeY, int stepX, int stepY) {
        for (int i = start; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                boolean check = true;
                for (int l = 0; l < winCount; l++) {
                    if (field[i + l * stepY][j + l * stepX] != c) {
                        check = false;
                        break;
                    }
                }
                if (check) return true;
            }
        }

        return false;
    }

    /**
     * Проверка выигрыша по горизонтали
     *
     * @param c фишка
     * @return результат проверки
     */
    private static boolean checkHorizon(char c) {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j <= fieldSizeX - winCount; j++) {
                boolean check = true;
                for (int l = 0; l < winCount; l++) {
                    if (field[i][j + l] != c) {
                        check = false;
                        break;
                    }
                }
                if (check) return true;
            }
        }

        return false;
    }

    /**
     * Проверка выигрыша по вертикали
     *
     * @param c фишка
     * @return результат проверки
     */
    private static boolean checkVertical(char c) {
        for (int i = 0; i <= fieldSizeY - winCount; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                boolean check = true;
                for (int l = 0; l < winCount; l++) {
                    if (field[i + l][j] != c) {
                        check = false;
                        break;
                    }
                }
                if (check) return true;
            }
        }

        return false;
    }

    /**
     * Проверка выигрыша по диагонали вниз
     *
     * @param c фишка
     * @return результат проверки
     */
    private static boolean checkDiagonalDown(char c) {
        for (int i = 0; i <= fieldSizeY - winCount; i++) {
            for (int j = 0; j <= fieldSizeX - winCount; j++) {
                boolean check = true;
                for (int l = 0; l < winCount; l++) {
                    if (field[i + l][j + l] != c) {
                        check = false;
                        break;
                    }
                }
                if (check) return true;
            }
        }

        return false;
    }

    /**
     * Проверка выигрыша по диагонали вверх
     *
     * @param c фишка
     * @return результат проверки
     */
    private static boolean checkDiagonalUp(char c) {
        for (int i = winCount - 1; i < fieldSizeY; i++) {
            for (int j = 0; j <= fieldSizeX - winCount; j++) {
                boolean check = true;
                for (int l = 0; l < winCount; l++) {
                    if (field[i - l][j + l] != c) {
                        check = false;
                        break;
                    }
                }
                if (check) return true;
            }
        }

        return false;
    }


    /**
     * Проверка на ничью
     *
     * @return результат проверки
     */
    private static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
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
        } else if (checkDraw()) {
            System.out.println("Ничья");
            return false;
        } else
            return false;
    }

}