package net.dwild.ets.log320.sudoku;

public class SudokuSolverRealLowestPossibilities {

    private Sudoku sudoku;
    private Sudoku solvedSudoku;
    private long solvingTime;

    private CachedSudokuPossibilities sudokuPossibilities;

    public SudokuSolverRealLowestPossibilities(Sudoku sudoku) {
        this.sudoku = sudoku;
        this.solvedSudoku = sudoku.clone();
    }

    public boolean  solve() {
        long startTime = System.nanoTime();

        this.sudokuPossibilities = new CachedSudokuPossibilities(solvedSudoku);

        boolean r = nextSolve();

        long endTime = System.nanoTime();
        solvingTime = endTime - startTime;

        return r;
    }

    private boolean trySolve(int x, int y) {
        int[] possibilities = sudokuPossibilities.getPossibilities(x, y);
        for(int j = 0; j < sudokuPossibilities.getNumberPossibilities(x, y); j++) {
            int number = possibilities[j];

            sudokuPossibilities.setNumber(x, y, number);

            boolean solved = nextSolve();

            if(solved) {
                return true;
            }
            else {
                sudokuPossibilities.setNumber(x, y, 0);
            }
        }

        return false;
    }

    private boolean nextSolve() {
        int minPos = 10;
        int minX = 0;
        int minY = 0;

        for(int y=0;y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if(sudokuPossibilities.getNumber(x, y) == 0 && sudokuPossibilities.getNumberPossibilities(x, y) < minPos) {
                    minPos = sudokuPossibilities.getNumberPossibilities(x, y);
                    minX = x;
                    minY = y;
                }
            }
        }

        if(minPos == 10) {
            return true;
        }

        return trySolve(minX, minY);
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
