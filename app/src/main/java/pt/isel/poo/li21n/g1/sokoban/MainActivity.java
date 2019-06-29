package pt.isel.poo.li21n.g1.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pt.isel.poo.li21n.g1.sokoban.controller.SokobanController;
import pt.isel.poo.li21n.g1.sokoban.model.Game;
import pt.isel.poo.li21n.g1.sokoban.view.SokobanView;

public class MainActivity extends AppCompatActivity {
    private Game model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new Game();
        SokobanView view = new SokobanView(this);
        SokobanController ctrl = new SokobanController(this, model, view);

        ctrl.run();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
