package pt.isel.poo.li21n.g1.sokoban.model.cell;

import java.io.PrintWriter;

import pt.isel.poo.li21n.g1.sokoban.model.Actor;
import pt.isel.poo.li21n.g1.sokoban.model.Cell;
import pt.isel.poo.li21n.g1.sokoban.model.CellType;

public class EmptyCell extends Cell {
    public EmptyCell(CellType t) {
        super(t);
    }

    @Override
    public void updateCell(Actor a) {
        this.actor = a;
    }

    @Override
    public void removeActor() {
        this.actor = Actor.EMPTY;
    }

    @Override
    public boolean canEnter() {
        // because we are over the wall, nope
        return false;
    }

    @Override
    public void save(PrintWriter pw) {
        type.save(pw);
    }
}
