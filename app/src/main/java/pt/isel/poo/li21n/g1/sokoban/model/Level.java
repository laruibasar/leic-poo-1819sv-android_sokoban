package pt.isel.poo.li21n.g1.sokoban.model;

import java.io.PrintWriter;
import java.util.Scanner;

import pt.isel.poo.li21n.g1.sokoban.model.cell.EmptyCell;
import pt.isel.poo.li21n.g1.sokoban.model.cell.FloorCell;
import pt.isel.poo.li21n.g1.sokoban.model.cell.HoleCell;
import pt.isel.poo.li21n.g1.sokoban.model.cell.ObjectiveCell;
import pt.isel.poo.li21n.g1.sokoban.model.cell.WallCell;

public class Level {

    private int levelNumber;
    private int height;
    private int width;

    private int remainingBoxes;
    private int moves;

    /**
     * The numObjectives and numBoxes is for check for losing conditions,
     * when we have less boxes than objectives
     */
    private int numObjectives;
    private int numBoxes;

    private boolean manInHole;
    private boolean boxInHole;

    private Actor man = Actor.MAN;
    private int manLine;
    private int manColumn;

    /**
     * The game area, full of cells, is a bi-dimensional array
     * of size to be define at each level
     */
    private Cell[][] cellboard;

    private Observer listener;

    /**
     * Class constructor, setting up the parameters needed to start the show
     *
     * @param levelNumber the level number for the game
     * @param height the number of lines
     * @param width the number of columns
     */
    public Level (int levelNumber, int height, int width) {
        this.levelNumber = levelNumber;
        this.height = height;
        this.width = width;
        this.cellboard = new Cell[height][width];
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isFinished() {
        return (this.remainingBoxes == 0) || manInHole || boxInHole;
    }

    public boolean manIsDead() {
        // we also die from stupidity: don't put to many boxes in holes
        return this.manInHole || this.boxInHole;
    }

    public int getNumber() {
        return this.levelNumber;
    }

    public int getRemainingBoxes() {
        return remainingBoxes;
    }
    public void setRemainingBoxes(int boxes) { remainingBoxes = boxes; }

    public int getMoves() {
        return moves;
    }
    public void setMoves(int moves) { this.moves = moves; }

    /**
     * Returns the Cell at line and column asked
     * @param line the line in the game area
     * @param column the column in the game area
     * @return the Cell in the l x c
     */
    public Cell getCell(int line, int column) {
        return cellboard[line][column];
    }

    /**
     * Method called for the movement of the man in the game area
     * @param dir the movement direction from the user
     */
    public void moveMan(Dir dir) {
        int newLine = manLine;
        int newColumn = manColumn;

        switch (dir) {
            case UP:
                if (--newLine < 0)
                    return;
                break;
            case DOWN:
                if (++newLine > (height - 1))
                    return;
                break;
            case LEFT:
                if (--newColumn < 0)
                    return;
                break;
            case RIGHT:
                if (++newColumn > (width - 1))
                    return;
                break;
        }

        // moves the man and save the new position
        if (moveManInCell(newLine, newColumn)) {
            manLine = newLine;
            manColumn = newColumn;

            moves++;
        } else {
            return;
        }

        updateRemainingBoxes();
    }

    /**
     * Method to make the man and boxes move in the cells
     * @param newLine the new line in the board to move to move into
     * @param newColumn the new column in the board to move into
     * @return success of the move
     */
    private boolean moveManInCell(int newLine, int newColumn) {
        Cell current = cellboard[manLine][manColumn];
        Cell next = cellboard[newLine][newColumn];

        if (next.canEnter()) {
            next.updateCell(man);
            listener.cellReplaced(newLine, newColumn, next);

            current.removeActor();
            listener.cellReplaced(manLine, manColumn, current);
        } else if (next.getActor() == Actor.BOX) {
            int fwdLine = newLine + (newLine - manLine);
            int fwdColumn = newColumn + (newColumn - manColumn);
            Cell fwd = cellboard[fwdLine][fwdColumn];

            if (fwd.canEnter()) {
                // lookout: if we put the box in the hole we lose it
                if (fwd.getType() == CellType.HOLE && next.getActor() == Actor.BOX) {
                    numBoxes--;

                    // we loose if we can't end
                    if (numBoxes < numObjectives)
                        boxInHole = true;

                    cellboard[fwdLine][fwdColumn] = new FloorCell(CellType.FLOOR);
                    listener.cellReplaced(fwdLine, fwdColumn, fwd);
                }

                fwd.updateCell(next.getActor());
                listener.cellReplaced(fwdLine, fwdColumn, fwd);

                next.removeActor();
                next.updateCell(man);
                listener.cellReplaced(newLine, newColumn, next);

                current.removeActor();
                listener.cellReplaced(manLine, manColumn, current);
            } else {
                return false;
            }
        } else {
            // let's head back because we really can't move
            return false;
        }

        // if something good ended and we go down the rabbit hole
        if (next.getType() == CellType.HOLE && next.getActor() == Actor.MAN) {
            manInHole = true;
        }

        return true;
    }


    /**
     * Do a full sweep to check for boxes in objectives and updates
     * the remaining boxes in the level
     */
    private void updateRemainingBoxes() {
        Cell c;
        int bc = 0;

        // count all boxes
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                c = cellboard[i][j];

                if (c.getActor() == Actor.BOX)
                    bc++;
            }
        }

        // count all boxes in ObjectiveCells
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                c = cellboard[i][j];

                if (c.getType() == CellType.OBJECTIVE) {
                    if (c.getActor() == Actor.BOX)
                        bc--;
                }
            }
        }
        remainingBoxes = bc;
    }

    /**
     * Reset the level stats et all, meaning the moves are set to zero,
     * cells, etc
     */
    public void reset() {
        moves = 0;
        /* remainingBoxes = 0; */ //found bug maybe affecting console app
        boxInHole = false; /* bug squash */
        manInHole = false; /* bug squash */
        cellboard = new Cell[height][width];
    }

    /**
     * Method to insert into the game area the type of cell depending of the
     * char read from file
     *
     * @param line the line in the game area
     * @param column the column in the game area
     * @param type the type of actor
     */
    public void put(int line, int column, char type) {
        // lock man position
        if (type == '@') {
            manLine = line;
            manColumn = column;
        }

        // count the number of boxes in the level
        if (type == 'B') {
            remainingBoxes++;
            numBoxes++;
        }

        // count the objectives to account for lost boxes
        if (type == '*')
            numObjectives++;

        if (cellboard[line][column] == null) {
            cellboard[line][column] = createCell(type);
        } else {
            Actor a = createActor(type);
            cellboard[line][column].updateCell(a);
            updateBoxes(cellboard[line][column]);
        }

    }

    /**
     * We verified if we have a Objective Cell with a Box
     */
    private void updateBoxes(Cell c) {
        if (c.getType() == CellType.OBJECTIVE && c.isBoxInObjective()) {
            remainingBoxes--;
        } else {
            remainingBoxes++;
        }
    }

    /**
     * Set a new instance of Actor depending of type.
     * We need to think careful if we have a special Cell that can have more
     * than one Actor, like HoleCell, ObjectiveCell, FloorCell,
     *
     * @param type symbol for the actor type
     * @return enum of the Type of Actor
     */
    private Actor createActor(char type) {
        switch (type) {
            case '@':
                return man;
            case 'B':
                return Actor.BOX;
            case '.':
            default:
                return Actor.EMPTY;
        }
    }

    /**
     * Create the cell depending of the type of actor.
     * @param ch the char read from file
     * @return the cell type
     */
    private Cell createCell(char ch) {
        Cell c = new FloorCell(CellType.FLOOR);

        switch (ch) {
            case '@':
                c.updateCell(man);
                return c;
            case 'B':
                c.updateCell(Actor.BOX);
                return c;
            case '.':
                return new EmptyCell(CellType.EMPTY);
            case ' ':
                return c;
            case 'X':
                return new WallCell(CellType.WALL);
            case '*':
                return new ObjectiveCell(CellType.OBJECTIVE);
            case 'H':
                return new HoleCell(CellType.HOLE);
            default:
                return null;
        }
    }

    public void init(Game game) {
        this.moves = 0;

        this.numBoxes = 0;
        this.numObjectives = 0;

        this.manInHole = false;
        this.boxInHole = false;
        updateRemainingBoxes(); //found bug maybe affecting console app in condition of restart level
    }

    public void setObserver(Observer listener) {
        this.listener = listener;
    }

    public interface Observer {

        public void cellUpdated(int l, int c, Cell cell);

        public void cellReplaced(int l, int c, Cell cell);

    }

    public void save(PrintWriter pw) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cellboard[i][j].save(pw);
            }
            pw.println();
        }
    }

    public void load(Scanner sc) {
        for (int i = 0; i < width; i++) {
            String str = sc.nextLine();
            for (int j = 0; j < height; j++) {
                char c = str.charAt(j);
                if (c == 'a') {
                    put(i, j, '*');
                    put(i, j, '@');
                } else if (c == 'b') {
                    put(i, j, '*');
                    put(i, j, 'B');
                } else {
                    put(i, j, c);
                }
            }
        }
    }

}
