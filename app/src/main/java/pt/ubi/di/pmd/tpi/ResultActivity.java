package pt.ubi.di.pmd.tpi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class ResultActivity extends Activity {

    // Declaration of TextView variables
    TextView win_role;
    TextView win_names;

    // Declaration of Button variables
    Button new_game;
    Button exit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Declare the remaining players ArrayList (with serialized players)
        ArrayList<Player> remainingPlayers = (ArrayList<Player>) getIntent().getSerializableExtra("remainingPlayers");

        // Declare the players ArrayList (with serialized players)
        ArrayList<Player> players = (ArrayList<Player>) getIntent().getSerializableExtra("players");

        // Initialization of TextView variables
        win_role = findViewById(R.id.role_result);
        win_names = findViewById(R.id.players_result);

        // Initialization of Button variables
        new_game = findViewById(R.id.new_game_btn);
        exit = findViewById(R.id.home_btn);

        // Set the winner role to the first role in the ArrayList of the remaining players
        win_role.setText(remainingPlayers.get(0).getRole());

        // Reset the win_names TextView
        win_names.setText("");

        // Set the winner names to the names of all the remaining players
        for (int i = 0; i < remainingPlayers.size(); i++) {
            win_names.setText(win_names.getText() + remainingPlayers.get(i).getName() + "\n");
        }

        // Save the score in the scoreboard XML file (with player's names and roles)
        newScore(win_role.getText().toString(), win_names.getText().toString());

        // If the new game button is clicked, it will start a new game
        new_game.setOnClickListener(v -> {
            // Start a new game and go to the Queue Activity
            Intent intent = new Intent(ResultActivity.this, QueueActivity.class);
            intent.putExtra("players", players);
            startActivity(intent);
            finish();
        });

        // If the exit button is clicked, it will go to the main menu
        exit.setOnClickListener(v -> {
            // Reset the players ArrayList
            players.clear();
            // Go to the main menu
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.putExtra("players", players);
            startActivity(intent);
            finish();
        });
    }

    // This function receives two strings (the role of the winners, and the names of the winners)
    // And saves it in the scoreboard XML file located in the raw folder
    public void newScore(String winner_role, String winner_names) {
        // Declare the FileOutputStream variable
        FileOutputStream outputStream;
        // Try to open the scoreboard XML file
        try {
            // Open the scoreboard XML file, located in the raw folder (res/raw/scoreboard.xml)
            outputStream = openFileOutput("scoreboard.xml", Context.MODE_APPEND);
            // Write the winner role and names in the file
            outputStream.write(("\n<score><player_role>" + winner_role + "</player_role><player_names>" + winner_names + "</player_names></score>").getBytes());
            // Close the file
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
