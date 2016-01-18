package com.bbcivil.toss.sample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.bbcivil.toss.TossImageView;
import com.bbcivil.toss.animation.TossAnimation;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String[] mInterpolatorNames = new String[]{
            "Decelerate",
            "Accelerate",
            "AccelerateDecelerate",
            "Bounce",
            "Overshoot",
            "AnticipateOvershoot"
    };
    private static final String[] mDirectionNames = new String[]{
            "none",
            "clockwise",
            "abtucclockwise"
    };

    private static final Interpolator[] mInterpolators = new Interpolator[]{
            new DecelerateInterpolator(),
            new AccelerateInterpolator(),
            new AccelerateDecelerateInterpolator(),
            new BounceInterpolator(),
            new OvershootInterpolator(),
            new AnticipateOvershootInterpolator()
    };
    private static final int[] mDirections = new int[]{
            TossAnimation.DIRECTION_NONE,
            TossAnimation.DIRECTION_CLOCKWISE,
            TossAnimation.DIRECTION_ABTUCCLOCKWISE
    };

    private Spinner mSpinner;
    private SeekBar mSeekBar;
    private EditText mEditText;
    private Spinner xSpinner, ySpinner, zSpinner;

    private Button start;
    private TossImageView mTossImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("TossImageView");

        setContentView(R.layout.activity_main);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mSeekBar.setProgress(getResources().getInteger(R.integer.toss_default_duration));
        mEditText = (EditText) findViewById(R.id.edittext);
        xSpinner = (Spinner) findViewById(R.id.xdirection);
        ySpinner = (Spinner) findViewById(R.id.ydirection);
        zSpinner = (Spinner) findViewById(R.id.zdirection);

        mSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mInterpolatorNames));
        xSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mDirectionNames));
        ySpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mDirectionNames));
        zSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mDirectionNames));
        mTossImageView = (TossImageView) findViewById(R.id.tiv);

        xSpinner.setSelection(1);

        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                int circleCount = getResources().getInteger(R.integer.toss_default_circleCount);
                try {
                    circleCount = Integer.parseInt(mEditText.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mTossImageView.setInterpolator(mInterpolators[mSpinner.getSelectedItemPosition()])
                        .setDuration(mSeekBar.getProgress())
                        .setCircleCount(circleCount)
                        .setXAxisDirection(mDirections[xSpinner.getSelectedItemPosition()])
                        .setYAxisDirection(mDirections[ySpinner.getSelectedItemPosition()])
                        .setZAxisDirection(mDirections[zSpinner.getSelectedItemPosition()])
                        .setResult(new Random().nextInt(2) == 0 ? TossImageView.RESULT_FRONT : TossImageView.RESULT_REVERSE)
                        .startToss();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.more:
                startActivity(new Intent(this, ComplexTossActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
