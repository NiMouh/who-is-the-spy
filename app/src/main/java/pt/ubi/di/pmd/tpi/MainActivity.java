package pt.ubi.di.pmd.tpi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declaration of the buttons
    Button add_player;
    Button remove_player;
    Button play_game;

    // Declaration of the TextViews
    TextView player_name;
    TextView list_players;

    // Declaration of the max number of players and min number of players
    int max_players = 12;
    int min_players = 5;

    // Make an array list of players static, so it can be accessed from other activities
    public static ArrayList<Player> players = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization of variables
        add_player = findViewById(R.id.add_player_btn);
        remove_player = findViewById(R.id.remove_player_btn);
        play_game = findViewById(R.id.start_game_btn);

        // Declaration of the TextView with the players names
        player_name = findViewById(R.id.player1);
        list_players = findViewById(R.id.lista_jogadores);

        // If the player is added, add the name to the arrayList and add it to the TextView
        add_player.setOnClickListener(v -> {
            // If the TextView is empty pop up an error
            if (player_name.getText().toString().isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "O nome encontra-se vazio", Snackbar.LENGTH_LONG).show();
            } else {
                // Run through every player on ArrayList, and If the name is already in the ArrayList of players pop up an error
                for (Player player : players) {
                    if (player.getName().equals(player_name.getText().toString())) {
                        Snackbar.make(findViewById(android.R.id.content), "Jogador já existe", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                }
                // Add the player to the ArrayList
                players.add(new Player(player_name.getText().toString()));
                // Add the player to the TextView
                list_players.setText(list_players.getText() + "\n" + player_name.getText());
                // Clean the EditText
                player_name.setText("");
            }
        });

        // If the remove player button is clicked, it will remove the most recent player added
        remove_player.setOnClickListener(v -> {
            // If the ArrayList is empty pop up an error
            if (!players.isEmpty()) {
                // Remove the last player added to the ArrayList
                players.remove(players.size() - 1);
                // Remove the last player added to the TextView
                list_players.setText(list_players.getText().toString().substring(0, list_players.getText().toString().lastIndexOf("\n")));
            }
        });

        // If the Start Game Button is pressed, start the GameActivity
        play_game.setOnClickListener(v -> {
            // If there are less than 5 player and more than 10 players pop up an error
            if (players.size() < min_players || players.size() > max_players) {
                Snackbar.make(findViewById(android.R.id.content), "Número de jogadores inválido", Snackbar.LENGTH_LONG).show();
            } else {
                // If the number of players is valid, start the GameActivity
                Intent intent = new Intent(this, QueueActivity.class);
                startActivity(intent);
            }
        });
    }
}