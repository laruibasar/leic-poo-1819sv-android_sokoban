package pt.isel.poo.li21n.g1.sokoban.model;

import java.io.PrintWriter;

/**
 * Enum CellType
 *
 * This enum represent all game cells types than make a Cell
 */
public enum CellType {
    EMPTY       ('.'),
    FLOOR       (' '),
    HOLE        ('H'),
    OBJECTIVE   ('*'),
    WALL        ('X');

    private final char c;

    CellType(char c) {
        this.c = c;
    }

    public void save(PrintWriter pw) {
        pw.print(c);
    }
}