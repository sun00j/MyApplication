package com.sun00j.myapplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by lewa on 10/26/15.
 */
public class GameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}
