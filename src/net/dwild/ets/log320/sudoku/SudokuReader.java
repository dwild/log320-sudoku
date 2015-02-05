package net.dwild.ets.log320.sudoku;

import java.io.IOException;
import java.io.InputStream;

public class SudokuReader {

    private InputStream inputStream;

    public SudokuReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Sudoku readSudoku() throws IOException {
        Sudoku sudoku = new Sudoku();

        for(int i = 0, c = 0; (c = inputStream.read()) > -1 && i < 81;) {
            if(c >= 48 && c <= 57) {
                sudoku.setNumber(i % 9, i/9, c - 48);
                i++;
            }
        }

        return sudoku;
    }

    public void close() throws IOException {
        this.inputStream.close();
    }
}
