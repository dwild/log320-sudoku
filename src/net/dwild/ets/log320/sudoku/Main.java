package net.dwild.ets.log320.sudoku;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        String usage = "Usage: java -jar sudoku.jar FICHIER [FICHIER...]";

        if (args.length < 1 ) {
            System.out.println(usage);
        }
        else {
            for(String file:args) {
                solveSudoku(file);
            }
        }
    }

    public static void solveSudoku(String filename)  {
        try {
            InputStream fileReader = new BufferedInputStream(new FileInputStream(filename));

            SudokuReader sudokuReader = new SudokuReader(fileReader);

            Sudoku sudoku = sudokuReader.readSudoku();

            SudokuSolver sudokuSolver = new SudokuSolver(sudoku);
            if(sudokuSolver.solve()) {
                System.out.println(sudokuSolver.getSolvedSudoku());
                System.out.println(sudokuSolver.getSolvingTime());
            }
            else {
                System.out.println("Sudoku impossible à résoudre.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable.");
        } catch (IOException e) {
            System.out.println("Probléme de lecture du sudoku.");
        }
    }
}
