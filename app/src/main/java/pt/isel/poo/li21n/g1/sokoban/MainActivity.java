package pt.isel.poo.li21n.g1.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pt.isel.poo.li21n.g1.sokoban.controller.SokobanController;
import pt.isel.poo.li21n.g1.sokoban.model.Game;
import pt.isel.poo.li21n.g1.sokoban.view.SokobanView;

public class MainActivity extends AppCompatActivity {
    private Game model;
    private SokobanView view;
    private SokobanController ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new Game();
        view = new SokobanView(this);
        ctrl = new SokobanController(this, model, view);

        ctrl.run();

        if (savedInstanceState != null) {
            ctrl.loadCurrentState(savedInstanceState.getInt("LEVEL"),
                                  savedInstanceState.getInt("MOVES"),
                                  savedInstanceState.getInt("BOXES"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("LEVEL", ctrl.getCurrentLevel());
        outState.putInt("MOVES", ctrl.getCurrentMoves());
        outState.putInt("BOXES", ctrl.getCurrentBoxes());
        ctrl.saveCurrentState();
    }
}
