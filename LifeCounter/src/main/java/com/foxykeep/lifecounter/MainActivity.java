package com.foxykeep.lifecounter;

import com.foxykeep.lifecounter.util.PlatformVersion;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String SAVED_STATE_SHOW_POISON_COUNTERS = "savedStateShowPoisonCounters";

    private static final String SAVED_STATE_PLAYER_1_LIFE = "savedStatePlayer1Life";
    private static final String SAVED_STATE_PLAYER_1_POISON = "savedStatePlayer1Poison";
    private static final String SAVED_STATE_PLAYER_2_LIFE = "savedStatePlayer2Life";
    private static final String SAVED_STATE_PLAYER_2_POISON = "savedStatePlayer2Poison";

    private TextView mPlayer1LifeView;
    private View mPlayer1PoisonContainer;
    private TextView mPlayer1PoisonView;
    private TextView mPlayer2LifeView;
    private View mPlayer2PoisonContainer;
    private TextView mPlayer2PoisonView;

    private boolean mShowPoisonCounters;

    private int mPlayer1Life;
    private int mPlayer1Poison;
    private int mPlayer2Life;
    private int mPlayer2Poison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bindViews();

        if (savedInstanceState != null) {
            mShowPoisonCounters = savedInstanceState.getBoolean(SAVED_STATE_SHOW_POISON_COUNTERS);

            mPlayer1Life = savedInstanceState.getInt(SAVED_STATE_PLAYER_1_LIFE);
            mPlayer1Poison = savedInstanceState.getInt(SAVED_STATE_PLAYER_1_POISON);
            mPlayer2Life = savedInstanceState.getInt(SAVED_STATE_PLAYER_2_LIFE);
            mPlayer2Poison = savedInstanceState.getInt(SAVED_STATE_PLAYER_2_POISON);

            populateViews();
        } else {
            resetAndPopulateViews();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(SAVED_STATE_SHOW_POISON_COUNTERS, mShowPoisonCounters);

        outState.putInt(SAVED_STATE_PLAYER_1_LIFE, mPlayer1Life);
        outState.putInt(SAVED_STATE_PLAYER_1_POISON, mPlayer1Poison);
        outState.putInt(SAVED_STATE_PLAYER_2_LIFE, mPlayer2Life);
        outState.putInt(SAVED_STATE_PLAYER_2_POISON, mPlayer2Poison);
    }

    private void bindViews() {
        setBackground();

        // Player 1 views
        findViewById(R.id.player1_life_add).setOnClickListener(this);
        findViewById(R.id.player1_life_remove).setOnClickListener(this);
        mPlayer1LifeView = (TextView) findViewById(R.id.player1_life);

        mPlayer1PoisonContainer = findViewById(R.id.player1_poison_container);
        findViewById(R.id.player1_poison_add).setOnClickListener(this);
        findViewById(R.id.player1_poison_remove).setOnClickListener(this);
        mPlayer1PoisonView = (TextView) findViewById(R.id.player1_poison);

        // Reset view
        findViewById(R.id.reset).setOnClickListener(this);

        // Player 2 views
        findViewById(R.id.player2_life_add).setOnClickListener(this);
        findViewById(R.id.player2_life_remove).setOnClickListener(this);
        mPlayer2LifeView = (TextView) findViewById(R.id.player2_life);

        mPlayer2PoisonContainer = findViewById(R.id.player2_poison_container);
        findViewById(R.id.player2_poison_add).setOnClickListener(this);
        findViewById(R.id.player2_poison_remove).setOnClickListener(this);
        mPlayer2PoisonView = (TextView) findViewById(R.id.player2_poison);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setBackground() {
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TR_BL,
                new int[] {Color.rgb(220, 20, 20), Color.rgb(0, 146, 69)});
        if (PlatformVersion.isAtLeastJellyBean()) {
            findViewById(R.id.root_container).setBackground(gd);
        } else {
            findViewById(R.id.root_container).setBackgroundDrawable(gd);
        }
    }

    private void populateViews() {
        mPlayer1LifeView.setText(String.valueOf(mPlayer1Life));
        mPlayer2LifeView.setText(String.valueOf(mPlayer2Life));

        if (mShowPoisonCounters) {
            mPlayer1PoisonContainer.setVisibility(View.VISIBLE);
            mPlayer1PoisonView.setVisibility(View.VISIBLE);
            mPlayer1PoisonView.setText(String.valueOf(mPlayer1Poison));

            mPlayer2PoisonContainer.setVisibility(View.VISIBLE);
            mPlayer2PoisonView.setVisibility(View.VISIBLE);
            mPlayer2PoisonView.setText(String.valueOf(mPlayer2Poison));
        } else {
            mPlayer1PoisonContainer.setVisibility(View.GONE);
            mPlayer1PoisonView.setVisibility(View.GONE);

            mPlayer2PoisonContainer.setVisibility(View.GONE);
            mPlayer2PoisonView.setVisibility(View.GONE);
        }
    }

    private void resetAndPopulateViews() {
        mPlayer1Life = 20;
        mPlayer2Life = 20;
        mPlayer1Poison = 0;
        mPlayer2Poison = 0;

        populateViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.player1_life_add:
                mPlayer1Life++;
                break;
            case R.id.player1_life_remove:
                mPlayer1Life--;
                break;
            case R.id.player1_poison_add:
                mPlayer1Poison++;
                break;
            case R.id.player1_poison_remove:
                if (mPlayer1Poison > 0) {
                    mPlayer1Poison--;
                }
                break;
            case R.id.reset:
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setTitle(R.string.main_reset_dialog_title);
                b.setMessage(R.string.main_reset_dialog_message);
                b.setIcon(android.R.drawable.ic_dialog_alert);
                b.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetAndPopulateViews();
                    }
                });
                b.setNegativeButton(android.R.string.no, null);
                b.show();
                break;
            case R.id.player2_life_add:
                mPlayer2Life++;
                break;
            case R.id.player2_life_remove:
                mPlayer2Life--;
                break;
            case R.id.player2_poison_add:
                mPlayer2Poison++;
                break;
            case R.id.player2_poison_remove:
                if (mPlayer2Poison > 0) {
                    mPlayer2Poison--;
                }
                break;
        }

        populateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_show_poison:
                mShowPoisonCounters = !item.isChecked();
                item.setChecked(mShowPoisonCounters);
                populateViews();
        }
        return super.onOptionsItemSelected(item);
    }
}