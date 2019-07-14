package pt.isel.poo.li21n.g1.sokoban.model.cell;

import java.io.PrintWriter;

import pt.isel.poo.li21n.g1.sokoban.model.Actor;
import pt.isel.poo.li21n.g1.sokoban.model.Cell;
import pt.isel.poo.li21n.g1.sokoban.model.CellType;

public class ObjectiveCell extends Cell {
    private boolean objective;

    public ObjectiveCell(CellType t) {
        super(t);
        this.objective = false;
    }

    @Override
    public void updateCell(Actor a) {
        this.actor = a;
        if (a == Actor.BOX)
            this.objective = true;
    }

    @Override
    public void removeActor() {
        this.actor = Actor.EMPTY;
    }

    @Override
    public boolean isBoxInObjective() {
        return this.objective;
    }

    @Override
    public void save(PrintWriter pw) {
        if (actor == Actor.MAN) {
            pw.print('a');
        } else if (actor == Actor.BOX) {
            pw.print('b');
        } else {
            type.save(pw);
        }
    }
}
