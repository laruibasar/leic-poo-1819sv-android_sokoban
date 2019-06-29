package pt.isel.poo.li21n.g1.sokoban.model.cell;

import pt.isel.poo.li21n.g1.sokoban.model.Actor;
import pt.isel.poo.li21n.g1.sokoban.model.Cell;
import pt.isel.poo.li21n.g1.sokoban.model.CellType;

public class WallCell extends Cell {
    public WallCell(CellType t) {
        super(t);
    }

    @Override
    public void updateCell(Actor a) { }

    @Override
    public void removeActor() { }

    @Override
    public boolean canEnter() {
        // because we are a wall, we say nay
        return false;
    }
}
