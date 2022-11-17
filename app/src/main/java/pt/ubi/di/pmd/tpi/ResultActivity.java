package pt.ubi.di.pmd.tpi;

import static pt.ubi.di.pmd.tpi.GameActivity.remainingPlayers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class ResultActivity extends AppCompatActivity {

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
            Intent intent = new Intent(this, QueueActivity.class);
            startActivity(intent);
        });

        // If the exit button is clicked, it will go to the main menu
        exit.setOnClickListener(v -> {
            // Reset the players ArrayList
            MainActivity.players.clear();
            // Go to the main menu
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    // This function receives two strings (the role of the winners, and the names of the winners)
    // And saves it in the scoreboard XML file located in the raw folder
    public void newScore(String winner_role, String winner_names){
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
