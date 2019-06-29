package pt.isel.poo.li21n.g1.sokoban.view;

import android.graphics.Canvas;
import android.graphics.Color;

import pt.isel.poo.li21n.g1.sokoban.model.Cell;

public class HoleTile extends CellTile {
    public HoleTile(Cell cell) {
        super(cell);
        img = SokobanView.getImageWater();
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
