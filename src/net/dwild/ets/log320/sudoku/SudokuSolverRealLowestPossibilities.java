package net.dwild.ets.log320.sudoku;

public class SudokuSolverRealLowestPossibilities {

    private Sudoku sudoku;
    private Sudoku solvedSudoku;
    private long solvingTime;

    private int[][] possibilities;

    public SudokuSolverRealLowestPossibilities(Sudoku sudoku) {
        this.sudoku = sudoku;
        this.solvedSudoku = sudoku.clone();
    }

    public boolean  solve() {
        long startTime = System.nanoTime();

        calcPossibilities();

        boolean r = tryNextSolve();

        long endTime = System.nanoTime();
        solvingTime = endTime - startTime;

        return r;
    }

    private void calcPossibilities() {
        possibilities = new int[9][9];

        for(int x=0;x < 9; x++) {
            for(int y=0;y < 9; y++) {
                possibilities[x][y] = 9 - solvedSudoku.getPossibilities(x, y).size();
            }
        }
    }

    private void recalcPossibilities(int x, int y) {
        for(int x2=0;x2 < 9; x2++) {
            possibilities[x2][y] = 9 - solvedSudoku.getPossibilities(x2, y).size();
        }

        for(int y2=0;y2 < 9; y2++) {
            possibilities[x][y2] = 9 - solvedSudoku.getPossibilities(x, y2).size();
        }

        int caseX = x/3 * 3;
        int caseY = y/3 * 3;

        for(int b = 0; b < 9; b++) {
            int tempX = caseX + b%3;
            int tempY = caseY + b/3;

            possibilities[tempX][tempY] = 9 - solvedSudoku.getPossibilities(tempX, tempY).size();
        }
    }

    private boolean trySolve(int i) {
        int x = i%9;
        int y = i/9;

        for(int n:solvedSudoku.getPossibilities(x, y)) {
            solvedSudoku.setNumber(x, y, n);

            recalcPossibilities(x, y);
            boolean solved = tryNextSolve();

            if(solved) {
                return true;
            }
            else {
                solvedSudoku.setNumber(x, y, 0);
                recalcPossibilities(x, y);
            }
        }

        return false;
    }

    private boolean tryNextSolve() {
        int maxI = 0;
        int maxPos = -1;
        for(int y=0;y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if(solvedSudoku.getNumber(x, y) == 0 && possibilities[x][y] > maxPos) {
                    maxI = y*9 + x;
                    maxPos = possibilities[x][y];
                }
            }
        }

        if(maxPos == -1) {
            return true;
        }

        boolean solved = trySolve(maxI);
        return solved;
    }

    public Sudoku getSudoku() {
        return sudoku;
    }

    public Sudoku getSolvedSudoku() {
        return solvedSudoku;
    }

    public long getSolvingTime() {
        return solvingTime;
    }
}
