package pt.ubi.di.pmd.tpi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declaration of the variables
    Button start;
    Button exit;
    Button add_player;
    Button play_game;
    TextView player_name;
    TextView list_players;

    // Make an array list of players static so it can be accessed from other activities
    public static ArrayList<Player> players = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization of variables
        start = findViewById(R.id.play_btn);
        exit = findViewById(R.id.exit_btn);
        add_player = findViewById(R.id.add_player_btn);
        play_game = findViewById(R.id.start_game_btn);

        // Declaration of the EditText
        player_name = findViewById(R.id.player1);

        // Declaration of the TextView with the players names
        list_players = findViewById(R.id.lista_jogadores);

        // If Start Game Button is pressed, show the add player button, the EditTexts and the
        // start game button and hide the play button and the exit button
        start.setOnClickListener(v -> {
            start.setVisibility(Button.INVISIBLE);
            exit.setVisibility(Button.INVISIBLE);
            player_name.setVisibility(android.view.View.VISIBLE);
            list_players.setVisibility(android.view.View.VISIBLE);
            add_player.setVisibility(Button.VISIBLE);
            play_game.setVisibility(Button.VISIBLE);
        });

        // If the Exit Button is pressed, close the app
        exit.setOnClickListener(v -> finish());

        // If the player is added, add the name to the arrayList and add it to the TextView
        add_player.setOnClickListener(v -> {
            // If the TextView is empty pop up an error
            if (player_name.getText().toString().isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Nome Vazio", Snackbar.LENGTH_LONG).show();
            } else {
                // Run through every player on ArrayList, and If the the name is already in the ArrayList of players pop up an error
                for (Player player : players) {
                    if (player.getName().equals(player_name.getText().toString())) {
                        Snackbar.make(findViewById(android.R.id.content), "Jogador já existe", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                }
                // Add the player to the ArrayList
                players.add(new Player(player_name.getText().toString()));
                // Add the player to the TextView
                list_players.setText(list_players.getText() + "\n" + player_name.getText().toString());
                // Clean the EditText
                player_name.setText("");
            }
        });

        // If the Start Game Button is pressed, start the GameActivity
        play_game.setOnClickListener(v -> {
            // If there are less than 5 player and more than 10 players pop up an error
            if (players.size() < 5 || players.size() > 10) {
                Snackbar.make(findViewById(android.R.id.content), "Número de jogadores inválido", Snackbar.LENGTH_LONG).show();
            } else {
                // If the number of players is valid, start the GameActivity
                Intent intent = new Intent(this, QueueActivity.class);
                startActivity(intent);
            }
        });
    }
}