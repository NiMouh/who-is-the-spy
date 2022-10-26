package pt.ubi.di.pmd.tpi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    // Declaration of variables
    TextView win_role;
    TextView win_names;
    Button new_game;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialization of variables
        win_role = findViewById(R.id.role_result);
        win_names = findViewById(R.id.players_result);
        new_game = findViewById(R.id.new_game_btn);
        exit = findViewById(R.id.home_btn);
    }
}
