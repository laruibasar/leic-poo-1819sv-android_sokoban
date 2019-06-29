package pt.isel.poo.li21n.g1.sokoban.view;

import android.graphics.Canvas;
import android.graphics.Color;

import pt.isel.poo.li21n.g1.sokoban.model.Actor;
import pt.isel.poo.li21n.g1.sokoban.model.Cell;

public class FloorTile extends CellTile {
    public FloorTile(Cell cell) {
        super(cell);

        Actor actor = cell.getActor();
        if (actor == Actor.MAN) {
            img = SokobanView.getImageMan();
        } else if (actor == Actor.BOX) {
            img = SokobanView.getImageBox();
        } else {
            paint.setColor(Color.LTGRAY);
        }
    }

    @Override
    public void draw(Canvas canvas, int side) {
        Actor actor = cell.getActor();
        if (actor != Actor.EMPTY) {
            img.draw(canvas, side, side, paint);
        } else {
            canvas.drawRect(0f, 0f, side, side, paint);
        }
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}