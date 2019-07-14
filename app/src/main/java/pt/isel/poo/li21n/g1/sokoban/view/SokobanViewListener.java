package pt.isel.poo.li21n.g1.sokoban.view;

import pt.isel.poo.li21n.g1.sokoban.model.Dir;

public interface SokobanViewListener {
    void onRestartLevel();
    void onMoveMan(Dir dir);
    void onGameMessage();
}
