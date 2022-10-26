package pt.ubi.di.pmd.tpi;

import static pt.ubi.di.pmd.tpi.MainActivity.players;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    // Make an ArrayList of the roles
    ArrayList<String> roles = new ArrayList<>();

    //


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        // Call the method that adds the roles to the ArrayList
        giveRoles();

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

        // If the queue button is clicked, change visibility of the role title and subtitle, the queue button to invisible
        // player_title, player_number, player_name and player_role and the next player button to visible.
        queue.setOnClickListener(v -> {

            // Change player_number to the current player number
            player_number.setText((players.size() - player_size_aux.get() + 1) + "");

            // Change player_name to the current player name
            player_name.setText(players.get(players.size() - player_size_aux.get()).getName());

            // FORGOT: Give the current player a location

            // Give role to the current player in the class
            //
            players.get(players.size() - player_size_aux.get()).setRole(roles.get(players.size() - player_size_aux.get()));
            player_role.setText(roles.get(players.size() - player_size_aux.get()));

            // View that disappears
            role_title.setVisibility(android.view.View.INVISIBLE);
            role_subtitle.setVisibility(android.view.View.INVISIBLE);
            queue.setVisibility(Button.INVISIBLE);

            // View that appears
            player_title.setVisibility(android.view.View.VISIBLE);
            player_number.setVisibility(android.view.View.VISIBLE);
            player_name.setVisibility(android.view.View.VISIBLE);
            player_role.setVisibility(android.view.View.VISIBLE);
            player_place.setVisibility(android.view.View.VISIBLE);
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
            player_title.setVisibility(android.view.View.INVISIBLE);
            player_number.setVisibility(android.view.View.INVISIBLE);
            player_name.setVisibility(android.view.View.INVISIBLE);
            player_role.setVisibility(android.view.View.INVISIBLE);
            player_place.setVisibility(android.view.View.INVISIBLE);
            next_player.setVisibility(Button.INVISIBLE);

            // View that appears
            role_title.setVisibility(android.view.View.VISIBLE);
            role_subtitle.setVisibility(android.view.View.VISIBLE);
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

    // Function that gives the location randomly to the ArrayList of locations
}
