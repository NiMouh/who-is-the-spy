package pt.ubi.di.pmd.tpi;

import static pt.ubi.di.pmd.tpi.MainActivity.players;

import android.annotation.SuppressLint;
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

    // Declare an ArrayList with the round players
    public static ArrayList<Player> roundPlayers;

    // Declare an ArrayList with the reunion players
    Player current_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize the ArrayList
        remainingPlayers = new ArrayList<>(players);
        roundPlayers = new ArrayList<>(players);

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


        // Set a current player
        current_player = roundPlayers.get((int) (Math.random() * roundPlayers.size()));

        // Set the text of the first player to play (randomly)
        turn_player.setText(current_player.getName());

        // Remove the player from the ArrayList (the player that has his name on the turn_player TextView)
        roundPlayers.remove(current_player);

        // The game starts
        // It will choose the first player randomly in the roundPlayers ArrayList
        // And remove him from the ArrayList
        // If the next button is pressed, it will choose the next player randomly in the roundPlayers ArrayList and remove him from the ArrayList roundPlayers
        // If the roundPlayers ArrayList is empty, it will show a reunion room
        next_player.setOnClickListener(v -> {
            // If the roundPlayers ArrayList is empty, it will show a reunion room
            if (roundPlayers.isEmpty()) {
                // Hide the turn
                ll_turn.setVisibility(View.GONE);

                // Hide the guess button
                guess_btn.setVisibility(View.GONE);

                // Show the reunion room
                ll_reunion.setVisibility(View.VISIBLE);

                // Set the remaining players in reunion_players text view + the number of each player
                for (int i = 0; i < remainingPlayers.size(); i++) {
                    reunion_players.append(reunion_players.getText() + remainingPlayers.get(i).getName() + " - " + (i + 1) + "\n");
                }
            } else {
                // Get a player randomly from the roundPlayers ArrayList
                current_player = roundPlayers.get((int) (Math.random() * roundPlayers.size()));
                // Show his name on the screen
                turn_player.setText(current_player.getName());
                // And remove him from the ArrayList
                roundPlayers.remove(current_player);
            }
        });

        // If one of the buttons to kick a player is clicked
        // Then the player gets off the remainingPlayers and roundPlayers ArrayList
        // And the game continues
        submit_num.setOnClickListener(v -> {
            // Get the number that the player inserted
            int num = Integer.parseInt(insert_num.getText().toString());

            // If the num is '0', then don't do anything
            if (num == 0) {
                // Print a Snackbar message saying you choose to not remove anyone
                Snackbar.make(v, "You choose to not remove anyone", Snackbar.LENGTH_LONG).show();
            } else {
                // If the num is bigger than the remaining players or less than zero, then don't do anything
                if (num > remainingPlayers.size() || num < 0) {
                    Snackbar.make(v, "Please insert a number between 1 and " + remainingPlayers.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    // Else remove the player from the remainingPlayers and roundPlayers ArrayList
                    remainingPlayers.remove(num - 1);
                    Snackbar.make(v, "Player kicked", Snackbar.LENGTH_SHORT).show();
                }
            }


            // Check if the remainingPlayers ArrayList only has two players
            // If it's true, then the game ends
            if (remainingPlayers.size() == 2) {
                // If one of them has a role "Espião" then remove the other one from the remainingPlayers ArrayList
                // And go to the result activity
                if (remainingPlayers.get(0).getRole().equals("Espião") || remainingPlayers.get(1).getRole().equals("Espião")) {
                    // Remove the player with the role "Investigador" from the remainingPlayers ArrayList
                    remainingPlayers.removeIf(player -> player.getRole().equals("Investigador"));
                    Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                    startActivity(intent);
                } else {
                    // Go to the result activity
                    Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                    startActivity(intent);
                }


                // If so, the game ends and it will show the result (ResultActivity)
                Intent intent = new Intent(this, ResultActivity.class);
                startActivity(intent);
            }
            // else if all the players with the role "Espião" are out
            else if (remainingPlayers.stream().noneMatch(player -> player.getRole().equals("Espião"))) {
                // If so, the game ends and it will show the result (ResultActivity)
                Intent intent = new Intent(this, ResultActivity.class);
                startActivity(intent);
            }
            else {
                // Hide the reunion room
                ll_reunion.setVisibility(View.GONE);

                // Reset the insert_num EditText
                insert_num.setText("");

                // Reset Round Players
                roundPlayers = new ArrayList<>(remainingPlayers);

                // Perform a click on the next player button
                next_player.performClick();

                // Show the turn
                ll_turn.setVisibility(View.VISIBLE);

                // Show the guess button
                guess_btn.setVisibility(View.VISIBLE);
            }
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
