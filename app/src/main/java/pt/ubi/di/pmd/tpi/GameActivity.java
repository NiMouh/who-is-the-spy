package pt.ubi.di.pmd.tpi;

import static pt.ubi.di.pmd.tpi.MainActivity.players;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    // Declaration of variables
    Button next_player;
    Button guess_btn;

    // Declare an ArrayList with the remaining players
    private ArrayList<Player> remainingPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize the ArrayList
        remainingPlayers = new ArrayList<>(players);

        // Initialize the buttons
        next_player = findViewById(R.id.next_player);
        guess_btn = findViewById(R.id.guess_btn);

        // Declare an ArrayList with the round players and put them all the players in it
        ArrayList<Player> roundPlayers = new ArrayList<>(players);

        // The game starts
        // It will choose the first player randomly in the roundPlayers ArrayList
        // And remove him from the ArrayList
        // If the next button is pressed, it will choose the next player randomly in the roundPlayers ArrayList and remove him from the ArrayList roundPlayers
        // If the roundPlayers ArrayList is empty, it will show a reunion room
        next_player.setOnClickListener(v -> {
            // If the roundPlayers ArrayList is empty, it will show a reunion room
            if (roundPlayers.isEmpty()) {
                // Show the reunion room
            } else {
                // Show his name on the screen
                // Choose the next player randomly in the roundPlayers ArrayList
                // And remove him from the ArrayList
            }
        });

        // If the guess button is clicked then it will show the options of the locations for the player to guess
        guess_btn.setOnClickListener(v -> {
            // Show the options of the locations for the player to guess
            // If the player guesses correctly, the game ends and it will show the result
            // If the player guesses incorrectly, it will expel him from the game
        });


    }

}
