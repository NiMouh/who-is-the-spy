package pt.ubi.di.pmd.tpi;

import static pt.ubi.di.pmd.tpi.GameActivity.remainingPlayers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        try {
            saveScore(win_role.getText().toString(), win_names.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If the new game button is clicked, it will start a new game
        new_game.setOnClickListener(v -> {
            // Start a new game and go to the Queue Activity
            Intent intent = new Intent(this, QueueActivity.class);
            startActivity(intent);
        });

        // If the exit button is clicked, it will go to the main menu
        exit.setOnClickListener(v -> {
            // Go to the main menu
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    // Function that receives the name of the role and the player's names and saves it in the scoreboard XML file
    public void saveScore(String role, String names) throws IOException {
        // Insert in the file scoreboard.xml in the raw folder
        InputStream inputStream = getResources().openRawResource(R.raw.scoreboard);
        // Read the file
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        // Make a string with the content of the file
        String line = reader.readLine();
        // Make a string with the content of the file
        StringBuilder fileContent = new StringBuilder();
        // While the line is not null, add it to the fileContent string
        while (line != null) {
            fileContent.append(line).append("\n");
            line = reader.readLine();
        }
        // Add the new score to the fileContent string "<player_role> <player_names>" inside "<score>"
        fileContent.append("<score><player_role>").append(role).append("</player_role><player_names>").append(names).append("</player_names></score>");
        // Save the fileContent string in the scoreboard.xml file
        FileOutputStream outputStream = openFileOutput("scoreboard.xml", Context.MODE_PRIVATE);
        outputStream.write(fileContent.toString().getBytes());
        outputStream.close();
    }
}
