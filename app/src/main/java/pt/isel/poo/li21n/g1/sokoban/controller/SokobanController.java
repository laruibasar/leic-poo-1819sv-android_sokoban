package pt.isel.poo.li21n.g1.sokoban.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

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
    private final SokobanView view;

    private Game model;
    private Level level;

    private boolean levelStarted = false;

    public SokobanController(Activity activity, Game model, SokobanView view) {
        this.context = activity;
        this.model = model;
        this.view = view;
    }

    public void run() {
        view.setViewListener(this);

        try {
            model.setGame(context.getResources().openRawResource(R.raw.levels));

            level = model.loadNextLevel();
            level.setObserver(this);
            view.setPanelSize(level.getWidth(), level.getHeight());


            playLevel();
        } catch (Loader.LevelFormatException e) {
            Log.e("Loader", "Read file: " + e + " Line " + e.getLineNumber() + " " + e.getLine());
        }
    }

    private boolean playLevel() {
        updateStatus();
        loadView();
        view.useRestart(levelStarted);

        //while (!level.isFinished())

        return true;
    }

    private void play(Dir dir) {
        levelStarted = (level.getMoves() == 0) ? false : true;
        view.useRestart(levelStarted);

        level.moveMan(dir);
        view.updateStatusMoves(level.getMoves());
        view.updateStatusBoxes(level.getRemainingBoxes());
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
        levelStarted = false;
        view.useRestart(levelStarted);
        model.restart();
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
