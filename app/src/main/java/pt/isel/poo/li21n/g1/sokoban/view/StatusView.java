package pt.isel.poo.li21n.g1.sokoban.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import pt.isel.poo.li21n.g1.sokoban.R;

public class StatusView extends LinearLayout {
    private FieldView fvLevel;
    private FieldView fvMoves;
    private FieldView fvBoxes;

    public StatusView(Context context) {
        super(context);
        init(context);
    }

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater li = LayoutInflater.from(context);
        li.inflate(R.layout.status_view, this);

        fvLevel = findViewById(R.id.level);
        fvMoves = findViewById(R.id.moves);
        fvBoxes = findViewById(R.id.boxes);
    }

    public void setLevel(int level) {
        fvLevel.setValue(level);
    }

    public void setMoves(int moves) {
        fvMoves.setValue(moves);
    }

    public void setBoxes(int boxes) {
        fvBoxes.setValue(boxes);
    }
}
