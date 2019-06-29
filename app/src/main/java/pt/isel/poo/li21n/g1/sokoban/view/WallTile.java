package pt.isel.poo.li21n.g1.sokoban.view;

import android.graphics.Canvas;
import android.graphics.Color;

import pt.isel.poo.li21n.g1.sokoban.model.Cell;

public class WallTile extends CellTile {
    public WallTile(Cell cell) {
        super(cell);
        img = SokobanView.getImageWall();
    }

    @Override
    public void draw(Canvas canvas, int side) {
        img.draw(canvas, side, side, paint);
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
