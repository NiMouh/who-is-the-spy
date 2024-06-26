package pt.ubi.di.pmd.tpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class GameActivity extends Activity {

    // Declaration of variables
    Button next_player;
    Button guess_btn;
    Button guess_location;
    Button submit_num;

    // Declaration of EditTexts
    TextView insert_num;
    TextView turn_player;
    TextView reunion_players;

    // Declaration of option Checkbox variables
    CheckBox option1;
    CheckBox option2;
    CheckBox option3;
    CheckBox option4;
    CheckBox option5;


    // Declaration of LinearLayouts
    LinearLayout ll_turn;
    LinearLayout ll_reunion;
    LinearLayout ll_guess_location;

    // Declare an ArrayList with the remaining players
    ArrayList<Player> remainingPlayers;

    // Declare an ArrayList with the round players
    ArrayList<Player> roundPlayers;

    // Declare an ArrayList with the reunion players
    Player current_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Declare Player ArrayList (with serialized players)
        ArrayList<Player> players = (ArrayList<Player>) getIntent().getSerializableExtra("players");

        // Declare the choosen location variable
        String choosenLocation = getIntent().getStringExtra("choosenLocation");

        // Declare the location ArrayList
        ArrayList<String> locations = (ArrayList<String>) getIntent().getSerializableExtra("locations");

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

        // Initialize the checkboxes
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        option5 = findViewById(R.id.option_5);


        // Initialize the LinearLayouts
        ll_turn = findViewById(R.id.ll_turn);
        ll_reunion = findViewById(R.id.ll_reunion);
        ll_guess_location = findViewById(R.id.ll_guess);


        // Set the standard visibility of the elements
        ll_turn.setVisibility(View.VISIBLE);
        guess_btn.setVisibility(View.VISIBLE);
        ll_reunion.setVisibility(View.GONE);
        ll_guess_location.setVisibility(View.GONE);


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
                    reunion_players.append(remainingPlayers.get(i).getName() + " - " + (i + 1) + "\n");
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
            // If the string is empty, it will show a message
            if (insert_num.getText().toString().isEmpty()) {
                // Print a snack bar message
                Snackbar.make(v, "Por favor inserir um número", Snackbar.LENGTH_LONG).show();
            } else {
                // Get the number that the player inserted
                int num = Integer.parseInt(insert_num.getText().toString());

                // If the num is '0', then don't do anything
                if (num == 0) {
                    // Print a Snackbar message saying you choose to not remove anyone
                    Snackbar.make(v, "Escolheram não eliminar ninguém.", Snackbar.LENGTH_LONG).show();
                } else {
                    // If the num is bigger than the remaining players or less than zero, then don't do anything
                    if (num > remainingPlayers.size() || num < 0) {
                        Snackbar.make(v, "Inserir apenas um número entre 1 e " + remainingPlayers.size(), Snackbar.LENGTH_LONG).show();
                    } else {
                        // Else remove the player from the remainingPlayers and roundPlayers ArrayList
                        Snackbar.make(v, "O jogador " + remainingPlayers.get(num - 1).getName() + " foi expulso.", Snackbar.LENGTH_SHORT).show();
                        remainingPlayers.remove(num - 1);
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

                        // Send the remaining players to the result activity
                        intent.putExtra("remainingPlayers", remainingPlayers);

                        // Send the players to the result activity
                        intent.putExtra("players", players);

                        startActivity(intent);
                        finish();
                    } else {
                        // Go to the result activity
                        Intent intent = new Intent(GameActivity.this, ResultActivity.class);

                        // Send the remaining players to the result activity
                        intent.putExtra("remainingPlayers", remainingPlayers);

                        // Send the players to the result activity
                        intent.putExtra("players", players);

                        startActivity(intent);
                        finish();
                    }


                    // If so, the game ends, and it will show the result (ResultActivity)
                    Intent intent = new Intent(GameActivity.this, ResultActivity.class);

                    // Send the remaining players to the result activity
                    intent.putExtra("remainingPlayers", remainingPlayers);

                    // Send the players to the result activity
                    intent.putExtra("players", players);

                    startActivity(intent);
                    finish();
                }
                // else if all the players with the role "Espião" are out
                else if (remainingPlayers.stream().noneMatch(player -> player.getRole().equals("Espião"))) {
                    // If so, the game ends, and it will show the result (ResultActivity)
                    Intent intent = new Intent(GameActivity.this, ResultActivity.class);

                    // Send the remaining players to the result activity
                    intent.putExtra("remainingPlayers", remainingPlayers);

                    // Send the players to the result activity
                    intent.putExtra("players", players);

                    startActivity(intent);
                    finish();
                } else {
                    // Hide the reunion room
                    ll_reunion.setVisibility(View.GONE);

                    // Reset the reunion_players text view (with the @string/nome_dos_jogadores)
                    reunion_players.setText(R.string.nome_dos_jogadores);

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
            }
        });

        // If the guess button is clicked then it will show the options of the locations for the player to guess
        guess_btn.setOnClickListener(v -> {
            // If the current player role is "Espião" then show the options
            if (current_player.getRole().equals("Espião")) {
                // Hide the turn
                ll_turn.setVisibility(LinearLayout.GONE);
                // Hide the guess button
                guess_btn.setVisibility(Button.INVISIBLE);

                // Select a location from the locations ArrayList randomly, add it to option1 and remove it from the ArrayList
                option1.setText(locations.get((int) (Math.random() * locations.size())));
                locations.remove(option1.getText().toString());

                option2.setText(locations.get((int) (Math.random() * locations.size())));
                locations.remove(option2.getText().toString());

                option3.setText(locations.get((int) (Math.random() * locations.size())));
                locations.remove(option3.getText().toString());

                option4.setText(locations.get((int) (Math.random() * locations.size())));
                locations.remove(option4.getText().toString());

                option5.setText(locations.get((int) (Math.random() * locations.size())));
                locations.remove(option5.getText().toString());


                // Show the guess location screen
                ll_guess_location.setVisibility(LinearLayout.VISIBLE);
            }
        });

        // If the guess location button is clicked then it will check if the player only selected one option and if it is the correct one
        guess_location.setOnClickListener(v -> {
            // If the player selected the correct option, it will show a message saying that he guessed correctly
            if (option1.isChecked() && option1.getText().equals(choosenLocation) || option2.isChecked() && option2.getText().equals(choosenLocation) || option3.isChecked() && option3.getText().equals(choosenLocation) || option4.isChecked() && option4.getText().equals(choosenLocation) || option5.isChecked() && option5.getText().equals(choosenLocation)) {
                // Change remainingPlayers ArrayList for only players that are "Espião" role
                remainingPlayers.removeIf(player -> !player.getRole().equals("Espião"));

                // If the player guesses correctly, the game ends, and it will show the result (ResultActivity)
                Intent intent = new Intent(this, ResultActivity.class);

                // Send the remaining players to the result activity
                intent.putExtra("remainingPlayers", remainingPlayers);

                // Send the players to the result activity
                intent.putExtra("players", players);

                startActivity(intent);
                finish();
            }
            // If no option is selected, then show a message saying that the player needs to select one option
            else if (!option1.isChecked() && !option2.isChecked() && !option3.isChecked() && !option4.isChecked() && !option5.isChecked()) {
                Snackbar.make(v, "Selecione uma opção", Snackbar.LENGTH_LONG).show();
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

                    // If roundPlayers ArrayList is now empty, then it will not show the turn room
                    if (!roundPlayers.isEmpty()) {
                        // Show the turn
                        ll_turn.setVisibility(LinearLayout.VISIBLE);
                    }

                    // If there is no more "Espião" players, then the game ends, and it will show the result (ResultActivity)
                    if (remainingPlayers.stream().noneMatch(player -> player.getRole().equals("Espião"))) {
                        Intent intent = new Intent(this, ResultActivity.class);

                        // Send the remaining players to the result activity
                        intent.putExtra("remainingPlayers", remainingPlayers);

                        // Send the players to the result activity
                        intent.putExtra("players", players);

                        startActivity(intent);
                        finish();
                    }
                }
            }
        });


        // Check if option1 CheckTextView is clicked
        option1.setOnClickListener(v -> {
            // If it is clicked, it will uncheck the other options
            option2.setChecked(false);
            option3.setChecked(false);
            option4.setChecked(false);
            option5.setChecked(false);
            // And it will check the option1
            option1.setChecked(true);
        });

        // Check if option2 CheckTextView is clicked
        option2.setOnClickListener(v -> {
            // If it is clicked, it will uncheck the other options
            option1.setChecked(false);
            option3.setChecked(false);
            option4.setChecked(false);
            option5.setChecked(false);
            // And it will check the option2
            option2.setChecked(true);
        });

        // Check if option3 CheckTextView is clicked
        option3.setOnClickListener(v -> {
            // If it is clicked, it will uncheck the other options
            option1.setChecked(false);
            option2.setChecked(false);
            option4.setChecked(false);
            option5.setChecked(false);
            // And it will check the option3
            option3.setChecked(true);
        });

        // Check if option4 CheckTextView is clicked
        option4.setOnClickListener(v -> {
            // If it is clicked, it will uncheck the other options
            option1.setChecked(false);
            option2.setChecked(false);
            option3.setChecked(false);
            option5.setChecked(false);
            // And check the option4
            option4.setChecked(true);
        });

        // Check if option5 CheckTextView is clicked
        option5.setOnClickListener(v -> {
            // If it is clicked, it will uncheck the other options
            option1.setChecked(false);
            option2.setChecked(false);
            option3.setChecked(false);
            option4.setChecked(false);
            // And check the option5
            option5.setChecked(true);
        });
    }

}
