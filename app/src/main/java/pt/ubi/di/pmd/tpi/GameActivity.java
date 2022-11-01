package pt.ubi.di.pmd.tpi;

import static pt.ubi.di.pmd.tpi.MainActivity.players;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    // Declaration of variables
    Button next_player;
    Button guess_btn;
    Button guess_location;
    TextView turn_title;
    TextView turn_player;
    TextView local_title;
    CheckedTextView option1;
    CheckedTextView option2;
    CheckedTextView option3;
    CheckedTextView option4;
    CheckedTextView option5;

    // Declare an ArrayList with the remaining players
    public static ArrayList<Player> remainingPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize the ArrayList
        remainingPlayers = new ArrayList<>(players);

        // Initialize the buttons
        next_player = findViewById(R.id.next_player);
        guess_btn = findViewById(R.id.guess_btn);
        guess_location = findViewById(R.id.take_guess);

        // Initialize the text views
        turn_title = findViewById(R.id.turn_title);
        turn_player = findViewById(R.id.turn_player);
        local_title = findViewById(R.id.local_title);

        // Initialize the checked text views
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        option5 = findViewById(R.id.option_5);

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
                // Show the reunion room (POR FAZER)
            } else {
                // Get a player randomly from the roundPlayers ArrayList
                Player current_player = roundPlayers.get((int) (Math.random() * roundPlayers.size()));
                // Show his name on the screen
                turn_player.setText(current_player.getName());
                // And remove him from the ArrayList
                roundPlayers.remove(current_player);
            }
        });

        // If the guess button is clicked then it will show the options of the locations for the player to guess
        guess_btn.setOnClickListener(v -> {
            // Hide the title of turn
            turn_title.setVisibility(TextView.INVISIBLE);
            // Hide the name of the player
            turn_player.setVisibility(TextView.INVISIBLE);
            // Hide the guess button
            guess_btn.setVisibility(Button.INVISIBLE);
            // Hide the next player button
            next_player.setVisibility(Button.INVISIBLE);


            // Get the locations
            ArrayList<String> locations = getLocations();

            // Show the locations on the CheckTextViews (including always the correct location)
            option1.setText(locations.get((int) (Math.random() * locations.size())));
            option2.setText(locations.get((int) (Math.random() * locations.size())));
            option3.setText(locations.get((int) (Math.random() * locations.size())));
            option4.setText(locations.get((int) (Math.random() * locations.size())));
            option5.setText(locations.get((int) (Math.random() * locations.size())));

            // Show the title of the locations
            local_title.setVisibility(TextView.VISIBLE);

            // Show the button to guess the location
            guess_location.setVisibility(Button.VISIBLE);

            // Show the options of the locations for the player to guess
            option1.setVisibility(CheckedTextView.VISIBLE);
            option2.setVisibility(CheckedTextView.VISIBLE);
            option3.setVisibility(CheckedTextView.VISIBLE);
            option4.setVisibility(CheckedTextView.VISIBLE);
            option5.setVisibility(CheckedTextView.VISIBLE);
        });

        // If the guess location button is clicked then it will check if the player only selected one option and if it is the correct one
        guess_location.setOnClickListener(v -> {
            // If the player selected more than one option, it will show a message saying that he can only select one option
            if (option1.isChecked() && option2.isChecked() || option1.isChecked() && option3.isChecked() || option1.isChecked() && option4.isChecked() || option1.isChecked() && option5.isChecked() || option2.isChecked() && option3.isChecked() || option2.isChecked() && option4.isChecked() || option2.isChecked() && option5.isChecked() || option3.isChecked() && option4.isChecked() || option3.isChecked() && option5.isChecked() || option4.isChecked() && option5.isChecked()) {
                // Show a message saying that he can only select one option (Snackbar)
                Snackbar.make(findViewById(android.R.id.content), "Apenas pode selecionar uma opção!", Snackbar.LENGTH_LONG).show();
            } else {
                // If the player selected the correct option, it will show a message saying that he guessed correctly
                if (option1.isChecked() && option1.getText().equals(getLocations().get(0)) || option2.isChecked() && option2.getText().equals(getLocations().get(0)) || option3.isChecked() && option3.getText().equals(getLocations().get(0)) || option4.isChecked() && option4.getText().equals(getLocations().get(0)) || option5.isChecked() && option5.getText().equals(getLocations().get(0))) {
                    // Change remainingPlayers ArrayList for only players that are "Espião" role
                    remainingPlayers.removeIf(player -> !player.getRole().equals("Espião"));

                    // If the player guesses correctly, the game ends and it will show the result (ResultActivity)
                    Intent intent = new Intent(this, ResultActivity.class);
                    startActivity(intent);
                } else {
                    // If the player selected the wrong option, it will show a message saying that he guessed incorrectly
                    // And remove him from the remaining players
                    if (option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked() || option5.isChecked()) {
                        // Remove him from the remaining players and the game continues
                        remainingPlayers.removeIf(player -> player.getName().contentEquals(turn_player.getText()));

                        // Set visibility of the title of the locations to GONE
                        local_title.setVisibility(CheckedTextView.GONE);

                        // Set visibility of the this button to GONE
                        guess_location.setVisibility(Button.GONE);

                        // Set visibility of the options to GONE
                        option1.setVisibility(CheckedTextView.GONE);
                        option2.setVisibility(CheckedTextView.GONE);
                        option3.setVisibility(CheckedTextView.GONE);
                        option4.setVisibility(CheckedTextView.GONE);
                        option5.setVisibility(CheckedTextView.GONE);
                    }
                }
            }
        });
    }

    // Make a function to get the locations, return an ArrayList with the locations
    public ArrayList<String> getLocations() {
        // Read the file location.txt in the raw folder
        InputStream inputStream = getResources().openRawResource(R.raw.location);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // Get all the locations from the locations XML file
        ArrayList<String> location = new ArrayList<>();

        // Read the file line by line and add it to the ArrayList
        String line;

        // Try to read the file
        try {
            while ((line = bufferedReader.readLine()) != null) {
                location.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the ArrayList with the locations
        return location;
    }

}
