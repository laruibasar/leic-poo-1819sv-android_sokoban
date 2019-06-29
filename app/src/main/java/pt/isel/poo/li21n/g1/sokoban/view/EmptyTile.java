package pt.isel.poo.li21n.g1.sokoban.view;

import android.graphics.Canvas;
import android.graphics.Color;

import pt.isel.poo.li21n.g1.sokoban.model.Cell;

public class EmptyTile extends CellTile {
    public EmptyTile(Cell cell) {
        super(cell);
        paint.setColor(Color.BLACK);
    }

    @Override
    public void draw(Canvas canvas, int side) {
        canvas.drawRect(0f, 0f, side, side, paint);
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
