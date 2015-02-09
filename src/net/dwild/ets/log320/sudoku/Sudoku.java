package net.dwild.ets.log320.sudoku;

import java.util.ArrayList;
import java.util.Arrays;

public class Sudoku implements Cloneable {

    private int[][] sudoku;

    public Sudoku() {
        this(new int[9][9]);
    }

    public Sudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    public int getNumber(int x, int y) {
        return sudoku[y][x];
    }

    public void setNumber(int x, int y, int number) {
        sudoku[y][x] = number;
    }

    public boolean isValid(int x, int y, int number) {
        for(int i = 0; i < 9; i++) {
            if(sudoku[y][i] == number && x != i) {
                return false;
            }
        }

        for(int i = 0; i < 9; i++) {
            if(sudoku[i][x] == number && y != i) {
                return false;
            }
        }

        int caseX = x/3 * 3;
        int caseY = y/3 * 3;

        for(int i = 0; i < 9; i++) {
            int tempX = caseX + i%3;
            int tempY = caseY + i/3;

            if(sudoku[tempY][tempX] == number && !(tempY == y && tempX == x)) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Integer> getPossibilities(int x, int y) {
        if(sudoku[y][x] > 0) {
            if(isValid(x, y, sudoku[y][x])) {
                return new ArrayList<Integer>(Arrays.asList(sudoku[y][x]));
            }
            else {
                return new ArrayList<Integer>();
            }
        }

        boolean[] possibilities = new boolean[10];

        for(int i = 0; i < 9; i++) {
            possibilities[sudoku[y][i]] = true;
        }

        for(int i = 0; i < 9; i++) {
            possibilities[sudoku[i][x]] = true;
        }

        int caseX = x/3 * 3;
        int caseY = y/3 * 3;

        for(int i = 0; i < 9; i++) {
            int tempX = caseX + i%3;
            int tempY = caseY + i/3;

            possibilities[sudoku[tempY][tempX]] = true;
        }

        ArrayList<Integer> finalPossibilities = new ArrayList<Integer>();

        for(int i = 1; i < 10; i++) {
            if(!possibilities[i]) {
                finalPossibilities.add(i);
            }
        }

        return finalPossibilities;
    }

    public Sudoku clone() {
        int[][] sudokuClone = new int[9][9];

        for(int i = 0; i < 9; i++) {
            sudokuClone[i] = sudoku[i].clone();
        }

        return  new Sudoku(sudokuClone);
    }

    @Override
    public String toString() {
        String r = "Sudoku [";

        for(int[] sudokuLine:sudoku) {
            r += Arrays.toString(sudokuLine) + ", ";
        }

        if(r.endsWith(", ")) {
            r = r.substring(0, r.length() - 2);
        }

        r += "]";
        return r;
    }
}
