package com.davidhincapie.mybowlingscores;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EnterScoresActivity extends AppCompatActivity {

    private Button saveButton;
    private Button historyButton;
    private Button changeDateButton;
    private EditText game1ScoreEditText;
    private EditText game2ScoreEditText;
    private EditText game3ScoreEditText;
    private TextView date;
    private TextView seriesTotal;

    private int month;  //private within this class
    private int day;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_scores);

        setupViews();

        //put today's date on the screen
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Date today = calendar.getTime();

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String cs = df.format(today);
        date.setText(cs);


    }


    /**
     * Get the three scores from the interface
     * Validate the scores
     * create an object to hold the scores
     *
     * @param v
     */
    public void saveClickHandler(View v) {
        String rawScore;
        rawScore = game1ScoreEditText.getText().toString();
        int score1 = Integer.parseInt(rawScore);
        rawScore = game2ScoreEditText.getText().toString();
        int score2 = Integer.parseInt(rawScore);
        rawScore = game3ScoreEditText.getText().toString();
        int score3 = Integer.parseInt(rawScore);

        Log.d("EnterScores", "I hear the Save Button");


        if (isValid(score1) && isValid(score2) && isValid(score3)) {
            BowlingScores bowlingScores;
            Date dateOfGames = new GregorianCalendar(year, month, day).getTime();
            bowlingScores = new BowlingScores(score1, score2, score3, dateOfGames);
            seriesTotal.setText("" + bowlingScores.calculateSeriesScore());

            MyBowlingScoresApplication app = (MyBowlingScoresApplication) getApplication();
            app.addBowlingScores(bowlingScores);

            Toast.makeText(getApplicationContext(), "Thank you for entering your scores!", Toast.LENGTH_LONG).show();
            //Change to the History Activity
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        } else {
            //pop up dialog indicating that data is invalid
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invalid Scores")
                    .setMessage("Bowling scores cannot be greater than 300")
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();

        }
    }

    private boolean isValid(int score) {
        return score >= 0 && score <= 300;
    }

    public void handleShowHistoryClick(View v) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void changeDateClickHandler(View v) {
        Log.d("EnterScores", "I hear the Change Date Button");

        DatePickerDialog.OnDateSetListener datePickerListener;
        datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int yearOfYear, int monthOfYear, int dayOfMonth) {
                year = yearOfYear;
                month = monthOfYear;
                day = dayOfMonth;

                Calendar cal = new GregorianCalendar(year, month, day);
                Date dateOfGames = cal.getTime();
                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
                String cs = df.format(dateOfGames);
                date.setText(cs);
            }
        };

        DatePickerDialog dpd = new DatePickerDialog(this, datePickerListener, year, month, day);
        dpd.show();
    }

    private void setupViews() {
        saveButton = (Button) findViewById(R.id.saveButton);
        historyButton = (Button) findViewById(R.id.showHistoryButton);
        game1ScoreEditText = (EditText) findViewById(R.id.game1EditText);
        game2ScoreEditText = (EditText) findViewById(R.id.game2EditText);
        game3ScoreEditText = (EditText) findViewById(R.id.game3EditText);
        date = (TextView) findViewById(R.id.dateTextView);
        seriesTotal = (TextView) findViewById(R.id.seriesTotalTextView);
    }
}
