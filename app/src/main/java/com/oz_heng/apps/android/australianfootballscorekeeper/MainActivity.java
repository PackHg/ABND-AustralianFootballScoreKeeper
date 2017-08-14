package com.oz_heng.apps.android.australianfootballscorekeeper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.team_a_goal_score) TextView teamA_GoalScoreTextView;
    @BindView(R.id.team_a_behind_score) TextView teamA_BehindScoreTextView;
    @BindView(R.id.team_a_total_score) TextView teamA_TotalScoreTextView;
    @BindView(R.id.team_b_goal_score) TextView teamB_GoalScoreTextView;
    @BindView(R.id.team_b_behind_score) TextView teamB_BehindScoreTextView;
    @BindView(R.id.team_b_total_score) TextView teamB_TotalScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Restore the saved scores.
        SharedPreferences teamsScoresSP = getSharedPreferences(TEAMS_SCORES, 0);
        if (teamsScoresSP != null) {
            teamA_GoalScore = teamsScoresSP.getInt(KEY_TEAM_A_GOAL_SCORE, teamA_GoalScore);
            teamA_BehindScore = teamsScoresSP.getInt(KEY_TEAM_A_BEHIND_SCORE, teamA_BehindScore);
            teamB_GoalScore = teamsScoresSP.getInt(KEY_TEAM_B_GOAL_SCORE, teamB_GoalScore);
            teamB_BehindScore = teamsScoresSP.getInt(KEY_TEAM_B_BEHIND_SCORE, teamB_BehindScore);
        }

        // Display the teams scores.
        teamA_GoalScoreTextView.setText(String.valueOf(teamA_GoalScore));
        teamA_BehindScoreTextView.setText(String.valueOf(teamA_BehindScore));
        teamB_GoalScoreTextView.setText(String.valueOf(teamB_GoalScore));
        teamB_BehindScoreTextView.setText(String.valueOf(teamB_BehindScore));
        updateTeamA_TotalScore();
        updapteTeamB_TotalScore();
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

    /* If Team A Goal Button is clicked, add 6 points to Team A Goal score TextView
       and update Team A total score TextView.
     */
    @OnClick(R.id.button_team_a_goal)
    public void updateTeamA_GoalScore(View view) {
        teamA_GoalScore += 6;
        teamA_GoalScoreTextView.setText(String.valueOf(teamA_GoalScore));
        updateTeamA_TotalScore();
    }

    /* If Team A Behind Button is clicked, add 1 point to Team A Behind score TextView
       and update Team A total score TextView.
     */
    @OnClick(R.id.button_team_a_behind)
    public void updateTeamA_BehindScore(View view) {
        teamA_BehindScore ++;
        teamA_BehindScoreTextView.setText(String.valueOf(teamA_BehindScore));
        updateTeamA_TotalScore();
    }

    /* If Team B Goal Button is clicked, add 6 points to Team B Goal score TextView
       and update Team B total score TextView.
     */
    @OnClick(R.id.button_team_b_goal)
    public void updateTeamB_GoalScore(View view) {
        teamB_GoalScore += 6;
        teamB_GoalScoreTextView.setText(String.valueOf(teamB_GoalScore));
        updapteTeamB_TotalScore();
    }

    /* If Team B Behind Button is clicked, add 1 point to Team B Behind score TextView
       and update Team B total score TextView.
     */
    @OnClick(R.id.button_team_b_behind)
    public void updateTeamB_BehindScore(View view) {
        teamB_BehindScore ++;
        teamB_BehindScoreTextView.setText(String.valueOf(teamB_BehindScore));
        updapteTeamB_TotalScore();
    }

    // If Reset button is clicked, set all the scores to 0.
    @OnClick(R.id.button_reset)
    public void reset(View view) {
        resetScores();
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

