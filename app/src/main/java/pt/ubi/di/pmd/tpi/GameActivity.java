package pt.ubi.di.pmd.tpi;

import static pt.ubi.di.pmd.tpi.MainActivity.players;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Get the TextView from the layout
        TextView textView = findViewById(R.id.textView2);

        // Set TextView to the first players Name
        textView.setText(players.get(0));
    }
}
