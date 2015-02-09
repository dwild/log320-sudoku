package net.dwild.ets.log320.sudoku;

public class SudokuSolverLowestPossibilities {

    private Sudoku sudoku;
    private Sudoku solvedSudoku;
    private long solvingTime;

    private int[][] possibilities;

    public SudokuSolverLowestPossibilities(Sudoku sudoku) {
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
                if(solvedSudoku.getNumber(x, y) > 0) {
                    for(int x2=0;x2 < 9; x2++) {
                        possibilities[x2][y]++;
                    }

                    for(int y2=0;y2 < 9; y2++) {
                        possibilities[x][y2]++;
                    }

                    int caseX = x/3 * 3;
                    int caseY = y/3 * 3;

                    for(int i = 0; i < 9; i++) {
                        int tempX = caseX + i%3;
                        int tempY = caseY + i/3;

                        possibilities[tempX][tempY]++;
                    }
                }
            }
        }
    }

    private boolean trySolve(int i) {
        int x = i%9;
        int y = i/9;

        for(int n:solvedSudoku.getPossibilities(x, y)) {
            solvedSudoku.setNumber(x, y, n);

            boolean solved = tryNextSolve();

            if(solved) {
                return true;
            }
            else {
                solvedSudoku.setNumber(x, y, 0);
            }
        }

        return false;
    }

    private boolean tryNextSolve() {

        int maxI = 0;
        int maxPos = 0;
        for(int x=0;x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if(solvedSudoku.getNumber(x, y) == 0 && possibilities[x][y] > maxPos) {
                    maxI = y*9 + x;
                    maxPos = possibilities[x][y];
                }
            }
        }

        if(maxPos == 0) {
            return true;
        }

        int x = maxI%9;
        int y = maxI/9;

        for(int x2=0;x2 < 9; x2++) {
            possibilities[x2][y]++;
        }

        for(int y2=0;y2 < 9; y2++) {
            possibilities[x][y2]++;
        }

        int caseX = x/3 * 3;
        int caseY = y/3 * 3;

        for(int i = 0; i < 9; i++) {
            int tempX = caseX + i%3;
            int tempY = caseY + i/3;

            possibilities[tempX][tempY]++;
        }

        boolean solved = trySolve(maxI);

        if(!solved) {
            for(int x2=0;x2 < 9; x2++) {
                possibilities[x2][y]--;
            }

            for(int y2=0;y2 < 9; y2++) {
                possibilities[x][y2]--;
            }

            for(int i = 0; i < 9; i++) {
                int tempX = caseX + i%3;
                int tempY = caseY + i/3;

                possibilities[tempX][tempY]--;
            }
        }

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
