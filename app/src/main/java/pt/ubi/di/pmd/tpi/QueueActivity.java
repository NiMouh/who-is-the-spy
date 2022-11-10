package pt.ubi.di.pmd.tpi;

import static pt.ubi.di.pmd.tpi.MainActivity.players;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QueueActivity extends AppCompatActivity {

    // Declaration of button and text variables
    TextView player_number;
    TextView player_name;
    TextView player_role;
    TextView player_place;

    Button next_player;
    Button queue;

    LinearLayout ll_player;
    LinearLayout ll_role;

    // Make an ArrayList of the roles
    ArrayList<String> roles = new ArrayList<>();

    // Make an ArrayList of the locations
    ArrayList<String> locations = new ArrayList<>();

    // Declare the choosen location variable
    String choosenLocation;


    // Make a aux with the number of players
    int aux = players.size();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        // Call the method that adds the roles to the ArrayList
        giveRoles();
        // Call the method that adds the locations to the ArrayList
        givePlace();


        // Initialization of the choosen location as a random location of the locations ArrayList
        choosenLocation = locations.get((int) (Math.random() * locations.size()));

        // Initialization of variables
        player_number = findViewById(R.id.player_number);
        player_name = findViewById(R.id.player_name);
        player_role = findViewById(R.id.player_role);
        player_place = findViewById(R.id.player_place);

        // Initialize of buttons
        next_player = findViewById(R.id.next_player_btn);
        queue = findViewById(R.id.queque_btn);

        // Set Queue Activity Scenarios
        ll_player = findViewById(R.id.ll_player);
        ll_role = findViewById(R.id.ll_role);

        // Set player_name to the name of the first player
        player_name.setText(players.get(players.size() - aux).getName());

        // If the queue button is clicked, it will show the role of the player
        queue.setOnClickListener(v -> {
            // Change the player's number to the current player
            player_number.setText((players.size() - aux + 1) + "");

            // Get a random role from the ArrayList of roles
            String role = roles.get((int) (Math.random() * roles.size()));

            // Change the player's role to the current player
            player_role.setText(role);

            // Give that player the role of the current player
            players.get(players.size() - aux).setRole(role);

            // Remove the role from the ArrayList of roles
            roles.remove(role);

            // Change the player's place to the current player
            // If the player have the role "Espião" then the place is "Descobre os outros locais" else give the place
            if (players.get(players.size() - aux).getRole().equals("Espião")) {
                player_place.setText("Descobre os outros locais");
            } else {
                player_place.setText(choosenLocation);
                players.get(players.size() - aux).setPlace(choosenLocation);
            }

            // Change the visibility of the player's name and role to visible
            ll_player.setVisibility(View.VISIBLE);

            // Change the visibility of the player's place to invisible
            ll_role.setVisibility(View.INVISIBLE);

            // Decrease the aux
            aux--;
        });

        // If the next player button is clicked, change visibility of the player title, player number, player name and player role
        // to invisible and the role title, role subtitle and the queue button to visible
        next_player.setOnClickListener(v -> {

            // If the aux is 0, go to the next activity
            if (aux == 0) {
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
            } else {
                // Change player_name to the current player name
                player_name.setText(players.get(players.size() - aux).getName());

                // View that disappears
                ll_player.setVisibility(View.INVISIBLE);

                // View that appears
                ll_role.setVisibility(View.VISIBLE);
            }
        });

    }


    // Function that give the roles randomly to the ArrayList of roles (1 Espião for every 5 players)
    public void giveRoles() {
        for (int i = 0; i < players.size(); i++) {
            if (i % 5 == 0) {
                roles.add("Espião");
            } else {
                roles.add("Investigador");
            }
        }
    }

    // Function that opens a .txt file with the locations saves them in an array and returns one of them randomly
    public void givePlace() {
        // Read the file location.xml in the raw folder
        InputStream inputStream = getResources().openRawResource(R.raw.location);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // Read the file line by line and add it to the ArrayList
        String line;

        // Fazer Shared Preferences (???)

        // Try to read the file (if the line is "<resources>" or "</resources>" then don't add it to the ArrayList)
        // Neither "<?xml version="1.0" encoding="utf-8"?>"
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("<resources>") && !line.equals("</resources>") && !line.equals("<?xml version=\"1.0\" encoding=\"utf-8\"?>")) {
                    locations.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
