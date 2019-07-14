package pt.isel.poo.li21n.g1.sokoban.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Game {
    private InputStream input;
    private int levelNumber = 0;
    private Level curLevel = null;

    // --- Methods to be use by Controller ---

    public Game() {

    }

    public Game(InputStream levelsFile) {
        input = levelsFile.markSupported() ? levelsFile : new BufferedInputStream(levelsFile);
        input.mark(40*1024);
    }

    public void setGame(InputStream levelsFile) {
        input = levelsFile.markSupported() ? levelsFile : new BufferedInputStream(levelsFile);
        input.mark(40*1024);
    }

    public Level loadNextLevel() throws Loader.LevelFormatException {
        curLevel = new Loader(createScanner()).load(++levelNumber);
        if (curLevel!=null)
            curLevel.init(this);
        return curLevel;
    }

    public Level loadLevel(int level) throws Loader.LevelFormatException {
        levelNumber = level - 1;
        return loadNextLevel();
    }

    public void restart() {
        new Loader(createScanner()).reload(curLevel);
        curLevel.init(this);
    }

    private Scanner createScanner() {
        try {
            input.reset();
            return new Scanner(input);
        } catch (IOException e) {
            throw new RuntimeException("IOException",e);
        }
    }
}
