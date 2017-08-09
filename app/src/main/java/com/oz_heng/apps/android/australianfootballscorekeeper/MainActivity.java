package com.oz_heng.apps.android.australianfootballscorekeeper;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Variables containing Teams A and B scores
    int teamAGoalScore = 0;
    int teamABehindScore = 0;
    int teamATotalScore = 0;
    int teamBGoalScore = 0;
    int teamBBehindScore = 0;
    int teamBTotalScore = 0;

    // Teams A & B score TextViews
    TextView teamAGoalScoreTextView;
    TextView teamABehindScoreTextView;
    TextView teamATotalScoreTextView;
    TextView teamBGoalScoreTextView;
    TextView teamBBehindScoreTextView;
    TextView teamBTotalScoreTextView;

    FragmentManager fragmentManager = getSupportFragmentManager();
    final String fragmentTag = "Scoring Rules Dialog Fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set UI in portrait mode.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        teamATotalScoreTextView= (TextView) findViewById(R.id.team_a_total_score);
        teamBTotalScoreTextView= (TextView) findViewById(R.id.team_b_total_score);

        /* If Team A Goal Button is clicked, add 6 points to Team A Goal score TextView
           and update Team A total score TextView.
         */
        teamAGoalScoreTextView = (TextView) findViewById(R.id.team_a_goal_score);
        Button teamAGoalButton = (Button) findViewById(R.id.team_a_goal);
        teamAGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamAGoalScore += 6;
                teamAGoalScoreTextView.setText(String.valueOf(teamAGoalScore));
                updaTeamATotalScore();
            }
        });

        /* If Team A Behind Button is clicked, add 1 point to Team A Behind score TextView
           and update Team A total score TextView.
         */
        teamABehindScoreTextView = (TextView) findViewById(R.id.team_a_behind_score);
        Button teamABehindButton = (Button) findViewById(R.id.team_a_behind);
        teamABehindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamABehindScore += 1;
                teamABehindScoreTextView.setText(String.valueOf(teamABehindScore));
                updaTeamATotalScore();
            }
        });

        /* If Team B Goal Button is clicked, add 6 points to Team B Goal score TextView
           and update Team B total score TextView.
         */
        teamBGoalScoreTextView = (TextView) findViewById(R.id.team_b_goal_score);
        Button teamBGoalButton = (Button) findViewById(R.id.team_b_goal);
        teamBGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamBGoalScore += 6;
                teamBGoalScoreTextView.setText(String.valueOf(teamBGoalScore));
                updaTeamBTotalScore();
            }
        });

        /* If Team B Behind Button is clicked, add 1 point to Team B Behind score TextView
           and update Team B total score TextView.
         */
        teamBBehindScoreTextView = (TextView) findViewById(R.id.team_b_behind_score);
        Button teamBBehindButton = (Button) findViewById(R.id.team_b_behind);
        teamBBehindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamBBehindScore += 1;
                teamBBehindScoreTextView.setText(String.valueOf(teamBBehindScore));
                updaTeamBTotalScore();
            }
        });

        // If Reset button is clicked, set all the scores to 0.
        Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetScores();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scoring_rules:
                ScoringRulesDialog scoringRulesDialog = new ScoringRulesDialog();
                scoringRulesDialog.show(fragmentManager, fragmentTag);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Update Team A total score
     */
    private void updaTeamATotalScore() {
        teamATotalScore = teamAGoalScore + teamABehindScore;
        teamATotalScoreTextView.setText(String.valueOf(teamATotalScore));
    }

    /**
     * Update Team B total score
     */
    private void updaTeamBTotalScore() {
        teamBTotalScore = teamBGoalScore + teamBBehindScore;
        teamBTotalScoreTextView.setText(String.valueOf(teamBTotalScore));
    }

    private void resetScores() {
        teamAGoalScore = 0;
        teamABehindScore = 0;
        teamATotalScore = 0;
        teamBGoalScore = 0;
        teamBBehindScore = 0;
        teamBTotalScore = 0;

        teamAGoalScoreTextView.setText(String.valueOf(0));
        teamABehindScoreTextView.setText(String.valueOf(0));
        teamATotalScoreTextView.setText(String.valueOf(0));
        teamBGoalScoreTextView.setText(String.valueOf(0));
        teamBBehindScoreTextView.setText(String.valueOf(0));
        teamBTotalScoreTextView.setText(String.valueOf(0));
    }
}

