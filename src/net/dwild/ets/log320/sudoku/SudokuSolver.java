package net.dwild.ets.log320.sudoku;

public class SudokuSolver {

    private Sudoku sudoku;
    private Sudoku solvedSudoku;
    private long solvingTime;

    public SudokuSolver(Sudoku sudoku) {
        this.sudoku = sudoku;
        this.solvedSudoku = sudoku.clone();
    }

    public boolean  solve() {
        long startTime = System.nanoTime();

        boolean r = trySolve(0);

        long endTime = System.nanoTime();
        solvingTime = endTime - startTime;

        return r;
    }

    private boolean trySolve(int i) {
        if(i >= 81){
            return true;
        }

        int x = i%9;
        int y = i/9;

        if(sudoku.getNumber(x,y) > 0) return trySolve(i+1);

        for(int n:solvedSudoku.getPossibilities(x, y)) {
            solvedSudoku.setNumber(x, y, n);

            boolean solved = trySolve(i+1);

            if(solved) {
                return true;
            }
            else {
                solvedSudoku.setNumber(x, y, 0);
            }
        }

        return false;
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
