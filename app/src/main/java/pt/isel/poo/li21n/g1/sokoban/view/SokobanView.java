package pt.isel.poo.li21n.g1.sokoban.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pt.isel.poo.li21n.g1.sokoban.R;
import pt.isel.poo.li21n.g1.sokoban.model.Dir;
import pt.isel.poo.tile.Img;
import pt.isel.poo.tile.OnTileTouchListener;
import pt.isel.poo.tile.Tile;
import pt.isel.poo.tile.TilePanel;

public class SokobanView extends View {
    private Context context;

    private final Paint paint = new Paint();

    private final StatusView status;
    private final TilePanel panel;
    private final Button btnRestart;
    private final TextView message;
    private final Button btnOk;

    // we have context in here so we load images, future move to CellTile (?)
    protected static Img imgMan;
    protected static Img imgBox;
    protected static Img imgWater;
    protected static Img imgWall;

    private SokobanViewListener viewListener = null;

    public SokobanView(Activity activity) {
        super(activity);
        this.context = activity;

        status = activity.findViewById(R.id.status);
        panel = activity.findViewById(R.id.panel);
        btnRestart = activity.findViewById(R.id.btnRestart);
        btnRestart.setEnabled(false);
        message = activity.findViewById(R.id.message);
        btnOk = activity.findViewById(R.id.btnOk);

        panel.setListener(new OnTileTouchListener() {
            @Override
            public boolean onClick(int xTile, int yTile) {
                return false;
            }

            @Override
            public boolean onDrag(int xFrom, int yFrom, int xTo, int yTo) {
                int dx = xTo - xFrom; // regarding model, moving between columns
                int dy = yTo - yFrom; // regarding model, moving between lines

                Dir move = null;

                if (dx < 0) {
                    move = Dir.LEFT;
                } else if (dx > 0) {
                    move = Dir.RIGHT;
                }

                if (dy < 0) {
                    move = Dir.UP;
                } else if (dy > 0) {
                    move = Dir.DOWN;
                }

                if (move == null) return false;
                moveMan(move);

                invalidate();
                return false;
            }

            @Override
            public void onDragEnd(int x, int y) {

            }

            @Override
            public void onDragCancel() {

            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartLevel();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMessageAnswer();
            }
        });


        // we have context in here so we load images, future move to CellTile (?)
        imgMan = new Img(activity, R.drawable.man);
        imgBox = new Img(activity, R.drawable.box);
        imgWater = new Img(activity, R.drawable.water);
        imgWall = new Img(activity, R.drawable.wall);
    }

    public static Img getImageBox() { return imgBox; }

    public static Img getImageWater() { return imgWater; }

    public static Img getImageWall() { return imgWall; }

    public static Img getImageMan() { return imgMan; }

    public void setViewListener(SokobanViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void setPanelSize(int width, int height) {
        panel.setSize(width, height);
    }

    public void updateStatusLevel(int level) {
        status.setLevel(level);
        invalidate();
    }

    public void updateStatusMoves(int moves) {
        status.setMoves(moves);
        invalidate();
    }

    public void updateStatusBoxes(int boxes) {
        status.setBoxes(boxes);
        invalidate();
    }

    public Tile getTile(int x, int y) {
        return panel.getTile(x, y);
    }

    public void setTile(int x, int y, Tile t) {
        panel.setTile(x, y, t);
        invalidate();
    }

    public void useRestart(boolean use) {
        btnRestart.setEnabled(use);
    }

    private void restartLevel() {
        if (viewListener != null) {
            viewListener.onRestartLevel();
        }
    }

    private void moveMan(Dir dir) {
        if (viewListener != null) {
            viewListener.onMoveMan(dir);
        }
    }

    public void showGameMessage(boolean level) {
        btnRestart.setVisibility(View.GONE);
        if (level) {
            message.setText(context.getResources().getString(R.string.level_win));
        } else {
            message.setText(context.getResources().getString(R.string.level_loose));
        }
        message.setVisibility(View.VISIBLE);
        btnOk.setVisibility(View.VISIBLE);
    }

    private void gameMessageAnswer() {
        if (viewListener != null) {
            viewListener.onGameMessage();
        }
        btnRestart.setVisibility(View.VISIBLE);
        message.setVisibility(View.GONE);
        btnOk.setVisibility(View.GONE);
    }
}
