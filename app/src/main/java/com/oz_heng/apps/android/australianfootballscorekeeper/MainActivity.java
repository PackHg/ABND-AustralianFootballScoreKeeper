package com.oz_heng.apps.android.australianfootballscorekeeper;

import android.content.SharedPreferences;
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
    int teamBGoalScore = 0;
    int teamBBehindScore = 0;

    // Teams A & B score TextViews
    TextView teamAGoalScoreTextView;
    TextView teamABehindScoreTextView;
    TextView teamATotalScoreTextView;
    TextView teamBGoalScoreTextView;
    TextView teamBBehindScoreTextView;
    TextView teamBTotalScoreTextView;

    // Keys to save the scores if this activity is going to be stopped.
    final static String KEY_TEAM_A_GOAL_SCORE = "teamAGoalScore";
    final static String KEY_TEAM_A_BEHIND_SCORE = "teamABehindScore";
    final static String KEY_TEAM_B_GOAL_SCORE = "teamBGoalScore";
    final static String KEY_TEAM_B_BEHIND_SCORE = "teamBBehindScore";

    // Preferences name used to save the teams scores.
    final static String TEAMS_SCORES = "com.oz_heng.apps.android.australianfootballscorekeeper.TeamsScores";

    FragmentManager fragmentManager = getSupportFragmentManager();
    final static String SCORING_RULES_DIALOG_FRAGMENT_TAG = "com.oz_heng.apps.android.australianfootballscorekeeper.ScoringRulesDialogFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set UI in portrait mode.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Restore the saved scores.
        SharedPreferences teamsScoresSP = getSharedPreferences(TEAMS_SCORES, 0);
        if (teamsScoresSP != null) {
            teamAGoalScore = teamsScoresSP.getInt(KEY_TEAM_A_GOAL_SCORE, teamAGoalScore);
            teamABehindScore = teamsScoresSP.getInt(KEY_TEAM_A_BEHIND_SCORE, teamABehindScore);
            teamBGoalScore = teamsScoresSP.getInt(KEY_TEAM_B_GOAL_SCORE, teamBGoalScore);
            teamBBehindScore = teamsScoresSP.getInt(KEY_TEAM_B_BEHIND_SCORE, teamBBehindScore);
        }

        // Identify the TextViews.
        teamAGoalScoreTextView = (TextView) findViewById(R.id.team_a_goal_score);
        teamABehindScoreTextView = (TextView) findViewById(R.id.team_a_behind_score);
        teamATotalScoreTextView= (TextView) findViewById(R.id.team_a_total_score);
        teamBGoalScoreTextView = (TextView) findViewById(R.id.team_b_goal_score);
        teamBBehindScoreTextView = (TextView) findViewById(R.id.team_b_behind_score);
        teamBTotalScoreTextView= (TextView) findViewById(R.id.team_b_total_score);

        // Display the teams scores.
        teamAGoalScoreTextView.setText(String.valueOf(teamAGoalScore));
        teamABehindScoreTextView.setText(String.valueOf(teamABehindScore));
        teamBGoalScoreTextView.setText(String.valueOf(teamBGoalScore));
        teamBBehindScoreTextView.setText(String.valueOf(teamBBehindScore));
        updaTeamATotalScore();
        updaTeamBTotalScore();

        /* If Team A Goal Button is clicked, add 6 points to Team A Goal score TextView
           and update Team A total score TextView.
         */
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
                scoringRulesDialog.show(fragmentManager, SCORING_RULES_DIALOG_FRAGMENT_TAG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Save the scores if this activity is going to be stopped.
     */
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences teamsScoresSP = getSharedPreferences(TEAMS_SCORES, 0);
        SharedPreferences.Editor editor = teamsScoresSP.edit();
        editor.putInt(KEY_TEAM_A_GOAL_SCORE, teamAGoalScore);
        editor.putInt(KEY_TEAM_A_BEHIND_SCORE, teamABehindScore);
        editor.putInt(KEY_TEAM_B_GOAL_SCORE, teamBGoalScore);
        editor.putInt(KEY_TEAM_B_BEHIND_SCORE, teamBBehindScore);
        editor.apply();
    }

    /**
     * Update Team A total score
     */
    private void updaTeamATotalScore() {
        teamATotalScoreTextView.setText(String.valueOf(teamAGoalScore + teamABehindScore));
    }

    /**
     * Update Team B total score
     */
    private void updaTeamBTotalScore() {
        teamBTotalScoreTextView.setText(String.valueOf(teamBGoalScore + teamBBehindScore));
    }

    private void resetScores() {
        teamAGoalScore = 0;
        teamABehindScore = 0;
        teamBGoalScore = 0;
        teamBBehindScore = 0;

        teamAGoalScoreTextView.setText(String.valueOf(0));
        teamABehindScoreTextView.setText(String.valueOf(0));
        teamATotalScoreTextView.setText(String.valueOf(0));
        teamBGoalScoreTextView.setText(String.valueOf(0));
        teamBBehindScoreTextView.setText(String.valueOf(0));
        teamBTotalScoreTextView.setText(String.valueOf(0));
    }
}

