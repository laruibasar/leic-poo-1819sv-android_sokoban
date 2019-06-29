package pt.isel.poo.li21n.g1.sokoban.model;

/**
 * Class Cell
 *
 * This class represents the cell used in creating the game area.
 * This class allows for for subclasses to extends this and get different
 * behaviours according with the actors expected to host.
 */
public class Cell {

    /**
     * Define the base type of cell host
     */
    protected CellType type;

    /**
     * There are cells types that should allow to host actors.
     * E.g. the floor cells should allow to have the Man or the Box to go
     * through them.
     */
    protected Actor actor;

    public Cell() {
        this.actor = Actor.EMPTY;
    }

    /**
     * Public constructor
     * @param t represent the type of cell.
     */
    public Cell(CellType t) {
        this.type = t;
        this.actor = Actor.EMPTY;
    }

    /**
     * Method to identified the type of cell in game. The type is relative to
     * the main actor of the cell
     * @return the type of the cell
     */
    public CellType getType() {
        return this.type;
    }

    /**
     * We need a more talkative class, so this method
     * return the actor using the cell
     */
    public Actor getActor() {
        return this.actor;
    }

    /**
     * This update the actor placed in the cell
     * @param a the actor to be placed on the cell
     */
    public void updateCell(Actor a) { }

    /**
     * This remove the actor placed in the cell
     */
    public void removeActor() { }

    /**
     * And this say to anyone who cares if the cell allows changing type.
     * If allows someone to enter the cell
     */
    public boolean canEnter() {
        return (this.actor == Actor.EMPTY);
    }

    /**
     * This answers the question: am i fulfilled?
     * Only overrides in ObjectiveCell
     */
    public boolean isBoxInObjective() {
        return false;
    }
}
