package com.davidhincapie.mybowlingscores;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by davidhincapie on 10/18/16.
 */

public class HistoryActivity extends ListActivity {

    private ArrayList<BowlingScores> allBowlingScores;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;

        setContentView(R.layout.history_layout);

        //get the data from the app
        MyBowlingScoresApplication app = (MyBowlingScoresApplication) getApplication();
        allBowlingScores = app.getAllBowlingScores();
        BowlingScores.updateRunningAverages(allBowlingScores);

        // View   ---   Adapter   ---  Data
        setListAdapter(
                new ArrayAdapter<BowlingScores>(this, R.layout.history_row,
                        allBowlingScores
                ));

        ListView listView = this.getListView();
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        verifyDeleteRow(position);
                        Log.d("DEBUG", "I hear item selected: " + position);
                    }
                }
        );
    }

    private void verifyDeleteRow(final int position) {
        // pop-up dialog to confirm delete row
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this series?")
                .setMessage("Do you want to delete this data? \n" + allBowlingScores.get(position))
                .setIcon(R.drawable.lane)
                .setCancelable(false)
                .setNegativeButton("NO! Leave it there!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BowlingScores toDelete = allBowlingScores.get(position);
                                MyBowlingScoresApplication app = (MyBowlingScoresApplication) getApplication();
                                app.deleteBowlingScores(toDelete);
                                onCreate(savedInstanceState);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();


    }
}
