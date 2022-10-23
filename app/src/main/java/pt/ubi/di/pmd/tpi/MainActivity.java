package pt.ubi.di.pmd.tpi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declaration of the variables
    Button start;
    Button exit;
    Button add_player;
    Button play_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization of variables
        start = findViewById(R.id.play_btn);
        exit = findViewById(R.id.exit_btn);
        add_player = findViewById(R.id.add_player_btn);
        play_game = findViewById(R.id.start_game_btn);

        // If Start Game Button is pressed, show the add player button, the recycler view and the
        // start game button and hide the play button and the exit button
        start.setOnClickListener(v -> {
            add_player.setVisibility(Button.VISIBLE);
            play_game.setVisibility(Button.VISIBLE);
            start.setVisibility(Button.INVISIBLE);
            exit.setVisibility(Button.INVISIBLE);
        });

        // If the Exit Button is pressed, close the app
        exit.setOnClickListener(v -> finish());

        // Manage the recycler view layout and his text fields and buttons

        // Add TextFields to the recycler view (5 players max)
        // If the Add Player Button is pressed, add a new text field to the recycler view

    }
}