package com.example.victor.golfscoreapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String SCORE_FILE = "com.example.victor.goldscoreapp.preferences";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Hole[] holes;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences(SCORE_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        holes = new Hole[18];
        holes = attemptToRetrieveScores(holes);

        mEditor.apply();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        HoleAdapter adapter = new HoleAdapter(holes, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private Hole[] attemptToRetrieveScores(Hole[] holes) {
        for (int i = 0; i < holes.length; i++) {
            holes[i] = new Hole(i, String.format(Locale.getDefault(), "Hole %d:", i+1), 0);
            int score = mSharedPreferences.getInt(String.format(Locale.getDefault(), "Hole%d", holes[i].getId()), 0);
            holes[i].setScore(score);
        }
        return holes;
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (Hole hole : holes) {
            mEditor.putInt(String.format(Locale.getDefault(), "Hole%d", hole.getId()), hole.getScore());
        }
        mEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearScores:
                mEditor.clear();
                mEditor.commit();
                for(Hole hole: holes) {
                    hole.setScore(0);
                }
                RecyclerView.Adapter asdf = mRecyclerView.getAdapter();
                asdf.notifyDataSetChanged();
        }

        return true;
    }
}
