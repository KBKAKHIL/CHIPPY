package com.example.parrot.pong1;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    GameEngine CHIPPY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get size of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Initialize the GameEngine object
        // Pass it the screen size (height & width)
        CHIPPY = new GameEngine(this, size.x, size.y);

        // Make GameEngine the view of the Activity
        setContentView(CHIPPY);
    }

    // Android Lifecycle functions
    // ----------------------------

    // This function gets run when user switches from the game to some other app on the phone
    @Override
    protected void onPause() {
        super.onPause();

        // Pause the game
        CHIPPY.pauseGame();
    }

    // This function gets run when user comes back to the game
    @Override
    protected void onResume() {
        super.onResume();

        // Start the game
        CHIPPY.startGame();
    }

}