package net.dwild.ets.log320.sudoku;

public class CachedSudokuPossibilities {

    private Sudoku sudoku;

    private boolean[][] columns;
    private boolean[][] lines;
    private boolean[][] boxes;

    private boolean[][][] possibilities;
    private int[][] numberPossibilities;

    private int[][] cachedB;

    public CachedSudokuPossibilities(Sudoku sudoku) {
        this.sudoku = sudoku;

        columns = new boolean[9][10];
        lines = new boolean[9][10];
        boxes = new boolean[9][10];

        possibilities = new boolean[9][9][10];
        numberPossibilities = new int[9][9];

        cachedB = new int[9][9];
        for(int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                cachedB[x][y] = x/3 + y/3*3;
            }
        }

        calcPossibilities();
    }

    private void calcPossibilities() {
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                int number = sudoku.getNumber(x, y);

                int b = cachedB[x][y];

                columns[x][number] = true;
                lines[y][number] = true;
                boxes[b][number] = true;
            }
        }

        for(int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                numberPossibilities[x][y] = 9;

                int b = cachedB[x][y];
                for (int number = 1; number < 10; number++) {
                    if(columns[x][number] || lines[y][number] || boxes[b][number]) {
                        possibilities[x][y][number] = true;
                        numberPossibilities[x][y]--;
                    }
                }
            }
        }
    }

    public int getNumber(int x, int y) {
        return sudoku.getNumber(x, y);
    }

    public void setNumber(int x, int y, int newNumber) {
        int oldNumber = sudoku.getNumber(x, y);

        sudoku.setNumber(x, y, newNumber);

        int b = cachedB[x][y];

        columns[x][oldNumber] = false;
        lines[y][oldNumber] = false;
        boxes[b][oldNumber] = false;

        columns[x][newNumber] = true;
        lines[y][newNumber] = true;
        boxes[b][newNumber] = true;

        for(int x2 = 0;x2 < 9; x2++) {
            int b2 = cachedB[x2][y];

            boolean oldBeforePossibilities = possibilities[x2][y][oldNumber];
            boolean newBeforePossibilities = possibilities[x2][y][newNumber];

            possibilities[x2][y][oldNumber] = false || columns[x2][oldNumber] || boxes[b2][oldNumber];
            possibilities[x2][y][newNumber] = true;

            numberPossibilities[x2][y]+= (oldNumber != 0 && oldBeforePossibilities !=  possibilities[x2][y][oldNumber]?1:0) + (newNumber != 0 && newBeforePossibilities !=  possibilities[x2][y][newNumber]?-1:0);
        }

        for(int y2 = 0;y2 < 9; y2++) {
            int b2 = cachedB[x][y2];

            boolean oldBeforePossibilities = possibilities[x][y2][oldNumber];
            boolean newBeforePossibilities = possibilities[x][y2][newNumber];

            possibilities[x][y2][oldNumber] = false || lines[y2][oldNumber] || boxes[b2][oldNumber];
            possibilities[x][y2][newNumber] = true;

            numberPossibilities[x][y2]+= (oldNumber != 0 && oldBeforePossibilities !=  possibilities[x][y2][oldNumber]?1:0) + (newNumber != 0 && newBeforePossibilities !=  possibilities[x][y2][newNumber]?-1:0);
        }

        final int caseX = x/3 * 3;
        final int caseY = y/3 * 3;

        for(int x2 = caseX; x2 < (caseX+3); x2++) {
            for(int y2 = caseY; y2 < (caseY+3); y2++) {
                boolean oldBeforePossibilities = possibilities[x2][y2][oldNumber];
                boolean newBeforePossibilities = possibilities[x2][y2][newNumber];

                possibilities[x2][y2][oldNumber] = false || columns[x2][oldNumber] || lines[y2][oldNumber];
                possibilities[x2][y2][newNumber] = true;

                numberPossibilities[x2][y2] += (oldNumber != 0 && oldBeforePossibilities != possibilities[x2][y2][oldNumber] ? 1 : 0) + (newNumber != 0 && newBeforePossibilities != possibilities[x2][y2][newNumber] ? -1 : 0);
            }
        }
    }

    public int[] getPossibilities(int x, int y) {
        int[] finalPossibilities = new int[9];

        int i = 0;
        for(int number = 1; number < 10; number++) {
            if(!possibilities[x][y][number]) {
                finalPossibilities[i] = number;
                i++;
            }
        }

        return finalPossibilities;
    }

    public int getNumberPossibilities(int x, int y) {
        return numberPossibilities[x][y];
    }
}
