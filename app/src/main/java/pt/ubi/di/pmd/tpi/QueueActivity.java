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
import java.util.concurrent.atomic.AtomicInteger;

public class QueueActivity extends AppCompatActivity {

    // Declaration of button and text variables
    TextView player_title;
    TextView player_number;
    TextView player_name;
    TextView player_role;
    TextView player_place;
    Button next_player;

    TextView role_title;
    TextView role_subtitle;
    Button queue;

    LinearLayout ll_player;

    // Make an ArrayList of the roles
    ArrayList<String> roles = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        // Call the method that adds the roles to the ArrayList
        giveRoles();
        // Declare the place where the players are
        String place = givePlace();

        // Make an aux with the number of the players, if that number comes to 0, go to the next activity
        AtomicInteger player_size_aux = new AtomicInteger(players.size());

        // Initialization of variables
        player_title = findViewById(R.id.player_title);
        player_number = findViewById(R.id.player_number);
        player_name = findViewById(R.id.player_name);
        player_role = findViewById(R.id.player_role);
        player_place = findViewById(R.id.player_place);
        next_player = findViewById(R.id.next_player_btn);

        role_title = findViewById(R.id.role_title);
        role_subtitle = findViewById(R.id.role_subtitle);
        queue = findViewById(R.id.queque_btn);

        ll_player = findViewById(R.id.ll_player);

        // Set player_name to the name of the first player
        player_name.setText(players.get(0).getName());

        // If the queue button is clicked, change visibility of the role title and subtitle, the queue button to invisible
        // player_title, player_number, player_name and player_role and the next player button to visible.
        queue.setOnClickListener(v -> {

            // Change player_number to the current player number
            player_number.setText((players.size() - player_size_aux.get() + 1) + "");

            // Change player_name to the current player name
            player_name.setText(players.get(players.size() - player_size_aux.get()).getName());

            // Give role to the current player in the class
            players.get(players.size() - player_size_aux.get()).setRole(roles.get(players.size() - player_size_aux.get()));
            player_role.setText(roles.get(players.size() - player_size_aux.get()));

            if (players.get(players.size() - player_size_aux.get()).getRole().equals("Espião")) {
                // If he's a spy then set place textView to "Descobre os outros locais"
                player_place.setText("Descobre os outros locais");
            } else {
                // If he's an investigator then set place textView to the given text
                // And set player class place
                player_place.setText(place);
                players.get(players.size() - player_size_aux.get()).setPlace(place);
            }

            // View that disappears
            ll_player.setVisibility(View.VISIBLE);
            player_name.setVisibility(View.INVISIBLE);
            queue.setVisibility(Button.INVISIBLE);

            // View that appears
            player_title.setVisibility(View.VISIBLE);
            player_number.setVisibility(View.VISIBLE);
            player_role.setVisibility(View.VISIBLE);
            player_place.setVisibility(View.VISIBLE);
            next_player.setVisibility(Button.VISIBLE);

            // Decrease the aux
            player_size_aux.getAndDecrement();
        });

        // If the next player button is clicked, change visibility of the player title, player number, player name and player role
        // to invisible and the role title, role subtitle and the queue button to visible
        next_player.setOnClickListener(v -> {
            // If the aux is 0, go to the next activity
            if (player_size_aux.get() == 0) {
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
            }

            // View that disappears
            player_title.setVisibility(View.INVISIBLE);
            player_number.setVisibility(View.INVISIBLE);
            player_role.setVisibility(View.INVISIBLE);
            player_place.setVisibility(View.INVISIBLE);
            next_player.setVisibility(Button.INVISIBLE);

            // View that appears
            ll_player.setVisibility(View.VISIBLE);
            player_name.setVisibility(View.VISIBLE);
            queue.setVisibility(Button.VISIBLE);
        });

    }


    // Function that give the roles randomly to the ArrayList of roles (1 Investigator for every 4 players)
    public void giveRoles() {
        for (int i = 0; i < players.size(); i++) {
            if (i % 4 == 0) {
                roles.add("Espião");
            } else {
                roles.add("Investigador");
            }
        }

        // Shuffle the roles
        for (int i = 0; i < roles.size(); i++) {
            int random = (int) (Math.random() * roles.size());
            String temp = roles.get(i);
            roles.set(i, roles.get(random));
            roles.set(random, temp);
        }
    }

    // Function that opens a .txt file with the locations saves them in an array and returns one of them randomly
    public String givePlace() {
        // Read the file location.txt in the raw folder
        InputStream inputStream = getResources().openRawResource(R.raw.location);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // Make an ArrayList of the locations
        ArrayList<String> places = new ArrayList<>();

        // Read the file line by line and add it to the ArrayList
        String line;

        // Fazer Shared Preferences

        // Try to read the file
        try {
            while ((line = bufferedReader.readLine()) != null) {
                places.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return a random location
        return places.get((int) (Math.random() * places.size()));
    }
}
