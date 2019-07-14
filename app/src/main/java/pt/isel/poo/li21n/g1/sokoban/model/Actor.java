package pt.isel.poo.li21n.g1.sokoban.model;

import java.io.PrintWriter;

/**
 * Enum Actor
 *
 * This enum represent all game actors than can be part of a Cell
 * (the use of class is harder)
 */
public enum Actor {
    EMPTY   (' '),
    BOX     ('B'),
    MAN     ('@');

    private final char a;
    Actor(char c) {
        a = c;
    }

    public void save(PrintWriter pw) {
        pw.print(a);
    }
}