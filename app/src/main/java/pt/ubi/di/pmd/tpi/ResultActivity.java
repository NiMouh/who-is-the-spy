package pt.ubi.di.pmd.tpi;

import static pt.ubi.di.pmd.tpi.GameActivity.remainingPlayers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    // Declaration of variables
    TextView win_role;
    TextView win_names;
    Button new_game;
    Button exit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialization of variables
        win_role = findViewById(R.id.role_result);
        win_names = findViewById(R.id.players_result);
        new_game = findViewById(R.id.new_game_btn);
        exit = findViewById(R.id.home_btn);

        // Set the winner role to the first role in the ArrayList of the remaining players
        win_role.setText(remainingPlayers.get(0).getRole());

        // Set the winner names to the names of all the remaining players
        for (int i = 0; i < remainingPlayers.size(); i++) {
            win_names.setText(win_names.getText() + remainingPlayers.get(i).getName() + "\n");
        }


        // If the new game button is clicked, it will start a new game
        new_game.setOnClickListener(v -> {
            // Start a new game
        });

        // If the exit button is clicked, it will go to the main menu
        exit.setOnClickListener(v -> {
            // Go to the main menu
        });
    }
}
