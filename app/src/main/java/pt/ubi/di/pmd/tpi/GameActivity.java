package pt.ubi.di.pmd.tpi;

import static pt.ubi.di.pmd.tpi.MainActivity.players;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class GameActivity extends AppCompatActivity {

    // Declaration of variables
    Button next_player;
    Button guess_btn;
    Button guess_location;
    Button submit_num;

    // Declaration of EditTexts
    TextView insert_num;
    TextView turn_player;
    TextView reunion_players;

    CheckedTextView option1;
    CheckedTextView option2;
    CheckedTextView option3;
    CheckedTextView option4;
    CheckedTextView option5;


    // Declaration of LinearLayouts
    LinearLayout ll_turn;
    LinearLayout ll_reunion;
    LinearLayout ll_guess_location;

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
        submit_num = findViewById(R.id.submit_num);

        // Initialize the text views
        turn_player = findViewById(R.id.turn_player);
        insert_num = findViewById(R.id.insert_num);
        reunion_players = findViewById(R.id.reunion_players);

        // Initialize the checked text views
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        option5 = findViewById(R.id.option_5);


        // Initialize the LinearLayouts
        ll_turn = findViewById(R.id.ll_turn);
        ll_reunion = findViewById(R.id.ll_reunion);
        ll_guess_location = findViewById(R.id.ll_guess);

        // Declare an ArrayList with the round players and put them all the players in it
        AtomicReference<ArrayList<Player>> roundPlayers = new AtomicReference<>(new ArrayList<>(players));

        // The game starts
        // It will choose the first player randomly in the roundPlayers ArrayList
        // And remove him from the ArrayList
        // If the next button is pressed, it will choose the next player randomly in the roundPlayers ArrayList and remove him from the ArrayList roundPlayers
        // If the roundPlayers ArrayList is empty, it will show a reunion room
        next_player.setOnClickListener(v -> {
            // If the roundPlayers ArrayList is empty, it will show a reunion room
            if (roundPlayers.get().isEmpty()) {
                // Hide the turn
                ll_turn.setVisibility(View.GONE);

                // Show the reunion room
                ll_reunion.setVisibility(View.VISIBLE);

                // Insert the remaining players in reunion_players text view + the number of each player
                for (int i = 0; i < remainingPlayers.size(); i++) {
                    reunion_players.append(remainingPlayers.get(i).getName() + " - " + remainingPlayers.get(i).getNumber() + "\n");
                }
            } else {
                // Get a player randomly from the roundPlayers ArrayList
                Player current_player = roundPlayers.get().get((int) (Math.random() * roundPlayers.get().size()));
                // Show his name on the screen
                turn_player.setText(current_player.getName());
                // And remove him from the ArrayList
                roundPlayers.get().remove(current_player);
            }
        });

        // If one of the buttons to kick a player is clicked
        // Then the player gets off the remainingPlayers and roundPlayers ArrayList
        // And the game continues
        submit_num.setOnClickListener(v -> {
            // Get the number that the player inserted
            int num = Integer.parseInt(insert_num.getText().toString());

            // Check if the number is in the remainingPlayers ArrayList, if it is, remove the player from the ArrayList
            if (remainingPlayers.stream().anyMatch(player -> player.getNumber() == num)) {
                remainingPlayers.removeIf(player -> player.getNumber() == num);
                roundPlayers.get().removeIf(player -> player.getNumber() == num);
                Snackbar.make(v, "Player kicked", Snackbar.LENGTH_SHORT).show();
            } else {
                // If not show an error message
                Snackbar.make(v, "Player not found", Snackbar.LENGTH_SHORT).show();
            }

            // Hide the reunion room
            ll_reunion.setVisibility(View.GONE);

            // Reset Round Players
            roundPlayers.set(new ArrayList<>(remainingPlayers));

            // Show the turn
            ll_turn.setVisibility(View.VISIBLE);
        });

        // If the guess button is clicked then it will show the options of the locations for the player to guess
        guess_btn.setOnClickListener(v -> {
            // Hide the turn
            ll_turn.setVisibility(LinearLayout.GONE);
            // Hide the guess button
            guess_btn.setVisibility(Button.INVISIBLE);


            // Get the locations
            ArrayList<String> locations = getLocations();

            // Show the locations on the CheckTextViews (including always the correct location)
            option1.setText(locations.get((int) (Math.random() * locations.size())));
            option2.setText(locations.get((int) (Math.random() * locations.size())));
            option3.setText(locations.get((int) (Math.random() * locations.size())));
            option4.setText(locations.get((int) (Math.random() * locations.size())));
            option5.setText(locations.get((int) (Math.random() * locations.size())));

            // Show the guess location screen
            ll_guess_location.setVisibility(LinearLayout.VISIBLE);
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

                        // Reset the CheckTextViews
                        option1.setChecked(false);
                        option2.setChecked(false);
                        option3.setChecked(false);
                        option4.setChecked(false);
                        option5.setChecked(false);

                        // Click the next player button
                        next_player.performClick();

                        // Hide the guess location screen
                        ll_guess_location.setVisibility(LinearLayout.GONE);

                        // Show the turn
                        ll_turn.setVisibility(LinearLayout.VISIBLE);
                    }
                }
            }
        });
    }

    // Function to get the locations, return an ArrayList with the locations
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
