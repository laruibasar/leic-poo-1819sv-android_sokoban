package pt.isel.poo.li21n.g1.sokoban.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import pt.isel.poo.li21n.g1.sokoban.R;
import pt.isel.poo.li21n.g1.sokoban.model.Cell;
import pt.isel.poo.li21n.g1.sokoban.model.Dir;
import pt.isel.poo.li21n.g1.sokoban.model.Game;
import pt.isel.poo.li21n.g1.sokoban.model.Level;
import pt.isel.poo.li21n.g1.sokoban.model.Loader;
import pt.isel.poo.li21n.g1.sokoban.view.CellTile;
import pt.isel.poo.li21n.g1.sokoban.view.SokobanView;
import pt.isel.poo.li21n.g1.sokoban.view.SokobanViewListener;

public class SokobanController implements SokobanViewListener, Level.Observer {
    private Context context;
    private Activity activity;
    private final SokobanView view;

    private static final String SAVE = "saved_level.txt";

    private Game model;
    private Level level;

    private boolean levelStarted = false;
    private boolean levelWin;

    public SokobanController(Activity activity, Game model, SokobanView view) {
        this.context = activity;
        this.activity = activity;
        this.model = model;
        this.view = view;
    }

    public void run() {
        view.setViewListener(this);
        model.setGame(context.getResources().openRawResource(R.raw.levels));
        // not like console game
        playLevel();
    }

    public void saveCurrentState() {
        try {
            FileOutputStream outputStream = context.openFileOutput(SAVE, Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(outputStream);
            level.save(pw);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadCurrentState(int level, int moves) {
        Log.d("State level", Integer.toString(level));
        Scanner sc = new Scanner(SAVE);
        //model.load(sc);
    }

    public int getCurrentLevel() {
        return level.getNumber();
    }

    public int getCurrentMoves() {
        return level.getMoves();
    }


    private void playLevel() {
        levelWin = false;
        try {
            level = model.loadNextLevel();

            level.setObserver(this);
            view.setPanelSize(level.getWidth(), level.getHeight());

            updateStatus();
            loadView();
            view.useRestart(levelStarted);
        } catch (Loader.LevelFormatException e) {
            Log.e("Loader", "Read file: " + e + " Line " + e.getLineNumber() + " " + e.getLine());
        }
    }

    private void play(Dir dir) {
        if (level.isFinished())
            return;

        level.moveMan(dir);

        levelStarted = (level.getMoves() == 0) ? false : true;
        view.useRestart(levelStarted);
        view.updateStatusMoves(level.getMoves());
        view.updateStatusBoxes(level.getRemainingBoxes());

        // check level status
        if (checkLevelFinished()) {
            if (checkManDeath()) {
                view.showGameMessage(levelWin = false);
            } else {
                view.showGameMessage(levelWin = true);
            }
        }
    }

    private boolean checkLevelFinished() {
        return level.isFinished();
    }

    private boolean checkManDeath() {
        return level.manIsDead();
    }

    private void updateStatus() {
        view.updateStatusLevel(level.getNumber());
        view.updateStatusMoves(level.getMoves());
        view.updateStatusBoxes(level.getRemainingBoxes());
    }

    private void loadView() {
        int height = level.getHeight();
        int width = level.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                view.setTile(x, y, CellTile.tileOf( level.getCell(y, x) )); // NOTE: x => c ; y => l
            }
        }
    }

    @Override
    public void onRestartLevel() {
        view.useRestart(levelStarted = false);
        model.restart();
        updateStatus();
        loadView();
    }

    @Override
    public void onGameMessage() {
        if (levelWin) {
            levelStarted = false;
            playLevel();
        } else {
            activity.finish(); /* as per requested on apk for demonstration */
        }
    }

    @Override
    public void onMoveMan(Dir dir) {
        play(dir);
    }

    @Override
    public void cellUpdated(int l, int c, Cell cell) {
        // on view x => c ; y => l
        view.getTile(c, l);
    }

    @Override
    public void cellReplaced(int l, int c, Cell cell) {
        // on view x => c ; y => l
        view.setTile(c, l, CellTile.tileOf(cell));
    }
}
