package pt.isel.poo.li21n.g1.sokoban.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import pt.isel.poo.li21n.g1.sokoban.model.Cell;
import pt.isel.poo.li21n.g1.sokoban.model.CellType;
import pt.isel.poo.tile.Img;
import pt.isel.poo.tile.Tile;

public class CellTile implements Tile {
    protected Cell cell;
    protected Paint paint = new Paint();
    protected Img img;

    public CellTile() {
        this.cell = null;
    }

    public CellTile(Cell cell) {
        this.cell = cell;
    }

    public static Tile tileOf(Cell cell) {
        CellType type = cell.getType();

        switch (type) {
                case FLOOR:
                    return new FloorTile(cell);
                case OBJECTIVE:
                    return new ObjectiveTile(cell);
                case WALL:
                    return new WallTile(cell);
                case HOLE:
                    return new HoleTile(cell);
                case EMPTY:
                default:
                    return new EmptyTile(cell);
        }
    }

    public void updateCellTile(Cell cell) {
        this.cell = cell;
    }

    @Override
    public void draw(Canvas canvas, int side) {

    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
