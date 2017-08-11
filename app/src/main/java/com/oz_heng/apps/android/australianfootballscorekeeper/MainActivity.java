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

    // Tag used to save the teams scores with SharedPreferences.
    final static String TEAMS_SCORES = "com.oz_heng.apps.android.australianfootballscorekeeper.TeamsScores";

    // TAG used for DialogFragment
    final static String SCORING_RULES_DIALOG_FRAGMENT_TAG = "com.oz_heng.apps.android.australianfootballscorekeeper.ScoringRulesDialogFragment";

    final FragmentManager fragmentManager = getSupportFragmentManager();

    // Keys to save the scores if this activity is going to be stopped.
    private final static String KEY_TEAM_A_GOAL_SCORE = "teamA_GoalScore";
    private final static String KEY_TEAM_A_BEHIND_SCORE = "teamA_BehindScore";
    private final static String KEY_TEAM_B_GOAL_SCORE = "teamB_GoalScore";
    private final static String KEY_TEAM_B_BEHIND_SCORE = "teamB_BehindScore";

    // Variables containing Teams A and B scores
    private int teamA_GoalScore = 0;
    private int teamA_BehindScore = 0;
    private int teamB_GoalScore = 0;
    private int teamB_BehindScore = 0;

    // Teams A & B score TextViews
    private TextView teamA_GoalScoreTextView;
    private TextView teamA_BehindScoreTextView;
    private TextView teamA_TotalScoreTextView;
    private TextView teamB_GoalScoreTextView;
    private TextView teamB_BehindScoreTextView;
    private TextView teamB_TotalScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set UI in portrait mode.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Restore the saved scores.
        SharedPreferences teamsScoresSP = getSharedPreferences(TEAMS_SCORES, 0);
        if (teamsScoresSP != null) {
            teamA_GoalScore = teamsScoresSP.getInt(KEY_TEAM_A_GOAL_SCORE, teamA_GoalScore);
            teamA_BehindScore = teamsScoresSP.getInt(KEY_TEAM_A_BEHIND_SCORE, teamA_BehindScore);
            teamB_GoalScore = teamsScoresSP.getInt(KEY_TEAM_B_GOAL_SCORE, teamB_GoalScore);
            teamB_BehindScore = teamsScoresSP.getInt(KEY_TEAM_B_BEHIND_SCORE, teamB_BehindScore);
        }

        // Identify the TextViews.
        teamA_GoalScoreTextView = (TextView) findViewById(R.id.team_a_goal_score);
        teamA_BehindScoreTextView = (TextView) findViewById(R.id.team_a_behind_score);
        teamA_TotalScoreTextView = (TextView) findViewById(R.id.team_a_total_score);
        teamB_GoalScoreTextView = (TextView) findViewById(R.id.team_b_goal_score);
        teamB_BehindScoreTextView = (TextView) findViewById(R.id.team_b_behind_score);
        teamB_TotalScoreTextView = (TextView) findViewById(R.id.team_b_total_score);

        // Display the teams scores.
        teamA_GoalScoreTextView.setText(String.valueOf(teamA_GoalScore));
        teamA_BehindScoreTextView.setText(String.valueOf(teamA_BehindScore));
        teamB_GoalScoreTextView.setText(String.valueOf(teamB_GoalScore));
        teamB_BehindScoreTextView.setText(String.valueOf(teamB_BehindScore));
        updateTeamA_TotalScore();
        updapteTeamB_TotalScore();

        /* If Team A Goal Button is clicked, add 6 points to Team A Goal score TextView
           and update Team A total score TextView.
         */
        Button teamA_GoalButton = (Button) findViewById(R.id.team_a_goal);
        teamA_GoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamA_GoalScore += 6;
                teamA_GoalScoreTextView.setText(String.valueOf(teamA_GoalScore));
                updateTeamA_TotalScore();
            }
        });

        /* If Team A Behind Button is clicked, add 1 point to Team A Behind score TextView
           and update Team A total score TextView.
         */
        Button teamA_BehindButton = (Button) findViewById(R.id.team_a_behind);
        teamA_BehindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamA_BehindScore += 1;
                teamA_BehindScoreTextView.setText(String.valueOf(teamA_BehindScore));
                updateTeamA_TotalScore();
            }
        });

        /* If Team B Goal Button is clicked, add 6 points to Team B Goal score TextView
           and update Team B total score TextView.
         */
        Button teamB_GoalButton = (Button) findViewById(R.id.team_b_goal);
        teamB_GoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamB_GoalScore += 6;
                teamB_GoalScoreTextView.setText(String.valueOf(teamB_GoalScore));
                updapteTeamB_TotalScore();
            }
        });

        /* If Team B Behind Button is clicked, add 1 point to Team B Behind score TextView
           and update Team B total score TextView.
         */
        Button teamB_BehindButton = (Button) findViewById(R.id.team_b_behind);
        teamB_BehindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamB_BehindScore += 1;
                teamB_BehindScoreTextView.setText(String.valueOf(teamB_BehindScore));
                updapteTeamB_TotalScore();
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
        editor.putInt(KEY_TEAM_A_GOAL_SCORE, teamA_GoalScore);
        editor.putInt(KEY_TEAM_A_BEHIND_SCORE, teamA_BehindScore);
        editor.putInt(KEY_TEAM_B_GOAL_SCORE, teamB_GoalScore);
        editor.putInt(KEY_TEAM_B_BEHIND_SCORE, teamB_BehindScore);
        editor.apply();
    }

    /**
     * Update Team A total score
     */
    private void updateTeamA_TotalScore() {
        teamA_TotalScoreTextView.setText(String.valueOf(teamA_GoalScore + teamA_BehindScore));
    }

    /**
     * Update Team B total score
     */
    private void updapteTeamB_TotalScore() {
        teamB_TotalScoreTextView.setText(String.valueOf(teamB_GoalScore + teamB_BehindScore));
    }

    private void resetScores() {
        teamA_GoalScore = 0;
        teamA_BehindScore = 0;
        teamB_GoalScore = 0;
        teamB_BehindScore = 0;

        teamA_GoalScoreTextView.setText(String.valueOf(0));
        teamA_BehindScoreTextView.setText(String.valueOf(0));
        teamA_TotalScoreTextView.setText(String.valueOf(0));
        teamB_GoalScoreTextView.setText(String.valueOf(0));
        teamB_BehindScoreTextView.setText(String.valueOf(0));
        teamB_TotalScoreTextView.setText(String.valueOf(0));
    }
}

